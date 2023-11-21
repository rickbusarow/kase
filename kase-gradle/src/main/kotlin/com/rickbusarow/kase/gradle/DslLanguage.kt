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

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.DslLanguageSettings.GroovyCompatible
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.KotlinCompatible
import dev.drewhamilton.poko.Poko

/** Trait interface for a [DslLanguage]. */
public interface HasDslLanguage {
  /** The [DslLanguage] instance. */
  public val dslLanguage: DslLanguage
}

/**
 * The language of the DSL, e.g. [GroovyDsl] or [KotlinDsl]
 *
 * @property quote the quoted character used for strings in this language, e.g. `'` or `"`
 * @property labelDelimiter the delimiter between a label and its value, e.g. ` = ` or `: `
 */
public sealed class DslLanguage(
  public val quote: Char,
  public val labelDelimiter: String
) {
  private val doubleQuote: Char = '"'

  /**
   * Whether to use double quotes for strings, even when single quotes are
   * valid. e.g. `project(":myProject")` instead of `project(':myProject')`
   *
   * This is always `true` in [KotlinDsl]
   */
  public abstract val useDoubleQuotes: Boolean

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
   * Whether to use labels for the parameters of function calls, where they're supported.
   *
   * example in Kotlin:
   * ```
   * dependencies {
   *   // useLabels = true (this is default behavior)
   *   api(project(path = ":myProject"))
   *   // useLabels = false
   *   api(project(":myProject"))
   * }
   * ```
   *
   * example in Groovy:
   * ```
   * dependencies {
   *   // useLabels = true (this is default behavior)
   *   api project(path: ':myProject')
   *   // useLabels = false
   *   api project(':myProject')
   * }
   * ```
   */
  public abstract val useLabels: Boolean

  /** Returns `settings.gradle` for Groovy and `settings.gradle.kts` for Kotlin. */
  public val settingsFileName: String = scriptFileName("settings")

  /** Returns `build.gradle` for Groovy and `build.gradle.kts` for Kotlin. */
  public val buildFileName: String = scriptFileName("build")

  /**
   * Wraps [content] in parentheses if it's necessary for this syntax, or
   * if this language is [GroovyDsl] and [GroovyDsl.useInfix] is `true`.
   *
   * @param content the content to parenthesize, e.g. `'com.acme:dynamite:1.0.0'`
   * @param infixSupport overrides the default behavior of the language's [useInfix] setting
   * @return the parenthesized content, e.g. `('com.acme:dynamite:1.0.0')`
   */
  public fun parens(content: String, infixSupport: InfixSupport? = null): String =
    when {
      content.isBlank() -> "()"
      infixSupport != null && !supports(infixSupport) -> "($content)"
      infixSupport != null && supports(infixSupport) -> " $content"
      useInfix -> " $content"
      else -> "($content)"
    }

  /**
   * Wraps [content] in [quote] characters
   *
   * @param content the content to quote, e.g. `com.acme:dynamite:1.0.0`
   * @param useDoubleQuotes whether to use double quotes for strings,
   *   even when the language is Groovy and single quotes are valid.
   *   e.g. `project(":myProject")` instead of `project(':myProject')`
   * @return the quoted content, e.g. `'com.acme:dynamite:1.0.0'`
   */
  public fun quote(
    content: String,
    useDoubleQuotes: Boolean = this.useDoubleQuotes
  ): String {
    return when {
      useDoubleQuotes -> "$doubleQuote$content$doubleQuote"
      else -> "$quote$content$quote"
    }
  }

  /** invokes [action] within this language's scope and returns the result */
  public inline fun <reified T> write(action: DslLanguage.() -> T): T = action()

  /** Returns `$simpleName.gradle` for Groovy and `$simpleName.gradle.kts` for Kotlin. */
  public fun scriptFileName(simpleName: String): String = when (this) {
    is GroovyDsl -> "$simpleName.gradle"
    is KotlinDsl -> "$simpleName.gradle.kts"
  }

  /**
   * Treats the receiver string as a method name and invokes it with the given
   * [param]. The parameter may be wrapped in parentheses or treated as infix.
   */
  public operator fun String.invoke(param: String): String = "$this${parens(param)}"

  /** Whether this language supports some language-specific feature. */
  public fun supports(settings: DslLanguageSettings): Boolean {
    return when (this) {
      is GroovyDsl -> settings is GroovyCompatible
      is KotlinDsl -> settings is KotlinCompatible
    }
  }

  /**
   * The Kotlin DSL, e.g. `build.gradle.kts` or `settings.gradle.kts`
   *
   * @property useInfix whether to always parenthesize method
   *   call parameters, even when not syntactically necessary.
   * @property useLabels whether to use labels for the parameters of function calls,
   */
  @Poko
  public class KotlinDsl(
    override val useInfix: Boolean = true,
    override val useLabels: Boolean = false
  ) : DslLanguage(quote = '"', labelDelimiter = " = ") {

    override val useDoubleQuotes: Boolean = true
  }

  /**
   * The Groovy DSL, e.g. `build.gradle` or `settings.gradle`
   *
   * @property useInfix whether to always parenthesize method
   *   call parameters, even when not syntactically necessary.
   * @property useLabels whether to use labels for the parameters of function calls,
   * @property useDoubleQuotes whether to use double quotes
   *   for strings, even when single quotes are valid.
   */
  @Poko
  public class GroovyDsl(
    override val useInfix: Boolean = true,
    override val useLabels: Boolean = false,
    override val useDoubleQuotes: Boolean = false
  ) : DslLanguage(quote = '\'', labelDelimiter = ": ")
}

