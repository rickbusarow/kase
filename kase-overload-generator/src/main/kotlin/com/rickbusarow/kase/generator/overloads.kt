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

@file:Suppress("FunctionName")

package com.rickbusarow.kase.generator

import com.rickbusarow.kase.generator.KaseArg.Companion.argsValueParams
import com.rickbusarow.kase.generator.KaseArg.Companion.argsWithParamNames
import com.rickbusarow.kase.generator.KaseArg.Companion.parametersPlural
import com.rickbusarow.kase.generator.KaseArg.Companion.paramsString
import com.rickbusarow.kase.generator.KaseArg.Companion.valueNames
import com.rickbusarow.kase.generator.KaseArg.Companion.valueTypesString
import com.rickbusarow.kase.generator.KaseArg.Companion.valuesFromItString

internal fun StringBuilder.kaseInterface(
  ct: Int,
  parametersPlural: String,
  args: List<KaseArg>,
  types: KaseTypes
) {
  val lastArg = args.last()

  appendLine(
    """
    |/** A strongly typed version of [Kase] for $ct $parametersPlural. */
    |public interface ${types.kaseInterface} : ${types.kaseSuperInterface} {
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
    |internal class ${types.defaultKase}(
    |  ${args.argsValueParams},
    |  private val displayNameFactory: ${types.displayNameFactory}
    |) : ${types.kaseInterface}, KaseInternal {
    |
    |  override val displayName: String
    |    get() = with(displayNameFactory) { createDisplayName() }
    |
    |  $componentFuns
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.defaultDisplayNameFactory(
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

internal fun StringBuilder.kaseFun1(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kdoc = buildString {
    appendLine("/**")
    appendLine(" * Creates a new [Kase] with the given ${args.parametersPlural}.")
    appendLine(" *")
    for (arg in args) {
      appendLine(
        " * @param ${arg.valueName} the [${types.kaseInterfaceNoTypes}.${arg.valueName}] parameter."
      )
    }
    appendLine(
      " * @param displayNameFactory defines the name used in test environments and dynamic tests"
    )
    append(" */")
  }

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

internal fun StringBuilder.kaseFun2(
  args: List<KaseArg>,
  types: KaseTypes
) {

  val kdoc = buildString {
    appendLine("/**")
    appendLine(" * Creates a new [Kase] with the given ${args.parametersPlural}.")
    appendLine(" *")
    appendLine(" * @param displayName the name used in test environments and dynamic tests")
    for (arg in args) {
      appendLine(
        " * @param ${arg.valueName} the [${types.kaseInterfaceNoTypes}.${arg.valueName}] parameter."
      )
    }
    append(" */")
  }

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
  }
    .makeKdoc()
  appendLine(
    """
    |$kdoc
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

internal fun StringBuilder.kasesFun1(
  kaseSimpleName: String,
  iterableParams: List<String>,
  iterableParamsWithType: String,
  typesString: String,
  args: List<String>,
  argsStringWithLabels: String,
  types: KaseTypes
) {
  val kasesKdoc = buildList {
    add("Returns a [List] of [$kaseSimpleName]s from the given parameters.")
    add("")
    addAll(iterableParams.withIndex()) { (i, iterable) ->
      "@param $iterable values mapped to the [$kaseSimpleName.a${i + 1}] parameter."
    }
    add("@param displayNameFactory defines the name used in test environments and dynamic tests")
    add("@return a [List] of [$kaseSimpleName]s from the given parameters.")
  }
    .makeKdoc()
  val forLoops = forLoops(args) {
    appendLine("add(kase($argsStringWithLabels, displayNameFactory = displayNameFactory))")
  }

  appendLine(
    """
    |$kasesKdoc
    |public fun $typesString kases(
    |  $iterableParamsWithType,
    |  displayNameFactory: ${types.displayNameFactory} = ${types.defaultDisplayNameFactory}()
    |): List<$kaseSimpleName$typesString> {
    |  return $forLoops
    |}
    """.trimMargin()
  )
}

internal fun String.fixBlankLines(): String {
  return mapLines { it.trimEnd() }
    .replace("""( *}\n)(?=[/\n@])""".toRegex(), "$1\n")
    .replace(Regex("\\n{3,}"), "\n\n")
    .trimEnd()
    .plus("\n")
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
     */
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
    public fun <${args.valueTypesString}> testFactory(
      kases: Iterable<${kaseTypes.kaseInterface}>,
      testAction: (${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory { kases.asTests { testAction(${args.valuesFromItString}) } }
    }
    """.trimIndent()
  )
}

internal fun forLoops(args: List<String>, center: StringBuilder.() -> Unit): String {
  return buildString {

    var indent = 1
    fun tab() = "  ".repeat(indent)

    appendLine("buildList {")
    indent++

    for ((index, arg) in args.withIndex()) {

      val i = index + 1

      appendLine("${tab()}for ($arg in args$i) {")
      indent++
    }

    append(tab())

    center()

    repeat(args.size + 1) {
      indent--
      appendLine("${tab()}}")
    }
  }.trim()
}

internal fun StringBuilder.timesFunctions(
  ct: Int,
  aArgs: List<KaseArg>,
  aTypes: KaseTypes
) {
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
        """.trimMargin()
      )
    }
  }
}
