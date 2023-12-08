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

/**
 * A marker indicating whether some DSL component is compatible with a specific language.
 *
 * Some examples of language-specific features:
 * - parameter labels or named arguments
 * - infix method calls
 * - single or double quotes for strings
 *
 * @since 0.1.0
 */
public sealed interface DslLanguageSettings {
  /**
   * A marker indicating that some DSL component is compatible with the Groovy DSL.
   *
   * @since 0.1.0
   */
  public interface GroovyCompatible : DslLanguageSettings

  /**
   * A marker indicating that some DSL component is compatible with the Kotlin DSL.
   *
   * @since 0.1.0
   */
  public interface KotlinCompatible : DslLanguageSettings

  /**
   * A marker indicating that some DSL component is compatible with both the Groovy and Kotlin DSLs.
   *
   * @since 0.1.0
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
   *
   * @since 0.1.0
   */
  public sealed class PropertyAccessSupport : DslLanguageSettings {

    override fun toString(): String = javaClass.simpleName

    /**
     * No property access syntax supported.
     *
     * @since 0.1.0
     */
    public object None : PropertyAccessSupport()

    /**
     * Property access is supported in the Groovy DSL.
     *
     * @since 0.1.0
     */
    public object GroovyPropertyAccess : PropertyAccessSupport(), GroovyCompatible

    /**
     * Property access is supported in the Kotlin DSL.
     *
     * @since 0.1.0
     */
    public object KotlinPropertyAccess : PropertyAccessSupport(), KotlinCompatible

    /**
     * Property access is supported in both the Groovy and Kotlin DSLs.
     *
     * @since 0.1.0
     */
    public object GroovyAndKotlinPropertyAccess : PropertyAccessSupport(), GroovyAndKotlinCompatible
  }

  /**
   * Whether this function call supports labels for its parameters.
   *
   * Labels will be supported in Kotlin if the function was re-written in the Kotlin DSL.
   *
   * @since 0.1.0
   */
  public sealed class LabelSupport : DslLanguageSettings {

    override fun toString(): String = javaClass.simpleName

    /**
     * No labels are supported.
     *
     * @since 0.1.0
     */
    public object NoLabels : LabelSupport()

    /**
     * Labels are supported in the Groovy DSL.
     *
     * @since 0.1.0
     */
    public object GroovyLabels : LabelSupport(), GroovyCompatible

    /**
     * Labels are supported in the Kotlin DSL.
     *
     * @since 0.1.0
     */
    public object KotlinLabels : LabelSupport(), KotlinCompatible

    /**
     * Labels are supported in both the Groovy and Kotlin DSLs.
     *
     * @since 0.1.0
     */
    public object GroovyAndKotlinLabels : LabelSupport(), GroovyAndKotlinCompatible
  }

  /**
   * Whether this function call supports infix.
   *
   * Infix *may* be supported in Kotlin if the function has a receiver
   * and only one parameter, and it was rewritten in the Kotlin DSL.
   *
   * @since 0.1.0
   */
  public sealed class InfixSupport : DslLanguageSettings {

    override fun toString(): String = javaClass.simpleName

    /**
     * No infix is supported.
     *
     * @since 0.1.0
     */
    public object NoInfix : InfixSupport()

    /**
     * Infix is supported in the Groovy DSL.
     *
     * @since 0.1.0
     */
    public object GroovyInfix : InfixSupport(), GroovyCompatible

    /**
     * Infix is supported in the Kotlin DSL.
     *
     * @since 0.1.0
     */
    public object KotlinInfix : InfixSupport(), KotlinCompatible

    /**
     * Infix is supported in both the Groovy and Kotlin DSLs.
     *
     * @since 0.1.0
     */
    public object GroovyAndKotlinInfix : InfixSupport(), GroovyAndKotlinCompatible
  }
}
