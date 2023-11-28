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
import java.nio.file.Paths

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

internal val IMPORTS = """
  import com.rickbusarow.kase.files.TestFunctionCoordinates
  import com.rickbusarow.kase.internal.KaseInternal
  import dev.drewhamilton.poko.Poko
  import org.junit.jupiter.api.DynamicNode
  import org.junit.jupiter.api.DynamicTest
  import java.util.stream.Stream

""".trimIndent()

internal const val MAX_PARAMS = 22

internal data class KaseTypes(val number: Int, val args: List<KaseArg>) {

  private val argsValueTypesString: String by lazy { args.valueTypesString }

  /** `Kase3` */
  val kaseInterfaceNoTypes = "Kase$number"

  /** `Kase3<A1, A2, A3>` */
  val kaseInterface: String = "$kaseInterfaceNoTypes<$argsValueTypesString>"

  /**  */
  val kaseSuperInterface: String = if (number == 1) {
    "Kase"
  } else {
    KaseTypes(number - 1, args.dropLast(1)).kaseInterface
  }

  /** `defaultKase2DisplayNameFactory` */
  val defaultDisplayNameFactory: String = "default${kaseInterfaceNoTypes}DisplayNameFactory"

  /** `KaseDisplayNameFactory<Kase2<A1, A2>>` */
  val displayNameFactory: String = "KaseDisplayNameFactory<$kaseInterface>"

  /** `DefaultKase3` */
  val defaultKaseNoTypes = "DefaultKase$number"

  /** `DefaultKase3<A1, A2, A3>` */
  val defaultKase = "DefaultKase$number<$argsValueTypesString>"

  /** `KaseLabels3` */
  val kaseLabels = "KaseLabels$number"

  /** `TestEnvironment` */
  val testEnvironment = "TestEnvironment"

  /** `TestEnvironmentFactory<TestEnvironment, Kase3<A1, A2, A3>>` */
  fun testEnvironmentFactory(environmentType: String) = "TestEnvironmentFactory<$environmentType>"

  /** `KaseParameterWithLabel<A1, A2, A3>` */
  fun kaseParameterWithLabel(argValueType: String) = "KaseParameterWithLabel<$argValueType>"
}

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

  companion object {

    /** `parameter` or `parameters` */
    val List<KaseArg>.parametersPlural
      get() = if (size == 1) "parameter" else "parameters"

    /** [a1, a2, a3, ...] */
    val List<KaseArg>.valueNames: List<String>
      get() = map { it.valueName }

    /** [a1, a2, a3, ...] */
    val List<KaseArg>.valueNamesString: String
      get() = joinToString(", ") { it.valueName }

    /** [A1, A2, A3, ...] */
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
     *   override val a1: A1,
     *   override val a2: A2
     * ```
     */
    val List<KaseArg>.argsValueParams: String
      get() = joinToString(",\n  ") { arg ->
        "override val ${arg.valueName}: ${arg.valueTypeName}"
      }

    /**
     * ```
     *   override val arg1WithLabel: KaseParameterWithLabel<A1>,
     *   override val arg2WithLabel: KaseParameterWithLabel<A2>
     * ```
     */
    val List<KaseArg>.argsWithLabelValueParams: String
      get() = joinToString(",\n  ") { arg ->
        "override val ${arg.valueWithLabelName}: ${arg.valueWithLabelTypeName}"
      }
  }
}

private val workingDir = Paths.get("").toAbsolutePath().toFile()
private val projectRoot = generateSequence(workingDir) { it.parentFile }
  .first { it.resolve("settings.gradle.kts").exists() }

internal val kasesKt = projectRoot
  .resolve("kase-gradle/src/main/kotlin/com/rickbusarow/kase/gradle/kases.kt")
internal val testElements = projectRoot
  .resolve("kase-gradle/src/test/kotlin/com/rickbusarow/kase/gradle/testElements.kt")
internal val overloads = projectRoot
  .resolve("kase/src/main/kotlin/com/rickbusarow/kase/overloads")

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

    val args = nums.map { "a$it" }

    val argsStringWithLabels = args.joinToString(", ") { "$it = $it" }

    val argsTypePairs = args.zip(types)
    val params = argsTypePairs.map { "${it.first}: ${it.second}" }
    val paramsString = params.joinToString(", ")

    val kaseSimpleName = "Kase$ct"

    val iterableParams = nums.map { "args$it" }

    val iterableParamsWithTypes = nums.zip(types).joinToString(",\n  ") { (i, type) ->
      "args$i: Iterable<$type>"
    }

    val parametersPlural = if (ct == 1) "parameter" else "parameters"

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

      kaseInterface(
        ct = ct,
        parametersPlural = parametersPlural,
        args = args2,
        types = kaseTypes
      )

      defaultKase(args = args2, types = kaseTypes)

      defaultDisplayNameFactory(args = args2, types = kaseTypes)

      kaseFun1(args = args2, types = kaseTypes)

      kaseFun2(args = args2, types = kaseTypes)

      testFun(args = args2, types = kaseTypes)

      kasesFun1(
        kaseSimpleName = kaseSimpleName,
        iterableParams = iterableParams,
        iterableParamsWithType = iterableParamsWithTypes,
        typesString = typesString,
        args = args,
        argsStringWithLabels = argsStringWithLabels,
        types = kaseTypes
      )

      asTests_Destructured(
        args = args2,
        kaseTypes = kaseTypes
      )

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

      timesFunctions(ct = ct, aArgs = args2, aTypes = kaseTypes)
    }
      .fixBlankLines()

    val file = overloads.resolve("Kase$ct.kt")
    file.writeText(txt)
  }
}
