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

package com.rickbusarow.kase.files.internal

import com.rickbusarow.kase.files.DirectoryBuilder
import com.rickbusarow.kase.stdlib.createSafely
import com.rickbusarow.kase.stdlib.div
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path

private class JavaFileFileInjection : FileInjection<File> {

  lateinit var factory: (String, String) -> File

  fun init(factory: (String, String) -> File) {
    this.factory = factory
  }

  override fun createInstance(
    name: String,
    content: String
  ): File = factory(name, content)

  override fun update(t: File, content: String): File {
    return t.apply { createSafely(content) }
  }
}

internal class DefaultDirectoryBuilder private constructor(
  override val path: File,
  override val parent: DirectoryBuilder?,
  private val fileInjection: JavaFileFileInjection
) : DirectoryBuilder,
  LanguageInjectionInternal<File> by DefaultLanguageInjection(fileInjection) {

  constructor(path: File, parent: DirectoryBuilder?) : this(
    path = path,
    parent = parent,
    fileInjection = JavaFileFileInjection()
  ) {
    fileInjection.init { name, content -> file(name, content) }
  }

  /**
   * Creates a child directory with the given [relativePath] and applies the [builder] to it.
   *
   * @since 0.1.0
   */
  override fun dir(relativePath: String, builder: DirectoryBuilder.() -> Unit) {
    val childPath = path / relativePath
    val child = DefaultDirectoryBuilder(path = childPath, parent = this)
    child.builder()
  }

  /**
   * Creates a file with the given [relativePath] and [content].
   *
   * @since 0.1.0
   */
  override fun file(relativePath: String, content: String): File {
    val file = path / relativePath
    file.createSafely(content)
    return file
  }

  override fun file(pathSegments: Collection<String>, content: String): File {
    return file(pathSegments.joinToPathString(), content)
  }

  private fun String.toPath(): Path = Path(this)

  @PublishedApi
  internal fun String.segments(): List<String> = toPath().normalize().map { it.toString() }
}
