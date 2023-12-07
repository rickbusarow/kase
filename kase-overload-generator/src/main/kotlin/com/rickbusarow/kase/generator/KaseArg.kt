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

package com.rickbusarow.kase.generator

internal data class KaseArg(
  /** 1-21 */
  val number: Int,
  /** `a_` as in `a1` */
  val valuePrefix: String = "a",
  /** `A_` as in `A1` */
  val valueTypeNamePrefix: String = "A"
) {
  /** `a1` */
  val valueName = "$valuePrefix$number"

  /** `A1` */
  val valueTypeName = "$valueTypeNamePrefix$number"

  /** `a1Label` */
  val labelName = "${valueName}Label"

  /** `args1` */
  val iterableName = "args$number"

  /** `a1WithLabel` */
  val valueWithLabelName = "${valueName}WithLabel"

  /** `KaseParameterWithLabel<A1>` */
  val valueWithLabelTypeName = "KaseParameterWithLabel<$valueTypeName>"

  /**
   * ```
   * /** @see Kase1.ai */
   * public operator fun component1(): Ai = ai
   * ```
   */
  val componentFun = """
    /** @see Kase$number.$valueName */
    public operator fun component$number(): $valueTypeName = $valueName
  """.trimIndent()

  /**
   * ```
   * override operator fun component1(): Ai = ai
   * ```
   */
  val componentFunOverride =
    "override operator fun component$number(): $valueTypeName = $valueName"

  val ordinalSuffix by lazy { number.withOrdinalSuffix() }

  companion object {

    /** `parameter` or `parameters` */
    val List<KaseArg>.parametersPlural: String
      get() = if (size == 1) "parameter" else "parameters"

    /** [`a1`, `a2`, `a3`, ...] */
    val List<KaseArg>.valueNames: List<String>
      get() = map { it.valueName }

    /** [`A1`, `A2`, `A3`, ...] */
    val List<KaseArg>.valueTypes: List<String>
      get() = map { it.valueTypeName }

    /** `"A1, A2, A3"` */
    val List<KaseArg>.valueTypesString: String
      get() = valueTypes.joinToString(", ")

    /** `["a1" to "A1", "a2" to "A2", "a3" to "A3", ...]` */
    val List<KaseArg>.valueTypePairs: List<Pair<String, String>>
      get() = valueNames.zip(valueTypes)

    /** `["a1: A1", "a2: A2", "a3: A3", ...]` */
    val List<KaseArg>.params: List<String>
      get() = valueTypePairs.map { "${it.first}: ${it.second}" }

    /** `"a1: A1, a2: A2, a3: A3"` */
    val List<KaseArg>.paramsString: String
      get() = params.joinToString(", ")

    /** `"it.a1, it.a2, it.a3"` */
    val List<KaseArg>.valuesFromItString: String
      get() = valueNames.joinToString(", ") { arg -> "it.$arg" }

    /** `"a1 = a1, a2 = a2, a3 = a3"` */
    val List<KaseArg>.argsWithParamNames: String
      get() = valueNames.joinToString(", ") { arg -> "$arg = $arg" }

    /**
     * ```
     *   args1: Iterable<A1>,
     *   args2: Iterable<A2>
     * ```
     */
    val List<KaseArg>.argsIterableValueParams: String
      get() = joinToString(",\n  ") { arg ->
        "args${arg.number}: Iterable<${arg.valueTypeName}>"
      }

    /**
     * ```
     *   args1: Sequence<A1>,
     *   args2: Sequence<A2>
     * ```
     */
    val List<KaseArg>.argsSequenceValueParams: String
      get() = joinToString(",\n  ") { arg ->
        "args${arg.number}: Sequence<${arg.valueTypeName}>"
      }

    /**
     * ```
     *   override val a1: A1,
     *   override val a2: A2
     * ```
     */
    val List<KaseArg>.argsValueParams: String
      get() = joinToString(",\n  ") { arg ->
        "override val ${arg.valueName}: ${arg.valueTypeName}"
      }
  }
}
