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
 * A strongly typed version of [Kase] for 20 parameters.
 *
 * @since 0.1.0
 */
public interface Kase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20> : Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> {

  /** The 20th parameter. */
  public val a20: A20

  /** @see Kase20.a20 */
  public operator fun component20(): A20 = a20
}

/**
 * An abstract base type of [Kase] for use with data classes.
 *
 * @since 0.8.0
 */
@Poko
public abstract class AbstractKase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20>(
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
  displayNameFactory: KaseDisplayNameFactory<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> = KaseDisplayNameFactory {
    toString().removeSurrounding("${this::class.simpleName!!}(", ")")
  }
): Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }
}

@PublishedApi
internal class DefaultKase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20>(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20,
  displayNameFactory: KaseDisplayNameFactory<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>>
) : AbstractKase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, displayNameFactory = displayNameFactory) {

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
}

/**
 * Returns a list of [Kase20]s from this [KaseMatrix] for the given keys.
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
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase20]s from this [KaseMatrix] for the given keys.
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
  reified A20 : KaseMatrixElement<*>
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
  displayNameFactory: KaseDisplayNameFactory<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> = KaseDisplayNameFactory {
    "${a1.label}: ${a1.value} | ${a2.label}: ${a2.value} | ${a3.label}: ${a3.value} | ${a4.label}: ${a4.value} | ${a5.label}: ${a5.value} | ${a6.label}: ${a6.value} | ${a7.label}: ${a7.value} | ${a8.label}: ${a8.value} | ${a9.label}: ${a9.value} | ${a10.label}: ${a10.value} | ${a11.label}: ${a11.value} | ${a12.label}: ${a12.value} | ${a13.label}: ${a13.value} | ${a14.label}: ${a14.value} | ${a15.label}: ${a15.value} | ${a16.label}: ${a16.value} | ${a17.label}: ${a17.value} | ${a18.label}: ${a18.value} | ${a19.label}: ${a19.value} | ${a20.label}: ${a20.value}"
  }
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
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
                                            add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, displayNameFactory = displayNameFactory))
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
 * Returns a list of [Kase20]s from this [KaseMatrix] for the given keys.
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
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from this [KaseMatrix] for the given keys.
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
  T : Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>
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
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> T
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
                                            add(instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20))
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

private fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> defaultKase20DisplayNameFactory(): KaseDisplayNameFactory<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3 | a4: $a4 | a5: $a5 | a6: $a6 | a7: $a7 | a8: $a8 | a9: $a9 | a10: $a10 | a11: $a11 | a12: $a12 | a13: $a13 | a14: $a14 | a15: $a15 | a16: $a16 | a17: $a17 | a18: $a18 | a19: $a19 | a20: $a20"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase20.a1] parameter.
 * @param a2 the [Kase20.a2] parameter.
 * @param a3 the [Kase20.a3] parameter.
 * @param a4 the [Kase20.a4] parameter.
 * @param a5 the [Kase20.a5] parameter.
 * @param a6 the [Kase20.a6] parameter.
 * @param a7 the [Kase20.a7] parameter.
 * @param a8 the [Kase20.a8] parameter.
 * @param a9 the [Kase20.a9] parameter.
 * @param a10 the [Kase20.a10] parameter.
 * @param a11 the [Kase20.a11] parameter.
 * @param a12 the [Kase20.a12] parameter.
 * @param a13 the [Kase20.a13] parameter.
 * @param a14 the [Kase20.a14] parameter.
 * @param a15 the [Kase20.a15] parameter.
 * @param a16 the [Kase20.a16] parameter.
 * @param a17 the [Kase20.a17] parameter.
 * @param a18 the [Kase20.a18] parameter.
 * @param a19 the [Kase20.a19] parameter.
 * @param a20 the [Kase20.a20] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20,
  displayNameFactory: KaseDisplayNameFactory<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> = defaultKase20DisplayNameFactory()
): Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> = DefaultKase20(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20,
  displayNameFactory = displayNameFactory
)

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase20.a1] parameter.
 * @param a2 the [Kase20.a2] parameter.
 * @param a3 the [Kase20.a3] parameter.
 * @param a4 the [Kase20.a4] parameter.
 * @param a5 the [Kase20.a5] parameter.
 * @param a6 the [Kase20.a6] parameter.
 * @param a7 the [Kase20.a7] parameter.
 * @param a8 the [Kase20.a8] parameter.
 * @param a9 the [Kase20.a9] parameter.
 * @param a10 the [Kase20.a10] parameter.
 * @param a11 the [Kase20.a11] parameter.
 * @param a12 the [Kase20.a12] parameter.
 * @param a13 the [Kase20.a13] parameter.
 * @param a14 the [Kase20.a14] parameter.
 * @param a15 the [Kase20.a15] parameter.
 * @param a16 the [Kase20.a16] parameter.
 * @param a17 the [Kase20.a17] parameter.
 * @param a18 the [Kase20.a18] parameter.
 * @param a19 the [Kase20.a19] parameter.
 * @param a20 the [Kase20.a20] parameter.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20
): Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> = DefaultKase20(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20,
  displayNameFactory = { displayName }
)

/**
 * Returns a list of [Kase20]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase20.a1] parameter.
 * @param args2 values mapped to the [Kase20.a2] parameter.
 * @param args3 values mapped to the [Kase20.a3] parameter.
 * @param args4 values mapped to the [Kase20.a4] parameter.
 * @param args5 values mapped to the [Kase20.a5] parameter.
 * @param args6 values mapped to the [Kase20.a6] parameter.
 * @param args7 values mapped to the [Kase20.a7] parameter.
 * @param args8 values mapped to the [Kase20.a8] parameter.
 * @param args9 values mapped to the [Kase20.a9] parameter.
 * @param args10 values mapped to the [Kase20.a10] parameter.
 * @param args11 values mapped to the [Kase20.a11] parameter.
 * @param args12 values mapped to the [Kase20.a12] parameter.
 * @param args13 values mapped to the [Kase20.a13] parameter.
 * @param args14 values mapped to the [Kase20.a14] parameter.
 * @param args15 values mapped to the [Kase20.a15] parameter.
 * @param args16 values mapped to the [Kase20.a16] parameter.
 * @param args17 values mapped to the [Kase20.a17] parameter.
 * @param args18 values mapped to the [Kase20.a18] parameter.
 * @param args19 values mapped to the [Kase20.a19] parameter.
 * @param args20 values mapped to the [Kase20.a20] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase20]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kases(
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
  displayNameFactory: KaseDisplayNameFactory<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> = defaultKase20DisplayNameFactory()
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
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
                                            add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, displayNameFactory = displayNameFactory))
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
 * Returns a sequence of [Kase20]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase20.a1] parameter.
 * @param args2 values mapped to the [Kase20.a2] parameter.
 * @param args3 values mapped to the [Kase20.a3] parameter.
 * @param args4 values mapped to the [Kase20.a4] parameter.
 * @param args5 values mapped to the [Kase20.a5] parameter.
 * @param args6 values mapped to the [Kase20.a6] parameter.
 * @param args7 values mapped to the [Kase20.a7] parameter.
 * @param args8 values mapped to the [Kase20.a8] parameter.
 * @param args9 values mapped to the [Kase20.a9] parameter.
 * @param args10 values mapped to the [Kase20.a10] parameter.
 * @param args11 values mapped to the [Kase20.a11] parameter.
 * @param args12 values mapped to the [Kase20.a12] parameter.
 * @param args13 values mapped to the [Kase20.a13] parameter.
 * @param args14 values mapped to the [Kase20.a14] parameter.
 * @param args15 values mapped to the [Kase20.a15] parameter.
 * @param args16 values mapped to the [Kase20.a16] parameter.
 * @param args17 values mapped to the [Kase20.a17] parameter.
 * @param args18 values mapped to the [Kase20.a18] parameter.
 * @param args19 values mapped to the [Kase20.a19] parameter.
 * @param args20 values mapped to the [Kase20.a20] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase20]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kases(
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
  displayNameFactory: KaseDisplayNameFactory<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> = defaultKase20DisplayNameFactory()
): Sequence<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
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
                                            yield(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, displayNameFactory = displayNameFactory))
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
 * @param others the [Kase19] to combine with this [Kase1]
 * @return a list of [Kase20]s from the cartesian product of this [Kase1] and the given [Kase19].
 * @since 0.1.0
 */
