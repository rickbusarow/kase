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

@file:Suppress("MagicNumber", "UnusedPrivateMember", "MemberVisibilityCanBePrivate")

package com.rickbusarow.kase.generator

import com.rickbusarow.kase.generator.KaseArg.Companion.valueTypesString
import java.io.File

internal val LICENSE = File("build.gradle.kts").readText()
  .substringBefore("*/")
  .plus("*/")

internal val FILE_ANNOTATIONS = """
  @file:Suppress(
    "DestructuringDeclarationWithTooManyEntries",
    "DuplicatedCode",
    "MaxLineLength",
    "PackageDirectoryMismatch"
  )
  @file:JvmMultifileClass
  @file:JvmName("KasesKt")
""".trimIndent()

@Suppress("SpellCheckingInspection")
internal val IMPORTS = """
  import com.rickbusarow.kase.files.TestFunctionCoordinates
  import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
  import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
  import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParam
  import dev.drewhamilton.poko.Poko
  import org.junit.jupiter.api.DynamicNode
  import org.junit.jupiter.api.DynamicTest
  import java.util.stream.Stream

""".trimIndent()

internal val kasesKt = File(
  "kase-gradle/src/main/kotlin/com/rickbusarow/kase/gradle/kases.kt"
)
internal val testElements = File(
  "kase-gradle/src/test/kotlin/com/rickbusarow/kase/gradle/testElements.kt"
)
internal val overloads = File("kase/src/main/kotlin/com/rickbusarow/kase/overloads")

internal const val MAX_PARAMS = 21

internal data class KaseTypes(val number: Int, val args: List<KaseArg>) {

  private val argsValueTypesString: String by lazy { args.valueTypesString }

  val kaseInterfaceNoTypes = "Kase$number"
  val kaseInterface: String = "$kaseInterfaceNoTypes<$argsValueTypesString>"

  val defaultKaseNoTypes = "DefaultKase$number"
  val defaultKase = "DefaultKase$number<$argsValueTypesString>"

  val kaseLabels = "KaseLabels$number"

  val testEnvironment = "TestEnvironment"

  fun testEnvironmentFactory(environmentType: String) = "TestEnvironmentFactory<$environmentType>"
  fun kaseParameterWithLabel(argValueType: String) = "KaseParameterWithLabel<$argValueType>"
}

internal data class KaseArg(
  val number: Int,
  val valuePrefix: String = "a",
  val valueTypeNamePrefix: String = "A"
) {
  val valueName = "$valuePrefix$number"
  val valueTypeName = "$valueTypeNamePrefix$number"
  val labelName = "${valueName}Label"
  val valueWithLabelName = "${valueName}WithLabel"

  companion object {
    val List<KaseArg>.valueNames: List<String>
      get() = map { it.valueName }
    val List<KaseArg>.valueTypes: List<String>
      get() = map { it.valueTypeName }
    val List<KaseArg>.valueTypesString: String
      get() = valueTypes.joinToString(", ")

    val List<KaseArg>.argsTypePairs: List<Pair<String, String>>
      get() = valueNames.zip(valueTypes)
    val List<KaseArg>.params: List<String>
      get() = argsTypePairs.map { "${it.first}: ${it.second}" }
    val List<KaseArg>.paramsString: String
      get() = params.joinToString(", ")
    val List<KaseArg>.valuesFromItString: String
      get() = valueNames.joinToString(", ") { arg -> "it.$arg" }
  }
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
//
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

    val args2 = (1..ct).map { KaseArg(it) }
    val kaseTypes = KaseTypes(ct, args2)

    val types = nums.map { "A$it" }
    val typesString = types.joinToString(separator = ", ", prefix = "<", postfix = ">")
    val typesWithVarianceString =
      types.joinToString(separator = ", ", prefix = "<", postfix = ">") {
        "out $it"
      }

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
      .joinToString(separator = ", ", prefix = "<", postfix = ">")
    val typesKaseEnvironment = (listOf("T", "K") + types)
      .joinToString(separator = ", ", prefix = "<", postfix = ">")

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
      "$kasePlus1${types.joinToString(separator = ", ", prefix = "<", postfix = ", $aPlus1>")}"
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

      val componentFuns = (1..ct).joinToString("\n\n  ") { i ->
        "/** @see $kaseSimpleName.a$i */\n" +
          "  public operator fun component$i(): A$i = a$i"
      }

      kaseInterface(
        ct = ct,
        parametersPlural = parametersPlural,
        kaseSimpleName = kaseSimpleName,
        typesWithVarianceString = typesWithVarianceString,
        propertiesString = propertiesString,
        componentFuns = componentFuns,
        aPlus1 = aPlus1,
        kasePlus1WithTypes = kasePlus1WithTypes,
        plus1Impl = plus1Impl
      )

      defaultKase(
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

      kaseLabelsClass(
        ct = ct,
        parametersPlural = parametersPlural,
        argToLabelPairs = argToLabelPairs,
        kaseSimpleName = kaseSimpleName,
        kaseLabelSimpleName = kaseLabelSimpleName,
        labelsProperties = labelsProperties,
        labels = labels
      )

      kaseFun(
        typesString = typesString,
        parametersPlural = parametersPlural,
        paramsString = paramsString,
        kaseLabelSimpleName = kaseLabelSimpleName,
        kaseSimpleName = kaseSimpleName,
        withLabelCalls = withLabelCalls,
        args = args2,
        kaseTypes = kaseTypes
      )

      testFun(
        typesKaseEnvironment = typesKaseEnvironment,
        typesString = typesString,
        paramsString = paramsString,
        kaseLabelSimpleName = kaseLabelSimpleName,
        argsStringWithLabels = argsStringWithLabels,
        kaseSimpleName = kaseSimpleName,
        args = args2,
        kaseTypes = kaseTypes
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

      asTests_Destructured(
        args = args2,
        kaseTypes = kaseTypes
      )

      // asTests_Destructured_TestEnvironment(
      //   args = args2,
      //   kaseTypes = kaseTypes
      // )

      // testFactory_vararg_Destructured_TestEnvironment(
      //   kdoc = testFactoryKdoc,
      //   args = args2,
      //   kaseTypes = kaseTypes
      // )

      // testFactory_iterable_Destructured_TestEnvironment(
      //   kdoc = testFactoryKdoc,
      //   args = args2,
      //   kaseTypes = kaseTypes
      // )

      testFactory_vararg_Destructured(
        kdoc = testFactoryKdoc,
        args = args2,
        kaseTypes = kaseTypes
      )

      testFactory_Iterable_Destructured(
        kdoc = testFactoryKdoc,
        args = args2,
        kaseTypes = kaseTypes
      )

      labelsFun(
        labelsParamsDefaults = labelsParamsDefaults,
        kaseLabelSimpleName = kaseLabelSimpleName,
        kaseSimpleName = kaseSimpleName,
        labels = labels
      )

      timesFunctions(ct = ct, aArgs = args2, aTypes = kaseTypes)
    }
      .fixBlankLines()

    val file = overloads.resolve("Kase$ct.kt")
    file.writeText(txt)
  }
}
