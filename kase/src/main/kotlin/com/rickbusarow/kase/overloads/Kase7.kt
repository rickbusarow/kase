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
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream

/** A strongly typed version of [Kase] for 7 parameters. */
public interface Kase7<A1, A2, A3, A4, A5, A6, A7> : Kase6<A1, A2, A3, A4, A5, A6> {

  /** The 7th parameter. */
  public val a7: A7

  /** @see Kase7.a7 */
  public operator fun component7(): A7 = a7
}

@Poko
@PublishedApi
internal class DefaultKase7<A1, A2, A3, A4, A5, A6, A7>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  override val a4: A4,
  override val a5: A5,
  override val a6: A6,
  override val a7: A7,
  private val displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>>
) : Kase7<A1, A2, A3, A4, A5, A6, A7>, KaseInternal {

  override val displayName: String
    get() = with(displayNameFactory) { createDisplayName() }

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
  override operator fun component4(): A4 = a4
  override operator fun component5(): A5 = a5
  override operator fun component6(): A6 = a6
  override operator fun component7(): A7 = a7
}

private fun <A1, A2, A3, A4, A5, A6, A7> defaultKase7DisplayNameFactory(): KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3 | a4: $a4 | a5: $a5 | a6: $a6 | a7: $a7"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase7.a1] parameter.
 * @param a2 the [Kase7.a2] parameter.
 * @param a3 the [Kase7.a3] parameter.
 * @param a4 the [Kase7.a4] parameter.
 * @param a5 the [Kase7.a5] parameter.
 * @param a6 the [Kase7.a6] parameter.
 * @param a7 the [Kase7.a7] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 */
public fun <A1, A2, A3, A4, A5, A6, A7> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7,
  displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>> = defaultKase7DisplayNameFactory()
): Kase7<A1, A2, A3, A4, A5, A6, A7> = DefaultKase7(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7,
  displayNameFactory = displayNameFactory
)
/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase7.a1] parameter.
 * @param a2 the [Kase7.a2] parameter.
 * @param a3 the [Kase7.a3] parameter.
 * @param a4 the [Kase7.a4] parameter.
 * @param a5 the [Kase7.a5] parameter.
 * @param a6 the [Kase7.a6] parameter.
 * @param a7 the [Kase7.a7] parameter.
 */
public fun <A1, A2, A3, A4, A5, A6, A7> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7
): Kase7<A1, A2, A3, A4, A5, A6, A7> = DefaultKase7(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7,
  displayNameFactory = { displayName }
)
/**
 * Creates a new [Kase7] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase7.a1] parameter.
 * @param a2 the [Kase7.a2] parameter.
 * @param a3 the [Kase7.a3] parameter.
 * @param a4 the [Kase7.a4] parameter.
 * @param a5 the [Kase7.a5] parameter.
 * @param a6 the [Kase7.a6] parameter.
 * @param a7 the [Kase7.a7] parameter.
 * @param displayNameFactory defines the name used for this test environment's working directory
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see KaseTestFactory
 */
public fun <T: TestEnvironment, A1, A2, A3, A4, A5, A6, A7> KaseTestFactory<T, Kase7<A1, A2, A3, A4, A5, A6, A7>>.test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7,
  displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>> = defaultKase7DisplayNameFactory(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) {
  this@KaseTestFactory.test(
    kase = kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, displayNameFactory = displayNameFactory),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Returns a [List] of [Kase7]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase7.a1] parameter.
 * @param args2 values mapped to the [Kase7.a2] parameter.
 * @param args3 values mapped to the [Kase7.a3] parameter.
 * @param args4 values mapped to the [Kase7.a4] parameter.
 * @param args5 values mapped to the [Kase7.a5] parameter.
 * @param args6 values mapped to the [Kase7.a6] parameter.
 * @param args7 values mapped to the [Kase7.a7] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a [List] of [Kase7]s from the given parameters.
 */
public fun <A1, A2, A3, A4, A5, A6, A7> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>> = defaultKase7DisplayNameFactory()
): List<Kase7<A1, A2, A3, A4, A5, A6, A7>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                for (a7 in args7) {
                  add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, displayNameFactory = displayNameFactory))
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
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase7]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase7
 */
@JvmName("asTestsKase7Destructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.asTests(
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase7] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase7]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase7
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase7VarargDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  vararg kases: Kase7<A1, A2, A3, A4, A5, A6, A7>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7) } }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase7] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase7]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase7
 * @see TestEnvironmentFactory
 */
@JvmName("testFactoryKase7IterableDestructured")
public inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  kases: Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>,
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7) } }
}

/**
 * @param others the [Kase1] to combine with this [Kase7]
 * @return a list of [Kase8]s from the cartesian product of this [Kase7] and the given [Kase1].
 */
@JvmName("kase7timesKase1")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase8<A1, A2, A3, A4, A5, A6, A7, B1>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase7]
 * @return a list of [Kase9]s from the cartesian product of this [Kase7] and the given [Kase2].
 */
@JvmName("kase7timesKase2")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase9<A1, A2, A3, A4, A5, A6, A7, B1, B2>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase7]
 * @return a list of [Kase10]s from the cartesian product of this [Kase7] and the given [Kase3].
 */
@JvmName("kase7timesKase3")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase10<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase7]
 * @return a list of [Kase11]s from the cartesian product of this [Kase7] and the given [Kase4].
 */
@JvmName("kase7timesKase4")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase7]
 * @return a list of [Kase12]s from the cartesian product of this [Kase7] and the given [Kase5].
 */
@JvmName("kase7timesKase5")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>
): List<Kase12<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase7]
 * @return a list of [Kase13]s from the cartesian product of this [Kase7] and the given [Kase6].
 */
@JvmName("kase7timesKase6")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>
): List<Kase13<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase7]
 * @return a list of [Kase14]s from the cartesian product of this [Kase7] and the given [Kase7].
 */
@JvmName("kase7timesKase7")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>
): List<Kase14<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase7]
 * @return a list of [Kase15]s from the cartesian product of this [Kase7] and the given [Kase8].
 */
@JvmName("kase7timesKase8")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>
): List<Kase15<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase7]
 * @return a list of [Kase16]s from the cartesian product of this [Kase7] and the given [Kase9].
 */
@JvmName("kase7timesKase9")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>
): List<Kase16<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase7]
 * @return a list of [Kase17]s from the cartesian product of this [Kase7] and the given [Kase10].
 */
@JvmName("kase7timesKase10")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>
): List<Kase17<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase7]
 * @return a list of [Kase18]s from the cartesian product of this [Kase7] and the given [Kase11].
 */
@JvmName("kase7timesKase11")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>
): List<Kase18<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase7]
 * @return a list of [Kase19]s from the cartesian product of this [Kase7] and the given [Kase12].
 */
@JvmName("kase7timesKase12")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>
): List<Kase19<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase7]
 * @return a list of [Kase20]s from the cartesian product of this [Kase7] and the given [Kase13].
 */
@JvmName("kase7timesKase13")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase7]
 * @return a list of [Kase21]s from the cartesian product of this [Kase7] and the given [Kase14].
 */
@JvmName("kase7timesKase14")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>
): List<Kase21<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase15] to combine with this [Kase7]
 * @return a list of [Kase22]s from the cartesian product of this [Kase7] and the given [Kase15].
 */
@JvmName("kase7timesKase15")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>
): List<Kase22<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
  }
}