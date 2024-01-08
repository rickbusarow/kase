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

package com.rickbusarow.kase

import com.rickbusarow.kase.files.TestFunctionCoordinates
import kotlinx.coroutines.runBlocking

/**
 * Creates [TestEnvironment]s.
 *
 * @since 0.1.0
 */
public interface TestEnvironmentFactory<T : TestEnvironment, K : Kase> {
  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [T].
   * @since 0.1.0
   */
  public fun newTestEnvironment(
    kase: K,
    parentNames: List<String>,
    testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
  ): T {
    @Suppress("UNCHECKED_CAST")
    return TestEnvironment(
      kase.displayName,
      testFunctionCoordinates = testFunctionCoordinates
    ) as? T
      ?: error("Override `newTestEnvironment` in order to create this TestEnvironment type.")
  }
}

/**
 * Convenience for invoking a test action with a `TestEnvironment` when no [Kase] is needed.
 *
 * @since 0.1.0
 */
public fun <T : TestEnvironment> TestEnvironmentFactory<T, Kase>.test(
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) {
  val testEnvironment = newTestEnvironment(Kase.EMPTY, emptyList(), testFunctionCoordinates)

  runBlocking {
    testEnvironment.asClueCatching {
      testEnvironment.testAction()
      println(testEnvironment)
    }
  }
}
