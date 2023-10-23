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

@file:Suppress("MemberVisibilityCanBePrivate")

package com.rickbusarow.kase.gradle.generation

import com.rickbusarow.kase.gradle.generation.FunctionCall.LabelSupport
import com.rickbusarow.kase.stdlib.indent
import dev.drewhamilton.poko.Poko
import kotlin.reflect.full.primaryConstructor

/** A parameter to a function call, such as `group: "com.acme"` or `"com.acme"`. */
public interface Parameter : DslElement {
  /** The label, such as `group` in `group: "com.acme"`. */
  public val label: String?

  /**
   * Creates a string representation of this [Parameter] in the given [language].
   *
   * @param language the [DslLanguage] to use when writing this [Parameter]
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @return the string representation of this [Parameter] in the given [language]
   */
  public fun write(language: DslLanguage, labelSupport: LabelSupport): String

  override fun write(language: DslLanguage): String = write(language, LabelSupport.BOTH)

  public companion object {
    /** Joins a list of [Parameter]s into a [ParameterList]. */
    public fun Iterable<Parameter>.join(
      separator: String = ParameterList.SEPARATOR_DEFAULT
    ): ParameterList = ParameterList(parameters = toList(), separator = separator)

    /**
     * Creates a [Parameter] with an optional label, such as `group: "com.acme"` or `"com.acme"`.
     */
    public operator fun invoke(value: String): ValueParameter {
      return ValueParameter(label = null, valueString = value)
    }
  }
}

/**
 * A parameter with an optional label, such as `group: "com.acme"` or `"com.acme"`.
 *
 * @property label the label, such as `group` in `group: "com.acme"`
 * @property valueString the value, such as `"com.acme"`
 */
@Poko
public class ValueParameter(
  override val label: String?,
  public val valueString: (DslLanguage) -> String
) : Parameter {
  public constructor(value: String) : this(label = null, valueString = { value })
  public constructor(label: String?, valueString: String) :
    this(label = label, valueString = {
      valueString
    })

  public constructor(valueElement: DslElement) : this(
    label = null,
    valueString = { valueElement.write(it) }
  )

  public constructor(label: String?, valueElement: DslElement) : this(
    label = label,
    valueString = { valueElement.write(it) }
  )

  override fun write(language: DslLanguage, labelSupport: LabelSupport): String {
    val valueString = valueString(language)
    return label?.takeIf { language.supports(labelSupport) }
      ?.let { labelValue -> "$labelValue${language.labelDelimiter} $valueString" }
      ?: valueString
  }
}

/**
 * A parameter which is a lambda, such as `exclude { group = "com.acme" }`.
 *
 * @property label the label, such as `exclude` in `exclude { group = "com.acme" }`
 * @param elements the list of [DslElement]s which make up the lambda
 */
@Poko
public class LambdaParameter(
  override val label: String?,
  elements: MutableList<DslElement> = mutableListOf()
) : DslElementContainer(elements = elements), Parameter {

  public constructor(elements: MutableList<DslElement>) : this(
    label = null,
    elements = elements
  )

  override fun write(language: DslLanguage): String = buildString {
    appendLine("{")
    indent {
      for (element in elements) {
        appendLine(element.write(language))
      }
    }
    append("}")
  }

  override fun write(language: DslLanguage, labelSupport: LabelSupport): String {

    val elementString = write(language)

    return label?.takeIf { language.supports(labelSupport) }
      ?.let { labelValue -> "$labelValue${language.labelDelimiter} $elementString" }
      ?: elementString
  }

  public companion object {
    /** Creates a new [LambdaParameter] using [builder] */
    public operator fun <T : DslElementContainer> invoke(
      receiver: T,
      builder: T.() -> Unit
    ): LambdaParameter = LambdaParameter(
      label = null,
      elements = receiver.apply(builder).elements.toMutableList()
    )

    /** Creates a new [LambdaParameter] using [builder] */
    public inline operator fun <reified T : DslElementContainer> invoke(
      label: String?,
      builder: T.() -> Unit
    ): LambdaParameter {
      val receiver = T::class.primaryConstructor!!.call()
      return LambdaParameter(label = label, receiver.apply(builder).elements.toMutableList())
    }

    /** Creates a new [LambdaParameter] using [builder] */
    public operator fun <T : DslElementContainer> invoke(
      label: String?,
      receiver: T,
      builder: T.() -> Unit
    ): LambdaParameter = LambdaParameter(
      label = label,
      elements = receiver.apply(builder).elements.toMutableList()
    )
  }
}

/** A list of [Parameter]s. */
@Poko
public class ParameterList(
  private val parameters: List<Parameter>,
  private val separator: String = SEPARATOR_DEFAULT
) : DslElement {

  /** The number of parameters in this list. */
  public val size: Int get() = parameters.size

  /** @return `true` if [size] is zero, otherwise `false`. */
  public fun isEmpty(): Boolean = parameters.isEmpty()

  /** @return `true` if [size] is greater than zero, otherwise `false`. */
  public fun isNotEmpty(): Boolean = parameters.isNotEmpty()

  /**
   * Creates a string representation of this [ParameterList] in the given [language].
   *
   * @param language the [DslLanguage] to use when writing this [ParameterList]
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @return the string representation of this [ParameterList] in the given [language]
   */
  public fun write(language: DslLanguage, labelSupport: LabelSupport): String {

    val trailingLambda = parameters.lastOrNull()
      ?.takeIf { it is LambdaParameter }

    val toJoin = when {
      trailingLambda != null -> parameters.dropLast(1)
      else -> parameters
    }

    val joined = toJoin.joinToString(separator = separator) {
      it.write(language = language, labelSupport = labelSupport)
    }

    val wrapInParens = toJoin.isNotEmpty() || trailingLambda == null

    val maybeWrapped = if (wrapInParens) {
      language.parens(joined)
    } else {
      joined
    }

    val lambdaString = trailingLambda?.let { " ${it.write(language)}" } ?: ""

    return "$maybeWrapped$lambdaString"
  }

  override fun write(language: DslLanguage): String = write(language, LabelSupport.NONE)

  public companion object {
    /** The default separator used when joining [Parameter]s into a [ParameterList]. */
    public const val SEPARATOR_DEFAULT: String = ", "
  }
}
