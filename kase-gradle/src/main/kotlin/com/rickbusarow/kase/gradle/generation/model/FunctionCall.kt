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

package com.rickbusarow.kase.gradle.generation.model

import com.rickbusarow.kase.gradle.generation.model.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.generation.model.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.InfixSupport
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.InfixSupport.NoInfix
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.PropertyAccessSupport
import com.rickbusarow.kase.gradle.generation.model.LanguageSpecific.GroovyAndKotlinCompatible
import com.rickbusarow.kase.gradle.generation.model.LanguageSpecific.GroovyCompatible
import com.rickbusarow.kase.gradle.generation.model.LanguageSpecific.KotlinCompatible
import dev.drewhamilton.poko.Poko

/** Allows for a different [DslElement] to be used depending on the [DslLanguage]. */
public class DecidingDslElement<T : DslElement>(
  private val kotlinElement: () -> T,
  private val groovyElement: () -> T
) : DslElement {
  override fun write(language: DslLanguage): String {
    return when (language) {
      is GroovyDsl -> groovyElement().write(language)
      is KotlinDsl -> kotlinElement().write(language)
    }
  }
}

/**
 * *Attempts to* model the special behaviors around how Kotlin and
 * Groovy handle `set___(...)` functions that were defined in Java.
 *
 * ### In Kotlin
 * - You can always still use `setFoo(...)` in Kotlin
 * - a `setFoo(T t)` function will be rewritten to support
 *   property access (`foo = t`) if `t` is assignable to `foo`
 * - a `setFoo(T t)` function will stay `setFoo(t: T)` if `t` is not assignable to `foo` (if it
 *   needs some sort of transformation before setting, like setting an `Int` to a `String`)
 *
 * ### In Groovy
 * - You can always still use `setFoo(...)` in Groovy
 * - You can use `foo = t` in Groovy if `t` is directly assignable to `foo`
 * - You can use the infix `foo t` function in Groovy if `t` is directly assignable to `foo`
 *   - This also supports labels, e.g. `foo bar: t`
 *
 * @property propertyName the name of the "property" if property
 *   access is used, like "foo" for a `setFoo(...)` function
 * @property propertyAccessSupport whether to use property
 *   access in the function call, such as `foo = t`
 * @property labelSupport whether to use labels in the function call, such as `group = "com.acme"`
 * @property infixSupport whether to use infix in the function call, such as `foo t`
 * @property parameter the new value to be set
 */
@Poko
public class SetterFunctionCall public constructor(
  public val propertyName: String,
  public val propertyAccessSupport: PropertyAccessSupport,
  public val labelSupport: LabelSupport,
  public val infixSupport: InfixSupport,
  public val parameter: ValueParameter
) : DslElement {
  override fun write(language: DslLanguage): String {

    return when {
      language.useInfix &&
        language.supports(infixSupport) &&
        language.supports(propertyAccessSupport) -> FunctionCall(
        name = propertyName,
        labelSupport = NoLabels,
        infixSupport = infixSupport,
        parameterList = ParameterList(parameter)
      ).write(language)

      language.supports(propertyAccessSupport) -> ValueAssignment.SetterAssignment(
        name = propertyName,
        value = parameter
      ).write(language)

      else -> FunctionCall(
        name = "set$propertyName",
        labelSupport = labelSupport,
        infixSupport = infixSupport,
        parameterList = ParameterList(parameter)
      ).write(language)
    }
  }
}

/**
 * A function call
 *
 * @property name the name of the function, such as `exclude`
 * @property labelSupport whether to use labels in the function call, such as `group = "com.acme"`
 * @property infixSupport whether to use infix in the function call, such as `foo t`
 * @property parameterList the list of parameters to pass to the function
 */
@Poko
public class FunctionCall(
  public val name: String,
  public val labelSupport: LabelSupport,
  public val infixSupport: InfixSupport,
  public val parameterList: ParameterList
) : DslElement {

  public constructor(
    name: String,
    labelSupport: LabelSupport,
    infixSupport: InfixSupport,
    vararg parameters: Parameter?
  ) : this(
    name = name,
    labelSupport = labelSupport,
    infixSupport = infixSupport,
    parameterList = ParameterList(parameters.filterNotNull())
  )

  public constructor(
    name: String,
    parameters: List<Parameter>
  ) : this(
    name = name,
    labelSupport = NoLabels,
    infixSupport = NoInfix,
    parameterList = ParameterList(parameters)
  )

  public constructor(
    name: String,
    labelSupport: LabelSupport,
    infixSupport: InfixSupport,
    parameters: List<Parameter>
  ) : this(
    name = name,
    labelSupport = labelSupport,
    infixSupport = infixSupport,
    parameterList = ParameterList(parameters)
  )

  public constructor(
    name: String,
    vararg parameters: Parameter?
  ) : this(
    name = name,
    labelSupport = NoLabels,
    infixSupport = NoInfix,
    parameters = parameters.filterNotNull()
  )

  public constructor(
    name: String,
    vararg parameters: String
  ) : this(
    name = name,
    parameters = parameters.map { Parameter(it) },
    labelSupport = NoLabels,
    infixSupport = NoInfix
  )

  override fun write(language: DslLanguage): String {
    return language.write { "$name${parameterList.write(language, labelSupport, infixSupport)}" }
  }

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
  public sealed class PropertyAccessSupport : LanguageSpecific {

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
  public sealed class LabelSupport : LanguageSpecific {

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
  public sealed class InfixSupport : LanguageSpecific {

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
