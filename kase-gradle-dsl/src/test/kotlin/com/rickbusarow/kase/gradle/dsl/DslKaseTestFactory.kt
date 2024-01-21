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

package com.rickbusarow.kase.gradle.dsl

import com.rickbusarow.kase.DefaultTestEnvironment
import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.ParamTestEnvironmentFactory
import com.rickbusarow.kase.files.TestLocation
import com.rickbusarow.kase.gradle.DslLanguage

interface DslKaseTestFactory : KaseTestFactory<Kase1<DslLanguage>, DslTestEnvironment, DslTestEnvironment.Factory> {

  override val testEnvironmentFactory: DslTestEnvironment.Factory
    get() = DslTestEnvironment.Factory()

  override val params: List<Kase1<DslLanguage>>
    get() = dslLanguages.kases()
}

open class DslTestEnvironment(
  val language: DslLanguage,
  names: List<String>,
  testLocation: TestLocation
) : DefaultTestEnvironment(names, testLocation) {
  val generator = ExpectedCodeGenerator(language)

  class Factory : ParamTestEnvironmentFactory<Kase1<DslLanguage>, DslTestEnvironment> {
    override fun create(
      params: Kase1<DslLanguage>,
      names: List<String>,
      location: TestLocation
    ): DslTestEnvironment = DslTestEnvironment(params.a1, names, location)
  }
}

val DslLanguage.displayName: String
  get() = toString()
    .replace("Dsl(", " [ ")
    .replace(")", " ] ")
    .replace("=", ": ")
    .replace(", ", " | ")
