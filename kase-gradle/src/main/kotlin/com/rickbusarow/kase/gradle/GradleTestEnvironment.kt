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

import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.TestFunctionCoordinates
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
  public val testVersions: TestVersions,
  testFunctionCoordinates: TestFunctionCoordinates
) : TestEnvironment(testVersions.displayNames, testFunctionCoordinates) {

  public val root: File get() = workingDir

  public val kotlinVersion: String get() = testVersions.kotlin
  public val agpVersion: String get() = testVersions.agp
  public val gradleVersion: String get() = testVersions.gradle

  public val DEFAULT_BUILD_FILE: String by lazy {
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

  public val rootBuild: File by lazy {
    root.resolve("build.gradle.kts")
      .createSafely(DEFAULT_BUILD_FILE, overwrite = false)
  }

  public val DEFAULT_SETTINGS_FILE: String by lazy {
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

  public val rootSettings: File by lazy {
    root.resolve("settings.gradle.kts")
      .createSafely(DEFAULT_SETTINGS_FILE)
  }

  public val rootProject: File by lazy {
    rootBuild
    rootSettings
    root
  }

  public val gradleRunner: GradleRunner by lazy {
    GradleRunner.create()
      .forwardOutput()
      .withGradleVersion(gradleVersion)
      .withDebug(true)
      .withProjectDir(workingDir)
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

  public fun shouldSucceed(
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

  public fun shouldFail(
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

  public infix fun BuildResult.withTrimmedMessage(message: String) {
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

  public fun markdown(path: String, @Language("markdown") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeMarkdownContent")
  public fun File.markdown(@Language("markdown") content: String): File =
    createSafely(content.trimIndent())

  public fun java(path: String, @Language("java") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeJavaContent")
  public fun File.java(@Language("java") content: String): File = createSafely(content.trimIndent())

  public fun groovy(path: String, @Language("groovy") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeGroovyContent")
  public fun File.groovy(@Language("groovy") content: String): File = createSafely(
    content.trimIndent()
  )

  public fun kotlin(path: String, @Language("kotlin") content: String): File =
    File(path).createSafely(content.trimIndent())

  @JvmName("writeKotlinContent")
  public fun File.kotlin(@Language("kotlin") content: String): File = createSafely(
    content.trimIndent()
  )

  public operator fun File.invoke(contentBuilder: () -> String) {
    createSafely(contentBuilder().trimIndent())
  }

  /** replace `ModuleCheck found 2 issues in 1.866 seconds.` with `ModuleCheck found 2 issues` */
  public fun String.removeDuration(): String {
    return replace(durationSuffixRegex) { it.destructured.component1() }
  }

  public companion object {
    protected val durationSuffixRegex: Regex =
      """(ModuleCheck found \d+ issues?) in [\d.]+ seconds\.[\s\S]*""".toRegex()
  }
}
