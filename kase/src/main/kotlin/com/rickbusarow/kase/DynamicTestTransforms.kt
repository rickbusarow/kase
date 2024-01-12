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
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream
import kotlin.streams.asStream

/**
 * Defines overloads for all top-level [asTests] and [testFactory] functions where the element
 * type is [PARAM]. These overloading functions add an [ENV] receiver to the `testAction` lambda.
 */
public sealed interface DynamicTestTransforms<PARAM, ENV : TestEnvironment> {

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param param The variant names related to the test.
   * @param parentNames The names of the parent test containers.
   * @param testLocation The [TestLocation] from which the test is being run.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun HasTestEnvironmentFactory<TestEnvironmentFactory<PARAM, ENV>>.test(
    param: PARAM,
    parentNames: List<String> = emptyList(),
    testLocation: TestLocation = TestLocation.get(),
    testAction: suspend ENV.() -> Unit
  ) {
    test(
      param = param,
      factory = testEnvironmentFactory,
      parentNames = parentNames,
      testLocation = testLocation,
      testAction = testAction
    )
  }

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param param The variant names related to the test.
   * @param factory A factory which creates a new [TestEnvironment] for this test.
   * @param parentNames The names of the parent test containers.
   * @param testLocation The [TestLocation] from which the test is being run.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun <E, T : TestEnvironment> test(
    param: E,
    factory: TestEnvironmentFactory<E, T>,
    parentNames: List<String> = emptyList(),
    testLocation: TestLocation = TestLocation.get(),
    testAction: suspend T.() -> Unit
  ) {
    val testEnvironment = factory.createEnvironment(
      params = param,
      names = parentNames + param.maybeDisplayNameOrToString(),
      location = testLocation
    )

    runBlocking {
      testEnvironment.asClueCatching {
        testAction()
      }
    }
  }

  /** Creates a stream of tests from [HasParams.params] */
  public fun HasParams<PARAM>.testFactory(
    testName: (PARAM) -> String = maybeDisplayName(),
    testAction: suspend ENV.(PARAM) -> Unit
  ): Stream<out DynamicNode> = with(this@DynamicTestTransforms) {
    this@testFactory.params.asSequence().asTests(testName, testAction)
  }

  /**
   * A test factory which returns a stream of [DynamicNode]s from the given parameters.
   * - Each [DynamicTest] in the stream uses its element to create a
   *   new [TestEnvironment] instance, then executes [testAction].
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
   */
  public fun testFactory(
    param1: PARAM,
    param2: PARAM,
    vararg additionalParams: PARAM,
    testName: (PARAM) -> String = maybeDisplayName(),
    testAction: suspend ENV.(PARAM) -> Unit
  ): Stream<out DynamicNode> =
    sequenceOf(param1).plus(param2).plus(additionalParams).asTests(testName, testAction)

  /**
   * A test factory which returns a stream of [DynamicNode]s from the given parameters.
   * - Each [DynamicTest] in the stream uses its elements to create a
   *   new [TestEnvironment] instance, then executes [testAction].
   * - Each [DynamicNode] has a display name which includes the values of the parameters.
   *
   * @param params the elements to use for this test factory
   * @param testName a function to compute the name of each test.
   * @param testAction the test action to execute.
   * @return a [Stream] of [DynamicNode]s from the given parameters.
   * @see Kase1
   * @see TestEnvironmentFactory
   */
  public fun testFactory(
    params: Iterable<PARAM>,
    testName: (PARAM) -> String = maybeDisplayName(),
    testAction: suspend ENV.(PARAM) -> Unit
  ): Stream<out DynamicNode> = params.asSequence().asTests(testName, testAction)

  /**
   * A test factory which returns a stream of [DynamicNode]s from the given parameters.
   * - Each [DynamicTest] in the stream uses its elements to create a
   *   new [TestEnvironment] instance, then executes [testAction].
   * - Each [DynamicNode] has a display name which includes the values of the parameters.
   *
   * @param params the elements to use for this test factory
   * @param testName a function to compute the name of each test.
   * @param testAction the test action to execute.
   * @return a [Stream] of [DynamicNode]s from the given parameters.
   * @see Kase1
   * @see TestEnvironmentFactory
   */
  public fun testFactory(
    params: Sequence<PARAM>,
    testName: (PARAM) -> String = maybeDisplayName(),
    testAction: suspend ENV.(PARAM) -> Unit
  ): Stream<out DynamicNode> = params.asTests(testName, testAction)

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Array] of [Kase]s.
   *
   * @param testName a function to compute the name of each test.
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   */
  public fun <E : PARAM> Array<E>.asTests(
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend ENV.(E) -> Unit
  ): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase]s.
   *
   * @param testName a function to compute the name of each test.
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   */
  public fun <E : PARAM> Iterable<E>.asTests(
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend ENV.(E) -> Unit
  ): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Sequence] of [Kase]s.
   *
   * @param testName a function to compute the name of each test.
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   */
  public fun <E : PARAM> Sequence<E>.asTests(
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend ENV.(E) -> Unit
  ): Stream<out DynamicNode>

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
  public fun <E, T : TestEnvironment> Iterable<E>.asTests(
    testEnvironmentFactory: TestEnvironmentFactory<E, T>,
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend T.(E) -> Unit
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
  public fun <E, T : TestEnvironment> Sequence<E>.asTests(
    testEnvironmentFactory: TestEnvironmentFactory<E, T>,
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> {
    val location = TestLocation.get()
    return map { element ->
      DynamicTest.dynamicTest(testName(element), location.testUriOrNull) {
        test(
          param = element,
          factory = testEnvironmentFactory,
          parentNames = emptyList(),
          testLocation = location
        ) { testAction(element) }
      }
    }
      .asStream()
  }
}
