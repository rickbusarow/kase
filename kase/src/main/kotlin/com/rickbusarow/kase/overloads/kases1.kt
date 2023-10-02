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
public fun <A1> kase(
  a1: A1, labels: KaseLabels1 = KaseLabels1()
): Kase1<A1> {
  return DefaultKase1(
    element(value = a1, label = labels.a1Label)
  )
}


/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1> test(
  a1: A1,
  labels: KaseLabels1 = KaseLabels1(),
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment<K>,
        K : Kase1<A1> {
  test(
    kase = kase(a1, labels),
    testFunctionName = testFunctionName,
    testAction = testAction
  )
}

/** */
public fun labels(
  a1Label: String = "a1"
): KaseLabels1 {
  return KaseLabels1(a1Label = a1Label)
}

/** */
public fun <A1> kases(
  args1: Iterable<A1>,
  labels: KaseLabels1 = KaseLabels1()
): List<Kase1<A1>> {
  return buildList {
    for (a1 in args1) {
      add(kase(a1 = a1, labels = labels))
    }
  }
}


// /** */
// @JvmName("asTestsKase1Kase")
// public inline fun <K, A1> Iterable<Kase1<A1>>.asTests(
//   labels: KaseLabels1 = KaseLabels1(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase1<A1> {
//   return testFactory(this@asTests, labels, testAction)
// }


// /** */
// @JvmName("asTestsKase1Destructured")
// public inline fun <K, A1> Iterable<Kase1<A1>>.asTests(
//   labels: KaseLabels1 = KaseLabels1(),
//   crossinline testAction: (a1: A1) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase1<A1> {
//   return testFactory(this@asTests, labels, testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1> Iterable<K>.asTests(
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase1<A1> {
  return testFactory(this@asTests, labels, testAction)
}


// /** */
// @JvmName("testFactoryKase1Kase")
// public inline fun <K, A1> testFactory(
//   vararg kases: Kase1<A1>,
//   labels: KaseLabels1 = KaseLabels1(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase1<A1> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }


// /** */
// @JvmName("testFactoryKase1Destructured")
// public inline fun <K, A1> testFactory(
//   vararg kases: Kase1<A1>,
//   labels: KaseLabels1 = KaseLabels1(),
//   crossinline testAction: (a1: A1) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase1<A1> {
//   return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1> testFactory(
  vararg kases: K,
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase1<A1> {
  return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
}


// /** */
// @JvmName("testFactoryKase1Kase")
// public inline fun <K, A1> testFactory(
//   kases: Iterable<Kase1<A1>>,
//   labels: KaseLabels1 = KaseLabels1(),
//   crossinline testAction: (kase: K) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase1<A1> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1) }
//   )
// }


// /** */
// @JvmName("testFactoryKase1Destructured")
// public inline fun <K, A1> testFactory(
//   kases: Iterable<Kase1<A1>>,
//   labels: KaseLabels1 = KaseLabels1(),
//   crossinline testAction: (a1: A1) -> Unit
// ): Stream<out DynamicNode>
//   where K : Kase1<A1> {
//   return kases.asTests(
//     testName = { it.displayName(labels) },
//     testAction = { testAction(it.a1) }
//   )
// }

/** */
context(TestEnvironmentFactory<T>)
public inline fun <T, K, A1> testFactory(
  kases: Iterable<K>,
  labels: KaseLabels1 = KaseLabels1(),
  crossinline testAction: T.(a1: A1) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase1<A1> {
  return kases.asTests(
    testName = { it.displayName(labels) },
    testAction = { kase -> testAction(kase.a1) }
  )
}

/** */
@JvmName("testFactoryKase1")
public inline fun <A1> testFactory(
  vararg kases: Kase1<A1>,
  crossinline kaseName: (Kase1<A1>) -> String = { it.toString() },
  crossinline testAction: (a1: A1) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1) }
}

/** A strongly-typed version of [Kase] for 1 parameters. */
public interface Kase1<out A1> : Kase<KaseLabels1> {

  /** The 1st parameter. */
  public val a1: A1

  override fun <T> plus(label: String, value: T): Kase2<A1, T>
}
/** */
@Poko
internal class DefaultKase1<out A1>(
  val a1Element: KaseParameterWithLabel<A1>
) : Kase1<A1>, KaseInternal<KaseLabels1> {
  override val a1: A1 get() = a1Element.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1Element)

  override fun <T> plus(label: String, value: T): DefaultKase2<A1, T> {
    return DefaultKase2(
      a1Element = a1Element,
      element(value = value, label = label)
    )
  }
}

/**
 * A strongly-typed version of [KaseLabels] for 1 parameters.
 *
 * @property a1Label The label for the [Kase1.a1] parameter.
 * @property delimiter The delimiter between the label and the value.
 * @property separator The separator between each label/value pair.
 * @property prefix The prefix before the first label/value pair.
 * @property postfix The postfix after the last label/value pair.
 */
@Poko
public class KaseLabels1(
  public val a1Label: String = "a1",
  override val delimiter: String = ": ",
  override val separator: String = " | ",
  override val prefix: String = "[",
  override val postfix: String = "]"
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label)
  }
}
