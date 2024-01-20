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
 * A strongly typed version of [Kase] for 11 parameters.
 *
 * @since 0.1.0
 */
public interface Kase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11> : Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {

  /** The 11th parameter. */
  public val a11: A11

  /** @see Kase11.a11 */
  public operator fun component11(): A11 = a11
}

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11>(
  override val a1: A1,
  override val a2: A2,
  override val a3: A3,
  override val a4: A4,
  override val a5: A5,
  override val a6: A6,
  override val a7: A7,
  override val a8: A8,
  override val a9: A9,
  override val a10: A10,
  override val a11: A11,
  displayNameFactory: KaseDisplayNameFactory<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11>(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11,
  displayNameFactory: KaseDisplayNameFactory<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>>
) : AbstractKase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, displayNameFactory = displayNameFactory) {

  override operator fun component1(): A1 = a1
  override operator fun component2(): A2 = a2
  override operator fun component3(): A3 = a3
  override operator fun component4(): A4 = a4
  override operator fun component5(): A5 = a5
  override operator fun component6(): A6 = a6
  override operator fun component7(): A7 = a7
  override operator fun component8(): A8 = a8
  override operator fun component9(): A9 = a9
  override operator fun component10(): A10 = a10
  override operator fun component11(): A11 = a11
}

/**
 * Returns a list of [Kase11]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase11]s from this [KaseMatrix] for the given keys.
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
  reified A8 : KaseMatrixElement<*>,
  reified A9 : KaseMatrixElement<*>,
  reified A10 : KaseMatrixElement<*>,
  reified A11 : KaseMatrixElement<*>
> KaseMatrix.kases(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  a5Key: KaseMatrixKey<A5>,
  a6Key: KaseMatrixKey<A6>,
  a7Key: KaseMatrixKey<A7>,
  a8Key: KaseMatrixKey<A8>,
  a9Key: KaseMatrixKey<A9>,
  a10Key: KaseMatrixKey<A10>,
  a11Key: KaseMatrixKey<A11>,
  displayNameFactory: KaseDisplayNameFactory<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value} | ${a3.label}: ${a3.value} | ${a4.label}: ${a4.value} | ${a5.label}: ${a5.value} | ${a6.label}: ${a6.value} | ${a7.label}: ${a7.value} | ${a8.label}: ${a8.value} | ${a9.label}: ${a9.value} | ${a10.label}: ${a10.value} | ${a11.label}: ${a11.value}"
  }
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            for (a5 in get(a5Key)) {
              for (a6 in get(a6Key)) {
                for (a7 in get(a7Key)) {
                  for (a8 in get(a8Key)) {
                    for (a9 in get(a9Key)) {
                      for (a10 in get(a10Key)) {
                        for (a11 in get(a11Key)) {
                          add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, displayNameFactory = displayNameFactory))
                        }
                      }
                    }
                  }
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
 * Returns a list of [Kase11]s from this [KaseMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from this [KaseMatrix] for the given keys.
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
  reified A8 : KaseMatrixElement<*>,
  reified A9 : KaseMatrixElement<*>,
  reified A10 : KaseMatrixElement<*>,
  reified A11 : KaseMatrixElement<*>,
  T : Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>
> KaseMatrix.get(
  a1Key: KaseMatrixKey<A1>,
  a2Key: KaseMatrixKey<A2>,
  a3Key: KaseMatrixKey<A3>,
  a4Key: KaseMatrixKey<A4>,
  a5Key: KaseMatrixKey<A5>,
  a6Key: KaseMatrixKey<A6>,
  a7Key: KaseMatrixKey<A7>,
  a8Key: KaseMatrixKey<A8>,
  a9Key: KaseMatrixKey<A9>,
  a10Key: KaseMatrixKey<A10>,
  a11Key: KaseMatrixKey<A11>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> T
): List<T> {
  return buildList {
    for (a1 in get(a1Key)) {
      for (a2 in get(a2Key)) {
        for (a3 in get(a3Key)) {
          for (a4 in get(a4Key)) {
            for (a5 in get(a5Key)) {
              for (a6 in get(a6Key)) {
                for (a7 in get(a7Key)) {
                  for (a8 in get(a8Key)) {
                    for (a9 in get(a9Key)) {
                      for (a10 in get(a10Key)) {
                        for (a11 in get(a11Key)) {
                          add(instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11))
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

private fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> defaultKase11DisplayNameFactory(): KaseDisplayNameFactory<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3 | a4: $a4 | a5: $a5 | a6: $a6 | a7: $a7 | a8: $a8 | a9: $a9 | a10: $a10 | a11: $a11"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase11.a1] parameter.
 * @param a2 the [Kase11.a2] parameter.
 * @param a3 the [Kase11.a3] parameter.
 * @param a4 the [Kase11.a4] parameter.
 * @param a5 the [Kase11.a5] parameter.
 * @param a6 the [Kase11.a6] parameter.
 * @param a7 the [Kase11.a7] parameter.
 * @param a8 the [Kase11.a8] parameter.
 * @param a9 the [Kase11.a9] parameter.
 * @param a10 the [Kase11.a10] parameter.
 * @param a11 the [Kase11.a11] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11,
  displayNameFactory: KaseDisplayNameFactory<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> = defaultKase11DisplayNameFactory()
): Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> = DefaultKase11(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11,
  displayNameFactory = displayNameFactory
)

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase11.a1] parameter.
 * @param a2 the [Kase11.a2] parameter.
 * @param a3 the [Kase11.a3] parameter.
 * @param a4 the [Kase11.a4] parameter.
 * @param a5 the [Kase11.a5] parameter.
 * @param a6 the [Kase11.a6] parameter.
 * @param a7 the [Kase11.a7] parameter.
 * @param a8 the [Kase11.a8] parameter.
 * @param a9 the [Kase11.a9] parameter.
 * @param a10 the [Kase11.a10] parameter.
 * @param a11 the [Kase11.a11] parameter.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11
): Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> = DefaultKase11(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11,
  displayNameFactory = { displayName }
)

/**
 * Returns a list of [Kase11]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase11.a1] parameter.
 * @param args2 values mapped to the [Kase11.a2] parameter.
 * @param args3 values mapped to the [Kase11.a3] parameter.
 * @param args4 values mapped to the [Kase11.a4] parameter.
 * @param args5 values mapped to the [Kase11.a5] parameter.
 * @param args6 values mapped to the [Kase11.a6] parameter.
 * @param args7 values mapped to the [Kase11.a7] parameter.
 * @param args8 values mapped to the [Kase11.a8] parameter.
 * @param args9 values mapped to the [Kase11.a9] parameter.
 * @param args10 values mapped to the [Kase11.a10] parameter.
 * @param args11 values mapped to the [Kase11.a11] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase11]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>,
  args9: Iterable<A9>,
  args10: Iterable<A10>,
  args11: Iterable<A11>,
  displayNameFactory: KaseDisplayNameFactory<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> = defaultKase11DisplayNameFactory()
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                for (a7 in args7) {
                  for (a8 in args8) {
                    for (a9 in args9) {
                      for (a10 in args10) {
                        for (a11 in args11) {
                          add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, displayNameFactory = displayNameFactory))
                        }
                      }
                    }
                  }
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
 * Returns a sequence of [Kase11]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase11.a1] parameter.
 * @param args2 values mapped to the [Kase11.a2] parameter.
 * @param args3 values mapped to the [Kase11.a3] parameter.
 * @param args4 values mapped to the [Kase11.a4] parameter.
 * @param args5 values mapped to the [Kase11.a5] parameter.
 * @param args6 values mapped to the [Kase11.a6] parameter.
 * @param args7 values mapped to the [Kase11.a7] parameter.
 * @param args8 values mapped to the [Kase11.a8] parameter.
 * @param args9 values mapped to the [Kase11.a9] parameter.
 * @param args10 values mapped to the [Kase11.a10] parameter.
 * @param args11 values mapped to the [Kase11.a11] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase11]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kases(
  args1: Sequence<A1>,
  args2: Sequence<A2>,
  args3: Sequence<A3>,
  args4: Sequence<A4>,
  args5: Sequence<A5>,
  args6: Sequence<A6>,
  args7: Sequence<A7>,
  args8: Sequence<A8>,
  args9: Sequence<A9>,
  args10: Sequence<A10>,
  args11: Sequence<A11>,
  displayNameFactory: KaseDisplayNameFactory<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> = defaultKase11DisplayNameFactory()
): Sequence<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return sequence {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                for (a7 in args7) {
                  for (a8 in args8) {
                    for (a9 in args9) {
                      for (a10 in args10) {
                        for (a11 in args11) {
                          yield(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, displayNameFactory = displayNameFactory))
                        }
                      }
                    }
                  }
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
 * @param others the [Kase10] to combine with this [Kase1]
 * @return a list of [Kase11]s from the cartesian product of this [Kase1] and the given [Kase10].
 * @since 0.1.0
 */
