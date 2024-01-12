/*
 * Copyright (C) 2024 Rick Busarow
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

package com.rickbusarow.kase

import com.rickbusarow.kase.KaseMatrix.KaseMatrixElement
import com.rickbusarow.kase.KaseMatrix.KaseMatrixKey
import dev.drewhamilton.poko.Poko
import java.util.stream.Stream
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest

/**
 * A strongly typed version of [Kase] for 3 parameters.
 *
 * @since 0.1.0
 */
public interface Kase3<out A1, out A2, out A3> : Kase2<A1, A2> {

  /** The 3rd parameter. */
  public val a3: A3

  /** @see Kase3.a3 */
  public operator fun component3(): A3 = a3
}

@Poko
@PublishedApi
internal class DefaultKase3<out A1, out A2, out A3>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  private val displayNameFactory: KaseDisplayNameFactory<Kase3<A1, A2, A3>>
) : Kase3<A1, A2, A3> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
}

/**
 * Returns a list of [Kase3]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase3]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>
> KaseMatrix.kases(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  displayNameFactory: KaseDisplayNameFactory<Kase3<A1, A2, A3>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value} | ${a3.label}: ${a3.value}"
  }
): List<Kase3<A1, A2, A3>> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          add(kase(a1 = a1, a2 = a2, a3 = a3, displayNameFactory = displayNameFactory))
        }
      }
    }
  }
}

/**
 * Returns a list of [Kase3]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase3]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>,
  T : Kase3<A1, A2, A3>
> KaseMatrix.get(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  instanceFactory: (A1, A2, A3) -> T
): List<T> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          add(instanceFactory(a1, a2, a3))
        }
      }
    }
  }
}

private fun <A1, A2, A3> defaultKase3DisplayNameFactory(): KaseDisplayNameFactory<Kase3<A1, A2, A3>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase3.a1] parameter.
 * @param a2 the [Kase3.a2] parameter.
 * @param a3 the [Kase3.a3] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2, A3> kase(
  a1: A1, a2: A2, a3: A3,
  displayNameFactory: KaseDisplayNameFactory<Kase3<A1, A2, A3>> = defaultKase3DisplayNameFactory()
): Kase3<A1, A2, A3> = DefaultKase3(
  a1 = a1, a2 = a2, a3 = a3,
  displayNameFactory = displayNameFactory
)
/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase3.a1] parameter.
 * @param a2 the [Kase3.a2] parameter.
 * @param a3 the [Kase3.a3] parameter.
 * @since 0.1.0
 */
public fun <A1, A2, A3> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3
): Kase3<A1, A2, A3> = DefaultKase3(
  a1 = a1, a2 = a2, a3 = a3,
  displayNameFactory = { displayName }
)
/**
 * Returns a list of [Kase3]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase3.a1] parameter.
 * @param args2 values mapped to the [Kase3.a2] parameter.
 * @param args3 values mapped to the [Kase3.a3] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase3]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  displayNameFactory: KaseDisplayNameFactory<Kase3<A1, A2, A3>> = defaultKase3DisplayNameFactory()
): List<Kase3<A1, A2, A3>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          add(kase(a1 = a1, a2 = a2, a3 = a3, displayNameFactory = displayNameFactory))
        }
      }
    }
  }
}

/**
 * Returns a sequence of [Kase3]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase3.a1] parameter.
 * @param args2 values mapped to the [Kase3.a2] parameter.
 * @param args3 values mapped to the [Kase3.a3] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase3]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3> kases(
  args1: Sequence<A1>,
  args2: Sequence<A2>,
  args3: Sequence<A3>,
  displayNameFactory: KaseDisplayNameFactory<Kase3<A1, A2, A3>> = defaultKase3DisplayNameFactory()
): Sequence<Kase3<A1, A2, A3>> {
  return sequence {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          yield(kase(a1 = a1, a2 = a2, a3 = a3, displayNameFactory = displayNameFactory))
        }
      }
    }
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase3]
 * @return a list of [Kase4]s from the cartesian product of this [Kase3] and the given [Kase1].
 * @since 0.1.0
 */
