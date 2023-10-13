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

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.TestEnvironmentFactory
import com.rickbusarow.kase.TestVariant
import com.rickbusarow.kase.asClueCatching
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD

@Execution(SAME_THREAD)
interface BaseGradleTest : TestEnvironmentFactory<GradleTestEnvironment> {

  /**
   * Runs the provided test [action] in the context of a new [TestEnvironment].
   *
   * @param action The test action to run within the [TestEnvironment].
   */
  fun test(action: suspend GradleTestEnvironment.() -> Unit) {

    val testEnvironment = newTestEnvironment(params)

    runBlocking {
      testEnvironment.asClueCatching {
        testEnvironment.action()
        println(testEnvironment)
      }
    }
  }

  /** @return A new [GradleTestEnvironment] */
  override fun newTestEnvironment(params: TestVariant): GradleTestEnvironment {
    return GradleTestEnvironment(
      testVersions = TestVersions(params.kase),
      projectCache = mutableMapOf(),
      testFunctionCoordinates = params.testFunctionCoordinates,
      testVariantNames = params.kase
    )
  }
}
