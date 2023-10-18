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

@file:Suppress("MemberVisibilityCanBePrivate")

package com.rickbusarow.kase.gradle.generation

import com.rickbusarow.kase.gradle.generation.Parameter.Companion.join
import dev.drewhamilton.poko.Poko

/**
 * A dependency exclusion.
 *
 * In the Kotlin DSL:
 * ```
 * exclude(group = "com.acme", module = "rocket")
 * ```
 *
 * In the Groovy DSL:
 * ```groovy
 * exclude group: 'com.acme', module: 'rocket'
 * ```
 *
 * @property group the group to exclude, such as `com.acme`
 * @property module the module to exclude, such as `rocket`
 */
@Poko
public class DependencyExclusion(
  public val group: String? = null,
  public val module: String? = null
) : DslBuilderComponent {
  init {
    require(group != null || module != null) {
      "At least one of group or module must be non-null"
    }
  }

  override fun write(language: DslLanguage): String {

    val params: ParameterList = buildList {
      if (group != null) {
        add(Parameter("group", group))
      }
      if (module != null) {
        add(Parameter("module", module))
      }
    }
      .join(", ")

    return "exclude${language.parens(params)}"
  }
}

/** A list of [Parameter]s. */
@Poko
public class ParameterList(
  private val parameters: List<Parameter>,
  private val separator: String = ", "
) : DslBuilderComponent {

  /** The number of parameters in this list. */
  public val size: Int get() = parameters.size

  /** @return `true` if [size] is zero, otherwise `false`. */
  public fun isEmpty(): Boolean = parameters.isEmpty()

  /** @return `true` if [size] is greater than zero, otherwise `false`. */
  public fun isNotEmpty(): Boolean = parameters.isNotEmpty()

  override fun write(language: DslLanguage): String {
    return parameters.joinToString(separator = separator) { it.write(language) }
  }
}
