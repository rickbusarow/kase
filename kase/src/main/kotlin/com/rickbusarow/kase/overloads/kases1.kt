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

@file:Suppress("PackageDirectoryMismatch")
@file:JvmMultifileClass
@file:JvmName("KasesKt")

package com.rickbusarow.kase

import com.rickbusarow.kase.KaseElement.Companion.element
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

/** */
public fun <A1> kase(
  a1: A1, labels: KaseLabels1 = KaseLabels1()
): Kase1<A1> {
  return DefaultKase1(
    element(value = a1, label = labels.a1Label)
  )
}

/** */
public fun labels(
  a1Label: String = "a1"
): KaseLabels1 {
  return KaseLabels1(a1Label = a1Label)
}

/** */
public fun <A1> kases(
  args1: Iterable<A1>,
  labels: KaseLabels1 = KaseLabels1()
): List<Kase1<A1>> {
  return buildList {
    for (a1 in args1) {
      add(kase(a1 = a1, labels = labels))
    }
  }
}

/** */
@JvmName("asTestsKase1")
public inline fun <A1> Iterable<Kase1<A1>>.asTests(
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> = testFactory(this@asTests, labels, testAction)

/** */
@JvmName("testFactoryKase1")
public inline fun <A1> testFactory(
  vararg kases: Kase1<A1>,
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory(
    kases = kases.toList(),
    labels = labels,
    testAction = testAction
  )
}

/** */
@JvmName("testFactoryKase1")
public inline fun <A1> testFactory(
  kases: Iterable<Kase1<A1>>,
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { testAction(it.a1) }
  )
}

/** */
@JvmName("testFactoryKase1")
public inline fun <A1> testFactory(
  vararg kases: Kase1<A1>,
  crossinline kaseName: (Kase1<A1>) -> String = { it.toString() },
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1) }
}

/** A strongly-typed version of [Kase] for 1 parameters. */
public interface Kase1<out A1> : KaseInternal<KaseLabels1> {

  /** The 1st parameter. */
  public val a1: A1

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1)
  override fun destructured(): List<KaseElement<Any?>> = elements
}

/** */
internal data class DefaultKase1<out A1>(
  val a1Element: KaseElement<A1>
) : Kase1<A1>, KaseInternal<KaseLabels1> {
  override val a1: A1 get() = a1Element.value

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1Element)
}

/**
 * A strongly-typed version of [KaseLabels] for 1 parameters.
 *
 * @property a1Label The label for the [Kase1.a1] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
public data class KaseLabels1(
  val a1Label: String = "a1",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override fun destructured(): List<String> {
    return listOf(a1Label)
  }
}
