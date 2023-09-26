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

/** Converts all line separators in the receiver string to use `\n`. */
fun String.normaliseLineSeparators(): String = replace("\r\n|\r".toRegex(), "\n")

/**
 * Removes all occurrences of specified strings from the receiver string.
 *
 * @param strings Strings to be removed from the receiver string.
 * @return A new string with all occurrences of specified strings removed.
 */
fun String.remove(vararg strings: String): String = strings.fold(this) { acc, string ->
  acc.replace(string, "")
}

/**
 * Removes all matches of specified regular expressions from the receiver string.
 *
 * @param patterns Regular expressions to be removed from the receiver string.
 * @return A new string with all matches of specified regular expressions removed.
 */
fun String.remove(vararg patterns: Regex): String = patterns.fold(this) { acc, regex ->
  acc.replace(regex, "")
}

/** Removes ANSI controls like `\u001B[]33m` */
fun String.noAnsi(): String = remove("""\u001B\[[;\d]*m""".toRegex())

/** replace ` ` with `·` */
val String.interpuncts: String get() = replace(" ", "·")

/** replace `·` with ` ` */
val String.noInterpuncts: String get() = replace("·", " ")

/**
 * performs [transform] on each line
 *
 * Doesn't preserve the original line endings.
 */
fun CharSequence.mapLines(transform: (String) -> CharSequence): String = lineSequence()
  .joinToString("\n", transform = transform)

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

/**
 * replace absolute paths with relative ones
 *
 *
 */
internal fun String.useRelativePaths(workingDir: File): String {
  return alwaysUnixFileSeparators()
    .remove(
      // order matters here!!  absolute must go first
      workingDir.absolutePath.alwaysUnixFileSeparators(),
      workingDir.path.alwaysUnixFileSeparators()
    )
}

/**
 * Replace Windows file separators with Unix ones, just for string comparison in tests
 *
 *
 */
internal fun String.alwaysUnixFileSeparators(): String = replace(File.separator, "/")
