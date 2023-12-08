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

package com.rickbusarow.kase.gradle.dsl.model

import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport
import com.rickbusarow.kase.gradle.DslStringFactory
import com.rickbusarow.kase.stdlib.indent
import dev.drewhamilton.poko.Poko
import kotlin.reflect.full.primaryConstructor

/**
 * A parameter to a function call, such as `group: "com.acme"` or `"com.acme"`.
 *
 * @since 0.1.0
 */
public interface Parameter : DslElement {
  /**
   * The label, such as `group` in `group: "com.acme"`.
   *
   * @since 0.1.0
   */
  public val label: String?

  /**
   * Creates a string representation of this [Parameter] in the given [language].
   *
   * @param language the [DslLanguage] to use when writing this [Parameter]
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @return the string representation of this [Parameter] in the given [language]
   * @since 0.1.0
   */
  public fun write(language: DslLanguage, labelSupport: LabelSupport): String

  override fun write(language: DslLanguage): String = write(language, LabelSupport.NoLabels)

  public companion object {
    /**
     * Joins a list of [Parameter]s into a [ParameterList].
     *
     * @since 0.1.0
     */
    public fun Iterable<Parameter>.join(
      separator: String = ParameterList.SEPARATOR_DEFAULT
    ): ParameterList = ParameterList(parameters = toList(), separator = separator)

    /**
     * Creates a [Parameter] with an optional label, such as `group: "com.acme"` or `"com.acme"`.
     *
     * @since 0.1.0
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
 * @property dslStringFactory the value, such as `"com.acme"`
 * @since 0.1.0
 */
@Poko
public class ValueParameter(
  override val label: String?,
  public val dslStringFactory: DslStringFactory
) : Parameter {
  public constructor(value: String) : this(
    label = null,
    dslStringFactory = DslStringFactory { value }
  )

  public constructor(label: String?, valueString: String) :
    this(label = label, dslStringFactory = DslStringFactory { valueString })

  public constructor(valueElement: DslElement) : this(
    label = null,
    dslStringFactory = DslStringFactory { valueElement.write(it) }
  )

  public constructor(label: String?, valueElement: DslElement) : this(
    label = label,
    dslStringFactory = DslStringFactory { valueElement.write(it) }
  )

  override fun write(language: DslLanguage, labelSupport: LabelSupport): String {
    val valueString = dslStringFactory.write(language)

    return label?.takeIf { language.useLabels && language.supports(labelSupport) }
      ?.let { labelValue -> "$labelValue${language.labelDelimiter}$valueString" }
      ?: valueString
  }
}

/**
 * A parameter which is a lambda, such as `exclude { group = "com.acme" }`.
 *
 * @property label the label, such as `exclude` in `exclude { group = "com.acme" }`
 * @param elements the list of [DslElement]s which make up the lambda
 * @since 0.1.0
 */
@Poko
public class LambdaParameter(
  override val label: String?,
  elements: MutableList<DslElement>
) : AbstractDslElementContainer<LambdaParameter>(elements = elements), Parameter {

  override fun write(language: DslLanguage): String = write(language, LabelSupport.NoLabels)

  override fun write(language: DslLanguage, labelSupport: LabelSupport): String {

    val elementString = buildString {

      appendLine("{")
      indent {
        for (element in elements) {
          when (element) {
            is Parameter -> appendLine(element.write(language, labelSupport))
            else -> appendLine(element.write(language))
          }
        }
      }
      append("}")
    }

    return label?.takeIf { language.supports(labelSupport) }
      ?.let { labelValue -> "$labelValue${language.labelDelimiter} $elementString" }
      ?: elementString
  }

  public companion object {

    /**
     * Creates a new [LambdaParameter] using [builder]
     *
     * @since 0.1.0
     */
    public operator fun <T : DslElementContainer<*>> invoke(
      label: String?,
      receiver: T,
      builder: T.() -> Unit
    ): LambdaParameter = LambdaParameter(
      label = label,
      elements = receiver.apply(builder).elements.toMutableList()
    )

    /**
     * Creates a new [LambdaParameter] using [builder]
     *
     * @since 0.1.0
     */
    public operator fun <T : DslElementContainer<*>> invoke(
      receiver: T,
      builder: T.() -> Unit
    ): LambdaParameter = LambdaParameter(
      label = null,
      elements = receiver.apply(builder).elements.toMutableList()
    )

    /**
     * Creates a new [LambdaParameter] using [builder]
     *
     * @since 0.1.0
     */
    public inline operator fun <reified T : DslElementContainer<*>> invoke(
      label: String?,
      noinline builder: T.() -> Unit
    ): LambdaParameter = invoke(
      label = label,
      receiver = requireNotNull(T::class.primaryConstructor?.call()),
      builder = builder
    )

    /**
     * Creates a new [LambdaParameter] using [builder]
     *
     * @since 0.1.0
     */
    public inline operator fun <reified T : DslElementContainer<*>> invoke(
      noinline builder: T.() -> Unit
    ): LambdaParameter = invoke(
      label = null,
      receiver = requireNotNull(T::class.primaryConstructor?.call()),
      builder = builder
    )
  }
}
