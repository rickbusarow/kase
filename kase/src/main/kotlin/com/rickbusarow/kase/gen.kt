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

package com.rickbusarow.kase

import java.io.File

fun main() {

  val txt = buildString {

    appendLine(
      """
      @file:Suppress("ktlint")

      package com.rickbusarow.kase

      import org.junit.jupiter.api.DynamicNode
      import java.util.stream.Stream

      """.trimIndent()
    )

    for (ct in (1..22)) {

      val nums = 1..ct

      val types = nums.map { "A$it" }
      val typesString = types.joinToString(", ", "<", ">")
      val typesWithVarianceString = types.joinToString(", ", "<", ">") { "out $it" }

      val args = nums.map { "a$it" }
      val argsString = args.joinToString(", ")
      val argsStringWithLabels = args.joinToString(", ") { "$it = $it" }

      val propertyReads = args.joinToString(", ") { "kase.$it" }

      val params = args.zip(types)
      val paramsString = params.joinToString(", ") { "${it.first}: ${it.second}" }

      val propertiesString = params.joinToString(", ") { "val ${it.first}: ${it.second}" }

      val kaseSimpleName = "Kase$ct"
      val kaseLabelSimpleName = "KaseLabels$ct"

      val labels = args.map { "${it}Label" }
      val labelsParams = args.joinToString(",\n  ") { "val ${it}Label: String = \"$it\"" }

      val names = labels.zip(args)
        .joinToString(
          ", ",
          "listOf(",
          ")"
        ) { (label, arg) ->
          "\"\${labels.$label}\${labels.delimiter}\$$arg\""
        }

      val label = labels.zip(args)
        .joinToString(
          "        append(labels.separator)\n",
          "buildString {\n",
          "      }"
        ) { (label, arg) ->
          "        append(\"\${labels.$label}\${labels.delimiter}\$$arg\")\n"
        }

      val iterableParams = nums.zip(types).joinToString(",\n  ") { (i, type) ->
        "args$i: Iterable<$type>"
      }

      val maps = buildString {
        for ((index, arg) in args.dropLast(1).withIndex()) {
          val i = index + 1
          appendLine("${"  ".repeat(i)}args$i.flatMap { $arg ->")
        }
        appendLine("${"  ".repeat(args.size)}args${args.size}.map { ${args.last()} ->")
        appendLine("${"  ".repeat(args.size + 1)}$kaseSimpleName($argsStringWithLabels)")

        for (it in args.indices.reversed()) {
          appendLine("${"  ".repeat(it + 1)}}")
        }
      }.trim()

      appendLine(
        //language=kotlin
        """
        |
        |/* $kaseSimpleName */
        |
        |fun $typesString kase(
        |  $paramsString
        |): $kaseSimpleName$typesString {
        |  return $kaseSimpleName($argsStringWithLabels)
        |}
        |
        |fun $typesString kases(
        |  $iterableParams
        |): List<$kaseSimpleName$typesString> {
        |  return $maps
        |}
        |
        |@JvmName("testFactory$kaseSimpleName")
        |inline fun $typesString testFactory(
        |  kases: Iterable<$kaseSimpleName$typesString>,
        |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
        |  crossinline testAction: ($paramsString) -> Unit
        |): Stream<out DynamicNode> {
        |  return kases.asTests(
        |    testName = { ($argsString) ->
        |      $label
        |    },
        |    testAction = { ($argsString) ->
        |      testAction($argsString)
        |    }
        |  )
        |}
        |
        |@JvmName("testFactory$kaseSimpleName")
        |inline fun $typesString testFactory(
        |  vararg kases: $kaseSimpleName$typesString,
        |  crossinline kaseName: ($kaseSimpleName$typesString) -> String = { it.toString() },
        |  crossinline testAction: ($paramsString) -> Unit
        |): Stream<out DynamicNode> {
        |  return kases.asSequence().asTests(kaseName) { ($argsString) ->
        |    testAction($argsString)
        |  }
        |}
        |
        |@JvmName("testFactory$kaseSimpleName")
        |inline fun $typesString testFactory(
        |  vararg kases: $kaseSimpleName$typesString,
        |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
        |  crossinline testAction: ($paramsString) -> Unit
        |): Stream<out DynamicNode> {
        |  return kases.asSequence().asTests(
        |    testName = { ($argsString) ->
        |      $label
        |    },
        |    testAction = { ($argsString) ->
        |      testAction($argsString)
        |    }
        |  )
        |}
        |
        |data class $kaseSimpleName$typesWithVarianceString($propertiesString) : Kase<$kaseLabelSimpleName> {
        |  override val args: List<Any?> = listOf($argsString)
        |
        |  override fun names(labels: $kaseLabelSimpleName): List<String> {
        |    return $names
        |  }
        |}
        |
        |data class $kaseLabelSimpleName(
        |  override val delimiter: String = ": ",
        |  override val separator: String = " | ",
        |  $labelsParams
        |): KaseLabels
        |
        """.replaceIndentByMargin()
      )
    }
  }

  val f = File(
    "/Users/rbusarow/Development/kase/kase/src/main/kotlin/com/rickbusarow/kase/kases.kt"
  )

  f.writeText(txt)
}
