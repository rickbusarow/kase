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

import org.junit.jupiter.api.Test

class RepositoryHandlerBuilderTest {

  @Test
  fun `canary thing`() {

    val builder = SettingsFileBuilder {

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
      }
    }

    val content = builder.write(DslLanguage.Groovy(alwaysUseDoubleQuotes = false, useInfix = true))

    println(content)
  }
}
