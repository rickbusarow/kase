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

import com.rickbusarow.kase.gradle.generation.model.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.InfixSupport
import com.rickbusarow.kase.stdlib.indent
import dev.drewhamilton.poko.Poko
import kotlin.LazyThreadSafetyMode.NONE

/** A list of [Parameter]s. */
@Poko
public class ParameterList(
  private val parameters: List<Parameter>,
  private val separator: String = SEPARATOR_DEFAULT
) : DslElement {

  public constructor(
    vararg parameters: Parameter,
    separator: String = SEPARATOR_DEFAULT
  ) : this(
    parameters.toList(),
    separator
  )

  /** The number of parameters in this list. */
  public val size: Int get() = parameters.size

  /** True if the last parameter is a [LambdaParameter], otherwise false. */
  public val hasTrailingLambda: Boolean by lazy(NONE) {
    parameters.lastOrNull() is LambdaParameter
  }

  /** @return `true` if [size] is zero, otherwise `false`. */
  public fun isEmpty(): Boolean = parameters.isEmpty()

  /** @return `true` if [size] is greater than zero, otherwise `false`. */
  public fun isNotEmpty(): Boolean = parameters.isNotEmpty()

  /**
   * Creates a string representation of this [ParameterList] in the given [language].
   *
   * @param language the [DslLanguage] to use when writing this [ParameterList]
   * @param labelSupport whether to use labels in the function call, such as `group = "com.acme"`
   * @param infixSupport whether the associated function call supports infix notation
   * @return the string representation of this [ParameterList] in the given [language]
   */
  public fun write(
    language: DslLanguage,
    labelSupport: FunctionCall.LabelSupport,
    infixSupport: FunctionCall.InfixSupport
  ): String {

    val trailingLambda = parameters.lastOrNull()
      ?.takeIf { it is LambdaParameter }

    val paramsWithoutLambda = when {
      trailingLambda != null -> parameters.dropLast(1)
      else -> parameters
    }

    val joined = buildString {
      indent("", "  ") {
        append(
          paramsWithoutLambda.joinToString(separator = separator) {
            it.write(language, labelSupport)
          }
        )
      }
    }

    val wrapInParens = when {
      joined.lines().size > 1 -> true
      // If there are no parameters, it can't be infix
      parameters.isEmpty() -> true
      // Kotlin only supports infix for a single parameter.  Technically Groovy does too,
      // but things get weird when that parameter is a map.
      language is KotlinDsl && parameters.size > 1 -> true
      // If the lambda isn't null, any parameters before it must be wrapped in parens.
      trailingLambda != null -> paramsWithoutLambda.isNotEmpty()
      else -> !language.useInfix || !language.supports(infixSupport)
    }

    val maybeWrapped = when {
      wrapInParens -> language.parens(joined, InfixSupport.NoInfix)
      paramsWithoutLambda.isNotEmpty() -> " $joined"
      else -> joined
    }

    val lambdaString = trailingLambda?.let { " ${it.write(language, labelSupport)}" }.orEmpty()

    return "$maybeWrapped$lambdaString"
  }

  override fun write(language: DslLanguage): String = write(
    language,
    FunctionCall.LabelSupport.NoLabels,
    FunctionCall.InfixSupport.NoInfix
  )

  public companion object {
    /** The default separator used when joining [Parameter]s into a [ParameterList]. */
    public const val SEPARATOR_DEFAULT: String = ", "
  }
}
