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

@file:Suppress("PackageDirectoryMismatch")
@file:JvmMultifileClass
@file:JvmName("KasesKt")

package com.rickbusarow.kase

import com.rickbusarow.kase.KaseParameterWithLabel.Companion.element
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

/** */
public fun <A1, A2, A3, A4, A5, A6, A7, A8> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, labels: KaseLabels8 = KaseLabels8()
): Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
  return DefaultKase8(
    element(value = a1, label = labels.a1Label),
    element(value = a2, label = labels.a2Label),
    element(value = a3, label = labels.a3Label),
    element(value = a4, label = labels.a4Label),
    element(value = a5, label = labels.a5Label),
    element(value = a6, label = labels.a6Label),
    element(value = a7, label = labels.a7Label),
    element(value = a8, label = labels.a8Label)
  )
}


/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8> test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8,
  labels: KaseLabels8 = KaseLabels8(),
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment<K>,
        K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
  test(
    kase = kase(a1, a2, a3, a4, a5, a6, a7, a8, labels),
    testFunctionName = testFunctionName,
    testAction = testAction
  )
}

/** */
public fun labels(
  a1Label: String = "a1",
  a2Label: String = "a2",
  a3Label: String = "a3",
  a4Label: String = "a4",
  a5Label: String = "a5",
  a6Label: String = "a6",
  a7Label: String = "a7",
  a8Label: String = "a8"
): KaseLabels8 {
  return KaseLabels8(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label, a6Label = a6Label, a7Label = a7Label, a8Label = a8Label)
}

/** */
public fun <A1, A2, A3, A4, A5, A6, A7, A8> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  args6: Iterable<A6>,
  args7: Iterable<A7>,
  args8: Iterable<A8>,
  labels: KaseLabels8 = KaseLabels8()
): List<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              for (a6 in args6) {
                for (a7 in args7) {
                  for (a8 in args8) {
                    add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, a6 = a6, a7 = a7, a8 = a8, labels = labels))
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


// /** */
// @JvmName("asTestsKase8Kase")
// public inline fun <K, A1, A2, A3, A4, A5, A6, A7, A8> Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>.asTests(
//   labels: KaseLabels8 = KaseLabels8(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
//   return testFactory(this@asTests, labels, testAction)
// }


// /** */
// @JvmName("asTestsKase8Destructured")
// public inline fun <K, A1, A2, A3, A4, A5, A6, A7, A8> Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>.asTests(
//   labels: KaseLabels8 = KaseLabels8(),
//   crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
//   return testFactory(this@asTests, labels, testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8> Iterable<K>.asTests(
  labels: KaseLabels8 = KaseLabels8(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
  return testFactory(this@asTests, labels, testAction)
}


// /** */
// @JvmName("testFactoryKase8Kase")
// public inline fun <K, A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
//   vararg kases: Kase8<A1, A2, A3, A4, A5, A6, A7, A8>,
//   labels: KaseLabels8 = KaseLabels8(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }


// /** */
// @JvmName("testFactoryKase8Destructured")
// public inline fun <K, A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
//   vararg kases: Kase8<A1, A2, A3, A4, A5, A6, A7, A8>,
//   labels: KaseLabels8 = KaseLabels8(),
//   crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  vararg kases: K,
  labels: KaseLabels8 = KaseLabels8(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
  return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
}


// /** */
// @JvmName("testFactoryKase8Kase")
// public inline fun <K, A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
//   kases: Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>,
//   labels: KaseLabels8 = KaseLabels8(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8) }
//   )
// }


// /** */
// @JvmName("testFactoryKase8Destructured")
// public inline fun <K, A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
//   kases: Iterable<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>>,
//   labels: KaseLabels8 = KaseLabels8(),
//   crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8) }
//   )
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  kases: Iterable<K>,
  labels: KaseLabels8 = KaseLabels8(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase8<A1, A2, A3, A4, A5, A6, A7, A8> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { kase -> testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5, kase.a6, kase.a7, kase.a8) }
  )
}

/** */
@JvmName("testFactoryKase8")
public inline fun <A1, A2, A3, A4, A5, A6, A7, A8> testFactory(
  vararg kases: Kase8<A1, A2, A3, A4, A5, A6, A7, A8>,
  crossinline kaseName: (Kase8<A1, A2, A3, A4, A5, A6, A7, A8>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3, it.a4, it.a5, it.a6, it.a7, it.a8) }
}

/** A strongly-typed version of [Kase] for 8 parameters. */
public interface Kase8<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8> : Kase<KaseLabels8> {

  /** The 1st parameter. */
  public val a1: A1
  /** The 2nd parameter. */
  public val a2: A2
  /** The 3rd parameter. */
  public val a3: A3
  /** The 4th parameter. */
  public val a4: A4
  /** The 5th parameter. */
  public val a5: A5
  /** The 6th parameter. */
  public val a6: A6
  /** The 7th parameter. */
  public val a7: A7
  /** The 8th parameter. */
  public val a8: A8

  override fun <T> plus(label: String, value: T): Kase9<A1, A2, A3, A4, A5, A6, A7, A8, T>
}
/** */
@Poko
internal class DefaultKase8<out A1, out A2, out A3, out A4, out A5, out A6, out A7, out A8>(
  val a1Element: KaseParameterWithLabel<A1>,
  val a2Element: KaseParameterWithLabel<A2>,
  val a3Element: KaseParameterWithLabel<A3>,
  val a4Element: KaseParameterWithLabel<A4>,
  val a5Element: KaseParameterWithLabel<A5>,
  val a6Element: KaseParameterWithLabel<A6>,
  val a7Element: KaseParameterWithLabel<A7>,
  val a8Element: KaseParameterWithLabel<A8>
) : Kase8<A1, A2, A3, A4, A5, A6, A7, A8>, KaseInternal<KaseLabels8> {
  override val a1: A1 get() = a1Element.value
  override val a2: A2 get() = a2Element.value
  override val a3: A3 get() = a3Element.value
  override val a4: A4 get() = a4Element.value
  override val a5: A5 get() = a5Element.value
  override val a6: A6 get() = a6Element.value
  override val a7: A7 get() = a7Element.value
  override val a8: A8 get() = a8Element.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1Element, a2Element, a3Element, a4Element, a5Element, a6Element, a7Element, a8Element)

  override fun <T> plus(label: String, value: T): DefaultKase9<A1, A2, A3, A4, A5, A6, A7, A8, T> {
    return DefaultKase9(
      a1Element = a1Element,
      a2Element = a2Element,
      a3Element = a3Element,
      a4Element = a4Element,
      a5Element = a5Element,
      a6Element = a6Element,
      a7Element = a7Element,
      a8Element = a8Element,
      element(value = value, label = label)
    )
  }
}

/**
 * A strongly-typed version of [KaseLabels] for 8 parameters.
 *
 * @property a1Label The label for the [Kase8.a1] parameter.
 * @property a2Label The label for the [Kase8.a2] parameter.
 * @property a3Label The label for the [Kase8.a3] parameter.
 * @property a4Label The label for the [Kase8.a4] parameter.
 * @property a5Label The label for the [Kase8.a5] parameter.
 * @property a6Label The label for the [Kase8.a6] parameter.
 * @property a7Label The label for the [Kase8.a7] parameter.
 * @property a8Label The label for the [Kase8.a8] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
@Poko
public class KaseLabels8(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  public val a4Label: String = "a4",
  public val a5Label: String = "a5",
  public val a6Label: String = "a6",
  public val a7Label: String = "a7",
  public val a8Label: String = "a8",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label, a6Label, a7Label, a8Label)
  }
}