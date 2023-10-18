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
  import java.util.stream.Stream

""".trimIndent()

private fun main() {

  val overloads = File(
    "/Users/rbusarow/Development/kase/kase/src/" +
      "main/kotlin/com/rickbusarow/kase/overloads"
  )
  overloads.deleteRecursively()

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

    List(22) { it + 1 }
      .map { "import com.rickbusarow.kase.Kase$it" }
      .sorted()
      .forEach(::appendLine)

    appendLine("import com.rickbusarow.kase.gradle.VersionsMatrix.Element")
    appendLine("import com.rickbusarow.kase.kases")
    appendLine()

    for (ct in (1..22)) {
      val types = (1..ct).joinToString(",\n", "\n", "\n  ") { "  reified A$it : Element" }

      appendLine(
        """
        |/** */
        |public inline fun <$types> VersionsMatrix.kases$ct(): List<Kase$ct<${
          (1..ct).joinToString(", ") { "A$it" }
        }>> {
        |  return kases(${
          (1..ct).joinToString(",\n", "\n", "\n  ") {
            "    args$it = get(A$it::class)"
          }
        })
        |}
        |
        """.trimMargin()
      )
    }
  }

  val kasesKt = File(
    "/Users/rbusarow/Development/kase/kase-gradle/src/main/kotlin/com/rickbusarow/kase/gradle/kases.kt"
  )
  kasesKt.writeText(gradle)

  for (ct in (1..22)) {
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

      val nums = 1..ct

      val types = nums.map { "A$it" }
      val typesString = types.joinToString(", ", "<", ">")
      val typesWithVarianceString = types.joinToString(", ", "<", ">") { "out $it" }

      val args = nums.map { "a$it" }

      val argsString = args.joinToString(", ")
      val argsFromItString = args.joinToString(", ") { arg -> "it.$arg" }
      val argsFromKaseString = args.joinToString(", ") { arg -> "kase.$arg" }

      val argsStringWithLabels = args.joinToString(", ") { "$it = $it" }

      val paramsPairs = args.zip(types)
      val params = paramsPairs.map { "${it.first}: ${it.second}" }
      val paramsString = params.joinToString(", ")

      val kaseSimpleName = "Kase$ct"
      val kaseLabelSimpleName = "KaseLabels$ct"

      val labels = args.map { "${it}Label" }
      val labelsParamsDefaults = args.joinToString(",\n  ") { "${it}Label: String = \"$it\"" }

      val iterableParams = nums.zip(types).joinToString(",\n  ") { (i, type) ->
        "args$i: Iterable<$type>"
      }

      val argToLabelPairs = args.zip(labels)
      val withLabelCalls = argToLabelPairs.joinToString(",\n    ") { (value, label) ->
        "${value}WithLabel = kaseParam(value = $value, label = labels.$label)"
      }

      val typesEnvironment = (listOf("T : TestEnvironment") + types)
        .joinToString(", ", "<", ">")
      val typesKaseEnvironment = (listOf("T", "K") + types)
        .joinToString(", ", "<", ">")

      val testActionTDestructured = "T.($paramsString) -> Unit"

      val parametersPlural = if (ct == 1) "parameter" else "parameters"

      val propertiesString = args.zip(types).joinToStringIndexed("\n") { i, (arg, type) ->
        """
          /** The ${(i + 1).withOrdinalSuffix()} parameter. */
          public val $arg: $type
          /** The ${(i + 1).withOrdinalSuffix()} parameter. */
          public val ${arg}WithLabel: KaseParameterWithLabel<$type>
        """.trimIndent()
      }

      val kasePlus1 = if (ct != 22) "Kase${ct + 1}" else "AnyKase"
      val aPlus1 = "A${ct + 1}"

      val kasePlus1WithTypes = if (ct != 22) {
        "$kasePlus1${types.joinToString(", ", "<", ", $aPlus1>")}"
      } else {
        kasePlus1
      }

      val argsWithLabels = args.map { arg -> "${arg}WithLabel" }
      val plus1Impl = if (ct != 22) {
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
        typesKaseEnvironment = typesKaseEnvironment,
        typesString = typesString,
        paramsString = paramsString,
        kaseLabelSimpleName = kaseLabelSimpleName,
        argsString = argsString,
        kaseSimpleName = kaseSimpleName
      )

      labelsFun(labelsParamsDefaults, kaseLabelSimpleName, labels)

      val forLoops = forLoops(args) {
        appendLine("add(kase($argsStringWithLabels, labels = labels))")
      }

      appendLine(
        """
        |/** */
        |public fun $typesString kases(
        |  $iterableParams,
        |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName()
        |): List<$kaseSimpleName$typesString> {
        |  return $forLoops
        |}
        """.trimMargin()
      )

      val testEnvironmentLambdas = listOf(
        "Destructured" to testActionTDestructured
      )

      asTests(
        kaseSimpleName = kaseSimpleName,
        typesString = typesString,
        testEnvironmentLambdas = testEnvironmentLambdas,
        typesEnvironment = typesEnvironment
      )

      testFactories(
        kaseSimpleName = kaseSimpleName,
        typesString = typesString,
        testEnvironmentLambdas = testEnvironmentLambdas,
        typesEnvironment = typesEnvironment
      )

      testFactories2(
        kaseSimpleName = kaseSimpleName,
        typesString = typesString,
        testEnvironmentLambdas = testEnvironmentLambdas,
        typesEnvironment = typesEnvironment,
        argsFromKaseString = argsFromKaseString
      )

      appendLine(
        """
        /** */
        @JvmName("testFactory$kaseSimpleName")
        public inline fun $typesString testFactory(
          vararg kases: $kaseSimpleName$typesString,
          crossinline kaseName: ($kaseSimpleName$typesString) -> String = { it.toString() },
          crossinline testAction: ($paramsString) -> Unit
        ): Stream<out DynamicNode> {
          return kases.asSequence().asTests(kaseName) { testAction($argsFromItString) }
        }
        """.trimIndent()
      )

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

      val labelsProperties = args.joinToString(",\n  ") {
        "public val ${it}Label: String = \"$it\""
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

      defaultKase(
        argsWithLabels = argsWithLabels,
        types = types,
        paramsPairs = paramsPairs,
        ct = ct,
        kasePlus1WithTypes = kasePlus1WithTypes,
        kasePlus1 = kasePlus1,
        kaseSimpleName = kaseSimpleName,
        typesWithVarianceString = typesWithVarianceString,
        typesString = typesString
      )
    }
      .mapLines { it.trimEnd() }
      .replace("""( *}\n)(?=[/\n@])""".toRegex(), "$1\n")
      .replace(Regex("\\n{3,}"), "\n\n")

    val file = overloads.resolve("kase$ct.kt")
    file.parentFile.mkdirs()
    file.writeText(txt)
  }
}

private fun StringBuilder.defaultKase(
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

  val defaultKasePlus1 = if (ct != 22) "Default$kasePlus1WithTypes" else kasePlus1

  val aPlus1 = "A${ct + 1}"
  val plus1Impl = if (ct != 22) {
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
  appendLine(
    """
    |@Poko
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
    |}
    """.trimMargin()
  )
}

private fun StringBuilder.asTests(
  kaseSimpleName: String,
  typesString: String,
  testEnvironmentLambdas: List<Pair<String, String>>,
  typesEnvironment: String
) {

  for ((suffix, lambdaSignature) in testEnvironmentLambdas) {
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
}

private fun StringBuilder.testFactories(
  kaseSimpleName: String,
  typesString: String,
  testEnvironmentLambdas: List<Pair<String, String>>,
  typesEnvironment: String
) {

  for ((suffix, lambdaSignature) in testEnvironmentLambdas) {
    appendLine(
      """
      /** */
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
}

