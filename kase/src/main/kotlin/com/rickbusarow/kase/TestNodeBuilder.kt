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
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
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
@KaseTestBuilderDsl
@Deprecated("no")
public fun testFactory(init: UnscopedTestNodeBuilder.() -> Unit): Stream<out DynamicNode> {
  return emptySequence<DynamicNode>().asStream()
}

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
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

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
  testName: (E) -> String = maybeDisplayName(),
  testAction: suspend (E) -> Unit
): Stream<out DynamicNode> {
  val coords = TestFunctionCoordinates.get()
  return map { element ->
    DynamicTest.dynamicTest(testName(element), coords.testUriOrNull) {
      runBlocking { testAction(element) }
    }
  }
    .asStream()
}

/**
 * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
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
 * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
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
  val coords = TestFunctionCoordinates.get()
  return map { e ->
    val name = displayName(e)
    UnscopedTestNodeBuilder(name, coords, null).run {
      DynamicContainer.dynamicContainer(name, coords.testUriOrNull, testAction(e))
    }
  }
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
@KaseTestBuilderDsl
public sealed interface TestNodeBuilder : HasDisplayName {

  /**
   * Captured before executing any tests, meaning that it's the frame that called `asTests { ... }`
   *
   * @since 0.6.0
   */
  public val testFunctionCoordinates: TestFunctionCoordinates

  /**
   * the parent node, or `null` if this is the root container
   *
   * @since 0.6.0
   */
  public val parent: TestNodeBuilder?
}

internal val TestNodeBuilder.namesFromRoot: List<String>
  get() = generateSequence<TestNodeBuilder>(this) { it.parent }
    .map { it.displayName }
    .toList()
    .asReversed()

/**
 * @property testFunctionCoordinates Captured before executing any
 *   tests, meaning that it's the frame that called `asTests { ... }`
 * @property parent the parent node, or `null` if this is the root container
 */
@KaseTestBuilderDsl
public class UnscopedTestNodeBuilder(
  override val displayName: String,
  override val testFunctionCoordinates: TestFunctionCoordinates,
  override val parent: TestNodeBuilder?
) : ContainerTransforms<UnscopedTestNodeBuilder>, TestNodeBuilder {

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
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend (E) -> Unit
  ): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

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
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend (E) -> Unit
  ): Stream<out DynamicNode> = map { element ->
    DynamicTest.dynamicTest(testName(element)) {
      runBlocking { testAction(element) }
    }
  }
    .asStream()

  override fun <E> Sequence<E>.asContainers(
    displayName: (E) -> String,
    testAction: UnscopedTestNodeBuilder.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode> {
    val coords = TestFunctionCoordinates.get()
    return map { e ->
      val name = displayName(e)
      UnscopedTestNodeBuilder(name, coords, this@UnscopedTestNodeBuilder).run {
        DynamicContainer.dynamicContainer(name, coords.testUriOrNull, testAction(e))
      }
    }
      .asStream()
  }
}

public interface ContainerTransforms<S> {
  /**
   * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
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
    testAction: S.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode> = asSequence().asContainers(displayName, testAction)

  /**
   * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
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
    testAction: S.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode>
}

internal fun <E> maybeDisplayName(): (E) -> String = { it.maybeDisplayNameOrToString() }

internal fun <E> E.maybeDisplayNameOrToString(): String {
  return (this as? HasDisplayName)?.displayName ?: toString()
}
