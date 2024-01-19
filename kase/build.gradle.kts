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

plugins {
  id("jvm-module")
}

jvmModule {
  published(
    artifactId = "kase",
    pomDescription = "Hermetic test environments and test factories"
  )
  poko()
}

// the overloaded functions can take 10+ minutes
tasks.named("detektMain", io.gitlab.arturbosch.detekt.Detekt::class) {
  exclude("**/overloads/**")
}

dependencies {

  api(libs.junit.jupiter)
  api(libs.junit.jupiter.api)
  api(libs.kotest.assertions.api)
  api(libs.kotest.assertions.shared)
  api(libs.kotest.common)
  api(libs.kotlinx.coroutines.core)

  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.junit4)
  testImplementation(libs.kotest.assertions.core.jvm)
}
