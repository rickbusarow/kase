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
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream
import kotlin.streams.asStream

/**
 * Helper function to build a dynamic test factory with specified initialization logic.
 *
 * Example usage:
 * ```
 * @TestFactory
 * fun `some test`() = testFactory {
 *   test("Test1") {
 *     println("Executing Test1")
 *   }
 * }
 * ```
 *
 * @param init a lambda with receiver that initializes the [TestNodeBuilder].
 * @return a stream of dynamic nodes constructed by the test factory builder.
 * @since 0.1.0
 */
public fun testFactory(init: TestNodeBuilder.() -> Unit): Stream<out DynamicNode> {
  return DefaultTestNodeBuilder(
    displayName = "root",
    testFunctionCoordinates = TestFunctionCoordinates.get(),
    parent = null
  )
    .apply { init() }
    .nodeSequence()
    .asStream()
}

/**
 * Builds a dynamic test container with a specific name and a list of dynamic
 * nodes (tests or containers). Provides functions for defining and adding
 * dynamic tests and containers to the nodes list. Each node within the
 * container can provide a list of names starting from the root container.
 *
 * Example usage:
 * ```
 * @TestFactory
 * fun `some test`() = asTests {
 *   test("Test1") {
 *     println("Executing Test1")
 *   }
 * }
 * ```
 *
 * @since 0.1.0
 */
public interface TestNodeBuilder : HasDisplayName {

  /**
   * Captured before executing any tests, meaning that it's the frame that called `asTests { ... }`
   */
  public val testFunctionCoordinates: TestFunctionCoordinates

  /** the parent node, or `null` if this is the root container */
  public val parent: TestNodeBuilder?

  /** Converts this builder to a [DynamicNode] */
  public fun build(): DynamicNode

  /** */
  public fun nodeSequence(): Sequence<DynamicNode>

  /**
   * Creates a dynamic test with the provided name and test logic, adds it to the list of nodes.
   *
   * @param name the name of the test. action a function containing the test logic.
   * @param testAction a function containing the test logic.
   * @since 0.1.0
   */
  public fun test(name: String, testAction: () -> Unit)

  /**
   * Creates a dynamic container with the provided name
   * and initialization logic, adds it to the nodes list.
   *
   * @param name the name of the container.
   * @param init a lambda with receiver that initializes the [TestNodeBuilder].
   * @since 0.1.0
   */
  public fun container(name: String, init: TestNodeBuilder.() -> Unit)

  /**
   * Adds tests to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the tests are determined by the [testName] function,
   * and the tests themselves are defined by the [testAction] function.
   *
   * @param testName a function to compute the name of each test.
   * @param testAction a function to define each test.
   * @receiver the [TestNodeBuilder] to which tests will be added.
   * @return the invoking [TestNodeBuilder], after adding the new tests.
   * @since 0.1.0
   */
  public fun <E> Iterable<E>.asTests(
    testName: (E) -> String = maybeDisplayNameOrToString(),
    testAction: (E) -> Unit
  ): TestNodeBuilder

  /**
   * Adds tests to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the tests are determined by the [testName] function,
   * and the tests themselves are defined by the [testAction] function.
   *
   * @param testName a function to compute the name of each test.
   * @param testEnvironmentFactory creates a new [TestEnvironment] for each test.
   * @param testAction a function to define each test.
   * @receiver the [TestNodeBuilder] to which tests will be added.
   * @return the invoking [TestNodeBuilder], after adding the new tests.
   * @since 0.1.0
   */
  public fun <T : TestEnvironment, K : Kase> Iterable<K>.asTests(
    testName: (K) -> String,
    testEnvironmentFactory: (kase: K) -> T,
    testAction: T.(K) -> Unit
  ): TestNodeBuilder

  /**
   * Adds tests to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The tests themselves are defined by the [testAction] function.
   *
   * @param testEnvironmentFactory creates a new [TestEnvironment] for each test.
   * @param testAction a function to define each test.
   * @receiver the [TestNodeBuilder] to which tests will be added.
   * @return the invoking [TestNodeBuilder], after adding the new tests.
   * @since 0.1.0
   */
  public fun <T : TestEnvironment, K : Kase> Iterable<K>.asTests(
    testEnvironmentFactory: (kase: K) -> T,
    testAction: T.(K) -> Unit
  ): TestNodeBuilder

  /**
   * Adds tests to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the tests are determined by the [testName] function,
   * and the tests themselves are defined by the [testAction] function.
   *
   * @param testName a function to compute the name of each test.
   * @param testAction a function to define each test.
   * @receiver the [TestNodeBuilder] to which tests will be added.
   * @return the invoking [TestNodeBuilder], after adding the new tests.
   * @since 0.1.0
   */
  public fun <E> Sequence<E>.asTests(
    testName: (E) -> String = maybeDisplayNameOrToString(),
    testAction: (E) -> Unit
  ): TestNodeBuilder

  /**
   * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the containers are determined by the [testName] function,
   * and the containers themselves are initialized by the [testAction] function.
   *
   * @param testName a function to compute the name of each container.
   * @param testAction a function to initialize each container.
   * @receiver the [TestNodeBuilder] to which containers will be added.
   * @return the invoking [TestNodeBuilder], after adding the new containers.
   * @since 0.1.0
   */
  public fun <E> Iterable<E>.asContainers(
    testName: (E) -> String = maybeDisplayNameOrToString(),
    testAction: TestNodeBuilder.(E) -> Unit
  ): TestNodeBuilder

  /**
   * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the containers are determined by the [testName] function,
   * and the containers themselves are initialized by the [testAction] function.
   *
   * @param testName a function to compute the name of each
   *   container. action a function to initialize each container.
   * @param testAction a function to initialize each container.
   * @receiver the [TestNodeBuilder] to which containers will be added.
   * @return the invoking [TestNodeBuilder], after adding the new containers.
   * @since 0.1.0
   */
  public fun <E> Sequence<E>.asContainers(
    testName: (E) -> String = maybeDisplayNameOrToString(),
    testAction: TestNodeBuilder.(E) -> Unit
  ): TestNodeBuilder
}

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
public fun <K : Kase> Iterable<K>.asContainers(
  testName: (K) -> String = { it.displayName },
  testAction: TestNodeBuilder.(K) -> Unit
): Stream<out DynamicNode> = testFactory { asContainers(testName, testAction) }

internal fun <E> maybeDisplayNameOrToString(): (E) -> String =
  { (it as? HasDisplayName)?.displayName ?: it.toString() }
