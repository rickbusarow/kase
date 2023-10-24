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

/** A strongly-typed version of [Kase] for 10 parameters. */
public interface Kase10<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10> : Kase {

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

  /** The delimiter between the label and the value, like `": "` in `label: value` */
  public val labelDelimiter: String get() = DELIMITER_DEFAULT

  /**
   * The separator between each label/value pair, like `" | "` in `label1: value1 | label2: value2`
   */
  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT

  /** @see Kase10.a1 */
  public operator fun component1(): A1 = a1

  /** @see Kase10.a2 */
  public operator fun component2(): A2 = a2

  /** @see Kase10.a3 */
  public operator fun component3(): A3 = a3

  /** @see Kase10.a4 */
  public operator fun component4(): A4 = a4

  /** @see Kase10.a5 */
  public operator fun component5(): A5 = a5

  /** @see Kase10.a6 */
  public operator fun component6(): A6 = a6

  /** @see Kase10.a7 */
  public operator fun component7(): A7 = a7

  /** @see Kase10.a8 */
  public operator fun component8(): A8 = a8

  /** @see Kase10.a9 */
  public operator fun component9(): A9 = a9

  /** @see Kase10.a10 */
  public operator fun component10(): A10 = a10

  override fun <A11> plus(label: String, value: A11): Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
    return DefaultKase11(
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
      a11WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

@Poko
@PublishedApi
internal class DefaultKase10<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10>(
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
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>, KaseInternal {
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

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel, a5WithLabel, a6WithLabel, a7WithLabel, a8WithLabel, a9WithLabel, a10WithLabel)

  override fun <A11> plus(label: String, value: A11): DefaultKase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
    return DefaultKase11(
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
      a11WithLabel = kaseParam(label = label, value = value),
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

  override fun toString(): String = displayName
}

/**
 * A strongly-typed version of [KaseLabels] for 10 parameters.
 *
 * @property a1Label The label for the [Kase10.a1] parameter.
 * @property a2Label The label for the [Kase10.a2] parameter.
 * @property a3Label The label for the [Kase10.a3] parameter.
 * @property a4Label The label for the [Kase10.a4] parameter.
 * @property a5Label The label for the [Kase10.a5] parameter.
 * @property a6Label The label for the [Kase10.a6] parameter.
 * @property a7Label The label for the [Kase10.a7] parameter.
 * @property a8Label The label for the [Kase10.a8] parameter.
 * @property a9Label The label for the [Kase10.a9] parameter.
 * @property a10Label The label for the [Kase10.a10] parameter.
 * @property labelDelimiter The delimiter between the label and the value. The default is `": "`.
 * @property displayNameSeparator The separator between
 *   each label/value pair. The default is `" | "`.
 */
@Poko
public class KaseLabels10(
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
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label, a9Label, a10Label)
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase10:a1] parameter.
 * @param a2 the [Kase10:a2] parameter.
 * @param a3 the [Kase10:a3] parameter.
 * @param a4 the [Kase10:a4] parameter.
 * @param a5 the [Kase10:a5] parameter.
 * @param a6 the [Kase10:a6] parameter.
 * @param a7 the [Kase10:a7] parameter.
 * @param a8 the [Kase10:a8] parameter.
 * @param a9 the [Kase10:a9] parameter.
 * @param a10 the [Kase10:a10] parameter.
 * @param labels the [KaseLabels10] to use for this [Kase10]
 * @param labelDelimiter the delimiter between the
 *   label and the value, like `": "` in `label: value`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `label1: value1 | label2: value2`
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10,
  labels: KaseLabels10 = KaseLabels10(),
  labelDelimiter: String = labels.labelDelimiter,
  displayNameSeparator: String = labels.displayNameSeparator
): Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {
  return DefaultKase10(
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
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/**
 * Creates a new [Kase10] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase10:a1] parameter.
 * @param a2 the [Kase10:a2] parameter.
 * @param a3 the [Kase10:a3] parameter.
 * @param a4 the [Kase10:a4] parameter.
 * @param a5 the [Kase10:a5] parameter.
 * @param a6 the [Kase10:a6] parameter.
 * @param a7 the [Kase10:a7] parameter.
 * @param a8 the [Kase10:a8] parameter.
 * @param a9 the [Kase10:a9] parameter.
 * @param a10 the [Kase10:a10] parameter.
 * @param labels the [KaseLabels10] to use for this [Kase10]
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see TestEnvironmentFactory
 */
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> TestEnvironmentFactory<T>.test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10,
  labels: KaseLabels10 = KaseLabels10(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {
  this@TestEnvironmentFactory.test(
    kase = kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, labels = labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Returns a [List] of [Kase10]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase10.a1] parameter.
 * @param args2 values mapped to the [Kase10.a2] parameter.
 * @param args3 values mapped to the [Kase10.a3] parameter.
 * @param args4 values mapped to the [Kase10.a4] parameter.
 * @param args5 values mapped to the [Kase10.a5] parameter.
 * @param args6 values mapped to the [Kase10.a6] parameter.
 * @param args7 values mapped to the [Kase10.a7] parameter.
 * @param args8 values mapped to the [Kase10.a8] parameter.
 * @param args9 values mapped to the [Kase10.a9] parameter.
 * @param args10 values mapped to the [Kase10.a10] parameter.
 * @param labels the [KaseLabels10] to use for this [Kase10]
 * @return a [List] of [Kase10]s from the given parameters.
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> kases(
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
  labels: KaseLabels10 = KaseLabels10()
): List<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> {
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
                        add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, labels = labels))
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
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase10]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase10
 */
@JvmName("asTestsKase10Destructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.asTests(
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10) }
  }
}

