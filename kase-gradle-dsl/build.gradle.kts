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
    artifactId = "kase-gradle-dsl",
    pomDescription = "A DSL for generating Gradle DSL files."
  )
  poko()
}

dependencies {
  api(project(":kase"))
  api(project(":kase-gradle"))

  compileOnly(gradleTestKit())

  testImplementation(libs.junit.jupiter)
  testImplementation(libs.junit.jupiter.api)
  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.kotest.assertions.shared)
}
