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

@file:Suppress("PackageDirectoryMismatch", "DuplicatedCode", "MaxLineLength")
@file:JvmMultifileClass
@file:JvmName("KasesKt")

package com.rickbusarow.kase

import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.POSTFIX_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.PREFIX_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.element
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream
/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase20:a1] parameter.
 * @param a2 the [Kase20:a2] parameter.
 * @param a3 the [Kase20:a3] parameter.
 * @param a4 the [Kase20:a4] parameter.
 * @param a5 the [Kase20:a5] parameter.
 * @param a6 the [Kase20:a6] parameter.
 * @param a7 the [Kase20:a7] parameter.
 * @param a8 the [Kase20:a8] parameter.
 * @param a9 the [Kase20:a9] parameter.
 * @param a10 the [Kase20:a10] parameter.
 * @param a11 the [Kase20:a11] parameter.
 * @param a12 the [Kase20:a12] parameter.
 * @param a13 the [Kase20:a13] parameter.
 * @param a14 the [Kase20:a14] parameter.
 * @param a15 the [Kase20:a15] parameter.
 * @param a16 the [Kase20:a16] parameter.
 * @param a17 the [Kase20:a17] parameter.
 * @param a18 the [Kase20:a18] parameter.
 * @param a19 the [Kase20:a19] parameter.
 * @param a20 the [Kase20:a20] parameter.
 * @param labels the [KaseLabels] to use for this [Kase]
 * @param delimiter the delimiter between the label and the value, like `": "` in `"label: value"`
 * @param separator the separator between each label/value pair, like `" | "` in `"label1: value1 | label2: value2"`
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20,
  labels: KaseLabels20 = KaseLabels20(),
  delimiter: String = KaseLabels.DELIMITER_DEFAULT,
  separator: String = KaseLabels.SEPARATOR_DEFAULT
): Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  return DefaultKase20(
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
    element(value = a14, label = labels.a14Label),
    element(value = a15, label = labels.a15Label),
    element(value = a16, label = labels.a16Label),
    element(value = a17, label = labels.a17Label),
    element(value = a18, label = labels.a18Label),
    element(value = a19, label = labels.a19Label),
    element(value = a20, label = labels.a20Label),
    delimiter = delimiter,
    separator = separator
  )
}

/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20,
  labels: KaseLabels20 = KaseLabels20(),
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment<K>,
        K : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  test(
    kase = kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, labels),
    testFunctionName = testFunctionName,
    testAction = testAction
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
  a14Label: String = "a14",
  a15Label: String = "a15",
  a16Label: String = "a16",
  a17Label: String = "a17",
  a18Label: String = "a18",
  a19Label: String = "a19",
  a20Label: String = "a20"
): KaseLabels20 {
  return KaseLabels20(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label, a9Label = a9Label, a10Label = a10Label, a11Label = a11Label, a12Label = a12Label, a13Label = a13Label, a14Label = a14Label, a15Label = a15Label, a16Label = a16Label, a17Label = a17Label, a18Label = a18Label, a19Label = a19Label, a20Label = a20Label)
}

