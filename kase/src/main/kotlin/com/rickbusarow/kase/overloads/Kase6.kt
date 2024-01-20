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
 * A strongly typed version of [Kase] for 6 parameters.
 *
 * @since 0.1.0
 */
public interface Kase6<out A1, out A2, out A3, out A4, out A5, out A6> : Kase5<A1, A2, A3, A4, A5> {

  /** The 6th parameter. */
  public val a6: A6

  /** @see Kase6.a6 */
  public operator fun component6(): A6 = a6
}

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase6<out A1, out A2, out A3, out A4, out A5, out A6>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  override val a4: A4,
  override val a5: A5,
  override val a6: A6,
  displayNameFactory: KaseDisplayNameFactory<Kase6<A1, A2, A3, A4, A5, A6>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase6<A1, A2, A3, A4, A5, A6> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase6<out A1, out A2, out A3, out A4, out A5, out A6>(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6,
  displayNameFactory: KaseDisplayNameFactory<Kase6<A1, A2, A3, A4, A5, A6>>
) : AbstractKase6<A1, A2, A3, A4, A5, A6>(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, displayNameFactory = displayNameFactory) {

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
  override operator fun component4(): A4 = a4
  override operator fun component5(): A5 = a5
  override operator fun component6(): A6 = a6
}

/**
 * Returns a list of [Kase6]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase6]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>,
  reified A4 : KaseMatrixElement<*>,
  reified A5 : KaseMatrixElement<*>,
  reified A6 : KaseMatrixElement<*>
> KaseMatrix.kases(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  a5Key: KaseMatrixKey<A5>,
  a6Key: KaseMatrixKey<A6>,
  displayNameFactory: KaseDisplayNameFactory<Kase6<A1, A2, A3, A4, A5, A6>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value} | ${a3.label}: ${a3.value} | ${a4.label}: ${a4.value} | ${a5.label}: ${a5.value} | ${a6.label}: ${a6.value}"
  }
): List<Kase6<A1, A2, A3, A4, A5, A6>> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            for (a5 in get(a5Key)) {
              for (a6 in get(a6Key)) {
                add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, displayNameFactory = displayNameFactory))
              }
            }
          }
        }
      }
    }
  }
}

/**
 * Returns a list of [Kase6]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase6]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>,
  reified A4 : KaseMatrixElement<*>,
  reified A5 : KaseMatrixElement<*>,
  reified A6 : KaseMatrixElement<*>,
  T : Kase6<A1, A2, A3, A4, A5, A6>
> KaseMatrix.get(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  a5Key: KaseMatrixKey<A5>,
  a6Key: KaseMatrixKey<A6>,
  instanceFactory: (A1, A2, A3, A4, A5, A6) -> T
): List<T> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            for (a5 in get(a5Key)) {
              for (a6 in get(a6Key)) {
                add(instanceFactory(a1, a2, a3, a4, a5, a6))
              }
            }
          }
        }
      }
    }
  }
}

private fun <A1, A2, A3, A4, A5, A6> defaultKase6DisplayNameFactory(): KaseDisplayNameFactory<Kase6<A1, A2, A3, A4, A5, A6>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3 | a4: $a4 | a5: $a5 | a6: $a6"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase6.a1] parameter.
 * @param a2 the [Kase6.a2] parameter.
 * @param a3 the [Kase6.a3] parameter.
 * @param a4 the [Kase6.a4] parameter.
 * @param a5 the [Kase6.a5] parameter.
 * @param a6 the [Kase6.a6] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6,
  displayNameFactory: KaseDisplayNameFactory<Kase6<A1, A2, A3, A4, A5, A6>> = defaultKase6DisplayNameFactory()
): Kase6<A1, A2, A3, A4, A5, A6> = DefaultKase6(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6,
  displayNameFactory = displayNameFactory
)

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase6.a1] parameter.
 * @param a2 the [Kase6.a2] parameter.
 * @param a3 the [Kase6.a3] parameter.
 * @param a4 the [Kase6.a4] parameter.
 * @param a5 the [Kase6.a5] parameter.
 * @param a6 the [Kase6.a6] parameter.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6
): Kase6<A1, A2, A3, A4, A5, A6> = DefaultKase6(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6,
  displayNameFactory = { displayName }
)

/**
 * Returns a list of [Kase6]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase6.a1] parameter.
 * @param args2 values mapped to the [Kase6.a2] parameter.
 * @param args3 values mapped to the [Kase6.a3] parameter.
 * @param args4 values mapped to the [Kase6.a4] parameter.
 * @param args5 values mapped to the [Kase6.a5] parameter.
 * @param args6 values mapped to the [Kase6.a6] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase6]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  displayNameFactory: KaseDisplayNameFactory<Kase6<A1, A2, A3, A4, A5, A6>> = defaultKase6DisplayNameFactory()
): List<Kase6<A1, A2, A3, A4, A5, A6>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, displayNameFactory = displayNameFactory))
              }
            }
          }
        }
      }
    }
  }
}

/**
 * Returns a sequence of [Kase6]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase6.a1] parameter.
 * @param args2 values mapped to the [Kase6.a2] parameter.
 * @param args3 values mapped to the [Kase6.a3] parameter.
 * @param args4 values mapped to the [Kase6.a4] parameter.
 * @param args5 values mapped to the [Kase6.a5] parameter.
 * @param args6 values mapped to the [Kase6.a6] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase6]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6> kases(
  args1: Sequence<A1>,
  args2: Sequence<A2>,
  args3: Sequence<A3>,
  args4: Sequence<A4>,
  args5: Sequence<A5>,
  args6: Sequence<A6>,
  displayNameFactory: KaseDisplayNameFactory<Kase6<A1, A2, A3, A4, A5, A6>> = defaultKase6DisplayNameFactory()
): Sequence<Kase6<A1, A2, A3, A4, A5, A6>> {
  return sequence {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                yield(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, displayNameFactory = displayNameFactory))
              }
            }
          }
        }
      }
    }
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase1]
 * @return a list of [Kase6]s from the cartesian product of this [Kase1] and the given [Kase5].
 * @since 0.1.0
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
 * @param others the [Kase5] to combine with this [Kase1]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase6]s from the cartesian product of this [Kase1] and the given [Kase5].
 * @since 0.5.0
 */
