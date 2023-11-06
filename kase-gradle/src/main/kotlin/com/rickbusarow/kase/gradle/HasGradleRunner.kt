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

import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.useRelativePaths
import com.rickbusarow.kase.stdlib.div
import com.rickbusarow.kase.stdlib.letIf
import com.rickbusarow.kase.stdlib.remove
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.FAILED
import org.gradle.util.GradleVersion

/** Trait interface for a test environment with a [GradleRunner]. */
public interface HasGradleRunner {

  /** The [GradleRunner] used to execute Gradle builds. */
  public val gradleRunner: GradleRunner

  /**
   * Runs the given [tasks] and asserts that they succeed.
   *
   * @param tasks the tasks to run
   * @param withPluginClasspath whether to include the plugin classpath
   * @param withHermeticTestKit whether to have a testKit directory unique to this test environment.
   * @param stacktrace whether to include the stacktrace
   * @param shouldFail whether the build should fail
   */
  public fun build(
    tasks: List<String>,
    withPluginClasspath: Boolean = false,
    withHermeticTestKit: Boolean = false,
    stacktrace: Boolean = true,
    shouldFail: Boolean = false
  ): BuildResult

  /**
   * Runs the given [tasks] and asserts that they succeed.
   *
   * @param tasks the tasks to run
   * @param withPluginClasspath whether to include the plugin classpath
   * @param withHermeticTestKit whether to have a testKit directory unique to this test environment.
   * @param stacktrace whether to include the stacktrace
   * @param assertions additional assertions to run on the [BuildResult]
   */
  public fun shouldSucceed(
    vararg tasks: String,
    withPluginClasspath: Boolean = false,
    withHermeticTestKit: Boolean = false,
    stacktrace: Boolean = true,
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
   * @param assertions additional assertions to run on the [BuildResult]
   */
  public fun shouldFail(
    vararg tasks: String,
    withPluginClasspath: Boolean = false,
    withHermeticTestKit: Boolean = false,
    stacktrace: Boolean = true,
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
   * @throws AssertionError if the [BuildResult] does not have the given [message]
   */
  public infix fun BuildResult.shouldHaveTrimmedMessage(message: String) {
    trimGradleNoise() shouldContain message
  }

  /**
   * Asserts that the [BuildResult.output][BuildResult.getOutput]
   * has the given [message] in its output.
   *
   * @param shortenPaths whether to shorten absolute paths to relative ones
   * @param message the message to search for
   * @throws AssertionError if the [BuildResult] does not have the given [message]
   */
  public fun BuildResult.shouldHaveTrimmedMessage(shortenPaths: Boolean, message: String) {
    trimGradleNoise(shortenPaths = shortenPaths) shouldContain message
  }

  /**
   * Removes the constant Gradle output from this [BuildResult]'s [output][BuildResult.getOutput].
   *
   * @param shortenPaths whether to shorten absolute paths to relative ones
   */
  public fun BuildResult.trimGradleNoise(shortenPaths: Boolean = true): String
}

/** Default implementation of [HasGradleRunner]. */
public open class DefaultHasGradleRunner(
  hasWorkingDir: HasWorkingDir,
  gradleVersion: () -> String = { GradleVersion.current().version }
) : HasGradleRunner, HasWorkingDir by hasWorkingDir {

  /** The [GradleRunner] used to execute Gradle builds. */
  override val gradleRunner: GradleRunner by lazy {
    GradleRunner.create()
      .forwardOutput()
      .withGradleVersion(gradleVersion())
      .withDebug(true)
      .withProjectDir(workingDir)
  }

  override fun build(
    tasks: List<String>,
    withPluginClasspath: Boolean,
    withHermeticTestKit: Boolean,
    stacktrace: Boolean,
    shouldFail: Boolean
  ): BuildResult {
    val withOptions = gradleRunner
      .letIf(withPluginClasspath) { it.withPluginClasspath() }
      .letIf(withHermeticTestKit) { it.withTestKitDir(workingDir / "testKit") }
      .withArguments(tasks.letIf(stacktrace) { it.plus("--stacktrace") })

    return if (shouldFail) {
      withOptions.buildAndFail()
    } else {
      withOptions.build()
        .also { result ->
          result.tasks
            .forAll { buildTask ->
              buildTask.outcome shouldNotBe FAILED
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
