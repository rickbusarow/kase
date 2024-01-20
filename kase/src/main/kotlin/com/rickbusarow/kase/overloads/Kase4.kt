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

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase4<out A1, out A2, out A3, out A4>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  override val a4: A4,
  displayNameFactory: KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase4<A1, A2, A3, A4> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase4<out A1, out A2, out A3, out A4>(
  a1: A1, a2: A2, a3: A3, a4: A4,
  displayNameFactory: KaseDisplayNameFactory<Kase4<A1, A2, A3, A4>>
) : AbstractKase4<A1, A2, A3, A4>(a1 = a1, a2 = a2, a3 = a3, a4 = a4, displayNameFactory = displayNameFactory) {

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
 * @param others the [Kase3] to combine with this [Kase1]
 * @return a list of [Kase4]s from the cartesian product of this [Kase1] and the given [Kase3].
 * @since 0.1.0
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
 * @param others the [Kase3] to combine with this [Kase1]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase4]s from the cartesian product of this [Kase1] and the given [Kase3].
 * @since 0.5.0
 */
@JvmName("kase1timesKase3InstanceFactory")
public inline fun <A1, B1, B2, B3, T> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase3<B1, B2, B3>>,
  instanceFactory: (A1, B1, B2, B3) -> T
): List<T> = flatMap { (a1) ->
  others.map { (b1, b2, b3) ->
    instanceFactory(a1, b1, b2, b3)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase2]
 * @return a list of [Kase4]s from the cartesian product of this [Kase2] and the given [Kase2].
 * @since 0.1.0
 */
@JvmName("kase2timesKase2")
public operator fun <A1, A2, B1, B2> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase4<A1, A2, B1, B2>> = flatMap { (a1, a2) ->
  others.map { (b1, b2) ->
    kase(a1, a2, b1, b2)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase2]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase4]s from the cartesian product of this [Kase2] and the given [Kase2].
 * @since 0.5.0
 */
@JvmName("kase2timesKase2InstanceFactory")
public inline fun <A1, A2, B1, B2, T> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase2<B1, B2>>,
  instanceFactory: (A1, A2, B1, B2) -> T
): List<T> = flatMap { (a1, a2) ->
  others.map { (b1, b2) ->
    instanceFactory(a1, a2, b1, b2)
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