@JvmName("kase1timesKase19")
public operator fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase19<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>>
): List<Kase20<A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19) ->
    kase(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19)
  }
}

/**
 * @param others the [Kase19] to combine with this [Kase1]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase1] and the given [Kase19].
 * @since 0.5.0
 */
@JvmName("kase1timesKase19InstanceFactory")
public inline fun <A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, T> Iterable<Kase1<A1>>.times(
  others: Iterable<Kase19<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>>,
  instanceFactory: (A1, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19) -> T
): List<T> = flatMap { (a1) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19) ->
    instanceFactory(a1, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19)
  }
}

/**
 * @param others the [Kase18] to combine with this [Kase2]
 * @return a list of [Kase20]s from the cartesian product of this [Kase2] and the given [Kase18].
 * @since 0.1.0
 */
@JvmName("kase2timesKase18")
public operator fun <A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>
): List<Kase20<A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>> = flatMap { (a1, a2) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
    kase(a1, a2, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
  }
}

/**
 * @param others the [Kase18] to combine with this [Kase2]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase2] and the given [Kase18].
 * @since 0.5.0
 */
@JvmName("kase2timesKase18InstanceFactory")
public inline fun <A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, T> Iterable<Kase2<A1, A2>>.times(
  others: Iterable<Kase18<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>>,
  instanceFactory: (A1, A2, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18) -> T
): List<T> = flatMap { (a1, a2) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18) ->
    instanceFactory(a1, a2, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18)
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
 * @param others the [Kase15] to combine with this [Kase5]
 * @return a list of [Kase20]s from the cartesian product of this [Kase5] and the given [Kase15].
 * @since 0.1.0
 */
