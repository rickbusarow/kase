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
import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParam
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream

/** A strongly-typed version of [Kase] for 18 parameters. */
public interface Kase18<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18> : Kase {

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

  /** The delimiter between the label and the value, like `": "` in `label: value` */
  public val labelDelimiter: String get() = DELIMITER_DEFAULT

  /**
   * The separator between each label/value pair, like `" | "` in `label1: value1 | label2: value2`
   */
  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT

  /** @see Kase18.a1 */
  public operator fun component1(): A1 = a1

  /** @see Kase18.a2 */
  public operator fun component2(): A2 = a2

  /** @see Kase18.a3 */
  public operator fun component3(): A3 = a3

  /** @see Kase18.a4 */
  public operator fun component4(): A4 = a4

  /** @see Kase18.a5 */
  public operator fun component5(): A5 = a5

  /** @see Kase18.a6 */
  public operator fun component6(): A6 = a6

  /** @see Kase18.a7 */
  public operator fun component7(): A7 = a7

  /** @see Kase18.a8 */
  public operator fun component8(): A8 = a8

  /** @see Kase18.a9 */
  public operator fun component9(): A9 = a9

  /** @see Kase18.a10 */
  public operator fun component10(): A10 = a10

  /** @see Kase18.a11 */
  public operator fun component11(): A11 = a11

  /** @see Kase18.a12 */
  public operator fun component12(): A12 = a12

  /** @see Kase18.a13 */
  public operator fun component13(): A13 = a13

  /** @see Kase18.a14 */
  public operator fun component14(): A14 = a14

  /** @see Kase18.a15 */
  public operator fun component15(): A15 = a15

  /** @see Kase18.a16 */
  public operator fun component16(): A16 = a16

  /** @see Kase18.a17 */
  public operator fun component17(): A17 = a17

  /** @see Kase18.a18 */
  public operator fun component18(): A18 = a18

