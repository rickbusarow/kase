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
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream

/** A strongly-typed version of [Kase] for 9 parameters. */
public interface Kase9<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9> : Kase {

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

  /** The delimiter between the label and the value, like `": "` in `label: value` */
  public val labelDelimiter: String get() = DELIMITER_DEFAULT

  /**
   * The separator between each label/value pair, like `" | "` in `label1: value1 | label2: value2`
   */
  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT

  /** @see Kase9.a1 */
  public operator fun component1(): A1 = a1
  /** @see Kase9.a2 */
  public operator fun component2(): A2 = a2
  /** @see Kase9.a3 */
  public operator fun component3(): A3 = a3
  /** @see Kase9.a4 */
  public operator fun component4(): A4 = a4
  /** @see Kase9.a5 */
  public operator fun component5(): A5 = a5
  /** @see Kase9.a6 */
  public operator fun component6(): A6 = a6
  /** @see Kase9.a7 */
  public operator fun component7(): A7 = a7
  /** @see Kase9.a8 */
  public operator fun component8(): A8 = a8
  /** @see Kase9.a9 */
  public operator fun component9(): A9 = a9

  override fun <A10> plus(label: String, value: A10): Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {
    return DefaultKase10(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = a5WithLabel,
      a6WithLabel = a6WithLabel,
      a7WithLabel = a7WithLabel,
      a8WithLabel = a8WithLabel,
      a9WithLabel = a9WithLabel,
      a10WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase9:a1] parameter.
 * @param a2 the [Kase9:a2] parameter.
 * @param a3 the [Kase9:a3] parameter.
 * @param a4 the [Kase9:a4] parameter.
 * @param a5 the [Kase9:a5] parameter.
 * @param a6 the [Kase9:a6] parameter.
 * @param a7 the [Kase9:a7] parameter.
 * @param a8 the [Kase9:a8] parameter.
 * @param a9 the [Kase9:a9] parameter.
 * @param labels the [KaseLabels9] to use for this [Kase9]
 * @param labelDelimiter the delimiter between the
 *   label and the value, like `": "` in `label: value`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `label1: value1 | label2: value2`
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9,
  labels: KaseLabels9 = KaseLabels9(),
  labelDelimiter: String = labels.labelDelimiter,
  displayNameSeparator: String = labels.displayNameSeparator
): Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9> {
  return DefaultKase9(
    a1WithLabel = kaseParam(value = a1, label = (a1 as? HasLabel)?.label ?: labels.a1Label),
    a2WithLabel = kaseParam(value = a2, label = (a2 as? HasLabel)?.label ?: labels.a2Label),
    a3WithLabel = kaseParam(value = a3, label = (a3 as? HasLabel)?.label ?: labels.a3Label),
    a4WithLabel = kaseParam(value = a4, label = (a4 as? HasLabel)?.label ?: labels.a4Label),
    a5WithLabel = kaseParam(value = a5, label = (a5 as? HasLabel)?.label ?: labels.a5Label),
    a6WithLabel = kaseParam(value = a6, label = (a6 as? HasLabel)?.label ?: labels.a6Label),
    a7WithLabel = kaseParam(value = a7, label = (a7 as? HasLabel)?.label ?: labels.a7Label),
    a8WithLabel = kaseParam(value = a8, label = (a8 as? HasLabel)?.label ?: labels.a8Label),
    a9WithLabel = kaseParam(value = a9, label = (a9 as? HasLabel)?.label ?: labels.a9Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/**
 * Creates a new [Kase9] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase9:a1] parameter.
 * @param a2 the [Kase9:a2] parameter.
 * @param a3 the [Kase9:a3] parameter.
 * @param a4 the [Kase9:a4] parameter.
 * @param a5 the [Kase9:a5] parameter.
 * @param a6 the [Kase9:a6] parameter.
 * @param a7 the [Kase9:a7] parameter.
 * @param a8 the [Kase9:a8] parameter.
 * @param a9 the [Kase9:a9] parameter.
 * @param labels the [KaseLabels9] to use for this [Kase9]
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see TestEnvironmentFactory
 */
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9> TestEnvironmentFactory<T>.test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9,
  labels: KaseLabels9 = KaseLabels9(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9> {
  this@TestEnvironmentFactory.test(
    kase = kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Creates a new [KaseLabels9] with the given labels.
 *
 * @param a1Label the label for the [Kase9.a1] property.
 * @param a2Label the label for the [Kase9.a2] property.
 * @param a3Label the label for the [Kase9.a3] property.
 * @param a4Label the label for the [Kase9.a4] property.
 * @param a5Label the label for the [Kase9.a5] property.
 * @param a6Label the label for the [Kase9.a6] property.
 * @param a7Label the label for the [Kase9.a7] property.
 * @param a8Label the label for the [Kase9.a8] property.
 * @param a9Label the label for the [Kase9.a9] property.
 * @return a new [KaseLabels9] with the given labels.
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
  a9Label: String = "a9"
): KaseLabels9 {
  return KaseLabels9(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label, a9Label = a9Label)
}

/**
 * Returns a [List] of [Kase9]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase9.a1] parameter.
 * @param args2 values mapped to the [Kase9.a2] parameter.
 * @param args3 values mapped to the [Kase9.a3] parameter.
 * @param args4 values mapped to the [Kase9.a4] parameter.
 * @param args5 values mapped to the [Kase9.a5] parameter.
 * @param args6 values mapped to the [Kase9.a6] parameter.
 * @param args7 values mapped to the [Kase9.a7] parameter.
 * @param args8 values mapped to the [Kase9.a8] parameter.
 * @param args9 values mapped to the [Kase9.a9] parameter.
 * @param labels the [KaseLabels9] to use for this [Kase9]
 * @return a [List] of [Kase9]s from the given parameters.
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>,
  args9: Iterable<A9>,
  labels: KaseLabels9 = KaseLabels9()
): List<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>> {
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
                      add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, labels = labels))
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
@JvmName("asTestsKase9DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9> Iterable<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>>.asTests(
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9) -> Unit
): Stream<out DynamicNode> {
  return testFactory(kases = this@asTests, testAction = testAction)
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase9] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase9]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase9
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase9DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9> testFactory(
  vararg kases: Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9) -> Unit
): Stream<out DynamicNode> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase9] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase9]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase9
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase9DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9> testFactory(
  kases: Iterable<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testAction = { kase: Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9> -> testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5, kase.a6, kase.a7, kase.a8, kase.a9) }
  )
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase9] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase9]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase9
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase9")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> testFactory(
  vararg kases: Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9) }
}

/**
 * A strongly-typed version of [KaseLabels] for 9 parameters.
 *
 * @property a1Label The label for the [Kase9.a1] parameter.
 * @property a2Label The label for the [Kase9.a2] parameter.
 * @property a3Label The label for the [Kase9.a3] parameter.
 * @property a4Label The label for the [Kase9.a4] parameter.
 * @property a5Label The label for the [Kase9.a5] parameter.
 * @property a6Label The label for the [Kase9.a6] parameter.
 * @property a7Label The label for the [Kase9.a7] parameter.
 * @property a8Label The label for the [Kase9.a8] parameter.
 * @property a9Label The label for the [Kase9.a9] parameter.
 * @property labelDelimiter The delimiter between the label and the value. The default is `": "`.
 * @property displayNameSeparator The separator between
 *   each label/value pair. The default is `" | "`.
 */
@Poko
public class KaseLabels9(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  public val a4Label: String = "a4",
  public val a5Label: String = "a5",
  public val a6Label: String = "a6",
  public val a7Label: String = "a7",
  public val a8Label: String = "a8",
  public val a9Label: String = "a9",
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label)
  }
}

@Poko
@PublishedApi
internal class DefaultKase9<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9>(
  override val a1WithLabel: KaseParameterWithLabel<A1>,
  override val a2WithLabel: KaseParameterWithLabel<A2>,
  override val a3WithLabel: KaseParameterWithLabel<A3>,
  override val a4WithLabel: KaseParameterWithLabel<A4>,
  override val a5WithLabel: KaseParameterWithLabel<A5>,
  override val a6WithLabel: KaseParameterWithLabel<A6>,
  override val a7WithLabel: KaseParameterWithLabel<A7>,
  override val a8WithLabel: KaseParameterWithLabel<A8>,
  override val a9WithLabel: KaseParameterWithLabel<A9>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>, KaseInternal {
  override val a1: A1 get() = a1WithLabel.value
  override val a2: A2 get() = a2WithLabel.value
  override val a3: A3 get() = a3WithLabel.value
  override val a4: A4 get() = a4WithLabel.value
  override val a5: A5 get() = a5WithLabel.value
  override val a6: A6 get() = a6WithLabel.value
  override val a7: A7 get() = a7WithLabel.value
  override val a8: A8 get() = a8WithLabel.value
  override val a9: A9 get() = a9WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel, a5WithLabel, a6WithLabel, a7WithLabel, a8WithLabel, a9WithLabel)

  override fun <A10> plus(label: String, value: A10): DefaultKase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {
    return DefaultKase10(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = a5WithLabel,
      a6WithLabel = a6WithLabel,
      a7WithLabel = a7WithLabel,
      a8WithLabel = a8WithLabel,
      a9WithLabel = a9WithLabel,
      a10WithLabel = kaseParam(label = label, value = value),
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

  override fun toString(): String = displayName
}