/** */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kases(
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
  args15: Iterable<A15>,
  args16: Iterable<A16>,
  args17: Iterable<A17>,
  args18: Iterable<A18>,
  args19: Iterable<A19>,
  args20: Iterable<A20>,
  labels: KaseLabels20 = KaseLabels20()
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
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
                                for (a15 in args15) {
                                  for (a16 in args16) {
                                    for (a17 in args17) {
                                      for (a18 in args18) {
                                        for (a19 in args19) {
                                          for (a20 in args20) {
                                            add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, labels = labels))
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
          }
        }
      }
    }
  }
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase20DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> Iterable<K>.asTests(
  labels: KaseLabels20 = KaseLabels20(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  return testFactory(this@asTests, labels, testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase20DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  vararg kases: K,
  labels: KaseLabels20 = KaseLabels20(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase20DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  kases: Iterable<K>,
  labels: KaseLabels20 = KaseLabels20(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {

  return kases.asTests(
    testName = { kase -> kase.displayName() },
    testAction = { kase -> testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5, kase.a6, kase.a7, kase.a8, kase.a9, kase.a10, kase.a11, kase.a12, kase.a13, kase.a14, kase.a15, kase.a16, kase.a17, kase.a18, kase.a19, kase.a20) }
  )
}

/** */
@JvmName("testFactoryKase20")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  vararg kases: Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>,
  crossinline kaseName: (Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18, it.a19, it.a20) }
}

/** A strongly-typed version of [Kase] for 20 parameters. */
public interface Kase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20> : Kase<KaseLabels20> {

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
  /** The 15th parameter. */
  public val a15: A15
  /** The 16th parameter. */
  public val a16: A16
  /** The 17th parameter. */
  public val a17: A17
  /** The 18th parameter. */
  public val a18: A18
  /** The 19th parameter. */
  public val a19: A19
  /** The 20th parameter. */
  public val a20: A20

  override fun <T> plus(label: String, value: T): Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, T>
}

/**
 * A strongly-typed version of [KaseLabels] for 20 parameters.
 *
 * @property a1Label The label for the [Kase20.a1] parameter.
 * @property a2Label The label for the [Kase20.a2] parameter.
 * @property a3Label The label for the [Kase20.a3] parameter.
 * @property a4Label The label for the [Kase20.a4] parameter.
 * @property a5Label The label for the [Kase20.a5] parameter.
 * @property a6Label The label for the [Kase20.a6] parameter.
 * @property a7Label The label for the [Kase20.a7] parameter.
 * @property a8Label The label for the [Kase20.a8] parameter.
 * @property a9Label The label for the [Kase20.a9] parameter.
 * @property a10Label The label for the [Kase20.a10] parameter.
 * @property a11Label The label for the [Kase20.a11] parameter.
 * @property a12Label The label for the [Kase20.a12] parameter.
 * @property a13Label The label for the [Kase20.a13] parameter.
 * @property a14Label The label for the [Kase20.a14] parameter.
 * @property a15Label The label for the [Kase20.a15] parameter.
 * @property a16Label The label for the [Kase20.a16] parameter.
 * @property a17Label The label for the [Kase20.a17] parameter.
 * @property a18Label The label for the [Kase20.a18] parameter.
 * @property a19Label The label for the [Kase20.a19] parameter.
 * @property a20Label The label for the [Kase20.a20] parameter.
 * @property delimiter The delimiter between the label and the value.  The default is `: `.
 * @property separator The separator between each label/value pair.  The default is ` | `.
 */
@Poko
public class KaseLabels20(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  public val a4Label: String = "a4",
  public val a5Label: String = "a5",
  public val a6Label: String = "a6",
  public val a7Label: String = "a7",
  public val a8Label: String = "a8",
  public val a9Label: String = "a9",
  public val a10Label: String = "a10",
  public val a11Label: String = "a11",
  public val a12Label: String = "a12",
  public val a13Label: String = "a13",
  public val a14Label: String = "a14",
  public val a15Label: String = "a15",
  public val a16Label: String = "a16",
  public val a17Label: String = "a17",
  public val a18Label: String = "a18",
  public val a19Label: String = "a19",
  public val a20Label: String = "a20",
  override val delimiter: String = DELIMITER_DEFAULT,
  override val separator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label, a11Label, a12Label, a13Label, a14Label, a15Label, a16Label, a17Label, a18Label, a19Label, a20Label)
  }
}

@Poko
internal class DefaultKase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20>(
  val a1Element: KaseParameterWithLabel<A1>,
  val a2Element: KaseParameterWithLabel<A2>,
  val a3Element: KaseParameterWithLabel<A3>,
  val a4Element: KaseParameterWithLabel<A4>,
  val a5Element: KaseParameterWithLabel<A5>,
  val a6Element: KaseParameterWithLabel<A6>,
  val a7Element: KaseParameterWithLabel<A7>,
  val a8Element: KaseParameterWithLabel<A8>,
  val a9Element: KaseParameterWithLabel<A9>,
  val a10Element: KaseParameterWithLabel<A10>,
  val a11Element: KaseParameterWithLabel<A11>,
  val a12Element: KaseParameterWithLabel<A12>,
  val a13Element: KaseParameterWithLabel<A13>,
  val a14Element: KaseParameterWithLabel<A14>,
  val a15Element: KaseParameterWithLabel<A15>,
  val a16Element: KaseParameterWithLabel<A16>,
  val a17Element: KaseParameterWithLabel<A17>,
  val a18Element: KaseParameterWithLabel<A18>,
  val a19Element: KaseParameterWithLabel<A19>,
  val a20Element: KaseParameterWithLabel<A20>,
  override val delimiter: String,
  override val separator: String,
) : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>, KaseInternal<KaseLabels20> {
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
  override val a15: A15 get() = a15Element.value
  override val a16: A16 get() = a16Element.value
  override val a17: A17 get() = a17Element.value
  override val a18: A18 get() = a18Element.value
  override val a19: A19 get() = a19Element.value
  override val a20: A20 get() = a20Element.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1Element, a2Element, a3Element, a4Element, a5Element, a6Element, a7Element, a8Element, a9Element, a10Element, a11Element, a12Element, a13Element, a14Element, a15Element, a16Element, a17Element, a18Element, a19Element, a20Element)

  override fun <T> plus(label: String, value: T): DefaultKase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, T> {
    return DefaultKase21(
      a1Element = a1Element,
      a2Element = a2Element,
      a3Element = a3Element,
      a4Element = a4Element,
      a5Element = a5Element,
      a6Element = a6Element,
      a7Element = a7Element,
      a8Element = a8Element,
      a9Element = a9Element,
      a10Element = a10Element,
      a11Element = a11Element,
      a12Element = a12Element,
      a13Element = a13Element,
      a14Element = a14Element,
      a15Element = a15Element,
      a16Element = a16Element,
      a17Element = a17Element,
      a18Element = a18Element,
      a19Element = a19Element,
      a20Element = a20Element,
      a21Element = element(value = value, label = label),
      delimiter = delimiter,
      separator = separator
    )
  }
}
