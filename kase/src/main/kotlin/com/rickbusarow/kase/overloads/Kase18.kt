/*
 * Copyright (C) 2023 Rick Busarow
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
@file:JvmName("KasesKt")

package com.rickbusarow.kase

import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.internal.KaseInternal
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream

/**
 * A strongly typed version of [Kase] for 18 parameters.
 *
 * @since 0.1.0
 */
public interface Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> : Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17> {

  /** The 18th parameter. */
  public val a18: A18

  /** @see Kase18.a18 */
  public operator fun component18(): A18 = a18
}

@Poko
@PublishedApi
internal class DefaultKase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>(
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
  private val displayNameFactory: KaseDisplayNameFactory<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>
) : Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>, KaseInternal {

  override val displayName: String by lazy(LazyThreadSafetyMode.NONE) {
    with(displayNameFactory) { createDisplayName() }
  }

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
}

private fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> defaultKase18DisplayNameFactory(): KaseDisplayNameFactory<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> {
  return KaseDisplayNameFactory {
    "a1: $a1 | a2: $a2 | a3: $a3 | a4: $a4 | a5: $a5 | a6: $a6 | a7: $a7 | a8: $a8 | a9: $a9 | a10: $a10 | a11: $a11 | a12: $a12 | a13: $a13 | a14: $a14 | a15: $a15 | a16: $a16 | a17: $a17 | a18: $a18"
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase18.a1] parameter.
 * @param a2 the [Kase18.a2] parameter.
 * @param a3 the [Kase18.a3] parameter.
 * @param a4 the [Kase18.a4] parameter.
 * @param a5 the [Kase18.a5] parameter.
 * @param a6 the [Kase18.a6] parameter.
 * @param a7 the [Kase18.a7] parameter.
 * @param a8 the [Kase18.a8] parameter.
 * @param a9 the [Kase18.a9] parameter.
 * @param a10 the [Kase18.a10] parameter.
 * @param a11 the [Kase18.a11] parameter.
 * @param a12 the [Kase18.a12] parameter.
 * @param a13 the [Kase18.a13] parameter.
 * @param a14 the [Kase18.a14] parameter.
 * @param a15 the [Kase18.a15] parameter.
 * @param a16 the [Kase18.a16] parameter.
 * @param a17 the [Kase18.a17] parameter.
 * @param a18 the [Kase18.a18] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18,
  displayNameFactory: KaseDisplayNameFactory<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> = defaultKase18DisplayNameFactory()
): Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> = DefaultKase18(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18,
  displayNameFactory = displayNameFactory
)
/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param displayName the name used in test environments and dynamic tests
 * @param a1 the [Kase18.a1] parameter.
 * @param a2 the [Kase18.a2] parameter.
 * @param a3 the [Kase18.a3] parameter.
 * @param a4 the [Kase18.a4] parameter.
 * @param a5 the [Kase18.a5] parameter.
 * @param a6 the [Kase18.a6] parameter.
 * @param a7 the [Kase18.a7] parameter.
 * @param a8 the [Kase18.a8] parameter.
 * @param a9 the [Kase18.a9] parameter.
 * @param a10 the [Kase18.a10] parameter.
 * @param a11 the [Kase18.a11] parameter.
 * @param a12 the [Kase18.a12] parameter.
 * @param a13 the [Kase18.a13] parameter.
 * @param a14 the [Kase18.a14] parameter.
 * @param a15 the [Kase18.a15] parameter.
 * @param a16 the [Kase18.a16] parameter.
 * @param a17 the [Kase18.a17] parameter.
 * @param a18 the [Kase18.a18] parameter.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kase(
  displayName: String,
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18
): Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> = DefaultKase18(
  a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18,
  displayNameFactory = { displayName }
)
/**
 * Returns a list of [Kase18]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase18.a1] parameter.
 * @param args2 values mapped to the [Kase18.a2] parameter.
 * @param args3 values mapped to the [Kase18.a3] parameter.
 * @param args4 values mapped to the [Kase18.a4] parameter.
 * @param args5 values mapped to the [Kase18.a5] parameter.
 * @param args6 values mapped to the [Kase18.a6] parameter.
 * @param args7 values mapped to the [Kase18.a7] parameter.
 * @param args8 values mapped to the [Kase18.a8] parameter.
 * @param args9 values mapped to the [Kase18.a9] parameter.
 * @param args10 values mapped to the [Kase18.a10] parameter.
 * @param args11 values mapped to the [Kase18.a11] parameter.
 * @param args12 values mapped to the [Kase18.a12] parameter.
 * @param args13 values mapped to the [Kase18.a13] parameter.
 * @param args14 values mapped to the [Kase18.a14] parameter.
 * @param args15 values mapped to the [Kase18.a15] parameter.
 * @param args16 values mapped to the [Kase18.a16] parameter.
 * @param args17 values mapped to the [Kase18.a17] parameter.
 * @param args18 values mapped to the [Kase18.a18] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a list of [Kase18]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kases(
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
  displayNameFactory: KaseDisplayNameFactory<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> = defaultKase18DisplayNameFactory()
): List<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> {
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
                                        add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, displayNameFactory = displayNameFactory))
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
 * Returns a sequence of [Kase18]s from the given parameters.
 *
 * @param args1 values mapped to the [Kase18.a1] parameter.
 * @param args2 values mapped to the [Kase18.a2] parameter.
 * @param args3 values mapped to the [Kase18.a3] parameter.
 * @param args4 values mapped to the [Kase18.a4] parameter.
 * @param args5 values mapped to the [Kase18.a5] parameter.
 * @param args6 values mapped to the [Kase18.a6] parameter.
 * @param args7 values mapped to the [Kase18.a7] parameter.
 * @param args8 values mapped to the [Kase18.a8] parameter.
 * @param args9 values mapped to the [Kase18.a9] parameter.
 * @param args10 values mapped to the [Kase18.a10] parameter.
 * @param args11 values mapped to the [Kase18.a11] parameter.
 * @param args12 values mapped to the [Kase18.a12] parameter.
 * @param args13 values mapped to the [Kase18.a13] parameter.
 * @param args14 values mapped to the [Kase18.a14] parameter.
 * @param args15 values mapped to the [Kase18.a15] parameter.
 * @param args16 values mapped to the [Kase18.a16] parameter.
 * @param args17 values mapped to the [Kase18.a17] parameter.
 * @param args18 values mapped to the [Kase18.a18] parameter.
 * @param displayNameFactory defines the name used in test environments and dynamic tests
 * @return a sequence of [Kase18]s from the given parameters.
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kases(
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
  displayNameFactory: KaseDisplayNameFactory<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> = defaultKase18DisplayNameFactory()
): Sequence<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> {
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
                                        yield(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, displayNameFactory = displayNameFactory))
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
 * Creates a new [Kase18] instance and [TestEnvironment]
 * from these parameters, then executes [testAction].
 *
 * @param a1 the [Kase18.a1] parameter.
 * @param a2 the [Kase18.a2] parameter.
 * @param a3 the [Kase18.a3] parameter.
 * @param a4 the [Kase18.a4] parameter.
 * @param a5 the [Kase18.a5] parameter.
 * @param a6 the [Kase18.a6] parameter.
 * @param a7 the [Kase18.a7] parameter.
 * @param a8 the [Kase18.a8] parameter.
 * @param a9 the [Kase18.a9] parameter.
 * @param a10 the [Kase18.a10] parameter.
 * @param a11 the [Kase18.a11] parameter.
 * @param a12 the [Kase18.a12] parameter.
 * @param a13 the [Kase18.a13] parameter.
 * @param a14 the [Kase18.a14] parameter.
 * @param a15 the [Kase18.a15] parameter.
 * @param a16 the [Kase18.a16] parameter.
 * @param a17 the [Kase18.a17] parameter.
 * @param a18 the [Kase18.a18] parameter.
 * @param displayNameFactory defines the name used for this test environment's working directory
 * @param testFunctionCoordinates the [TestFunctionCoordinates] from which the test is being run.
 * @param testAction the test action to execute.
 * @see KaseTestFactory
 * @since 0.1.0
 */
