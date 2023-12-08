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

package com.rickbusarow.kase.gradle.dsl.model

import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.NoInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.DslLanguageSettings.PropertyAccessSupport
import dev.drewhamilton.poko.Poko

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
 * @since 0.1.0
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
        dslStringFactory = parameter
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
 * @since 0.1.0
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
}

/**
 * Wraps [content] in parentheses if it's necessary for this syntax, or
 * if this language is [GroovyDsl] and [GroovyDsl.useInfix] is `true`.
 *
 * @param content the content to parenthesize, e.g. `exclude group: "com.acme", module: "rocket"`
 * @param infixSupport overrides the default behavior of
 *   the language's [useInfix][DslLanguage.useInfix] setting
 * @return the parenthesized content, e.g. `(exclude group: "com.acme", module: "rocket")`
 * @since 0.1.0
 */
public fun DslLanguage.parens(content: DslElement, infixSupport: InfixSupport? = null): String {
  return parens(content.write(this), infixSupport)
}
