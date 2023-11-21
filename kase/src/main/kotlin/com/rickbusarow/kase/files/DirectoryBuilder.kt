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

import com.rickbusarow.kase.files.DirectoryBuilder.FileWithContent
import dev.drewhamilton.poko.Poko
import java.io.File
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.io.path.name
import kotlin.io.path.writeText

/**
 * A DSL for building a directory tree.
 *
 * example:
 * ```
 * buildDirectory(myProjectDir) {
 *   dir("src/main/kotlin") {
 *     kotlinFile(
 *       "MyClass.kt",
 *       """
 *       package com.example
 *
 *       class MyClass
 *       """.trimIndent()
 *     )
 *   }
 * }
 * ```
 *
 * @param file the root directory
 * @param builder adds files and child directories to this root directory
 */
public inline fun buildDirectory(
  file: File,
  builder: DirectoryBuilder.() -> Unit
): File = buildDirectory(
  path = file.toPath(),
  builder = builder
).toFile()

/** */
public inline fun File.directoryBuilder(
  builder: DirectoryBuilder.() -> Unit = {}
): DirectoryBuilder = DirectoryBuilder(path = toPath()).apply(builder)

/** */
public inline fun Path.directoryBuilder(
  builder: DirectoryBuilder.() -> Unit = {}
): DirectoryBuilder = DirectoryBuilder(path = this).apply(builder)

/**
 * A DSL for building a directory tree.
 *
 * example:
 * ```
 * buildDirectory(myProjectDir) {
 *   dir("src/main/kotlin") {
 *     kotlinFile(
 *       "MyClass.kt",
 *       """
 *       package com.example
 *
 *       class MyClass
 *       """.trimIndent()
 *     )
 *   }
 * }
 * ```
 *
 * @param path the root directory
 * @param builder adds files and child directories to this root directory
 */
public inline fun buildDirectory(
  path: Path,
  builder: DirectoryBuilder.() -> Unit
): Path = path.also {
  DirectoryBuilder(path = path)
    .apply(builder)
    .write()
}

/** A DSL for building a directory tree. */
public interface DirectoryBuilder : LanguageInjection<DirectoryBuilder.FileWithContent> {

  /** The [Path] of the directory being built. */
  public val path: Path

  /** The parent directory builder, or null if this is the root. */
  public val parent: DirectoryBuilder?

  /** Creates a child directory with the given [name] and applies the [builder] to it. */
  public fun dir(name: String, builder: DirectoryBuilder.() -> Unit)

  /** Creates a file with the given [relativeName] and [content]. */
  public fun file(relativeName: String, content: String): FileWithContent

  /** Writes the directory tree to this [path]. */
  public fun write(): Path

  /** Shorthand for `"$this${File.separatorChar}$other"`. */
  public operator fun String.div(other: String): String = "$this${File.separatorChar}$other"

  /**
   * Models a "file" with a path and content, but it only exists in memory
   *
   * @property path the path of the file
   * @property content the content of the file
   */
  @Poko
  public class FileWithContent(public val path: Path, public var content: String)

  public companion object {
    /** Creates a new [DirectoryBuilder] instance. */
    public operator fun invoke(path: Path): DirectoryBuilder {
      return DefaultDirectoryBuilder(path, ConcurrentHashMap())
    }

    /** Creates a new [DirectoryBuilder] instance. */
    public operator fun invoke(file: File): DirectoryBuilder {
      return DefaultDirectoryBuilder(file.toPath(), ConcurrentHashMap())
    }

    /** Applies the [builder] to this directory. */
    public inline operator fun <reified T : DirectoryBuilder> T.invoke(builder: T.() -> Unit): T {
      return apply(builder)
    }
  }
}

internal fun String.toPath(): Path = Path(this)

internal class DefaultDirectoryBuilder(
  override val path: Path,
  private val context: ConcurrentHashMap<Path, DefaultDirectoryBuilder>
) : DirectoryBuilder,
  LanguageInjectionInternal<DirectoryBuilder.FileWithContent> by LanguageInjectionImpl() {

  init {
    context[path] = this
  }

  override val parent: DirectoryBuilder?
    get() = path.parent?.let {
      context.computeIfAbsent(it) { parentPath ->
        DefaultDirectoryBuilder(parentPath, context)
      }
    }

  @PublishedApi
  internal val childDirs: MutableMap<Path, DefaultDirectoryBuilder> = mutableMapOf()

  @PublishedApi
  internal val files: MutableMap<Path, FileWithContent> = mutableMapOf()

  /** Creates a child directory with the given [name] and applies the [builder] to it. */
  override fun dir(name: String, builder: DirectoryBuilder.() -> Unit) {
    val child = DefaultDirectoryBuilder(
      path = path / name,
      context = context
    )
    child.builder()
  }

  /** Creates a file with the given [relativeName] and [content]. */
  override fun file(relativeName: String, content: String): FileWithContent {

    val segments = relativeName.segments()
    val name = segments.last()
    val filePath = path / name

    val file = FileWithContent(path = filePath, content = content)

    if (segments.size > 1) {

      val dirPath = segments.subList(0, segments.lastIndex - 1).joinToString(File.separator)

      dir(dirPath) { files[filePath] = file }
    } else {
      files[filePath] = file
    }

    return file
  }

  /** Writes the directory tree to the given [parentDir]. */
  override fun write(): Path {
    path.createDirectories()

    for (file in files.values) {
      file.path.writeText(file.content)
    }
    for (dir in childDirs.values) {
      dir.write()
    }

    return path
  }

  @PublishedApi
  internal fun String.segments(): List<String> = toPath().normalize().map { it.toString() }
}
