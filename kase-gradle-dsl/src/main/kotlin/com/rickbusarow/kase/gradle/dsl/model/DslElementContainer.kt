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

import com.rickbusarow.kase.KaseDsl
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.PropertyAccessSupport
import com.rickbusarow.kase.gradle.dsl.model.ValueAssignment.GradlePropertyAssignment
import com.rickbusarow.kase.gradle.dsl.model.ValueAssignment.SetterAssignment

/**
 * Collects [DslElement]s, to be written to a [DslLanguage]
 * file. Elements are written in the order they are added.
 *
 * @since 0.1.0
 */
@KaseDsl
public interface DslElementContainer<SELF : DslElementContainer<SELF>> : DslElement {

  /**
   * The list of [DslElement]s in this container.
   *
   * @since 0.1.0
   */
  public val elements: List<DslElement>

  /**
   * Adds a new [DslElement] to the DSL.
   *
   * @param element the element to add
   * @since 0.1.0
   */
  public fun addElement(element: DslElement): SELF

  /**
   * Adds new [DslElement]s to the DSL.
   *
   * @since 0.1.0
   */
  public fun addAllElements(vararg elements: DslElement): SELF

  /**
   * Adds new [DslElement]s to the DSL.
   *
   * @since 0.1.0
   */
  public fun addAllElements(elements: Collection<DslElement>): SELF

  /**
   * Adds a blank line to the DSL.
   *
   * @since 0.1.0
   */
  public fun addBlankLine(): SELF

  /**
   * Wraps a String literal in language-specific quotes
   *
   * @param stringValue the String value to be quoted.
   * @param useDoubleQuotes whether to use double quotes for strings. Defaults to
   *   `null` so that the setting is inherited from the language configuration.
   * @since 0.1.0
   */
  public fun stringLiteral(
    stringValue: String,
    useDoubleQuotes: Boolean? = null
  ): StringLiteral = StringLiteral(
    value = stringValue,
    useDoubleQuotes = useDoubleQuotes
  )

  /**
   * @param useDoubleQuotes whether to use double quotes for strings. Defaults to
   *   `null` so that the setting is inherited from the language configuration.
   * @receiver the String value to be quoted.
   * @return a [StringLiteral] representing the quoted string
   * @since 0.1.0
   */
  public fun String.asStringLiteral(
    useDoubleQuotes: Boolean? = null
  ): StringLiteral = stringLiteral(
    stringValue = this,
    useDoubleQuotes = useDoubleQuotes
  )

  /**
   * Adds some exact text to be written to the DSL, without modification.
   *
   * @since 0.1.0
   */
  public fun raw(value: String): SELF = addElement(RawLiteral(value = value))

  /**
   * Adds a new [FunctionCall] to the DSL.
   *
   * @param name the name of the function, such as `exclude`
   * @param parameters the list of parameters to pass to the function
   * @since 0.1.0
   */
  public fun functionCall(
    name: String,
    vararg parameters: Parameter?
  ): SELF = addElement(
    FunctionCall(
      name = name,
      labelSupport = LabelSupport.NoLabels,
      infixSupport = InfixSupport.NoInfix,
      parameters = parameters.filterNotNull()
    )
  )

  /**
   * Adds a new [FunctionCall] to the DSL.
   *
   * @param name the name of the function, such as `exclude`
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @param infixSupport whether to use infix in the function call, such as `foo t`
   * @param parameters the list of parameters to pass to the function
   * @since 0.1.0
   */
  public fun functionCall(
    name: String,
    labelSupport: LabelSupport,
    infixSupport: InfixSupport,
    vararg parameters: Parameter?
  ): SELF = addElement(
    FunctionCall(
      name = name,
      labelSupport = labelSupport,
      infixSupport = infixSupport,
      parameters = parameters.filterNotNull()
    )
  )

  /**
   * Adds a new [DecidingDslElement] to the DSL.
   *
   * @param kotlinElement invoked for the Kotlin DSL
   * @param groovyElement invoked for the Groovy DSL
   * @since 0.1.0
   */
  public fun <T : DslElement> deciding(
    kotlinElement: () -> T,
    groovyElement: () -> T
  ): SELF = addElement(
    DecidingDslElement(
      kotlinElement = kotlinElement,
      groovyElement = groovyElement
    )
  )

