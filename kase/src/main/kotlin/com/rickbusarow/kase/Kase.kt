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

@file:Suppress("ktlint:parameter-list-wrapping", "ktlint:argument-list-wrapping")

package com.rickbusarow.kase

import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

interface Kase {
  val args: List<Any?>
}


/* Kase1 */

fun <A1> kase(
  a1: A1
): Kase1<A1> {
  return Kase1(a1 = a1)
}

fun <A1> kases(
  args1: Iterable<A1>
): List<Kase1<A1>> {
  return args1.map { a1 ->
    Kase1(a1 = a1)
  }
}

@JvmName("testFactoryKase1")
inline fun <A1> testFactory(
  kases: Iterable<Kase1<A1>>,
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
      }
    },
    testAction = { (a1) ->
      testAction(a1)
    }
  )
}

@JvmName("testFactoryKase1")
inline fun <A1> testFactory(
  vararg kases: Kase1<A1>,
  crossinline kaseName: (Kase1<A1>) -> String = { it.toString() },
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1) ->
    testAction(a1)
  }
}

@JvmName("testFactoryKase1")
inline fun <A1> testFactory(
  vararg kases: Kase1<A1>,
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
      }
    },
    testAction = { (a1) ->
      testAction(a1)
    }
  )
}

data class Kase1<out A1>(val a1: A1) : Kase {
  override val args: List<Any?> = listOf(a1)
}

data class KaseLabels1(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1")


/* Kase2 */

fun <A1, A2> kase(
  a1: A1, a2: A2
): Kase2<A1, A2> {
  return Kase2(a1 = a1, a2 = a2)
}

fun <A1, A2> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>
): List<Kase2<A1, A2>> {
  return args1.flatMap { a1 ->
    args2.map { a2 ->
      Kase2(a1 = a1, a2 = a2)
  }
    }
}

@JvmName("testFactoryKase2")
inline fun <A1, A2> testFactory(
  kases: Iterable<Kase2<A1, A2>>,
  labels: KaseLabels2 = KaseLabels2(),
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
      }
    },
    testAction = { (a1, a2) ->
      testAction(a1, a2)
    }
  )
}

@JvmName("testFactoryKase2")
inline fun <A1, A2> testFactory(
  vararg kases: Kase2<A1, A2>,
  crossinline kaseName: (Kase2<A1, A2>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2) ->
    testAction(a1, a2)
  }
}

@JvmName("testFactoryKase2")
inline fun <A1, A2> testFactory(
  vararg kases: Kase2<A1, A2>,
  labels: KaseLabels2 = KaseLabels2(),
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
      }
    },
    testAction = { (a1, a2) ->
      testAction(a1, a2)
    }
  )
}

data class Kase2<out A1, out A2>(val a1: A1, val a2: A2) : Kase {
  override val args: List<Any?> = listOf(a1, a2)
}

data class KaseLabels2(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2")


/* Kase3 */

fun <A1, A2, A3> kase(
  a1: A1, a2: A2, a3: A3
): Kase3<A1, A2, A3> {
  return Kase3(a1 = a1, a2 = a2, a3 = a3)
}

fun <A1, A2, A3> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>
): List<Kase3<A1, A2, A3>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.map { a3 ->
        Kase3(a1 = a1, a2 = a2, a3 = a3)
  }
    }
      }
}

@JvmName("testFactoryKase3")
inline fun <A1, A2, A3> testFactory(
  kases: Iterable<Kase3<A1, A2, A3>>,
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
      }
    },
    testAction = { (a1, a2, a3) ->
      testAction(a1, a2, a3)
    }
  )
}

@JvmName("testFactoryKase3")
inline fun <A1, A2, A3> testFactory(
  vararg kases: Kase3<A1, A2, A3>,
  crossinline kaseName: (Kase3<A1, A2, A3>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3) ->
    testAction(a1, a2, a3)
  }
}

@JvmName("testFactoryKase3")
inline fun <A1, A2, A3> testFactory(
  vararg kases: Kase3<A1, A2, A3>,
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
      }
    },
    testAction = { (a1, a2, a3) ->
      testAction(a1, a2, a3)
    }
  )
}

