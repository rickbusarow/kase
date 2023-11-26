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

import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.files.DirectoryBuilder.Companion.invoke
import com.rickbusarow.kase.kases
import java.io.File

class DirectoryBuilderTest : KaseTestFactory<TestEnvironment, Kase> {
  override val kases: List<Kase1<(File) -> DirectoryBuilder>>
    get() = kases(
      listOf(
        { it.directoryBuilder() },
        { it.toPath().directoryBuilder() },
        { DirectoryBuilder(it) }
      )
    )

  val factories = listOf<Pair<String, (File) -> DirectoryBuilder>>(
    "java.io.File extension" to { it.directoryBuilder() },
    "java.nio.files.Path extension" to { it.toPath().directoryBuilder() },
    "java.io.File invoke constructor" to { DirectoryBuilder(it) },
    "java.nio.files.Path invoke constructor" to { DirectoryBuilder(it.toPath()) }

  )
}
