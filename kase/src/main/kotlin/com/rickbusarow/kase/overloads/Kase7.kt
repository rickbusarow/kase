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
 * A strongly typed version of [Kase] for 7 parameters.
 *
 * @since 0.1.0
 */
public interface Kase7<out A1, out A2, out A3, out A4, out A5, out A6, out A7> : Kase6<A1, A2, A3, A4, A5, A6> {

  /** The 7th parameter. */
  public val a7: A7

  /** @see Kase7.a7 */
  public operator fun component7(): A7 = a7
}

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase7<out A1, out A2, out A3, out A4, out A5, out A6, out A7>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  override val a4: A4,
  override val a5: A5,
  override val a6: A6,
  override val a7: A7,
  displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase7<A1, A2, A3, A4, A5, A6, A7> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase7<out A1, out A2, out A3, out A4, out A5, out A6, out A7>(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7,
  displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>>
) : AbstractKase7<A1, A2, A3, A4, A5, A6, A7>(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, displayNameFactory = displayNameFactory) {

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
  override operator fun component4(): A4 = a4
  override operator fun component5(): A5 = a5
  override operator fun component6(): A6 = a6
  override operator fun component7(): A7 = a7
}

/**
 * Returns a list of [Kase7]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase7]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>,
  reified A4 : KaseMatrixElement<*>,
  reified A5 : KaseMatrixElement<*>,
  reified A6 : KaseMatrixElement<*>,
  reified A7 : KaseMatrixElement<*>
> KaseMatrix.kases(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  a5Key: KaseMatrixKey<A5>,
  a6Key: KaseMatrixKey<A6>,
  a7Key: KaseMatrixKey<A7>,
  displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value} | ${a3.label}: ${a3.value} | ${a4.label}: ${a4.value} | ${a5.label}: ${a5.value} | ${a6.label}: ${a6.value} | ${a7.label}: ${a7.value}"
  }
): List<Kase7<A1, A2, A3, A4, A5, A6, A7>> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            for (a5 in get(a5Key)) {
              for (a6 in get(a6Key)) {
                for (a7 in get(a7Key)) {
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
 * Returns a list of [Kase7]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase7]s from this [KaseMatrix] for the given keys.
 * @since 0.5.0
 */
public inline fun <
  reified A1 : KaseMatrixElement<*>,
  reified A2 : KaseMatrixElement<*>,
  reified A3 : KaseMatrixElement<*>,
  reified A4 : KaseMatrixElement<*>,
  reified A5 : KaseMatrixElement<*>,
  reified A6 : KaseMatrixElement<*>,
  reified A7 : KaseMatrixElement<*>,
  T : Kase7<A1, A2, A3, A4, A5, A6, A7>
> KaseMatrix.get(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  a5Key: KaseMatrixKey<A5>,
  a6Key: KaseMatrixKey<A6>,
  a7Key: KaseMatrixKey<A7>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7) -> T
): List<T> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            for (a5 in get(a5Key)) {
              for (a6 in get(a6Key)) {
                for (a7 in get(a7Key)) {
                  add(instanceFactory(a1, a2, a3, a4, a5, a6, a7))
                }
              }
            }
          }
        }
      }
    }
  }
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
 * @since 0.1.0
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
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7
): Kase7<A1, A2, A3, A4, A5, A6, A7> = DefaultKase7(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7,
  displayNameFactory = { displayName }
)

/**
 * Returns a list of [Kase7]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase7.a1] parameter.
 * @param args2 values mapped to the [Kase7.a2] parameter.
 * @param args3 values mapped to the [Kase7.a3] parameter.
 * @param args4 values mapped to the [Kase7.a4] parameter.
 * @param args5 values mapped to the [Kase7.a5] parameter.
 * @param args6 values mapped to the [Kase7.a6] parameter.
 * @param args7 values mapped to the [Kase7.a7] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase7]s from the given parameters.
 * @since 0.1.0
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
 * Returns a sequence of [Kase7]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase7.a1] parameter.
 * @param args2 values mapped to the [Kase7.a2] parameter.
 * @param args3 values mapped to the [Kase7.a3] parameter.
 * @param args4 values mapped to the [Kase7.a4] parameter.
 * @param args5 values mapped to the [Kase7.a5] parameter.
 * @param args6 values mapped to the [Kase7.a6] parameter.
 * @param args7 values mapped to the [Kase7.a7] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase7]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7> kases(
  args1: Sequence<A1>,
  args2: Sequence<A2>,
  args3: Sequence<A3>,
  args4: Sequence<A4>,
  args5: Sequence<A5>,
  args6: Sequence<A6>,
  args7: Sequence<A7>,
  displayNameFactory: KaseDisplayNameFactory<Kase7<A1, A2, A3, A4, A5, A6, A7>> = defaultKase7DisplayNameFactory()
): Sequence<Kase7<A1, A2, A3, A4, A5, A6, A7>> {
  return sequence {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                for (a7 in args7) {
                  yield(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, displayNameFactory = displayNameFactory))
                }
              }
            }
          }
        }
      }
    }
  }
}
