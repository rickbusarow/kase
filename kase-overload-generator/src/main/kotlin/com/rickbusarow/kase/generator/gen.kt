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

@file:Suppress("MagicNumber", "UnusedPrivateMember")

package com.rickbusarow.kase.generator

import java.io.File

private val LICENSE = File("build.gradle.kts").readText()
  .substringBefore("*/")
  .plus("*/")

private val FILE_ANNOTATIONS = """
  @file:Suppress("PackageDirectoryMismatch", "DuplicatedCode", "MaxLineLength")
  @file:JvmMultifileClass
  @file:JvmName("KasesKt")
""".trimIndent()

@Suppress("SpellCheckingInspection")
private val IMPORTS = """
  import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
  import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
  import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParam
  import dev.drewhamilton.poko.Poko
  import org.junit.jupiter.api.DynamicNode
  import org.junit.jupiter.api.DynamicTest
  import java.util.stream.Stream

""".trimIndent()

private val kasesKt = File(
  "kase-gradle/src/main/kotlin/com/rickbusarow/kase/gradle/kases.kt"
)
private val testElements = File(
  "kase-gradle/src/test/kotlin/com/rickbusarow/kase/gradle/testElements.kt"
)
private val overloads = File("kase/src/main/kotlin/com/rickbusarow/kase/overloads")

private const val MAX_PARAMS = 21

internal data class Types(val number: Int) {
  val kaseInterface = "Kase$number"
  val defaultKase = "DefaultKase$number"
  val kaseLabels = "KaseLabels$number"

  val testEnvironment = "TestEnvironment"
  fun testEnvironmentFactory(environmentType: String) = "TestEnvironmentFactory<$environmentType>"

  fun kaseInterface(vararg valueTypeNames: String) =
    "$kaseInterface<${valueTypeNames.joinToString(", ")}>"

  fun kaseParameterWithLabel(argValueType: String) = "KaseParameterWithLabel<$argValueType>"
}

internal data class Arg(val number: Int) {
  val valueName = "a$number"
  val valueTypeName = "A$number"
  val labelName = "${valueName}Label"
  val valueWithLabelName = "${valueName}WithLabel"
}

// data class TypeParameter(
//   val name: String,
//   val variance: Variance,
//   val bounds: List<String> = emptyList()
// ) {
//   constructor(name: String) : this(name, Variance.NONE, emptyList())
//   constructor(name: String, variance: Variance) : this(name, variance, emptyList())
//   constructor(name: String, vararg bounds: String) : this(name, Variance.NONE, bounds.toList())
//
//   enum class Variance(val asString: String) {
//     IN("in "),
//     OUT("out "),
//     NONE("")
//   }
// }

// internal fun function(
//   name: String,
//   kdoc: String,
//   context: String?,
//   inline: Boolean
// ): String {
//   return ""
// }

