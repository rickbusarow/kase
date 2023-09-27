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

data class Case1<A>(val arg1: A)
data class Case2<A1, A2>(val arg1: A1, val arg2: A2)
data class Case3<A1, A2, A3>(val arg1: A1, val arg2: A2, val arg3: A3)
data class Case4<A1, A2, A3, A4>(val arg1: A1, val arg2: A2, val arg3: A3, val arg4: A4)
data class Case5<A1, A2, A3, A4, A5>(
  val arg1: A1,
  val arg2: A2,
  val arg3: A3,
  val arg4: A4,
  val arg5: A5
)

data class Case6<A1, A2, A3, A4, A5, A6>(
  val arg1: A1,
  val arg2: A2,
  val arg3: A3,
  val arg4: A4,
  val arg5: A5,
  val arg6: A6
)

data class Case7<A1, A2, A3, A4, A5, A6, A7>(
  val arg1: A1,
  val arg2: A2,
  val arg3: A3,
  val arg4: A4,
  val arg5: A5,
  val arg6: A6,
  val arg7: A7
)

data class Case8<A1, A2, A3, A4, A5, A6, A7, A8>(
  val arg1: A1,
  val arg2: A2,
  val arg3: A3,
  val arg4: A4,
  val arg5: A5,
  val arg6: A6,
  val arg7: A7,
  val arg8: A8
)

@JvmName("testFactoryCase1")
inline fun <A1> testFactory(
  cases: Iterable<Case1<A1>>,
  crossinline caseName: (Case1<A1>) -> String = { it.toString() },
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(case.arg1)
  }
}

@JvmName("testFactoryCase2")
inline fun <A1, A2> testFactory(
  cases: Iterable<Case2<A1, A2>>,
  crossinline caseName: (Case2<A1, A2>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(case.arg1, case.arg2)
  }
}

@JvmName("testFactoryCase3")
inline fun <A1, A2, A3> testFactory(
  cases: Iterable<Case3<A1, A2, A3>>,
  crossinline caseName: (Case3<A1, A2, A3>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(case.arg1, case.arg2, case.arg3)
  }
}

@JvmName("testFactoryCase4")
inline fun <A1, A2, A3, A4> testFactory(
  cases: Iterable<Case4<A1, A2, A3, A4>>,
  crossinline caseName: (Case4<A1, A2, A3, A4>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(case.arg1, case.arg2, case.arg3, case.arg4)
  }
}

@JvmName("testFactoryCase5")
inline fun <A1, A2, A3, A4, A5> testFactory(
  cases: Iterable<Case5<A1, A2, A3, A4, A5>>,
  crossinline caseName: (Case5<A1, A2, A3, A4, A5>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5)
  }
}

@JvmName("testFactoryCase6")
inline fun <A1, A2, A3, A4, A5, A6> testFactory(
  cases: Iterable<Case6<A1, A2, A3, A4, A5, A6>>,
  crossinline caseName: (Case6<A1, A2, A3, A4, A5, A6>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5, case.arg6)
  }
}

@JvmName("testFactoryCase7")
inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  cases: Iterable<Case7<A1, A2, A3, A4, A5, A6, A7>>,
  crossinline caseName: (Case7<A1, A2, A3, A4, A5, A6, A7>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5, case.arg6, case.arg7)
  }
}

@JvmName("testFactoryCase8")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  cases: Iterable<Case8<A1, A2, A3, A4, A5, A6, A7, A8>>,
  crossinline caseName: (Case8<A1, A2, A3, A4, A5, A6, A7, A8>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode> {
  return cases.asTests(caseName) { case ->
    testAction(
      case.arg1,
      case.arg2,
      case.arg3,
      case.arg4,
      case.arg5,
      case.arg6,
      case.arg7,
      case.arg8
    )
  }
}

@JvmName("testFactory1")
inline fun <A1> testFactory(
  args1: Iterable<A1>,
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return args1.asTests(arg1Name) { a1 ->
    testAction(a1)
  }
}

@JvmName("testFactory2")
inline fun <A1, A2> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline arg2Name: (A2) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return args1.asContainers(arg1Name) { a1 ->
    args2.asTests(arg2Name) { a2 ->
      testAction(a1, a2)
    }
  }
}