@JvmName("kase3timesKase1")
public operator fun <A1, A2, A3, B1> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase4<A1, A2, A3, B1>> = flatMap { (a1, a2, a3) ->
  others.map { (b1) ->
    kase(a1, a2, a3, b1)
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase4]s from the cartesian product of this [Kase3] and the given [Kase1].
 * @since 0.5.0
 */
@JvmName("kase3timesKase1InstanceFactory")
public inline fun <A1, A2, A3, B1, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase1<B1>>,
  instanceFactory: (A1, A2, A3, B1) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1) ->
    instanceFactory(a1, a2, a3, b1)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase3]
 * @return a list of [Kase5]s from the cartesian product of this [Kase3] and the given [Kase2].
 * @since 0.1.0
 */
@JvmName("kase3timesKase2")
public operator fun <A1, A2, A3, B1, B2> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase5<A1, A2, A3, B1, B2>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2) ->
    kase(a1, a2, a3, b1, b2)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase5]s from the cartesian product of this [Kase3] and the given [Kase2].
 * @since 0.5.0
 */
@JvmName("kase3timesKase2InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase2<B1, B2>>,
  instanceFactory: (A1, A2, A3, B1, B2) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2) ->
    instanceFactory(a1, a2, a3, b1, b2)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase3]
 * @return a list of [Kase6]s from the cartesian product of this [Kase3] and the given [Kase3].
 * @since 0.1.0
 */
@JvmName("kase3timesKase3")
public operator fun <A1, A2, A3, B1, B2, B3> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase6<A1, A2, A3, B1, B2, B3>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, b1, b2, b3)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase6]s from the cartesian product of this [Kase3] and the given [Kase3].
 * @since 0.5.0
 */
@JvmName("kase3timesKase3InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase3<B1, B2, B3>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3) ->
    instanceFactory(a1, a2, a3, b1, b2, b3)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase3]
 * @return a list of [Kase7]s from the cartesian product of this [Kase3] and the given [Kase4].
 * @since 0.1.0
 */
@JvmName("kase3timesKase4")
public operator fun <A1, A2, A3, B1, B2, B3, B4> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase7<A1, A2, A3, B1, B2, B3, B4>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, a2, a3, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase7]s from the cartesian product of this [Kase3] and the given [Kase4].
 * @since 0.5.0
 */
@JvmName("kase3timesKase4InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase3]
 * @return a list of [Kase8]s from the cartesian product of this [Kase3] and the given [Kase5].
 * @since 0.1.0
 */
@JvmName("kase3timesKase5")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>
): List<Kase8<A1, A2, A3, B1, B2, B3, B4, B5>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase8]s from the cartesian product of this [Kase3] and the given [Kase5].
 * @since 0.5.0
 */
@JvmName("kase3timesKase5InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase3]
 * @return a list of [Kase9]s from the cartesian product of this [Kase3] and the given [Kase6].
 * @since 0.1.0
 */
@JvmName("kase3timesKase6")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>
): List<Kase9<A1, A2, A3, B1, B2, B3, B4, B5, B6>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase9]s from the cartesian product of this [Kase3] and the given [Kase6].
 * @since 0.5.0
 */
@JvmName("kase3timesKase6InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase3]
 * @return a list of [Kase10]s from the cartesian product of this [Kase3] and the given [Kase7].
 * @since 0.1.0
 */
@JvmName("kase3timesKase7")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>
): List<Kase10<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase10]s from the cartesian product of this [Kase3] and the given [Kase7].
 * @since 0.5.0
 */
@JvmName("kase3timesKase7InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase3]
 * @return a list of [Kase11]s from the cartesian product of this [Kase3] and the given [Kase8].
 * @since 0.1.0
 */
@JvmName("kase3timesKase8")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>
): List<Kase11<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase3] and the given [Kase8].
 * @since 0.5.0
 */
@JvmName("kase3timesKase8InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase3]
 * @return a list of [Kase12]s from the cartesian product of this [Kase3] and the given [Kase9].
 * @since 0.1.0
 */
@JvmName("kase3timesKase9")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>
): List<Kase12<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase12]s from the cartesian product of this [Kase3] and the given [Kase9].
 * @since 0.5.0
 */
@JvmName("kase3timesKase9InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase3]
 * @return a list of [Kase13]s from the cartesian product of this [Kase3] and the given [Kase10].
 * @since 0.1.0
 */
@JvmName("kase3timesKase10")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>
): List<Kase13<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase13]s from the cartesian product of this [Kase3] and the given [Kase10].
 * @since 0.5.0
 */