/**
 * A marker indicating whether some DSL component is compatible with a specific language.
 *
 * Some examples of language-specific features:
 * - parameter labels or named arguments
 * - infix method calls
 * - single or double quotes for strings
 */
public sealed interface DslLanguageSettings {
  /** A marker indicating that some DSL component is compatible with the Groovy DSL. */
  public interface GroovyCompatible : DslLanguageSettings

  /** A marker indicating that some DSL component is compatible with the Kotlin DSL. */
  public interface KotlinCompatible : DslLanguageSettings

  /**
   * A marker indicating that some DSL component is compatible with both the Groovy and Kotlin DSLs.
   */
  public interface GroovyAndKotlinCompatible : GroovyCompatible, KotlinCompatible

  /**
   * Whether this function should be treated as property access instead.
   *
   * Given some Java:
   *
   * ```java
   * interface MyInterface {
   *   String foo;
   *   // supports property access because of the parameter type
   *   setFoo(String str);
   *   // does not support property access because CharSequence cannot be assigned to String
   *   setFoo(CharSequence str);
   *   // supports property access because `foo` is a `String`
   *   String getFoo();
   *   // does not support property access because `foo` is a `String`
   *   Integer getFoo();
   * }
   * ```
   *
   * Property access is supported by default in both languages, but only
   * if the parameter is assignable to the property - such as a `String`
   * parameter when the property is a `CharSequence`. If the parameter is
   * not assignable to the property, then the function will be used instead.
   */
  public sealed class PropertyAccessSupport : DslLanguageSettings {

    override fun toString(): String = javaClass.simpleName

    /** No property access syntax supported. */
    public object None : PropertyAccessSupport()

    /** Property access is supported in the Groovy DSL. */
    public object GroovyPropertyAccess : PropertyAccessSupport(), GroovyCompatible

    /** Property access is supported in the Kotlin DSL. */
    public object KotlinPropertyAccess : PropertyAccessSupport(), KotlinCompatible

    /** Property access is supported in both the Groovy and Kotlin DSLs. */
    public object GroovyAndKotlinPropertyAccess : PropertyAccessSupport(), GroovyAndKotlinCompatible
  }

  /**
   * Whether this function call supports labels for its parameters.
   *
   * Labels will be supported in Kotlin if the function was re-written in the Kotlin DSL.
   */
  public sealed class LabelSupport : DslLanguageSettings {

    override fun toString(): String = javaClass.simpleName

    /** No labels are supported. */
    public object NoLabels : LabelSupport()

    /** Labels are supported in the Groovy DSL. */
    public object GroovyLabels : LabelSupport(), GroovyCompatible

    /** Labels are supported in the Kotlin DSL. */
    public object KotlinLabels : LabelSupport(), KotlinCompatible

    /** Labels are supported in both the Groovy and Kotlin DSLs. */
    public object GroovyAndKotlinLabels : LabelSupport(), GroovyAndKotlinCompatible
  }

  /**
   * Whether this function call supports infix.
   *
   * Infix *may* be supported in Kotlin if the function has a receiver
   * and only one parameter, and it was rewritten in the Kotlin DSL.
   */
  public sealed class InfixSupport : DslLanguageSettings {

    override fun toString(): String = javaClass.simpleName

    /** No infix is supported. */
    public object NoInfix : InfixSupport()

    /** Infix is supported in the Groovy DSL. */
    public object GroovyInfix : InfixSupport(), GroovyCompatible

    /** Infix is supported in the Kotlin DSL. */
    public object KotlinInfix : InfixSupport(), KotlinCompatible

    /** Infix is supported in both the Groovy and Kotlin DSLs. */
    public object GroovyAndKotlinInfix : InfixSupport(), GroovyAndKotlinCompatible
  }
}
