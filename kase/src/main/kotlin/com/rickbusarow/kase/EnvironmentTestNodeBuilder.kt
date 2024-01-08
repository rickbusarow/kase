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
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream
import kotlin.streams.asStream

/**
 * Juxtaposition of [EnvironmentTests] and a [TestNodeBuilder], so that nested
 * containers and dynamic tests are created within the scope of the parent container.
 */
public class EnvironmentTestNodeBuilder<PARAM, ENV, FACT>(
  override val displayName: String,
  override val testLocation: TestLocation,
  override val parent: TestNodeBuilder?,
  private val factory: FACT
) : EnvironmentTests<PARAM, ENV, FACT>,
  DynamicTestTransforms<ENV, PARAM>,
  TestNodeBuilder
  where ENV : TestEnvironment,
        FACT : TestEnvironmentFactory<PARAM, ENV> {

  override val testEnvironmentFactory: FACT get() = factory

  /** Creates a stream of tests from [HasParams.params] */
  override fun HasParams<PARAM>.testFactory(
    testName: (PARAM) -> String,
    testAction: suspend ENV.(PARAM) -> Unit
  ): Stream<out DynamicNode> = with(this@EnvironmentTestNodeBuilder) {
    this@testFactory.params.asSequence().asTests(testName, testAction)
  }

  override fun <E : PARAM> Sequence<E>.asTests(
    testName: (E) -> String,
    testAction: suspend ENV.(E) -> Unit
  ): Stream<out DynamicNode> = map { param ->
    val name = testName(param)
    DynamicTest.dynamicTest(name, testLocation.testUriOrNull) {
      test(
        param = param,
        parentNames = namesFromRoot,
        testLocation = testLocation
      ) { testAction(param) }
    }
  }.asStream()

  override fun <E, T : TestEnvironment> Sequence<E>.asTests(
    testEnvironmentFactory: TestEnvironmentFactory<E, T>,
    testName: (E) -> String,
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> {
    val location = TestLocation.get()
    return map { param ->

      DynamicTest.dynamicTest(testName(param), location.testUriOrNull) {
        test(
          param = param,
          factory = testEnvironmentFactory,
          parentNames = namesFromRoot,
          testLocation = location
        ) { testAction(param) }
      }
    }.asStream()
  }

  override fun <E> Iterable<E>.asContainers(
    displayName: (E) -> String,
    testAction: EnvironmentTestNodeBuilder<PARAM, ENV, FACT>.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode> = asSequence().asContainers(displayName, testAction)

  override fun <E> Sequence<E>.asContainers(
    displayName: (E) -> String,
    childNodes: EnvironmentTestNodeBuilder<PARAM, ENV, FACT>.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode> {
    val testLocation = TestLocation.get()
    return map { e ->
      val name = displayName(e)
      val builder = EnvironmentTestNodeBuilder(
        displayName = name,
        testLocation = testLocation,
        parent = this@EnvironmentTestNodeBuilder,
        factory = factory
      )
      DynamicContainer.dynamicContainer(
        name,
        testLocation.testUriOrNull,
        builder.run { childNodes(e) }
      )
    }
      .asStream()
  }
}
