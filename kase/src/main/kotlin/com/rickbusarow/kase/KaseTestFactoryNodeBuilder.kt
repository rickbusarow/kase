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
import java.util.stream.Stream
import kotlin.streams.asStream

/**
 * Enables the creation of multiple node layers without
 * losing the scope of the original TestEnvironment factory.
 *
 * @since 0.6.0
 */
@KaseTestBuilderDsl
public class KaseTestFactoryNodeBuilder<T : TestEnvironment, K : Kase>(
  private val delegateFactory: KaseTestFactory<T, K>,
  delegateNodeBuilder: TestNodeBuilder
) : TestNodeBuilder by delegateNodeBuilder,
  KaseTestFactory<T, K> by delegateFactory,
  ScopedDynamicTestTransform<T> {

  override fun <E : Any> Sequence<E>.asTests(
    testAction: suspend T.(E) -> Unit
  ): Stream<out DynamicNode> {
    return map { kase ->
    }
  }

  /**
   * Enables the creation of multiple node layers without
   * losing the scope of the original TestEnvironment factory.
   */
  override fun <E : K> Sequence<E>.asTests(testAction: suspend T.(E) -> Unit): Stream<DynamicNode> {
    return this@KaseTestFactoryNodeBuilder.also { builder ->
      for (kase in this@asTests) {
        test(kase.displayName) {
          val parents = generateSequence<TestNodeBuilder>(builder) { it.parent }
          val names = parents.toList()
            .dropLast(1)
            .asReversed()
            .map { it.displayName }

          test(
            kase = kase,
            parentNames = names,
            testFunctionCoordinates = testFunctionCoordinates
          ) { testAction(kase) }
        }
      }
    }
      .nodeSequence()
      .asStream()
  }

  override fun <E> Iterable<E>.asTests(
    testName: (E) -> String,
    testAction: suspend T.(E) -> Unit
  ): TestNodeBuilder {
    TODO("Not yet implemented")
  }

  override fun <E> Sequence<E>.asTests(
    testName: (E) -> String,
    testAction: suspend T.(E) -> Unit
  ): TestNodeBuilder {
    TODO("Not yet implemented")
  }

  override fun newTestEnvironment(
    param: Any,
    parentNames: List<String>,
    testFunctionCoordinates: TestFunctionCoordinates
  ): T = delegateFactory.newTestEnvironment(param, parentNames, testFunctionCoordinates)

  internal fun wrapContainer(
    name: String,
    init: KaseTestFactoryNodeBuilder<T, K>.() -> Unit
  ) {
    container(name) del@{
      val delegate: TestNodeBuilder = this@del

      val child = KaseTestFactoryNodeBuilder<T, K>(
        delegateFactory = this@KaseTestFactoryNodeBuilder.delegateFactory,
        delegateNodeBuilder = delegate
      )
      child.init()
    }
  }
}
