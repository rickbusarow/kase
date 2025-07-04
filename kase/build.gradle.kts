/*
 * Copyright (C) 2025 Rick Busarow
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
  alias(libs.plugins.mahout.kotlin.jvm.module)
}

mahout {
  publishing {
    publishMaven(
      artifactId = "kase",
      pomDescription = "Hermetic test environments and test factories",
      name = "Kase"
    )
  }
  poko()
}

// the overloaded functions can take 10+ minutes
tasks.named("detektMain", io.gitlab.arturbosch.detekt.Detekt::class) {
  exclude("**/overloads/**")
}

dependencies {

  api(libs.junit.jupiter.api)

  implementation(libs.kotlinx.coroutines.core)

  testImplementation(libs.junit.jupiter)
  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.junit4)
  testImplementation(libs.kotest.assertions.api)
  testImplementation(libs.kotest.assertions.core.jvm)
  testImplementation(libs.kotest.assertions.shared)
  testImplementation(libs.kotest.common)
}
