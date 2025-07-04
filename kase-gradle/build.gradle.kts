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
      artifactId = "kase-gradle",
      pomDescription = "Test environments that create Gradle projects for testing Gradle plugins.",
      name = "Kase Gradle"
    )
  }
  poko()
}

dependencies {

  api(libs.junit.jupiter.api)

  api(project(":kase"))

  compileOnly(gradleTestKit())

  testImplementation(gradleTestKit())

  testImplementation(libs.junit.jupiter)
  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.kotest.assertions.api)
  testImplementation(libs.kotest.assertions.core.jvm)
  testImplementation(libs.kotest.assertions.shared)
}
