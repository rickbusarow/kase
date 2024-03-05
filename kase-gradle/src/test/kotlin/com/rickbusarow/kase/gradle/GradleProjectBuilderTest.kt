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

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.DefaultTestEnvironment
import com.rickbusarow.kase.HasTestEnvironmentFactory
import com.rickbusarow.kase.KaseTests
import com.rickbusarow.kase.stdlib.div
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GradleProjectBuilderTest :
  KaseTests,
  HasTestEnvironmentFactory<DefaultTestEnvironment.Factory> {

  override val testEnvironmentFactory = DefaultTestEnvironment.Factory()

  @Test
  fun `subproject builders create projects with the correct relative path`() = test {
    var assertions = 0
    rootProject {
      project("sub1") {
        path shouldBe workingDir / "sub1"
        assertions++
      }
      project("sub2") {
        path shouldBe workingDir / "sub2"
        assertions++
      }
    }

    // make sure that project configuration doesn't mysteriously become lazy
    assertions shouldBe 2
  }
}
