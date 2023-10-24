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

/** Interface for any DSL builder components. */
public interface DslElement : Comparable<DslElement> {

  /** Returns the DSL code for this component. */
  public fun write(language: DslLanguage): String

  override fun compareTo(other: DslElement): Int {
    return toString().compareTo(other.toString())
  }
}

/** Represents a blank line in the generated DSL */
public object BlankLine : DslElement {
  override fun write(language: DslLanguage): String = ""
}

/**
 * Represents a String value that will be wrapped in single
 * or double quotes, depending upon the dsl language.
 *
 * @property value The String value to be quoted.
 */
@JvmInline
public value class Quoted(public val value: String) : DslElement {
  override fun write(language: DslLanguage): String = language.quote(value)
}
