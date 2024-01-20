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
 * A strongly typed version of [Kase] for 1 parameter.
 *
 * @since 0.1.0
 */
public interface Kase1<out A1> : Kase {

  /** The 1st parameter. */
  public val a1: A1

  /** @see Kase1.a1 */
  public operator fun component1(): A1 = a1
}

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase1<out A1>(
  override val a1: A1,
  displayNameFactory: KaseDisplayNameFactory<Kase1<A1>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase1<A1> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase1<out A1>(
  a1: A1,
  displayNameFactory: KaseDisplayNameFactory<Kase1<A1>>
) : AbstractKase1<A1>(a1 = a1, displayNameFactory = displayNameFactory) {

  override operator fun component1(): A1 = a1
}

/**
 * Returns a list of [Kase1]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase1]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>
> KaseMatrix.kases(
  a1Key: KaseMatrixKey<A1>,
  displayNameFactory: KaseDisplayNameFactory<Kase1<A1>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value}"
  }
): List<Kase1<A1>> {
  return buildList {
    for (a1 in get(a1Key)) {
      add(kase(a1 = a1, displayNameFactory = displayNameFactory))
    }
  }
}

/**
 * Returns a list of [Kase1]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase1]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  T : Kase1<A1>
> KaseMatrix.get(
  a1Key: KaseMatrixKey<A1>,
  instanceFactory: (A1) -> T
): List<T> {
  return buildList {
    for (a1 in get(a1Key)) {
      add(instanceFactory(a1))
    }
  }
}

private fun <A1> defaultKase1DisplayNameFactory(): KaseDisplayNameFactory<Kase1<A1>> {
  return KaseDisplayNameFactory {
    "a1: $a1"
  }
}

/**
 * Creates a new [Kase] with the given parameter.
 *
 * @param a1 the [Kase1.a1] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1> kase(
  a1: A1,
  displayNameFactory: KaseDisplayNameFactory<Kase1<A1>> = defaultKase1DisplayNameFactory()
): Kase1<A1> = DefaultKase1(
  a1 = a1,
  displayNameFactory = displayNameFactory
)

/**
 * Creates a new [Kase] with the given parameter.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase1.a1] parameter.
 * @since 0.1.0
 */
public fun <A1> kase(
  displayName: String,
  a1: A1
): Kase1<A1> = DefaultKase1(
  a1 = a1,
  displayNameFactory = { displayName }
)

/**
 * Returns a list of [Kase1]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase1.a1] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase1]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1> kases(
  args1: Iterable<A1>,
  displayNameFactory: KaseDisplayNameFactory<Kase1<A1>> = defaultKase1DisplayNameFactory()
): List<Kase1<A1>> {
  return buildList {
    for (a1 in args1) {
      add(kase(a1 = a1, displayNameFactory = displayNameFactory))
    }
  }
}

/**
 * Returns a sequence of [Kase1]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase1.a1] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase1]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1> kases(
  args1: Sequence<A1>,
  displayNameFactory: KaseDisplayNameFactory<Kase1<A1>> = defaultKase1DisplayNameFactory()
): Sequence<Kase1<A1>> {
  return sequence {
    for (a1 in args1) {
      yield(kase(a1 = a1, displayNameFactory = displayNameFactory))
    }
  }
}
