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

@file:Suppress("ktlint:standard:parameter-list-wrapping", "MaxLineLength")

package com.rickbusarow.kase

import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

/*
@JvmName("testFactoryKase1")
inline fun <A1> testFactory(
  kases: Iterable<Kase1<A1>>,
  crossinline kaseName: (Kase1<A1>) -> String = { it.toString() },
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(kase.a1)
  }
}

@JvmName("testFactoryKase2")
inline fun <A1, A2> testFactory(
  kases: Iterable<Kase2<A1, A2>>,
  crossinline kaseName: (Kase2<A1, A2>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(kase.a1, kase.a2)
  }
}


@JvmName("testFactoryKase3")
inline fun <A1, A2, A3> testFactory(
  kases: Iterable<Kase3<A1, A2, A3>>,
  crossinline kaseName: (Kase3<A1, A2, A3>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(kase.a1, kase.a2, kase.a3)
  }
}

@JvmName("testFactoryKase4")
inline fun <A1, A2, A3, A4> testFactory(
  kases: Iterable<Kase4<A1, A2, A3, A4>>,
  crossinline kaseName: (Kase4<A1, A2, A3, A4>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(kase.a1, kase.a2, kase.a3, kase.a4)
  }
}

@JvmName("testFactoryKase5")
inline fun <A1, A2, A3, A4, A5> testFactory(
  kases: Iterable<Kase5<A1, A2, A3, A4, A5>>,
  crossinline kaseName: (Kase5<A1, A2, A3, A4, A5>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5)
  }
}

@JvmName("testFactoryKase6")
inline fun <A1, A2, A3, A4, A5, A6> testFactory(
  kases: Iterable<Kase6<A1, A2, A3, A4, A5, A6>>,
  crossinline kaseName: (Kase6<A1, A2, A3, A4, A5, A6>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5, kase.a6)
  }
}

@JvmName("testFactoryKase7")
inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  kases: Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>,
  crossinline kaseName: (Kase7<A1, A2, A3, A4, A5, A6, A7>) -> String = {
    it.toString()
  },
  crossinline testAction: (
    a1: A1,
    a2: A2,
    a3: A3,
    a4: A4,
    a5: A5,
    a6: A6,
    a7: A7
  ) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5, kase.a6, kase.a7)
  }
}

@JvmName("testFactoryKase8")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  kases: Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>,
  crossinline kaseName: (Kase8<A1, A2, A3, A4, A5, A6, A7, A8>) -> String = {
    it.toString()
  },
  crossinline testAction: (
    a1: A1,
    a2: A2,
    a3: A3,
    a4: A4,
    a5: A5,
    a6: A6,
    a7: A7,
    a8: A8
  ) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(kaseName) { kase ->
    testAction(
      kase.a1,
      kase.a2,
      kase.a3,
      kase.a4,
      kase.a5,
      kase.a6,
      kase.a7,
      kase.a8
    )
  }
}

@JvmName("testFactory1")
inline fun <A1> testFactory(
  args1: Iterable<A1>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return args1.asTests(a1Name) { a1 ->
    testAction(a1)
  }
}

@JvmName("testFactory2")
inline fun <A1, A2> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline a2Name: (A2) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return args1.asContainers(a1Name) { a1 ->
    args2.asTests(a2Name) { a2 ->
      testAction(a1, a2)
    }
  }
}

@JvmName("testFactory3")
inline fun <A1, A2, A3> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline a2Name: (A2) -> String = { it.toString() },
  crossinline a3Name: (A3) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(a1Name) { a1 ->
    args2.asContainers(a2Name) { a2 ->
      args3.asTests(a3Name) { a3 ->
        testAction(a1, a2, a3)
      }
    }
  }
}

@JvmName("testFactory4")
inline fun <A1, A2, A3, A4> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline a2Name: (A2) -> String = { it.toString() },
  crossinline a3Name: (A3) -> String = { it.toString() },
  crossinline a4Name: (A4) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(a1Name) { a1 ->
    args2.asContainers(a2Name) { a2 ->
      args3.asContainers(a3Name) { a3 ->
        args4.asTests(a4Name) { a4 ->
          testAction(a1, a2, a3, a4)
        }
      }
    }
  }
}

@JvmName("testFactory5")
inline fun <A1, A2, A3, A4, A5> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline a2Name: (A2) -> String = { it.toString() },
  crossinline a3Name: (A3) -> String = { it.toString() },
  crossinline a4Name: (A4) -> String = { it.toString() },
  crossinline a5Name: (A5) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(a1Name) { a1 ->
    args2.asContainers(a2Name) { a2 ->
      args3.asContainers(a3Name) { a3 ->
        args4.asContainers(a4Name) { a4 ->
          args5.asTests(a5Name) { a5 ->
            testAction(a1, a2, a3, a4, a5)
          }
        }
      }
    }
  }
}

@JvmName("testFactory6")
inline fun <A1, A2, A3, A4, A5, A6> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline a2Name: (A2) -> String = { it.toString() },
  crossinline a3Name: (A3) -> String = { it.toString() },
  crossinline a4Name: (A4) -> String = { it.toString() },
  crossinline a5Name: (A5) -> String = { it.toString() },
  crossinline a6Name: (A6) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(a1Name) { a1 ->
    args2.asContainers(a2Name) { a2 ->
      args3.asContainers(a3Name) { a3 ->
        args4.asContainers(a4Name) { a4 ->
          args5.asContainers(a5Name) { a5 ->
            args6.asTests(a6Name) { a6 ->
              testAction(a1, a2, a3, a4, a5, a6)
            }
          }
        }
      }
    }
  }
}

@JvmName("testFactory7")
inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline a2Name: (A2) -> String = { it.toString() },
  crossinline a3Name: (A3) -> String = { it.toString() },
  crossinline a4Name: (A4) -> String = { it.toString() },
  crossinline a5Name: (A5) -> String = { it.toString() },
  crossinline a6Name: (A6) -> String = { it.toString() },
  crossinline a7Name: (A7) -> String = { it.toString() },
  crossinline testAction: (
    a1: A1,
    a2: A2,
    a3: A3,
    a4: A4,
    a5: A5,
    a6: A6,
    a7: A7
  ) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(a1Name) { a1 ->
    args2.asContainers(a2Name) { a2 ->
      args3.asContainers(a3Name) { a3 ->
        args4.asContainers(a4Name) { a4 ->
          args5.asContainers(a5Name) { a5 ->
            args6.asContainers(a6Name) { a6 ->
              args7.asTests(a7Name) { a7 ->
                testAction(a1, a2, a3, a4, a5, a6, a7)
              }
            }
          }
        }
      }
    }
  }
}

@JvmName("testFactory8")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>,
  crossinline a1Name: (A1) -> String = { it.toString() },
  crossinline a2Name: (A2) -> String = { it.toString() },
  crossinline a3Name: (A3) -> String = { it.toString() },
  crossinline a4Name: (A4) -> String = { it.toString() },
  crossinline a5Name: (A5) -> String = { it.toString() },
  crossinline a6Name: (A6) -> String = { it.toString() },
  crossinline a7Name: (A7) -> String = { it.toString() },
  crossinline a8Name: (A8) -> String = { it.toString() },
  crossinline testAction: (
    a1: A1,
    a2: A2,
    a3: A3,
    a4: A4,
    a5: A5,
    a6: A6,
    a7: A7,
    a8: A8
  ) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(a1Name) { a1 ->
    args2.asContainers(a2Name) { a2 ->
      args3.asContainers(a3Name) { a3 ->
        args4.asContainers(a4Name) { a4 ->
          args5.asContainers(a5Name) { a5 ->
            args6.asContainers(a6Name) { a6 ->
              args7.asContainers(a7Name) { a7 ->
                args8.asTests(a8Name) { a8 ->
                  testAction(a1, a2, a3, a4, a5, a6, a7, a8)
                }
              }
            }
          }
        }
      }
    }
  }
}


 */
