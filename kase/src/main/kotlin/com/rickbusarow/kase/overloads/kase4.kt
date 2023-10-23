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

/** A strongly-typed version of [Kase] for 4 parameters. */
public interface Kase4<out A1, out A2, out A3, out A4> : Kase {

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

  /** The delimiter between the label and the value, like `": "` in `label: value` */
  public val labelDelimiter: String get() = DELIMITER_DEFAULT

  /**
   * The separator between each label/value pair, like `" | "` in `label1: value1 | label2: value2`
   */
  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT

  /** @see Kase4.a1 */
  public operator fun component1(): A1 = a1
  /** @see Kase4.a2 */
  public operator fun component2(): A2 = a2
  /** @see Kase4.a3 */
  public operator fun component3(): A3 = a3
  /** @see Kase4.a4 */
  public operator fun component4(): A4 = a4

  override fun <A5> plus(label: String, value: A5): Kase5<A1, A2, A3, A4, A5> {
    return DefaultKase5(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase4:a1] parameter.
 * @param a2 the [Kase4:a2] parameter.
 * @param a3 the [Kase4:a3] parameter.
 * @param a4 the [Kase4:a4] parameter.
 * @param labels the [KaseLabels4] to use for this [Kase4]
 * @param labelDelimiter the delimiter between the
 *   label and the value, like `": "` in `label: value`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `label1: value1 | label2: value2`
 */
public fun <A1, A2, A3, A4> kase(
  a1: A1, a2: A2, a3: A3, a4: A4,
  labels: KaseLabels4 = KaseLabels4(),
  labelDelimiter: String = labels.labelDelimiter,
  displayNameSeparator: String = labels.displayNameSeparator
): Kase4<A1, A2, A3, A4> {
  return DefaultKase4(
    a1WithLabel = kaseParam(value = a1, label = (a1 as? HasLabel)?.label ?: labels.a1Label),
    a2WithLabel = kaseParam(value = a2, label = (a2 as? HasLabel)?.label ?: labels.a2Label),
    a3WithLabel = kaseParam(value = a3, label = (a3 as? HasLabel)?.label ?: labels.a3Label),
    a4WithLabel = kaseParam(value = a4, label = (a4 as? HasLabel)?.label ?: labels.a4Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/**
 * Creates a new [Kase4] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase4:a1] parameter.
 * @param a2 the [Kase4:a2] parameter.
 * @param a3 the [Kase4:a3] parameter.
 * @param a4 the [Kase4:a4] parameter.
 * @param labels the [KaseLabels4] to use for this [Kase4]
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see TestEnvironmentFactory
 */
public fun <T, K, A1, A2, A3, A4> TestEnvironmentFactory<T>.test(
  a1: A1, a2: A2, a3: A3, a4: A4,
  labels: KaseLabels4 = KaseLabels4(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase4<A1, A2, A3, A4> {
  this@TestEnvironmentFactory.test(
    kase = kase(a1, a2, a3, a4, labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Creates a new [KaseLabels4] with the given labels.
 *
 * @param a1Label the label for the [Kase4.a1] property.
 * @param a2Label the label for the [Kase4.a2] property.
 * @param a3Label the label for the [Kase4.a3] property.
 * @param a4Label the label for the [Kase4.a4] property.
 * @return a new [KaseLabels4] with the given labels.
 */
public fun labels(
  a1Label: String = "a1",
  a2Label: String = "a2",
  a3Label: String = "a3",
  a4Label: String = "a4"
): KaseLabels4 {
  return KaseLabels4(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label)
}

/**
 * Returns a [List] of [Kase4]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase4.a1] parameter.
 * @param args2 values mapped to the [Kase4.a2] parameter.
 * @param args3 values mapped to the [Kase4.a3] parameter.
 * @param args4 values mapped to the [Kase4.a4] parameter.
 * @param labels the [KaseLabels4] to use for this [Kase4]
 * @return a [List] of [Kase4]s from the given parameters.
 */
public fun <A1, A2, A3, A4> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  labels: KaseLabels4 = KaseLabels4()
): List<Kase4<A1, A2, A3, A4>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, labels = labels))
          }
        }
      }
    }
  }
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase4DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4> Iterable<Kase4<A1, A2, A3, A4>>.asTests(
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return testFactory(kases = this@asTests, testAction = testAction)
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase4] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase4]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase4
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase4DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4> testFactory(
  vararg kases: Kase4<A1, A2, A3, A4>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase4] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase4]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase4
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase4DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1, A2, A3, A4> testFactory(
  kases: Iterable<Kase4<A1, A2, A3, A4>>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testAction = { kase: Kase4<A1, A2, A3, A4> -> testAction(kase.a1, kase.a2, kase.a3, kase.a4) }
  )
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase4] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase4]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase4
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase4")
public inline fun <A1, A2, A3, A4> testFactory(
  vararg kases: Kase4<A1, A2, A3, A4>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4) }
}

/**
 * A strongly-typed version of [KaseLabels] for 4 parameters.
 *
 * @property a1Label The label for the [Kase4.a1] parameter.
 * @property a2Label The label for the [Kase4.a2] parameter.
 * @property a3Label The label for the [Kase4.a3] parameter.
 * @property a4Label The label for the [Kase4.a4] parameter.
 * @property labelDelimiter The delimiter between the label and the value. The default is `": "`.
 * @property displayNameSeparator The separator between
 *   each label/value pair. The default is `" | "`.
 */
@Poko
public class KaseLabels4(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  public val a4Label: String = "a4",
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label)
  }
}

@Poko
@PublishedApi
internal class DefaultKase4<out A1, out A2, out A3, out A4>(
  override val a1WithLabel: KaseParameterWithLabel<A1>,
  override val a2WithLabel: KaseParameterWithLabel<A2>,
  override val a3WithLabel: KaseParameterWithLabel<A3>,
  override val a4WithLabel: KaseParameterWithLabel<A4>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase4<A1, A2, A3, A4>, KaseInternal {
  override val a1: A1 get() = a1WithLabel.value
  override val a2: A2 get() = a2WithLabel.value
  override val a3: A3 get() = a3WithLabel.value
  override val a4: A4 get() = a4WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel)

  override fun <A5> plus(label: String, value: A5): DefaultKase5<A1, A2, A3, A4, A5> {
    return DefaultKase5(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }

  override fun component1(): A1 = a1
  override fun component2(): A2 = a2
  override fun component3(): A3 = a3
  override fun component4(): A4 = a4

  override fun toString(): String = displayName
}