@JvmName("kase5timesKase15")
public operator fun <A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> Iterable<Kase5<A1, A2, A3, A4, A5>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>
): List<Kase20<A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>> = flatMap { (a1, a2, a3, a4, a5) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
    kase(a1, a2, a3, a4, a5, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
  }
}

/**
 * @param others the [Kase15] to combine with this [Kase5]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase5] and the given [Kase15].
 * @since 0.5.0
 */
@JvmName("kase5timesKase15InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, T> Iterable<Kase5<A1, A2, A3, A4, A5>>.times(
  others: Iterable<Kase15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>>,
  instanceFactory: (A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) ->
    instanceFactory(a1, a2, a3, a4, a5, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase6]
 * @return a list of [Kase20]s from the cartesian product of this [Kase6] and the given [Kase14].
 * @since 0.1.0
 */
@JvmName("kase6timesKase14")
public operator fun <A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> Iterable<Kase6<A1, A2, A3, A4, A5, A6>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>
): List<Kase20<A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>> = flatMap { (a1, a2, a3, a4, a5, a6) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    kase(a1, a2, a3, a4, a5, a6, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase14] to combine with this [Kase6]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase6] and the given [Kase14].
 * @since 0.5.0
 */
@JvmName("kase6timesKase14InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, T> Iterable<Kase6<A1, A2, A3, A4, A5, A6>>.times(
  others: Iterable<Kase14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase7]
 * @return a list of [Kase20]s from the cartesian product of this [Kase7] and the given [Kase13].
 * @since 0.1.0
 */
@JvmName("kase7timesKase13")
public operator fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    kase(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase13] to combine with this [Kase7]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase7] and the given [Kase13].
 * @since 0.5.0
 */