private fun main() {

  writeGradleFile()
  writeTestElements()

  overloads.deleteRecursively()
  overloads.mkdirs()

  for (ct in (1..MAX_PARAMS)) {

    val nums = 1..ct

    val types = nums.map { "A$it" }
    val typesString = types.joinToString(", ", "<", ">")
    val typesWithVarianceString = types.joinToString(", ", "<", ">") { "out $it" }

    val args = nums.map { "a$it" }

    val argsString = args.joinToString(", ")
    val argsFromItString = args.joinToString(", ") { arg -> "it.$arg" }
    val argsFromKaseString = args.joinToString(", ") { arg -> "kase.$arg" }

    val argsStringWithLabels = args.joinToString(", ") { "$it = $it" }

    val argsTypePairs = args.zip(types)
    val params = argsTypePairs.map { "${it.first}: ${it.second}" }
    val paramsString = params.joinToString(", ")

    val kaseSimpleName = "Kase$ct"
    val kaseLabelSimpleName = "KaseLabels$ct"

    val labels = args.map { "${it}Label" }
    val labelsParamsDefaults = args.joinToString(",\n  ") { "${it}Label: String = \"$it\"" }

    val iterableParams = nums.map { "args$it" }

    val iterableParamsWithTypes = nums.zip(types).joinToString(",\n  ") { (i, type) ->
      "args$i: Iterable<$type>"
    }

    val argToLabelPairs = args.zip(labels)
    val withLabelCalls = argToLabelPairs.joinToString(",\n    ") { (value, label) ->
      "${value}WithLabel = kaseParam(value = $value, label = ($value as? HasLabel)?.label ?: labels.$label)"
    }

    val typesEnvironment = (listOf("T : TestEnvironment") + types)
      .joinToString(", ", "<", ">")
    val typesKaseEnvironment = (listOf("T", "K") + types)
      .joinToString(", ", "<", ">")

    val testActionTDestructured = "T.($paramsString) -> Unit"

    val parametersPlural = if (ct == 1) "parameter" else "parameters"

    val propertiesString = args.zip(types).joinToStringIndexed("\n\n") { i, (arg, type) ->
      """
          /** The ${(i + 1).withOrdinalSuffix()} parameter. */
          public val $arg: $type

          /** The ${(i + 1).withOrdinalSuffix()} parameter. */
          public val ${arg}WithLabel: KaseParameterWithLabel<$type>
      """.trimIndent()
    }

    val kasePlus1 = if (ct != MAX_PARAMS) "Kase${ct + 1}" else "AnyKase"
    val aPlus1 = "A${ct + 1}"

    val kasePlus1WithTypes = if (ct != MAX_PARAMS) {
      "$kasePlus1${types.joinToString(", ", "<", ", $aPlus1>")}"
    } else {
      kasePlus1
    }

    val argsWithLabels = args.map { arg -> "${arg}WithLabel" }
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

    val testFactoryKdoc = buildString {
      appendLine("/**")
      appendLine(
        " * A test factory which returns a stream of [DynamicNode]s from the given parameters."
      )
      appendLine(
        " * - Each [DynamicTest] in the stream uses its [$kaseSimpleName] element to create"
      )
      appendLine(" *   a new [TestEnvironment] instance, then executes [testAction].")
      appendLine(
        " * - Each [DynamicNode] has a display name which includes the values of the parameters."
      )
      appendLine(" *")
      appendLine(" * @param kases the [$kaseSimpleName]s to use for this test factory")
      appendLine(" * @param testAction the test action to execute.")
      appendLine(" * @return a [Stream] of [DynamicNode]s from the given parameters.")
      appendLine(" * @see $kaseSimpleName")
      appendLine(" * @see TestEnvironmentFactory")
      append(" */")
    }

    val labelsProperties = args.joinToString(",\n  ") {
      "public val ${it}Label: String = \"$it\""
    }

    val txt = buildString {

      appendLine(
        """
        |$LICENSE
        |
        |$FILE_ANNOTATIONS
        |
        |package com.rickbusarow.kase
        |
        |$IMPORTS
        """.trimMargin()
      )

      val componentFuns = (1..ct).joinToString("\n  ") { i ->
        "/** @see $kaseSimpleName.a$i */\n" +
          "  public operator fun component$i(): A$i = a$i"
      }

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

      kaseFun(
        typesString = typesString,
        args = args,
        parametersPlural = parametersPlural,
        paramsString = paramsString,
        kaseLabelSimpleName = kaseLabelSimpleName,
        kaseSimpleName = kaseSimpleName,
        withLabelCalls = withLabelCalls
      )

      testFun(
        args = args,
        typesKaseEnvironment = typesKaseEnvironment,
        typesString = typesString,
        paramsString = paramsString,
        kaseLabelSimpleName = kaseLabelSimpleName,
        argsString = argsString,
        kaseSimpleName = kaseSimpleName
      )

      labelsFun(
        labelsParamsDefaults = labelsParamsDefaults,
        kaseLabelSimpleName = kaseLabelSimpleName,
        kaseSimpleName = kaseSimpleName,
        labels = labels
      )

      kasesFun1(
        kaseSimpleName = kaseSimpleName,
        iterableParams = iterableParams,
        iterableParamsWithType = iterableParamsWithTypes,
        kaseLabelSimpleName = kaseLabelSimpleName,
        typesString = typesString,
        args = args,
        argsStringWithLabels = argsStringWithLabels
      )

      asTests(
        kaseSimpleName = kaseSimpleName,
        typesString = typesString,
        suffix = "Destructured",
        lambdaSignature = testActionTDestructured,
        typesEnvironment = typesEnvironment
      )

      testFactoryContext1(
        kdoc = testFactoryKdoc,
        kaseSimpleName = kaseSimpleName,
        typesString = typesString,
        suffix = "Destructured",
        lambdaSignature = testActionTDestructured,
        typesEnvironment = typesEnvironment
      )

      testFactoryContext2(
        kdoc = testFactoryKdoc,
        kaseSimpleName = kaseSimpleName,
        typesString = typesString,
        suffix = "Destructured",
        lambdaSignature = testActionTDestructured,
        typesEnvironment = typesEnvironment,
        argsFromKaseString = argsFromKaseString
      )

      testFactory(
        kdoc = testFactoryKdoc,
        kaseSimpleName = kaseSimpleName,
        typesString = typesString,
        paramsString = paramsString,
        argsFromItString = argsFromItString
      )

      kaseLabelsClass(
        ct = ct,
        parametersPlural = parametersPlural,
        argToLabelPairs = argToLabelPairs,
        kaseSimpleName = kaseSimpleName,
        kaseLabelSimpleName = kaseLabelSimpleName,
        labelsProperties = labelsProperties,
        labels = labels
      )

      defaultKase(
        args = args,
        argsWithLabels = argsWithLabels,
        types = types,
        paramsPairs = argsTypePairs,
        ct = ct,
        kasePlus1WithTypes = kasePlus1WithTypes,
        kasePlus1 = kasePlus1,
        kaseSimpleName = kaseSimpleName,
        typesWithVarianceString = typesWithVarianceString,
        typesString = typesString
      )
    }
      .fixBlankLines()

    val file = overloads.resolve("Kase$ct.kt")
    file.writeText(txt)
  }
}

