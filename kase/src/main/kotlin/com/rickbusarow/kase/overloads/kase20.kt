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
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParam
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

/** A strongly-typed version of [Kase] for 20 parameters. */
public interface Kase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20> : Kase {

  /** The 1st parameter. */
  public val a1: A1
  /** The 1st parameter. */
  public val a1WithLabel: KaseParameterWithLabel<A1>
  /** The 2nd parameter. */
  public val a2: A2
  /** The 2nd parameter. */
  public val a2WithLabel: KaseParameterWithLabel<A2>
  /** The 3rd parameter. */
  public val a3: A3
  /** The 3rd parameter. */
  public val a3WithLabel: KaseParameterWithLabel<A3>
  /** The 4th parameter. */
  public val a4: A4
  /** The 4th parameter. */
  public val a4WithLabel: KaseParameterWithLabel<A4>
  /** The 5th parameter. */
  public val a5: A5
  /** The 5th parameter. */
  public val a5WithLabel: KaseParameterWithLabel<A5>
  /** The 6th parameter. */
  public val a6: A6
  /** The 6th parameter. */
  public val a6WithLabel: KaseParameterWithLabel<A6>
  /** The 7th parameter. */
  public val a7: A7
  /** The 7th parameter. */
  public val a7WithLabel: KaseParameterWithLabel<A7>
  /** The 8th parameter. */
  public val a8: A8
  /** The 8th parameter. */
  public val a8WithLabel: KaseParameterWithLabel<A8>
  /** The 9th parameter. */
  public val a9: A9
  /** The 9th parameter. */
  public val a9WithLabel: KaseParameterWithLabel<A9>
  /** The 10th parameter. */
  public val a10: A10
  /** The 10th parameter. */
  public val a10WithLabel: KaseParameterWithLabel<A10>
  /** The 11th parameter. */
  public val a11: A11
  /** The 11th parameter. */
  public val a11WithLabel: KaseParameterWithLabel<A11>
  /** The 12th parameter. */
  public val a12: A12
  /** The 12th parameter. */
  public val a12WithLabel: KaseParameterWithLabel<A12>
  /** The 13th parameter. */
  public val a13: A13
  /** The 13th parameter. */
  public val a13WithLabel: KaseParameterWithLabel<A13>
  /** The 14th parameter. */
  public val a14: A14
  /** The 14th parameter. */
  public val a14WithLabel: KaseParameterWithLabel<A14>
  /** The 15th parameter. */
  public val a15: A15
  /** The 15th parameter. */
  public val a15WithLabel: KaseParameterWithLabel<A15>
  /** The 16th parameter. */
  public val a16: A16
  /** The 16th parameter. */
  public val a16WithLabel: KaseParameterWithLabel<A16>
  /** The 17th parameter. */
  public val a17: A17
  /** The 17th parameter. */
  public val a17WithLabel: KaseParameterWithLabel<A17>
  /** The 18th parameter. */
  public val a18: A18
  /** The 18th parameter. */
  public val a18WithLabel: KaseParameterWithLabel<A18>
  /** The 19th parameter. */
  public val a19: A19
  /** The 19th parameter. */
  public val a19WithLabel: KaseParameterWithLabel<A19>
  /** The 20th parameter. */
  public val a20: A20
  /** The 20th parameter. */
  public val a20WithLabel: KaseParameterWithLabel<A20>

  public val labelDelimiter: String get() = DELIMITER_DEFAULT

  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT

