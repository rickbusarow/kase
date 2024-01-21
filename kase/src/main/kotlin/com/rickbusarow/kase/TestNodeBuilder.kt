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
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream
import kotlin.streams.asStream

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
public sealed interface TestNodeBuilder : HasDisplayName, HasDisplayNames {

  /**
   * Captured before executing any tests, meaning that it's the frame that called `asTests { ... }`
   *
   * @since 0.6.0
   */
  public val testLocation: TestLocation

  /**
   * the parent node, or `null` if this is the root container
   *
   * @since 0.6.0
   */
  public val parent: TestNodeBuilder?

  override val displayNames: List<String>
    get() = namesFromRoot
}

internal val TestNodeBuilder.namesFromRoot: List<String>
  get() = generateSequence<TestNodeBuilder>(this) { it.parent }
    .map { it.displayName }
    .toList()
    .asReversed()

/**
 * Creates a stream of tests from [HasParams.params]
 *
 * @since 0.7.0
 */
public fun <E> HasParams<E>.testFactory(
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> = params.asSequence().asTests(testName, testAction)

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param param1 the first element to use for this test factory
 * @param param2 the second element to use for this test factory
 * @param additionalParams all additional elements to use for this test factory
 * @param testName a function to compute the name of each test.
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase1
 * @see TestEnvironmentFactory
 * @since 0.7.0
 */
public fun <E> testFactory(
  param1: E,
  param2: E,
  vararg additionalParams: E,
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> =
  sequenceOf(param1).plus(param2).plus(additionalParams).asTests(testName, testAction)

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param params the elements to use for this test factory
 * @param testName a function to compute the name of each test.
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase1
 * @see TestEnvironmentFactory
 * @since 0.7.0
 */
public fun <E> testFactory(
  params: Iterable<E>,
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> = params.asSequence().asTests(testName, testAction)

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param params the elements to use for this test factory
 * @param testName a function to compute the name of each test.
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase1
 * @see TestEnvironmentFactory
 * @since 0.7.0
 */
public fun <E> testFactory(
  params: Sequence<E>,
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> = params.asTests(testName, testAction)

/**
 * Creates a [Stream] of [DynamicNode]s from this [Array] of [Kase]s.
 *
 * @param testName a function to compute the name of each test.
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @since 0.7.0
 */
public fun <E> Array<E>.asTests(
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

/**
 * Adds tests to the invoking [TestNodeBuilder] for each parameter in the
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
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

/**
 * Adds tests to the invoking [TestNodeBuilder] for each parameter in the
 * iterable. The names of the tests are determined by the [testName] function,
 * and the tests themselves are defined by the [testAction] function.
 *
 * @param testName a function to compute the name of each test.
 * @param testAction a function to define each test.
 * @receiver the [TestNodeBuilder] to which tests will be added.
 * @return the invoking [TestNodeBuilder], after adding the new tests.
 * @since 0.1.0
 */
public fun <PARAM> Sequence<PARAM>.asTests(
  testName: (PARAM) -> String = maybeDisplayName(),
  testAction: suspend (PARAM) -> Unit
): Stream<out DynamicNode> {
  val location = TestLocation.get()
  return map { element ->
    DynamicTest.dynamicTest(testName(element), location.testUriOrNull) {
      runBlocking { testAction(element) }
    }
  }
    .asStream()
}

/**
 * Adds tests to the invoking [TestNodeBuilder] for each parameter in the
 * iterable. The names of the tests are determined by the [testName] function,
 * and the tests themselves are defined by the [testAction] function.
 *
 * @param testEnvironmentFactory creates a new [TestEnvironment] for each test.
 * @param testName a function to compute the name of each test.
 * @param testAction a function to define each test.
 * @receiver the [TestNodeBuilder] to which tests will be added.
 * @return the invoking [TestNodeBuilder], after adding the new tests.
 * @since 0.1.0
 */
public fun <PARAM, ENV : TestEnvironment> Iterable<PARAM>.asTests(
  testEnvironmentFactory: TestEnvironmentFactory<PARAM, ENV>,
  testName: (PARAM) -> String = maybeDisplayName(),
  testAction: suspend ENV.(PARAM) -> Unit
): Stream<out DynamicNode> = asSequence().asTests(
  testEnvironmentFactory = testEnvironmentFactory,
  testName = testName,
  testAction = testAction
)

/**
 * Adds tests to the invoking [TestNodeBuilder] for each parameter in the
 * iterable. The names of the tests are determined by the [testName] function,
 * and the tests themselves are defined by the [testAction] function.
 *
 * @param testEnvironmentFactory creates a new [TestEnvironment] for each test.
 * @param testName a function to compute the name of each test.
 * @param testAction a function to define each test.
 * @receiver the [TestNodeBuilder] to which tests will be added.
 * @return the invoking [TestNodeBuilder], after adding the new tests.
 * @since 0.1.0
 */
public fun <PARAM, ENV : TestEnvironment> Sequence<PARAM>.asTests(
  testEnvironmentFactory: TestEnvironmentFactory<PARAM, ENV>,
  testName: (PARAM) -> String = maybeDisplayName(),
  testAction: suspend ENV.(PARAM) -> Unit
): Stream<out DynamicNode> {
  val location = TestLocation.get()
  return map { element ->
    DynamicTest.dynamicTest(testName(element), location.testUriOrNull) {
      val environment = when (testEnvironmentFactory) {
        is NoParamTestEnvironmentFactory -> testEnvironmentFactory.create(
          names = listOf(testName(element)),
          location = location
        )

        is ParamTestEnvironmentFactory -> testEnvironmentFactory.create(
          params = element,
          names = listOf(testName(element)),
          location = location
        )
      }

      runBlocking { testAction(environment, element) }
    }
  }
    .asStream()
}

/**
 * Adds containers to the invoking [TestNodeBuilder] for each parameter in the
 * iterable. The names of the containers are determined by the [displayName] function,
 * and the containers themselves are initialized by the [testAction] function.
 *
 * @param displayName a function to compute the name of each container.
 * @param testAction a function to initialize each container.
 * @receiver the [TestNodeBuilder] to which containers will be added.
 * @return the invoking [TestNodeBuilder], after adding the new containers.
 * @since 0.1.0
 */
public fun <E> Iterable<E>.asContainers(
  displayName: (E) -> String = maybeDisplayName(),
  testAction: UnscopedTestNodeBuilder.(E) -> Stream<out DynamicNode>
): Stream<out DynamicNode> = asSequence().asContainers(displayName, testAction)

/**
 * Adds containers to the invoking [TestNodeBuilder] for each parameter in the
 * iterable. The names of the containers are determined by the [displayName] function,
 * and the containers themselves are initialized by the [testAction] function.
 *
 * @param displayName a function to compute the name of each
 *   container. action a function to initialize each container.
 * @param testAction a function to initialize each container.
 * @receiver the [TestNodeBuilder] to which containers will be added.
 * @return the invoking [TestNodeBuilder], after adding the new containers.
 * @since 0.1.0
 */
public fun <E> Sequence<E>.asContainers(
  displayName: (E) -> String = maybeDisplayName(),
  testAction: UnscopedTestNodeBuilder.(E) -> Stream<out DynamicNode>
): Stream<out DynamicNode> {
  val coords = TestLocation.get()
  return map { e ->
    val name = displayName(e)
    UnscopedTestNodeBuilder(name, coords, null).run {
      DynamicContainer.dynamicContainer(name, coords.testUriOrNull, testAction(e))
    }
  }
    .asStream()
}

internal fun <E> maybeDisplayName(): (E) -> String = { it.maybeDisplayNameOrToString() }

internal fun <E> E.maybeDisplayNameOrToString(): String {
  return (this as? HasDisplayName)?.displayName ?: toString()
}
