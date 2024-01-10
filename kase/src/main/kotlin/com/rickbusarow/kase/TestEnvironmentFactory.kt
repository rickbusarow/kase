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
import dev.drewhamilton.poko.Poko
import kotlinx.coroutines.runBlocking

public interface TestEnvironmentParams {
  public val names: List<String>
  public val testFunctionCoordinates: TestFunctionCoordinates

  public companion object {
    public fun empty(
      coordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
    ): TestEnvironmentParams = DefaultTestEnvironmentParams(
      names = emptyList(),
      testFunctionCoordinates = coordinates
    )
  }
}

@Poko
public open class DefaultTestEnvironmentParams(
  override val names: List<String>,
  override val testFunctionCoordinates: TestFunctionCoordinates
) : TestEnvironmentParams

public interface HasTestEnvironmentFactory<out T : TestEnvironment, Params : TestEnvironmentParams> {
  public val testEnvironmentFactory: TestEnvironmentFactory<T, Params>
    get() = TestEnvironmentFactory { params ->
      @Suppress("UNCHECKED_CAST")
      TestEnvironment(
        testParameterDisplayNames = params.names,
        testFunctionCoordinates = params.testFunctionCoordinates
      ) as? T
        ?: error("Override `newTestEnvironment` in order to create this TestEnvironment type.")
    }
}

/**
 * Creates [TestEnvironment]s.
 *
 * @since 0.1.0
 */
public fun interface TestEnvironmentFactory<out T : TestEnvironment, Params : TestEnvironmentParams> {
  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [T].
   * @since 0.1.0
   */
  public fun newTestEnvironment(params: Params): T
}

/**
 * Convenience for invoking a test action with a `TestEnvironment` when no [Kase] is needed.
 *
 * @since 0.1.0
 */
public fun TestEnvironmentFactory<TestEnvironment, TestEnvironmentParams>.test(
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend TestEnvironment.() -> Unit
) {
  val testEnvironment = newTestEnvironment(TestEnvironmentParams.empty(testFunctionCoordinates))

  runBlocking {
    testEnvironment.asClueCatching {
      testEnvironment.testAction()
      println(testEnvironment)
    }
  }
}

/**
 * Convenience for invoking a test action with a `TestEnvironment` when no [Kase] is needed.
 *
 * @since 0.1.0
 */
public fun HasTestEnvironmentFactory<TestEnvironment, TestEnvironmentParams>.test(
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend TestEnvironment.() -> Unit
) {
  val testEnvironment = testEnvironmentFactory
    .newTestEnvironment(TestEnvironmentParams.empty(testFunctionCoordinates))

  runBlocking {
    testEnvironment.asClueCatching {
      testEnvironment.testAction()
      println(testEnvironment)
    }
  }
}
