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

import dev.drewhamilton.poko.Poko
import org.intellij.lang.annotations.Language
import java.io.File
import java.nio.file.Path
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
  DirectoryBuilder(simpleName = path.name)
    .apply(builder)
    .write(parentDir = path.parent!!)
}

/** A DSL for building a directory tree. */
public class DirectoryBuilder(
  private val simpleName: String
) {

  init {
    check(!simpleName.contains(File.separatorChar)) {

      val realSimpleName = Path(simpleName).name

      "This directory's simpleName must not contain a file path separator " +
        "('${File.separatorChar}').  It should be the name of the directory itself, " +
        "like 'src' or 'main'.  In this case it seems to be '$realSimpleName'."
    }
  }

  @PublishedApi
  internal val childDirs: MutableMap<String, DirectoryBuilder> = mutableMapOf()

  @PublishedApi
  internal val files: MutableMap<String, FileWithContent> = mutableMapOf()

  /** Applies the [builder] to this directory. */
  public inline operator fun invoke(builder: DirectoryBuilder.() -> Unit): DirectoryBuilder {
    return apply(builder)
  }

  /** Creates a child directory with the given [name] and applies the [builder] to it. */
  public inline fun dir(name: String, builder: DirectoryBuilder.() -> Unit) {
    dir(segments = name.segments(), builder = builder)
  }

  /** Creates a child directory with the given [name] and applies the [builder] to it. */
  public inline fun dir(vararg names: String, builder: DirectoryBuilder.() -> Unit) {
    dir(segments = names.flatMap { it.segments() }, builder = builder)
  }

  /**
   * Creates child directories for the given [segments] and applies the [builder] to the last one.
   */
  public inline fun dir(segments: List<String>, builder: DirectoryBuilder.() -> Unit) {
    var currentSegments = segments
    var currentChild: DirectoryBuilder? = null

    while (currentSegments.isNotEmpty()) {
      currentChild = (currentChild ?: this).childDirs.getOrPut(currentSegments.first()) {
        DirectoryBuilder(currentSegments.first())
      }

      if (currentSegments.size > 1) {
        currentSegments = currentSegments.subList(1, currentSegments.size)
      } else {
        currentChild.builder()
        break
      }
    }
  }

  /** Creates a file with the given [nameWithExtension] and [content]. */
  public fun file(nameWithExtension: String, content: String): DirectoryBuilder = apply {
    file(nameWithExtension.segments(), content)
  }

  private fun file(segments: List<String>, content: String): DirectoryBuilder = apply {

    val nameWithoutExtension = segments.last()
    if (segments.size > 1) {
      dir(segments.subList(0, segments.lastIndex - 1)) {
        files[nameWithoutExtension] = FileWithContent(
          nameWithExtension = nameWithoutExtension,
          content = content
        )
      }
    } else {
      files[nameWithoutExtension] = FileWithContent(
        nameWithExtension = nameWithoutExtension,
        content = content
      )
    }
  }

  /**
   * Creates a file with the given [nameWithExtension] and
   * [content], with Kotlin language injection in the IDE.
   */
  public fun kotlinFile(
    nameWithExtension: String,
    @Language("kotlin") content: String
  ): DirectoryBuilder = file(nameWithExtension = nameWithExtension, content = content)

  /**
   * Creates a file with the given [nameWithExtension] and
   * [content], with Groovy language injection in the IDE.
   */
  public fun groovyFile(
    nameWithExtension: String,
    @Language("groovy") content: String
  ): DirectoryBuilder = file(nameWithExtension = nameWithExtension, content = content)

  /**
   * Creates a file with the given [nameWithExtension] and
   * [content], with Java language injection in the IDE.
   */
  public fun javaFile(
    nameWithExtension: String,
    @Language("java") content: String
  ): DirectoryBuilder = file(nameWithExtension = nameWithExtension, content = content)

  /**
   * Creates a file with the given [nameWithExtension] and
   * [content], with XML language injection in the IDE.
   */
  public fun xmlFile(
    nameWithExtension: String,
    @Language("xml") content: String
  ): DirectoryBuilder = file(nameWithExtension = nameWithExtension, content = content)

  private fun String.toPath(): Path = Path(this)

  /** Writes the directory tree to the given [parentDir]. */
  public fun write(parentDir: String): Path = write(parentDir.toPath())

  /** Writes the directory tree to the given [parentDir]. */
  public fun write(parentDir: File): File = write(parentDir.toPath()).toFile()

  /** Writes the directory tree to the given [parentDir]. */
  public fun write(parentDir: Path): Path {
    val dirPath = parentDir / simpleName
    dirPath.createDirectories()

    for (file in files.values) {
      val fileAsPath = dirPath / file.nameWithExtension
      fileAsPath.writeText(file.content)
    }
    for (dir in childDirs.values) {
      dir.write(dirPath)
    }

    return dirPath
  }

  /** Shorthand for `"$this${File.separatorChar}$other"`. */
  public operator fun String.div(other: String): String = "$this${File.separatorChar}$other"

  @PublishedApi
  internal fun String.segments(): List<String> = toPath().normalize().map { it.toString() }

  @Poko
  @PublishedApi
  internal class FileWithContent(val nameWithExtension: String, val content: String)
}
