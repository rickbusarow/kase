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

/** A strongly-typed version of [Kase] for 1 parameter. */
public interface Kase1<out A1> : Kase {

  /** The 1st parameter. */
  public val a1: A1

  /** The 1st parameter. */
  public val a1WithLabel: KaseParameterWithLabel<A1>

  /** The delimiter between the label and the value, like `": "` in `label: value` */
  public val labelDelimiter: String get() = DELIMITER_DEFAULT

  /**
   * The separator between each label/value pair, like `" | "` in `label1: value1 | label2: value2`
   */
  public val displayNameSeparator: String get() = SEPARATOR_DEFAULT

  /** @see Kase1.a1 */
  public operator fun component1(): A1 = a1

  override fun <A2> plus(label: String, value: A2): Kase2<A1, A2> {
    return DefaultKase2(
      a1WithLabel = a1WithLabel,
      a2WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

/**
 * Creates a new [Kase] with the given parameter.
 *
 * @param a1 the [Kase1:a1] parameter.
 * @param labels the [KaseLabels1] to use for this [Kase1]
 * @param labelDelimiter the delimiter between the
 *   label and the value, like `": "` in `label: value`
 * @param displayNameSeparator the separator between each label/value
 *   pair, like `" | "` in `label1: value1 | label2: value2`
 */
public fun <A1> kase(
  a1: A1,
  labels: KaseLabels1 = KaseLabels1(),
  labelDelimiter: String = labels.labelDelimiter,
  displayNameSeparator: String = labels.displayNameSeparator
): Kase1<A1> {
  return DefaultKase1(
    a1WithLabel = kaseParam(value = a1, label = (a1 as? HasLabel)?.label ?: labels.a1Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/**
 * Creates a new [Kase1] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase1:a1] parameter.
 * @param labels the [KaseLabels1] to use for this [Kase1]
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see TestEnvironmentFactory
 */
public fun <T, K, A1> TestEnvironmentFactory<T>.test(
  a1: A1,
  labels: KaseLabels1 = KaseLabels1(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase1<A1> {
  this@TestEnvironmentFactory.test(
    kase = kase(a1, labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Creates a new [KaseLabels1] with the given labels.
 *
 * @param a1Label the label for the [Kase1.a1] property.
 * @return a new [KaseLabels1] with the given labels.
 */
public fun labels(
  a1Label: String = "a1"
): KaseLabels1 {
  return KaseLabels1(a1Label = a1Label)
}

/**
 * Returns a [List] of [Kase1]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase1.a1] parameter.
 * @param labels the [KaseLabels1] to use for this [Kase1]
 * @return a [List] of [Kase1]s from the given parameters.
 */
public fun <A1> kases(
  args1: Iterable<A1>,
  labels: KaseLabels1 = KaseLabels1()
): List<Kase1<A1>> {
  return buildList {
    for (a1 in args1) {
      add(kase(a1 = a1, labels = labels))
    }
  }
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase1DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1> Iterable<Kase1<A1>>.asTests(
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory(kases = this@asTests, testAction = testAction)
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase1] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase1]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase1
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase1DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1> testFactory(
  vararg kases: Kase1<A1>,
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase1] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase1]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase1
 * @see TestEnvironmentFactory
 */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase1DestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1> testFactory(
  kases: Iterable<Kase1<A1>>,
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testAction = { kase: Kase1<A1> -> testAction(kase.a1) }
  )
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase1] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase1]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase1
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase1")
public inline fun <A1> testFactory(
  vararg kases: Kase1<A1>,
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests { testAction(it.a1) }
}

/**
 * A strongly-typed version of [KaseLabels] for 1 parameter.
 *
 * @property a1Label The label for the [Kase1.a1] parameter.
 * @property labelDelimiter The delimiter between the label and the value. The default is `": "`.
 * @property displayNameSeparator The separator between
 *   each label/value pair. The default is `" | "`.
 */
@Poko
public class KaseLabels1(
  public val a1Label: String = "a1",
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label)
  }
}

@Poko
@PublishedApi
internal class DefaultKase1<out A1>(
  override val a1WithLabel: KaseParameterWithLabel<A1>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase1<A1>, KaseInternal {
  override val a1: A1 get() = a1WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel)

  override fun <A2> plus(label: String, value: A2): DefaultKase2<A1, A2> {
    return DefaultKase2(
      a1WithLabel = a1WithLabel,
      a2WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }

  override fun component1(): A1 = a1

  override fun toString(): String = displayName
}
