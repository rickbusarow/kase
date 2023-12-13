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
import com.rickbusarow.kase.Kase22
import com.rickbusarow.kase.kase

@Suppress("ktlint:standard:comment-wrapping")
class KaseTupleSample {

  @Sample
  fun kaseTypeSafeTuplePair() {

    val kaseA: Kase2<Int, String> = kase(3, "some string")
    val kaseB/* Kase2<Int, String> */ = kase(3, "some string")
    val kaseC/* Kase2<Int, String> */ = kase<Int, String>(3, "some string")

    assert(kaseA == kaseB)
    assert(kaseA == kaseC)
    assert(kaseB == kaseC)

    val (integer: Int, string: String) = kase(3, "some string")

    assert(integer == 3)
    assert(string == "some string")

    // the default toString() is meaningful
    assert(kaseA.toString() == "a1: 3 | a2: some string")

    // toString() implementations can be customized at instance creation
    val fancyName =
      kase(3, "some string", displayNameFactory = { "integer -> $a1, someString -> $a2" })

    assert(fancyName.toString() == "integer -> 3, someString -> some string")
  }

  @Sample
  @Suppress("ktlint:standard:wrapping", "UnusedPrivateProperty", "UNUSED_VARIABLE")
  fun kaseTypeSafeTupleBig() {

    // This may have any types, but it gets unpleasant to look at.
    val bigKase: Kase22<
      Char, Char, Char, Char, Char, Char, Char, Char, Char, Char, Char,
      Char, Char, Char, Char, Char, Char, Char, Char, Char, Char, Char
      > = kase(
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
      'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v'
    )
  }
}