@JvmName("kase7timesKase13InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, T> Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>.times(
  others: Iterable<Kase13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase8]
 * @return a list of [Kase20]s from the cartesian product of this [Kase8] and the given [Kase12].
 * @since 0.1.0
 */
@JvmName("kase8timesKase12")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase12] to combine with this [Kase8]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase8] and the given [Kase12].
 * @since 0.5.0
 */
@JvmName("kase8timesKase12InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, T> Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>.times(
  others: Iterable<Kase12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase9]
 * @return a list of [Kase20]s from the cartesian product of this [Kase9] and the given [Kase11].
 * @since 0.1.0
 */
@JvmName("kase9timesKase11")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> Iterable<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase11] to combine with this [Kase9]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase9] and the given [Kase11].
 * @since 0.5.0
 */
@JvmName("kase9timesKase11InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, T> Iterable<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>>.times(
  others: Iterable<Kase11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase10]
 * @return a list of [Kase20]s from the cartesian product of this [Kase10] and the given [Kase10].
 * @since 0.1.0
 */
@JvmName("kase10timesKase10")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase10] to combine with this [Kase10]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase10] and the given [Kase10].
 * @since 0.5.0
 */
@JvmName("kase10timesKase10InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, T> Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>.times(
  others: Iterable<Kase10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase11]
 * @return a list of [Kase20]s from the cartesian product of this [Kase11] and the given [Kase9].
 * @since 0.1.0
 */
@JvmName("kase11timesKase9")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, B1, B2, B3, B4, B5, B6, B7, B8, B9> Iterable<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, B1, B2, B3, B4, B5, B6, B7, B8, B9>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase9] to combine with this [Kase11]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase11] and the given [Kase9].
 * @since 0.5.0
 */
@JvmName("kase11timesKase9InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, B1, B2, B3, B4, B5, B6, B7, B8, B9, T> Iterable<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>>.times(
  others: Iterable<Kase9<B1, B2, B3, B4, B5, B6, B7, B8, B9>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, B1, B2, B3, B4, B5, B6, B7, B8, B9) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8, b9) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, b1, b2, b3, b4, b5, b6, b7, b8, b9)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase12]
 * @return a list of [Kase20]s from the cartesian product of this [Kase12] and the given [Kase8].
 * @since 0.1.0
 */
@JvmName("kase12timesKase8")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, B1, B2, B3, B4, B5, B6, B7, B8> Iterable<Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, B1, B2, B3, B4, B5, B6, B7, B8>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase8] to combine with this [Kase12]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase12] and the given [Kase8].
 * @since 0.5.0
 */
@JvmName("kase12timesKase8InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, B1, B2, B3, B4, B5, B6, B7, B8, T> Iterable<Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>>.times(
  others: Iterable<Kase8<B1, B2, B3, B4, B5, B6, B7, B8>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, B1, B2, B3, B4, B5, B6, B7, B8) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7, b8) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, b1, b2, b3, b4, b5, b6, b7, b8)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase13]
 * @return a list of [Kase20]s from the cartesian product of this [Kase13] and the given [Kase7].
 * @since 0.1.0
 */
@JvmName("kase13timesKase7")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, B1, B2, B3, B4, B5, B6, B7> Iterable<Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, B1, B2, B3, B4, B5, B6, B7>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase7] to combine with this [Kase13]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase13] and the given [Kase7].
 * @since 0.5.0
 */
@JvmName("kase13timesKase7InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, B1, B2, B3, B4, B5, B6, B7, T> Iterable<Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>>.times(
  others: Iterable<Kase7<B1, B2, B3, B4, B5, B6, B7>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, B1, B2, B3, B4, B5, B6, B7) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) ->
  others.map { (b1, b2, b3, b4, b5, b6, b7) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, b1, b2, b3, b4, b5, b6, b7)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase14]
 * @return a list of [Kase20]s from the cartesian product of this [Kase14] and the given [Kase6].
 * @since 0.1.0
 */
