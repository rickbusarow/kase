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

package com.rickbusarow.kase.gradle.dsl

import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.gradle.DslLanguage
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory

class AndroidExtensionSpecTest : DslKaseTestFactory<DslTestEnvironment, Kase1<DslLanguage>> {

  @TestFactory
  fun `an empty android lambda block is written`() = testFactory {

    val builder = BuildFileSpec {
      android {
      }
    }

    val content = builder.write(language)

    content shouldBe "android {\n}"
  }

  @TestFactory
  fun `compileSdk is written correctly`() = testFactory {

    val builder = BuildFileSpec {
      android {
        compileSdk.setEquals(47)
      }
    }

    val content = builder.write(language)

    content shouldBe """
      android {
        compileSdk = 47
      }
    """.trimIndent()
  }

  @TestFactory
  fun `namespace is written correctly`() = testFactory {

    val builder = BuildFileSpec {
      android {
        namespace.setEquals("com.acme.dynamite")
      }
    }

    val content = builder.write(language)

    content shouldBe """
      android {
        namespace = ${language.quote("com.acme.dynamite")}
      }
    """.trimIndent()
  }

  @TestFactory
  fun `resourcePrefix is written correctly`() = testFactory {

    val builder = BuildFileSpec {
      android {
        resourcePrefix.setEquals("bingo")
      }
    }

    val content = builder.write(language)

    content shouldBe """
      android {
        resourcePrefix = ${language.quote("bingo")}
      }
    """.trimIndent()
  }

  @Nested
  inner class `compileOptions` {

    @TestFactory
    fun `an empty compileOptions lambda block is written`() = testFactory {

      val builder = BuildFileSpec {
        android {
          compileOptions {
          }
        }
      }

      val content = builder.write(language)

      content shouldBe """
        android {
          compileOptions {
          }
        }
      """.trimIndent()
    }

    @TestFactory
    fun `all compileOptions options`() = testFactory {

      val builder = BuildFileSpec {
        android {
          compileOptions {
            raw("// function calls")
            sourceCompatibility("1.8")
            targetCompatibility("1.8")
            addBlankLine()
            raw("// property setters")
            sourceCompatibility.setEquals("1.8")
            targetCompatibility.setEquals("1.8")
            addBlankLine()
            encoding.setEquals("UTF-8")
            isCoreLibraryDesugaringEnabled.setEquals(true)
          }
        }
      }

      val content = builder.write(language)

      val gen = generator.copy(kotlinInfix = false, groovyLabels = false)

      content shouldBe """
        android {
          compileOptions {
            // function calls
            ${gen.createFunction("sourceCompatibility", "sourceCompatibility" to "1.8")}
            ${gen.createFunction("targetCompatibility", "targetCompatibility" to "1.8")}

            // property setters
            sourceCompatibility = ${language.quote("1.8")}
            targetCompatibility = ${language.quote("1.8")}

            encoding = ${language.quote("UTF-8")}
            isCoreLibraryDesugaringEnabled = true
          }
        }
      """.trimIndent()
    }
  }
}
