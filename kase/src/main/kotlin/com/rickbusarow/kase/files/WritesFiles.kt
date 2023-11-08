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

package com.rickbusarow.kase.files

import com.rickbusarow.kase.stdlib.createSafely
import org.intellij.lang.annotations.Language
import java.io.File

/** Provides functions for writing files with language injection. */
public interface WritesFiles {

  /**
   * Creates a file with the given [path] and [content], with Markdown language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun markdown(path: String, @Language("markdown") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Markdown language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  public fun File.markdown(@Language("markdown") content: String): File =
    createSafely(content.trimIndent())

  /**
   * Creates a file with the given [path] and [content], with Java language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun java(path: String, @Language("java") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Java language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  public fun File.java(@Language("java") content: String): File = createSafely(content.trimIndent())

  /**
   * Creates a file with the given [path] and [content], with Groovy language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun groovy(path: String, @Language("groovy") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Groovy language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  public fun File.groovy(@Language("groovy") content: String): File =
    createSafely(content.trimIndent())

  /**
   * Creates a file with the given [path] and [content], with Kotlin language injection.
   *
   * @param path the path to the file to create
   * @param content the content to write to the file
   * @return the created file
   */
  public fun kotlin(path: String, @Language("kotlin") content: String): File =
    File(path).createSafely(content.trimIndent())

  /**
   * Writes the given [content] to the receiver [File], with Kotlin language injection.
   *
   * @param content the content to write to the file
   * @receiver the file to write to
   * @return the receiver [File]
   */
  public fun File.kotlin(@Language("kotlin") content: String): File =
    createSafely(content.trimIndent())

  /** Writes the result of [contentBuilder] to the receiver file. */
  public operator fun File.invoke(contentBuilder: () -> String) {
    createSafely(contentBuilder().trimIndent())
  }
}