@JvmName("kase1timesKase5InstanceFactory")
public inline fun <A1, B1, B2, B3, B4, B5, T> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>,
  instanceFactory: (A1, B1, B2, B3, B4, B5) -> T
): List<T> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5) ->
    instanceFactory(a1, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase2]
 * @return a list of [Kase6]s from the cartesian product of this [Kase2] and the given [Kase4].
 * @since 0.1.0
 */
@JvmName("kase2timesKase4")
public operator fun <A1, A2, B1, B2, B3, B4> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase6<A1, A2, B1, B2, B3, B4>> = flatMap { (a1, a2) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, a2, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase2]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase6]s from the cartesian product of this [Kase2] and the given [Kase4].
 * @since 0.5.0
 */
@JvmName("kase2timesKase4InstanceFactory")
public inline fun <A1, A2, B1, B2, B3, B4, T> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>,
  instanceFactory: (A1, A2, B1, B2, B3, B4) -> T
): List<T> = flatMap { (a1, a2) ->
  others.map { (b1, b2, b3, b4) ->
    instanceFactory(a1, a2, b1, b2, b3, b4)
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
 * @param others the [Kase1] to combine with this [Kase5]
 * @return a list of [Kase6]s from the cartesian product of this [Kase5] and the given [Kase1].
 * @since 0.1.0
 */
@JvmName("kase5timesKase1")
public operator fun <A1, A2, A3, A4, A5, B1> Iterable<Kase5<A1, A2, A3, A4, A5>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase6<A1, A2, A3, A4, A5, B1>> = flatMap { (a1, a2, a3, a4, a5) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, b1)
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase5]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase6]s from the cartesian product of this [Kase5] and the given [Kase1].
 * @since 0.5.0
 */
@JvmName("kase5timesKase1InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, B1, T> Iterable<Kase5<A1, A2, A3, A4, A5>>.times(
  others: Iterable<Kase1<B1>>,
  instanceFactory: (A1, A2, A3, A4, A5, B1) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5) ->
  others.map { (b1) ->
    instanceFactory(a1, a2, a3, a4, a5, b1)
  }
}