data class Kase3<out A1, out A2, out A3>(val a1: A1, val a2: A2, val a3: A3) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3)
}

data class KaseLabels3(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3")


/* Kase4 */

fun <A1, A2, A3, A4> kase(
  a1: A1, a2: A2, a3: A3, a4: A4
): Kase4<A1, A2, A3, A4> {
  return Kase4(a1 = a1, a2 = a2, a3 = a3, a4 = a4)
}

fun <A1, A2, A3, A4> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>
): List<Kase4<A1, A2, A3, A4>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.map { a4 ->
          Kase4(a1 = a1, a2 = a2, a3 = a3, a4 = a4)
  }
    }
      }
        }
}

@JvmName("testFactoryKase4")
inline fun <A1, A2, A3, A4> testFactory(
  kases: Iterable<Kase4<A1, A2, A3, A4>>,
  labels: KaseLabels4 = KaseLabels4(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
      }
    },
    testAction = { (a1, a2, a3, a4) ->
      testAction(a1, a2, a3, a4)
    }
  )
}

@JvmName("testFactoryKase4")
inline fun <A1, A2, A3, A4> testFactory(
  vararg kases: Kase4<A1, A2, A3, A4>,
  crossinline kaseName: (Kase4<A1, A2, A3, A4>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4) ->
    testAction(a1, a2, a3, a4)
  }
}

@JvmName("testFactoryKase4")
inline fun <A1, A2, A3, A4> testFactory(
  vararg kases: Kase4<A1, A2, A3, A4>,
  labels: KaseLabels4 = KaseLabels4(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
      }
    },
    testAction = { (a1, a2, a3, a4) ->
      testAction(a1, a2, a3, a4)
    }
  )
}

data class Kase4<out A1, out A2, out A3, out A4>(val a1: A1, val a2: A2, val a3: A3, val a4: A4) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4)
}

data class KaseLabels4(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4")


/* Kase5 */

fun <A1, A2, A3, A4, A5> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5
): Kase5<A1, A2, A3, A4, A5> {
  return Kase5(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5)
}

fun <A1, A2, A3, A4, A5> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>
): List<Kase5<A1, A2, A3, A4, A5>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.map { a5 ->
            Kase5(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5)
  }
    }
      }
        }
          }
}

@JvmName("testFactoryKase5")
inline fun <A1, A2, A3, A4, A5> testFactory(
  kases: Iterable<Kase5<A1, A2, A3, A4, A5>>,
  labels: KaseLabels5 = KaseLabels5(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
      }
    },
    testAction = { (a1, a2, a3, a4, a5) ->
      testAction(a1, a2, a3, a4, a5)
    }
  )
}

@JvmName("testFactoryKase5")
inline fun <A1, A2, A3, A4, A5> testFactory(
  vararg kases: Kase5<A1, A2, A3, A4, A5>,
  crossinline kaseName: (Kase5<A1, A2, A3, A4, A5>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5) ->
    testAction(a1, a2, a3, a4, a5)
  }
}

@JvmName("testFactoryKase5")
inline fun <A1, A2, A3, A4, A5> testFactory(
  vararg kases: Kase5<A1, A2, A3, A4, A5>,
  labels: KaseLabels5 = KaseLabels5(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
      }
    },
    testAction = { (a1, a2, a3, a4, a5) ->
      testAction(a1, a2, a3, a4, a5)
    }
  )
}

data class Kase5<out A1, out A2, out A3, out A4, out A5>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5)
}

data class KaseLabels5(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5")


/* Kase6 */

fun <A1, A2, A3, A4, A5, A6> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6
): Kase6<A1, A2, A3, A4, A5, A6> {
  return Kase6(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6)
}

fun <A1, A2, A3, A4, A5, A6> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>
): List<Kase6<A1, A2, A3, A4, A5, A6>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.map { a6 ->
              Kase6(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6)
  }
    }
      }
        }
          }
            }
}

@JvmName("testFactoryKase6")
inline fun <A1, A2, A3, A4, A5, A6> testFactory(
  kases: Iterable<Kase6<A1, A2, A3, A4, A5, A6>>,
  labels: KaseLabels6 = KaseLabels6(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6) ->
      testAction(a1, a2, a3, a4, a5, a6)
    }
  )
}

