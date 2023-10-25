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

import com.rickbusarow.kase.gradle.generation.ValueAssignment.GradlePropertyAssignment
import com.rickbusarow.kase.gradle.generation.ValueAssignment.SetterAssignment
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport

/** */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
public annotation class DslElementContainerMarker

/**
 * Collects [DslElement]s, to be written to a [DslLanguage]
 * file. Elements are written in the order they are added.
 */
@DslElementContainerMarker
public interface DslElementContainer<SELF : DslElementContainer<SELF>> : DslElement {

  /** The list of [DslElement]s in this container. */
  public val elements: List<DslElement>

  /**
   * Adds a new [DslElement] to the DSL.
   *
   * @param element the element to add
   */
  public fun addElement(element: DslElement): SELF

  /** Adds new [DslElement]s to the DSL. */
  public fun addAllElements(vararg elements: DslElement): SELF

  /** Adds new [DslElement]s to the DSL. */
  public fun addAllElements(elements: Collection<DslElement>): SELF

  /** Adds a blank line to the DSL. */
  public fun addBlankLine(): SELF

  /**
   * Wraps a String literal in language-specific quotes
   *
   * @param stringValue the String value to be quoted.
   * @param useDoubleQuotes whether to use double quotes for strings, even when single
   *   quotes are valid. e.g. `project(":myProject")` instead of `project(':myProject')`
   * @see string as another code-completion friendly alias for this function
   */
  @Deprecated(
    "Renamed to stringLiteral",
    ReplaceWith("stringLiteral(stringValue, useDoubleQuotes)")
  )
  public fun quoted(
    stringValue: String,
    useDoubleQuotes: Boolean = false
  ): StringLiteral = stringLiteral(
    stringValue = stringValue,
    useDoubleQuotes = useDoubleQuotes
  )

  /**
   * Wraps a String literal in language-specific quotes
   *
   * @param stringValue the String value to be quoted.
   * @param useDoubleQuotes whether to use double quotes for strings, even when single
   *   quotes are valid. e.g. `project(":myProject")` instead of `project(':myProject')`
   * @see quoted as another code-completion friendly alias for this function
   */
  @Deprecated(
    "Renamed to stringLiteral",
    ReplaceWith("stringLiteral(stringValue, useDoubleQuotes)")
  )
  public fun string(
    stringValue: String,
    useDoubleQuotes: Boolean = false
  ): StringLiteral = stringLiteral(
    stringValue = stringValue,
    useDoubleQuotes = useDoubleQuotes
  )

  /**
   * Wraps a String literal in language-specific quotes
   *
   * @param stringValue the String value to be quoted.
   * @param useDoubleQuotes whether to use double quotes for strings. Defaults to
   *   `null` so that the setting is inherited from the language configuration.
   * @see quoted as another code-completion friendly alias for this function
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
   */
  public fun String.asStringLiteral(
    useDoubleQuotes: Boolean? = null
  ): StringLiteral = stringLiteral(
    stringValue = this,
    useDoubleQuotes = useDoubleQuotes
  )

  /**
   * Adds a new [FunctionCall] to the DSL.
   *
   * @param name the name of the function, such as `exclude`
   * @param parameters the list of parameters to pass to the function
   */
  public fun functionCall(
    name: String,
    vararg parameters: Parameter
  ): SELF = addElement(
    FunctionCall(
      name = name,
      labelSupport = LabelSupport.NoLabels,
      parameters = parameters.toList()
    )
  )

  /**
   * Adds a new [FunctionCall] to the DSL.
   *
   * @param name the name of the function, such as `exclude`
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @param parameters the list of parameters to pass to the function
   */
  public fun functionCall(
    name: String,
    labelSupport: LabelSupport,
    vararg parameters: Parameter
  ): SELF = addElement(
    FunctionCall(
      name = name,
      labelSupport = labelSupport,
      parameters = parameters.toList()
    )
  )

  /**
   * Adds a new [FunctionCall] to the DSL.
   *
   * @param name the name of the function, such as `exclude`
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @param parameterList the parameters to pass to the function
   */
  public fun functionCall(
    name: String,
    labelSupport: LabelSupport,
    parameterList: ParameterList
  ): SELF = addElement(
    FunctionCall(
      name = name,
      parameterList = parameterList,
      labelSupport = labelSupport
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
   */
  public fun String.set(value: DslElement): SELF {
    return addElement(GradlePropertyAssignment(name = this, value = value))
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
   */
  public infix fun String.setEquals(value: DslElement): SELF {
    return addElement(SetterAssignment(name = this, value = value))
  }
}

/**
 * Adds a new [FunctionCall] to the DSL.
 *
 * @param name the name of the function, such as `exclude`
 * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
 * @param parameters the list of parameters to pass to the function
 */
internal fun <SELF : DslElementContainer<SELF>> DslElementContainer<SELF>.functionCall(
  name: String,
  labelSupport: LabelSupport,
  vararg parameters: Parameter?
): SELF = addElement(
  FunctionCall(
    name = name,
    labelSupport = labelSupport,
    parameters = listOfNotNull(*parameters)
  )
)

internal class SimpleDslElementContainer(
  elements: MutableList<DslElement> = mutableListOf()
) : AbstractDslElementContainer<SimpleDslElementContainer>(elements)