/**
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase10]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase10
 */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase10ExtensionDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.asTests(
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase10] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase10]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase10
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase10VarargDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> testFactory(
  vararg kases: Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase10] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase10]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase10
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase10IterableDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> testFactory(
  kases: Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase10] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase10]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase10
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase10VarargDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> testFactory(
  vararg kases: Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10) } }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase10] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase10]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase10
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase10IterableDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> testFactory(
  kases: Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10) } }
}

/**
 * Creates a new [KaseLabels10] with the given labels.
 *
 * @param a1Label the label for the [Kase10.a1] property.
 * @param a2Label the label for the [Kase10.a2] property.
 * @param a3Label the label for the [Kase10.a3] property.
 * @param a4Label the label for the [Kase10.a4] property.
 * @param a5Label the label for the [Kase10.a5] property.
 * @param a6Label the label for the [Kase10.a6] property.
 * @param a7Label the label for the [Kase10.a7] property.
 * @param a8Label the label for the [Kase10.a8] property.
 * @param a9Label the label for the [Kase10.a9] property.
 * @param a10Label the label for the [Kase10.a10] property.
 * @return a new [KaseLabels10] with the given labels.
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
  a10Label: String = "a10"
): KaseLabels10 {
  return KaseLabels10(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label, a9Label = a9Label, a10Label = a10Label)
}

/**
 * @param others the [Kase1] to combine with this [Kase10]
 * @return a list of [Kase11]s from the cartesian product of this [Kase10] and the given [Kase1].
 */
@JvmName("kase10timesKase1")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase10]
 * @return a list of [Kase12]s from the cartesian product of this [Kase10] and the given [Kase2].
 */
@JvmName("kase10timesKase2")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase10]
 * @return a list of [Kase13]s from the cartesian product of this [Kase10] and the given [Kase3].
 */
@JvmName("kase10timesKase3")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase10]
 * @return a list of [Kase14]s from the cartesian product of this [Kase10] and the given [Kase4].
 */
@JvmName("kase10timesKase4")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase10]
 * @return a list of [Kase15]s from the cartesian product of this [Kase10] and the given [Kase5].
 */
@JvmName("kase10timesKase5")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>
): List<Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase10]
 * @return a list of [Kase16]s from the cartesian product of this [Kase10] and the given [Kase6].
 */
@JvmName("kase10timesKase6")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>
): List<Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase10]
 * @return a list of [Kase17]s from the cartesian product of this [Kase10] and the given [Kase7].
 */
@JvmName("kase10timesKase7")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>
): List<Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase10]
 * @return a list of [Kase18]s from the cartesian product of this [Kase10] and the given [Kase8].
 */
@JvmName("kase10timesKase8")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>
): List<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase10]
 * @return a list of [Kase19]s from the cartesian product of this [Kase10] and the given [Kase9].
 */
@JvmName("kase10timesKase9")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>
): List<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase10]
 * @return a list of [Kase20]s from the cartesian product of this [Kase10] and the given [Kase10].
 */
@JvmName("kase10timesKase10")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase10]
 * @return a list of [Kase21]s from the cartesian product of this [Kase10] and the given [Kase11].
 */
@JvmName("kase10timesKase11")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>
): List<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}