  override fun <A21> plus(label: String, value: A21): Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> {
    return DefaultKase21(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = a5WithLabel,
      a6WithLabel = a6WithLabel,
      a7WithLabel = a7WithLabel,
      a8WithLabel = a8WithLabel,
      a9WithLabel = a9WithLabel,
      a10WithLabel = a10WithLabel,
      a11WithLabel = a11WithLabel,
      a12WithLabel = a12WithLabel,
      a13WithLabel = a13WithLabel,
      a14WithLabel = a14WithLabel,
      a15WithLabel = a15WithLabel,
      a16WithLabel = a16WithLabel,
      a17WithLabel = a17WithLabel,
      a18WithLabel = a18WithLabel,
      a19WithLabel = a19WithLabel,
      a20WithLabel = a20WithLabel,
      a21WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

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
 * @param labelDelimiter the delimiter between the label
 *   and the value, like `": "` in `"label: value"`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `"label1: value1 | label2: value2"`
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20,
  labels: KaseLabels20 = KaseLabels20(),
  labelDelimiter: String = DELIMITER_DEFAULT,
  displayNameSeparator: String = SEPARATOR_DEFAULT
): Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  return DefaultKase20(
    a1WithLabel = kaseParam(value = a1, label = labels.a1Label),
    a2WithLabel = kaseParam(value = a2, label = labels.a2Label),
    a3WithLabel = kaseParam(value = a3, label = labels.a3Label),
    a4WithLabel = kaseParam(value = a4, label = labels.a4Label),
    a5WithLabel = kaseParam(value = a5, label = labels.a5Label),
    a6WithLabel = kaseParam(value = a6, label = labels.a6Label),
    a7WithLabel = kaseParam(value = a7, label = labels.a7Label),
    a8WithLabel = kaseParam(value = a8, label = labels.a8Label),
    a9WithLabel = kaseParam(value = a9, label = labels.a9Label),
    a10WithLabel = kaseParam(value = a10, label = labels.a10Label),
    a11WithLabel = kaseParam(value = a11, label = labels.a11Label),
    a12WithLabel = kaseParam(value = a12, label = labels.a12Label),
    a13WithLabel = kaseParam(value = a13, label = labels.a13Label),
    a14WithLabel = kaseParam(value = a14, label = labels.a14Label),
    a15WithLabel = kaseParam(value = a15, label = labels.a15Label),
    a16WithLabel = kaseParam(value = a16, label = labels.a16Label),
    a17WithLabel = kaseParam(value = a17, label = labels.a17Label),
    a18WithLabel = kaseParam(value = a18, label = labels.a18Label),
    a19WithLabel = kaseParam(value = a19, label = labels.a19Label),
    a20WithLabel = kaseParam(value = a20, label = labels.a20Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20,
  labels: KaseLabels20 = KaseLabels20(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  test(
    kase = kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, labels),
    testFunctionCoordinates = testFunctionCoordinates,
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
  where T : TestEnvironment,
        K : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  return testFactory(kases = this@asTests, testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase20DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  vararg kases: K,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment,
        K : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase20DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  kases: Iterable<K>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment,
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
 * @property labelDelimiter The delimiter between the label and the value. The default is `: `.
 * @property displayNameSeparator The separator between each label/value pair. The default is ` | `.
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
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label, a11Label, a12Label, a13Label, a14Label, a15Label, a16Label, a17Label, a18Label, a19Label, a20Label)
  }
}

@Poko
internal class DefaultKase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20>(
  override val a1WithLabel: KaseParameterWithLabel<A1>,
  override val a2WithLabel: KaseParameterWithLabel<A2>,
  override val a3WithLabel: KaseParameterWithLabel<A3>,
  override val a4WithLabel: KaseParameterWithLabel<A4>,
  override val a5WithLabel: KaseParameterWithLabel<A5>,
  override val a6WithLabel: KaseParameterWithLabel<A6>,
  override val a7WithLabel: KaseParameterWithLabel<A7>,
  override val a8WithLabel: KaseParameterWithLabel<A8>,
  override val a9WithLabel: KaseParameterWithLabel<A9>,
  override val a10WithLabel: KaseParameterWithLabel<A10>,
  override val a11WithLabel: KaseParameterWithLabel<A11>,
  override val a12WithLabel: KaseParameterWithLabel<A12>,
  override val a13WithLabel: KaseParameterWithLabel<A13>,
  override val a14WithLabel: KaseParameterWithLabel<A14>,
  override val a15WithLabel: KaseParameterWithLabel<A15>,
  override val a16WithLabel: KaseParameterWithLabel<A16>,
  override val a17WithLabel: KaseParameterWithLabel<A17>,
  override val a18WithLabel: KaseParameterWithLabel<A18>,
  override val a19WithLabel: KaseParameterWithLabel<A19>,
  override val a20WithLabel: KaseParameterWithLabel<A20>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>, KaseInternal {
  override val a1: A1 get() = a1WithLabel.value
  override val a2: A2 get() = a2WithLabel.value
  override val a3: A3 get() = a3WithLabel.value
  override val a4: A4 get() = a4WithLabel.value
  override val a5: A5 get() = a5WithLabel.value
  override val a6: A6 get() = a6WithLabel.value
  override val a7: A7 get() = a7WithLabel.value
  override val a8: A8 get() = a8WithLabel.value
  override val a9: A9 get() = a9WithLabel.value
  override val a10: A10 get() = a10WithLabel.value
  override val a11: A11 get() = a11WithLabel.value
  override val a12: A12 get() = a12WithLabel.value
  override val a13: A13 get() = a13WithLabel.value
  override val a14: A14 get() = a14WithLabel.value
  override val a15: A15 get() = a15WithLabel.value
  override val a16: A16 get() = a16WithLabel.value
  override val a17: A17 get() = a17WithLabel.value
  override val a18: A18 get() = a18WithLabel.value
  override val a19: A19 get() = a19WithLabel.value
  override val a20: A20 get() = a20WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel, a5WithLabel, a6WithLabel, a7WithLabel, a8WithLabel, a9WithLabel, a10WithLabel, a11WithLabel, a12WithLabel, a13WithLabel, a14WithLabel, a15WithLabel, a16WithLabel, a17WithLabel, a18WithLabel, a19WithLabel, a20WithLabel)

  override fun <A21> plus(label: String, value: A21): DefaultKase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> {
    return DefaultKase21(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = a5WithLabel,
      a6WithLabel = a6WithLabel,
      a7WithLabel = a7WithLabel,
      a8WithLabel = a8WithLabel,
      a9WithLabel = a9WithLabel,
      a10WithLabel = a10WithLabel,
      a11WithLabel = a11WithLabel,
      a12WithLabel = a12WithLabel,
      a13WithLabel = a13WithLabel,
      a14WithLabel = a14WithLabel,
      a15WithLabel = a15WithLabel,
      a16WithLabel = a16WithLabel,
      a17WithLabel = a17WithLabel,
      a18WithLabel = a18WithLabel,
      a19WithLabel = a19WithLabel,
      a20WithLabel = a20WithLabel,
      a21WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}