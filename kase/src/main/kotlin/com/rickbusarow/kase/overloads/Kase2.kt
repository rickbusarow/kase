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
 * A strongly typed version of [Kase] for 2 parameters.
 *
 * @since 0.1.0
 */
public interface Kase2<out A1, out A2> : Kase1<A1> {

  /** The 2nd parameter. */
  public val a2: A2

  /** @see Kase2.a2 */
  public operator fun component2(): A2 = a2
}

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase2<out A1, out A2>(
  override val a1: A1,
  override val a2: A2,
  displayNameFactory: KaseDisplayNameFactory<Kase2<A1, A2>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase2<A1, A2> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase2<out A1, out A2>(
  a1: A1, a2: A2,
  displayNameFactory: KaseDisplayNameFactory<Kase2<A1, A2>>
) : AbstractKase2<A1, A2>(a1 = a1, a2 = a2, displayNameFactory = displayNameFactory) {

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
}

/**
 * Returns a list of [Kase2]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase2]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>
> KaseMatrix.kases(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  displayNameFactory: KaseDisplayNameFactory<Kase2<A1, A2>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value}"
  }
): List<Kase2<A1, A2>> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        add(kase(a1 = a1, a2 = a2, displayNameFactory = displayNameFactory))
      }
    }
  }
}

/**
 * Returns a list of [Kase2]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase2]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  T : Kase2<A1, A2>
> KaseMatrix.get(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  instanceFactory: (A1, A2) -> T
): List<T> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        add(instanceFactory(a1, a2))
      }
    }
  }
}

private fun <A1, A2> defaultKase2DisplayNameFactory(): KaseDisplayNameFactory<Kase2<A1, A2>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase2.a1] parameter.
 * @param a2 the [Kase2.a2] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2> kase(
  a1: A1, a2: A2,
  displayNameFactory: KaseDisplayNameFactory<Kase2<A1, A2>> = defaultKase2DisplayNameFactory()
): Kase2<A1, A2> = DefaultKase2(
  a1 = a1, a2 = a2,
  displayNameFactory = displayNameFactory
)

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase2.a1] parameter.
 * @param a2 the [Kase2.a2] parameter.
 * @since 0.1.0
 */
public fun <A1, A2> kase(
  displayName: String,
  a1: A1, a2: A2
): Kase2<A1, A2> = DefaultKase2(
  a1 = a1, a2 = a2,
  displayNameFactory = { displayName }
)

/**
 * Returns a list of [Kase2]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase2.a1] parameter.
 * @param args2 values mapped to the [Kase2.a2] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase2]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  displayNameFactory: KaseDisplayNameFactory<Kase2<A1, A2>> = defaultKase2DisplayNameFactory()
): List<Kase2<A1, A2>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        add(kase(a1 = a1, a2 = a2, displayNameFactory = displayNameFactory))
      }
    }
  }
}

/**
 * Returns a sequence of [Kase2]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase2.a1] parameter.
 * @param args2 values mapped to the [Kase2.a2] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase2]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2> kases(
  args1: Sequence<A1>,
  args2: Sequence<A2>,
  displayNameFactory: KaseDisplayNameFactory<Kase2<A1, A2>> = defaultKase2DisplayNameFactory()
): Sequence<Kase2<A1, A2>> {
  return sequence {
    for (a1 in args1) {
      for (a2 in args2) {
        yield(kase(a1 = a1, a2 = a2, displayNameFactory = displayNameFactory))
      }
    }
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase1]
 * @return a list of [Kase2]s from the cartesian product of this [Kase1] and the given [Kase1].
 * @since 0.1.0
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
 * @param others the [Kase1] to combine with this [Kase1]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase2]s from the cartesian product of this [Kase1] and the given [Kase1].
 * @since 0.5.0
 */
@JvmName("kase1timesKase1InstanceFactory")
public inline fun <A1, B1, T> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase1<B1>>,
  instanceFactory: (A1, B1) -> T
): List<T> = flatMap { (a1) ->
  others.map { (b1) ->
    instanceFactory(a1, b1)
  }
}
