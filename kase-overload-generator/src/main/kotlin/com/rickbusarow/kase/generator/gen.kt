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

    val args = (1..ct).map { KaseArg(it) }

    val types = KaseTypes(ct, args)

    val testFactoryKdoc = buildList {
      val kaseSimpleName = types.kaseInterfaceNoTypes

      add("A test factory which returns a stream of [DynamicNode]s from the given parameters.")
      add("- Each [DynamicTest] in the stream uses its [$kaseSimpleName] element to create")
      add("  a new [TestEnvironment] instance, then executes [testAction].")
      add("- Each [DynamicNode] has a display name which includes the values of the parameters.")
      add("")
      add("@param kases the [$kaseSimpleName]s to use for this test factory")
      add("@param testAction the test action to execute.")
      add("@return a [Stream] of [DynamicNode]s from the given parameters.")
      add("@see $kaseSimpleName")
      add("@see TestEnvironmentFactory")
    }
      .makeKdoc()

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

      kaseInterface(args = args, types = types)

      defaultKase(args = args, types = types)

      defaultKaseDisplayNameFactory(args = args, types = types)

      kase_displayNameFactory(args = args, types = types)
      kase_displayName(args = args, types = types)

      kases_iterable(args = args, types = types)
      kases_sequence(args = args, types = types)

      testFun(args = args, types = types)

      asTests_Destructured(
        args = args,
        kaseTypes = types
      )

      testFactory_vararg(
        kdoc = testFactoryKdoc,
        args = args,
        kaseTypes = types
      )

      testFactory_Iterable(
        kdoc = testFactoryKdoc,
        args = args,
        kaseTypes = types
      )

      timesFunctions(aArgs = args, aTypes = types)
    }
      .fixBlankLines()

    val file = overloads.resolve("Kase$ct.kt")
    file.writeText(txt)
  }
}
