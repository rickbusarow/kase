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

import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.kase
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.TestFactory
import java.io.File

class DirectoryBuilderTest : KaseTestFactory<TestEnvironment, Kase1<(File) -> DirectoryBuilder>> {
  override val kases: List<Kase1<(File) -> DirectoryBuilder>>
    get() = listOf(
      kase(displayName = "extension - java.io.File ") { it.directoryBuilder() },
      kase(displayName = "extension - java.nio.files.Path ") { it.toPath().directoryBuilder() },
      kase(displayName = "invoke() - java.io.File") { DirectoryBuilder(it) },
      kase(displayName = "invoke() - java.nio.files.Path") { DirectoryBuilder(it.toPath()) }
    )

  @TestFactory
  fun `canary`() = testFactory { (builderFactory) ->
    val builder = builderFactory(workingDir)

    builder.shouldBeInstanceOf<DirectoryBuilder>()
  }
}
