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

package com.rickbusarow.kase.gradle.generation

/**
 * Collects [DslElement]s, to be written to a [DslLanguage]
 * file. Elements are written in the order they are added.
 */
public abstract class DslElementContainer(
  elements: MutableList<DslElement> = mutableListOf()
) : DslElement {
  private val _elements: MutableList<DslElement> = elements

  @PublishedApi
  internal val elements: List<DslElement>
    get() = _elements

  internal fun add(element: DslElement) {
    _elements.add(element)
  }

  /**
   * Adds a new [FunctionCall] to the DSL.
   *
   * @param name the name of the function, such as `exclude`
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @param parameters the list of parameters to pass to the function
   */
  internal fun <T : DslElementContainer> T.functionCall(
    name: String,
    labelSupport: FunctionCall.LabelSupport,
    vararg parameters: Parameter
  ): T = apply {
    add(
      FunctionCall(
        name = name,
        labelSupport = labelSupport,
        parameters = parameters.toList()
          .dropLastWhile { it is LambdaParameter && it.elements.isEmpty() }
      )
    )
  }

  internal fun addAll(vararg elements: DslElement) {
    this._elements.addAll(elements)
  }

  internal fun addAll(elements: Collection<DslElement>) {
    this._elements.addAll(elements)
  }

  /** Adds a newLine to the DSL. */
  public fun newLine(): DslElementContainer = apply { add(NewLine) }

  override fun write(language: DslLanguage): String {

    return elements.joinToString("\n") {
      if (it is NewLine) {
        it.write(language)
      } else {
        it.write(language).trimEnd()
      }
    }
  }
}
