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

import kotlinx.coroutines.runBlocking

/** Creates [TestEnvironment]s. */
public interface TestEnvironmentFactory<T : TestEnvironment<*>> {

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param kase The variant names related to the test.
   * @param testFunctionName The [TestFunctionName] from which the test is being run.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun test(
    kase: AnyKase = Kase.EMPTY,
    testFunctionName: TestFunctionName = TestFunctionName.get(),
    testAction: suspend T.() -> Unit
  ) {
    val testEnvironment = newTestEnvironment(kase, testFunctionName)

    runBlocking {
      testEnvironment.asClueCatching {
        testEnvironment.testAction()
        println(testEnvironment)
      }
    }
  }

  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [T].
   */
  public fun <K : AnyKase> newTestEnvironment(
    kase: K,
    testFunctionName: TestFunctionName = TestFunctionName.get()
  ): T {
    @Suppress("UNCHECKED_CAST")
    return TestEnvironment(kase = kase, testFunctionName = testFunctionName) as? T
      ?: error("Override `newTestEnvironment` in order to create this TestEnvironment type.")
  }

  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [T].
   */
  public fun newTestEnvironment(params: TestVariant): T {
    @Suppress("UNCHECKED_CAST")
    return TestEnvironment(kase = params.kase, testFunctionName = params.testFunctionName) as? T
      ?: error("Override `newTestEnvironment` in order to create this TestEnvironment type.")
  }
}
