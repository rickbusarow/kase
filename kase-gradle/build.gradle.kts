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
  id("jvm-module")
}

jvmModule {
  published(
    artifactId = "kase-gradle",
    pomDescription = "Test environments that create Gradle projects for testing Gradle plugins."
  )
  poko()
}

dependencies {
  api(libs.junit.engine)
  api(libs.junit.jupiter)
  api(libs.junit.jupiter.api)
  api(libs.junit.params)
  api(libs.kotest.assertions.api)
  api(libs.kotest.assertions.core.jvm)
  api(libs.kotest.assertions.shared)
  api(libs.kotest.common)
  api(libs.kotest.extensions)
  api(libs.kotest.property.jvm)

  api(project(":kase"))

  compileOnly(gradleTestKit())
}
