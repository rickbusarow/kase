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

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestFactory

class AndroidComponentsExtensionSpecTest : DslKaseTestFactory {

  @TestFactory
  fun `an empty androidComponents lambda block is written`() = testFactory {

    val builder = BuildFileSpec {
      androidComponents {
      }
    }

    val content = builder.write(language)

    content shouldBe "androidComponents {\n}"
  }

  @TestFactory
  fun `finaleDsl is written correctly`() = testFactory {

    val builder = BuildFileSpec {
      androidComponents {
        finalizeDsl { }
      }
    }

    val content = builder.write(language)

    content shouldBe """
      androidComponents {
        finalizeDsl {
        }
      }
    """.trimIndent()
  }
}
