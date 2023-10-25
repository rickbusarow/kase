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

package com.rickbusarow.kase.gradle.generation.internal

/**
 * Collects [DslElement]s, to be written to a [DslLanguage]
 * file. Elements are written in the order they are added.
 */
@Suppress("UnnecessaryAbstractClass")
public abstract class AbstractDslElementContainer<SELF : AbstractDslElementContainer<SELF>>(
  elements: MutableList<DslElement> = mutableListOf()
) : DslElementContainer<SELF> {

  private fun apply(action: SELF.() -> Unit): SELF {
    @Suppress("UNCHECKED_CAST")
    action(this as SELF)
    return this
  }

  protected val mutableElements: MutableList<DslElement> = elements

  override val elements: List<DslElement>
    get() = mutableElements

  override fun addElement(element: DslElement): SELF = apply {
    mutableElements.add(element)
  }

  override fun addAllElements(vararg elements: DslElement): SELF = apply {
    mutableElements.addAll(elements)
  }

  override fun addAllElements(
    elements: Collection<DslElement>
  ): SELF = apply { mutableElements.addAll(elements) }

  override fun addBlankLine(): SELF = apply { addElement(BlankLine) }

  override fun write(language: DslLanguage): String {
    return elements.joinToString("\n") {
      if (it is BlankLine) {
        it.write(language)
      } else {
        it.write(language) // .trimEnd()
      }
    }
  }
}