private fun StringBuilder.kaseLabelsClass(
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

private fun StringBuilder.kasesFun1(
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

private fun String.fixBlankLines(): String {
  return mapLines { it.trimEnd() }
    .replace("""( *}\n)(?=[/\n@])""".toRegex(), "$1\n")
    .replace(Regex("\\n{3,}"), "\n\n")
    .trimEnd()
    .plus("\n")
}

private fun writeGradleFile() {
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
          (1..ct).joinToString(",\n", "\n", "\n  ") {
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

private fun writeTestElements() {

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

private fun StringBuilder.defaultKase(
  args: List<String>,
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

private fun StringBuilder.asTests(
  kaseSimpleName: String,
  typesString: String,
  suffix: String,
  lambdaSignature: String,
  typesEnvironment: String
) {

  appendLine(
    """
    /** */
    context(TestEnvironmentFactory<T>)
    @JvmName("asTests$kaseSimpleName${suffix}TestEnvironment")
    public inline fun $typesEnvironment Iterable<$kaseSimpleName$typesString>.asTests(
      crossinline testAction: $lambdaSignature
    ): Stream<out DynamicNode> {
      return testFactory(kases = this@asTests, testAction = testAction)
    }
    """.trimIndent()
  )
}

private fun StringBuilder.testFactoryContext1(
  kdoc: String,
  kaseSimpleName: String,
  typesString: String,
  suffix: String,
  lambdaSignature: String,
  typesEnvironment: String
) {

  appendLine(kdoc)
  appendLine(
    """
    context(TestEnvironmentFactory<T>)
    @JvmName("testFactory$kaseSimpleName${suffix}TestEnvironment")
    public inline fun $typesEnvironment testFactory(
      vararg kases: $kaseSimpleName$typesString,
      crossinline testAction: $lambdaSignature
    ): Stream<out DynamicNode> {
      return testFactory(kases = kases.toList(), testAction = testAction)
    }
    """.trimIndent()
  )
}

private fun StringBuilder.testFactoryContext2(
  kdoc: String,
  kaseSimpleName: String,
  typesString: String,
  suffix: String,
  lambdaSignature: String,
  typesEnvironment: String,
  argsFromKaseString: String
) {

  appendLine(kdoc)
  appendLine(
    """
    context(TestEnvironmentFactory<T>)
    @JvmName("testFactory$kaseSimpleName${suffix}TestEnvironment")
    public inline fun $typesEnvironment testFactory(
      kases: Iterable<$kaseSimpleName$typesString>,
      crossinline testAction: $lambdaSignature
    ): Stream<out DynamicNode> {
      return kases.asTests(
        testAction = { kase: $kaseSimpleName$typesString -> testAction($argsFromKaseString) }
      )
    }
    """.trimIndent()
  )
}

private fun StringBuilder.testFactory(
  kdoc: String,
  kaseSimpleName: String,
  typesString: String,
  paramsString: String,
  argsFromItString: String
) {

  appendLine(kdoc)
  appendLine(
    """
    @JvmName("testFactory$kaseSimpleName")
    public inline fun $typesString testFactory(
      vararg kases: $kaseSimpleName$typesString,
      crossinline testAction: ($paramsString) -> Unit
    ): Stream<out DynamicNode> {
      return kases.asSequence().asTests { testAction($argsFromItString) }
    }
    """.trimIndent()
  )
}

private fun forLoops(args: List<String>, center: StringBuilder.() -> Unit): String {
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

private fun StringBuilder.labelsFun(
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

private fun StringBuilder.kaseFun(
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

private fun StringBuilder.testFun(
  args: List<String>,
  typesKaseEnvironment: String,
  typesString: String,
  paramsString: String,
  kaseLabelSimpleName: String,
  argsString: String,
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
    |    kase = kase($argsString, labels),
    |    testFunctionCoordinates = testFunctionCoordinates,
    |    testAction = testAction
    |  )
    |}
    """.trimMargin()
  )
}
