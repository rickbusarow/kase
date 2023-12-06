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
import org.junit.jupiter.api.TestFactory

class BuildFileSpecTest : DslKaseTestFactory<DslTestEnvironment, Kase1<DslLanguage>> {

  @TestFactory
  fun `dependencies block`() = testFactory {

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
  fun `simple dependency declarations`() = testFactory {

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

    val gen = generator.copy(
      kotlinInfix = false,
      groovyLabels = false
    )

    content shouldBe """
        |dependencies {
        |  ${gen.createFunction("androidTestApi", "dependencyNotation", language.quote("a:b:c"))}
        |  ${
      gen.createFunction(
        "androidTestCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "androidTestImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "androidTestRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${gen.createFunction("anvil", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("anvilAndroidTest", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("anvilDebug", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("anvilRelease", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("anvilTest", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("api", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("commonJvmApi", "dependencyNotation", language.quote("a:b:c"))}
        |  ${
      gen.createFunction(
        "commonJvmCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonJvmImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonJvmRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${gen.createFunction("commonJvmTestApi", "dependencyNotation", language.quote("a:b:c"))}
        |  ${
      gen.createFunction(
        "commonJvmTestCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonJvmTestImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonJvmTestRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${gen.createFunction("commonMainApi", "dependencyNotation", language.quote("a:b:c"))}
        |  ${
      gen.createFunction(
        "commonMainCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonMainImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonMainRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${gen.createFunction("commonTestApi", "dependencyNotation", language.quote("a:b:c"))}
        |  ${
      gen.createFunction(
        "commonTestCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonTestImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "commonTestRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${gen.createFunction("compileOnly", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("debugApi", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("debugCompileOnly", "dependencyNotation", language.quote("a:b:c"))}
        |  ${
      gen.createFunction(
        "debugImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${gen.createFunction("debugRuntimeOnly", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("implementation", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("kapt", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("kaptAndroidTest", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("kaptDebug", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("kaptRelease", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("kaptTest", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("ksp", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("kspAndroidTest", "dependencyNotation", language.quote("a:b:c"))}
        |  ${gen.createFunction("kspDebug", "dependencyNotation", language.quote("a:b:c"))}
        |  ${
      gen.createFunction(
        "kspRelease",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "kspTest",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "releaseApi",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "releaseCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "releaseImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "releaseRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "runtimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testApi",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testFixturesApi",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testFixturesCompileOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testFixturesImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testFixturesRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testImplementation",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |  ${
      gen.createFunction(
        "testRuntimeOnly",
        "dependencyNotation",
        language.quote("a:b:c")
      )
    }
        |}
    """.trimMargin()
  }
}
