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
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, labels: KaseLabels11 = KaseLabels11()
): Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
  return DefaultKase11(
    element(value = a1, label = labels.a1Label),
    element(value = a2, label = labels.a2Label),
    element(value = a3, label = labels.a3Label),
    element(value = a4, label = labels.a4Label),
    element(value = a5, label = labels.a5Label),
    element(value = a6, label = labels.a6Label),
    element(value = a7, label = labels.a7Label),
    element(value = a8, label = labels.a8Label),
    element(value = a9, label = labels.a9Label),
    element(value = a10, label = labels.a10Label),
    element(value = a11, label = labels.a11Label)
  )
}

/** */
public fun labels(
  a1Label: String = "a1",
  a2Label: String = "a2",
  a3Label: String = "a3",
  a4Label: String = "a4",
  a5Label: String = "a5",
  a6Label: String = "a6",
  a7Label: String = "a7",
  a8Label: String = "a8",
  a9Label: String = "a9",
  a10Label: String = "a10",
  a11Label: String = "a11"
): KaseLabels11 {
  return KaseLabels11(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label, a9Label = a9Label, a10Label = a10Label, a11Label = a11Label)
}

/** */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>,
  args9: Iterable<A9>,
  args10: Iterable<A10>,
  args11: Iterable<A11>,
  labels: KaseLabels11 = KaseLabels11()
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                for (a7 in args7) {
                  for (a8 in args8) {
                    for (a9 in args9) {
                      for (a10 in args10) {
                        for (a11 in args11) {
                          add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, labels = labels))
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

/** */
@JvmName("asTestsKase11")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Iterable<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>>.asTests(
  labels: KaseLabels11 = KaseLabels11(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode> = testFactory(this@asTests, labels, testAction)

/** */
@JvmName("testFactoryKase11")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  vararg kases: Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>,
  labels: KaseLabels11 = KaseLabels11(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode> {
  return testFactory(
    kases = kases.toList(),
    labels = labels,
    testAction = testAction
  )
}

/** */
@JvmName("testFactoryKase11")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  kases: Iterable<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>>,
  labels: KaseLabels11 = KaseLabels11(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11) }
  )
}

/** */
@JvmName("testFactoryKase11")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  vararg kases: Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>,
  crossinline kaseName: (Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11) }
}

/** A strongly-typed version of [Kase] for 11 parameters. */
public interface Kase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11> : KaseInternal<KaseLabels11> {

  /** The 1st parameter. */
  public val a1: A1
  /** The 2nd parameter. */
  public val a2: A2
  /** The 3rd parameter. */
  public val a3: A3
  /** The 4th parameter. */
  public val a4: A4
  /** The 5th parameter. */
  public val a5: A5
  /** The 6th parameter. */
  public val a6: A6
  /** The 7th parameter. */
  public val a7: A7
  /** The 8th parameter. */
  public val a8: A8
  /** The 9th parameter. */
  public val a9: A9
  /** The 10th parameter. */
  public val a10: A10
  /** The 11th parameter. */
  public val a11: A11

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
  override fun destructured(): List<KaseElement<Any?>> = elements
}

/** */
internal data class DefaultKase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11>(
  val a1Element: KaseElement<A1>,
  val a2Element: KaseElement<A2>,
  val a3Element: KaseElement<A3>,
  val a4Element: KaseElement<A4>,
  val a5Element: KaseElement<A5>,
  val a6Element: KaseElement<A6>,
  val a7Element: KaseElement<A7>,
  val a8Element: KaseElement<A8>,
  val a9Element: KaseElement<A9>,
  val a10Element: KaseElement<A10>,
  val a11Element: KaseElement<A11>
) : Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>, KaseInternal<KaseLabels11> {
  override val a1: A1 get() = a1Element.value
  override val a2: A2 get() = a2Element.value
  override val a3: A3 get() = a3Element.value
  override val a4: A4 get() = a4Element.value
  override val a5: A5 get() = a5Element.value
  override val a6: A6 get() = a6Element.value
  override val a7: A7 get() = a7Element.value
  override val a8: A8 get() = a8Element.value
  override val a9: A9 get() = a9Element.value
  override val a10: A10 get() = a10Element.value
  override val a11: A11 get() = a11Element.value

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1Element, a2Element, a3Element, a4Element, a5Element, a6Element, a7Element, a8Element, a9Element, a10Element, a11Element)
}

/**
 * A strongly-typed version of [KaseLabels] for 11 parameters.
 *
 * @property a1Label The label for the [Kase11.a1] parameter.
 * @property a2Label The label for the [Kase11.a2] parameter.
 * @property a3Label The label for the [Kase11.a3] parameter.
 * @property a4Label The label for the [Kase11.a4] parameter.
 * @property a5Label The label for the [Kase11.a5] parameter.
 * @property a6Label The label for the [Kase11.a6] parameter.
 * @property a7Label The label for the [Kase11.a7] parameter.
 * @property a8Label The label for the [Kase11.a8] parameter.
 * @property a9Label The label for the [Kase11.a9] parameter.
 * @property a10Label The label for the [Kase11.a10] parameter.
 * @property a11Label The label for the [Kase11.a11] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
public data class KaseLabels11(
  val a1Label: String = "a1",
  val a2Label: String = "a2",
  val a3Label: String = "a3",
  val a4Label: String = "a4",
  val a5Label: String = "a5",
  val a6Label: String = "a6",
  val a7Label: String = "a7",
  val a8Label: String = "a8",
  val a9Label: String = "a9",
  val a10Label: String = "a10",
  val a11Label: String = "a11",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override fun destructured(): List<String> {
    return listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label, a11Label)
  }
}
