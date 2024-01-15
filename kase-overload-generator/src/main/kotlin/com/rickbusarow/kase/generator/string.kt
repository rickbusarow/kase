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

package com.rickbusarow.kase.generator

import org.intellij.lang.annotations.Language
import java.util.Locale

@Suppress("MagicNumber")
internal fun Int.withOrdinalSuffix(): String {
  val mod10 = this % 10
  return when {
    mod10 == 1 && this != 11 -> "${this}st"
    mod10 == 2 && this != 12 -> "${this}nd"
    mod10 == 3 && this != 13 -> "${this}rd"
    else -> "${this}th"
  }
}

internal fun String.makeKdoc(): String = lines().makeKdoc()

internal fun List<String>.makeKdoc(): String = joinToString(
  separator = "\n",
  prefix = "/**\n",
  postfix = "\n */"
) { if (it.isBlank()) " *" else " * $it" }

internal fun <T> MutableList<String>.addAll(elements: Iterable<T>, transform: (T) -> String) {
  addAll(elements.map(transform))
}

internal fun String.fixBlankLines(): String {
  return mapLines { it.trimEnd() }
    .replace("""(\n[)}]\n)(?=[/\n]|@(?!file))""".toRegex(), "$1\n")
    .replace(Regex("\\n{3,}"), "\n\n")
    .trimEnd()
    .plus("\n")
}

/**
 * Creates a string from all the elements separated using [separator]
 * and using the given [prefix] and [postfix] if supplied.
 *
 * If the collection could be huge, you can specify a non-negative value
 * of [limit], in which case only the first [limit] elements will be
 * appended, followed by the [truncated] string (which defaults to "...").
 *
 * @since 0.1.0
 */
internal fun <T> List<T>.joinToStringIndexed(
  separator: CharSequence = ", ",
  prefix: CharSequence = "",
  postfix: CharSequence = "",
  limit: Int = -1,
  truncated: CharSequence = "...",
  transform: (Int, T) -> CharSequence
): String = buildString {
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

/**
 * performs [transform] on each line
 *
 * Doesn't preserve the original line endings.
 *
 * @since 0.1.0
 */
internal fun CharSequence.mapLines(transform: (String) -> CharSequence): String = lineSequence()
  .joinToString("\n", transform = transform)

/**
 * performs [transform] on each line
 *
 * Doesn't preserve the original line endings.
 *
 * @since 0.1.0
 */
internal fun CharSequence.mapLinesIndexed(
  transform: (Int, String) -> CharSequence
): String = lineSequence()
  .mapIndexed(transform)
  .joinToString("\n")

internal fun String.replaceRegex(@Language("regexp") pattern: String, replacement: String): String {
  return replace(Regex(pattern), replacement)
}

/**
 * shorthand for `replace(___, "")` against multiple tokens
 *
 * @since 0.1.0
 */
internal fun String.remove(vararg strings: String): String = strings.fold(this) { acc, string ->
  acc.replace(string, "")
}

/**
 * shorthand for `replace(___, "")` against multiple tokens
 *
 * @since 0.1.0
 */
internal fun String.remove(vararg regex: Regex): String = regex.fold(this) { acc, reg ->
  acc.replace(reg, "")
}

internal fun String.trimIndentAfterFirstLine(): String {
  val split = lines()
  val first = split.first()
  val remaining = split.drop(1).joinToString("\n").trimIndent()
  return "$first\n$remaining"
}

/**
 * example:
 *
 * ```
 * override fun toString() = buildString {
 *   appendLine("SomeClass(")
 *   indent {
 *     appendLine("prop1=$prop1")
 *     appendLine("prop2=$prop2")
 *   }
 *   appendLine(")")
 * }
 * ```
 *
 * @since 0.1.0
 */
inline fun StringBuilder.indent(
  builder: StringBuilder.() -> Unit
) {
  indent(leadingIndent = "  ", builder = builder)
}

/**
 * example:
 *
 * ```
 * override fun toString() = buildString {
 *   appendLine("SomeClass(")
 *   indent {
 *     appendLine("prop1=$prop1")
 *     appendLine("prop2=$prop2")
 *   }
 *   appendLine(")")
 * }
 * ```
 *
 * @since 0.1.0
 */
inline fun StringBuilder.indent(
  leadingIndent: String,
  continuationIndent: String = leadingIndent,
  builder: StringBuilder.() -> Unit
) {

  append(
    buildString {
      append(leadingIndent)

      builder()
    }
      .prependContinuationIndent(continuationIndent)
  )
}

/**
 * Prepends [continuationIndent] to every line of the original string.
 *
 * Doesn't preserve the original line endings.
 *
 * @since 0.1.0
 */
fun CharSequence.prependContinuationIndent(
  continuationIndent: String,
  skipBlankLines: Boolean = true
): String = mapLinesIndexed { i, line ->
  when {
    i == 0 -> line
    skipBlankLines && line.isBlank() -> line
    else -> "$continuationIndent$line"
  }
}

/**
 * Decapitalizes the first character of this [String] using the specified [locale].
 *
 * @param locale The [Locale] to be used for decapitalization. Defaults to [Locale.US].
 * @receiver The original String.
 * @return The string with the first character decapitalized.
 * @since 0.1.0
 */
fun String.decapitalize(locale: Locale = Locale.US): String =
  replaceFirstChar { it.lowercase(locale) }
