/*
 * Copyright (C) 2024 Rick Busarow
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

import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.useRelativePaths
import com.rickbusarow.kase.stdlib.div
import com.rickbusarow.kase.stdlib.letIf
import com.rickbusarow.kase.stdlib.remove
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.FAILED
import java.io.File

/**
 * Trait interface for a test environment with a [GradleRunner].
 *
 * @since 0.1.0
 */
public interface HasGradleRunner : HasWorkingDir {

  /**
   * The [GradleRunner] used to execute Gradle builds.
   *
   * @since 0.1.0
   */
  public val gradleRunner: GradleRunner

  /**
   * Runs the given [tasks] and asserts that they succeed.
   *
   * @param tasks the tasks to run
   * @param withPluginClasspath whether to include the plugin classpath
   * @param withHermeticTestKit whether to have a testKit directory unique to this test environment.
   * @param stacktrace whether to include the stacktrace
   * @param debug whether to include the debug flag
   * @param projectDir the project directory to use
   * @param shouldFail whether the build should fail
   * @since 0.1.0
   */
  public fun build(
    tasks: List<String>,
    withPluginClasspath: Boolean = false,
    withHermeticTestKit: Boolean = false,
    stacktrace: Boolean = true,
    debug: Boolean = false,
    projectDir: File = workingDir,
    shouldFail: Boolean = false
  ): BuildResult

  /**
   * Runs the given [tasks] and asserts that they succeed.
   *
   * @param tasks the tasks to run
   * @param withPluginClasspath whether to include the plugin classpath
   * @param withHermeticTestKit whether to have a testKit directory unique to this test environment.
   * @param stacktrace whether to include the stacktrace
   * @param debug whether to include the debug flag
   * @param projectDir the project directory to use
   * @param assertions additional assertions to run on the [BuildResult]
   * @since 0.1.0
   */
  public fun shouldSucceed(
    vararg tasks: String,
    withPluginClasspath: Boolean = false,
    withHermeticTestKit: Boolean = false,
    stacktrace: Boolean = true,
    debug: Boolean = false,
    projectDir: File = workingDir,
    assertions: BuildResult.() -> Unit = {}
  ): BuildResult {

    return build(
      tasks.toList(),
      withPluginClasspath = withPluginClasspath,
      withHermeticTestKit = withHermeticTestKit,
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
   * @param withHermeticTestKit whether to have a testKit directory unique to this test environment.
   * @param stacktrace whether to include the stacktrace
   * @param debug whether to include the debug flag
   * @param projectDir the project directory to use
   * @param assertions additional assertions to run on the [BuildResult]
   * @since 0.1.0
   */
  public fun shouldFail(
    vararg tasks: String,
    withPluginClasspath: Boolean = false,
    withHermeticTestKit: Boolean = false,
    stacktrace: Boolean = true,
    debug: Boolean = false,
    projectDir: File = workingDir,
    assertions: BuildResult.() -> Unit = {}
  ): BuildResult {

    return build(
      tasks.toList(),
      withPluginClasspath = withPluginClasspath,
      withHermeticTestKit = withHermeticTestKit,
      stacktrace = stacktrace,
      shouldFail = true
    ).also { result ->
      result.assertions()
    }
  }

  /**
   * Asserts that the [BuildResult.output][BuildResult.getOutput]
   * has the given [message] in its output.
   *
   * @since 0.1.0
   * @throws AssertionError if the [BuildResult] does not have the given [message]
   */
  public infix fun BuildResult.shouldHaveTrimmedMessage(message: String) {
    assert(output.contains(message)) {
      "Expected output to contain:\n\n$message\n\nActual output:\n\n$output"
    }
  }

  /**
   * Asserts that the [BuildResult.output][BuildResult.getOutput]
   * has the given [message] in its output.
   *
   * @param message the message to search for
   * @param shortenPaths whether to shorten absolute paths to relative ones
   * @since 0.1.0
   * @throws AssertionError if the [BuildResult] does not have the given [message]
   */
  public fun BuildResult.shouldHaveTrimmedMessage(message: String, shortenPaths: Boolean) {
    assert(trimGradleNoise(shortenPaths).contains(message)) {
      "Expected output to contain:\n\n$message\n\nActual output:\n\n$output"
    }
  }

  /**
   * Removes the constant Gradle output from this [BuildResult]'s [output][BuildResult.getOutput].
   *
   * @param shortenPaths whether to shorten absolute paths to relative ones
   * @since 0.1.0
   */
  public fun BuildResult.trimGradleNoise(shortenPaths: Boolean = true): String
}

/**
 * Default implementation of [HasGradleRunner].
 *
 * @since 0.1.0
 */
public open class DefaultHasGradleRunner(
  hasWorkingDir: HasWorkingDir,
  gradleVersion: () -> GradleDependencyVersion = { GradleDependencyVersion.current() }
) : HasGradleRunner,
  HasWorkingDir by hasWorkingDir {

  /**
   * The [GradleRunner] used to execute Gradle builds.
   *
   * @since 0.1.0
   */
  override val gradleRunner: GradleRunner by lazy {
    GradleRunner.create()
      .forwardOutput()
      .withGradleVersion(gradleVersion().value)
      .withProjectDir(workingDir)
  }

  override fun build(
    tasks: List<String>,
    withPluginClasspath: Boolean,
    withHermeticTestKit: Boolean,
    stacktrace: Boolean,
    debug: Boolean,
    projectDir: File,
    shouldFail: Boolean
  ): BuildResult {
    val withOptions = gradleRunner
      .letIf(projectDir != workingDir) { it.withProjectDir(projectDir) }
      .letIf(withPluginClasspath) { it.withPluginClasspath() }
      .letIf(withHermeticTestKit) { it.withTestKitDir(workingDir / "testKit") }
      .letIf(debug) { it.withDebug(true) }
      .withArguments(tasks.letIf(stacktrace) { it.plus("--stacktrace") })

    return if (shouldFail) {
      withOptions.buildAndFail()
    } else {
      withOptions.build()
        .also { result ->
          val failed = result.tasks.filter { it.outcome == FAILED }
          assert(failed.isEmpty()) {
            "The following tasks failed: ${failed.joinToString { it.path }}"
          }
        }
    }
  }

  override fun BuildResult.trimGradleNoise(shortenPaths: Boolean): String {
    return output
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
      .letIf(shortenPaths) { it.useRelativePaths() }
  }
}