@JvmName("kase1timesKase10")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>
): List<Kase11<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase1]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase1] and the given [Kase10].
 * @since 0.5.0
 */
@JvmName("kase1timesKase10InstanceFactory")
public inline fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, T> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>,
  instanceFactory: (A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10) -> T
): List<T> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    instanceFactory(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase2]
 * @return a list of [Kase11]s from the cartesian product of this [Kase2] and the given [Kase9].
 * @since 0.1.0
 */
@JvmName("kase2timesKase9")
public operator fun <A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>
): List<Kase11<A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9>> = flatMap { (a1, a2) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    kase(a1, a2, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase2]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase2] and the given [Kase9].
 * @since 0.5.0
 */
@JvmName("kase2timesKase9InstanceFactory")
public inline fun <A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9, T> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>,
  instanceFactory: (A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9) -> T
): List<T> = flatMap { (a1, a2) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    instanceFactory(a1, a2, b1, b2, b3, b4, b5, b6, b7, b8, b9)
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
 * @param others the [Kase6] to combine with this [Kase5]
 * @return a list of [Kase11]s from the cartesian product of this [Kase5] and the given [Kase6].
 * @since 0.1.0
 */
@JvmName("kase5timesKase6")
public operator fun <A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6> Iterable<Kase5<A1, A2, A3, A4, A5>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>
): List<Kase11<A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6>> = flatMap { (a1, a2, a3, a4, a5) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    kase(a1, a2, a3, a4, a5, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase5]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase5] and the given [Kase6].
 * @since 0.5.0
 */
