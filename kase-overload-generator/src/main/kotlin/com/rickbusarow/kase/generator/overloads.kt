/*
 * Copyright (C) 2024 Rick Busarow
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

@file:Suppress("FunctionName")

package com.rickbusarow.kase.generator

import com.rickbusarow.kase.generator.KaseArg.Companion.argsIterableValueParams
import com.rickbusarow.kase.generator.KaseArg.Companion.argsSequenceValueParams
import com.rickbusarow.kase.generator.KaseArg.Companion.argsValueParams
import com.rickbusarow.kase.generator.KaseArg.Companion.argsWithParamNames
import com.rickbusarow.kase.generator.KaseArg.Companion.parametersPlural
import com.rickbusarow.kase.generator.KaseArg.Companion.paramsString
import com.rickbusarow.kase.generator.KaseArg.Companion.valueNames
import com.rickbusarow.kase.generator.KaseArg.Companion.valueTypesString
import com.rickbusarow.kase.generator.KaseArg.Companion.valuesFromItString
import com.rickbusarow.kase.generator.Names.kaseTestBuilderDsl

internal fun StringBuilder.kaseInterface(
  args: List<KaseArg>,
  types: KaseTypes
) {
  val lastArg = args.last()

  appendLine(
    """
    |/**
    | * A strongly typed version of [Kase] for ${args.size} ${args.parametersPlural}.
    | *
    | * @since 0.1.0
    | */
    |public interface ${types.kaseInterfaceVariance} : ${types.kaseSuperInterface} {
    |
    |  /** The ${types.number.withOrdinalSuffix()} parameter. */
    |  public val ${lastArg.valueName}: ${lastArg.valueTypeName}
    |
    |  ${lastArg.componentFun.prependContinuationIndent("  ")}
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.defaultKase(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val componentFuns = args.joinToString("\n  ") { arg ->
    arg.componentFunOverride
  }

  appendLine(
    """
    |@Poko
    |@PublishedApi
    |internal class ${types.defaultKaseVariance}(
    |  ${args.argsValueParams},
    |  private val displayNameFactory: ${types.displayNameFactory}
    |) : ${types.kaseInterface}, KaseInternal {
    |
    |  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    |    with(displayNameFactory) { createDisplayName() }
    |  }
    |
    |  $componentFuns
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.matrixAccessor(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kdoc = buildList {
    add(
      "Returns a list of [${types.kaseInterfaceNoTypes}]s from this [KaseMatrix] for the given keys."
    )
    add("")
    for (arg in args) {
      add("@param ${arg.valueName}Key the key for the ${arg.ordinalSuffix} parameter.")
    }
    add("@param displayNameFactory defines the name used in test environments and dynamic tests")
    add(
      "@return a list of [${types.kaseInterfaceNoTypes}]s from this [KaseMatrix] for the given keys."
    )
    add("@since 0.5.0")
  }
    .makeKdoc()

  val typeParams = args.joinToString(separator = ",\n  ") {
    "reified ${it.valueTypeName} : KaseMatrixElement<*>"
  }

  val params = args.joinToString(separator = ",\n  ") {
    "${it.valueName}Key: KaseMatrixKey<${it.valueTypeName}>"
  }

  val factoryDefault = args.joinToString(" | ", "\"", "\"") {
    "${'$'}{${it.valueName}.label}: ${'$'}{${it.valueName}.value}"
  }

  val forLoops = forLoops(
    args = args,
    builderName = "buildList",
    accessor = { "get(${valueName}Key)" }
  ) {
    "add(kase(${args.argsWithParamNames}, displayNameFactory = displayNameFactory))"
  }

  appendLine(
    """
    |$kdoc
    |public inline fun <
    |  $typeParams
    |> KaseMatrix.kases(
    |  $params,
    |  displayNameFactory: ${types.displayNameFactory} = KaseDisplayNameFactory {
    |    $factoryDefault
    |  }
    |): List<${types.kaseInterface}> {
    |  return $forLoops
    |}
    |
    """.trimMargin()
  )
}

internal fun StringBuilder.matrixAccessorFactory(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kdoc = buildList {
    add(
      "Returns a list of [${types.kaseInterfaceNoTypes}]s from this [KaseMatrix] for the given keys."
    )
    add("")
    for (arg in args) {
      add("@param ${arg.valueName}Key the key for the ${arg.ordinalSuffix} parameter.")
    }
    add("@param instanceFactory creates a custom Kase instance for each permutation")
    add(
      "@return a list of [${types.kaseInterfaceNoTypes}]s from this [KaseMatrix] for the given keys."
    )
    add("@since 0.5.0")
  }
    .makeKdoc()

  val typeParams = args.joinToString(separator = ",\n  ") {
    "reified ${it.valueTypeName} : KaseMatrixElement<*>"
  }

  val params = args.joinToString(separator = ",\n  ") {
    "${it.valueName}Key: KaseMatrixKey<${it.valueTypeName}>"
  }

  val forLoops = forLoops(
    args = args,
    builderName = "buildList",
    accessor = { "get(${valueName}Key)" }
  ) {
    "add(instanceFactory(${args.joinToString(", ") { it.valueName }}))"
  }

  appendLine(
    """
    |$kdoc
    |public inline fun <
    |  $typeParams,
    |  T : ${types.kaseInterface}
    |> KaseMatrix.get(
    |  $params,
    |  instanceFactory: (${args.valueTypesString}) -> T
    |): List<T> {
    |  return $forLoops
    |}
    |
    """.trimMargin()
  )
}

internal fun StringBuilder.defaultKaseDisplayNameFactory(
  args: List<KaseArg>,
  types: KaseTypes
) {
  appendLine(
    """
    |
    |private fun <${args.valueTypesString}> ${types.defaultDisplayNameFactory}(): ${types.displayNameFactory} {
    |  return KaseDisplayNameFactory {
    |    "${args.valueNames.joinToString(" | ") { "$it: $$it" }}"
    |  }
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.kase_displayNameFactory(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kdoc = buildList {
    add("Creates a new [Kase] with the given ${args.parametersPlural}.")
    add("")
    for (arg in args) {
      add("@param ${arg.valueName} the [${types.kaseInterfaceNoTypes}.${arg.valueName}] parameter.")
    }
    add("@param displayNameFactory defines the name used in test environments and dynamic tests")
    add("@since 0.1.0")
  }
    .makeKdoc()

  appendLine(
    """
    |$kdoc
    |public fun <${args.valueTypesString}> kase(
    |  ${args.paramsString},
    |  displayNameFactory: ${types.displayNameFactory} = ${types.defaultDisplayNameFactory}()
    |): ${types.kaseInterface} = ${types.defaultKaseNoTypes}(
    |  ${args.argsWithParamNames},
    |  displayNameFactory = displayNameFactory
    |)
    """.trimMargin()
  )
}

internal fun StringBuilder.kase_displayName(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kdoc = buildList {
    add("Creates a new [Kase] with the given ${args.parametersPlural}.")
    add("")
    add("@param displayName the name used in test environments and dynamic tests")
    for (arg in args) {
      add("@param ${arg.valueName} the [${types.kaseInterfaceNoTypes}.${arg.valueName}] parameter.")
    }
    add("@since 0.1.0")
  }
    .makeKdoc()

  appendLine(
    """
    |$kdoc
    |public fun <${args.valueTypesString}> kase(
    |  displayName: String,
    |  ${args.paramsString}
    |): ${types.kaseInterface} = ${types.defaultKaseNoTypes}(
    |  ${args.argsWithParamNames},
    |  displayNameFactory = { displayName }
    |)
    """.trimMargin()
  )
}

internal fun StringBuilder.kases_iterable(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kasesKdoc = buildList {
    add("Returns a list of [${types.kaseInterfaceNoTypes}]s from the given parameters.")
    add("")
    addAll(args) { arg ->
      "@param ${arg.iterableName} values mapped to the [${types.kaseInterfaceNoTypes}.${arg.valueName}] parameter."
    }
    add("@param displayNameFactory defines the name used in test environments and dynamic tests")
    add("@return a list of [${types.kaseInterfaceNoTypes}]s from the given parameters.")
    add("@since 0.1.0")
  }
    .makeKdoc()

  val forLoops = forLoops(args = args, builderName = "buildList", { iterableName }) {
    "add(kase(${args.argsWithParamNames}, displayNameFactory = displayNameFactory))"
  }

  appendLine(
    """
    |$kasesKdoc
    |public fun <${args.valueTypesString}> kases(
    |  ${args.argsIterableValueParams},
    |  displayNameFactory: ${types.displayNameFactory} = ${types.defaultDisplayNameFactory}()
    |): List<${types.kaseInterface}> {
    |  return $forLoops
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.kases_sequence(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kasesKdoc = buildList {
    add("Returns a sequence of [${types.kaseInterfaceNoTypes}]s from the given parameters.")
    add("")
    addAll(args) { arg ->
      "@param ${arg.iterableName} values mapped to the [${types.kaseInterfaceNoTypes}.${arg.valueName}] parameter."
    }
    add("@param displayNameFactory defines the name used in test environments and dynamic tests")
    add("@return a sequence of [${types.kaseInterfaceNoTypes}]s from the given parameters.")
    add("@since 0.1.0")
  }
    .makeKdoc()

  val forLoops = forLoops(
    args = args,
    builderName = "sequence",
    accessor = { iterableName }
  ) {
    "yield(kase(${args.argsWithParamNames}, displayNameFactory = displayNameFactory))"
  }

  appendLine(
    """
    |$kasesKdoc
    |public fun <${args.valueTypesString}> kases(
    |  ${args.argsSequenceValueParams},
    |  displayNameFactory: ${types.displayNameFactory} = ${types.defaultDisplayNameFactory}()
    |): Sequence<${types.kaseInterface}> {
    |  return $forLoops
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.testFun(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kdoc = buildList {
    add("Creates a new [${types.kaseInterfaceNoTypes}] instance and [TestEnvironment]")
    add("from these parameters, then executes [testAction].")
    add("")
    addAll(args) { arg ->
      "@param ${arg.valueName} the [${types.kaseInterfaceNoTypes}.${arg.valueName}] parameter."
    }
    add(
      "@param displayNameFactory defines the name used for this test environment's working directory"
    )
    add(
      "@param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run."
    )
    add("@param testAction the test action to execute.")
    add("@see KaseTestFactory")
    add("@since 0.1.0")
  }
    .makeKdoc()
  appendLine(
    """
    |$kdoc
    |@${kaseTestBuilderDsl.simple}
    |public fun <T: TestEnvironment, ${args.valueTypesString}> KaseTestFactory<T, ${types.kaseInterface}>.test(
    |  ${args.paramsString},
    |  displayNameFactory: ${types.displayNameFactory} = ${types.defaultDisplayNameFactory}(),
    |  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
    |  testAction: suspend T.() -> Unit
    |) {
    |  this@KaseTestFactory.test(
    |    kase = kase(${args.argsWithParamNames}, displayNameFactory = displayNameFactory),
    |    testFunctionCoordinates = testFunctionCoordinates,
    |    testAction = testAction
    |  )
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.asTests_Destructured(
  args: List<KaseArg>,
  kaseTypes: KaseTypes
) {

  appendLine(
    """
    /**
     * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [${kaseTypes.kaseInterfaceNoTypes}]s.
     *
     * @param testAction the test action to run for each kase.
     * @return a [Stream] of [DynamicNode]s from these kases.
     * @see ${kaseTypes.kaseInterfaceNoTypes}
     * @since 0.1.0
     */
    @${kaseTestBuilderDsl.simple}
    public fun <${args.valueTypesString}> Iterable<${kaseTypes.kaseInterface}>.asTests(
      testAction: (${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory {
        this@asTests.asTests { testAction(${args.valuesFromItString}) }
      }
    }
    """.trimIndent()
  )
}

internal fun StringBuilder.testFactory_vararg(
  kdoc: String,
  args: List<KaseArg>,
  kaseTypes: KaseTypes
) {

  appendLine(kdoc)
  appendLine(
    """
    @${kaseTestBuilderDsl.simple}
    public fun <${args.valueTypesString}> testFactory(
      vararg kases: ${kaseTypes.kaseInterface},
      testAction: (${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory { kases.asSequence().asTests { testAction(${args.valuesFromItString}) } }
    }
    """.trimIndent()
  )
}

internal fun StringBuilder.testFactory_Iterable(
  kdoc: String,
  args: List<KaseArg>,
  kaseTypes: KaseTypes
) {

  appendLine(kdoc)
  appendLine(
    """
    @${kaseTestBuilderDsl.simple}
    public fun <${args.valueTypesString}> testFactory(
      kases: Iterable<${kaseTypes.kaseInterface}>,
      testAction: (${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory { kases.asTests { testAction(${args.valuesFromItString}) } }
    }
    """.trimIndent()
  )
}

/**
 * ```
 * buildList {
 *   for (a1 in args1) {
 *     for (a2 in args2) {
 *       for (a3 in args3) {
 *         [...]
 *       }
 *     }
 *   }
 * }
 * ```
 * @param args the [KaseArg]s to iterate over
 * @param builderName the name of the builder function to invoke (e.g. `buildList` or `sequence`)
 * @param accessor the function to access the iterable from the [KaseArg] (e.g. `iterableName`)
 * @param center the code to execute at the center of the nested for loops
 * @since 0.1.0
 */
internal fun forLoops(
  args: List<KaseArg>,
  builderName: String,
  accessor: KaseArg.() -> String,
  center: () -> String
): String {

  fun StringBuilder.loop(args: List<KaseArg>, center: () -> String) {
    if (args.isEmpty()) {
      appendLine(center())
    } else {
      val arg = args.first()
      appendLine("for (${arg.valueName} in ${accessor(arg)}) {")
      indent {
        loop(args.dropView(1), center)
      }
      appendLine("}")
    }
  }

  return buildString {
    appendLine("$builderName {")
    indent("    ") {
      loop(args, center)
    }
    appendLine("  }")
  }.trim()
}

internal fun StringBuilder.timesFunctions(
  aArgs: List<KaseArg>,
  aTypes: KaseTypes
) {
  val ct = aTypes.number
  val maxTimes = MAX_PARAMS - ct
  if (maxTimes != 0) {

    for (b in 1..maxTimes) {

      val bArgs = (1..b).map {
        KaseArg(number = it, valuePrefix = "b", valueTypeNamePrefix = "B")
      }
      val bTypes = KaseTypes(b, bArgs)

      val cArgs = aArgs + bArgs

      val cArgTypes = cArgs.map { it.valueTypeName }
      val cArgTypesString = cArgTypes.joinToString(", ")

      val cTypes = KaseTypes(number = ct + b, args = cArgs)

      val aValueString = aArgs.joinToString(", ") { it.valueName }
      val bValueString = bArgs.joinToString(", ") { it.valueName }

      appendLine(
        """
        |/**
        | * @param others the [${bTypes.kaseInterfaceNoTypes}] to combine with this [${aTypes.kaseInterfaceNoTypes}]
        | * @return a list of [${cTypes.kaseInterfaceNoTypes}]s from the cartesian product of this [${aTypes.kaseInterfaceNoTypes}] and the given [${bTypes.kaseInterfaceNoTypes}].
        | * @since 0.1.0
        | */
        |@JvmName("${aTypes.kaseInterfaceNoTypes.decapitalize()}times${bTypes.kaseInterfaceNoTypes}")
        |public operator fun <$cArgTypesString> Iterable<${aTypes.kaseInterface}>.times(
        |  others: Iterable<${bTypes.kaseInterface}>
        |): List<${cTypes.kaseInterface}> = flatMap { ($aValueString) ->
        |  others.map { ($bValueString) ->
        |    kase(${cArgs.joinToString(", ") { it.valueName }})
        |  }
        |}
        |
        |/**
        | * @param others the [${bTypes.kaseInterfaceNoTypes}] to combine with this [${aTypes.kaseInterfaceNoTypes}]
        | * @param instanceFactory creates a custom Kase instance for each permutation
        | * @return a list of [${cTypes.kaseInterfaceNoTypes}]s from the cartesian product of this [${aTypes.kaseInterfaceNoTypes}] and the given [${bTypes.kaseInterfaceNoTypes}].
        | * @since 0.5.0
        | */
        |@JvmName("${aTypes.kaseInterfaceNoTypes.decapitalize()}times${bTypes.kaseInterfaceNoTypes}InstanceFactory")
        |public inline fun <$cArgTypesString, T> Iterable<${aTypes.kaseInterface}>.times(
        |  others: Iterable<${bTypes.kaseInterface}>,
        |  instanceFactory: (${cArgs.valueTypesString}) -> T
        |): List<T> = flatMap { ($aValueString) ->
        |  others.map { ($bValueString) ->
        |    instanceFactory(${cArgs.joinToString(", ") { it.valueName }})
        |  }
        |}
        |
        """.trimMargin()
      )
    }
  }
}
