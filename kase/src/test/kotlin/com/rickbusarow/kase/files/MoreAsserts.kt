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

package com.rickbusarow.kase.files

import com.rickbusarow.kase.stdlib.remove
import com.rickbusarow.kase.stdlib.toStringPretty
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.shouldBe
import java.io.File

interface MoreAsserts {

  infix fun File.shouldHaveText(expected: String) {
    shouldExist()
    readText() shouldBe expected
  }

  infix fun TestLocation?.shouldBe(other: TestLocation) {
    val reg = " {2}lineNumber=\\d+,\\n".toRegex()

    toStringPretty().remove(reg) shouldBe other.toStringPretty().remove(reg)
  }
}

fun Class<*>.packageName(): String = `package`.name
