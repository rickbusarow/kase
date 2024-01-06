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
import com.rickbusarow.kase.internal.DefaultTestNodeBuilder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream
import kotlin.streams.asStream

/**
 * Common interface for creating dynamic tests with predefined
 * [kases][HasKases.kases] and a unique [TestEnvironment]
 *
 * @since 0.1.0
 */
public interface KaseTestFactory<T : TestEnvironment, K : Kase> :
  HasKases<K>,
  TestEnvironmentFactory<T, K> {

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param kase The variant names related to the test.
   * @param testFunctionCoordinates The [TestFunctionCoordinates] from which the test is being run.
   * @param testAction The test action to run within the [TestEnvironment].
   * @since 0.1.0
   */
  public fun <E : K> test(
    kase: E,
    testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
    testAction: suspend T.() -> Unit
  ) {

    val testEnvironment = newTestEnvironment(kase, testFunctionCoordinates)

    runBlocking {
      testEnvironment.asClueCatching {
        testAction()
      }
    }
  }

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase]s.
   *
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   * @since 0.1.0
   */
  public fun <E : K> Iterable<E>.asTests(
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> = asSequence().asTests(testAction)

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Sequence] of [Kase]s.
   *
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   * @since 0.1.0
   */
  public fun <E : K> Sequence<E>.asTests(
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> {
    return com.rickbusarow.kase.testFactory {
      this@asTests.asTests { kaseElement ->
        test(kaseElement, testFunctionCoordinates) { testAction(kaseElement) }
      }
    }
  }

  /**
   * Creates a stream of tests from [kases]
   *
   * @since 0.1.0
   */
  public fun testFactory(
    testAction: suspend T.(K) -> Unit
  ): Stream<out DynamicNode> = kases.asTests(testAction)

  /**
   * A test factory which returns a stream of [DynamicNode]s from the given parameters.
   * - Each [DynamicTest] in the stream uses its [Kase] element to create
   *   a new [TestEnvironment] instance, then executes [testAction].
   * - Each [DynamicNode] has a display name which includes the values of the parameters.
   *
   * @param kases the [Kase1]s to use for this test factory
   * @param testAction the test action to execute.
   * @return a [Stream] of [DynamicNode]s from the given parameters.
   * @see Kase1
   * @see TestEnvironmentFactory
   * @since 0.1.0
   */
  public fun <E : K> testFactory(
    vararg kases: E,
    testAction: suspend T.(kase: E) -> Unit
  ): Stream<out DynamicNode> = kases.asSequence().asTests(testAction)

  /**
   * A test factory which returns a stream of [DynamicNode]s from the given parameters.
   * - Each [DynamicTest] in the stream uses its [Kase] element to create
   *   a new [TestEnvironment] instance, then executes [testAction].
   * - Each [DynamicNode] has a display name which includes the values of the parameters.
   *
   * @param kases the [Kase]s to use for this test factory
   * @param testAction the test action to execute.
   * @return a [Stream] of [DynamicNode]s from the given parameters.
   * @see Kase1
   * @see TestEnvironmentFactory
   * @since 0.1.0
   */
  public fun <E : K> testFactory(
    kases: Iterable<E>,
    testAction: suspend T.(kase: E) -> Unit
  ): Stream<out DynamicNode> = kases.asSequence().asTests(testAction)

  /**
   * Transforms an iterable into a stream of dynamic test containers. The
   * names of the containers are determined by the [testName] function, and
   * the containers themselves are initialized by the [testAction] function.
   *
   * @param testName a function to compute the name of each test.
   * @param testAction a function to initialize each test container.
   * @return a stream of dynamic nodes representing the containers.
   * @since 0.1.0
   */
  public fun <K2 : Kase> Iterable<K2>.asContainers(
    testName: (K2) -> String = { it.displayName },
    testAction: KaseTestFactoryNodeBuilder<T, K>.(K2) -> Unit
  ): Stream<out DynamicNode> = asSequence().asContainers(testName, testAction)

  /**
   * Transforms a sequence into a stream of dynamic test containers. The
   * names of the containers are determined by the [testName] function, and
   * the containers themselves are initialized by the [testAction] function.
   *
   * @param testName a function to compute the name of each test.
   * @param testAction a function to initialize each test container.
   * @return a stream of dynamic nodes representing the containers.
   * @since 0.1.0
   */
  public fun <K2 : Kase> Sequence<K2>.asContainers(
    testName: (K2) -> String = { it.displayName },
    testAction: KaseTestFactoryNodeBuilder<T, K>.(K2) -> Unit
  ): Stream<out DynamicNode> = KaseTestFactoryNodeBuilder<T, K>(
    delegateFactory = this@KaseTestFactory,
    delegateNodeBuilder = DefaultTestNodeBuilder(
      displayName = "root",
      testFunctionCoordinates = TestFunctionCoordinates.get(),
      parent = null
    )
  )
    .also {
      for (k in this@asContainers) {
        it.wrapContainer(testName(k)) { testAction(k) }
      }
    }
    .nodeSequence()
    .asStream()
}