@JvmName("kase5timesKase6InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6, T> Iterable<Kase5<A1, A2, A3, A4, A5>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>,
  instanceFactory: (A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    instanceFactory(a1, a2, a3, a4, a5, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase6]
 * @return a list of [Kase11]s from the cartesian product of this [Kase6] and the given [Kase5].
 * @since 0.1.0
 */
@JvmName("kase6timesKase5")
public operator fun <A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5> Iterable<Kase6<A1, A2, A3, A4, A5, A6>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>
): List<Kase11<A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5>> = flatMap { (a1, a2, a3, a4, a5, a6) ->
  others.map { (b1, b2, b3, b4, b5) ->
    kase(a1, a2, a3, a4, a5, a6, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase6]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase6] and the given [Kase5].
 * @since 0.5.0
 */
@JvmName("kase6timesKase5InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, T> Iterable<Kase6<A1, A2, A3, A4, A5, A6>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6) ->
  others.map { (b1, b2, b3, b4, b5) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase7]
 * @return a list of [Kase11]s from the cartesian product of this [Kase7] and the given [Kase4].
 * @since 0.1.0
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
 * @param others the [Kase4] to combine with this [Kase7]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase7] and the given [Kase4].
 * @since 0.5.0
 */
@JvmName("kase7timesKase4InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, T> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase8]
 * @return a list of [Kase11]s from the cartesian product of this [Kase8] and the given [Kase3].
 * @since 0.1.0
 */
@JvmName("kase8timesKase3")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3> Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, b1, b2, b3)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase8]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase8] and the given [Kase3].
 * @since 0.5.0
 */
@JvmName("kase8timesKase3InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, T> Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>.times(
  others: Iterable<Kase3<B1, B2, B3>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8) ->
  others.map { (b1, b2, b3) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, b1, b2, b3)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase9]
 * @return a list of [Kase11]s from the cartesian product of this [Kase9] and the given [Kase2].
 * @since 0.1.0
 */
@JvmName("kase9timesKase2")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2> Iterable<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
  others.map { (b1, b2) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, b1, b2)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase9]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase9] and the given [Kase2].
 * @since 0.5.0
 */
@JvmName("kase9timesKase2InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2, T> Iterable<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>>.times(
  others: Iterable<Kase2<B1, B2>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
  others.map { (b1, b2) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, b1, b2)
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase10]
 * @return a list of [Kase11]s from the cartesian product of this [Kase10] and the given [Kase1].
 * @since 0.1.0
 */
@JvmName("kase10timesKase1")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1)
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase10]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase11]s from the cartesian product of this [Kase10] and the given [Kase1].
 * @since 0.5.0
 */
@JvmName("kase10timesKase1InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, T> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase1<B1>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1)
  }
}
