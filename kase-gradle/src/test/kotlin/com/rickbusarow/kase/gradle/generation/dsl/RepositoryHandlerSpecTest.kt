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

package com.rickbusarow.kase.gradle.generation.dsl

import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.gradle.generation.RepositoryHandlerBuilderAction
import com.rickbusarow.kase.gradle.generation.dslLanguages
import com.rickbusarow.kase.gradle.generation.expectedFunctionCall
import com.rickbusarow.kase.kase
import com.rickbusarow.kase.kases
import com.rickbusarow.kase.times
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

class RepositoryHandlerSpecTest {

  @TestFactory
  fun `common repositories without configuration`(): Stream<out DynamicNode> {
    return listOf<Kase2<RepositoryHandlerBuilderAction, String>>(
      // kase({ maven(string("com.apple")) }, """maven("com.apple")"""),
      kase({ mavenCentral() }, """mavenCentral()"""),
      kase({ google() }, """google()"""),
      kase({ gradlePluginPortal() }, """gradlePluginPortal()"""),
      kase({ mavenLocal() }, """mavenLocal()""")
    )
      .times(kases(dslLanguages))
      .asTests { builderFun, expected, language ->

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
  }

  @TestFactory
  fun `custom maven repository without configuration`(): Stream<out DynamicNode> =
    kases(dslLanguages).asTests { language ->

      val builder = SettingsFileSpec {
        pluginManagement {
          repositories {
            maven("com.apple".asStringLiteral())
          }
        }
      }

      val content = builder.write(language)

      val expectedFunctionCall = expectedFunctionCall(
        functionName = "maven",
        labelName = "url",
        valueString = "com.apple",
        language = language
      )

      content shouldBe """
        |pluginManagement {
        |  repositories {
        |    $expectedFunctionCall
        |  }
        |}
      """.trimMargin()
    }

  @TestFactory
  fun `custom maven repository with configuration`(): Stream<out DynamicNode> =
    kases(dslLanguages).asTests { language ->

      val builder = SettingsFileSpec {
        pluginManagement {
          repositories {
            maven("com.apple".asStringLiteral()) {
              mavenContent {
                releasesOnly()
              }
            }
          }
        }
      }

      val content = builder.write(language)

      val expectedFunctionCall = expectedFunctionCall(
        functionName = "maven",
        labelName = "url",
        valueString = "com.apple",
        language = language
      )

      content shouldBe """
        |pluginManagement {
        |  repositories {
        |    $expectedFunctionCall {
        |      mavenContent {
        |        releasesOnly()
        |      }
        |    }
        |  }
        |}
      """.trimMargin()
    }
}
