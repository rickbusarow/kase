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

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.streams.asSequence

class Kase1Test {

  @Test
  fun `kase function should correctly create a Kase1 instance`() {
    val kase = kase(a1 = 1, labels = KaseLabels1(a1Label = "Test"))

    kase.a1 shouldBe 1
    kase.a1WithLabel.label shouldBe "Test"
  }

  @Test
  fun `DefaultKase1 should have correct elements`() {
    val kaseParam = KaseParameterWithLabel.kaseParam("Test", 1)
    val kase =
      DefaultKase1(a1WithLabel = kaseParam, labelDelimiter = ": ", displayNameSeparator = " | ")

    val elements = kase.elements

    elements shouldBe listOf(kaseParam)
  }

  @Test
  fun `asTests function should create dynamic tests for each Kase1`() {
    val kase1 = kase(a1 = 1, labels = KaseLabels1(a1Label = "Test"))
    val kase2 = kase(a1 = 2, labels = KaseLabels1(a1Label = "Test"))
    val kases = listOf(kase1, kase2)

    val dynamicNodes = kases.asTests { }.asSequence().toList()
      .map { it.displayName }

    dynamicNodes shouldBe listOf("[Test: 1]", "[Test: 2]")
  }
}
