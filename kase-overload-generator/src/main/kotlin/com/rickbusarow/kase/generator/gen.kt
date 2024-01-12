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

@file:Suppress("MagicNumber", "UnusedPrivateMember", "MemberVisibilityCanBePrivate")

package com.rickbusarow.kase.generator

import com.rickbusarow.kase.generator.Names.kaseMatrixElement
import com.rickbusarow.kase.generator.Names.kaseMatrixKey
import java.io.File

internal val LICENSE = File("build.gradle.kts").readText()
  .substringBefore("*/")
  .plus("*/")

internal object Names {

  data class Fqn(
    val asString: String,
    val simple: String = asString.substringAfterLast(".")
  ) : Comparable<Fqn> {
    fun child(simple: String) = Fqn(asString = "$asString.$simple", simple = simple)
    override fun toString(): String = asString
    override fun compareTo(other: Fqn): Int = asString.compareTo(other.asString)

    companion object {
      fun Iterable<Fqn>.asImports(): String = sorted().joinToString("\n") { "import $it" }
    }
  }

  val basePackage = Fqn("com.rickbusarow.kase")

  val asTests = basePackage.child("asTests")
  val kaseInternalPackage = basePackage.child("internal")
  val kaseInternal = kaseInternalPackage.child("KaseInternal")

  val kaseMatrix = basePackage.child("KaseMatrix")
  val kaseMatrixElement = kaseMatrix.child("KaseMatrixElement")
  val kaseMatrixKey = kaseMatrix.child("KaseMatrixKey")

  val filesPackage = basePackage.child("files")
  val testLocation = filesPackage.child("TestFunctionCoordinates")

  val poko = Fqn("dev.drewhamilton.poko.Poko")
}

internal const val MAX_PARAMS = 22

private fun main() {

  writeTestElements()

  Files.overloadDir.deleteRecursively()
  Files.overloadDir.mkdirs()

  for (ct in (1..MAX_PARAMS)) {

    val args = (1..ct).map { KaseArg(it) }

    val types = KaseTypes(ct, args)

    val txt = buildString {

      appendLine(
        """
        |$LICENSE
        |
        |@file:Suppress(
        |  "DestructuringDeclarationWithTooManyEntries",
        |  "DuplicatedCode",
        |  "MaxLineLength",
        |  "PackageDirectoryMismatch"
        |)
        |@file:JvmMultifileClass
        |
        |package com.rickbusarow.kase
        |
        |import $kaseMatrixElement
        |import $kaseMatrixKey
        |import dev.drewhamilton.poko.Poko
        |import java.util.stream.Stream
        |import org.junit.jupiter.api.DynamicNode
        |import org.junit.jupiter.api.DynamicTest
        |
        """.trimMargin()
      )

      kaseInterface(args = args, types = types)

      defaultKase(args = args, types = types)

      matrixAccessor(args = args, types = types)
      matrixAccessorFactory(args = args, types = types)

      defaultKaseDisplayNameFactory(args = args, types = types)

      kase_displayNameFactory(args = args, types = types)
      kase_displayName(args = args, types = types)

      kases_iterable(args = args, types = types)
      kases_sequence(args = args, types = types)

      timesFunctions(aArgs = args, aTypes = types)
    }
      .fixBlankLines()

    val file = Files.overloadKase(ct)
    file.writeText(txt)
  }
}
