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

/** Provides functions for writing files with language injection. */
public interface LanguageInjection<T> {

  /**
   * Creates a t with the given [path] and [content], with Markdown language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   */
  public fun markdown(path: String, @Language("markdown") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Markdown language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   */
  public fun T.markdown(@Language("markdown") content: String): T

  /**
   * Creates a t with the given [path] and [content], with Java language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   */
  public fun java(path: String, @Language("java") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Java language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   */
  public fun T.java(@Language("java") content: String): T

  /**
   * Creates a t with the given [path] and [content], with Groovy language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   */
  public fun groovy(path: String, @Language("groovy") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Groovy language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   */
  public fun T.groovy(@Language("groovy") content: String): T

  /**
   * Creates a t with the given [path] and [content], with Kotlin language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   */
  public fun kotlin(path: String, @Language("kotlin") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Kotlin language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   */
  public fun T.kotlin(@Language("kotlin") content: String): T

  /**
   * Creates a t with the given [path] and [content], with Xml language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   */
  public fun xml(path: String, @Language("xml") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Xml language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   */
  public fun T.xml(@Language("xml") content: String): T

  /** Writes the result of [contentBuilder] to the receiver t. */
  public operator fun T.invoke(contentBuilder: () -> String): T

  public companion object {
    /** Creates a new [LanguageInjection] instance. */
    public operator fun <T> invoke(): LanguageInjection<T> = LanguageInjectionImpl()
  }
}

internal interface LanguageInjectionInternal<T> : LanguageInjection<T> {
  fun createInstance(name: String, content: String): T
  fun T.update(content: String): T
}

internal class LanguageInjectionImpl<T> : LanguageInjection<T>, LanguageInjectionInternal<T> {
  override fun markdown(path: String, content: String): T = createInstance(path, content)
  override fun T.markdown(content: String): T = update(content)
  override fun java(path: String, content: String): T = createInstance(path, content)
  override fun T.java(content: String): T = update(content)
  override fun groovy(path: String, content: String): T = createInstance(path, content)
  override fun T.groovy(content: String): T = update(content)
  override fun kotlin(path: String, content: String): T = createInstance(path, content)
  override fun T.kotlin(content: String): T = update(content)
  override fun xml(path: String, content: String): T = createInstance(path, content)
  override fun T.xml(content: String): T = update(content)
  override fun T.invoke(contentBuilder: () -> String): T = update(contentBuilder())

  override fun createInstance(name: String, content: String): T = error("must be overridden")
  override fun T.update(content: String): T = error("must be overridden")
}
