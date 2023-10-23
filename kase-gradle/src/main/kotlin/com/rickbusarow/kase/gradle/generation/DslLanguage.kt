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
import com.rickbusarow.kase.gradle.generation.FunctionCall.LabelSupport
import com.rickbusarow.kase.gradle.generation.LanguageSpecific.GroovyCompatible
import com.rickbusarow.kase.gradle.generation.LanguageSpecific.KotlinCompatible
import dev.drewhamilton.poko.Poko

/**
 * The language of the DSL, e.g. [Groovy] or [Kotlin]
 *
 * @property quote the quoted character used for strings in this language, e.g. `'` or `"`
 * @property labelDelimiter the delimiter between a label and its value, e.g. ` = ` or `: `
 */
public sealed class DslLanguage(public val quote: Char, public val labelDelimiter: String) {

  /**
   * Whether to always parenthesize method call parameters, even when not syntactically necessary.
   *
   * example in Kotlin:
   * ```
   * plugins {
   *   // useInfix = true (this is default behavior)
   *   id("com.acme.anvil") version "1.0.0" apply false
   *   // useInfix = false
   *   id("com.acme.anvil").version("1.0.0").apply(false)
   * }
   * ```
   *
   * example in Groovy:
   * ```
   * plugins {
   *  // useInfix = true (this is default behavior)
   *  id 'com.acme.anvil' version '1.0.0' apply false
   *  // useInfix = false
   *  id('com.acme.anvil').version('1.0.0').apply(false)
   * ```
   */
  public abstract val useInfix: Boolean

  /**
   * Wraps [content] in parentheses if it's necessary for this syntax,
   * or if this language is [Groovy] and [Groovy.useInfix] is `true`.
   *
   * @param content the content to parenthesize, e.g. `'com.acme:dynamite:1.0.0'`
   * @param infixInKotlin whether the [content] is an infix method call in the Kotlin DSL
   * @return the parenthesized content, e.g. `('com.acme:dynamite:1.0.0')`
   */
  public abstract fun parens(content: String, infixInKotlin: Boolean = false): String

  /**
   * Wraps [content] in parentheses if it's necessary for this syntax,
   * or if this language is [Groovy] and [Groovy.useInfix] is `true`.
   *
   * @param content the content to parenthesize, e.g. `exclude group: "com.acme", module: "rocket"`
   * @return the parenthesized content, e.g. `(exclude group: "com.acme", module: "rocket")`
   */
  public fun parens(content: DslElement): String {
    return parens(content.write(this))
  }

  /**
   * Wraps [content] in [quote] characters
   *
   * @param content the content to quote, e.g. `com.acme:dynamite:1.0.0`
   * @return the quoted content, e.g. `'com.acme:dynamite:1.0.0'`
   */
  public fun quote(content: String): String = "$quote$content$quote"

  /** invokes [action] within this language's scope and returns the result */
  public inline fun <reified T> write(action: DslLanguage.() -> T): T = action()

  /**
   * Treats the receiver string as a method name and invokes it with the given
   * [param]. The parameter may be wrapped in parentheses or treated as infix.
   */
  public operator fun String.invoke(param: String): String = "$this${parens(param)}"

  /**
   * Treats the receiver string as a method name and invokes it with the given [param].
   * The parameter may be wrapped in parentheses or treated as infix. The returned
   * string will also be appended as a line in the context receiver StringBuilder.
   */
  context (StringBuilder)
  public operator fun String.invoke(param: String): String {
    return "$this${parens(param)}".also { append(it) }
  }

  /**
   * Treats the receiver string as a method name and invokes it with the given [params].
   * The parameter may be wrapped in parentheses or treated as infix. The returned
   * string will also be appended as a line in the context receiver StringBuilder.
   */
  context (StringBuilder)
  public operator fun String.invoke(
    vararg params: Parameter,
    separator: String = ParameterList.SEPARATOR_DEFAULT
  ): String = invoke(ParameterList(params.toList(), separator))

  /**
   * Treats the receiver string as a method name and invokes it with the given [params].
   * The parameter may be wrapped in parentheses or treated as infix. The returned
   * string will also be appended as a line in the context receiver StringBuilder.
   */
  context (StringBuilder)
  public operator fun String.invoke(params: ParameterList): String {
    val paramString = params.write(this@DslLanguage)
    return invoke("$this${parens(paramString)}")
  }

  /** Whether this language supports some language-specific feature. */
  public fun supports(languageSpecific: LanguageSpecific): Boolean {
    return when (this) {
      is Groovy -> languageSpecific is GroovyCompatible
      is Kotlin -> languageSpecific is KotlinCompatible
    }
  }

  /** Whether this language supports labels for this function or parameter */
  public fun supports(labelSupport: LabelSupport): Boolean {
    return when (this) {
      is Groovy -> labelSupport is GroovyCompatible
      is Kotlin -> labelSupport is KotlinCompatible
    }
  }

  /** The Kotlin DSL, e.g. `build.gradle.kts` or `settings.gradle.kts` */
  @Poko
  public class Kotlin(
    override val useInfix: Boolean = true
  ) : DslLanguage(
    quote = '"',
    labelDelimiter = " = "
  ) {
    override fun parens(content: String, infixInKotlin: Boolean): String =
      if (infixInKotlin && useInfix && content.isNotBlank()) {
        " $content"
      } else {
        "($content)"
      }
  }

  /**
   * The Groovy DSL, e.g. `build.gradle` or `settings.gradle`
   *
   * @param alwaysUseDoubleQuotes whether to use double quotes for strings, even when single
   *   quotes are valid. e.g. `project(":myProject")` instead of `project(':myProject')`
   * @property useInfix whether to always parenthesize method call
   *   parameters, even when not syntactically necessary. e.g.
   *   `api('com.acme:dynamite:1.0.0')` instead of `api 'com.acme:dynamite:1.0.0'`
   */
  @Poko
  public class Groovy(
    alwaysUseDoubleQuotes: Boolean = false,
    override val useInfix: Boolean = true
  ) : DslLanguage(
    quote = if (alwaysUseDoubleQuotes) '"' else '\'',
    labelDelimiter = ": "
  ) {
    override fun parens(content: String, infixInKotlin: Boolean): String =
      if (useInfix && content.isNotBlank()) {
        " $content"
      } else {
        "($content)"
      }
  }
}

/**
 * A marker indicating whether some DSL component is compatible with a specific language.
 *
 * Some examples of language-specific features:
 * - parameter labels or named arguments
 * - infix method calls
 * - single or double quotes for strings
 */
public sealed interface LanguageSpecific {
  /** A marker indicating that some DSL component is compatible with the Groovy DSL. */
  public interface GroovyCompatible : LanguageSpecific

  /** A marker indicating that some DSL component is compatible with the Kotlin DSL. */
  public interface KotlinCompatible : LanguageSpecific

  /**
   * A marker indicating that some DSL component is compatible with both the Groovy and Kotlin DSLs.
   */
  public interface GroovyAndKotlinCompatible : GroovyCompatible, KotlinCompatible
}
