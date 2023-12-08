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

import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.TestEnvironmentFactory
import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import java.io.File

/**
 * A base class for Gradle plugin tests.
 *
 * @since 0.1.0
 */
@Execution(ExecutionMode.SAME_THREAD)
public interface BaseGradleTest<K : TestVersions> :
  GradleTestEnvironmentFactory<K>,
  HasVersionMatrix,
  KaseTestFactory<GradleTestEnvironment<K>, K>

/**
 * A factory for creating [GradleTestEnvironment]s.
 *
 * @since 0.1.0
 */
public interface GradleTestEnvironmentFactory<K : TestVersions> :
  TestEnvironmentFactory<GradleTestEnvironment<K>, K> {

  /**
   * The [DslLanguage] to use for generating build and settings files.
   *
   * @since 0.1.0
   */
  public val dslLanguage: DslLanguage
    get() = KotlinDsl(useInfix = true, useLabels = false)

  /**
   * A local Maven repository to use for resolving
   * dependencies, such as `<projectRoot>/build/m2` or `~/.m2`.
   *
   * @since 0.1.0
   */
  public val localM2Path: File? get() = null

  /**
   * Defines the default contents of the root project's `build.gradle(.kts)` file.
   *
   * @since 0.1.0
   */
  public fun buildFileDefault(versions: K): DslStringFactory = DslStringFactory { "" }

  /**
   * Defines the default contents of the root project's `settings.gradle(.kts)` file.
   *
   * @since 0.1.0
   */
  public fun settingsFileDefault(versions: K): DslStringFactory = DslStringFactory { language ->

    val maybeLocal = if (localM2Path == null) {
      ""
    } else {
      when (language) {
        is GroovyDsl -> """maven { url "$localM2Path" }"""
        is KotlinDsl -> """maven("$localM2Path")"""
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

  override fun newTestEnvironment(
    kase: K,
    testFunctionCoordinates: TestFunctionCoordinates
  ): GradleTestEnvironment<K> = GradleTestEnvironment(
    testVersions = kase,
    dslLanguage = this.dslLanguage,
    hasWorkingDir = HasWorkingDir(listOf(kase.displayName), testFunctionCoordinates),
    defaultBuildFile = buildFileDefault(kase),
    defaultSettingsFile = settingsFileDefault(kase)
  )
}
