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
 * A strongly typed version of [Kase] for 22 parameters.
 *
 * @since 0.1.0
 */
public interface Kase22<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20, out A21, out A22> : Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> {

  /** The 22nd parameter. */
  public val a22: A22

  /** @see Kase22.a22 */
  public operator fun component22(): A22 = a22
}

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase22<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20, out A21, out A22>(
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
  override val a12: A12,
  override val a13: A13,
  override val a14: A14,
  override val a15: A15,
  override val a16: A16,
  override val a17: A17,
  override val a18: A18,
  override val a19: A19,
  override val a20: A20,
  override val a21: A21,
  override val a22: A22,
  displayNameFactory: KaseDisplayNameFactory<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase22<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20, out A21, out A22>(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21, a22: A22,
  displayNameFactory: KaseDisplayNameFactory<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>>
) : AbstractKase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22, displayNameFactory = displayNameFactory) {

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
  override operator fun component12(): A12 = a12
  override operator fun component13(): A13 = a13
  override operator fun component14(): A14 = a14
  override operator fun component15(): A15 = a15
  override operator fun component16(): A16 = a16
  override operator fun component17(): A17 = a17
  override operator fun component18(): A18 = a18
  override operator fun component19(): A19 = a19
  override operator fun component20(): A20 = a20
  override operator fun component21(): A21 = a21
  override operator fun component22(): A22 = a22
}

