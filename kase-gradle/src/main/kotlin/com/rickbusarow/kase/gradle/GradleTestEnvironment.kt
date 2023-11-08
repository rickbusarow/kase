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
import com.rickbusarow.kase.DefaultTestEnvironment
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.files.DirectoryBuilder
import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.files.WritesFiles
import com.rickbusarow.kase.files.buildDirectory
import com.rickbusarow.kase.files.relativePath
import com.rickbusarow.kase.gradle.generation.BuildFileComponents
import com.rickbusarow.kase.gradle.generation.dsl.BuildFileSpec
import com.rickbusarow.kase.gradle.generation.dsl.SettingsFileSpec
import com.rickbusarow.kase.gradle.generation.model.DslElement
import com.rickbusarow.kase.gradle.generation.model.DslLanguage
import com.rickbusarow.kase.gradle.generation.model.HasDslLanguage
import com.rickbusarow.kase.stdlib.createSafely
import org.gradle.testkit.runner.GradleRunner
import java.io.File

/**
 * A [TestEnvironment] which provides a [GradleRunner]
 * and a [File] representing the root project directory.
 *
 * @param hasWorkingDir the [HasWorkingDir] for this test environment
 * @property testVersions the [TestVersions] for this test environment
 * @param buildFileComponents the [BuildFileComponents] for this test environment
 * @property dslLanguage the [DslLanguage] for this test environment
 * @param localM2Path the local maven repository path
 */
