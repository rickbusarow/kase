/*
 * Copyright (C) 2024 Rick Busarow
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

package com.rickbusarow.kase.samples

import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.kase
import com.rickbusarow.kase.kases
import com.rickbusarow.kase.times
import io.kotest.matchers.shouldBe

class TransformSamples {

  @Sample
  fun `list multiplication simple`() {

    val firstNames = kases(listOf("Bluey", "Bingo", "Chilli", "Bandit"))
    val lastName = listOf(kase("Heeler"))

    val names = firstNames * lastName

    names shouldBe listOf(
      kase(a1 = "Bluey", a2 = "Heeler"),
      kase(a1 = "Bingo", a2 = "Heeler"),
      kase(a1 = "Chilli", a2 = "Heeler"),
      kase(a1 = "Bandit", a2 = "Heeler")
    )
  }

  @Sample
  fun `list multiplication custom type`() {
    class Dog(
      val firstName: String,
      val lastName: String
    ) : Kase2<String, String> by kase(
      a1 = firstName,
      a2 = lastName
    ) {
      override val displayName: String get() = "$firstName $lastName"
      override fun toString(): String = displayName
    }

    val names = kases(listOf("Bluey", "Bingo", "Chilli", "Bandit"))
      .times(kases(listOf("Heeler")), ::Dog)

    println(names.joinToString("\n"))
  }
}