@JvmName("testFactoryKase6")
inline fun <A1, A2, A3, A4, A5, A6> testFactory(
  vararg kases: Kase6<A1, A2, A3, A4, A5, A6>,
  crossinline kaseName: (Kase6<A1, A2, A3, A4, A5, A6>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6) ->
    testAction(a1, a2, a3, a4, a5, a6)
  }
}

@JvmName("testFactoryKase6")
inline fun <A1, A2, A3, A4, A5, A6> testFactory(
  vararg kases: Kase6<A1, A2, A3, A4, A5, A6>,
  labels: KaseLabels6 = KaseLabels6(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6) ->
      testAction(a1, a2, a3, a4, a5, a6)
    }
  )
}

data class Kase6<out A1, out A2, out A3, out A4, out A5, out A6>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6)
}

data class KaseLabels6(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6")


/* Kase7 */

fun <A1, A2, A3, A4, A5, A6, A7> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7
): Kase7<A1, A2, A3, A4, A5, A6, A7> {
  return Kase7(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7)
}

fun <A1, A2, A3, A4, A5, A6, A7> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>
): List<Kase7<A1, A2, A3, A4, A5, A6, A7>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.map { a7 ->
                Kase7(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7)
  }
    }
      }
        }
          }
            }
              }
}

@JvmName("testFactoryKase7")
inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  kases: Iterable<Kase7<A1, A2, A3, A4, A5, A6, A7>>,
  labels: KaseLabels7 = KaseLabels7(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7) ->
      testAction(a1, a2, a3, a4, a5, a6, a7)
    }
  )
}

@JvmName("testFactoryKase7")
inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  vararg kases: Kase7<A1, A2, A3, A4, A5, A6, A7>,
  crossinline kaseName: (Kase7<A1, A2, A3, A4, A5, A6, A7>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7) ->
    testAction(a1, a2, a3, a4, a5, a6, a7)
  }
}

@JvmName("testFactoryKase7")
inline fun <A1, A2, A3, A4, A5, A6, A7> testFactory(
  vararg kases: Kase7<A1, A2, A3, A4, A5, A6, A7>,
  labels: KaseLabels7 = KaseLabels7(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7) ->
      testAction(a1, a2, a3, a4, a5, a6, a7)
    }
  )
}

data class Kase7<out A1, out A2, out A3, out A4, out A5, out A6, out A7>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7)
}

data class KaseLabels7(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7")


/* Kase8 */

fun <A1, A2, A3, A4, A5, A6, A7, A8> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8
): Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
  return Kase8(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>
): List<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.map { a8 ->
                  Kase8(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8)
  }
    }
      }
        }
          }
            }
              }
                }
}

@JvmName("testFactoryKase8")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  kases: Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>,
  labels: KaseLabels8 = KaseLabels8(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8)
    }
  )
}

@JvmName("testFactoryKase8")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  vararg kases: Kase8<A1, A2, A3, A4, A5, A6, A7, A8>,
  crossinline kaseName: (Kase8<A1, A2, A3, A4, A5, A6, A7, A8>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8)
  }
}

@JvmName("testFactoryKase8")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  vararg kases: Kase8<A1, A2, A3, A4, A5, A6, A7, A8>,
  labels: KaseLabels8 = KaseLabels8(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8)
    }
  )
}

data class Kase8<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8)
}

