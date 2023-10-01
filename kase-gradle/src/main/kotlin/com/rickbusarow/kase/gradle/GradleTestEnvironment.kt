/*
 * Copyright (C) 2023 Rick Busarow
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.TestFunctionName
import com.rickbusarow.kase.stdlib.createSafely
import com.rickbusarow.kase.stdlib.letIf
import com.rickbusarow.kase.stdlib.remove
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.FAILED
import org.intellij.lang.annotations.Language
import java.io.File

@Suppress("PropertyName", "VariableNaming")
public class GradleTestEnvironment(
  override val testVersions: TestVersions,
  override val projectCache: MutableMap<String, KaseGradleProject>,
  testFunctionName: TestFunctionName,
  kase: Kase<*>
) : TestEnvironment(kase, testFunctionName),
  ProjectCollector,
  HasTestVersions<TestVersions> {

  private val versions by lazy {
    kase.destructured()
      .filterNotNull()
      .associateBy { it::class }
  }

  private inline fun <reified T : DependencyVersion> version(default: () -> String): String {
    return (versions[T::class] as? T)?.value ?: default()
  }

  override val root: File get() = workingDir

  public val kotlinVersion get() = testVersions.kotlin
  public val agpVersion get() = testVersions.agp
  public val gradleVersion get() = testVersions.gradle

  public val DEFAULT_BUILD_FILE by lazy {
    """
      buildscript {
        dependencies {
          classpath("com.android.tools.build:gradle:$agpVersion")
          classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        }
      }

      plugins {

      }
    """.trimIndent()
  }

  val rootBuild by lazy {
    root.resolve("build.gradle.kts")
      .createSafely(DEFAULT_BUILD_FILE, overwrite = false)
  }

  val DEFAULT_SETTINGS_FILE by lazy {
    """
      rootProject.name = "root"

      pluginManagement {
        repositories {
          gradlePluginPortal()
          mavenCentral()
          mavenLocal()
          google()
        }
        resolutionStrategy {
          eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
              useVersion("$agpVersion")
            }
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
              useVersion("$kotlinVersion")
            }
            if (requested.id.id == "com.squareup.anvil") {
              useVersion("$anvilVersion")
            }
          }
        }
      }
      dependencyResolutionManagement {
        @Suppress("UnstableApiUsage")
        repositories {
          mavenCentral()
          mavenLocal()
          google()
        }
      }
    """.trimIndent()
  }

  val rootSettings by lazy {
    root.resolve("settings.gradle.kts")
      .createSafely(DEFAULT_SETTINGS_FILE)
  }

  val rootProject by lazy {
    rootBuild
    rootSettings
    root
  }

  val gradleRunner: GradleRunner by lazy {
    GradleRunner.create()
      .forwardOutput()
      .withGradleVersion(gradleVersion)
      .withDebug(true)
      .withProjectDir(workingDir)
  }

  // Make sure that every project in the cache is also added to the root project's settings file
  private fun addIncludes() {
    val includes = projectCache.keys
      .joinToString(separator = "\n", prefix = "\n", postfix = "\n") { "include(\"$it\")" }
    rootSettings.appendText(includes)
  }

  private fun build(
    tasks: List<String>,
    withPluginClasspath: Boolean,
    stacktrace: Boolean,
    shouldFail: Boolean = false
  ): BuildResult {
    ensureFilesAreWritten()
    return gradleRunner
      .letIf(withPluginClasspath) { it.withPluginClasspath() }
      .withArguments(tasks.letIf(stacktrace) { it.plus("--stacktrace") })
      .let { runner ->
        if (shouldFail) {
          runner.buildAndFail()
        } else {
          runner.build()
            .also { result ->
              result.tasks
                .forAll { buildTask ->
                  buildTask.outcome shouldNotBe FAILED
                }
            }
        }
      }
  }

  private fun ensureFilesAreWritten() {
    rootBuild
    rootSettings
    workingDir.walkTopDown()
      .filter { it.isFile }
      .forEach { println("file://$it") }
  }

  fun shouldSucceed(
    vararg tasks: String,
    withPluginClasspath: Boolean = false,
    stacktrace: Boolean = true,
    assertions: BuildResult.() -> Unit = {}
  ): BuildResult {

    return build(
      tasks.toList(),
      withPluginClasspath = withPluginClasspath,
      stacktrace = stacktrace,
      shouldFail = false
    ).also { result ->
      result.assertions()
    }
  }

  fun shouldFail(
    vararg tasks: String,
    withPluginClasspath: Boolean = false,
    stacktrace: Boolean = true,
    assertions: BuildResult.() -> Unit = {}
  ): BuildResult {
    return build(
      tasks.toList(),
      withPluginClasspath = withPluginClasspath,
      stacktrace = stacktrace,
      shouldFail = true
    ).also { result ->
      result.assertions()
    }
  }

  infix fun BuildResult.withTrimmedMessage(message: String) {
    val trimmed = output
      .cleanOutput()
      .remove(
        "FAILURE: Build failed with an exception.",
        "* What went wrong:",
        "* Try:",
        "> Run with --stacktrace option to get the stack trace.",
        "> Run with --info or --debug option to get more log output.",
        "> Run with --scan to get full insights.",
        "* Get more help at https://help.gradle.org",
        "Daemon will be stopped at the end of the build after running out of JVM memory"
      )
      // remove standard Gradle output noise
      .remove(
        "> Task [^\\n]*".toRegex(),
        ".*Run with --.*".toRegex(),
        """See https://docs\.gradle\.org/[^/]+/userguide/command_line_interface\.html#sec:command_line_warnings""".toRegex(),
        "BUILD (?:SUCCESSFUL|FAILED) in .*".toRegex(),
        """\d+ actionable tasks?: \d+ executed""".toRegex()
      )
      .removeDuration()
      .remove("\u200B")
      .trim()

    trimmed shouldBe message
  }

  fun markdown(path: String, @Language("markdown") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeMarkdownContent")
  fun File.markdown(@Language("markdown") content: String): File =
    createSafely(content.trimIndent())

  fun java(path: String, @Language("java") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeJavaContent")
  fun File.java(@Language("java") content: String): File = createSafely(content.trimIndent())

  fun groovy(path: String, @Language("groovy") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeGroovyContent")
  fun File.groovy(@Language("groovy") content: String): File = createSafely(content.trimIndent())

  fun kotlin(path: String, @Language("kotlin") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeKotlinContent")
  fun File.kotlin(@Language("kotlin") content: String): File = createSafely(content.trimIndent())

  operator fun File.invoke(contentBuilder: () -> String) {
    createSafely(contentBuilder().trimIndent())
  }

  /** replace `ModuleCheck found 2 issues in 1.866 seconds.` with `ModuleCheck found 2 issues` */
  fun String.removeDuration(): String {
    return replace(durationSuffixRegex) { it.destructured.component1() }
  }

  companion object {
    protected val durationSuffixRegex: Regex =
      """(ModuleCheck found \d+ issues?) in [\d.]+ seconds\.[\s\S]*""".toRegex()
  }
}
