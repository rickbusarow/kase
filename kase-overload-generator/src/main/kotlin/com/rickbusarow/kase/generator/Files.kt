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

package com.rickbusarow.kase.generator

import java.nio.file.Paths

internal object Files {

  private val workingDir = Paths.get("").toAbsolutePath().toFile()
  private val projectRoot = generateSequence(workingDir) { it.parentFile }
    .first { it.resolve("settings.gradle.kts").exists() }

  internal val testElements = projectRoot.resolve("kase-gradle")
    .resolve("src/test/kotlin")
    .resolve("com/rickbusarow/kase/gradle")
    .resolve("testElements.kt")

  internal val overloadDir = projectRoot.resolve("kase")
    .resolve("src/main/kotlin")
    .resolve("com/rickbusarow/kase/overloads")

  internal fun overloadKase(ct: Int) = overloadDir.resolve("Kase$ct.kt")
}