data class KaseLabels8(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8")


/* Kase9 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9
): Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9> {
  return Kase9(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>,
  args9: Iterable<A9>
): List<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.map { a9 ->
                    Kase9(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9)
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

@JvmName("testFactoryKase9")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> testFactory(
  kases: Iterable<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>>,
  labels: KaseLabels9 = KaseLabels9(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9)
    }
  )
}

@JvmName("testFactoryKase9")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> testFactory(
  vararg kases: Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>,
  crossinline kaseName: (Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9)
  }
}

@JvmName("testFactoryKase9")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> testFactory(
  vararg kases: Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>,
  labels: KaseLabels9 = KaseLabels9(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9)
    }
  )
}

data class Kase9<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9)
}

data class KaseLabels9(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9")


/* Kase10 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10
): Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {
  return Kase10(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>,
  args9: Iterable<A9>,
  args10: Iterable<A10>
): List<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.map { a10 ->
                      Kase10(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10)
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

@JvmName("testFactoryKase10")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> testFactory(
  kases: Iterable<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>,
  labels: KaseLabels10 = KaseLabels10(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
    }
  )
}

@JvmName("testFactoryKase10")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> testFactory(
  vararg kases: Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>,
  crossinline kaseName: (Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
  }
}

@JvmName("testFactoryKase10")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> testFactory(
  vararg kases: Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>,
  labels: KaseLabels10 = KaseLabels10(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
    }
  )
}

data class Kase10<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
}

data class KaseLabels10(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10")


/* Kase11 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11
): Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
  return Kase11(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> kases(
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
  args11: Iterable<A11>
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.map { a11 ->
                        Kase11(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11)
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

@JvmName("testFactoryKase11")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  kases: Iterable<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>>,
  labels: KaseLabels11 = KaseLabels11(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
    }
  )
}

@JvmName("testFactoryKase11")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  vararg kases: Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>,
  crossinline kaseName: (Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
  }
}

@JvmName("testFactoryKase11")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> testFactory(
  vararg kases: Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>,
  labels: KaseLabels11 = KaseLabels11(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
    }
  )
}

data class Kase11<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
}

data class KaseLabels11(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11")


/* Kase12 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12
): Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> {
  return Kase12(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> kases(
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
  args12: Iterable<A12>
): List<Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.map { a12 ->
                          Kase12(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12)
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

@JvmName("testFactoryKase12")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> testFactory(
  kases: Iterable<Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>>,
  labels: KaseLabels12 = KaseLabels12(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)
    }
  )
}

@JvmName("testFactoryKase12")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> testFactory(
  vararg kases: Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>,
  crossinline kaseName: (Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)
  }
}

@JvmName("testFactoryKase12")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> testFactory(
  vararg kases: Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>,
  labels: KaseLabels12 = KaseLabels12(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)
    }
  )
}

data class Kase12<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)
}

data class KaseLabels12(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12")


/* Kase13 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13
): Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> {
  return Kase13(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> kases(
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
  args13: Iterable<A13>
): List<Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.map { a13 ->
                            Kase13(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13)
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

@JvmName("testFactoryKase13")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> testFactory(
  kases: Iterable<Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>>,
  labels: KaseLabels13 = KaseLabels13(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)
    }
  )
}

@JvmName("testFactoryKase13")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> testFactory(
  vararg kases: Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>,
  crossinline kaseName: (Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)
  }
}

@JvmName("testFactoryKase13")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> testFactory(
  vararg kases: Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>,
  labels: KaseLabels13 = KaseLabels13(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)
    }
  )
}

data class Kase13<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)
}

data class KaseLabels13(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13")


/* Kase14 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14
): Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> {
  return Kase14(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> kases(
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
  args14: Iterable<A14>
): List<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.map { a14 ->
                              Kase14(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14)
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

@JvmName("testFactoryKase14")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> testFactory(
  kases: Iterable<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>>,
  labels: KaseLabels14 = KaseLabels14(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
    }
  )
}

@JvmName("testFactoryKase14")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> testFactory(
  vararg kases: Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>,
  crossinline kaseName: (Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
  }
}

@JvmName("testFactoryKase14")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> testFactory(
  vararg kases: Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>,
  labels: KaseLabels14 = KaseLabels14(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
    }
  )
}

data class Kase14<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
}

data class KaseLabels14(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14")


/* Kase15 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15
): Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> {
  return Kase15(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> kases(
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
  args15: Iterable<A15>
): List<Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.map { a15 ->
                                Kase15(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15)
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

@JvmName("testFactoryKase15")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> testFactory(
  kases: Iterable<Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>>,
  labels: KaseLabels15 = KaseLabels15(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
    }
  )
}

@JvmName("testFactoryKase15")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> testFactory(
  vararg kases: Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>,
  crossinline kaseName: (Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
  }
}

@JvmName("testFactoryKase15")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> testFactory(
  vararg kases: Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>,
  labels: KaseLabels15 = KaseLabels15(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
    }
  )
}

data class Kase15<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
}

data class KaseLabels15(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15")


/* Kase16 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16
): Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> {
  return Kase16(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> kases(
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
  args16: Iterable<A16>
): List<Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.flatMap { a15 ->
                                args16.map { a16 ->
                                  Kase16(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16)
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

@JvmName("testFactoryKase16")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> testFactory(
  kases: Iterable<Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>>,
  labels: KaseLabels16 = KaseLabels16(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
    }
  )
}

@JvmName("testFactoryKase16")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> testFactory(
  vararg kases: Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>,
  crossinline kaseName: (Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
  }
}

@JvmName("testFactoryKase16")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> testFactory(
  vararg kases: Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>,
  labels: KaseLabels16 = KaseLabels16(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
    }
  )
}

data class Kase16<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15, val a16: A16) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
}

data class KaseLabels16(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15", val a16Label: String = "a16")


/* Kase17 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17
): Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17> {
  return Kase17(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17> kases(
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
  args17: Iterable<A17>
): List<Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.flatMap { a15 ->
                                args16.flatMap { a16 ->
                                  args17.map { a17 ->
                                    Kase17(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17)
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

@JvmName("testFactoryKase17")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17> testFactory(
  kases: Iterable<Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>>,
  labels: KaseLabels17 = KaseLabels17(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)
    }
  )
}

@JvmName("testFactoryKase17")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17> testFactory(
  vararg kases: Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>,
  crossinline kaseName: (Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)
  }
}

@JvmName("testFactoryKase17")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17> testFactory(
  vararg kases: Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>,
  labels: KaseLabels17 = KaseLabels17(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)
    }
  )
}

data class Kase17<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15, val a16: A16, val a17: A17) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)
}

data class KaseLabels17(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15", val a16Label: String = "a16", val a17Label: String = "a17")


/* Kase18 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18
): Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> {
  return Kase18(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> kases(
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
  args18: Iterable<A18>
): List<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.flatMap { a15 ->
                                args16.flatMap { a16 ->
                                  args17.flatMap { a17 ->
                                    args18.map { a18 ->
                                      Kase18(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18)
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

@JvmName("testFactoryKase18")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  kases: Iterable<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>>,
  labels: KaseLabels18 = KaseLabels18(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)
    }
  )
}

@JvmName("testFactoryKase18")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  vararg kases: Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>,
  crossinline kaseName: (Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)
  }
}

@JvmName("testFactoryKase18")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18> testFactory(
  vararg kases: Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>,
  labels: KaseLabels18 = KaseLabels18(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)
    }
  )
}

data class Kase18<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15, val a16: A16, val a17: A17, val a18: A18) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)
}

data class KaseLabels18(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15", val a16Label: String = "a16", val a17Label: String = "a17", val a18Label: String = "a18")


/* Kase19 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19
): Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> {
  return Kase19(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> kases(
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
  args19: Iterable<A19>
): List<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.flatMap { a15 ->
                                args16.flatMap { a16 ->
                                  args17.flatMap { a17 ->
                                    args18.flatMap { a18 ->
                                      args19.map { a19 ->
                                        Kase19(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19)
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

@JvmName("testFactoryKase19")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> testFactory(
  kases: Iterable<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>>,
  labels: KaseLabels19 = KaseLabels19(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)
    }
  )
}

@JvmName("testFactoryKase19")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> testFactory(
  vararg kases: Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>,
  crossinline kaseName: (Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)
  }
}

@JvmName("testFactoryKase19")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> testFactory(
  vararg kases: Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>,
  labels: KaseLabels19 = KaseLabels19(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)
    }
  )
}

data class Kase19<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15, val a16: A16, val a17: A17, val a18: A18, val a19: A19) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)
}

data class KaseLabels19(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15", val a16Label: String = "a16", val a17Label: String = "a17", val a18Label: String = "a18", val a19Label: String = "a19")


/* Kase20 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20
): Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> {
  return Kase20(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> kases(
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
  args20: Iterable<A20>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.flatMap { a15 ->
                                args16.flatMap { a16 ->
                                  args17.flatMap { a17 ->
                                    args18.flatMap { a18 ->
                                      args19.flatMap { a19 ->
                                        args20.map { a20 ->
                                          Kase20(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20)
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

@JvmName("testFactoryKase20")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  kases: Iterable<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>>,
  labels: KaseLabels20 = KaseLabels20(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
        append(labels.separator)
        append("${labels.a20Label}${labels.delimiter}$a20")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)
    }
  )
}

@JvmName("testFactoryKase20")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  vararg kases: Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>,
  crossinline kaseName: (Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)
  }
}

@JvmName("testFactoryKase20")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20> testFactory(
  vararg kases: Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>,
  labels: KaseLabels20 = KaseLabels20(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
        append(labels.separator)
        append("${labels.a20Label}${labels.delimiter}$a20")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)
    }
  )
}

data class Kase20<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15, val a16: A16, val a17: A17, val a18: A18, val a19: A19, val a20: A20) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)
}

data class KaseLabels20(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15", val a16Label: String = "a16", val a17Label: String = "a17", val a18Label: String = "a18", val a19Label: String = "a19", val a20Label: String = "a20")


/* Kase21 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21
): Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> {
  return Kase21(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> kases(
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
  args21: Iterable<A21>
): List<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.flatMap { a15 ->
                                args16.flatMap { a16 ->
                                  args17.flatMap { a17 ->
                                    args18.flatMap { a18 ->
                                      args19.flatMap { a19 ->
                                        args20.flatMap { a20 ->
                                          args21.map { a21 ->
                                            Kase21(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21)
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

@JvmName("testFactoryKase21")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> testFactory(
  kases: Iterable<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>>,
  labels: KaseLabels21 = KaseLabels21(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
        append(labels.separator)
        append("${labels.a20Label}${labels.delimiter}$a20")
        append(labels.separator)
        append("${labels.a21Label}${labels.delimiter}$a21")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)
    }
  )
}

@JvmName("testFactoryKase21")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> testFactory(
  vararg kases: Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>,
  crossinline kaseName: (Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)
  }
}

@JvmName("testFactoryKase21")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21> testFactory(
  vararg kases: Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>,
  labels: KaseLabels21 = KaseLabels21(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
        append(labels.separator)
        append("${labels.a20Label}${labels.delimiter}$a20")
        append(labels.separator)
        append("${labels.a21Label}${labels.delimiter}$a21")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)
    }
  )
}

data class Kase21<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20, out A21>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15, val a16: A16, val a17: A17, val a18: A18, val a19: A19, val a20: A20, val a21: A21) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)
}

data class KaseLabels21(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15", val a16Label: String = "a16", val a17Label: String = "a17", val a18Label: String = "a18", val a19Label: String = "a19", val a20Label: String = "a20", val a21Label: String = "a21")


/* Kase22 */

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21, a22: A22
): Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> {
  return Kase22(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> kases(
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
  args22: Iterable<A22>
): List<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> {
  return args1.flatMap { a1 ->
    args2.flatMap { a2 ->
      args3.flatMap { a3 ->
        args4.flatMap { a4 ->
          args5.flatMap { a5 ->
            args6.flatMap { a6 ->
              args7.flatMap { a7 ->
                args8.flatMap { a8 ->
                  args9.flatMap { a9 ->
                    args10.flatMap { a10 ->
                      args11.flatMap { a11 ->
                        args12.flatMap { a12 ->
                          args13.flatMap { a13 ->
                            args14.flatMap { a14 ->
                              args15.flatMap { a15 ->
                                args16.flatMap { a16 ->
                                  args17.flatMap { a17 ->
                                    args18.flatMap { a18 ->
                                      args19.flatMap { a19 ->
                                        args20.flatMap { a20 ->
                                          args21.flatMap { a21 ->
                                            args22.map { a22 ->
                                              Kase22(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, a9 = a9, a10 = a10, a11 = a11, a12 = a12, a13 = a13, a14 = a14, a15 = a15, a16 = a16, a17 = a17, a18 = a18, a19 = a19, a20 = a20, a21 = a21, a22 = a22)
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

@JvmName("testFactoryKase22")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> testFactory(
  kases: Iterable<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>>,
  labels: KaseLabels22 = KaseLabels22(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21, a22: A22) -> Unit
): Stream<out DynamicNode> {
  return kases.asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
        append(labels.separator)
        append("${labels.a20Label}${labels.delimiter}$a20")
        append(labels.separator)
        append("${labels.a21Label}${labels.delimiter}$a21")
        append(labels.separator)
        append("${labels.a22Label}${labels.delimiter}$a22")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22)
    }
  )
}

@JvmName("testFactoryKase22")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> testFactory(
  vararg kases: Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>,
  crossinline kaseName: (Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21, a22: A22) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) ->
    testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22)
  }
}

@JvmName("testFactoryKase22")
inline fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22> testFactory(
  vararg kases: Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>,
  labels: KaseLabels22 = KaseLabels22(),
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21, a22: A22) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(
    testName = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) ->
      buildString {
        append("${labels.a1Label}${labels.delimiter}$a1")
        append(labels.separator)
        append("${labels.a2Label}${labels.delimiter}$a2")
        append(labels.separator)
        append("${labels.a3Label}${labels.delimiter}$a3")
        append(labels.separator)
        append("${labels.a4Label}${labels.delimiter}$a4")
        append(labels.separator)
        append("${labels.a5Label}${labels.delimiter}$a5")
        append(labels.separator)
        append("${labels.a6Label}${labels.delimiter}$a6")
        append(labels.separator)
        append("${labels.a7Label}${labels.delimiter}$a7")
        append(labels.separator)
        append("${labels.a8Label}${labels.delimiter}$a8")
        append(labels.separator)
        append("${labels.a9Label}${labels.delimiter}$a9")
        append(labels.separator)
        append("${labels.a10Label}${labels.delimiter}$a10")
        append(labels.separator)
        append("${labels.a11Label}${labels.delimiter}$a11")
        append(labels.separator)
        append("${labels.a12Label}${labels.delimiter}$a12")
        append(labels.separator)
        append("${labels.a13Label}${labels.delimiter}$a13")
        append(labels.separator)
        append("${labels.a14Label}${labels.delimiter}$a14")
        append(labels.separator)
        append("${labels.a15Label}${labels.delimiter}$a15")
        append(labels.separator)
        append("${labels.a16Label}${labels.delimiter}$a16")
        append(labels.separator)
        append("${labels.a17Label}${labels.delimiter}$a17")
        append(labels.separator)
        append("${labels.a18Label}${labels.delimiter}$a18")
        append(labels.separator)
        append("${labels.a19Label}${labels.delimiter}$a19")
        append(labels.separator)
        append("${labels.a20Label}${labels.delimiter}$a20")
        append(labels.separator)
        append("${labels.a21Label}${labels.delimiter}$a21")
        append(labels.separator)
        append("${labels.a22Label}${labels.delimiter}$a22")
      }
    },
    testAction = { (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) ->
      testAction(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22)
    }
  )
}

data class Kase22<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8, out A9, out A10, out A11, out A12, out A13, out A14, out A15, out A16, out A17, out A18, out A19, out A20, out A21, out A22>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5, val a6: A6, val a7: A7, val a8: A8, val a9: A9, val a10: A10, val a11: A11, val a12: A12, val a13: A13, val a14: A14, val a15: A15, val a16: A16, val a17: A17, val a18: A18, val a19: A19, val a20: A20, val a21: A21, val a22: A22) : Kase {
  override val args: List<Any?> = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22)
}

data class KaseLabels22(val delimiter: String = ": ", val separator: String = " | ", val a1Label: String = "a1", val a2Label: String = "a2", val a3Label: String = "a3", val a4Label: String = "a4", val a5Label: String = "a5", val a6Label: String = "a6", val a7Label: String = "a7", val a8Label: String = "a8", val a9Label: String = "a9", val a10Label: String = "a10", val a11Label: String = "a11", val a12Label: String = "a12", val a13Label: String = "a13", val a14Label: String = "a14", val a15Label: String = "a15", val a16Label: String = "a16", val a17Label: String = "a17", val a18Label: String = "a18", val a19Label: String = "a19", val a20Label: String = "a20", val a21Label: String = "a21", val a22Label: String = "a22")
