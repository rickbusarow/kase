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

import com.rickbusarow.kase.files.TestLocation
import kotlinx.coroutines.runBlocking

/**
 * Trait interface for providing a [TestEnvironmentFactory].
 *
 * @since 0.7.0
 */
public interface HasTestEnvironmentFactory<out FACT : TestEnvironmentFactory<*, *>> {

  /** @since 0.7.0 */
  public val testEnvironmentFactory: FACT
}

/**
 * Creates [TestEnvironment]s.
 *
 * @since 0.1.0
 */
public fun interface TestEnvironmentFactory<in PARAM, out ENV : TestEnvironment> {
  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [ENV].
   * @since 0.1.0
   */
  public fun createEnvironment(params: PARAM, names: List<String>, location: TestLocation): ENV
}

/**
 * Convenience for invoking a test action with a `TestEnvironment` when no [Kase] is needed.
 *
 * @since 0.1.0
 */
public fun HasTestEnvironmentFactory<DefaultTestEnvironment.Factory>.test(
  testLocation: TestLocation = TestLocation.get(),
  testAction: suspend TestEnvironment.() -> Unit
) {
  val testEnvironment = testEnvironmentFactory.createEnvironment(
    names = emptyList(),
    location = testLocation
  )

  runBlocking {
    testEnvironment.asClueCatching {
      testEnvironment.testAction()
      println(testEnvironment)
    }
  }
}
