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

import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

plugins {
  kotlin("multiplatform")
}


val compilerVersionAttribute: Attribute<String> = Attribute.of(
  "kase.ioType",
  String::class.java,
)

kotlin {
  jvm("kotlin1810") { attributes.attribute(compilerVersionAttribute, name) }
  jvm("kotlin1820") { attributes.attribute(compilerVersionAttribute, name) }
  jvm("kotlin190") { attributes.attribute(compilerVersionAttribute, name) }

  targets.withType<KotlinJvmTarget> {
    jvmToolchain(11)
    // withJava()


    println("######################################## artifacts task name -- $artifactsTaskName")


    testRuns.configureEach {
      executionTask.configure { useJUnitPlatform() }
    }
  }

  tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
      allWarningsAsErrors = false

      // val kotlinMajor = libs.versions.kotlinApi.get()
      // languageVersion = kotlinMajor
      // apiVersion = kotlinMajor

      // jvmTarget = libs.versions.jvmTarget.get()

      freeCompilerArgs += listOf(
        "-opt-in=kotlin.contracts.ExperimentalContracts",
      )
    }
  }

  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val kotlin1810Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.10")
      }
    }
    val kotlin1810Test by getting
    val kotlin1820Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.20")
      }
    }
    val kotlin1820Test by getting
    val kotlin190Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
      }
    }
    val kotlin190Test by getting
  }
}


kotlin {
  jvm()


}

// plugins {
//   id("jvm-module")
// }

// jvmModule {
//   published(
//     artifactId = "kase",
//     pomDescription = "Hermetic test environments and test factories"
//   )
//   poko()
// }

// dependencies {
//
//   api(libs.square.okio)
//   api(libs.junit.engine)
//   api(libs.junit.jupiter)
//   api(libs.junit.jupiter.api)
//   api(libs.junit.params)
//   api(libs.kotest.assertions.api)
//   api(libs.kotest.assertions.core.jvm)
//   api(libs.kotest.assertions.shared)
//   api(libs.kotest.common)
//   api(libs.kotest.extensions)
//   api(libs.kotest.property.jvm)
//   api(libs.kotlinx.coroutines.core)
// }
