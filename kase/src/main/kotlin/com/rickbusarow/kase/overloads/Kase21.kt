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

@file:Suppress(
  "DestructuringDeclarationWithTooManyEntries",
  "DuplicatedCode",
  "MaxLineLength",
  "PackageDirectoryMismatch"
)
@file:JvmMultifileClass
@file:JvmName("KasesKt")

package com.rickbusarow.kase

import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.internal.KaseInternal
import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParam
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream

/** A strongly-typed version of [Kase] for 21 parameters. */
public interface Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {

  /** The 21st parameter. */
  public val a21: A21

  /** The 21st parameter with its label. */
  public val a21WithLabel: KaseParameterWithLabel<A21>

  /** @see Kase21.a21 */
  public operator fun component21(): A21 = a21
}

@Poko
@PublishedApi
internal class DefaultKase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>(
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
  override val a21WithLabel: KaseParameterWithLabel<A21>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>, KaseInternal {
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
  override val a21: A21 get() = a21WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel, a5WithLabel, a6WithLabel, a7WithLabel, a8WithLabel, a9WithLabel, a10WithLabel, a11WithLabel, a12WithLabel, a13WithLabel, a14WithLabel, a15WithLabel, a16WithLabel, a17WithLabel, a18WithLabel, a19WithLabel, a20WithLabel, a21WithLabel)

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
  override operator fun component4(): A4 = a4
  override operator fun component5(): A5 = a5
  override operator fun component6(): A6 = a6
  override operator fun component7(): A7 = a7
  override operator fun component8(): A8 = a8
  override operator fun component9(): A9 = a9
  override operator fun component10(): A10 = a10
  override operator fun component11(): A11 = a11
  override operator fun component12(): A12 = a12
  override operator fun component13(): A13 = a13
  override operator fun component14(): A14 = a14
  override operator fun component15(): A15 = a15
  override operator fun component16(): A16 = a16
  override operator fun component17(): A17 = a17
  override operator fun component18(): A18 = a18
  override operator fun component19(): A19 = a19
  override operator fun component20(): A20 = a20
  override operator fun component21(): A21 = a21

  override fun toString(): String = displayName
}

/**
 * A strongly-typed version of [KaseLabels] for 21 parameters.
 *
 * @property a1Label The label for the [Kase21.a1] parameter.
 * @property a2Label The label for the [Kase21.a2] parameter.
 * @property a3Label The label for the [Kase21.a3] parameter.
 * @property a4Label The label for the [Kase21.a4] parameter.
 * @property a5Label The label for the [Kase21.a5] parameter.
 * @property a6Label The label for the [Kase21.a6] parameter.
 * @property a7Label The label for the [Kase21.a7] parameter.
 * @property a8Label The label for the [Kase21.a8] parameter.
 * @property a9Label The label for the [Kase21.a9] parameter.
 * @property a10Label The label for the [Kase21.a10] parameter.
 * @property a11Label The label for the [Kase21.a11] parameter.
 * @property a12Label The label for the [Kase21.a12] parameter.
 * @property a13Label The label for the [Kase21.a13] parameter.
 * @property a14Label The label for the [Kase21.a14] parameter.
 * @property a15Label The label for the [Kase21.a15] parameter.
 * @property a16Label The label for the [Kase21.a16] parameter.
 * @property a17Label The label for the [Kase21.a17] parameter.
 * @property a18Label The label for the [Kase21.a18] parameter.
 * @property a19Label The label for the [Kase21.a19] parameter.
 * @property a20Label The label for the [Kase21.a20] parameter.
 * @property a21Label The label for the [Kase21.a21] parameter.
 * @property labelDelimiter The delimiter between the label and the value. The default is `": "`.
 * @property displayNameSeparator The separator between
 *   each label/value pair. The default is `" | "`.
 */
