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
public fun <A1, A2, A3> kase(
  a1: A1, a2: A2, a3: A3, labels: KaseLabels3 = KaseLabels3()
): Kase3<A1, A2, A3> {
  return DefaultKase3(
    element(value = a1, label = labels.a1Label),
    element(value = a2, label = labels.a2Label),
    element(value = a3, label = labels.a3Label)
  )
}

/** */
public fun labels(
  a1Label: String = "a1",
  a2Label: String = "a2",
  a3Label: String = "a3"
): KaseLabels3 {
  return KaseLabels3(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label)
}

/** */
public fun <A1, A2, A3> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  labels: KaseLabels3 = KaseLabels3()
): List<Kase3<A1, A2, A3>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          add(kase(a1 = a1, a2 = a2, a3 = a3, labels = labels))
        }
      }
    }
  }
}

/** */
@JvmName("asTestsKase3")
public inline fun <A1, A2, A3> Iterable<Kase3<A1, A2, A3>>.asTests(
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> = testFactory(this@asTests, labels, testAction)

/** */
@JvmName("testFactoryKase3")
public inline fun <A1, A2, A3> testFactory(
  vararg kases: Kase3<A1, A2, A3>,
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return testFactory(
    kases = kases.toList(),
    labels = labels,
    testAction = testAction
  )
}

/** */
@JvmName("testFactoryKase3")
public inline fun <A1, A2, A3> testFactory(
  kases: Iterable<Kase3<A1, A2, A3>>,
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { testAction(it.a1, it.a2, it.a3) }
  )
}

/** */
@JvmName("testFactoryKase3")
public inline fun <A1, A2, A3> testFactory(
  vararg kases: Kase3<A1, A2, A3>,
  crossinline kaseName: (Kase3<A1, A2, A3>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3) }
}

/** A strongly-typed version of [Kase] for 3 parameters. */
public interface Kase3<out A1, out A2, out A3> : KaseInternal<KaseLabels3> {

  /** The 1st parameter. */
  public val a1: A1
  /** The 2nd parameter. */
  public val a2: A2
  /** The 3rd parameter. */
  public val a3: A3

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1, a2, a3)
  override fun destructured(): List<KaseElement<Any?>> = elements
}

/** */
internal data class DefaultKase3<out A1, out A2, out A3>(
  val a1Element: KaseElement<A1>,
  val a2Element: KaseElement<A2>,
  val a3Element: KaseElement<A3>
) : Kase3<A1, A2, A3>, KaseInternal<KaseLabels3> {
  override val a1: A1 get() = a1Element.value
  override val a2: A2 get() = a2Element.value
  override val a3: A3 get() = a3Element.value

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1Element, a2Element, a3Element)
}

/**
 * A strongly-typed version of [KaseLabels] for 3 parameters.
 *
 * @property a1Label The label for the [Kase3.a1] parameter.
 * @property a2Label The label for the [Kase3.a2] parameter.
 * @property a3Label The label for the [Kase3.a3] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
public data class KaseLabels3(
  val a1Label: String = "a1",
  val a2Label: String = "a2",
  val a3Label: String = "a3",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override fun destructured(): List<String> {
    return listOf(a1Label, a2Label, a3Label)
  }
}
