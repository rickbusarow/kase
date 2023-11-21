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

import com.rickbusarow.kase.asTests
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
          anvil("a:b:c")
          anvilAndroidTest("a:b:c")
          anvilDebug("a:b:c")
          anvilRelease("a:b:c")
          anvilTest("a:b:c")
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
          kapt("a:b:c")
          kaptAndroidTest("a:b:c")
          kaptDebug("a:b:c")
          kaptRelease("a:b:c")
          kaptTest("a:b:c")
          ksp("a:b:c")
          kspAndroidTest("a:b:c")
          kspDebug("a:b:c")
          kspRelease("a:b:c")
          kspTest("a:b:c")
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
        groovyLabels = false
      )

      content shouldBe """
        |dependencies {
        |  ${expectedGenerator.createFunction("androidTestApi", "dependencyNotation", "a:b:c")}
        |  ${
        expectedGenerator.createFunction(
          "androidTestCompileOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "androidTestImplementation",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "androidTestRuntimeOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${expectedGenerator.createFunction("anvil", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("anvilAndroidTest", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("anvilDebug", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("anvilRelease", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("anvilTest", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("api", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("commonJvmApi", "dependencyNotation", "a:b:c")}
        |  ${
        expectedGenerator.createFunction(
          "commonJvmCompileOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonJvmImplementation",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonJvmRuntimeOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${expectedGenerator.createFunction("commonJvmTestApi", "dependencyNotation", "a:b:c")}
        |  ${
        expectedGenerator.createFunction(
          "commonJvmTestCompileOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonJvmTestImplementation",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonJvmTestRuntimeOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${expectedGenerator.createFunction("commonMainApi", "dependencyNotation", "a:b:c")}
        |  ${
        expectedGenerator.createFunction(
          "commonMainCompileOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonMainImplementation",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonMainRuntimeOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${expectedGenerator.createFunction("commonTestApi", "dependencyNotation", "a:b:c")}
        |  ${
        expectedGenerator.createFunction(
          "commonTestCompileOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonTestImplementation",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "commonTestRuntimeOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${expectedGenerator.createFunction("compileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("debugApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("debugCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("debugImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("debugRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("implementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kapt", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kaptAndroidTest", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kaptDebug", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kaptRelease", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kaptTest", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("ksp", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kspAndroidTest", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kspDebug", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kspRelease", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("kspTest", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("releaseApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("releaseCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${
        expectedGenerator.createFunction(
          "releaseImplementation",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${expectedGenerator.createFunction("releaseRuntimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("runtimeOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("testApi", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("testCompileOnly", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("testFixturesApi", "dependencyNotation", "a:b:c")}
        |  ${
        expectedGenerator.createFunction(
          "testFixturesCompileOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "testFixturesImplementation",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${
        expectedGenerator.createFunction(
          "testFixturesRuntimeOnly",
          "dependencyNotation",
          "a:b:c"
        )
      }
        |  ${expectedGenerator.createFunction("testImplementation", "dependencyNotation", "a:b:c")}
        |  ${expectedGenerator.createFunction("testRuntimeOnly", "dependencyNotation", "a:b:c")}
        |}
      """.trimMargin()
    }
}
