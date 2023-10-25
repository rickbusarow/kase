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
import com.rickbusarow.kase.gradle.generation.internal.DslLanguage
import com.rickbusarow.kase.gradle.generation.internal.DslLanguage.Groovy
import com.rickbusarow.kase.gradle.generation.internal.DslLanguage.Kotlin
import com.rickbusarow.kase.gradle.generation.internal.FunctionCall.LabelSupport.NONE
import com.rickbusarow.kase.kase
import com.rickbusarow.kase.kases
import com.rickbusarow.kase.times
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

typealias MavenArtifactRepositoryBuilderAction = MavenArtifactRepositoryBuilder.() -> Unit
typealias PluginManagementSpecBuilderAction = PluginManagementSpecBuilder.() -> Unit
typealias RepositoryHandlerBuilderAction = RepositoryHandlerBuilder.() -> Unit
typealias SettingsFileBuilderAction = SettingsFileBuilder.() -> Unit

val dslLanguages = listOf(
  Groovy(alwaysUseDoubleQuotes = true, useInfix = false),
  Groovy(alwaysUseDoubleQuotes = true, useInfix = true),
  Groovy(alwaysUseDoubleQuotes = false, useInfix = false),
  Groovy(alwaysUseDoubleQuotes = false, useInfix = true),
  Kotlin(useInfix = false),
  Kotlin(useInfix = true)
)

class SettingsFileBuilderTest {

  @TestFactory
  fun `empty lambdas`(): Stream<out DynamicNode> {
    return listOf<Kase2<SettingsFileBuilderAction, String>>(
      kase({ pluginManagement { } }, "pluginManagement {\n}"),
      kase({ plugins { } }, "plugins {\n}"),
      kase({ dependencyResolutionManagement { } }, "dependencyResolutionManagement {\n}")
    )
      .times(kases(dslLanguages))
      .asTests { builderFun, expected, language ->

        val builder = SettingsFileBuilder {
          builderFun(this)
        }

        val content = builder.write(language)

        content shouldBe expected
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

    val content = builder.write(DslLanguage.Groovy(alwaysUseDoubleQuotes = false, useInfix = true))

    println(content)
  }
}
