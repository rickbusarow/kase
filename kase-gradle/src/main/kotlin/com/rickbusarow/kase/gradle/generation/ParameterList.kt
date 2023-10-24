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

import dev.drewhamilton.poko.Poko

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
  public fun write(language: DslLanguage, labelSupport: FunctionCall.LabelSupport): String {

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

    val lambdaString = trailingLambda?.let { " ${it.write(language, labelSupport)}" }.orEmpty()

    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("       parameters: $parameters")
    println("   trailingLambda: $trailingLambda")
    println("           toJoin: $toJoin")
    println("      toJoin size: ${toJoin.size}")
    println(
      "     wrapInParens: $wrapInParens   (${toJoin.isNotEmpty()} || ${trailingLambda == null})"
    )
    println("     maybeWrapped: $maybeWrapped")
    println("     lambdaString: $lambdaString")
    println("           joined: `$joined`")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

    return "$maybeWrapped$lambdaString"
  }

  override fun write(language: DslLanguage): String = write(
    language,
    FunctionCall.LabelSupport.NONE
  )

  public companion object {
    /** The default separator used when joining [Parameter]s into a [ParameterList]. */
    public const val SEPARATOR_DEFAULT: String = ", "
  }
}