@Poko
public class KaseLabels21(
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
  public val a21Label: String = "a21",
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label, a11Label, a12Label, a13Label, a14Label, a15Label, a16Label, a17Label, a18Label, a19Label, a20Label, a21Label)
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase21.a1] parameter.
 * @param a2 the [Kase21.a2] parameter.
 * @param a3 the [Kase21.a3] parameter.
 * @param a4 the [Kase21.a4] parameter.
 * @param a5 the [Kase21.a5] parameter.
 * @param a6 the [Kase21.a6] parameter.
 * @param a7 the [Kase21.a7] parameter.
 * @param a8 the [Kase21.a8] parameter.
 * @param a9 the [Kase21.a9] parameter.
 * @param a10 the [Kase21.a10] parameter.
 * @param a11 the [Kase21.a11] parameter.
 * @param a12 the [Kase21.a12] parameter.
 * @param a13 the [Kase21.a13] parameter.
 * @param a14 the [Kase21.a14] parameter.
 * @param a15 the [Kase21.a15] parameter.
 * @param a16 the [Kase21.a16] parameter.
 * @param a17 the [Kase21.a17] parameter.
 * @param a18 the [Kase21.a18] parameter.
 * @param a19 the [Kase21.a19] parameter.
 * @param a20 the [Kase21.a20] parameter.
 * @param a21 the [Kase21.a21] parameter.
 * @param labels the [KaseLabels21] to use for this [Kase21]
 * @param labelDelimiter the delimiter between the
 *   label and the value, like `": "` in `label: value`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `label1: value1 | label2: value2`
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21,
  labels: KaseLabels21 = KaseLabels21(),
  labelDelimiter: String = labels.labelDelimiter,
  displayNameSeparator: String = labels.displayNameSeparator
): Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> {
  return DefaultKase21(
    a1WithLabel = kaseParam(value = a1, label = (a1 as? HasLabel)?.label ?: labels.a1Label),
    a2WithLabel = kaseParam(value = a2, label = (a2 as? HasLabel)?.label ?: labels.a2Label),
    a3WithLabel = kaseParam(value = a3, label = (a3 as? HasLabel)?.label ?: labels.a3Label),
    a4WithLabel = kaseParam(value = a4, label = (a4 as? HasLabel)?.label ?: labels.a4Label),
    a5WithLabel = kaseParam(value = a5, label = (a5 as? HasLabel)?.label ?: labels.a5Label),
    a6WithLabel = kaseParam(value = a6, label = (a6 as? HasLabel)?.label ?: labels.a6Label),
    a7WithLabel = kaseParam(value = a7, label = (a7 as? HasLabel)?.label ?: labels.a7Label),
    a8WithLabel = kaseParam(value = a8, label = (a8 as? HasLabel)?.label ?: labels.a8Label),
    a9WithLabel = kaseParam(value = a9, label = (a9 as? HasLabel)?.label ?: labels.a9Label),
    a10WithLabel = kaseParam(value = a10, label = (a10 as? HasLabel)?.label ?: labels.a10Label),
    a11WithLabel = kaseParam(value = a11, label = (a11 as? HasLabel)?.label ?: labels.a11Label),
    a12WithLabel = kaseParam(value = a12, label = (a12 as? HasLabel)?.label ?: labels.a12Label),
    a13WithLabel = kaseParam(value = a13, label = (a13 as? HasLabel)?.label ?: labels.a13Label),
    a14WithLabel = kaseParam(value = a14, label = (a14 as? HasLabel)?.label ?: labels.a14Label),
    a15WithLabel = kaseParam(value = a15, label = (a15 as? HasLabel)?.label ?: labels.a15Label),
    a16WithLabel = kaseParam(value = a16, label = (a16 as? HasLabel)?.label ?: labels.a16Label),
    a17WithLabel = kaseParam(value = a17, label = (a17 as? HasLabel)?.label ?: labels.a17Label),
    a18WithLabel = kaseParam(value = a18, label = (a18 as? HasLabel)?.label ?: labels.a18Label),
    a19WithLabel = kaseParam(value = a19, label = (a19 as? HasLabel)?.label ?: labels.a19Label),
    a20WithLabel = kaseParam(value = a20, label = (a20 as? HasLabel)?.label ?: labels.a20Label),
    a21WithLabel = kaseParam(value = a21, label = (a21 as? HasLabel)?.label ?: labels.a21Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/**
 * Creates a new [Kase21] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase21.a1] parameter.
 * @param a2 the [Kase21.a2] parameter.
 * @param a3 the [Kase21.a3] parameter.
 * @param a4 the [Kase21.a4] parameter.
 * @param a5 the [Kase21.a5] parameter.
 * @param a6 the [Kase21.a6] parameter.
 * @param a7 the [Kase21.a7] parameter.
 * @param a8 the [Kase21.a8] parameter.
 * @param a9 the [Kase21.a9] parameter.
 * @param a10 the [Kase21.a10] parameter.
 * @param a11 the [Kase21.a11] parameter.
 * @param a12 the [Kase21.a12] parameter.
 * @param a13 the [Kase21.a13] parameter.
 * @param a14 the [Kase21.a14] parameter.
 * @param a15 the [Kase21.a15] parameter.
 * @param a16 the [Kase21.a16] parameter.
 * @param a17 the [Kase21.a17] parameter.
 * @param a18 the [Kase21.a18] parameter.
 * @param a19 the [Kase21.a19] parameter.
 * @param a20 the [Kase21.a20] parameter.
 * @param a21 the [Kase21.a21] parameter.
 * @param labels the [KaseLabels21] to use for this [Kase21]
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see KaseTestFactory
 */
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> KaseTestFactory<T, Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>>.test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21,
  labels: KaseLabels21 = KaseLabels21(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> {
  this@KaseTestFactory.test(
    kase = kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, labels = labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Returns a [List] of [Kase21]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase21.a1] parameter.
 * @param args2 values mapped to the [Kase21.a2] parameter.
 * @param args3 values mapped to the [Kase21.a3] parameter.
 * @param args4 values mapped to the [Kase21.a4] parameter.
 * @param args5 values mapped to the [Kase21.a5] parameter.
 * @param args6 values mapped to the [Kase21.a6] parameter.
 * @param args7 values mapped to the [Kase21.a7] parameter.
 * @param args8 values mapped to the [Kase21.a8] parameter.
 * @param args9 values mapped to the [Kase21.a9] parameter.
 * @param args10 values mapped to the [Kase21.a10] parameter.
 * @param args11 values mapped to the [Kase21.a11] parameter.
 * @param args12 values mapped to the [Kase21.a12] parameter.
 * @param args13 values mapped to the [Kase21.a13] parameter.
 * @param args14 values mapped to the [Kase21.a14] parameter.
 * @param args15 values mapped to the [Kase21.a15] parameter.
 * @param args16 values mapped to the [Kase21.a16] parameter.
 * @param args17 values mapped to the [Kase21.a17] parameter.
 * @param args18 values mapped to the [Kase21.a18] parameter.
 * @param args19 values mapped to the [Kase21.a19] parameter.
 * @param args20 values mapped to the [Kase21.a20] parameter.
 * @param args21 values mapped to the [Kase21.a21] parameter.
 * @param labels the [KaseLabels21] to use for this [Kase21]
 * @return a [List] of [Kase21]s from the given parameters.
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> kases(
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
  args21: Iterable<A21>,
  labels: KaseLabels21 = KaseLabels21()
): List<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>> {
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
                                            for (a21 in args21) {
                                              add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, labels = labels))
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
}

/**
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase21]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase21
 */
@JvmName("asTestsKase21Destructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> Iterable<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>>.asTests(
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18, it.a19, it.a20, it.a21) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase21] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase21]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase21
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase21VarargDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> testFactory(
  vararg kases: Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18, it.a19, it.a20, it.a21) } }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase21] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase21]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase21
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase21IterableDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> testFactory(
  kases: Iterable<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18, it.a19, it.a20, it.a21) } }
}