/**
 * Returns a list of [Kase22]s from this [KaseMatrix] for the given keys.
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
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @param a17Key the key for the 17th parameter.
 * @param a18Key the key for the 18th parameter.
 * @param a19Key the key for the 19th parameter.
 * @param a20Key the key for the 20th parameter.
 * @param a21Key the key for the 21st parameter.
 * @param a22Key the key for the 22nd parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase22]s from this [KaseMatrix] for the given keys.
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
  reified A12 : KaseMatrixElement<*>,
  reified A13 : KaseMatrixElement<*>,
  reified A14 : KaseMatrixElement<*>,
  reified A15 : KaseMatrixElement<*>,
  reified A16 : KaseMatrixElement<*>,
  reified A17 : KaseMatrixElement<*>,
  reified A18 : KaseMatrixElement<*>,
  reified A19 : KaseMatrixElement<*>,
  reified A20 : KaseMatrixElement<*>,
  reified A21 : KaseMatrixElement<*>,
  reified A22 : KaseMatrixElement<*>
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
  a12Key: KaseMatrixKey<A12>,
  a13Key: KaseMatrixKey<A13>,
  a14Key: KaseMatrixKey<A14>,
  a15Key: KaseMatrixKey<A15>,
  a16Key: KaseMatrixKey<A16>,
  a17Key: KaseMatrixKey<A17>,
  a18Key: KaseMatrixKey<A18>,
  a19Key: KaseMatrixKey<A19>,
  a20Key: KaseMatrixKey<A20>,
  a21Key: KaseMatrixKey<A21>,
  a22Key: KaseMatrixKey<A22>,
  displayNameFactory: KaseDisplayNameFactory<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value} | ${a3.label}: ${a3.value} | ${a4.label}: ${a4.value} | ${a5.label}: ${a5.value} | ${a6.label}: ${a6.value} | ${a7.label}: ${a7.value} | ${a8.label}: ${a8.value} | ${a9.label}: ${a9.value} | ${a10.label}: ${a10.value} | ${a11.label}: ${a11.value} | ${a12.label}: ${a12.value} | ${a13.label}: ${a13.value} | ${a14.label}: ${a14.value} | ${a15.label}: ${a15.value} | ${a16.label}: ${a16.value} | ${a17.label}: ${a17.value} | ${a18.label}: ${a18.value} | ${a19.label}: ${a19.value} | ${a20.label}: ${a20.value} | ${a21.label}: ${a21.value} | ${a22.label}: ${a22.value}"
  }
): List<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> {
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
                          for (a12 in get(a12Key)) {
                            for (a13 in get(a13Key)) {
                              for (a14 in get(a14Key)) {
                                for (a15 in get(a15Key)) {
                                  for (a16 in get(a16Key)) {
                                    for (a17 in get(a17Key)) {
                                      for (a18 in get(a18Key)) {
                                        for (a19 in get(a19Key)) {
                                          for (a20 in get(a20Key)) {
                                            for (a21 in get(a21Key)) {
                                              for (a22 in get(a22Key)) {
                                                add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22, displayNameFactory = displayNameFactory))
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
 * Returns a list of [Kase22]s from this [KaseMatrix] for the given keys.
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
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @param a17Key the key for the 17th parameter.
 * @param a18Key the key for the 18th parameter.
 * @param a19Key the key for the 19th parameter.
 * @param a20Key the key for the 20th parameter.
 * @param a21Key the key for the 21st parameter.
 * @param a22Key the key for the 22nd parameter.
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase22]s from this [KaseMatrix] for the given keys.
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
  reified A12 : KaseMatrixElement<*>,
  reified A13 : KaseMatrixElement<*>,
  reified A14 : KaseMatrixElement<*>,
  reified A15 : KaseMatrixElement<*>,
  reified A16 : KaseMatrixElement<*>,
  reified A17 : KaseMatrixElement<*>,
  reified A18 : KaseMatrixElement<*>,
  reified A19 : KaseMatrixElement<*>,
  reified A20 : KaseMatrixElement<*>,
  reified A21 : KaseMatrixElement<*>,
  reified A22 : KaseMatrixElement<*>,
  T : Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>
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
  a12Key: KaseMatrixKey<A12>,
  a13Key: KaseMatrixKey<A13>,
  a14Key: KaseMatrixKey<A14>,
  a15Key: KaseMatrixKey<A15>,
  a16Key: KaseMatrixKey<A16>,
  a17Key: KaseMatrixKey<A17>,
  a18Key: KaseMatrixKey<A18>,
  a19Key: KaseMatrixKey<A19>,
  a20Key: KaseMatrixKey<A20>,
  a21Key: KaseMatrixKey<A21>,
  a22Key: KaseMatrixKey<A22>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) -> T
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
                          for (a12 in get(a12Key)) {
                            for (a13 in get(a13Key)) {
                              for (a14 in get(a14Key)) {
                                for (a15 in get(a15Key)) {
                                  for (a16 in get(a16Key)) {
                                    for (a17 in get(a17Key)) {
                                      for (a18 in get(a18Key)) {
                                        for (a19 in get(a19Key)) {
                                          for (a20 in get(a20Key)) {
                                            for (a21 in get(a21Key)) {
                                              for (a22 in get(a22Key)) {
                                                add(instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22))
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

private fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> defaultKase22DisplayNameFactory(): KaseDisplayNameFactory<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3 | a4: $a4 | a5: $a5 | a6: $a6 | a7: $a7 | a8: $a8 | a9: $a9 | a10: $a10 | a11: $a11 | a12: $a12 | a13: $a13 | a14: $a14 | a15: $a15 | a16: $a16 | a17: $a17 | a18: $a18 | a19: $a19 | a20: $a20 | a21: $a21 | a22: $a22"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase22.a1] parameter.
 * @param a2 the [Kase22.a2] parameter.
 * @param a3 the [Kase22.a3] parameter.
 * @param a4 the [Kase22.a4] parameter.
 * @param a5 the [Kase22.a5] parameter.
 * @param a6 the [Kase22.a6] parameter.
 * @param a7 the [Kase22.a7] parameter.
 * @param a8 the [Kase22.a8] parameter.
 * @param a9 the [Kase22.a9] parameter.
 * @param a10 the [Kase22.a10] parameter.
 * @param a11 the [Kase22.a11] parameter.
 * @param a12 the [Kase22.a12] parameter.
 * @param a13 the [Kase22.a13] parameter.
 * @param a14 the [Kase22.a14] parameter.
 * @param a15 the [Kase22.a15] parameter.
 * @param a16 the [Kase22.a16] parameter.
 * @param a17 the [Kase22.a17] parameter.
 * @param a18 the [Kase22.a18] parameter.
 * @param a19 the [Kase22.a19] parameter.
 * @param a20 the [Kase22.a20] parameter.
 * @param a21 the [Kase22.a21] parameter.
 * @param a22 the [Kase22.a22] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21, a22: A22,
  displayNameFactory: KaseDisplayNameFactory<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> = defaultKase22DisplayNameFactory()
): Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> = DefaultKase22(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22,
  displayNameFactory = displayNameFactory
)

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase22.a1] parameter.
 * @param a2 the [Kase22.a2] parameter.
 * @param a3 the [Kase22.a3] parameter.
 * @param a4 the [Kase22.a4] parameter.
 * @param a5 the [Kase22.a5] parameter.
 * @param a6 the [Kase22.a6] parameter.
 * @param a7 the [Kase22.a7] parameter.
 * @param a8 the [Kase22.a8] parameter.
 * @param a9 the [Kase22.a9] parameter.
 * @param a10 the [Kase22.a10] parameter.
 * @param a11 the [Kase22.a11] parameter.
 * @param a12 the [Kase22.a12] parameter.
 * @param a13 the [Kase22.a13] parameter.
 * @param a14 the [Kase22.a14] parameter.
 * @param a15 the [Kase22.a15] parameter.
 * @param a16 the [Kase22.a16] parameter.
 * @param a17 the [Kase22.a17] parameter.
 * @param a18 the [Kase22.a18] parameter.
 * @param a19 the [Kase22.a19] parameter.
 * @param a20 the [Kase22.a20] parameter.
 * @param a21 the [Kase22.a21] parameter.
 * @param a22 the [Kase22.a22] parameter.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21, a22: A22
): Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> = DefaultKase22(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22,
  displayNameFactory = { displayName }
)

/**
 * Returns a list of [Kase22]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase22.a1] parameter.
 * @param args2 values mapped to the [Kase22.a2] parameter.
 * @param args3 values mapped to the [Kase22.a3] parameter.
 * @param args4 values mapped to the [Kase22.a4] parameter.
 * @param args5 values mapped to the [Kase22.a5] parameter.
 * @param args6 values mapped to the [Kase22.a6] parameter.
 * @param args7 values mapped to the [Kase22.a7] parameter.
 * @param args8 values mapped to the [Kase22.a8] parameter.
 * @param args9 values mapped to the [Kase22.a9] parameter.
 * @param args10 values mapped to the [Kase22.a10] parameter.
 * @param args11 values mapped to the [Kase22.a11] parameter.
 * @param args12 values mapped to the [Kase22.a12] parameter.
 * @param args13 values mapped to the [Kase22.a13] parameter.
 * @param args14 values mapped to the [Kase22.a14] parameter.
 * @param args15 values mapped to the [Kase22.a15] parameter.
 * @param args16 values mapped to the [Kase22.a16] parameter.
 * @param args17 values mapped to the [Kase22.a17] parameter.
 * @param args18 values mapped to the [Kase22.a18] parameter.
 * @param args19 values mapped to the [Kase22.a19] parameter.
 * @param args20 values mapped to the [Kase22.a20] parameter.
 * @param args21 values mapped to the [Kase22.a21] parameter.
 * @param args22 values mapped to the [Kase22.a22] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase22]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> kases(
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
  args12: Iterable<A12>,
  args13: Iterable<A13>,
  args14: Iterable<A14>,
  args15: Iterable<A15>,
  args16: Iterable<A16>,
  args17: Iterable<A17>,
  args18: Iterable<A18>,
  args19: Iterable<A19>,
  args20: Iterable<A20>,
  args21: Iterable<A21>,
  args22: Iterable<A22>,
  displayNameFactory: KaseDisplayNameFactory<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> = defaultKase22DisplayNameFactory()
): List<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> {
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
                          for (a12 in args12) {
                            for (a13 in args13) {
                              for (a14 in args14) {
                                for (a15 in args15) {
                                  for (a16 in args16) {
                                    for (a17 in args17) {
                                      for (a18 in args18) {
                                        for (a19 in args19) {
                                          for (a20 in args20) {
                                            for (a21 in args21) {
                                              for (a22 in args22) {
                                                add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22, displayNameFactory = displayNameFactory))
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
 * Returns a sequence of [Kase22]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase22.a1] parameter.
 * @param args2 values mapped to the [Kase22.a2] parameter.
 * @param args3 values mapped to the [Kase22.a3] parameter.
 * @param args4 values mapped to the [Kase22.a4] parameter.
 * @param args5 values mapped to the [Kase22.a5] parameter.
 * @param args6 values mapped to the [Kase22.a6] parameter.
 * @param args7 values mapped to the [Kase22.a7] parameter.
 * @param args8 values mapped to the [Kase22.a8] parameter.
 * @param args9 values mapped to the [Kase22.a9] parameter.
 * @param args10 values mapped to the [Kase22.a10] parameter.
 * @param args11 values mapped to the [Kase22.a11] parameter.
 * @param args12 values mapped to the [Kase22.a12] parameter.
 * @param args13 values mapped to the [Kase22.a13] parameter.
 * @param args14 values mapped to the [Kase22.a14] parameter.
 * @param args15 values mapped to the [Kase22.a15] parameter.
 * @param args16 values mapped to the [Kase22.a16] parameter.
 * @param args17 values mapped to the [Kase22.a17] parameter.
 * @param args18 values mapped to the [Kase22.a18] parameter.
 * @param args19 values mapped to the [Kase22.a19] parameter.
 * @param args20 values mapped to the [Kase22.a20] parameter.
 * @param args21 values mapped to the [Kase22.a21] parameter.
 * @param args22 values mapped to the [Kase22.a22] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase22]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> kases(
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
  args12: Sequence<A12>,
  args13: Sequence<A13>,
  args14: Sequence<A14>,
  args15: Sequence<A15>,
  args16: Sequence<A16>,
  args17: Sequence<A17>,
  args18: Sequence<A18>,
  args19: Sequence<A19>,
  args20: Sequence<A20>,
  args21: Sequence<A21>,
  args22: Sequence<A22>,
  displayNameFactory: KaseDisplayNameFactory<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> = defaultKase22DisplayNameFactory()
): Sequence<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> {
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
                          for (a12 in args12) {
                            for (a13 in args13) {
                              for (a14 in args14) {
                                for (a15 in args15) {
                                  for (a16 in args16) {
                                    for (a17 in args17) {
                                      for (a18 in args18) {
                                        for (a19 in args19) {
                                          for (a20 in args20) {
                                            for (a21 in args21) {
                                              for (a22 in args22) {
                                                yield(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22, displayNameFactory = displayNameFactory))
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