  override fun <A19> plus(label: String, value: A19): Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> {
    return DefaultKase19(
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
      a19WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

@Poko
@PublishedApi
internal class DefaultKase18<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18>(
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
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>, KaseInternal {
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

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel, a5WithLabel, a6WithLabel, a7WithLabel, a8WithLabel, a9WithLabel, a10WithLabel, a11WithLabel, a12WithLabel, a13WithLabel, a14WithLabel, a15WithLabel, a16WithLabel, a17WithLabel, a18WithLabel)

  override fun <A19> plus(label: String, value: A19): DefaultKase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> {
    return DefaultKase19(
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
      a19WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }

  override fun component1(): A1 = a1
  override fun component2(): A2 = a2
  override fun component3(): A3 = a3
  override fun component4(): A4 = a4
  override fun component5(): A5 = a5
  override fun component6(): A6 = a6
  override fun component7(): A7 = a7
  override fun component8(): A8 = a8
  override fun component9(): A9 = a9
  override fun component10(): A10 = a10
  override fun component11(): A11 = a11
  override fun component12(): A12 = a12
  override fun component13(): A13 = a13
  override fun component14(): A14 = a14
  override fun component15(): A15 = a15
  override fun component16(): A16 = a16
  override fun component17(): A17 = a17
  override fun component18(): A18 = a18

  override fun toString(): String = displayName
}

/**
 * A strongly-typed version of [KaseLabels] for 18 parameters.
 *
 * @property a1Label The label for the [Kase18.a1] parameter.
 * @property a2Label The label for the [Kase18.a2] parameter.
 * @property a3Label The label for the [Kase18.a3] parameter.
 * @property a4Label The label for the [Kase18.a4] parameter.
 * @property a5Label The label for the [Kase18.a5] parameter.
 * @property a6Label The label for the [Kase18.a6] parameter.
 * @property a7Label The label for the [Kase18.a7] parameter.
 * @property a8Label The label for the [Kase18.a8] parameter.
 * @property a9Label The label for the [Kase18.a9] parameter.
 * @property a10Label The label for the [Kase18.a10] parameter.
 * @property a11Label The label for the [Kase18.a11] parameter.
 * @property a12Label The label for the [Kase18.a12] parameter.
 * @property a13Label The label for the [Kase18.a13] parameter.
 * @property a14Label The label for the [Kase18.a14] parameter.
 * @property a15Label The label for the [Kase18.a15] parameter.
 * @property a16Label The label for the [Kase18.a16] parameter.
 * @property a17Label The label for the [Kase18.a17] parameter.
 * @property a18Label The label for the [Kase18.a18] parameter.
 * @property labelDelimiter The delimiter between the label and the value. The default is `": "`.
 * @property displayNameSeparator The separator between
 *   each label/value pair. The default is `" | "`.
 */
@Poko
public class KaseLabels18(
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
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label, a11Label, a12Label, a13Label, a14Label, a15Label, a16Label, a17Label, a18Label)
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase18:a1] parameter.
 * @param a2 the [Kase18:a2] parameter.
 * @param a3 the [Kase18:a3] parameter.
 * @param a4 the [Kase18:a4] parameter.
 * @param a5 the [Kase18:a5] parameter.
 * @param a6 the [Kase18:a6] parameter.
 * @param a7 the [Kase18:a7] parameter.
 * @param a8 the [Kase18:a8] parameter.
 * @param a9 the [Kase18:a9] parameter.
 * @param a10 the [Kase18:a10] parameter.
 * @param a11 the [Kase18:a11] parameter.
 * @param a12 the [Kase18:a12] parameter.
 * @param a13 the [Kase18:a13] parameter.
 * @param a14 the [Kase18:a14] parameter.
 * @param a15 the [Kase18:a15] parameter.
 * @param a16 the [Kase18:a16] parameter.
 * @param a17 the [Kase18:a17] parameter.
 * @param a18 the [Kase18:a18] parameter.
 * @param labels the [KaseLabels18] to use for this [Kase18]
 * @param labelDelimiter the delimiter between the
 *   label and the value, like `": "` in `label: value`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `label1: value1 | label2: value2`
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18,
  labels: KaseLabels18 = KaseLabels18(),
  labelDelimiter: String = labels.labelDelimiter,
  displayNameSeparator: String = labels.displayNameSeparator
): Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> {
  return DefaultKase18(
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
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/**
 * Creates a new [Kase18] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase18:a1] parameter.
 * @param a2 the [Kase18:a2] parameter.
 * @param a3 the [Kase18:a3] parameter.
 * @param a4 the [Kase18:a4] parameter.
 * @param a5 the [Kase18:a5] parameter.
 * @param a6 the [Kase18:a6] parameter.
 * @param a7 the [Kase18:a7] parameter.
 * @param a8 the [Kase18:a8] parameter.
 * @param a9 the [Kase18:a9] parameter.
 * @param a10 the [Kase18:a10] parameter.
 * @param a11 the [Kase18:a11] parameter.
 * @param a12 the [Kase18:a12] parameter.
 * @param a13 the [Kase18:a13] parameter.
 * @param a14 the [Kase18:a14] parameter.
 * @param a15 the [Kase18:a15] parameter.
 * @param a16 the [Kase18:a16] parameter.
 * @param a17 the [Kase18:a17] parameter.
 * @param a18 the [Kase18:a18] parameter.
 * @param labels the [KaseLabels18] to use for this [Kase18]
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see TestEnvironmentFactory
 */
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> TestEnvironmentFactory<T>.test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18,
  labels: KaseLabels18 = KaseLabels18(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> {
  this@TestEnvironmentFactory.test(
    kase = kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, labels = labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Returns a [List] of [Kase18]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase18.a1] parameter.
 * @param args2 values mapped to the [Kase18.a2] parameter.
 * @param args3 values mapped to the [Kase18.a3] parameter.
 * @param args4 values mapped to the [Kase18.a4] parameter.
 * @param args5 values mapped to the [Kase18.a5] parameter.
 * @param args6 values mapped to the [Kase18.a6] parameter.
 * @param args7 values mapped to the [Kase18.a7] parameter.
 * @param args8 values mapped to the [Kase18.a8] parameter.
 * @param args9 values mapped to the [Kase18.a9] parameter.
 * @param args10 values mapped to the [Kase18.a10] parameter.
 * @param args11 values mapped to the [Kase18.a11] parameter.
 * @param args12 values mapped to the [Kase18.a12] parameter.
 * @param args13 values mapped to the [Kase18.a13] parameter.
 * @param args14 values mapped to the [Kase18.a14] parameter.
 * @param args15 values mapped to the [Kase18.a15] parameter.
 * @param args16 values mapped to the [Kase18.a16] parameter.
 * @param args17 values mapped to the [Kase18.a17] parameter.
 * @param args18 values mapped to the [Kase18.a18] parameter.
 * @param labels the [KaseLabels18] to use for this [Kase18]
 * @return a [List] of [Kase18]s from the given parameters.
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kases(
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
  labels: KaseLabels18 = KaseLabels18()
): List<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> {
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
                                        add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, labels = labels))
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
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase18]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase18
 */
@JvmName("asTestsKase18Destructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.asTests(
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) }
  }
}

/**
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase18]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase18
 */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase18ExtensionDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.asTests(
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase18] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase18]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase18
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase18VarargDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  vararg kases: Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase18] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase18]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase18
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase18IterableDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  kases: Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase18] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase18]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase18
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase18VarargDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  vararg kases: Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) } }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase18] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase18]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase18
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase18IterableDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  kases: Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) } }
}