@JvmName("kase3timesKase10InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase3]
 * @return a list of [Kase14]s from the cartesian product of this [Kase3] and the given [Kase11].
 * @since 0.1.0
 */
@JvmName("kase3timesKase11")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>
): List<Kase14<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase14]s from the cartesian product of this [Kase3] and the given [Kase11].
 * @since 0.5.0
 */
@JvmName("kase3timesKase11InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase3]
 * @return a list of [Kase15]s from the cartesian product of this [Kase3] and the given [Kase12].
 * @since 0.1.0
 */
@JvmName("kase3timesKase12")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>
): List<Kase15<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase15]s from the cartesian product of this [Kase3] and the given [Kase12].
 * @since 0.5.0
 */
@JvmName("kase3timesKase12InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase3]
 * @return a list of [Kase16]s from the cartesian product of this [Kase3] and the given [Kase13].
 * @since 0.1.0
 */
@JvmName("kase3timesKase13")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>
): List<Kase16<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase16]s from the cartesian product of this [Kase3] and the given [Kase13].
 * @since 0.5.0
 */
@JvmName("kase3timesKase13InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase3]
 * @return a list of [Kase17]s from the cartesian product of this [Kase3] and the given [Kase14].
 * @since 0.1.0
 */
@JvmName("kase3timesKase14")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>
): List<Kase17<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase17]s from the cartesian product of this [Kase3] and the given [Kase14].
 * @since 0.5.0
 */
@JvmName("kase3timesKase14InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase15] to combine with this [Kase3]
 * @return a list of [Kase18]s from the cartesian product of this [Kase3] and the given [Kase15].
 * @since 0.1.0
 */
@JvmName("kase3timesKase15")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>
): List<Kase18<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
  }
}

/**
 * @param others the [Kase15] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase18]s from the cartesian product of this [Kase3] and the given [Kase15].
 * @since 0.5.0
 */
@JvmName("kase3timesKase15InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
  }
}

/**
 * @param others the [Kase16] to combine with this [Kase3]
 * @return a list of [Kase19]s from the cartesian product of this [Kase3] and the given [Kase16].
 * @since 0.1.0
 */
@JvmName("kase3timesKase16")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>>
): List<Kase19<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16)
  }
}

/**
 * @param others the [Kase16] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase19]s from the cartesian product of this [Kase3] and the given [Kase16].
 * @since 0.5.0
 */
@JvmName("kase3timesKase16InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16)
  }
}

/**
 * @param others the [Kase17] to combine with this [Kase3]
 * @return a list of [Kase20]s from the cartesian product of this [Kase3] and the given [Kase17].
 * @since 0.1.0
 */
@JvmName("kase3timesKase17")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase17<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>>
): List<Kase20<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17)
  }
}

/**
 * @param others the [Kase17] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase3] and the given [Kase17].
 * @since 0.5.0
 */
@JvmName("kase3timesKase17InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase17<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17)
  }
}

/**
 * @param others the [Kase18] to combine with this [Kase3]
 * @return a list of [Kase21]s from the cartesian product of this [Kase3] and the given [Kase18].
 * @since 0.1.0
 */
@JvmName("kase3timesKase18")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>
): List<Kase21<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
  }
}

/**
 * @param others the [Kase18] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase21]s from the cartesian product of this [Kase3] and the given [Kase18].
 * @since 0.5.0
 */
@JvmName("kase3timesKase18InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
  }
}

/**
 * @param others the [Kase19] to combine with this [Kase3]
 * @return a list of [Kase22]s from the cartesian product of this [Kase3] and the given [Kase19].
 * @since 0.1.0
 */
@JvmName("kase3timesKase19")
public operator fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase19<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>>
): List<Kase22<A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19) ->
    kase(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19)
  }
}

/**
 * @param others the [Kase19] to combine with this [Kase3]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase22]s from the cartesian product of this [Kase3] and the given [Kase19].
 * @since 0.5.0
 */
@JvmName("kase3timesKase19InstanceFactory")
public inline fun <A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, T> Iterable<Kase3<A1, A2, A3>>.times(
  others: Iterable<Kase19<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>>,
  instanceFactory: (A1, A2, A3, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19) -> T
): List<T> = flatMap { (a1, a2, a3) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19) ->
    instanceFactory(a1, a2, a3, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19)
  }
}
