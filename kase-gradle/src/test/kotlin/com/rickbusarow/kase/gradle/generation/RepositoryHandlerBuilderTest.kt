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

package com.rickbusarow.kase.gradle.generation

import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.gradle.generation.internal.DslLanguage.Groovy
import com.rickbusarow.kase.gradle.generation.internal.FunctionCall.LabelSupport.NONE
import com.rickbusarow.kase.kase
import com.rickbusarow.kase.kases
import com.rickbusarow.kase.times
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

class RepositoryHandlerBuilderTest {

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

        val builder = SettingsFileBuilder {
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

  @Test
  fun `canary thing`() {

    val builder = SettingsFileBuilder {

      "rootProject.name".set("kase-gradle")
      "rootProject.name" setEquals string("kase-gradle")

      pluginManagement {
        repositories {
          mavenCentral()
          google()
          gradlePluginPortal()
          maven(quoted("https://plugins.gradle.org/m2/"))
          mavenLocal()

          maven(quoted("https://jitpack.io")) {

            content {
              excludeGroup("com.android.tools.external.com-intellij")
            }
          }
        }
        plugins {
          id("com.rickbusarow.kase")
          alias("libs.plugins.doks")
        }

        includeBuild(string("aa"))
      }

      addBlankLine()

      plugins {
        id("com.rickbusarow.kase")
        alias("libs.plugins.doks")
      }

      addBlankLine()

      dependencyResolutionManagement {

        defaultLibrariesExtensionName.set(string("lobs"))
        defaultProjectsExtensionName.set(defaultLibrariesExtensionName)

        rulesMode.set(repositoriesMode.get())

        rulesMode.set(repositoriesMode.map { functionCall("foo", NONE) })

        versionCatalogs {}

        repositories {
          mavenCentral()
          google()
          gradlePluginPortal()
          maven(quoted("https://plugins.gradle.org/m2/"))
          mavenLocal()

          maven(quoted("https://jitpack.io")) {

            content {
              excludeGroup("com.android.tools.external.com-intellij")
            }
          }
        }
      }
    }

    val content = builder.write(Groovy(alwaysUseDoubleQuotes = false, useInfix = true))

    println(content)
  }
}