private fun StringBuilder.testFactories2(
  kaseSimpleName: String,
  typesString: String,
  testEnvironmentLambdas: List<Pair<String, String>>,
  typesEnvironment: String,
  argsFromKaseString: String
) {

  for ((suffix, lambdaSignature) in testEnvironmentLambdas) {
    appendLine(
      """
      /** */
      context(TestEnvironmentFactory<T>)
      @JvmName("testFactory$kaseSimpleName${suffix}TestEnvironment")
      public inline fun $typesEnvironment testFactory(
        kases: Iterable<$kaseSimpleName$typesString>,
        crossinline testAction: $lambdaSignature
      ): Stream<out DynamicNode> {
        return kases.asTests(
          testName = { kase -> kase.displayName() },
          testAction = { kase -> testAction($argsFromKaseString) }
        )
      }
      """.trimIndent()
    )
  }
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
  labels: List<String>
) {
  appendLine(
    """
    |/** */
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
    appendLine(" * @param labels the [KaseLabels] to use for this [Kase]")
    appendLine(" * @param labelDelimiter the delimiter between the label")
    appendLine(""" *   and the value, like `": "` in `label: value`""")
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
    |  labelDelimiter: String = DELIMITER_DEFAULT,
    |  displayNameSeparator: String = SEPARATOR_DEFAULT
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
  typesKaseEnvironment: String,
  typesString: String,
  paramsString: String,
  kaseLabelSimpleName: String,
  argsString: String,
  kaseSimpleName: String
) {
  appendLine(
    """
    |/** */
    |context(TestEnvironmentFactory<T>)
    |public fun $typesKaseEnvironment test(
    |  $paramsString,
    |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
    |  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
    |  testAction: suspend T.() -> Unit
    |) where T : TestEnvironment,
    |        K : $kaseSimpleName$typesString {
    |  test(
    |    kase = kase($argsString, labels),
    |    testFunctionCoordinates = testFunctionCoordinates,
    |    testAction = testAction
    |  )
    |}
    """.trimMargin()
  )
}
