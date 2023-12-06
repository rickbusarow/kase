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

import com.rickbusarow.kase.DefaultTestEnvironment
import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.gradle.DslLanguage

interface DslKaseTestFactory<T : DslTestEnvironment, K : Kase1<DslLanguage>> : KaseTestFactory<T, K> {

  @Suppress("UNCHECKED_CAST")
  override val kases: List<K>
    get() = dslLanguages.kases() as List<K>

  override fun newTestEnvironment(
    kase: K,
    testFunctionCoordinates: TestFunctionCoordinates
  ): T {
    val environment = DslTestEnvironment(
      language = kase.a1,
      testFunctionCoordinates = testFunctionCoordinates
    )

    @Suppress("UNCHECKED_CAST")
    return environment as T
  }
}

open class DslTestEnvironment(
  val language: DslLanguage,
  testFunctionCoordinates: TestFunctionCoordinates
) : DefaultTestEnvironment(listOf(element = language.displayName), testFunctionCoordinates) {
  val generator = ExpectedCodeGenerator(language)
}

val DslLanguage.displayName: String
  get() = toString()
    .replace("Dsl(", " [ ")
    .replace(")", " ] ")
    .replace("=", ": ")
    .replace(", ", " | ")
