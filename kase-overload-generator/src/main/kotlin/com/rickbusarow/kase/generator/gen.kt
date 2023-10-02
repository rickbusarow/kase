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

private val FILE_ANNOTATIONS = """
  @file:Suppress("PackageDirectoryMismatch")
  @file:JvmMultifileClass
  @file:JvmName("KasesKt")
""".trimIndent()

@Suppress("SpellCheckingInspection")
private val IMPORTS = """
  import com.rickbusarow.kase.KaseParameterWithLabel.Companion.element
  import dev.drewhamilton.poko.Poko
  import org.junit.jupiter.api.DynamicNode
  import java.util.stream.Stream
""".trimIndent()

private fun main() {

  for (ct in (1..22)) {
    val txt = buildString {

      appendLine(
        """
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
      val labelsParams = args.joinToString(",\n  ") { "${it}Label: String" }
      val labelsParamsDefaults = args.joinToString(",\n  ") { "${it}Label: String = \"$it\"" }

      val iterableParams = nums.zip(types).joinToString(",\n  ") { (i, type) ->
        "args$i: Iterable<$type>"
      }

      val argToLabelPairs = args.zip(labels)
      val elementCalls = argToLabelPairs.joinToString(",\n    ") { (value, label) ->
        "element(value = $value, label = labels.$label)"
      }

      val typesK = (listOf("K") + types)
        .joinToString(", ", "<", ">")
      val typesKaseEnvironment = (listOf("T", "K") + types)
        .joinToString(", ", "<", ">")

      val testActionDestructured = "($paramsString) -> Unit"
      val testActionK = "(kase: K) -> Unit"
      val testActionTDestructured = "T.($paramsString) -> Unit"
      val testActionTK = "T.(kase: K) -> Unit"
      val testActionTNoArg = "T.() -> Unit"

      kaseFun(typesString, paramsString, kaseLabelSimpleName, kaseSimpleName, elementCalls)
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
        |
        """.trimMargin()
      )

      val lambdas = listOf(
        "Kase" to testActionK,
        "Destructured" to testActionDestructured
      )
      val testEnvironmentLambdas = listOf(
        "Destructured" to testActionTDestructured
        // , "NoArg" to testActionTNoArg
      )

      for ((suffix, lambdaSignature) in lambdas) {
        appendLine(
          """
          /** */
          @JvmName("asTests$kaseSimpleName$suffix")
          public inline fun $typesK Iterable<$kaseSimpleName$typesString>.asTests(
            labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
            crossinline testAction: $lambdaSignature
          ): Stream<out DynamicNode>
            where K : $kaseSimpleName$typesString {
            return testFactory(this@asTests, labels, testAction)
          }

          """.trimIndent()
            .comment()
        )
      }

      for ((suffix, lambdaSignature) in testEnvironmentLambdas) {
        appendLine(
          """
            /** */
            context(TestEnvironmentFactory<T>)
            public inline fun $typesKaseEnvironment Iterable<K>.asTests(
              labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
              crossinline testAction: $lambdaSignature
            ): Stream<out DynamicNode>
              where T : TestEnvironment<K>,
                    K : $kaseSimpleName$typesString {
              return testFactory(this@asTests, labels, testAction)
            }

          """.trimIndent()
        )
      }

      for ((suffix, lambdaSignature) in lambdas) {
        appendLine(
          """
            /** */
            @JvmName("testFactory$kaseSimpleName$suffix")
            public inline fun $typesK testFactory(
              vararg kases: $kaseSimpleName$typesString,
              labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
              crossinline testAction: $lambdaSignature
            ): Stream<out DynamicNode>
              where K : $kaseSimpleName$typesString {
              return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
            }

          """.trimIndent()
            .comment()
        )
      }

      for ((suffix, lambdaSignature) in testEnvironmentLambdas) {
        appendLine(
          """
           /** */
           context(TestEnvironmentFactory<T>)
           public inline fun $typesKaseEnvironment testFactory(
             vararg kases: K,
             labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
             crossinline testAction: $lambdaSignature
           ): Stream<out DynamicNode>
             where T : TestEnvironment<K>,
                   K : $kaseSimpleName$typesString {
             return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
           }

          """.trimIndent()
        )
      }

      for ((suffix, lambdaSignature) in lambdas) {
        appendLine(
          """
          /** */
          @JvmName("testFactory$kaseSimpleName$suffix")
          public inline fun $typesK testFactory(
            kases: Iterable<$kaseSimpleName$typesString>,
            labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
            crossinline testAction: $lambdaSignature
          ): Stream<out DynamicNode>
            where K : $kaseSimpleName$typesString {
            return kases.asTests(
              testName = { it.displayName(labels) },
              testAction = { testAction($argsFromItString) }
            )
          }

          """.trimIndent()
            .comment()
        )
      }

      for ((suffix, lambdaSignature) in testEnvironmentLambdas) {
        appendLine(
          """
           /** */
           context(TestEnvironmentFactory<T>)
           public inline fun $typesKaseEnvironment testFactory(
             kases: Iterable<K>,
             labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
             crossinline testAction: $lambdaSignature
           ): Stream<out DynamicNode>
             where T : TestEnvironment<K>,
                   K : $kaseSimpleName$typesString {
             return kases.asTests(
               testName = { it.displayName(labels) },
               testAction = { kase -> testAction($argsFromKaseString) }
             )
           }

          """.trimIndent()

        )
      }

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

      val propertiesString = params.joinToStringIndexed("\n  ") { i, it ->
        """
          /** The ${(i + 1).withOrdinalSuffix()} parameter. */
            public val $it
        """.trimIndent()
      }

      val kasePlus1 = "Kase${ct + 1}"
      val kasePlus1WithTypes = "$kasePlus1${types.joinToString(", ", "<", ", T>")}"

      appendLine(
        """
        |
        |/** A strongly-typed version of [Kase] for $ct parameters. */
        |public interface $kaseSimpleName$typesWithVarianceString : Kase<$kaseLabelSimpleName> {
        |
        |  $propertiesString
        |
        |  override fun <T> plus(label: String, value: T): $kasePlus1WithTypes
        |}
        """.trimMargin()
      )

      val argsElements = args.map { arg -> "${arg}Element" }

      val a = argsElements.zip(types).joinToString(",\n  ") { (element, type) ->
        "val $element: KaseParameterWithLabel<$type>"
      }
      val b = paramsPairs.joinToString("\n  ") { (arg, type) ->
        "override val $arg: $type get() = ${arg}Element.value"
      }

      appendLine(
        """
        |/** */
        |@Poko
        |internal class Default$kaseSimpleName$typesWithVarianceString(
        |  $a
        |) : Kase$ct$typesString, KaseInternal<$kaseLabelSimpleName> {
        |  $b
        |
        |  override val elements: List<KaseParameterWithLabel<Any?>>
        |    get() = listOf(${argsElements.joinToString(", ")})
        |
        |  override fun <T> plus(label: String, value: T): Default$kasePlus1WithTypes {
        |    return Default$kasePlus1(
        |      ${argsElements.joinToString(",\n      ") { "$it = $it" }},
        |      element(value = value, label = label)
        |    )
        |  }
        |}
        |
        """.trimMargin()
      )

      val kaseLabelsKdoc = buildString {
        appendLine("/**")
        appendLine(" * A strongly-typed version of [KaseLabels] for $ct parameters.")
        appendLine(" *")
        for ((arg, label) in argToLabelPairs) {
          appendLine(" * @property $label The label for the [$kaseSimpleName.$arg] parameter.")
        }
        appendLine(" * @property delimiter The delimiter between the label and the value.")
        appendLine(" * @property separator The separator between each label/value pair.")
        appendLine(" * @property prefix The prefix before the first label/value pair.")
        appendLine(" * @property postfix The postfix after the last label/value pair.")
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
        |  override val delimiter: String = ": ",
        |  override val separator: String = " | ",
        |  override val prefix: String = "[",
        |  override val postfix: String = "]"
        |) : KaseLabels {
        |
        |  override val orderedLabels: List<String> by lazy {
        |    listOf(${labels.joinToString(", ")})
        |  }
        |}
        |
        """.replaceIndentByMargin()
      )
    }

    val file = File(
      "/Users/rbusarow/Development/kase/kase/src/" +
        "main/kotlin/com/rickbusarow/kase/overloads/kases$ct.kt"
    )
    file.parentFile.mkdirs()
    file.writeText(txt)
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
    |
    """.trimMargin()
  )
}

private fun StringBuilder.kaseFun(
  typesString: String,
  paramsString: String,
  kaseLabelSimpleName: String,
  kaseSimpleName: String,
  elementCalls: String
) {
  appendLine(
    """
    |
    |/** */
    |public fun $typesString kase(
    |  $paramsString, labels: $kaseLabelSimpleName = $kaseLabelSimpleName()
    |): $kaseSimpleName$typesString {
    |  return Default$kaseSimpleName(
    |    $elementCalls
    |  )
    |}
    |
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
    |
    |/** */
    |context(TestEnvironmentFactory<T>)
    |public fun $typesKaseEnvironment test(
    |  $paramsString,
    |  labels: $kaseLabelSimpleName = $kaseLabelSimpleName(),
    |  testFunctionName: TestFunctionName = TestFunctionName.get(),
    |  testAction: suspend T.() -> Unit
    |) where T : TestEnvironment<K>,
    |        K : $kaseSimpleName$typesString {
    |  test(
    |    kase = kase($argsString, labels),
    |    testFunctionName = testFunctionName,
    |    testAction = testAction
    |  )
    |}
    |
    """.trimMargin()
  )
}

private fun Int.withOrdinalSuffix(): String {
  val mod10 = this % 10
  return when {
    mod10 == 1 && this != 11 -> "${this}st"
    mod10 == 2 && this != 12 -> "${this}nd"
    mod10 == 3 && this != 13 -> "${this}rd"
    else -> "${this}th"
  }
}

/**
 * Creates a string from all the elements separated using [separator]
 * and using the given [prefix] and [postfix] if supplied.
 *
 * If the collection could be huge, you can specify a non-negative value
 * of [limit], in which case only the first [limit] elements will be
 * appended, followed by the [truncated] string (which defaults to "...").
 */
fun <T> List<T>.joinToStringIndexed(
  separator: CharSequence = ", ",
  prefix: CharSequence = "",
  postfix: CharSequence = "",
  limit: Int = -1,
  truncated: CharSequence = "...",
  transform: (Int, T) -> CharSequence
): String {
  return buildString {
    append(prefix)
    var count = 0
    for (element in this@joinToStringIndexed) {
      if (++count > 1) append(separator)
      if (limit < 0 || count <= limit) {
        append(transform(count - 1, element))
      } else {
        break
      }
    }
    if (limit in 0 until count) append(truncated)
    append(postfix)
  }
}

/**
 * performs [transform] on each line
 *
 * Doesn't preserve the original line endings.
 */
public fun CharSequence.mapLines(transform: (String) -> CharSequence): String = lineSequence()
  .joinToString("\n", transform = transform)

/**
 * performs [transform] on each line
 *
 * Doesn't preserve the original line endings.
 */
public fun CharSequence.mapLinesIndexed(transform: (Int, String) -> CharSequence): String =
  lineSequence()
    .mapIndexed(transform)
    .joinToString("\n")

fun String.comment() = trim().mapLines { "// $it" }.let { "\n$it\n" }