@Suppress("PropertyName", "VariableNaming", "MemberVisibilityCanBePrivate", "MagicNumber")
public class GradleTestEnvironment<TV> public constructor(
  hasWorkingDir: HasWorkingDir,
  override val testVersions: TV,
  buildFileComponents: BuildFileComponents,
  override val dslLanguage: DslLanguage,
  localM2Path: File
) : DefaultTestEnvironment(hasWorkingDir),
  TestVersions by testVersions,
  HasGradleRunner by DefaultHasGradleRunner(
    hasWorkingDir = hasWorkingDir,
    gradleVersion = { testVersions.gradleVersion }
  ),
  HasTestVersions<TV>,
  WritesFiles,
  HasDslLanguage
  where TV : TestVersions,
        TV : AnyKase {

  /**
   * @param testVersions the [TestVersions] for this test environment
   * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run
   * @param kase the variant names related to the test
   * @param buildFileComponents the [BuildFileComponents] for this test environment
   * @param dslLanguage the [DslLanguage] for this test environment
   * @param localM2Path the local maven repository path
   */
  public constructor (
    testVersions: TV,
    testFunctionCoordinates: TestFunctionCoordinates,
    kase: TV,
    buildFileComponents: BuildFileComponents,
    dslLanguage: DslLanguage,
    localM2Path: File
  ) : this(
    hasWorkingDir = HasWorkingDir(kase.displayNames, testFunctionCoordinates),
    testVersions = testVersions,
    buildFileComponents = buildFileComponents,
    dslLanguage = dslLanguage,
    localM2Path = localM2Path
  )

  /** The [File] representing the root project directory. */
  public val root: File get() = workingDir

  /** The [File] representing the root project directory. */
  public inline fun root(action: DirectoryBuilder.() -> Unit): File {
    return buildDirectory(workingDir, action)
  }

  /** The default build.gradle file. */
  public val DEFAULT_BUILD_FILE: String by lazy {
    BuildFileSpec {
      buildscript {
      }

      plugins {
        kotlin("jvm", KotlinVersion.CURRENT.toString(), apply = false)
      }
    }.write(dslLanguage)
  }

  /** The default build.gradle file. */
  public val rootBuild: File = root.resolve(dslLanguage.buildFileName)
    .createSafely(DEFAULT_BUILD_FILE, overwrite = false)

  /** Applies [builder] to the root `build.gradle[.kts]` file. */
  public fun rootBuild(
    dslLanguage: DslLanguage = this.dslLanguage,
    builder: BuildFileSpec.() -> Unit
  ): File = rootBuild.fromDslElement(dslLanguage, BuildFileSpec(builder))

  /** The default settings.gradle file. */
  public val DEFAULT_SETTINGS_FILE: String by lazy {
    SettingsFileSpec {

      pluginManagement {
        repositories {
          maven(localM2Path)
          gradlePluginPortal()
          mavenCentral()
          google()
        }
      }
      dependencyResolutionManagement {
        repositories {
          maven(localM2Path)
          mavenCentral()
          google()
        }
      }

      rootProjectName.setEquals("root")
    }.write(dslLanguage)
  }

  /** The root settings.gradle file. */
  public val rootSettings: File = root.resolve(dslLanguage.settingsFileName)
    .createSafely(DEFAULT_SETTINGS_FILE)

  /** Applies [builder] to the root `settings.gradle[.kts]` file. */
  public fun rootSettings(
    dslLanguage: DslLanguage = this.dslLanguage,
    builder: SettingsFileSpec.() -> Unit
  ): File = rootSettings.fromDslElement(dslLanguage, SettingsFileSpec(builder))

  public fun DirectoryBuilder.buildFile(
    dslLanguage: DslLanguage = this@GradleTestEnvironment.dslLanguage,
    builder: BuildFileSpec.() -> Unit
  ): DirectoryBuilder = file(
    nameWithExtension = dslLanguage.buildFileName,
    content = BuildFileSpec(builder).write(dslLanguage)
  )

  public fun DirectoryBuilder.settingsFile(
    dslLanguage: DslLanguage = this@GradleTestEnvironment.dslLanguage,
    builder: SettingsFileSpec.() -> Unit
  ): DirectoryBuilder = file(
    nameWithExtension = dslLanguage.settingsFileName,
    content = SettingsFileSpec(builder).write(dslLanguage)
  )

  /** The root project directory. */
  public val rootProject: File by lazy {
    rootBuild
    rootSettings
    root
  }

  @Suppress("UnusedPrivateMember")
  public fun addIncludes() {

    val subprojectDirs = root.walkTopDown()
      .onEnter {
        it == root ||
          (
            !it.resolve("settings.gradle").exists() &&
              !it.resolve("settings.gradle.kts").exists()
            )
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
      .filterNot { it.isBlank() }

    val includes = subprojectPaths
      .joinToString(
        separator = "\n",
        prefix = "\n",
        postfix = "\n"
      ) { "include(\"$it\")" }
    rootSettings.appendText("\n$includes")
  }

  /** Writes the output of [dslElement] to this [File]. */
  public fun File.fromDslElement(
    dslLanguage: DslLanguage,
    dslElement: DslElement
  ): File = createSafely(dslElement.write(dslLanguage))

  /**
   * Writes the output of [dslElement] to this [File]
   * using the [GroovyDsl][DslLanguage.GroovyDsl] language.
   */
  public fun File.groovy(dslElement: DslElement): File = createSafely(dslElement.writeGroovy())

  /**
   * Writes the output of [dslElement] to this [File]
   * using the [GroovyDsl][DslLanguage.GroovyDsl] language.
   */
  public fun File.groovy(
    useInfix: Boolean = true,
    useLabels: Boolean = false,
    useDoubleQuotes: Boolean = false,
    dslElement: DslElement
  ): File = createSafely(
    dslElement.writeGroovy(
      useInfix = useInfix,
      useLabels = useLabels,
      useDoubleQuotes = useDoubleQuotes
    )
  )

  /**
   * Writes the output of [dslElement] to this [File]
   * using the [KotlinDsl][DslLanguage.KotlinDsl] language.
   */
  public fun File.kotlin(dslElement: DslElement): File = createSafely(dslElement.writeKotlin())

  /**
   * Writes the output of [dslElement] to this [File]
   * using the [KotlinDsl][DslLanguage.KotlinDsl] language.
   */
  public fun File.kotlin(
    useInfix: Boolean = true,
    useLabels: Boolean = false,
    dslElement: DslElement
  ): File = createSafely(
    dslElement.writeKotlin(useInfix = useInfix, useLabels = useLabels)
  )
}
