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
import com.rickbusarow.kase.files.DirectoryBuilder.FileWithContent
import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.files.buildDirectory
import com.rickbusarow.kase.files.directoryBuilder
import com.rickbusarow.kase.files.relativePath
import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.internal.DslStringFactory
import org.gradle.testkit.runner.GradleRunner
import java.io.File

/**
 * A [TestEnvironment] which provides a [GradleRunner]
 * and a [File] representing the root project directory.
 *
 * @param hasWorkingDir the [HasWorkingDir] for this test environment
 * @param rootDirectoryBuilder the [DirectoryBuilder] for this test environment
 * @property testVersions the [TestVersions] for this test environment
 * @property dslLanguage the [DslLanguage] for this
 *   test environment (default: [DslLanguage.KotlinDsl])
 * @param defaultBuildFile the default [DslStringFactory] for the root `build.gradle[.kts]` file
 * @param defaultSettingsFile the default [DslStringFactory]
 *   for the root `settings.gradle[.kts]` file
 */
@Suppress("VariableNaming", "MemberVisibilityCanBePrivate", "MagicNumber")
public class GradleTestEnvironment<K> private constructor(
  hasWorkingDir: HasWorkingDir,
  rootDirectoryBuilder: DirectoryBuilder,
  override val testVersions: K,
  override val dslLanguage: DslLanguage,
  defaultBuildFile: DslStringFactory,
  defaultSettingsFile: DslStringFactory
) : DefaultTestEnvironment(hasWorkingDir),
  TestVersions by testVersions,
  DirectoryBuilder by rootDirectoryBuilder,
  HasGradleRunner by DefaultHasGradleRunner(
    hasWorkingDir = hasWorkingDir,
    rootDirectoryBuilder = rootDirectoryBuilder,
    gradleVersion = { testVersions.gradleVersion }
  ),
  HasTestVersions<K>,
  HasDslLanguage
  where K : TestVersions,
        K : AnyKase {

  public constructor(
    hasWorkingDir: HasWorkingDir,
    testVersions: K,
    dslLanguage: DslLanguage,
    localM2Path: File? = null,
    defaultBuildFile: DslStringFactory = buildFileDefault,
    defaultSettingsFile: DslStringFactory = getSettingsFileDefault(localM2Path)
  ) : this(
    hasWorkingDir = hasWorkingDir,
    rootDirectoryBuilder = hasWorkingDir.workingDir.directoryBuilder(),
    testVersions = testVersions,
    dslLanguage = dslLanguage,
    defaultBuildFile = defaultBuildFile,
    defaultSettingsFile = defaultSettingsFile
  )

  /**
   * @param testVersions the [TestVersions] for this test environment
   * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run
   * @param kase the variant names related to the test
   * @param dslLanguage the [DslLanguage] for this test environment
   * @param localM2Path the local maven repository path
   */
  public constructor(
    testVersions: K,
    testFunctionCoordinates: TestFunctionCoordinates,
    kase: K,
    dslLanguage: DslLanguage,
    localM2Path: File? = null
  ) : this(
    hasWorkingDir = HasWorkingDir(kase.displayNames, testFunctionCoordinates),
    testVersions = testVersions,
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
  public val rootBuild: FileWithContent = file(
    relativeName = dslLanguage.buildFileName,
    content = defaultBuildFile.write(dslLanguage)
  )

  // /** Applies [builder] to the root `build.gradle[.kts]` file. */
  // public fun rootBuild(
  //   dslLanguage: DslLanguage = this.dslLanguage,
  //   builder: BuildFileSpec.() -> Unit
  // ): FileWithContent = file(dslLanguage.buildFileName, BuildFileSpec(builder).write(dslLanguage))

  /** The root settings.gradle file. */
  public val rootSettings: FileWithContent = file(
    relativeName = dslLanguage.settingsFileName,
    content = defaultSettingsFile.write(dslLanguage)
  )

  // /** Applies [builder] to the root `settings.gradle[.kts]` file. */
  // public fun rootSettings(
  //   dslLanguage: DslLanguage = this.dslLanguage,
  //   builder: SettingsFileSpec.() -> Unit
  // ): FileWithContent = file(dslLanguage.buildFileName, SettingsFileSpec(builder).write(dslLanguage))

  // /** The root project directory. */
  // public val rootProject: File by lazy {
  //   rootBuild
  //   rootSettings
  //   root
  // }

  /**
   * Walks the root project directory and adds `include("path")`
   * statements to the root settings.gradle file for every subproject.
   */
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
    rootSettings.content += "\n$includes"
  }

  internal companion object {

    private val buildFileDefault = DslStringFactory { language ->
      when (language) {
        is GroovyDsl -> """
          plugins {
            id 'org.jetbrains.kotlin.jvm' version '${KotlinVersion.CURRENT}'
          }
        """.trimIndent()

        is KotlinDsl -> """
          plugins {
            kotlin("jvm") version "${KotlinVersion.CURRENT}"
          }
        """.trimIndent()
      }
    }

    private fun getSettingsFileDefault(localM2Path: File?): DslStringFactory =
      DslStringFactory { language ->

        val maybeLocal = if (localM2Path == null) {
          ""
        } else {
          when (language) {
            is GroovyDsl -> """maven { url files("$localM2Path") }\n"""
            is KotlinDsl -> """maven(files($localM2Path))\n"""
          }
        }

        """
        pluginManagement {
          repositories {
            $maybeLocal
            gradlePluginPortal()
            mavenCentral()
            google()
          }
        }
        dependencyResolutionManagement {
          repositories {
            $maybeLocal
            mavenCentral()
            google()
          }
        }

        rootProject.name = "root"
        """.trimIndent()
      }
  }
}
