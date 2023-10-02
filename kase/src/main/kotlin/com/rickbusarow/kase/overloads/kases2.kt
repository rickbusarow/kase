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
public fun <A1, A2> kase(
  a1: A1, a2: A2, labels: KaseLabels2 = KaseLabels2()
): Kase2<A1, A2> {
  return DefaultKase2(
    element(value = a1, label = labels.a1Label),
    element(value = a2, label = labels.a2Label)
  )
}


/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2> test(
  a1: A1, a2: A2,
  labels: KaseLabels2 = KaseLabels2(),
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment<K>,
        K : Kase2<A1, A2> {
  test(
    kase = kase(a1, a2, labels),
    testFunctionName = testFunctionName,
    testAction = testAction
  )
}

/** */
public fun labels(
  a1Label: String = "a1",
  a2Label: String = "a2"
): KaseLabels2 {
  return KaseLabels2(a1Label = a1Label, a2Label = a2Label)
}

/** */
public fun <A1, A2> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  labels: KaseLabels2 = KaseLabels2()
): List<Kase2<A1, A2>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        add(kase(a1 = a1, a2 = a2, labels = labels))
      }
    }
  }
}


// /** */
// @JvmName("asTestsKase2Kase")
// public inline fun <K, A1, A2> Iterable<Kase2<A1, A2>>.asTests(
//   labels: KaseLabels2 = KaseLabels2(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase2<A1, A2> {
//   return testFactory(this@asTests, labels, testAction)
// }


// /** */
// @JvmName("asTestsKase2Destructured")
// public inline fun <K, A1, A2> Iterable<Kase2<A1, A2>>.asTests(
//   labels: KaseLabels2 = KaseLabels2(),
//   crossinline testAction: (a1: A1, a2: A2) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase2<A1, A2> {
//   return testFactory(this@asTests, labels, testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2> Iterable<K>.asTests(
  labels: KaseLabels2 = KaseLabels2(),
  crossinline testAction: T.(a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase2<A1, A2> {
  return testFactory(this@asTests, labels, testAction)
}


// /** */
// @JvmName("testFactoryKase2Kase")
// public inline fun <K, A1, A2> testFactory(
//   vararg kases: Kase2<A1, A2>,
//   labels: KaseLabels2 = KaseLabels2(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase2<A1, A2> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }


// /** */
// @JvmName("testFactoryKase2Destructured")
// public inline fun <K, A1, A2> testFactory(
//   vararg kases: Kase2<A1, A2>,
//   labels: KaseLabels2 = KaseLabels2(),
//   crossinline testAction: (a1: A1, a2: A2) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase2<A1, A2> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2> testFactory(
  vararg kases: K,
  labels: KaseLabels2 = KaseLabels2(),
  crossinline testAction: T.(a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase2<A1, A2> {
  return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
}


// /** */
// @JvmName("testFactoryKase2Kase")
// public inline fun <K, A1, A2> testFactory(
//   kases: Iterable<Kase2<A1, A2>>,
//   labels: KaseLabels2 = KaseLabels2(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase2<A1, A2> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1, it.a2) }
//   )
// }


// /** */
// @JvmName("testFactoryKase2Destructured")
// public inline fun <K, A1, A2> testFactory(
//   kases: Iterable<Kase2<A1, A2>>,
//   labels: KaseLabels2 = KaseLabels2(),
//   crossinline testAction: (a1: A1, a2: A2) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase2<A1, A2> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1, it.a2) }
//   )
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1, A2> testFactory(
  kases: Iterable<K>,
  labels: KaseLabels2 = KaseLabels2(),
  crossinline testAction: T.(a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase2<A1, A2> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { kase -> testAction(kase.a1, kase.a2) }
  )
}

/** */
@JvmName("testFactoryKase2")
public inline fun <A1, A2> testFactory(
  vararg kases: Kase2<A1, A2>,
  crossinline kaseName: (Kase2<A1, A2>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2) }
}

/** A strongly-typed version of [Kase] for 2 parameters. */
public interface Kase2<out A1, out A2> : Kase<KaseLabels2> {

  /** The 1st parameter. */
  public val a1: A1
  /** The 2nd parameter. */
  public val a2: A2

  override fun <T> plus(label: String, value: T): Kase3<A1, A2, T>
}
/** */
@Poko
internal class DefaultKase2<out A1, out A2>(
  val a1Element: KaseParameterWithLabel<A1>,
  val a2Element: KaseParameterWithLabel<A2>
) : Kase2<A1, A2>, KaseInternal<KaseLabels2> {
  override val a1: A1 get() = a1Element.value
  override val a2: A2 get() = a2Element.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1Element, a2Element)

  override fun <T> plus(label: String, value: T): DefaultKase3<A1, A2, T> {
    return DefaultKase3(
      a1Element = a1Element,
      a2Element = a2Element,
      element(value = value, label = label)
    )
  }
}

/**
 * A strongly-typed version of [KaseLabels] for 2 parameters.
 *
 * @property a1Label The label for the [Kase2.a1] parameter.
 * @property a2Label The label for the [Kase2.a2] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
@Poko
public class KaseLabels2(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label)
  }
}
