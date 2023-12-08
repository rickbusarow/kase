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

import org.intellij.lang.annotations.Language

/**
 * Provides functions for writing files with language injection.
 *
 * @since 0.1.0
 */
public interface LanguageInjection<T> {

  /**
   * Creates a t with the given [path] and [content], with Java language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   * @since 0.1.0
   */
  public fun javaFile(path: String, @Language("java") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Java language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   * @since 0.1.0
   */
  public fun T.java(@Language("java") content: String): T

  /**
   * Creates a t with the given [path] and [content], with Groovy language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   * @since 0.1.0
   */
  public fun groovyFile(path: String, @Language("groovy") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Groovy language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   * @since 0.1.0
   */
  public fun T.groovy(@Language("groovy") content: String): T

  /**
   * Creates a t with the given [path] and [content], with Kotlin language injection.
   *
   * @param path the path to the t to create
   * @param content the content to write to the t
   * @return the created t
   * @since 0.1.0
   */
  public fun kotlinFile(path: String, @Language("kotlin") content: String): T

  /**
   * Writes the given [content] to the receiver [T], with Kotlin language injection.
   *
   * @param content the content to write to the t
   * @receiver the t to write to
   * @return the receiver [T]
   * @since 0.1.0
   */
  public fun T.kotlin(@Language("kotlin") content: String): T

  /**
   * Writes the result of [contentBuilder] to the receiver t.
   *
   * @since 0.1.0
   */
  public operator fun T.invoke(contentBuilder: () -> String): T
}
