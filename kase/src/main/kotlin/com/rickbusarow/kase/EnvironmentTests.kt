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
 * Common interface for creating dynamic tests with a unique [TestEnvironment]
 *
 * @since 0.7.0
 */
public interface EnvironmentTests<PARAM, ENV, FACT> :
  HasTestEnvironmentFactory<FACT>,
  DynamicContainerTransforms<EnvironmentTestNodeBuilder<PARAM, ENV, FACT>>,
  DynamicTestTransforms<PARAM, ENV>
  where ENV : TestEnvironment,
        FACT : TestEnvironmentFactory<PARAM, ENV> {

  override fun <E : PARAM> Sequence<E>.asTests(
    testName: (E) -> String,
    testAction: suspend ENV.(E) -> Unit
  ): Stream<out DynamicNode> = asTests(
    testEnvironmentFactory = testEnvironmentFactory,
    testName = testName,
    testAction = testAction
  )

  override fun <E, T : TestEnvironment> Sequence<E>.asTests(
    testEnvironmentFactory: TestEnvironmentFactory<E, T>,
    testName: (E) -> String,
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> {
    val location = TestLocation.get()
    return map { param ->

      val name = testName(param)
      DynamicTest.dynamicTest(name, location.testUriOrNull) {
        test(
          param = param,
          factory = testEnvironmentFactory,
          parentNames = listOf(name),
          testLocation = location
        ) { testAction(param) }
      }
    }.asStream()
  }

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
        parent = null,
        factory = testEnvironmentFactory
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
