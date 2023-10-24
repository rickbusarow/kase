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

import com.rickbusarow.kase.AnyKase
import com.rickbusarow.kase.DirectoryBuilder
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.TestFunctionCoordinates
import com.rickbusarow.kase.TestVariant
import com.rickbusarow.kase.buildDirectory
import com.rickbusarow.kase.gradle.generation.BuildFileComponents
import com.rickbusarow.kase.gradle.generation.DslLanguage
import com.rickbusarow.kase.stdlib.createSafely
import com.rickbusarow.kase.stdlib.letIf
import com.rickbusarow.kase.stdlib.remove
import com.rickbusarow.kase.stdlib.replaceIndent
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.FAILED
import org.intellij.lang.annotations.Language
import java.io.File

/** A factory for creating [GradleTestEnvironment]s. */
public interface GradleTestEnvironmentFactory<T : TestVersions, K : AnyKase> {

  /** Creates a new [GradleTestEnvironment] for the given [testVariant] and [testVersions]. */
  public fun newTestEnvironment(
    testVariant: TestVariant<K>,
    testVersions: T
  ): GradleTestEnvironment<T, K> {
    return GradleTestEnvironment(
      testVersions = testVersions,
      testFunctionCoordinates = testVariant.testFunctionCoordinates,
      kase = testVariant.kase,
      buildFileComponents = object : BuildFileComponents {},
      DslLanguage.Groovy(alwaysUseDoubleQuotes = false, useInfix = true)
    )
  }
}

/**
 * A [TestEnvironment] which provides a [GradleRunner]
 * and a [File] representing the root project directory.
 *
 * @property testVersions the [TestVersions] for this test environment
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run
 * @param kase the variant names related to the test
 * @param buildFileComponents the [BuildFileComponents] for this test environment
 * @param dslLanguage the [DslLanguage] for this test environment
 */
@Suppress("PropertyName", "VariableNaming", "MemberVisibilityCanBePrivate", "MagicNumber")
public class GradleTestEnvironment<T : TestVersions, K : AnyKase>(
  override val testVersions: T,
  testFunctionCoordinates: TestFunctionCoordinates,
  kase: K,
  buildFileComponents: BuildFileComponents,
  dslLanguage: DslLanguage
) : TestEnvironment(kase.displayNames, testFunctionCoordinates),
  TestVersions by testVersions,
  HasTestVersions<T> {

  /** The [File] representing the root project directory. */
  public val root: File get() = workingDir

  /** The [File] representing the root project directory. */
  public inline fun root(action: DirectoryBuilder.() -> Unit): File {
    return buildDirectory(workingDir, action)
  }

  /** The default build.gradle file. */
  public val DEFAULT_BUILD_FILE: String by lazy {
    """
    buildscript {
      dependencies {
        ${
      buildFileComponents.buildscriptDependenciesBlockContent(dslLanguage, testVersions)
        .replaceIndent(4)
    }
      }
    }

    plugins {

    }
    """.trimIndent()
  }

  /** The default build.gradle file. */
  public val rootBuild: File by lazy {
    root.resolve("build.gradle.kts")
      .createSafely(DEFAULT_BUILD_FILE, overwrite = false)
  }

  /** The default settings.gradle file. */
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

  /** The root settings.gradle file. */
  public val rootSettings: File by lazy {
    root.resolve("settings.gradle.kts")
      .createSafely(DEFAULT_SETTINGS_FILE)
  }

  /** The root project directory. */
  public val rootProject: File by lazy {
    rootBuild
    rootSettings
    root
  }

  /** The [GradleRunner] used to execute Gradle builds. */
  public val gradleRunner: GradleRunner by lazy {
    GradleRunner.create()
      .forwardOutput()
      .withGradleVersion(gradleVersion)
      .withDebug(true)
      .withProjectDir(workingDir)
  }

  @Suppress("UnusedPrivateMember")
  private fun addIncludes() {

    val subprojectDirs = root.walkTopDown()
      .onEnter {
        !it.resolve("settings.gradle").exists() &&
          !it.resolve("settings.gradle.kts").exists()
      }
      .filter { it.isDirectory }
      .filter {
        it.resolve("build.gradle").exists() ||
          it.resolve("build.gradle.kts").exists()
      }

    val subprojectPaths = subprojectDirs.map {
      it.relativePath()
        .replace(File.separatorChar, ':')
    }

    val includes = subprojectPaths
      .joinToString(
        separator = "\n",
        prefix = "\n",
        postfix = "\n"
      ) { "include(\"$it\")" }
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

  /**
   * Runs the given [tasks] and asserts that they succeed.
   *
   * @param tasks the tasks to run
   * @param withPluginClasspath whether to include the plugin classpath
   * @param stacktrace whether to include the stacktrace
   * @param assertions additional assertions to run on the [BuildResult]
   */
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

  /**
   * Runs the given [tasks] and asserts that they fail.
   *
   * @param tasks the tasks to run
   * @param withPluginClasspath whether to include the plugin classpath
   * @param stacktrace whether to include the stacktrace
   * @param assertions additional assertions to run on the [BuildResult]
   */
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

  /** Asserts that the [BuildResult] has the given [message] in its output. */
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

    trimmed shouldBe message
  }

  /**
   * Creates a file with the given [path] and [content], with Markdown language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun markdown(path: String, @Language("markdown") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Markdown language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  @JvmName("writeMarkdownContent")
  public fun File.markdown(@Language("markdown") content: String): File =
    createSafely(content.trimIndent())

  /**
   * Creates a file with the given [path] and [content], with Java language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun java(path: String, @Language("java") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Java language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  @JvmName("writeJavaContent")
  public fun File.java(@Language("java") content: String): File = createSafely(content.trimIndent())

  /**
   * Creates a file with the given [path] and [content], with Groovy language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun groovy(path: String, @Language("groovy") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Groovy language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  @JvmName("writeGroovyContent")
  public fun File.groovy(@Language("groovy") content: String): File =
    createSafely(content.trimIndent())

  /**
   * Creates a file with the given [path] and [content], with Kotlin language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun kotlin(path: String, @Language("kotlin") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Kotlin language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  @JvmName("writeKotlinContent")
  public fun File.kotlin(@Language("kotlin") content: String): File =
    createSafely(content.trimIndent())

  /** Writes the result of [contentBuilder] to the receiver file. */
  public operator fun File.invoke(contentBuilder: () -> String) {
    createSafely(contentBuilder().trimIndent())
  }
}
