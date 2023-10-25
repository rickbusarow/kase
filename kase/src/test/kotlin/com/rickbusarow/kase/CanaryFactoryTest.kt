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

import com.rickbusarow.kase.stdlib.createSafely
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class CanaryFactoryTest : TestEnvironmentFactory<TestEnvironment> {

  @TestFactory
  fun `asTests individual args`() = kases(
    listOf("a", "b"),
    listOf(1, 2, 3),
    listOf('A', 'B', 'C')
  ).asTests { a, b, c ->
    workingDir.resolve("test.txt").createSafely("hello world")
  }

  @TestFactory
  fun `asTests destructured`() = kases(
    listOf("a", "b"),
    listOf(1, 2, 3)
  ).asTests { (a1, a2) ->
    workingDir.resolve("test.txt").createSafely("hello world")
  }

  @Test
  fun `kase addition`() {
    val kase2: Kase2<String, List<Char>> = kase(a1 = "a", a2 = listOf('c', 'd'))

    val kase3: Kase3<String, List<Char>, Set<Int>> = kase2.plus("some integers", setOf(1, 2, 3))

    kase3.displayName shouldBe "[a1: a | a2: [c, d] | some integers: [1, 2, 3]]"
  }
}