@JvmName("kase14timesKase6")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, B1, B2, B3, B4, B5, B6> Iterable<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, B1, B2, B3, B4, B5, B6>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase6] to combine with this [Kase14]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase14] and the given [Kase6].
 * @since 0.5.0
 */
@JvmName("kase14timesKase6InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, B1, B2, B3, B4, B5, B6, T> Iterable<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>>.times(
  others: Iterable<Kase6<B1, B2, B3, B4, B5, B6>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, B1, B2, B3, B4, B5, B6) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) ->
  others.map { (b1, b2, b3, b4, b5, b6) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, b1, b2, b3, b4, b5, b6)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase15]
 * @return a list of [Kase20]s from the cartesian product of this [Kase15] and the given [Kase5].
 * @since 0.1.0
 */
@JvmName("kase15timesKase5")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, B1, B2, B3, B4, B5> Iterable<Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, B1, B2, B3, B4, B5>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) ->
  others.map { (b1, b2, b3, b4, b5) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase5] to combine with this [Kase15]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase15] and the given [Kase5].
 * @since 0.5.0
 */
@JvmName("kase15timesKase5InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, B1, B2, B3, B4, B5, T> Iterable<Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>>.times(
  others: Iterable<Kase5<B1, B2, B3, B4, B5>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, B1, B2, B3, B4, B5) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) ->
  others.map { (b1, b2, b3, b4, b5) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, b1, b2, b3, b4, b5)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase16]
 * @return a list of [Kase20]s from the cartesian product of this [Kase16] and the given [Kase4].
 * @since 0.1.0
 */
@JvmName("kase16timesKase4")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, B1, B2, B3, B4> Iterable<Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, B1, B2, B3, B4>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase16]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase16] and the given [Kase4].
 * @since 0.5.0
 */
@JvmName("kase16timesKase4InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, B1, B2, B3, B4, T> Iterable<Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, B1, B2, B3, B4) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) ->
  others.map { (b1, b2, b3, b4) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, b1, b2, b3, b4)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase17]
 * @return a list of [Kase20]s from the cartesian product of this [Kase17] and the given [Kase3].
 * @since 0.1.0
 */
@JvmName("kase17timesKase3")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, B1, B2, B3> Iterable<Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, B1, B2, B3>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, b1, b2, b3)
  }
}

/**
 * @param others the [Kase3] to combine with this [Kase17]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase17] and the given [Kase3].
 * @since 0.5.0
 */
@JvmName("kase17timesKase3InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, B1, B2, B3, T> Iterable<Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>>.times(
  others: Iterable<Kase3<B1, B2, B3>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, B1, B2, B3) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) ->
  others.map { (b1, b2, b3) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, b1, b2, b3)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase18]
 * @return a list of [Kase20]s from the cartesian product of this [Kase18] and the given [Kase2].
 * @since 0.1.0
 */
@JvmName("kase18timesKase2")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase2<B1, B2>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1, b2) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1, b2)
  }
}

/**
 * @param others the [Kase2] to combine with this [Kase18]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase18] and the given [Kase2].
 * @since 0.5.0
 */
@JvmName("kase18timesKase2InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2, T> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase2<B1, B2>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1, b2) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1, b2)
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase19]
 * @return a list of [Kase20]s from the cartesian product of this [Kase19] and the given [Kase1].
 * @since 0.1.0
 */
@JvmName("kase19timesKase1")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, B1> Iterable<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, B1>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, b1)
  }
}

/**
 * @param others the [Kase1] to combine with this [Kase19]
 * @param instanceFactory creates a custom Kase instance for each permutation
 * @return a list of [Kase20]s from the cartesian product of this [Kase19] and the given [Kase1].
 * @since 0.5.0
 */
@JvmName("kase19timesKase1InstanceFactory")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, B1, T> Iterable<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>>.times(
  others: Iterable<Kase1<B1>>,
  instanceFactory: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, B1) -> T
): List<T> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) ->
  others.map { (b1) ->
    instanceFactory(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, b1)
  }
}
