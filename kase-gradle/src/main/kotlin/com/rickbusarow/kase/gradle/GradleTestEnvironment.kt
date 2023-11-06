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
import com.rickbusarow.kase.TestEnvironmentFactory
import com.rickbusarow.kase.TestVariant
import com.rickbusarow.kase.files.DirectoryBuilder
import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.files.WritesFiles
import com.rickbusarow.kase.files.buildDirectory
import com.rickbusarow.kase.files.relativePath
import com.rickbusarow.kase.gradle.generation.BuildFileComponents
import com.rickbusarow.kase.gradle.generation.model.DslLanguage
import com.rickbusarow.kase.stdlib.createSafely
import com.rickbusarow.kase.stdlib.replaceIndent
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD
import java.io.File

/** A base class for Gradle plugin tests. */
@Execution(SAME_THREAD)
public interface BaseGradleTest<E : GradleTestEnvironment<V>, V> :
  GradleTestEnvironmentFactory<V>,
  HasVersionMatrix
  where V : TestVersions,
        V : AnyKase

public interface KaseTestFactory<T : TestEnvironment, K : AnyKase> :
  HasVersionMatrix,
  TestEnvironmentFactory<T, K>

/** A factory for creating [GradleTestEnvironment]s. */
public interface GradleTestEnvironmentFactory<T> :
  TestEnvironmentFactory<GradleTestEnvironment<T>, T>
  where T : TestVersions,
        T : AnyKase {

  /** Creates a new [GradleTestEnvironment] for the given [testVariant] and [testVersions]. */
  public fun newTestEnvironment(
    testVariant: TestVariant<T>,
    testVersions: T
  ): GradleTestEnvironment<T> {
    return GradleTestEnvironment(
      testVersions = testVersions,
      testFunctionCoordinates = testVariant.testFunctionCoordinates,
      kase = testVariant.kase,
      buildFileComponents = object : BuildFileComponents {},
      DslLanguage.GroovyDsl(useInfix = true, useLabels = true, useDoubleQuotes = false)
    )
  }

  override fun newTestEnvironment(
    kase: T,
    testFunctionCoordinates: TestFunctionCoordinates
  ): GradleTestEnvironment<T> {
    return GradleTestEnvironment(
      testVersions = kase,
      testFunctionCoordinates = testFunctionCoordinates,
      kase = kase,
      buildFileComponents = object : BuildFileComponents {},
      DslLanguage.GroovyDsl(useInfix = true, useLabels = true, useDoubleQuotes = false)
    )
  }

  override fun newTestEnvironment(params: TestVariant<out AnyKase>): GradleTestEnvironment<T> {
    return super.newTestEnvironment(params)
  }
}

/**
 * A [TestEnvironment] which provides a [GradleRunner]
 * and a [File] representing the root project directory.
 *
 * @param hasWorkingDir the [HasWorkingDir] for this test environment
 * @property testVersions the [TestVersions] for this test environment
 * @param buildFileComponents the [BuildFileComponents] for this test environment
 * @param dslLanguage the [DslLanguage] for this test environment
 */
@Suppress("PropertyName", "VariableNaming", "MemberVisibilityCanBePrivate", "MagicNumber")
public class GradleTestEnvironment<T> public constructor(
  hasWorkingDir: HasWorkingDir,
  override val testVersions: T,
  buildFileComponents: BuildFileComponents,
  dslLanguage: DslLanguage
) : DefaultTestEnvironment(hasWorkingDir),
  TestVersions by testVersions,
  HasGradleRunner by DefaultHasGradleRunner(
    hasWorkingDir = hasWorkingDir,
    gradleVersion = { testVersions.gradleVersion }
  ),
  HasTestVersions<T>,
  WritesFiles
  where T : TestVersions,
        T : AnyKase {

  /**
   * @param testVersions the [TestVersions] for this test environment
   * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run
   * @param kase the variant names related to the test
   * @param buildFileComponents the [BuildFileComponents] for this test environment
   * @param dslLanguage the [DslLanguage] for this test environment
   */
  public constructor (
    testVersions: T,
    testFunctionCoordinates: TestFunctionCoordinates,
    kase: T,
    buildFileComponents: BuildFileComponents,
    dslLanguage: DslLanguage
  ) : this(
    HasWorkingDir(kase.displayNames, testFunctionCoordinates),
    testVersions,
    buildFileComponents,
    dslLanguage
  )

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

  private fun writeFilesToEnvironment() {
    rootBuild
    rootSettings
    workingDir.walkTopDown()
      .filter { it.isFile }
      .forEach { println("file://$it") }
  }
}