public fun <T: TestEnvironment, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> KaseTestFactory<T, Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18,
  displayNameFactory: KaseDisplayNameFactory<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> = defaultKase18DisplayNameFactory(),
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get(),
  testAction: suspend T.() -> Unit
) {
  this@KaseTestFactory.test(
    kase = kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, displayNameFactory = displayNameFactory),
    testFunctionCoordinates = testFunctionCoordinates,
    testAction = testAction
  )
}

/**
 * Creates a [Stream] of [DynamicNode]s from this [Iterable] of [Kase18]s.
 *
 * @param testAction the test action to run for each kase.
 * @return a [Stream] of [DynamicNode]s from these kases.
 * @see Kase18
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.asTests(
  testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory {
    this@asTests.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) }
  }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase18] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase18]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase18
 * @see TestEnvironmentFactory
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  vararg kases: Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>,
  testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asSequence().asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) } }
}

/**
 * A test factory which returns a stream of [DynamicNode]s from the given parameters.
 * - Each [DynamicTest] in the stream uses its [Kase18] element to create
 *   a new [TestEnvironment] instance, then executes [testAction].
 * - Each [DynamicNode] has a display name which includes the values of the parameters.
 *
 * @param kases the [Kase18]s to use for this test factory
 * @param testAction the test action to execute.
 * @return a [Stream] of [DynamicNode]s from the given parameters.
 * @see Kase18
 * @see TestEnvironmentFactory
 * @since 0.1.0
 */
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  kases: Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>,
  testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return testFactory { kases.asTests { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8, it.a9, it.a10, it.a11, it.a12, it.a13, it.a14, it.a15, it.a16, it.a17, it.a18) } }
}

/**
 * @param others the [Kase1] to combine with this [Kase18]
 * @return a list of [Kase19]s from the cartesian product of this [Kase18] and the given [Kase1].
 * @since 0.1.0
 */
@JvmName("kase18timesKase1")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase1<B1>>
): List<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1)
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
 * @param others the [Kase3] to combine with this [Kase18]
 * @return a list of [Kase21]s from the cartesian product of this [Kase18] and the given [Kase3].
 * @since 0.1.0
 */
@JvmName("kase18timesKase3")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2, B3> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase3<B1, B2, B3>>
): List<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2, B3>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1, b2, b3) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1, b2, b3)
  }
}

/**
 * @param others the [Kase4] to combine with this [Kase18]
 * @return a list of [Kase22]s from the cartesian product of this [Kase18] and the given [Kase4].
 * @since 0.1.0
 */
@JvmName("kase18timesKase4")
public operator fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2, B3, B4> Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>.times(
  others: Iterable<Kase4<B1, B2, B3, B4>>
): List<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, B1, B2, B3, B4>> = flatMap { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
  others.map { (b1, b2, b3, b4) ->
    kase(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, b1, b2, b3, b4)
  }
}