/**
 * Creates a new [KaseLabels18] with the given labels.
 *
 * @param a1Label the label for the [Kase18.a1] property.
 * @param a2Label the label for the [Kase18.a2] property.
 * @param a3Label the label for the [Kase18.a3] property.
 * @param a4Label the label for the [Kase18.a4] property.
 * @param a5Label the label for the [Kase18.a5] property.
 * @param a6Label the label for the [Kase18.a6] property.
 * @param a7Label the label for the [Kase18.a7] property.
 * @param a8Label the label for the [Kase18.a8] property.
 * @param a9Label the label for the [Kase18.a9] property.
 * @param a10Label the label for the [Kase18.a10] property.
 * @param a11Label the label for the [Kase18.a11] property.
 * @param a12Label the label for the [Kase18.a12] property.
 * @param a13Label the label for the [Kase18.a13] property.
 * @param a14Label the label for the [Kase18.a14] property.
 * @param a15Label the label for the [Kase18.a15] property.
 * @param a16Label the label for the [Kase18.a16] property.
 * @param a17Label the label for the [Kase18.a17] property.
 * @param a18Label the label for the [Kase18.a18] property.
 * @return a new [KaseLabels18] with the given labels.
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
  a18Label: String = "a18"
): KaseLabels18 {
  return KaseLabels18(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label, a9Label = a9Label, a10Label = a10Label, a11Label = a11Label, a12Label = a12Label, a13Label = a13Label, a14Label = a14Label, a15Label = a15Label, a16Label = a16Label, a17Label = a17Label, a18Label = a18Label)
}

/**
 * @param others the [Kase1] to combine with this [Kase18]
 * @return a list of [Kase19]s from the cartesian product of this [Kase18] and the given [Kase1].
 */
@JvmName("kase18timesKase1")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase18]
 * @return a list of [Kase20]s from the cartesian product of this [Kase18] and the given [Kase2].
 */
@JvmName("kase18timesKase2")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1, b2) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1, b2)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase18]
 * @return a list of [Kase21]s from the cartesian product of this [Kase18] and the given [Kase3].
 */
@JvmName("kase18timesKase3")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2, B3> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2, B3>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1, b2, b3)
  }
}