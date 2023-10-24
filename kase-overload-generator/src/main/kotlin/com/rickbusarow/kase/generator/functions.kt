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

import com.rickbusarow.kase.generator.KaseArg.Companion.paramsString
import com.rickbusarow.kase.generator.KaseArg.Companion.valueTypesString
import com.rickbusarow.kase.generator.KaseArg.Companion.valuesFromItString

internal fun StringBuilder.kaseInterface(
  ct: Int,
  parametersPlural: String,
  kaseSimpleName: String,
  typesWithVarianceString: String,
  propertiesString: String,
  componentFuns: String,
  aPlus1: String,
  kasePlus1WithTypes: String,
  plus1Impl: String
) {
  appendLine(
    """
    |/** A strongly-typed version of [Kase] for $ct $parametersPlural. */
    |public interface $kaseSimpleName$typesWithVarianceString : Kase {
    |
    |  ${propertiesString.prependIndentAfterFirst("  ")}
    |
    |  /** The delimiter between the label and the value, like `": "` in `label: value` */
    |  public val labelDelimiter: String get() = DELIMITER_DEFAULT
    |
    |  /**
    |   * The separator between each label/value pair, like `" | "` in `label1: value1 | label2: value2`
    |   */
    |  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT
    |
    |  $componentFuns
    |
    |  override fun <$aPlus1> plus(label: String, value: $aPlus1): $kasePlus1WithTypes {
    |    ${plus1Impl.prependIndentAfterFirst("    ")}
    |  }
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.kaseLabelsClass(
  ct: Int,
  parametersPlural: String,
  argToLabelPairs: List<Pair<String, String>>,
  kaseSimpleName: String,
  kaseLabelSimpleName: String,
  labelsProperties: String,
  labels: List<String>
) {
  val kaseLabelsKdoc = buildString {
    appendLine("/**")
    appendLine(" * A strongly-typed version of [KaseLabels] for $ct $parametersPlural.")
    appendLine(" *")
    for ((arg, label) in argToLabelPairs) {
      appendLine(" * @property $label The label for the [$kaseSimpleName.$arg] parameter.")
    }
    appendLine(
      " * @property labelDelimiter The delimiter between the label and the value. The default is `\": \"`."
    )
    appendLine(" * @property displayNameSeparator The separator between")
    appendLine(" *   each label/value pair. The default is `\" | \"`.")
    append(" */")
  }
  appendLine(
    """
    |$kaseLabelsKdoc
    |@Poko
    |public class $kaseLabelSimpleName(
    |  $labelsProperties,
    |  override val labelDelimiter: String = DELIMITER_DEFAULT,
    |  override val displayNameSeparator: String = SEPARATOR_DEFAULT
    |) : KaseLabels {
    |
    |  override val orderedLabels: List<String> by lazy {
    |    listOf(${labels.joinToString(", ")})
    |  }
    |}
    """.replaceIndentByMargin()
  )
}

internal fun StringBuilder.kasesFun1(
  kaseSimpleName: String,
  iterableParams: List<String>,
  iterableParamsWithType: String,
  kaseLabelSimpleName: String,
  typesString: String,
  args: List<String>,
  argsStringWithLabels: String
) {
  val kasesKdoc = buildString {
    appendLine("/**")
    appendLine(" * Returns a [List] of [$kaseSimpleName]s from the given parameters.")
    appendLine(" *")
    for ((i, iterable) in iterableParams.withIndex()) {
      appendLine(" * @param $iterable values mapped to the [$kaseSimpleName.a${i + 1}] parameter.")
    }
    appendLine(" * @param labels the [$kaseLabelSimpleName] to use for this [$kaseSimpleName]")
    appendLine(" * @return a [List] of [$kaseSimpleName]s from the given parameters.")
    append(" */")
  }
  val forLoops = forLoops(args) {
    appendLine("add(kase($argsStringWithLabels, labels = labels))")
  }

  appendLine(
    """
    |$kasesKdoc
    |public fun $typesString kases(
    |  $iterableParamsWithType,
    |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName()
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

internal fun writeGradleFile() {
  val gradle = buildString {

    appendLine(
      """
      |$LICENSE
      |
      |$FILE_ANNOTATIONS
      |
      |package com.rickbusarow.kase.gradle
      |
      """.trimMargin()
    )

    List(MAX_PARAMS) { it + 1 }
      .map { "import com.rickbusarow.kase.Kase$it" }
      .sorted()
      .forEach(::appendLine)

    appendLine("import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixElement")
    appendLine("import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey")
    appendLine("import com.rickbusarow.kase.kases")
    appendLine()

    for (ct in (1..MAX_PARAMS)) {
      val types = (1..ct)
        .joinToString(
          separator = ",\n",
          prefix = "\n",
          postfix = "\n  "
        ) { "  reified A$it : VersionMatrixElement<*>" }

      val params = (1..ct)
        .joinToString(
          separator = ",\n",
          prefix = "\n",
          postfix = "\n"
        ) { "  a${it}Key: VersionMatrixKey<A$it>" }

      val kdoc = """
        |/**
        | * Returns a [List] of [Kase$ct]s from this [VersionMatrix] for the given keys.
        | *
        |${
        (1..ct)
          .joinToString("\n") {
            "| * @param a${it}Key the key for the ${it.withOrdinalSuffix()} parameter."
          }
      }
        | * @return a [List] of [Kase$ct]s from this [VersionMatrix] for the given keys
        | */
      """.trimMargin()

      appendLine(
        """
        |$kdoc
        |public inline fun <$types> VersionMatrix.kases($params): List<Kase$ct<${
          (1..ct).joinToString(", ") { "A$it" }
        }>> {
        |  return kases(${
          (1..ct).joinToString(separator = ",\n", prefix = "\n", postfix = "\n  ") {
            "    args$it = get(a${it}Key)"
          }
        })
        |}
        |
        """.trimMargin()
      )
    }
  }
    .fixBlankLines()

  kasesKt.writeText(gradle)
}

internal fun writeTestElements() {

  val classNames = List(MAX_PARAMS) { "TestVersion${it + 1}" }

  val content = buildString {

    appendLine(
      """
      |$LICENSE
      |
      |$FILE_ANNOTATIONS
      |
      |package com.rickbusarow.kase.gradle
      |
      |${
        classNames.sorted()
          .joinToString("\n") { "import com.rickbusarow.kase.gradle.$it.${it}Key" }
      }
      |import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
      |import dev.drewhamilton.poko.Poko
      |
      """.trimMargin()
    )

    val classes = classNames.map { tv ->
      """
      |@Poko
      |class $tv(
      |  override val value: String
      |) : AbstractDependencyVersion<String, $tv, ${tv}Key>(${tv}Key) {
      |
      |  companion object ${tv}Key : VersionMatrixKey<$tv>
      |}
      |
      """.trimMargin()
    }

    for (clazz in classes) {
      appendLine(clazz)
    }
  }
    .fixBlankLines()

  testElements.writeText(content)
}

internal fun StringBuilder.defaultKase(
  argsWithLabels: List<String>,
  types: List<String>,
  paramsPairs: List<Pair<String, String>>,
  ct: Int,
  kasePlus1WithTypes: String,
  kasePlus1: String,
  kaseSimpleName: String,
  typesWithVarianceString: String,
  typesString: String
) {
  val argWithLabelValueParams = argsWithLabels.zip(types).joinToString(",\n  ") { (element, type) ->
    "override val $element: KaseParameterWithLabel<$type>"
  }
  val argMemberProperties = paramsPairs.joinToString("\n  ") { (arg, type) ->
    "override val $arg: $type get() = ${arg}WithLabel.value"
  }

  val defaultKasePlus1 = if (ct != MAX_PARAMS) "Default$kasePlus1WithTypes" else kasePlus1

  val aPlus1 = "A${ct + 1}"
  val plus1Impl = if (ct != MAX_PARAMS) {
    """
    |return Default$kasePlus1(
    |  ${argsWithLabels.joinToString(",\n  ") { "$it = $it" }},
    |  a${ct + 1}WithLabel = kaseParam(label = label, value = value),
    |  labelDelimiter = labelDelimiter,
    |  displayNameSeparator = displayNameSeparator
    |)
    """.trimMargin()
  } else {
    """error("A Kase cannot have more than 22 parameters")"""
  }
  val componentFuns = (1..ct).joinToString("\n  ") { i ->
    "override fun component$i(): A$i = a$i"
  }
  appendLine(
    """
    |@Poko
    |@PublishedApi
    |internal class Default$kaseSimpleName$typesWithVarianceString(
    |  $argWithLabelValueParams,
    |  override val labelDelimiter: String,
    |  override val displayNameSeparator: String,
    |) : Kase$ct$typesString, KaseInternal {
    |  $argMemberProperties
    |
    |  override val elements: List<KaseParameterWithLabel<Any?>>
    |    get() = listOf(${argsWithLabels.joinToString(", ")})
    |
    |  override fun <$aPlus1> plus(label: String, value: $aPlus1): $defaultKasePlus1 {
    |    ${plus1Impl.prependIndentAfterFirst("    ")}
    |  }
    |
    |  $componentFuns
    |
    |  override fun toString(): String = displayName
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
     */
    @JvmName("asTests${kaseTypes.kaseInterfaceNoTypes}Destructured")
    public inline fun <${args.valueTypesString}> Iterable<${kaseTypes.kaseInterface}>.asTests(
      crossinline testAction: (${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory {
        this@asTests.asTests { testAction(${args.valuesFromItString}) }
      }
    }
    """.trimIndent()
  )
}

internal fun StringBuilder.asTests_Destructured_TestEnvironment(
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
    context(TestEnvironmentFactory<T>)
    @JvmName("asTests${kaseTypes.kaseInterfaceNoTypes}ExtensionDestructuredTestEnvironment")
    public inline fun <T : TestEnvironment, ${args.valueTypesString}> Iterable<${kaseTypes.kaseInterface}>.asTests(
      crossinline testAction: T.(${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory {
        this@asTests.asTests { testAction(${args.valuesFromItString}) }
      }
    }
    """.trimIndent()
  )
}

internal fun StringBuilder.testFactory_vararg_Destructured_TestEnvironment(
  kdoc: String,
  args: List<KaseArg>,
  kaseTypes: KaseTypes
) {

  appendLine(kdoc)
  appendLine(
    """
    context(TestEnvironmentFactory<T>)
    @JvmName("testFactory${kaseTypes.kaseInterfaceNoTypes}VarargDestructuredTestEnvironment")
    public inline fun <T : TestEnvironment, ${args.valueTypesString}> testFactory(
      vararg kases: ${kaseTypes.kaseInterface},
      crossinline testAction: T.(${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory {
        kases.asSequence().asTests { testAction(${args.valuesFromItString}) }
      }
    }
    """.trimIndent()
  )
}

internal fun StringBuilder.testFactory_iterable_Destructured_TestEnvironment(
  kdoc: String,
  args: List<KaseArg>,
  kaseTypes: KaseTypes
) {

  appendLine(kdoc)
  appendLine(
    """
    context(TestEnvironmentFactory<T>)
    @JvmName("testFactory${kaseTypes.kaseInterfaceNoTypes}IterableDestructuredTestEnvironment")
    public inline fun <T : TestEnvironment, ${args.valueTypesString}> testFactory(
      kases: Iterable<${kaseTypes.kaseInterface}>,
      crossinline testAction: T.(${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory {
        kases.asTests { testAction(${args.valuesFromItString}) }
      }
    }
    """.trimIndent()
  )
}

internal fun StringBuilder.testFactory_vararg_Destructured(
  kdoc: String,
  args: List<KaseArg>,
  kaseTypes: KaseTypes
) {

  appendLine(kdoc)
  appendLine(
    """
    @JvmName("testFactory${kaseTypes.kaseInterfaceNoTypes}VarargDestructured")
    public inline fun <${args.valueTypesString}> testFactory(
      vararg kases: ${kaseTypes.kaseInterface},
      crossinline testAction: (${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory { kases.asSequence().asTests { testAction(${args.valuesFromItString}) } }
    }
    """.trimIndent()
  )
}

internal fun StringBuilder.testFactory_Iterable_Destructured(
  kdoc: String,
  args: List<KaseArg>,
  kaseTypes: KaseTypes
) {

  appendLine(kdoc)
  appendLine(
    """
    @JvmName("testFactory${kaseTypes.kaseInterfaceNoTypes}IterableDestructured")
    public inline fun <${args.valueTypesString}> testFactory(
      kases: Iterable<${kaseTypes.kaseInterface}>,
      crossinline testAction: (${args.paramsString}) -> Unit
    ): Stream<out DynamicNode> {
      return testFactory { kases.asTests { testAction(${args.valuesFromItString}) } }
    }
    """.trimIndent()
  )
}

internal fun forLoops(args: List<String>, center: StringBuilder.() -> Unit): String {
  return buildString {

    var idt = 1
    fun tab() = "  ".repeat(idt)

    appendLine("buildList {")
    idt++

    for ((index, arg) in args.withIndex()) {

      val i = index + 1

      appendLine("${tab()}for ($arg in args$i) {")
      idt++
    }

    append(tab())

    center()

    repeat(args.size + 1) {
      idt--
      appendLine("${tab()}}")
    }
  }.trim()
}

internal fun StringBuilder.labelsFun(
  labelsParamsDefaults: String,
  kaseLabelSimpleName: String,
  kaseSimpleName: String,
  labels: List<String>
) {
  val kdoc = buildString {
    appendLine("/**")
    appendLine(" * Creates a new [$kaseLabelSimpleName] with the given labels.")
    appendLine(" *")
    for ((i, label) in labels.withIndex()) {
      appendLine(" * @param $label the label for the [$kaseSimpleName.a${i + 1}] property.")
    }
    appendLine(" * @return a new [$kaseLabelSimpleName] with the given labels.")
    append(" */")
  }
  appendLine(
    """
    |$kdoc
    |public fun labels(
    |  $labelsParamsDefaults
    |): $kaseLabelSimpleName {
    |  return $kaseLabelSimpleName(${labels.joinToString(", ") { "$it = $it" }})
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.kaseFun(
  typesString: String,
  args: List<String>,
  parametersPlural: String,
  paramsString: String,
  kaseLabelSimpleName: String,
  kaseSimpleName: String,
  withLabelCalls: String
) {

  val kdoc = buildString {
    appendLine("/**")
    appendLine(" * Creates a new [Kase] with the given $parametersPlural.")
    appendLine(" *")
    for (arg in args) {
      appendLine(" * @param $arg the [$kaseSimpleName:$arg] parameter.")
    }
    appendLine(" * @param labels the [$kaseLabelSimpleName] to use for this [$kaseSimpleName]")
    appendLine(" * @param labelDelimiter the delimiter between the")
    appendLine(""" *   label and the value, like `": "` in `label: value`""")
    appendLine(" * @param displayNameSeparator the separator between each label/value")
    appendLine(""" *   pair, like `" | "` in `label1: value1 | label2: value2`""")
    append(" */")
  }

  appendLine(
    """
    |$kdoc
    |public fun $typesString kase(
    |  $paramsString,
    |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
    |  labelDelimiter: String = labels.labelDelimiter,
    |  displayNameSeparator: String = labels.displayNameSeparator
    |): $kaseSimpleName$typesString {
    |  return Default$kaseSimpleName(
    |    $withLabelCalls,
    |    labelDelimiter = labelDelimiter,
    |    displayNameSeparator = displayNameSeparator
    |  )
    |}
    """.trimMargin()
  )
}

internal fun StringBuilder.testFun(
  args: List<String>,
  typesKaseEnvironment: String,
  typesString: String,
  paramsString: String,
  kaseLabelSimpleName: String,
  argsStringWithLabels: String,
  kaseSimpleName: String
) {

  val kdoc = buildString {
    appendLine("/**")
    appendLine(" * Creates a new [$kaseSimpleName] instance and [TestEnvironment]")
    appendLine(" * from these parameters, then executes [testAction].")
    appendLine(" *")
    for (arg in args) {
      appendLine(" * @param $arg the [$kaseSimpleName:$arg] parameter.")
    }
    appendLine(" * @param labels the [$kaseLabelSimpleName] to use for this [$kaseSimpleName]")
    appendLine(
      " * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run."
    )
    appendLine(" * @param testAction the test action to execute.")
    appendLine(" * @see TestEnvironmentFactory")
    append(" */")
  }
  appendLine(
    """
    |$kdoc
    |public fun $typesKaseEnvironment TestEnvironmentFactory<T>.test(
    |  $paramsString,
    |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
    |  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
    |  testAction: suspend T.() -> Unit
    |) where T : TestEnvironment,
    |        K : $kaseSimpleName$typesString {
    |  this@TestEnvironmentFactory.test(
    |    kase = kase($argsStringWithLabels, labels = labels),
    |    testFunctionCoordinates = testFunctionCoordinates,
    |    testAction = testAction
    |  )
    |}
    """.trimMargin()
  )
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
