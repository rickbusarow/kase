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

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase3<out A1, out A2, out A3>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  displayNameFactory: KaseDisplayNameFactory<Kase3<A1, A2, A3>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase3<A1, A2, A3> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase3<out A1, out A2, out A3>(
  a1: A1, a2: A2, a3: A3,
  displayNameFactory: KaseDisplayNameFactory<Kase3<A1, A2, A3>>
) : AbstractKase3<A1, A2, A3>(a1 = a1, a2 = a2, a3 = a3, displayNameFactory = displayNameFactory) {

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
