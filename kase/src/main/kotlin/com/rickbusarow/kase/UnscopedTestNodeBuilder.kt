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
 * Builds a dynamic test container with a specific name and a list of dynamic nodes (tests
 * or containers). Provides functions for adding child dynamic tests and containers.
 *
 * @property displayName the name of the test container
 * @property testLocation Captured before executing any tests,
 *   meaning that it's the frame that called `asTests { ... }`
 * @property parent the parent node, or `null` if this is the root container
 * @since 0.7.0
 */
public class UnscopedTestNodeBuilder(
  override val displayName: String,
  override val testLocation: TestLocation,
  override val parent: TestNodeBuilder?
) : DynamicContainerTransforms<UnscopedTestNodeBuilder>, TestNodeBuilder {

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
    val location = TestLocation.get()
    return map { element ->
      DynamicTest.dynamicTest(testName(element), location.testUriOrNull) {
        runBlocking { testAction(element) }
      }
    }
      .asStream()
  }

  override fun <E> Sequence<E>.asContainers(
    displayName: (E) -> String,
    childNodes: UnscopedTestNodeBuilder.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode> {
    val location = TestLocation.get()
    return map { e ->
      val name = displayName(e)

      val builder = UnscopedTestNodeBuilder(
        displayName = name,
        testLocation = location,
        parent = this@UnscopedTestNodeBuilder
      )
      DynamicContainer.dynamicContainer(
        name,
        location.testUriOrNull,
        builder.run { childNodes(e) }
      )
    }
      .asStream()
  }
}
