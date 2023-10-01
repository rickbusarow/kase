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

package com.rickbusarow.kase.stdlib

import java.io.File
import java.util.Locale

/** Converts all line separators in the receiver string to use `\n`. */
public fun String.normaliseLineSeparators(): String = replace("\r\n|\r".toRegex(), "\n")

/**
 * Removes all occurrences of specified strings from the receiver string.
 *
 * @param strings Strings to be removed from the receiver string.
 * @return A new string with all occurrences of specified strings removed.
 */
public fun String.remove(vararg strings: String): String = strings.fold(this) { acc, string ->
  acc.replace(string, "")
}

/**
 * Removes all matches of specified regular expressions from the receiver string.
 *
 * @param patterns Regular expressions to be removed from the receiver string.
 * @return A new string with all matches of specified regular expressions removed.
 */
public fun String.remove(vararg patterns: Regex): String = patterns.fold(this) { acc, regex ->
  acc.replace(regex, "")
}

/** Removes ANSI controls like `\u001B[]33m` */
public fun String.noAnsi(): String = remove("""\u001B\[[;\d]*m""".toRegex())

/** replace ` ` with `·` */
public val String.interpuncts: String get() = replace(" ", "·")

/** replace `·` with ` ` */
public val String.noInterpuncts: String get() = replace("·", " ")

/**
 * Decapitalizes the first character of this [String] using the specified [locale].
 *
 * @param locale The [Locale] to be used for decapitalization. Defaults to [Locale.US].
 * @receiver The original String.
 * @return The string with the first character decapitalized.
 */
public fun String.decapitalize(locale: Locale = Locale.US): String =
  replaceFirstChar { it.lowercase(locale) }

/**
 * Capitalizes the first character of this [String] using the specified [locale].
 *
 * @param locale The [Locale] to be used for capitalization. Defaults to [Locale.US].
 * @receiver The original String.
 * @return The string with the first character capitalized.
 */
public fun String.capitalize(locale: Locale = Locale.US): String =
  replaceFirstChar { it.uppercase(locale) }

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

/**
 * Removes various bits of noise and machine-specific variables from a console or report output.
 * Cleans the provided string by applying various modifications such as normalising line separators,
 * using relative paths, enforcing Unix file separators, and removing specific strings or patterns.
 *
 * @param workingDir The working directory that will be used when making paths relative.
 * @receiver The raw string that needs to be cleaned.
 * @return The cleaned string after all the modifications have been applied.
 */
internal fun String.cleanOutput(workingDir: File): String {
  return normaliseLineSeparators()
    .useRelativePaths(workingDir)
    .alwaysUnixFileSeparators()
    .remove("in [\\d.]+ seconds\\.".toRegex())
    .noAnsi()
    .mapLines { it.trimEnd() }
    .trimEnd()
    .trimStart('\n')
}

/** replace absolute paths with relative ones */
internal fun String.useRelativePaths(workingDir: File): String {
  return alwaysUnixFileSeparators()
    .remove(
      // order matters here!!  absolute must go first
      workingDir.absolutePath.alwaysUnixFileSeparators(),
      workingDir.path.alwaysUnixFileSeparators()
    )
}

/** Replace Windows file separators with Unix ones, just for string comparison in tests */
internal fun String.alwaysUnixFileSeparators(): String = replace(File.separator, "/")

internal fun String.osFileSeparators(): String {
  return if ("win" in System.getProperty("os.name").lowercase()) {
    replace('/', File.separatorChar)
  } else {
    replace('\\', File.separatorChar)
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
public fun <T> List<T>.joinToStringIndexed(
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
 */
public inline fun StringBuilder.indent(
  leadingIndent: String = "  ",
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
 */
public fun CharSequence.prependContinuationIndent(
  continuationIndent: String,
  skipBlankLines: Boolean = true
): String = mapLinesIndexed { i, line ->
  when {
    i == 0 -> line
    skipBlankLines && line.isBlank() -> line
    else -> "$continuationIndent$line"
  }
}