  /**
   * Adds a new [FunctionCall] to the DSL.
   *
   * @param name the name of the function, such as `exclude`
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @param infixSupport whether to use infix in the function call, such as `foo t`
   * @param parameterList the parameters to pass to the function
   * @since 0.1.0
   */
  public fun functionCall(
    name: String,
    labelSupport: LabelSupport,
    infixSupport: InfixSupport,
    parameterList: ParameterList
  ): SELF = addElement(
    FunctionCall(
      name = name,
      labelSupport = labelSupport,
      infixSupport = infixSupport,
      parameterList = parameterList
    )
  )

  /**
   * Adds a new [SetterFunctionCall] to the DSL.
   *
   * @param propertyName the name of the "property" if property
   *   access is used, like "foo" for a `setFoo(...)` function
   * @param parameter the new value to be set
   * @param propertyAccessSupport whether to use property
   *   access in the function call, such as `foo = t`
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @param infixSupport whether to use infix in the function call, such as `foo t`
   * @since 0.1.0
   */
  public fun setterFunctionCall(
    propertyName: String,
    parameter: ValueParameter,
    propertyAccessSupport: PropertyAccessSupport,
    labelSupport: LabelSupport,
    infixSupport: InfixSupport
  ): SELF = addElement(
    SetterFunctionCall(
      propertyName = propertyName,
      propertyAccessSupport = propertyAccessSupport,
      labelSupport = labelSupport,
      infixSupport = infixSupport,
      parameter = parameter
    )
  )

  /**
   * Adds a new [Gradle Property Assignment][GradlePropertyAssignment] to the DSL.
   *
   * ```
   * myDsl {
   *   group.set("com.acme")
   * }
   * ```
   *
   * @param propertyName the name of the property, such as `group`
   * @param value the value of the property, such as `com.acme`
   * @since 0.1.0
   */
  public fun assignGradleProperty(
    propertyName: String,
    value: String
  ): SELF = addElement(GradlePropertyAssignment(name = propertyName, value = value))

  /**
   * Adds a new [Gradle Property Assignment][GradlePropertyAssignment] to the DSL.
   *
   * Use `set(string(...))` to add a string value wrapped in quotes.
   *
   * given:
   * ```
   * // builder DSL
   * myConfig {
   *   "group".set(GROUP)
   * }
   * ```
   *
   * ```
   * myDsl {
   *   group.set("com.acme")
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun String.set(value: String): SELF {
    return addElement(GradlePropertyAssignment(name = this, value = value))
  }

  /**
   * Adds a new [Gradle Property Assignment][GradlePropertyAssignment] to the DSL.
   *
   * given:
   * ```
   * // builder DSL
   * myConfig {
   *   "group".set(string("com.acme"))
   * }
   * ```
   *
   * ```
   * myDsl {
   *   group.set("com.acme")
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun String.set(value: DslElement): SELF {
    return addElement(GradlePropertyAssignment(name = this, dslStringFactory = value))
  }

  /**
   * Adds a new [SetterAssignment] to the DSL.
   *
   * given:
   * ```
   * // builder DSL
   * myConfig {
   *   "group" setEquals GROUP
   * }
   * ```
   *
   * ```
   * myDsl {
   *   group = GROUP
   * }
   * ```
   *
   * @since 0.1.0
   */
  public infix fun String.setEquals(value: String): SELF {
    return addElement(SetterAssignment(name = this, value = value))
  }

  /**
   * Adds a new [SetterAssignment] to the DSL.
   *
   * given:
   * ```
   * // builder DSL
   * myConfig {
   *   "group" setEquals string("com.acme")
   * }
   * ```
   *
   * ```
   * myDsl {
   *   group = "com.acme"
   * }
   * ```
   *
   * @since 0.1.0
   */
  public infix fun String.setEquals(value: DslElement): SELF {
    return addElement(SetterAssignment(name = this, dslStringFactory = value))
  }
}

internal class SimpleDslElementContainer(
  elements: MutableList<DslElement> = mutableListOf()
) : AbstractDslElementContainer<SimpleDslElementContainer>(elements)
