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

package com.rickbusarow.kase.internal

import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.TestNodeBuilder
import com.rickbusarow.kase.files.TestFunctionCoordinates
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import kotlin.streams.asStream

@Poko
internal class DefaultTestNodeBuilder @PublishedApi internal constructor(
  override val displayName: String,
  override val testFunctionCoordinates: TestFunctionCoordinates,
  override val parent: TestNodeBuilder?
) : TestNodeBuilder {

  @PublishedApi
  internal val nodes: MutableList<() -> DynamicNode> = mutableListOf()

  override fun nodeSequence(): Sequence<DynamicNode> = nodes.asSequence().map { it() }

  override fun build(): DynamicNode {
    return DynamicContainer.dynamicContainer(displayName, nodeSequence().asStream())
  }

  override fun test(name: String, testAction: () -> Unit) {
    nodes.add {
      DynamicTest.dynamicTest(name, testFunctionCoordinates.testUriOrNull) { testAction() }
    }
  }

  override fun container(name: String, init: TestNodeBuilder.() -> Unit) {
    nodes.add {
      DefaultTestNodeBuilder(
        displayName = name,
        testFunctionCoordinates = testFunctionCoordinates,
        parent = this
      )
        .apply(init)
        .build()
    }
  }

  override fun <E> Iterable<E>.asTests(
    testName: (E) -> String,
    testAction: (E) -> Unit
  ): TestNodeBuilder = this@DefaultTestNodeBuilder.apply {
    for (element in this@asTests) {
      test(testName(element)) { testAction(element) }
    }
  }

  override fun <T : TestEnvironment, K : Kase> Iterable<K>.asTests(
    testName: (K) -> String,
    testEnvironmentFactory: (kase: K) -> T,
    testAction: T.(K) -> Unit
  ): TestNodeBuilder = this@DefaultTestNodeBuilder.apply {
    for (kase in this@asTests) {
      test(testName(kase)) {
        val environment = testEnvironmentFactory.invoke(kase)
        environment.testAction(kase)
      }
    }
  }

  override fun <T : TestEnvironment, K : Kase> Iterable<K>.asTests(
    testEnvironmentFactory: (kase: K) -> T,
    testAction: T.(K) -> Unit
  ): TestNodeBuilder = this@DefaultTestNodeBuilder.apply {
    for (kase in this@asTests) {
      test(kase.displayName) {
        val environment = testEnvironmentFactory.invoke(kase)
        environment.testAction(kase)
      }
    }
  }

  override fun <E> Sequence<E>.asTests(
    testName: (E) -> String,
    testAction: (E) -> Unit
  ): TestNodeBuilder = this@DefaultTestNodeBuilder.apply {
    for (element in this@asTests) {
      test(testName(element)) { testAction(element) }
    }
  }

  override fun <E> Iterable<E>.asContainers(
    testName: (E) -> String,
    testAction: TestNodeBuilder.(E) -> Unit
  ): TestNodeBuilder = this@DefaultTestNodeBuilder.apply {
    for (element in this@asContainers) {
      container(testName(element)) { testAction(element) }
    }
  }

  override fun <E> Sequence<E>.asContainers(
    testName: (E) -> String,
    testAction: TestNodeBuilder.(E) -> Unit
  ): TestNodeBuilder = this@DefaultTestNodeBuilder.apply {
    for (element in this@asContainers) {
      container(testName(element)) { testAction(element) }
    }
  }
}
