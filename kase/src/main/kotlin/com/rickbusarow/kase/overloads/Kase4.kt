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

/**
 * A strongly typed version of [Kase] for 4 parameters.
 *
 * @since 0.1.0
 */
public interface Kase4<out A1, out A2, out A3, out A4> : Kase3<A1, A2, A3> {

  /** The 4th parameter. */
  public val a4: A4

  /** @see Kase4.a4 */
  public operator fun component4(): A4 = a4
}

@Poko
@PublishedApi
internal class DefaultKase4<out A1, out A2, out A3, out A4>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  override val a4: A4,
  private val displayNameFactory: KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>>
) : Kase4<A1, A2, A3, A4> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
  override operator fun component4(): A4 = a4
}

/**
 * Returns a list of [Kase4]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase4]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>,
  reified A4 : KaseMatrixElement<*>
> KaseMatrix.kases(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  displayNameFactory: KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value} | ${a3.label}: ${a3.value} | ${a4.label}: ${a4.value}"
  }
): List<Kase4<A1, A2, A3, A4>> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, displayNameFactory = displayNameFactory))
          }
        }
      }
    }
  }
}

/**
 * Returns a list of [Kase4]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase4]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>,
  reified A4 : KaseMatrixElement<*>,
  T : Kase4<A1, A2, A3, A4>
> KaseMatrix.get(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  instanceFactory: (A1, A2, A3, A4) -> T
): List<T> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            add(instanceFactory(a1, a2, a3, a4))
          }
        }
      }
    }
  }
}

private fun <A1, A2, A3, A4> defaultKase4DisplayNameFactory(): KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3 | a4: $a4"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase4.a1] parameter.
 * @param a2 the [Kase4.a2] parameter.
 * @param a3 the [Kase4.a3] parameter.
 * @param a4 the [Kase4.a4] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4> kase(
  a1: A1, a2: A2, a3: A3, a4: A4,
  displayNameFactory: KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>> = defaultKase4DisplayNameFactory()
): Kase4<A1, A2, A3, A4> = DefaultKase4(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4,
  displayNameFactory = displayNameFactory
)
/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase4.a1] parameter.
 * @param a2 the [Kase4.a2] parameter.
 * @param a3 the [Kase4.a3] parameter.
 * @param a4 the [Kase4.a4] parameter.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4
): Kase4<A1, A2, A3, A4> = DefaultKase4(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4,
  displayNameFactory = { displayName }
)
/**
 * Returns a list of [Kase4]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase4.a1] parameter.
 * @param args2 values mapped to the [Kase4.a2] parameter.
 * @param args3 values mapped to the [Kase4.a3] parameter.
 * @param args4 values mapped to the [Kase4.a4] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase4]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  displayNameFactory: KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>> = defaultKase4DisplayNameFactory()
): List<Kase4<A1, A2, A3, A4>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, displayNameFactory = displayNameFactory))
          }
        }
      }
    }
  }
}

/**
 * Returns a sequence of [Kase4]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase4.a1] parameter.
 * @param args2 values mapped to the [Kase4.a2] parameter.
 * @param args3 values mapped to the [Kase4.a3] parameter.
 * @param args4 values mapped to the [Kase4.a4] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase4]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4> kases(
  args1: Sequence<A1>,
  args2: Sequence<A2>,
  args3: Sequence<A3>,
  args4: Sequence<A4>,
  displayNameFactory: KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>> = defaultKase4DisplayNameFactory()
): Sequence<Kase4<A1, A2, A3, A4>> {
  return sequence {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            yield(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, displayNameFactory = displayNameFactory))
          }
        }
      }
    }
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase4]
 * @return a list of [Kase5]s from the cartesian product of this [Kase4] and the given [Kase1].
 * @since 0.1.0
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
 * @param others the [Kase1] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase5]s from the cartesian product of this [Kase4] and the given [Kase1].
 * @since 0.5.0
 */
@JvmName("kase4timesKase1InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase1<B1>>,
  instanceFactory: (A1, A2, A3, A4, B1) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1) ->
    instanceFactory(a1, a2, a3, a4, b1)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase4]
 * @return a list of [Kase6]s from the cartesian product of this [Kase4] and the given [Kase2].
 * @since 0.1.0
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
 * @param others the [Kase2] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase6]s from the cartesian product of this [Kase4] and the given [Kase2].
 * @since 0.5.0
 */
@JvmName("kase4timesKase2InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase2<B1, B2>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2) ->
    instanceFactory(a1, a2, a3, a4, b1, b2)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase4]
 * @return a list of [Kase7]s from the cartesian product of this [Kase4] and the given [Kase3].
 * @since 0.1.0
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
 * @param others the [Kase3] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase7]s from the cartesian product of this [Kase4] and the given [Kase3].
 * @since 0.5.0
 */
@JvmName("kase4timesKase3InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase3<B1, B2, B3>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase4]
 * @return a list of [Kase8]s from the cartesian product of this [Kase4] and the given [Kase4].
 * @since 0.1.0
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
 * @param others the [Kase4] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase8]s from the cartesian product of this [Kase4] and the given [Kase4].
 * @since 0.5.0
 */
