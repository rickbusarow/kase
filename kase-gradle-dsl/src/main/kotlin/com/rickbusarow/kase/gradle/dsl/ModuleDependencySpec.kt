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

import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.GroovyInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.GroovyAndKotlinLabels
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.ParameterList
import com.rickbusarow.kase.gradle.dsl.model.ValueParameter

/**
 * Configures an individual project or external dependency
 *
 * @since 0.1.0
 */
public class ModuleDependencySpec : AbstractDslElementContainer<ModuleDependencySpec>() {

  /**
   * ```
   * api 'com.acme:hairbrush:1.2.3' {
   *   exclude group: 'com.acme', module: 'toothbrush'
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun exclude(
    group: String? = null,
    module: String? = null
  ): ModuleDependencySpec = functionCall(
    name = "exclude",
    labelSupport = GroovyAndKotlinLabels,
    infixSupport = GroovyInfix,
    ParameterList(
      listOfNotNull(
        group?.let { ValueParameter("group", it.asStringLiteral()) },
        module?.let { ValueParameter("module", it.asStringLiteral()) }
      )
    )
  )
}
