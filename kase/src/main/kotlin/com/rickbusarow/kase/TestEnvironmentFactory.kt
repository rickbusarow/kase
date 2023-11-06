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

import com.rickbusarow.kase.files.TestFunctionCoordinates
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream

/** Creates [TestEnvironment]s. */
public interface TestEnvironmentFactory<T : TestEnvironment, K : AnyKase> {

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase]s.
   *
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   */
  public fun <E : K> Iterable<E>.asTests(
    testAction: T.(E) -> Unit
  ): Stream<out DynamicNode> {
    return testFactory(init = {
      this@asTests.asTests { newTestEnvironment(it, testFunctionCoordinates).testAction(it) }
    })
  }

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Sequence] of [Kase]s.
   *
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   */
  public fun <E : K> Sequence<E>.asTests(
    testAction: T.(E) -> Unit
  ): Stream<out DynamicNode> {
    return testFactory(init = {
      this@asTests.asTests { newTestEnvironment(it, testFunctionCoordinates).testAction(it) }
    })
  }

  /**
   * A test factory which returns a stream of [DynamicNode]s from the given parameters.
   * - Each [DynamicTest] in the stream uses its [Kase1] element to create
   *   a new [TestEnvironment] instance, then executes [testAction].
   * - Each [DynamicNode] has a display name which includes the values of the parameters.
   *
   * @param kases the [Kase1]s to use for this test factory
   * @param testAction the test action to execute.
   * @return a [Stream] of [DynamicNode]s from the given parameters.
   * @see Kase1
   * @see TestEnvironmentFactory
   */
  public fun <E : K> testFactory(
    vararg kases: E,
    testAction: T.(kase: E) -> Unit
  ): Stream<out DynamicNode> {
    return testFactory(init = {
      kases.asSequence().asTests { newTestEnvironment(it, testFunctionCoordinates).testAction(it) }
    })
  }

  /**
   * A test factory which returns a stream of [DynamicNode]s from the given parameters.
   * - Each [DynamicTest] in the stream uses its [Kase1] element to create
   *   a new [TestEnvironment] instance, then executes [testAction].
   * - Each [DynamicNode] has a display name which includes the values of the parameters.
   *
   * @param kases the [Kase1]s to use for this test factory
   * @param testAction the test action to execute.
   * @return a [Stream] of [DynamicNode]s from the given parameters.
   * @see Kase1
   * @see TestEnvironmentFactory
   */
  public fun <E : K> testFactory(
    kases: Iterable<E>,
    testAction: T.(kase: E) -> Unit
  ): Stream<out DynamicNode> {
    return testFactory(init = {
      kases.asTests { newTestEnvironment(it, testFunctionCoordinates).testAction(it) }
    })
  }

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param kase The variant names related to the test.
   * @param testFunctionCoordinates The [TestFunctionCoordinates] from which the test is being run.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun <E : K> test(
    kase: E,
    testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
    testAction: suspend T.() -> Unit
  ) {

    val testEnvironment = newTestEnvironment(kase, testFunctionCoordinates)

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
  public fun newTestEnvironment(
    kase: K,
    testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
  ): T {
    @Suppress("UNCHECKED_CAST")
    return TestEnvironment(
      kase.displayNames,
      testFunctionCoordinates = testFunctionCoordinates
    ) as? T
      ?: error("Override `newTestEnvironment` in order to create this TestEnvironment type.")
  }

  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [T].
   */
  public fun newTestEnvironment(params: TestVariant<out AnyKase>): T {
    @Suppress("UNCHECKED_CAST")
    return TestEnvironment(
      testParameterDisplayNames = params.kase.displayNames,
      testFunctionCoordinates = params.testFunctionCoordinates
    ) as? T
      ?: error("Override `newTestEnvironment` in order to create this TestEnvironment type.")
  }
}