/**
 * Creates a new [KaseLabels21] with the given labels.
 *
 * @param a1Label the label for the [Kase21.a1] property.
 * @param a2Label the label for the [Kase21.a2] property.
 * @param a3Label the label for the [Kase21.a3] property.
 * @param a4Label the label for the [Kase21.a4] property.
 * @param a5Label the label for the [Kase21.a5] property.
 * @param a6Label the label for the [Kase21.a6] property.
 * @param a7Label the label for the [Kase21.a7] property.
 * @param a8Label the label for the [Kase21.a8] property.
 * @param a9Label the label for the [Kase21.a9] property.
 * @param a10Label the label for the [Kase21.a10] property.
 * @param a11Label the label for the [Kase21.a11] property.
 * @param a12Label the label for the [Kase21.a12] property.
 * @param a13Label the label for the [Kase21.a13] property.
 * @param a14Label the label for the [Kase21.a14] property.
 * @param a15Label the label for the [Kase21.a15] property.
 * @param a16Label the label for the [Kase21.a16] property.
 * @param a17Label the label for the [Kase21.a17] property.
 * @param a18Label the label for the [Kase21.a18] property.
 * @param a19Label the label for the [Kase21.a19] property.
 * @param a20Label the label for the [Kase21.a20] property.
 * @param a21Label the label for the [Kase21.a21] property.
 * @return a new [KaseLabels21] with the given labels.
 */
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
  a20Label: String = "a20",
  a21Label: String = "a21"
): KaseLabels21 {
  return KaseLabels21(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label, a9Label = a9Label, a10Label = a10Label, a11Label = a11Label, a12Label = a12Label, a13Label = a13Label, a14Label = a14Label, a15Label = a15Label, a16Label = a16Label, a17Label = a17Label, a18Label = a18Label, a19Label = a19Label, a20Label = a20Label, a21Label = a21Label)
}

/**
 * @param others the [Kase1] to combine with this [Kase21]
 * @return a list of [Kase22]s from the cartesian product of this [Kase21] and the given [Kase1].
 */
@JvmName("kase21timesKase1")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, B1> Iterable<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, B1>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, b1)
  }
}
