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

package com.rickbusarow.kase

import dev.drewhamilton.poko.Poko
import okio.FileSystem
import okio.Path.Companion.toPath
import org.intellij.lang.annotations.Language
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.name

public inline fun buildDirectory(
  file: File,
  builder: DirectoryBuilder.() -> Unit
): File = buildDirectory(
  path = file.toPath(),
  builder = builder
).toFile()

public inline fun buildDirectory(
  path: Path,
  builder: DirectoryBuilder.() -> Unit
): Path = path.also {
  DirectoryBuilder(simpleName = path.name)
    .apply(builder)
    .write(parentDir = path.parent!!)
}

public class DirectoryBuilder @PublishedApi internal constructor(
  private val simpleName: String
) {

  init {
    check(!simpleName.contains(File.separatorChar)) {

      val realSimpleName = simpleName.toPath().name

      "This directory's simpleName must not contain a file path separator " +
        "('${File.separatorChar}').  It should be the name of the directory itself, " +
        "like 'src' or 'main'."
    }
  }

  @PublishedApi
  internal val childDirs: MutableMap<String, DirectoryBuilder> = mutableMapOf()

  @PublishedApi
  internal val files: MutableMap<String, FileWithContent> = mutableMapOf()

  public inline fun dir(name: String, builder: DirectoryBuilder.() -> Unit) {
    dir(segments = name.segments(), builder = builder)
  }

  public inline fun dir(segments: List<String>, builder: DirectoryBuilder.() -> Unit) {

    val child = childDirs.getOrPut(segments.first()) {
      DirectoryBuilder(segments.first())
    }

    if (segments.size > 1) {
      child.dirInternal(segments = segments.subList(1, segments.lastIndex), builder = builder)
    } else {
      child.builder()
    }
  }

  @PublishedApi
  internal inline fun dirInternal(segments: List<String>, builder: DirectoryBuilder.() -> Unit) {
    dir(segments = segments, builder = builder)
  }

  public fun file(nameWithExtension: String, content: String): DirectoryBuilder = apply {
    file(nameWithExtension.segments(), content)
  }

  public fun file(segments: List<String>, content: String): DirectoryBuilder = apply {
    Path(segments)
    val nameWithoutExtension = segments.last()
    if (segments.size > 1) {
      dir(segments.subList(0, segments.lastIndex - 1)) {
        files[nameWithoutExtension] =
          (FileWithContent(nameWithExtension = nameWithoutExtension, content = content))
      }
    } else {
      files[nameWithoutExtension] =
        (FileWithContent(nameWithExtension = nameWithoutExtension, content = content))
    }
  }

  public fun kotlinFile(
    nameWithExtension: String,
    @Language("kotlin") content: String
  ): DirectoryBuilder {
    return file(nameWithExtension = nameWithExtension, content = content)
  }

  public fun groovyFile(
    nameWithExtension: String,
    @Language("groovy") content: String
  ): DirectoryBuilder {
    return file(nameWithExtension = nameWithExtension, content = content)
  }

  public fun javaFile(
    nameWithExtension: String,
    @Language("java") content: String
  ): DirectoryBuilder {
    return file(nameWithExtension = nameWithExtension, content = content)
  }

  public fun xmlFile(
    nameWithExtension: String,
    @Language("xml") content: String
  ): DirectoryBuilder {
    return file(nameWithExtension = nameWithExtension, content = content)
  }

  public fun write(parentDir: String, fileSystem: FileSystem = FileSystem.SYSTEM): Path {
    return write(parentDir.toPath(), fileSystem)
  }

  public fun write(parentDir: Path, fileSystem: FileSystem = FileSystem.SYSTEM): Path {
    val dirPath = parentDir / simpleName
    fileSystem.createDirectory(dirPath, mustCreate = false)

    for (file in files.values) {
      fileSystem.write(dirPath / file.nameWithExtension) {
        writeUtf8(file.content)
      }
    }
    for (dir in childDirs.values) {
      dir.write(dirPath, fileSystem)
    }
  }

  public operator fun String.div(other: String): String = "$this${Path.DIRECTORY_SEPARATOR}$other"

  @PublishedApi
  internal fun String.segments(): List<String> = toPath(normalize = false).segments

  @Poko
  @PublishedApi
  internal class FileWithContent(val nameWithExtension: String, val content: String)
}
