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

import com.rickbusarow.kase.DefaultTestEnvironment
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.files.HasWorkingDir
import org.gradle.testkit.runner.GradleRunner
import java.io.File

/**
 * A [TestEnvironment] which provides a [GradleRunner]
 * and a [File] representing the root project directory.
 *
 * @param gradleVersion the Gradle version used in this environment's runner
 * @property dslLanguage the [DslLanguage] for this test environment
 * @property rootProject the [GradleRootProjectBuilder] for this test environment
 * @param hasWorkingDir the [HasWorkingDir] for this test environment
 * @since 0.1.0
 */
@Suppress("VariableNaming", "MemberVisibilityCanBePrivate", "MagicNumber")
public class GradleTestEnvironment private constructor(
  gradleVersion: GradleDependencyVersion,
  override val dslLanguage: DslLanguage,
  public val rootProject: GradleRootProjectBuilder,
  private val hasWorkingDir: HasWorkingDir
) : DefaultTestEnvironment(hasWorkingDir),
  HasGradleRunner by DefaultHasGradleRunner(
    hasWorkingDir = hasWorkingDir,
    gradleVersion = { gradleVersion }
  ),
  HasDslLanguage {

  override val workingDir: File
    get() = hasWorkingDir.workingDir

  /**
   * @param gradleVersion the Gradle version used in this environment's runner
   * @param dslLanguage the [DslLanguage] for this test environment
   * @param hasWorkingDir the [HasWorkingDir] for this test environment
   * @param defaultBuildFile the default [DslStringFactory] for the root `build.gradle[.kts]` file
   * @param defaultSettingsFile the default [DslStringFactory]
   *   for the root `settings.gradle[.kts]` file
   * @since 0.1.0
   */
  public constructor(
    gradleVersion: GradleDependencyVersion,
    dslLanguage: DslLanguage,
    hasWorkingDir: HasWorkingDir,
    defaultBuildFile: DslStringFactory,
    defaultSettingsFile: DslStringFactory
  ) : this(
    gradleVersion = gradleVersion,
    dslLanguage = dslLanguage,
    hasWorkingDir = hasWorkingDir,
    rootProject = rootProject(
      path = hasWorkingDir.workingDir,
      dslLanguage = dslLanguage
    ) {
      buildFile(defaultBuildFile)
      settingsFile(defaultSettingsFile)
    }
  )

  /**
   * @param testVersions the [TestVersions] for this test environment
   * @param dslLanguage the [DslLanguage] for this test environment
   * @param hasWorkingDir the [HasWorkingDir] for this test environment
   * @param defaultBuildFile the default [DslStringFactory] for the root `build.gradle[.kts]` file
   * @param defaultSettingsFile the default [DslStringFactory]
   *   for the root `settings.gradle[.kts]` file
   * @since 0.1.0
   */
  public constructor(
    testVersions: TestVersions,
    dslLanguage: DslLanguage,
    hasWorkingDir: HasWorkingDir,
    defaultBuildFile: DslStringFactory,
    defaultSettingsFile: DslStringFactory
  ) : this(
    gradleVersion = testVersions.gradle,
    dslLanguage = dslLanguage,
    hasWorkingDir = hasWorkingDir,
    rootProject = rootProject(
      path = hasWorkingDir.workingDir,
      dslLanguage = dslLanguage
    ) {
      buildFile(defaultBuildFile)
      settingsFile(defaultSettingsFile)
    }
  )

  /**
   * DSL for configuring the root project or any subprojects.
   *
   * ```
   * rootProject {
   *   buildFile.groovy(
   *     """
   *     plugins {
   *       id 'com.acme.dynamite' version '1.0.0'
   *     }
   *     """.trimIndent()
   *   )
   *   settingsFile.groovy(
   *     """
   *     pluginsManagement {
   *       repositories {
   *         maven { url = uri('https://repo.acme.com/m2') }
   *       }
   *     }
   *     """.trimIndent()
   *   )
   * }
   * ```
   *
   * @since 0.1.0
   */
  public inline fun rootProject(action: GradleRootProjectBuilder.() -> Unit): File {
    return rootProject.apply(action).path
  }

  override fun String.cleanOutput(): String = with(hasWorkingDir) { cleanOutput() }
}
