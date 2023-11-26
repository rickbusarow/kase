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

import com.rickbusarow.kase.KaseDsl
import com.rickbusarow.kase.files.internal.DefaultDirectoryBuilder
import dev.drewhamilton.poko.Poko
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path

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
): File = DirectoryBuilder(file = file)
  .apply(builder)
  .write()

/**
 * Builds a directory using the specified builder function and returns a DirectoryBuilder object.
 *
 * @param builder The builder function to customize the directory.
 * @return A DirectoryBuilder object for the receiver file path.
 */
public inline fun File.directoryBuilder(
  builder: DirectoryBuilder.() -> Unit = {}
): DirectoryBuilder = DirectoryBuilder(file = this).apply(builder)

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
): Path = buildDirectory(file = path.toFile(), builder = builder).toPath()

/**
 * Builds a directory using the specified builder function and returns a DirectoryBuilder object.
 *
 * @param builder The builder function to customize the directory.
 * @return A DirectoryBuilder object for the receiver file path.
 */
public inline fun Path.directoryBuilder(
  builder: DirectoryBuilder.() -> Unit = {}
): DirectoryBuilder = DirectoryBuilder(file = toFile()).apply(builder)

/**
 * A DSL for building a directory tree.
 *
 * example:
 * ```
 * DirectoryBuilder(myProjectDir) {
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
 */
@KaseDsl
public interface DirectoryBuilder : LanguageInjection<DirectoryBuilder.FileWithContent> {

  /** The [Path] of the directory being built. */
  public val path: File

  /** The parent directory builder, or null if this is the root. */
  public val parent: DirectoryBuilder?

  /** Creates a child directory with the given [relativePath] and applies the [builder] to it. */
  public fun dir(relativePath: String, builder: DirectoryBuilder.() -> Unit)

  /**
   * Creates a child directory with the given relative
   * [pathSegments] and applies the [builder] to it.
   */
  public fun dir(pathSegments: Collection<String>, builder: DirectoryBuilder.() -> Unit) {
    check(pathSegments.isNotEmpty()) { "pathSegments must not be empty" }
    dir(pathSegments.joinToPathString(), builder)
  }

  /** Creates a file with the given [relativePath] and [content]. */
  public fun file(relativePath: String, content: String): FileWithContent

  /** Creates a file with the given [pathSegments] and [content]. */
  public fun file(pathSegments: Collection<String>, content: String): FileWithContent {
    check(pathSegments.isNotEmpty()) { "pathSegments must not be empty" }
    return file(pathSegments.joinToPathString(), content)
  }

  /** Writes the directory tree to this [path]. */
  public fun write(): File

  /**
   * Joins two "path" segments together using the
   * system-dependent [File.separatorChar] as a separator.
   *
   * ```
   * val joined = "path" / "to" / "file.txt"
   * ```
   */
  public operator fun String.div(other: String): String = "$this${File.separatorChar}$other"

  /**
   * Joins the receiver path segments together using the
   * system-dependent [File.separatorChar] as a separator.
   *
   * @receiver A list of path segments, like `["path", "to", "file.txt"]`
   */
  public fun Iterable<String>.joinToPathString(): String = joinToString(File.separator)

  /**
   * Models a "file" with a path and content, but it only exists in memory
   *
   * @property path the path of the file
   * @property content the content of the file
   */
  @Poko
  public class FileWithContent(public val path: File, public var content: String)

  public companion object {
    /** Creates a new [DirectoryBuilder] instance. */
    public operator fun invoke(file: File): DirectoryBuilder {
      return DefaultDirectoryBuilder(path = file, parent = null)
    }

    /** Creates a new [DirectoryBuilder] instance. */
    public operator fun invoke(path: Path): DirectoryBuilder {
      return DefaultDirectoryBuilder(path = path.toFile(), parent = null)
    }

    /** Applies the [builder] to this directory. */
    public inline operator fun <reified T : DirectoryBuilder> T.invoke(builder: T.() -> Unit): T {
      return apply(builder)
    }
  }
}
