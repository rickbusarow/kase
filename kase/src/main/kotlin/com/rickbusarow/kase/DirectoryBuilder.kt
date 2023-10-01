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

internal class DirectoryBuilder @PublishedApi internal constructor(
  internal val parent: DirectoryBuilder?,
  internal val name: String
) {
  val childDirs = mutableListOf<DirectoryBuilder>()
  val files = mutableListOf<FileWithContent>()

  inline fun dir(name: String, builder: DirectoryBuilder.() -> Unit) {

    val child = DirectoryBuilder(this, name).also(builder)
    childDirs.add(child)
  }

  data class FileWithContent(val name: String, val content: String)
}
