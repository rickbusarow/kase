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
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, labels: KaseLabels14 = KaseLabels14()
): Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> {
  return DefaultKase14(
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
    element(value = a11, label = labels.a11Label),
    element(value = a12, label = labels.a12Label),
    element(value = a13, label = labels.a13Label),
    element(value = a14, label = labels.a14Label)
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
  a11Label: String = "a11",
  a12Label: String = "a12",
  a13Label: String = "a13",
  a14Label: String = "a14"
): KaseLabels14 {
  return KaseLabels14(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label, a9Label = a9Label, a10Label = a10Label, a11Label = a11Label, a12Label = a12Label, a13Label = a13Label, a14Label = a14Label)
}

/** */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> kases(
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
  args12: Iterable<A12>,
  args13: Iterable<A13>,
  args14: Iterable<A14>,
  labels: KaseLabels14 = KaseLabels14()
): List<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>> {
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
                          for (a12 in args12) {
                            for (a13 in args13) {
                              for (a14 in args14) {
                                add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, labels = labels))
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
    }
  }
}

/** */
@JvmName("asTestsKase14")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Iterable<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>>.asTests(
  labels: KaseLabels14 = KaseLabels14(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14) -> Unit
): Stream<out DynamicNode> = testFactory(this@asTests, labels, testAction)

/** */
@JvmName("testFactoryKase14")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> testFactory(
  vararg kases: Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>,
  labels: KaseLabels14 = KaseLabels14(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14) -> Unit
): Stream<out DynamicNode> {
  return testFactory(
    kases = kases.toList(),
    labels = labels,
    testAction = testAction
  )
}

/** */
@JvmName("testFactoryKase14")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> testFactory(
  kases: Iterable<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>>,
  labels: KaseLabels14 = KaseLabels14(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14) }
  )
}

/** */
@JvmName("testFactoryKase14")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> testFactory(
  vararg kases: Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>,
  crossinline kaseName: (Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14) }
}

/** A strongly-typed version of [Kase] for 14 parameters. */
public interface Kase14<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14> : KaseInternal<KaseLabels14> {

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
  /** The 12th parameter. */
  public val a12: A12
  /** The 13th parameter. */
  public val a13: A13
  /** The 14th parameter. */
  public val a14: A14

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
  override fun destructured(): List<KaseElement<Any?>> = elements
}

/** */
internal data class DefaultKase14<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14>(
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
  val a11Element: KaseElement<A11>,
  val a12Element: KaseElement<A12>,
  val a13Element: KaseElement<A13>,
  val a14Element: KaseElement<A14>
) : Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>, KaseInternal<KaseLabels14> {
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
  override val a12: A12 get() = a12Element.value
  override val a13: A13 get() = a13Element.value
  override val a14: A14 get() = a14Element.value

  override val elements: List<KaseElement<Any?>>
    get() = listOf(a1Element, a2Element, a3Element, a4Element, a5Element, a6Element, a7Element, a8Element, a9Element, a10Element, a11Element, a12Element, a13Element, a14Element)
}

/**
 * A strongly-typed version of [KaseLabels] for 14 parameters.
 *
 * @property a1Label The label for the [Kase14.a1] parameter.
 * @property a2Label The label for the [Kase14.a2] parameter.
 * @property a3Label The label for the [Kase14.a3] parameter.
 * @property a4Label The label for the [Kase14.a4] parameter.
 * @property a5Label The label for the [Kase14.a5] parameter.
 * @property a6Label The label for the [Kase14.a6] parameter.
 * @property a7Label The label for the [Kase14.a7] parameter.
 * @property a8Label The label for the [Kase14.a8] parameter.
 * @property a9Label The label for the [Kase14.a9] parameter.
 * @property a10Label The label for the [Kase14.a10] parameter.
 * @property a11Label The label for the [Kase14.a11] parameter.
 * @property a12Label The label for the [Kase14.a12] parameter.
 * @property a13Label The label for the [Kase14.a13] parameter.
 * @property a14Label The label for the [Kase14.a14] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
public data class KaseLabels14(
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
  val a12Label: String = "a12",
  val a13Label: String = "a13",
  val a14Label: String = "a14",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override fun destructured(): List<String> {
    return listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label, a11Label, a12Label, a13Label, a14Label)
  }
}