@JvmName("testFactory3")
inline fun <A1, A2, A3> testFactory(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline arg2Name: (A2) -> String = { it.toString() },
  crossinline arg3Name: (A3) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(arg1Name) { a1 ->
    args2.asContainers(arg2Name) { a2 ->
      args3.asTests(arg3Name) { a3 ->
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
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline arg2Name: (A2) -> String = { it.toString() },
  crossinline arg3Name: (A3) -> String = { it.toString() },
  crossinline arg4Name: (A4) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(arg1Name) { a1 ->
    args2.asContainers(arg2Name) { a2 ->
      args3.asContainers(arg3Name) { a3 ->
        args4.asTests(arg4Name) { a4 ->
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
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline arg2Name: (A2) -> String = { it.toString() },
  crossinline arg3Name: (A3) -> String = { it.toString() },
  crossinline arg4Name: (A4) -> String = { it.toString() },
  crossinline arg5Name: (A5) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(arg1Name) { a1 ->
    args2.asContainers(arg2Name) { a2 ->
      args3.asContainers(arg3Name) { a3 ->
        args4.asContainers(arg4Name) { a4 ->
          args5.asTests(arg5Name) { a5 ->
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
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline arg2Name: (A2) -> String = { it.toString() },
  crossinline arg3Name: (A3) -> String = { it.toString() },
  crossinline arg4Name: (A4) -> String = { it.toString() },
  crossinline arg5Name: (A5) -> String = { it.toString() },
  crossinline arg6Name: (A6) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(arg1Name) { a1 ->
    args2.asContainers(arg2Name) { a2 ->
      args3.asContainers(arg3Name) { a3 ->
        args4.asContainers(arg4Name) { a4 ->
          args5.asContainers(arg5Name) { a5 ->
            args6.asTests(arg6Name) { a6 ->
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
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline arg2Name: (A2) -> String = { it.toString() },
  crossinline arg3Name: (A3) -> String = { it.toString() },
  crossinline arg4Name: (A4) -> String = { it.toString() },
  crossinline arg5Name: (A5) -> String = { it.toString() },
  crossinline arg6Name: (A6) -> String = { it.toString() },
  crossinline arg7Name: (A7) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(arg1Name) { a1 ->
    args2.asContainers(arg2Name) { a2 ->
      args3.asContainers(arg3Name) { a3 ->
        args4.asContainers(arg4Name) { a4 ->
          args5.asContainers(arg5Name) { a5 ->
            args6.asContainers(arg6Name) { a6 ->
              args7.asTests(arg7Name) { a7 ->
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
  crossinline arg1Name: (A1) -> String = { it.toString() },
  crossinline arg2Name: (A2) -> String = { it.toString() },
  crossinline arg3Name: (A3) -> String = { it.toString() },
  crossinline arg4Name: (A4) -> String = { it.toString() },
  crossinline arg5Name: (A5) -> String = { it.toString() },
  crossinline arg6Name: (A6) -> String = { it.toString() },
  crossinline arg7Name: (A7) -> String = { it.toString() },
  crossinline arg8Name: (A8) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode> {

  return args1.asContainers(arg1Name) { a1 ->
    args2.asContainers(arg2Name) { a2 ->
      args3.asContainers(arg3Name) { a3 ->
        args4.asContainers(arg4Name) { a4 ->
          args5.asContainers(arg5Name) { a5 ->
            args6.asContainers(arg6Name) { a6 ->
              args7.asContainers(arg7Name) { a7 ->
                args8.asTests(arg8Name) { a8 ->
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
