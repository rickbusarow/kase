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
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.io.File
import java.net.URI
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.io.path.div
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
 */
public fun testFactory(init: TestNodeBuilder.() -> Unit): Stream<out DynamicNode> {
  return TestNodeBuilder(
    name = "root",
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
 * @property name the name of the test container.
 * @property testFunctionCoordinates Captured before executing any
 *   tests, meaning that it's the frame which called `asTests { ... }`
 * @property parent the parent node, or `null` if this is the root container
 */
@Poko
public class TestNodeBuilder @PublishedApi internal constructor(
  public val name: String,
  public val testFunctionCoordinates: TestFunctionCoordinates,
  public val parent: TestNodeBuilder?
) {

  @PublishedApi
  internal val nodes: MutableList<() -> DynamicNode> = mutableListOf()

  @PublishedApi
  internal fun nodeSequence(): Sequence<DynamicNode> = nodes.asSequence().map { it() }

  /**
   * Creates a dynamic test with the provided name and test logic, adds it to the nodes list.
   *
   * @param name the name of the test. action a function containing the test logic.
   * @param testAction a function containing the test logic.
   */
  public fun test(name: String, testAction: () -> Unit) {
    addTest(name, testAction)
  }

  /**
   * Creates a dynamic container with the provided name
   * and initialization logic, adds it to the nodes list.
   *
   * @param name the name of the container.
   * @param init a lambda with receiver that initializes the [TestNodeBuilder].
   */
  public fun container(name: String, init: TestNodeBuilder.() -> Unit) {

    nodes.add {
      TestNodeBuilder(
        name = name,
        testFunctionCoordinates = testFunctionCoordinates,
        parent = this
      )
        .apply(init)
        .build()
    }
  }

  /**
   * Builds the test container from the current state of this builder.
   *
   * @return a dynamic container with the defined name and nodes.
   */
  @PublishedApi
  internal fun build(): DynamicNode {
    return DynamicContainer.dynamicContainer(name, nodeSequence().asStream())
  }

  @PublishedApi
  internal fun addTest(name: String, testAction: () -> Unit) {
    nodes.add { DynamicTest.dynamicTest(name, testUri()) { testAction() } }
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
   */
  public inline fun <E> Iterable<E>.asTests(
    testName: (E) -> String = { it.toString() },
    crossinline testAction: (E) -> Unit
  ): TestNodeBuilder = this@TestNodeBuilder.apply {
    for (element in this@asTests) {
      test(testName(element)) { testAction(element) }
    }
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
   */
  public inline fun <E> Sequence<E>.asTests(
    testName: (E) -> String = { it.toString() },
    crossinline testAction: (E) -> Unit
  ): TestNodeBuilder = this@TestNodeBuilder.apply {
    for (element in this@asTests) {
      test(testName(element)) { testAction(element) }
    }
  }

  /**
   * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the containers are determined by the [testName] function,
   * and the containers themselves are initialized by the [testAction] function.
   *
   * @param testName a function to compute the name of each container.
   * @param testAction a function to initialize each container.
   * @receiver the [TestNodeBuilder] to which containers will be added.
   * @return the invoking [TestNodeBuilder], after adding the new containers.
   */
  public inline fun <E> Iterable<E>.asContainers(
    testName: (E) -> String = { it.toString() },
    crossinline testAction: TestNodeBuilder.(E) -> Unit
  ): TestNodeBuilder = this@TestNodeBuilder.apply {
    for (element in this@asContainers) {
      container(testName(element)) { testAction(element) }
    }
  }

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
   */
  public inline fun <E> Sequence<E>.asContainers(
    testName: (E) -> String = { it.toString() },
    crossinline testAction: TestNodeBuilder.(E) -> Unit
  ): TestNodeBuilder = this@TestNodeBuilder.apply {
    for (element in this@asContainers) {
      container(testName(element)) { testAction(element) }
    }
  }

  /**
   * Creates a dynamic test with the provided name and test logic, adds it to the nodes list.
   *
   * @param testAction a function containing the test logic.
   */
  context(TestEnvironmentFactory<T>)
  public inline fun <T : TestEnvironment, K : AnyKase> Iterable<K>.asTests(
    crossinline testAction: suspend T.(K) -> Unit
  ): TestNodeBuilder = this@TestNodeBuilder.apply {
    for (kase in this@asTests) {
      test(kase) { testAction(kase) }
    }
  }

  /**
   * Creates a dynamic test with the provided name and test logic, adds it to the nodes list.
   *
   * @param name the name of the test.
   * @param testAction a function containing the test logic.
   */
  context(TestEnvironmentFactory<T>)
  public inline fun <T : TestEnvironment> test(
    name: String,
    crossinline testAction: suspend T.() -> Unit
  ) {
    addTest(name) {
      this@TestEnvironmentFactory.test(
        testFunctionCoordinates = testFunctionCoordinates,
        testAction = { testAction() }
      )
    }
  }

  /**
   * Creates a dynamic test with the provided name and test logic, adds it to the nodes list.
   *
   * @param kase the test Kase
   * @param testAction a function containing the test logic.
   */
  context(TestEnvironmentFactory<T>)
  public inline fun <T : TestEnvironment, K : AnyKase> test(
    kase: K,
    crossinline testAction: suspend T.() -> Unit
  ) {
    addTest(kase.displayNames().toString()) {
      this@TestEnvironmentFactory.test(
        kase = kase,
        testFunctionCoordinates = testFunctionCoordinates,
        testAction = { testAction() }
      )
    }
  }

  /**
   * Creates a dynamic test with the provided name and test logic, adds it to the nodes list.
   *
   * @param testAction a function containing the test logic.
   */
  context(TestEnvironmentFactory<T>)
  public inline fun <T : TestEnvironment, K : AnyKase> Sequence<K>.asTests(
    crossinline testAction: suspend T.(K) -> Unit
  ): TestNodeBuilder = this@TestNodeBuilder.apply {
    for (kase in this@asTests) {
      test(kase) { testAction(kase) }
    }
  }

  @PublishedApi
  internal fun testUri(): URI {

    val here = Paths.get("").toAbsolutePath()
    val srcTestKotlin = here / "src/test/kotlin"
    val packageDir = testFunctionCoordinates.packageName
      .replace('.', File.separatorChar)
    val classFile = srcTestKotlin / packageDir / testFunctionCoordinates.fileName
    val lineNumber = testFunctionCoordinates.lineNumber

    return URI.create(File("$classFile").toURI().toString() + "?line=$lineNumber")
  }
}

/**
 * Transforms an iterable into a stream of dynamic tests. The names of the tests are determined
 * by the kase instance, and the tests themselves are defined by the [testAction] function.
 *
 * @param testAction a function to define each test.
 * @return a stream of dynamic nodes representing the tests.
 */
context(TestEnvironmentFactory<T>)
public inline fun <T : TestEnvironment, K : AnyKase> Iterable<K>.asTests(
  crossinline testAction: suspend T.(K) -> Unit
): Stream<out DynamicNode> = testFactory { asTests(testAction) }

/**
 * Transforms an iterable into a stream of dynamic tests. The names of the tests are determined
 * by the kase instance, and the tests themselves are defined by the [testAction] function.
 *
 * @param testAction a function to define each test.
 * @return a stream of dynamic nodes representing the tests.
 */
context(TestEnvironmentFactory<T>)
public inline fun <T : TestEnvironment, K : AnyKase> Sequence<K>.asTests(
  crossinline testAction: suspend T.(K) -> Unit
): Stream<out DynamicNode> = testFactory { asTests(testAction) }

/**
 * Transforms an iterable into a stream of dynamic test containers. The
 * names of the containers are determined by the [testName] function, and
 * the containers themselves are initialized by the [testAction] function.
 *
 * @param testName a function to compute the name of each test.
 * @param testAction a function to initialize each test container.
 * @return a stream of dynamic nodes representing the containers.
 */
public inline fun <K : AnyKase> Iterable<K>.asContainers(
  crossinline testName: (K) -> String = { it.displayName },
  crossinline testAction: TestNodeBuilder.(K) -> Unit
): Stream<out DynamicNode> = testFactory { asContainers(testName, testAction) }

/**
 * Transforms a sequence into a stream of dynamic test containers. The
 * names of the containers are determined by the [testName] function, and
 * the containers themselves are initialized by the [testAction] function.
 *
 * @param testName a function to compute the name of each test.
 * @param testAction a function to initialize each test container.
 * @return a stream of dynamic nodes representing the containers.
 */
public inline fun <K : AnyKase> Sequence<K>.asContainers(
  crossinline testName: (K) -> String = { it.displayName },
  crossinline testAction: TestNodeBuilder.(K) -> Unit
): Stream<out DynamicNode> = testFactory { asContainers(testName, testAction) }
