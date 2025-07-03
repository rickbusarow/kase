/*
 * Copyright (C) 2025 Rick Busarow
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
import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.ParamTestEnvironmentFactory
import com.rickbusarow.kase.files.TestLocation
import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.kases
import com.rickbusarow.kase.stdlib.cartesianProduct
import com.rickbusarow.kase.stdlib.div
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import org.junit.jupiter.api.TestFactory
import java.io.File

class GradleTestEnvironmentFactoryTest : KaseTestFactory<Kase1<DslLanguage>, DslTestEnvironment, DslTestEnvironment.Factory> {

  override val testEnvironmentFactory: DslTestEnvironment.Factory
    get() = DslTestEnvironment.Factory()

  override val params: List<Kase1<DslLanguage>>
    get() = listOf(listOf(true, false), listOf(true, false))
      .cartesianProduct()
      .flatMap { (useInfix, useLabels) ->
        kases(
          args1 = listOf(
            KotlinDsl(useInfix = useInfix, useLabels = useLabels),
            GroovyDsl(useInfix = useInfix, useLabels = useLabels, useDoubleQuotes = true),
            GroovyDsl(useInfix = useInfix, useLabels = useLabels, useDoubleQuotes = false)
          ),
          displayNameFactory = { a1.displayName }
        )
      }

  @TestFactory
  fun `settingsFileDefault local maven statement uses an invariant path`() = testFactory {

    val m2Path = workingDir / "build" / "m2"

    val gradleFactory =
      object : GradleTestEnvironmentFactory<GradleTestVersions, DefaultGradleTestEnvironment> {

        override val localM2Path: File = m2Path

        override fun create(
          params: GradleTestVersions,
          names: List<String>,
          location: TestLocation
        ): DefaultGradleTestEnvironment {
          return DefaultGradleTestEnvironment(
            gradleVersion = params,
            dslLanguage = language,
            hasWorkingDir = this@testFactory,
            defaultBuildFile = buildFileDefault(params),
            defaultSettingsFile = settingsFileDefault(params)
          )
        }
      }

    val settingsFile = gradleFactory
      .settingsFileDefault(GradleTestVersions(GradleDependencyVersion.current()))
      .write(language)

    settingsFile shouldContain m2Path.invariantSeparatorsPath
    settingsFile shouldNotContain "\\"
  }
}

open class DslTestEnvironment(
  val language: DslLanguage,
  names: List<String>,
  testLocation: TestLocation
) : DefaultTestEnvironment(names, testLocation) {

  class Factory : ParamTestEnvironmentFactory<Kase1<DslLanguage>, DslTestEnvironment> {
    override fun create(
      params: Kase1<DslLanguage>,
      names: List<String>,
      location: TestLocation
    ): DslTestEnvironment = DslTestEnvironment(
      language = params.a1,
      names = names,
      testLocation = location
    )
  }
}

val DslLanguage.displayName: String
  get() = toString()
    .replace("Dsl(", " [ ")
    .replace(")", " ] ")
    .replace("=", ": ")
    .replace(", ", " | ")
