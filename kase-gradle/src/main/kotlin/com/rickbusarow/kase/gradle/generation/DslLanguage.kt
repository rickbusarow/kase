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

import com.rickbusarow.kase.gradle.generation.DslLanguage.Groovy
import com.rickbusarow.kase.gradle.generation.DslLanguage.Kotlin
import dev.drewhamilton.poko.Poko

/**
 * The language of the DSL, e.g. [Groovy] or [Kotlin]
 *
 * @property quote the quote character used for strings in this language, e.g. `'` or `"`
 * @property labelDelimiter the delimiter between a label and its value, e.g. `=` or `:`
 */
public sealed class DslLanguage(public val quote: Char, public val labelDelimiter: Char) {

  /**
   * Wraps [content] in parentheses if it's necessary for this syntax, or
   * if this language is [Groovy] and [Groovy.alwaysParenthesize] is `true`.
   *
   * @param content the content to parenthesize, e.g. `'com.acme:dynamite:1.0.0'`
   * @return the parenthesized content, e.g. `('com.acme:dynamite:1.0.0')`
   */
  public abstract fun parens(content: String): String

  /**
   * Wraps [content] in parentheses if it's necessary for this syntax, or
   * if this language is [Groovy] and [Groovy.alwaysParenthesize] is `true`.
   *
   * @param content the content to parenthesize, e.g. `exclude group: "com.acme", module: "rocket"`
   * @return the parenthesized content, e.g. `(exclude group: "com.acme", module: "rocket")`
   */
  public fun parens(content: DslBuilderComponent): String {
    return parens(content.write(this))
  }

  /**
   * Wraps [content] in [quote] characters
   *
   * @param content the content to quote, e.g. `com.acme:dynamite:1.0.0`
   * @return the quoted content, e.g. `'com.acme:dynamite:1.0.0'`
   */
  public fun quote(content: String): String = "$quote$content$quote"

  /** The Kotlin DSL, e.g. `build.gradle.kts` or `settings.gradle.kts` */
  public object Kotlin : DslLanguage(quote = '"', labelDelimiter = '=') {
    override fun parens(content: String): String = "($content)"
  }

  /**
   * The Groovy DSL, e.g. `build.gradle` or `settings.gradle`
   *
   * @param alwaysUseDoubleQuotes whether to use double quotes for strings, even when single
   *   quotes are valid. e.g. `project(":myProject")` instead of `project(':myProject')`
   * @property alwaysParenthesize whether to always parenthesize method
   *   call parameters, even when not syntactically necessary. e.g.
   *   `api('com.acme:dynamite:1.0.0')` instead of `api 'com.acme:dynamite:1.0.0'`
   */
  @Poko
  public class Groovy(
    alwaysUseDoubleQuotes: Boolean = false,
    private val alwaysParenthesize: Boolean = false
  ) : DslLanguage(
    quote = if (alwaysUseDoubleQuotes) '"' else '\'',
    labelDelimiter = ':'
  ) {
    override fun parens(content: String): String {
      return if (alwaysParenthesize) "($content)" else content
    }
  }
}
