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

class TestLocationJUnit4Test : MoreAsserts {

  @org.junit.Test
  fun `junit 4 test annotations work`() {

    TestLocation.get() shouldBe expectedLocation("junit 4 test annotations work")
  }

  fun expectedLocation(functionName: String): TestLocation {
    val clazz = TestLocationJUnit4Test::class.java
    return TestLocation(
      fileName = "${clazz.simpleName}.kt",
      // line numbers don't matter since they're excluded from comparison
      lineNumber = 1,
      packageName = clazz.packageName(),
      declaringClass = clazz,
      declaringClassWithoutSynthetics = clazz,
      declaringClassSimpleNames = listOf(clazz.simpleName),
      callingFunctionSimpleName = functionName
    )
  }
}