@JvmName("kase4timesKase4InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase4]
 * @return a list of [Kase9]s from the cartesian product of this [Kase4] and the given [Kase5].
 * @since 0.1.0
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
 * @param others the [Kase5] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase9]s from the cartesian product of this [Kase4] and the given [Kase5].
 * @since 0.5.0
 */
@JvmName("kase4timesKase5InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase4]
 * @return a list of [Kase10]s from the cartesian product of this [Kase4] and the given [Kase6].
 * @since 0.1.0
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
 * @param others the [Kase6] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase10]s from the cartesian product of this [Kase4] and the given [Kase6].
 * @since 0.5.0
 */
@JvmName("kase4timesKase6InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase4]
 * @return a list of [Kase11]s from the cartesian product of this [Kase4] and the given [Kase7].
 * @since 0.1.0
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
 * @param others the [Kase7] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase4] and the given [Kase7].
 * @since 0.5.0
 */
@JvmName("kase4timesKase7InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase4]
 * @return a list of [Kase12]s from the cartesian product of this [Kase4] and the given [Kase8].
 * @since 0.1.0
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
 * @param others the [Kase8] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase12]s from the cartesian product of this [Kase4] and the given [Kase8].
 * @since 0.5.0
 */
@JvmName("kase4timesKase8InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase4]
 * @return a list of [Kase13]s from the cartesian product of this [Kase4] and the given [Kase9].
 * @since 0.1.0
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
 * @param others the [Kase9] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase13]s from the cartesian product of this [Kase4] and the given [Kase9].
 * @since 0.5.0
 */
@JvmName("kase4timesKase9InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase4]
 * @return a list of [Kase14]s from the cartesian product of this [Kase4] and the given [Kase10].
 * @since 0.1.0
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
 * @param others the [Kase10] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase14]s from the cartesian product of this [Kase4] and the given [Kase10].
 * @since 0.5.0
 */
@JvmName("kase4timesKase10InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase4]
 * @return a list of [Kase15]s from the cartesian product of this [Kase4] and the given [Kase11].
 * @since 0.1.0
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
 * @param others the [Kase11] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase15]s from the cartesian product of this [Kase4] and the given [Kase11].
 * @since 0.5.0
 */
@JvmName("kase4timesKase11InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase4]
 * @return a list of [Kase16]s from the cartesian product of this [Kase4] and the given [Kase12].
 * @since 0.1.0
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
 * @param others the [Kase12] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase16]s from the cartesian product of this [Kase4] and the given [Kase12].
 * @since 0.5.0
 */
@JvmName("kase4timesKase12InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase4]
 * @return a list of [Kase17]s from the cartesian product of this [Kase4] and the given [Kase13].
 * @since 0.1.0
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
 * @param others the [Kase13] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase17]s from the cartesian product of this [Kase4] and the given [Kase13].
 * @since 0.5.0
 */
@JvmName("kase4timesKase13InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase4]
 * @return a list of [Kase18]s from the cartesian product of this [Kase4] and the given [Kase14].
 * @since 0.1.0
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
 * @param others the [Kase14] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase18]s from the cartesian product of this [Kase4] and the given [Kase14].
 * @since 0.5.0
 */
@JvmName("kase4timesKase14InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase15] to combine with this [Kase4]
 * @return a list of [Kase19]s from the cartesian product of this [Kase4] and the given [Kase15].
 * @since 0.1.0
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
 * @param others the [Kase15] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase19]s from the cartesian product of this [Kase4] and the given [Kase15].
 * @since 0.5.0
 */
@JvmName("kase4timesKase15InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
  }
}

/**
 * @param others the [Kase16] to combine with this [Kase4]
 * @return a list of [Kase20]s from the cartesian product of this [Kase4] and the given [Kase16].
 * @since 0.1.0
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
 * @param others the [Kase16] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase4] and the given [Kase16].
 * @since 0.5.0
 */
@JvmName("kase4timesKase16InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16)
  }
}

/**
 * @param others the [Kase17] to combine with this [Kase4]
 * @return a list of [Kase21]s from the cartesian product of this [Kase4] and the given [Kase17].
 * @since 0.1.0
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
 * @param others the [Kase17] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase21]s from the cartesian product of this [Kase4] and the given [Kase17].
 * @since 0.5.0
 */
@JvmName("kase4timesKase17InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase17<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17)
  }
}

/**
 * @param others the [Kase18] to combine with this [Kase4]
 * @return a list of [Kase22]s from the cartesian product of this [Kase4] and the given [Kase18].
 * @since 0.1.0
 */
@JvmName("kase4timesKase18")
public operator fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>
): List<Kase22<A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
    kase(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
  }
}

/**
 * @param others the [Kase18] to combine with this [Kase4]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase22]s from the cartesian product of this [Kase4] and the given [Kase18].
 * @since 0.5.0
 */
@JvmName("kase4timesKase18InstanceFactory")
public inline fun <A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, T> Iterable<Kase4<A1, A2, A3, A4>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>,
  instanceFactory: (A1, A2, A3, A4, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18) -> T
): List<T> = flatMap { (a1, a2, a3, a4) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
    instanceFactory(a1, a2, a3, a4, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
  }
}
