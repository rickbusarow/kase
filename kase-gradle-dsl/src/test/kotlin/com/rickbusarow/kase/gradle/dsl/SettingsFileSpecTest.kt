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

import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.NoInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.NoLabels
import com.rickbusarow.kase.kase
import com.rickbusarow.kase.kases
import com.rickbusarow.kase.times
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

class SettingsFileSpecTest {

  @TestFactory
  fun `empty lambdas`(): Stream<out DynamicNode> {
    return listOf<Kase2<SettingsFileBuilderAction, String>>(
      kase({ pluginManagement { } }, "pluginManagement {\n}"),
      kase({ plugins { } }, "plugins {\n}"),
      kase({ dependencyResolutionManagement { } }, "dependencyResolutionManagement {\n}")
    )
      .times(kases(dslLanguages))
      .asTests { (builderFun, expected, language) ->

        val builder = SettingsFileSpec {
          builderFun(this)
        }

        val content = builder.write(language)

        content shouldBe expected
      }
  }

  @Test
  fun `canary thing`() {

    val builder = SettingsFileSpec {

      "rootProject.name".set("kase-gradle")
      "rootProject.name" setEquals stringLiteral("kase-gradle")

      pluginManagement {
        repositories {
          mavenCentral()
          google()
          gradlePluginPortal()
          maven(stringLiteral("https://plugins.gradle.org/m2/"))
          mavenLocal()

          maven(stringLiteral("https://jitpack.io")) {

            content {
              excludeGroup("com.android.tools.external.com-intellij")
            }
          }
        }
        plugins {
          id("com.rickbusarow.kase")
          alias("libs.plugins.doks")
        }

        includeBuild(stringLiteral("aa"))
      }

      addBlankLine()

      plugins {
        id("com.rickbusarow.kase")
        alias("libs.plugins.doks")
      }

      addBlankLine()

      include(":project0")

      addBlankLine()

      include(":project1", ":project2", ":more-projects:project3")

      addBlankLine()

      dependencyResolutionManagement {

        defaultLibrariesExtensionName.set(stringLiteral("lobs"))
        defaultProjectsExtensionName.set(defaultLibrariesExtensionName)

        rulesMode.set(repositoriesMode.get())

        rulesMode.set(repositoriesMode.map { functionCall("foo", NoLabels, NoInfix) })

        versionCatalogs {}

        repositories {
          mavenCentral()
          google()
          gradlePluginPortal()
          maven(stringLiteral("https://plugins.gradle.org/m2/"))
          mavenLocal()

          maven(stringLiteral("https://jitpack.io")) {

            content {
              excludeGroup("com.android.tools.external.com-intellij")
            }
          }
        }
      }
    }

    val content = builder.write(
      DslLanguage.KotlinDsl(useInfix = false, useLabels = false)
      // DslLanguage.GroovyDsl(useDoubleQuotes = false, useInfix = true)
    )

    println(content)
  }
}
