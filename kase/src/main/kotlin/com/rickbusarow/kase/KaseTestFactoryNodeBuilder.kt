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

/**
 * Enables the creation of multiple layers of nodes without
 * losing the scope of the original TestEnvironment factory.
 */
public class KaseTestFactoryNodeBuilder<T : TestEnvironment, K : Kase>(
  delegateFactory: KaseTestFactory<T, K>,
  delegateNodeBuilder: TestNodeBuilder
) : TestNodeBuilder by delegateNodeBuilder,
  KaseTestFactory<T, K> by delegateFactory {

  /**
   * Enables the creation of multiple layers of nodes without
   * losing the scope of the original TestEnvironment factory.
   */
  public fun Iterable<K>.asTests(testAction: T.(K) -> Unit): TestNodeBuilder {
    return this@KaseTestFactoryNodeBuilder.also { builder ->
      for (kase in this@asTests) {
        test(kase.displayName) {
          val parents = generateSequence<TestNodeBuilder>(builder) { it.parent }
          val names = parents.toList()
            .dropLast(1)
            .asReversed()
            .map { it.displayName }
            .plus(kase.displayName)
          val environment = newTestEnvironment(names, testFunctionCoordinates)
          environment.testAction(kase)
        }
      }
    }
  }

  internal fun wrapContainer(
    name: String,
    init: KaseTestFactoryNodeBuilder<T, K>.() -> Unit
  ) {
    container(name) del@{
      val delegate: TestNodeBuilder = this@del

      val child = KaseTestFactoryNodeBuilder<T, K>(
        delegateFactory = this@KaseTestFactoryNodeBuilder,
        delegateNodeBuilder = delegate
      )
      child.init()
    }
  }
}
