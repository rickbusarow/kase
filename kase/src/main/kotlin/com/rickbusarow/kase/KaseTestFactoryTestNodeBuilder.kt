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
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream
import kotlin.streams.asStream

@KaseTestBuilderDsl
public class KaseTestFactoryTestNodeBuilder<T : TestEnvironment, Params : TestEnvironmentParams, K : Kase>(
  override val displayName: String,
  override val testFunctionCoordinates: TestFunctionCoordinates,
  override val parent: TestNodeBuilder?,
  override val kases: List<K>,
  private val delegateFactory: KaseTestFactory<T, Params, K>
) : KaseTestFactory<T, Params, K> by delegateFactory, TestNodeBuilder {

  override fun testFactory(
    testName: (K) -> String,
    testAction: suspend T.(K) -> Unit
  ): Stream<out DynamicNode> = kases.asTests(testName, testAction)

  override fun <E : K> testFactory(
    vararg kases: E,
    testName: (E) -> String,
    testAction: suspend T.(kase: E) -> Unit
  ): Stream<out DynamicNode> = kases.asSequence().asTests(testName, testAction)

  override fun <E : K> testFactory(
    kases: Iterable<E>,
    testName: (E) -> String,
    testAction: suspend T.(kase: E) -> Unit
  ): Stream<out DynamicNode> = kases.asSequence().asTests(testName, testAction)

  override fun <E> Iterable<E>.asTests(
    testName: (E) -> String,
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> = asSequence().asTests(testName, testAction)

  override fun <E> Sequence<E>.asTests(
    testName: (E) -> String,
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> = map { param ->
    val name = testName(param)
    DynamicTest.dynamicTest(name, testFunctionCoordinates.testUriOrNull) {
      test(
        param = param,
        parentNames = namesFromRoot,
        testFunctionCoordinates = testFunctionCoordinates
      ) { testAction(param) }
    }
  }.asStream()
}
