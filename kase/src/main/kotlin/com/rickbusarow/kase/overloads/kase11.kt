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

/** A strongly-typed version of [Kase] for 11 parameters. */
public interface Kase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11> : Kase {

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

  public val labelDelimiter: String get() = DELIMITER_DEFAULT

  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT

  override fun <A12> plus(label: String, value: A12): Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> {
    return DefaultKase12(
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
      a12WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase11:a1] parameter.
 * @param a2 the [Kase11:a2] parameter.
 * @param a3 the [Kase11:a3] parameter.
 * @param a4 the [Kase11:a4] parameter.
 * @param a5 the [Kase11:a5] parameter.
 * @param a6 the [Kase11:a6] parameter.
 * @param a7 the [Kase11:a7] parameter.
 * @param a8 the [Kase11:a8] parameter.
 * @param a9 the [Kase11:a9] parameter.
 * @param a10 the [Kase11:a10] parameter.
 * @param a11 the [Kase11:a11] parameter.
 * @param labels the [KaseLabels] to use for this [Kase]
 * @param labelDelimiter the delimiter between the label
 *   and the value, like `": "` in `"label: value"`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `"label1: value1 | label2: value2"`
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11,
  labels: KaseLabels11 = KaseLabels11(),
  labelDelimiter: String = DELIMITER_DEFAULT,
  displayNameSeparator: String = SEPARATOR_DEFAULT
): Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
  return DefaultKase11(
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
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11,
  labels: KaseLabels11 = KaseLabels11(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
  test(
    kase = kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, labels),
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
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase11DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Iterable<K>.asTests(
  labels: KaseLabels11 = KaseLabels11(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment,
        K : Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
  return testFactory(kases = this@asTests, testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase11DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  vararg kases: K,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment,
        K : Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase11DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  kases: Iterable<K>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment,
        K : Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {

  return kases.asTests(
    testName = { kase -> kase.displayName() },
    testAction = { kase -> testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5, kase.a6, kase.a7, kase.a8, kase.a9, kase.a10, kase.a11) }
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
 * @property labelDelimiter The delimiter between the label and the value. The default is `: `.
 * @property displayNameSeparator The separator between each label/value pair. The default is ` | `.
 */
@Poko
public class KaseLabels11(
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
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label, a11Label)
  }
}

@Poko
internal class DefaultKase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11>(
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
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>, KaseInternal {
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

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel, a5WithLabel, a6WithLabel, a7WithLabel, a8WithLabel, a9WithLabel, a10WithLabel, a11WithLabel)

  override fun <A12> plus(label: String, value: A12): DefaultKase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> {
    return DefaultKase12(
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
      a12WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}