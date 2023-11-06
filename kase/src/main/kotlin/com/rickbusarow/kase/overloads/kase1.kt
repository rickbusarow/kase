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

import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParam
import com.rickbusarow.kase.files.TestFunctionCoordinates
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
    kase = kase(a1 = a1, labels = labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
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

/**
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase1]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase1
 */
@JvmName("asTestsKase1Destructured")
public inline fun <A1> Iterable<Kase1<A1>>.asTests(
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests(testName = { it.displayName }) { testAction(it.a1) }
  }
}

/**
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase1]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase1
 */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase1ExtensionDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1> Iterable<Kase1<A1>>.asTests(
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1) }
  }
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
@JvmName("testFactoryKase1VarargDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1> testFactory(
  vararg kases: Kase1<A1>,
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    kases.asSequence().asTests { testAction(it.a1) }
  }
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
@JvmName("testFactoryKase1IterableDestructuredTestEnvironment")
public inline fun <T : TestEnvironment, A1> testFactory(
  kases: Iterable<Kase1<A1>>,
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    kases.asTests { testAction(it.a1) }
  }
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
@JvmName("testFactoryKase1VarargDestructured")
public inline fun <A1> testFactory(
  vararg kases: Kase1<A1>,
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asSequence().asTests { testAction(it.a1) } }
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
@JvmName("testFactoryKase1IterableDestructured")
public inline fun <A1> testFactory(
  kases: Iterable<Kase1<A1>>,
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asTests { testAction(it.a1) } }
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
 * @param others the [Kase1] to combine with this [Kase1]
 * @return a list of [Kase2]s from the cartesian product of this [Kase1] and the given [Kase1].
 */
@JvmName("kase1timesKase1")
public operator fun <A1, B1> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase2<A1, B1>> = flatMap { (a1) ->
  others.map { (b1) ->
    kase(a1, b1)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase1]
 * @return a list of [Kase3]s from the cartesian product of this [Kase1] and the given [Kase2].
 */
@JvmName("kase1timesKase2")
public operator fun <A1, B1, B2> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase3<A1, B1, B2>> = flatMap { (a1) ->
  others.map { (b1, b2) ->
    kase(a1, b1, b2)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase1]
 * @return a list of [Kase4]s from the cartesian product of this [Kase1] and the given [Kase3].
 */
@JvmName("kase1timesKase3")
public operator fun <A1, B1, B2, B3> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase4<A1, B1, B2, B3>> = flatMap { (a1) ->
  others.map { (b1, b2, b3) ->
    kase(a1, b1, b2, b3)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase1]
 * @return a list of [Kase5]s from the cartesian product of this [Kase1] and the given [Kase4].
 */
@JvmName("kase1timesKase4")
public operator fun <A1, B1, B2, B3, B4> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase5<A1, B1, B2, B3, B4>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase1]
 * @return a list of [Kase6]s from the cartesian product of this [Kase1] and the given [Kase5].
 */
@JvmName("kase1timesKase5")
public operator fun <A1, B1, B2, B3, B4, B5> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>
): List<Kase6<A1, B1, B2, B3, B4, B5>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5) ->
    kase(a1, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase1]
 * @return a list of [Kase7]s from the cartesian product of this [Kase1] and the given [Kase6].
 */
@JvmName("kase1timesKase6")
public operator fun <A1, B1, B2, B3, B4, B5, B6> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>
): List<Kase7<A1, B1, B2, B3, B4, B5, B6>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    kase(a1, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase1]
 * @return a list of [Kase8]s from the cartesian product of this [Kase1] and the given [Kase7].
 */
@JvmName("kase1timesKase7")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>
): List<Kase8<A1, B1, B2, B3, B4, B5, B6, B7>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase1]
 * @return a list of [Kase9]s from the cartesian product of this [Kase1] and the given [Kase8].
 */
@JvmName("kase1timesKase8")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>
): List<Kase9<A1, B1, B2, B3, B4, B5, B6, B7, B8>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase1]
 * @return a list of [Kase10]s from the cartesian product of this [Kase1] and the given [Kase9].
 */
@JvmName("kase1timesKase9")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>
): List<Kase10<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase1]
 * @return a list of [Kase11]s from the cartesian product of this [Kase1] and the given [Kase10].
 */
@JvmName("kase1timesKase10")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>
): List<Kase11<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase1]
 * @return a list of [Kase12]s from the cartesian product of this [Kase1] and the given [Kase11].
 */
@JvmName("kase1timesKase11")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>
): List<Kase12<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase1]
 * @return a list of [Kase13]s from the cartesian product of this [Kase1] and the given [Kase12].
 */
@JvmName("kase1timesKase12")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>
): List<Kase13<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase1]
 * @return a list of [Kase14]s from the cartesian product of this [Kase1] and the given [Kase13].
 */
@JvmName("kase1timesKase13")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>
): List<Kase14<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase1]
 * @return a list of [Kase15]s from the cartesian product of this [Kase1] and the given [Kase14].
 */
@JvmName("kase1timesKase14")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>
): List<Kase15<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase15] to combine with this [Kase1]
 * @return a list of [Kase16]s from the cartesian product of this [Kase1] and the given [Kase15].
 */
@JvmName("kase1timesKase15")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>
): List<Kase16<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>> =
  flatMap { (a1) ->
    others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
      kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
    }
  }

/**
 * @param others the [Kase16] to combine with this [Kase1]
 * @return a list of [Kase17]s from the cartesian product of this [Kase1] and the given [Kase16].
 */
@JvmName("kase1timesKase16")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>>
): List<Kase17<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>> =
  flatMap { (a1) ->
    others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16) ->
      kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16)
    }
  }

/**
 * @param others the [Kase17] to combine with this [Kase1]
 * @return a list of [Kase18]s from the cartesian product of this [Kase1] and the given [Kase17].
 */
@JvmName("kase1timesKase17")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase17<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>>
): List<Kase18<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>> =
  flatMap { (a1) ->
    others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17) ->
      kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17)
    }
  }

/**
 * @param others the [Kase18] to combine with this [Kase1]
 * @return a list of [Kase19]s from the cartesian product of this [Kase1] and the given [Kase18].
 */
@JvmName("kase1timesKase18")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>
): List<Kase19<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>> =
  flatMap { (a1) ->
    others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
      kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
    }
  }

/**
 * @param others the [Kase19] to combine with this [Kase1]
 * @return a list of [Kase20]s from the cartesian product of this [Kase1] and the given [Kase19].
 */
@JvmName("kase1timesKase19")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase19<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>>
): List<Kase20<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>> =
  flatMap { (a1) ->
    others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19) ->
      kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19)
    }
  }

/**
 * @param others the [Kase20] to combine with this [Kase1]
 * @return a list of [Kase21]s from the cartesian product of this [Kase1] and the given [Kase20].
 */
@JvmName("kase1timesKase20")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase20<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20>>
): List<Kase21<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20>> =
  flatMap { (a1) ->
    others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20) ->
      kase(
        a1,
        b1,
        b2,
        b3,
        b4,
        b5,
        b6,
        b7,
        b8,
        b9,
        b10,
        b11,
        b12,
        b13,
        b14,
        b15,
        b16,
        b17,
        b18,
        b19,
        b20
      )
    }
  }
