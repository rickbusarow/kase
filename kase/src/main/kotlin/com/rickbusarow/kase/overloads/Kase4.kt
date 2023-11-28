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

/** A strongly-typed version of [Kase] for 4 parameters. */
public interface Kase4<A1, A2, A3, A4> : Kase3<A1, A2, A3> {

  /** The 4th parameter. */
  public val a4: A4

  /** The 4th parameter with its label. */
  public val a4WithLabel: KaseParameterWithLabel<A4>

  /** @see Kase4.a4 */
  public operator fun component4(): A4 = a4
}

@Poko
@PublishedApi
internal class DefaultKase4<A1, A2, A3, A4>(
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

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
  override operator fun component4(): A4 = a4

  override fun toString(): String = displayName
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

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase4.a1] parameter.
 * @param a2 the [Kase4.a2] parameter.
 * @param a3 the [Kase4.a3] parameter.
 * @param a4 the [Kase4.a4] parameter.
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
 * @param a1 the [Kase4.a1] parameter.
 * @param a2 the [Kase4.a2] parameter.
 * @param a3 the [Kase4.a3] parameter.
 * @param a4 the [Kase4.a4] parameter.
 * @param labels the [KaseLabels4] to use for this [Kase4]
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see KaseTestFactory
 */
public fun <T, K, A1, A2, A3, A4> KaseTestFactory<T, Kase4<A1, A2, A3, A4>>.test(
  a1: A1, a2: A2, a3: A3, a4: A4,
  labels: KaseLabels4 = KaseLabels4(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment,
        K : Kase4<A1, A2, A3, A4> {
  this@KaseTestFactory.test(
    kase = kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, labels = labels),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
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

/**
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase4]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase4
 */
@JvmName("asTestsKase4Destructured")
public inline fun <A1, A2, A3, A4> Iterable<Kase4<A1, A2, A3, A4>>.asTests(
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4) }
  }
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
@JvmName("testFactoryKase4VarargDestructured")
public inline fun <A1, A2, A3, A4> testFactory(
  vararg kases: Kase4<A1, A2, A3, A4>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4) } }
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
@JvmName("testFactoryKase4IterableDestructured")
public inline fun <A1, A2, A3, A4> testFactory(
  kases: Iterable<Kase4<A1, A2, A3, A4>>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4) } }
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
 * @param others the [Kase1] to combine with this [Kase4]
 * @return a list of [Kase5]s from the cartesian product of this [Kase4] and the given [Kase1].
 */
@JvmName("kase4timesKase1")
public operator fun <A1, A2, A3, A4, B1> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase5<A1, A2, A3, A4, B1>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, b1)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase4]
 * @return a list of [Kase6]s from the cartesian product of this [Kase4] and the given [Kase2].
 */
@JvmName("kase4timesKase2")
public operator fun <A1, A2, A3, A4, B1, B2> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase6<A1, A2, A3, A4, B1, B2>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2) ->
    kase(a1, a2, a3, a4, b1, b2)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase4]
 * @return a list of [Kase7]s from the cartesian product of this [Kase4] and the given [Kase3].
 */
@JvmName("kase4timesKase3")
public operator fun <A1, A2, A3, A4, B1, B2, B3> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase7<A1, A2, A3, A4, B1, B2, B3>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, a4, b1, b2, b3)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase4]
 * @return a list of [Kase8]s from the cartesian product of this [Kase4] and the given [Kase4].
 */
@JvmName("kase4timesKase4")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase8<A1, A2, A3, A4, B1, B2, B3, B4>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase4]
 * @return a list of [Kase9]s from the cartesian product of this [Kase4] and the given [Kase5].
 */
@JvmName("kase4timesKase5")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>
): List<Kase9<A1, A2, A3, A4, B1, B2, B3, B4, B5>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase4]
 * @return a list of [Kase10]s from the cartesian product of this [Kase4] and the given [Kase6].
 */
@JvmName("kase4timesKase6")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>
): List<Kase10<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase4]
 * @return a list of [Kase11]s from the cartesian product of this [Kase4] and the given [Kase7].
 */
@JvmName("kase4timesKase7")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>
): List<Kase11<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase4]
 * @return a list of [Kase12]s from the cartesian product of this [Kase4] and the given [Kase8].
 */
@JvmName("kase4timesKase8")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>
): List<Kase12<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase4]
 * @return a list of [Kase13]s from the cartesian product of this [Kase4] and the given [Kase9].
 */
@JvmName("kase4timesKase9")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>
): List<Kase13<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase4]
 * @return a list of [Kase14]s from the cartesian product of this [Kase4] and the given [Kase10].
 */
@JvmName("kase4timesKase10")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>
): List<Kase14<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase4]
 * @return a list of [Kase15]s from the cartesian product of this [Kase4] and the given [Kase11].
 */
@JvmName("kase4timesKase11")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>
): List<Kase15<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase4]
 * @return a list of [Kase16]s from the cartesian product of this [Kase4] and the given [Kase12].
 */
@JvmName("kase4timesKase12")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>
): List<Kase16<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase4]
 * @return a list of [Kase17]s from the cartesian product of this [Kase4] and the given [Kase13].
 */
@JvmName("kase4timesKase13")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>
): List<Kase17<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase4]
 * @return a list of [Kase18]s from the cartesian product of this [Kase4] and the given [Kase14].
 */
@JvmName("kase4timesKase14")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>
): List<Kase18<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase15] to combine with this [Kase4]
 * @return a list of [Kase19]s from the cartesian product of this [Kase4] and the given [Kase15].
 */
@JvmName("kase4timesKase15")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>
): List<Kase19<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
  }
}

/**
 * @param others the [Kase16] to combine with this [Kase4]
 * @return a list of [Kase20]s from the cartesian product of this [Kase4] and the given [Kase16].
 */
@JvmName("kase4timesKase16")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>>
): List<Kase20<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16)
  }
}

/**
 * @param others the [Kase17] to combine with this [Kase4]
 * @return a list of [Kase21]s from the cartesian product of this [Kase4] and the given [Kase17].
 */
@JvmName("kase4timesKase17")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase17<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>>
): List<Kase21<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17)
  }
}

/**
 * @param others the [Kase18] to combine with this [Kase4]
 * @return a list of [Kase22]s from the cartesian product of this [Kase4] and the given [Kase18].
 */
@JvmName("kase4timesKase18")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>
): List<Kase22<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
  }
}
