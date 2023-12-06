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

      testFactory_vararg(
        kdoc = testFactoryKdoc,
        args = args2,
        kaseTypes = kaseTypes
      )

      testFactory_Iterable(
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
