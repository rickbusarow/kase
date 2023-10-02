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
public fun <A1, A2, A3, A4> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, labels: KaseLabels4 = KaseLabels4()
): Kase4<A1, A2, A3, A4> {
  return DefaultKase4(
    element(value = a1, label = labels.a1Label),
    element(value = a2, label = labels.a2Label),
    element(value = a3, label = labels.a3Label),
    element(value = a4, label = labels.a4Label)
  )
}


/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2, A3, A4> test(
  a1: A1, a2: A2, a3: A3, a4: A4,
  labels: KaseLabels4 = KaseLabels4(),
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment<K>,
        K : Kase4<A1, A2, A3, A4> {
  test(
    kase = kase(a1, a2, a3, a4, labels),
    testFunctionName = testFunctionName,
    testAction = testAction
  )
}

/** */
public fun labels(
  a1Label: String = "a1",
  a2Label: String = "a2",
  a3Label: String = "a3",
  a4Label: String = "a4"
): KaseLabels4 {
  return KaseLabels4(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label)
}

/** */
public fun <A1, A2, A3, A4> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  labels: KaseLabels4 = KaseLabels4()
): List<Kase4<A1, A2, A3, A4>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, labels = labels))
          }
        }
      }
    }
  }
}


// /** */
// @JvmName("asTestsKase4Kase")
// public inline fun <K, A1, A2, A3, A4> Iterable<Kase4<A1, A2, A3, A4>>.asTests(
//   labels: KaseLabels4 = KaseLabels4(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase4<A1, A2, A3, A4> {
//   return testFactory(this@asTests, labels, testAction)
// }


// /** */
// @JvmName("asTestsKase4Destructured")
// public inline fun <K, A1, A2, A3, A4> Iterable<Kase4<A1, A2, A3, A4>>.asTests(
//   labels: KaseLabels4 = KaseLabels4(),
//   crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase4<A1, A2, A3, A4> {
//   return testFactory(this@asTests, labels, testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2, A3, A4> Iterable<K>.asTests(
  labels: KaseLabels4 = KaseLabels4(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase4<A1, A2, A3, A4> {
  return testFactory(this@asTests, labels, testAction)
}


// /** */
// @JvmName("testFactoryKase4Kase")
// public inline fun <K, A1, A2, A3, A4> testFactory(
//   vararg kases: Kase4<A1, A2, A3, A4>,
//   labels: KaseLabels4 = KaseLabels4(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase4<A1, A2, A3, A4> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }


// /** */
// @JvmName("testFactoryKase4Destructured")
// public inline fun <K, A1, A2, A3, A4> testFactory(
//   vararg kases: Kase4<A1, A2, A3, A4>,
//   labels: KaseLabels4 = KaseLabels4(),
//   crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase4<A1, A2, A3, A4> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2, A3, A4> testFactory(
  vararg kases: K,
  labels: KaseLabels4 = KaseLabels4(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase4<A1, A2, A3, A4> {
  return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
}


// /** */
// @JvmName("testFactoryKase4Kase")
// public inline fun <K, A1, A2, A3, A4> testFactory(
//   kases: Iterable<Kase4<A1, A2, A3, A4>>,
//   labels: KaseLabels4 = KaseLabels4(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase4<A1, A2, A3, A4> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1, it.a2, it.a3, it.a4) }
//   )
// }


// /** */
// @JvmName("testFactoryKase4Destructured")
// public inline fun <K, A1, A2, A3, A4> testFactory(
//   kases: Iterable<Kase4<A1, A2, A3, A4>>,
//   labels: KaseLabels4 = KaseLabels4(),
//   crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase4<A1, A2, A3, A4> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1, it.a2, it.a3, it.a4) }
//   )
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2, A3, A4> testFactory(
  kases: Iterable<K>,
  labels: KaseLabels4 = KaseLabels4(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase4<A1, A2, A3, A4> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { kase -> testAction(kase.a1, kase.a2, kase.a3, kase.a4) }
  )
}

/** */
@JvmName("testFactoryKase4")
public inline fun <A1, A2, A3, A4> testFactory(
  vararg kases: Kase4<A1, A2, A3, A4>,
  crossinline kaseName: (Kase4<A1, A2, A3, A4>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3, it.a4) }
}

/** A strongly-typed version of [Kase] for 4 parameters. */
public interface Kase4<out A1, out A2, out A3, out A4> : Kase<KaseLabels4> {

  /** The 1st parameter. */
  public val a1: A1
  /** The 2nd parameter. */
  public val a2: A2
  /** The 3rd parameter. */
  public val a3: A3
  /** The 4th parameter. */
  public val a4: A4

  override fun <T> plus(label: String, value: T): Kase5<A1, A2, A3, A4, T>
}
/** */
@Poko
internal class DefaultKase4<out A1, out A2, out A3, out A4>(
  val a1Element: KaseParameterWithLabel<A1>,
  val a2Element: KaseParameterWithLabel<A2>,
  val a3Element: KaseParameterWithLabel<A3>,
  val a4Element: KaseParameterWithLabel<A4>
) : Kase4<A1, A2, A3, A4>, KaseInternal<KaseLabels4> {
  override val a1: A1 get() = a1Element.value
  override val a2: A2 get() = a2Element.value
  override val a3: A3 get() = a3Element.value
  override val a4: A4 get() = a4Element.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1Element, a2Element, a3Element, a4Element)

  override fun <T> plus(label: String, value: T): DefaultKase5<A1, A2, A3, A4, T> {
    return DefaultKase5(
      a1Element = a1Element,
      a2Element = a2Element,
      a3Element = a3Element,
      a4Element = a4Element,
      element(value = value, label = label)
    )
  }
}

/**
 * A strongly-typed version of [KaseLabels] for 4 parameters.
 *
 * @property a1Label The label for the [Kase4.a1] parameter.
 * @property a2Label The label for the [Kase4.a2] parameter.
 * @property a3Label The label for the [Kase4.a3] parameter.
 * @property a4Label The label for the [Kase4.a4] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
@Poko
public class KaseLabels4(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  public val a4Label: String = "a4",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label)
  }
}
