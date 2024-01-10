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
 * Common interface for creating dynamic tests with predefined
 * [kases][HasKases.kases] and a unique [TestEnvironment]
 *
 * @since 0.1.0
 */
public interface KaseTestFactory<T : TestEnvironment, Params : TestEnvironmentParams, K : Kase> :
  HasKases<K>,
  HasTestEnvironmentFactory<T, Params>,
  ContainerTransforms<KaseTestFactoryTestNodeBuilder<T, Params, K>> {

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param param The variant names related to the test.
   * @param testFunctionCoordinates The [TestFunctionCoordinates] from which the test is being run.
   * @param testAction The test action to run within the [TestEnvironment].
   * @since 0.1.0
   */
  public fun <E> test(
    param: E,
    parentNames: List<String> = emptyList(),
    testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
    testAction: suspend T.() -> Unit
  ) {

    @Suppress("UNCHECKED_CAST")
    val params = DefaultTestEnvironmentParams(
      names = parentNames + param.maybeDisplayNameOrToString(),
      testFunctionCoordinates = testFunctionCoordinates
    ) as Params
    val testEnvironment = testEnvironmentFactory.newTestEnvironment(params)

    runBlocking {
      testEnvironment.asClueCatching {
        testAction()
      }
    }
  }

  /**
   * Creates a stream of tests from [kases]
   *
   * @since 0.1.0
   */
  public fun testFactory(
    testName: (K) -> String = maybeDisplayName(),
    testAction: suspend T.(K) -> Unit
  ): Stream<out DynamicNode> = kases.asTests(testName, testAction)

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
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend T.(kase: E) -> Unit
  ): Stream<out DynamicNode> = kases.asSequence().asTests(testName, testAction)

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
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend T.(kase: E) -> Unit
  ): Stream<out DynamicNode> = kases.asSequence().asTests(testName, testAction)

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase]s.
   *
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   * @since 0.1.0
   */
  public fun <E> Iterable<E>.asTests(
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

  /**
   * Creates a [Stream] of [DynamicNode]s from this [Sequence] of [Kase]s.
   *
   * @param testAction the test action to run for each kase.
   * @return a [Stream] of [DynamicNode]s from these kases.
   * @since 0.1.0
   */
  public fun <E> Sequence<E>.asTests(
    testName: (E) -> String = maybeDisplayName(),
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> {
    val coords = TestFunctionCoordinates.get()
    return map { kaseElement ->

      DynamicTest.dynamicTest(
        testName(kaseElement),
        coords.testUriOrNull
      ) {
        test(
          param = kaseElement,
          parentNames = emptyList(),
          testFunctionCoordinates = coords
        ) { testAction(kaseElement) }
      }
    }.asStream()
  }

  override fun <E> Sequence<E>.asContainers(
    displayName: (E) -> String,
    testAction: KaseTestFactoryTestNodeBuilder<T, Params, K>.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode> {
    val coords = TestFunctionCoordinates.get()
    return map { e ->
      val name = displayName(e)
      KaseTestFactoryTestNodeBuilder(
        displayName = name,
        testFunctionCoordinates = coords,
        parent = null,
        kases = kases,
        delegateFactory = this@KaseTestFactory
      ).run {
        DynamicContainer.dynamicContainer(name, coords.testUriOrNull, testAction(e))
      }
    }
      .asStream()
  }
}
