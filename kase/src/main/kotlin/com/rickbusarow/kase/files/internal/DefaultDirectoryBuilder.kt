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
import com.rickbusarow.kase.files.DirectoryBuilder.FileWithContent
import com.rickbusarow.kase.stdlib.div
import java.io.File
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.Path

/** The implementation-details api of a [DirectoryBuilder]. */
public interface DirectoryBuilderInternal : DirectoryBuilder {
  /** All directories that have been added to this specific directory. */
  public val childDirs: ConcurrentHashMap<File, DirectoryBuilderInternal>

  /** All files that have been added to this specific directory. */
  public val files: ConcurrentHashMap<File, FileWithContent>
}

private class FileWithContentInjection : FileInjection<FileWithContent> {

  lateinit var factory: (String, String) -> FileWithContent

  fun init(factory: (String, String) -> FileWithContent) {
    this.factory = factory
  }

  override fun createInstance(
    name: String,
    content: String
  ): FileWithContent = factory(name, content)

  override fun update(t: FileWithContent, content: String): FileWithContent {
    return t.apply { this.content = content }
  }
}

internal class DefaultDirectoryBuilder private constructor(
  override val path: File,
  override val parent: DirectoryBuilder?,
  private val fileInjection: FileWithContentInjection
) : DirectoryBuilderInternal,
  LanguageInjectionInternal<FileWithContent> by DefaultLanguageInjection(fileInjection) {

  constructor(path: File, parent: DirectoryBuilder?) : this(
    path = path,
    parent = parent,
    fileInjection = FileWithContentInjection()
  ) {
    fileInjection.init { name, content -> file(name, content) }
  }

  override val childDirs: ConcurrentHashMap<File, DirectoryBuilderInternal> = ConcurrentHashMap()
  override val files: ConcurrentHashMap<File, FileWithContent> = ConcurrentHashMap()

  /** Creates a child directory with the given [relativePath] and applies the [builder] to it. */
  override fun dir(relativePath: String, builder: DirectoryBuilder.() -> Unit) {
    val childPath = path / relativePath
    val child = DefaultDirectoryBuilder(path = childPath, parent = this)
    childDirs[childPath] = child
    child.builder()
  }

  /** Creates a file with the given [relativePath] and [content]. */
  override fun file(relativePath: String, content: String): FileWithContent {
    return file(relativePath.segments(), content)
  }

  override fun file(pathSegments: Collection<String>, content: String): FileWithContent {

    val filePath = path / pathSegments.joinToPathString()

    val file = FileWithContent(path = filePath, content = content)

    if (pathSegments.size > 1) {
      dir(pathSegments.toList().dropLast(1)) { file(pathSegments.last(), content) }
    } else {
      files[filePath] = file
    }

    return file
  }

  /** Writes the directory tree to the configured [path]. */
  override fun write(): File {
    path.mkdirs()

    for (file in files.values) {
      file.path.writeText(file.content)
    }

    for ((_, builder) in childDirs) {
      builder.write()
    }

    return path
  }

  private fun String.toPath(): Path = Path(this)

  @PublishedApi
  internal fun String.segments(): List<String> = toPath().normalize().map { it.toString() }
}
