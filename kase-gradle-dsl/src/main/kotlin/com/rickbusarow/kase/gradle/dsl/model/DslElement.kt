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

package com.rickbusarow.kase.gradle.dsl.model

import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.DslStringFactory
import dev.drewhamilton.poko.Poko

/**
 * Interface for any DSL builder components.
 *
 * @since 0.1.0
 */
public fun interface DslElement : DslStringFactory {

  /**
   * Returns the DSL code for this component.
   *
   * @since 0.1.0
   */
  override fun write(language: DslLanguage): String
}

/**
 * shorthand for `write(GroovyDsl(useInfix, useLabels, useDoubleQuotes))`
 *
 * @since 0.1.0
 */
public fun DslElement.writeGroovy(
  useInfix: Boolean = true,
  useLabels: Boolean = false,
  useDoubleQuotes: Boolean = false
): String = write(
  GroovyDsl(
    useInfix = useInfix,
    useLabels = useLabels,
    useDoubleQuotes = useDoubleQuotes
  )
)

/**
 * Shortcut for `ValueStringFactory { value.write(language) }`.
 *
 * @since 0.1.0
 */
public fun DslStringFactory(value: DslElement): DslStringFactory {
  return DslStringFactory(value::write)
}

/**
 * shorthand for `write(KotlinDsl(useInfix, useLabels))`
 *
 * @since 0.1.0
 */
public fun DslElement.writeKotlin(
  useInfix: Boolean = true,
  useLabels: Boolean = false
): String = write(KotlinDsl(useInfix = useInfix, useLabels = useLabels))

/**
 * Represents a blank line in the generated DSL
 *
 * @since 0.1.0
 */
public object BlankLine : DslElement {
  override fun write(language: DslLanguage): String = ""
}

/**
 * Represents exact text to be written to the DSL, without modification.
 *
 * @property value The exact text to be written to the DSL.
 * @since 0.1.0
 */
@JvmInline
public value class RawLiteral(public val value: String) : DslElement {
  override fun write(language: DslLanguage): String = value
}

/**
 * Represents a String value that will be wrapped in single
 * or double quotes, depending upon the dsl language.
 *
 * @property value The String value to be quoted.
 * @property useDoubleQuotes whether to use double quotes for strings,
 *   even when the language is Groovy and single quotes are valid.
 *   e.g. `project(":myProject")` instead of `project(':myProject')`
 * @since 0.1.0
 */
@Poko
public class StringLiteral(
  public val value: String,
  public val useDoubleQuotes: Boolean? = null
) : DslElement {
  override fun write(language: DslLanguage): String {
    return language.quote(
      content = value,
      useDoubleQuotes = useDoubleQuotes ?: language.useDoubleQuotes
    )
  }
}

@Deprecated(
  "Renamed to StringLiteral",
  ReplaceWith("StringLiteral", "com.rickbusarow.kase.gradle.dsl.model.StringLiteral")
)
public typealias Quoted = StringLiteral
