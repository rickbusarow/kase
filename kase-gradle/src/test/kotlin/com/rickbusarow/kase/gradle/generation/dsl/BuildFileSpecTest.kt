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

import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.gradle.generation.ExpectedCodeGenerator
import com.rickbusarow.kase.gradle.generation.dslLanguages
import com.rickbusarow.kase.kases
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestFactory

class BuildFileSpecTest {

  @TestFactory
  fun `dependencies block`() = kases(dslLanguages)
    .asTests { language ->

      val builder = BuildFileSpec {

        dependencies {}
      }

      val content = builder.write(language)

      content shouldBe """
        |dependencies {
        |}
      """.trimMargin()
    }

  @TestFactory
  fun `simple dependency declarations`() = kases(dslLanguages)
    .asTests { language ->

      val builder = BuildFileSpec {

        dependencies {

          androidTestApi("a:b:c")
          androidTestCompileOnly("a:b:c")
          androidTestImplementation("a:b:c")
          androidTestRuntimeOnly("a:b:c")
          api("a:b:c")
          commonJvmApi("a:b:c")
          commonJvmCompileOnly("a:b:c")
          commonJvmImplementation("a:b:c")
          commonJvmRuntimeOnly("a:b:c")
          commonJvmTestApi("a:b:c")
          commonJvmTestCompileOnly("a:b:c")
          commonJvmTestImplementation("a:b:c")
          commonJvmTestRuntimeOnly("a:b:c")
          commonMainApi("a:b:c")
          commonMainCompileOnly("a:b:c")
          commonMainImplementation("a:b:c")
          commonMainRuntimeOnly("a:b:c")
          commonTestApi("a:b:c")
          commonTestCompileOnly("a:b:c")
          commonTestImplementation("a:b:c")
          commonTestRuntimeOnly("a:b:c")
          compileOnly("a:b:c")
          debugApi("a:b:c")
          debugCompileOnly("a:b:c")
          debugImplementation("a:b:c")
          debugRuntimeOnly("a:b:c")
          implementation("a:b:c")
          releaseApi("a:b:c")
          releaseCompileOnly("a:b:c")
          releaseImplementation("a:b:c")
          releaseRuntimeOnly("a:b:c")
          runtimeOnly("a:b:c")
          testApi("a:b:c")
          testCompileOnly("a:b:c")
          testFixturesApi("a:b:c")
          testFixturesCompileOnly("a:b:c")
          testFixturesImplementation("a:b:c")
          testFixturesRuntimeOnly("a:b:c")
          testImplementation("a:b:c")
          testRuntimeOnly("a:b:c")
        }
      }

      val content = builder.write(language)

      val expectedGenerator = ExpectedCodeGenerator(
        language = language,
        kotlinInfix = false,
        kotlinLabels = false,
        groovyLabels = false
      )

      content shouldBe """
        |dependencies {
        |  ${expectedGenerator.create("androidTestApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("androidTestCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("androidTestImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("androidTestRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("api", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmTestApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmTestCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmTestImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonJvmTestRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonMainApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonMainCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonMainImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonMainRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonTestApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonTestCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonTestImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("commonTestRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("compileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("debugApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("debugCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("debugImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("debugRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("implementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("releaseApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("releaseCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("releaseImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("releaseRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("runtimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testFixturesApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testFixturesCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testFixturesImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testFixturesRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.create("testRuntimeOnly", "dependencyNotation", "a:b:c")}
        |}
      """.trimMargin()
    }
}
