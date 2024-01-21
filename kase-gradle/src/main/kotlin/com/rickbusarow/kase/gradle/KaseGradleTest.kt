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

import com.rickbusarow.kase.HasKaseMatrix
import com.rickbusarow.kase.HasTestEnvironmentFactory
import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.ParamTestEnvironmentFactory
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
public interface KaseGradleTest<PARAM, ENV, FACT> :
  HasTestEnvironmentFactory<FACT>,
  KaseTestFactory<PARAM, ENV, FACT>,
  HasKaseMatrix
  where PARAM : HasGradleDependencyVersion,
        PARAM : Kase,
        ENV : GradleTestEnvironment,
        FACT : GradleTestEnvironmentFactory<PARAM, ENV>

/**
 * A factory for creating [GradleTestEnvironment]s.
 *
 * @since 0.1.0
 */
public interface GradleTestEnvironmentFactory<PARAM, ENV> : ParamTestEnvironmentFactory<PARAM, ENV>
  where PARAM : HasGradleDependencyVersion,
        PARAM : Kase,
        ENV : GradleTestEnvironment {

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
  public fun buildFileDefault(versions: PARAM): DslStringFactory = DslStringFactory { "" }

  /**
   * Defines the default contents of the root project's `settings.gradle(.kts)` file.
   *
   * @since 0.1.0
   */
  public fun settingsFileDefault(versions: PARAM): DslStringFactory = DslStringFactory { language ->

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
}
