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

package com.rickbusarow.kase.gradle.dsl

import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.dsl.model.StringLiteral
import com.rickbusarow.kase.gradle.dsl.model.ValueAssignment
import com.rickbusarow.kase.kase
import com.rickbusarow.kase.kases
import com.rickbusarow.kase.times
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestFactory

class RepositoryHandlerSpecTest {

  @TestFactory
  fun `common repositories without configuration`() =
    listOf<Kase2<RepositoryHandlerBuilderAction, String>>(
      kase({ mavenCentral() }, """mavenCentral()"""),
      kase({ google() }, """google()"""),
      kase({ gradlePluginPortal() }, """gradlePluginPortal()"""),
      kase({ mavenLocal() }, """mavenLocal()""")
    )
      .timesLanguages()
      .asTests { (builderFun, expected, language) ->

        val builder = SettingsFileSpec {
          pluginManagement {
            repositories {
              builderFun(this)
            }
          }
        }

        val content = builder.write(language)

        content shouldBe """
          |pluginManagement {
          |  repositories {
          |    $expected
          |  }
          |}
        """.trimMargin()
      }

  @TestFactory
  fun `custom maven repository without configuration`() =
    listOf<Kase2<String, RepositoryHandlerSpec.(StringLiteral) -> Unit>>(
      kase("maven", "maven") { literal -> maven(literal) },
      kase("mavenLocal", "mavenLocal") { literal -> mavenLocal(literal) }
    )
      .timesLanguages()
      .asTests { (mavenName, mavenFun, language) ->

        val builder = SettingsFileSpec {
          pluginManagement {
            repositories {
              mavenFun("com.apple".asStringLiteral())
            }
          }
        }

        val content = builder.write(language)

        val generator = ExpectedCodeGenerator(
          language = language,
          kotlinInfix = false,
          kotlinLabels = true
        )
        val expectedMavenCall = when (language) {
          is GroovyDsl -> mavenName
          is KotlinDsl -> generator.createFunction(
            functionName = mavenName,
            labelName = "url",
            valueString = language.quote("com.apple")
          )
        }
        val urlGenerator = generator.copy(
          kotlinLabels = false,
          groovyLabels = false
        )
        val expectedUrlSetter = when {
          language is GroovyDsl && !language.useInfix -> ValueAssignment.SetterAssignment(
            name = "url",
            dslStringFactory = StringLiteral(
              value = "com.apple",
              useDoubleQuotes = language.useDoubleQuotes
            )
          ).write(language)

          language is GroovyDsl -> urlGenerator.createFunction(
            functionName = "url",
            labelName = "url",
            valueString = language.quote("com.apple")
          )

          else -> ""
        }

        val expected = when (language) {
          is KotlinDsl -> """
            |pluginManagement {
            |  repositories {
            |    $expectedMavenCall
            |  }
            |}
          """.trimMargin()

          is GroovyDsl -> """
            |pluginManagement {
            |  repositories {
            |    $expectedMavenCall {
            |      $expectedUrlSetter
            |    }
            |  }
            |}
          """.trimMargin()
        }

        content shouldBe expected
      }

  @TestFactory
  fun `custom maven repository with configuration`() =
    listOf<
      Kase2<
        String,
        RepositoryHandlerSpec.(StringLiteral, MavenArtifactRepositorySpec.() -> Unit) -> Unit
        >
      >(
      kase("maven", "maven") { literal, cfg -> maven(literal, cfg) },
      kase("mavenLocal", "mavenLocal") { literal, cfg -> mavenLocal(literal, cfg) }
    )
      .timesLanguages()
      .asTests { (mavenName, mavenFun, language) ->

        val builder = SettingsFileSpec {
          pluginManagement {
            repositories {
              mavenFun("com.apple".asStringLiteral()) {
                mavenContent {
                  releasesOnly()
                }
              }
            }
          }
        }

        val content = builder.write(language)

        val generator = ExpectedCodeGenerator(
          language = language,
          kotlinInfix = false,
          kotlinLabels = true
        )
        val expectedMavenCall = when (language) {
          is GroovyDsl -> mavenName
          is KotlinDsl -> generator.createFunction(
            functionName = mavenName,
            labelName = "url",
            valueString = language.quote("com.apple")
          )
        }
        val urlGenerator = generator.copy(
          kotlinLabels = false,
          groovyLabels = false
        )
        val expectedUrlSetter = when {
          language is GroovyDsl && !language.useInfix -> ValueAssignment.SetterAssignment(
            name = "url",
            dslStringFactory = StringLiteral(
              value = "com.apple",
              useDoubleQuotes = language.useDoubleQuotes
            )
          ).write(language)

          language is GroovyDsl -> urlGenerator.createFunction(
            functionName = "url",
            labelName = "url",
            valueString = language.quote("com.apple")
          )

          else -> ""
        }

        val expected = when (language) {
          is KotlinDsl -> """
           |pluginManagement {
           |  repositories {
           |    $expectedMavenCall {
           |      mavenContent {
           |        releasesOnly()
           |      }
           |    }
           |  }
           |}
          """.trimMargin()

          is GroovyDsl -> """
            |pluginManagement {
            |  repositories {
            |    $expectedMavenCall {
            |      $expectedUrlSetter
            |      mavenContent {
            |        releasesOnly()
            |      }
            |    }
            |  }
            |}
          """.trimMargin()
        }

        content shouldBe expected
      }

  val mavenFuns = listOf<Kase1<RepositoryHandlerSpec.(StringLiteral) -> Unit>>(
    kase("maven") { literal -> maven(literal) },
    kase("mavenLocal") { literal -> mavenLocal(literal) }
  )

  fun <A, B> List<Kase2<A, B>>.timesLanguages() =
    times(kases(dslLanguages)) { (a1, a2), (b1) -> kase(a1, a2, b1) }
}
