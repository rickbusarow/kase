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

@file:Suppress("PackageDirectoryMismatch", "DuplicatedCode", "MaxLineLength")
@file:JvmMultifileClass
@file:JvmName("KasesKt")

package com.rickbusarow.kase

import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.POSTFIX_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.PREFIX_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.element
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream
/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase3:a1] parameter.
 * @param a2 the [Kase3:a2] parameter.
 * @param a3 the [Kase3:a3] parameter.
 * @param labels the [KaseLabels] to use for this [Kase]
 * @param delimiter the delimiter between the label and the value, like `": "` in `"label: value"`
 * @param separator the separator between each label/value pair, like `" | "` in `"label1: value1 | label2: value2"`
 */
public fun <A1, A2, A3> kase(
  a1: A1, a2: A2, a3: A3,
  labels: KaseLabels3 = KaseLabels3(),
  delimiter: String = KaseLabels.DELIMITER_DEFAULT,
  separator: String = KaseLabels.SEPARATOR_DEFAULT
): Kase3<A1, A2, A3> {
  return DefaultKase3(
    element(value = a1, label = labels.a1Label),
    element(value = a2, label = labels.a2Label),
    element(value = a3, label = labels.a3Label),
    delimiter = delimiter,
    separator = separator
  )
}

/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2, A3> test(
  a1: A1, a2: A2, a3: A3,
  labels: KaseLabels3 = KaseLabels3(),
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment<K>,
        K : Kase3<A1, A2, A3> {
  test(
    kase = kase(a1, a2, a3, labels),
    testFunctionName = testFunctionName,
    testAction = testAction
  )
}

/** */
public fun labels(
  a1Label: String = "a1",
  a2Label: String = "a2",
  a3Label: String = "a3"
): KaseLabels3 {
  return KaseLabels3(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label)
}

/** */
public fun <A1, A2, A3> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  labels: KaseLabels3 = KaseLabels3()
): List<Kase3<A1, A2, A3>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          add(kase(a1 = a1, a2 = a2, a3 = a3, labels = labels))
        }
      }
    }
  }
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase3DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3> Iterable<K>.asTests(
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase3<A1, A2, A3> {
  return testFactory(this@asTests, labels, testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase3DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3> testFactory(
  vararg kases: K,
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase3<A1, A2, A3> {
  return testFactory(kases = kases.toList(), labels = labels, testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase3DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3> testFactory(
  kases: Iterable<K>,
  labels: KaseLabels3 = KaseLabels3(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase3<A1, A2, A3> {

  return kases.asTests(
    testName = { kase -> kase.displayName() },
    testAction = { kase -> testAction(kase.a1, kase.a2, kase.a3) }
  )
}

/** */
@JvmName("testFactoryKase3")
public inline fun <A1, A2, A3> testFactory(
  vararg kases: Kase3<A1, A2, A3>,
  crossinline kaseName: (Kase3<A1, A2, A3>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3) }
}

/** A strongly-typed version of [Kase] for 3 parameters. */
public interface Kase3<out A1, out A2, out A3> : Kase<KaseLabels3> {

  /** The 1st parameter. */
  public val a1: A1
  /** The 2nd parameter. */
  public val a2: A2
  /** The 3rd parameter. */
  public val a3: A3

  override fun <T> plus(label: String, value: T): Kase4<A1, A2, A3, T>
}

/**
 * A strongly-typed version of [KaseLabels] for 3 parameters.
 *
 * @property a1Label The label for the [Kase3.a1] parameter.
 * @property a2Label The label for the [Kase3.a2] parameter.
 * @property a3Label The label for the [Kase3.a3] parameter.
 * @property delimiter The delimiter between the label and the value.  The default is `: `.
 * @property separator The separator between each label/value pair.  The default is ` | `.
 */
@Poko
public class KaseLabels3(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  override val delimiter: String = DELIMITER_DEFAULT,
  override val separator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label)
  }
}

@Poko
internal class DefaultKase3<out A1, out A2, out A3>(
  val a1Element: KaseParameterWithLabel<A1>,
  val a2Element: KaseParameterWithLabel<A2>,
  val a3Element: KaseParameterWithLabel<A3>,
  override val delimiter: String,
  override val separator: String,
) : Kase3<A1, A2, A3>, KaseInternal<KaseLabels3> {
  override val a1: A1 get() = a1Element.value
  override val a2: A2 get() = a2Element.value
  override val a3: A3 get() = a3Element.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1Element, a2Element, a3Element)

  override fun <T> plus(label: String, value: T): DefaultKase4<A1, A2, A3, T> {
    return DefaultKase4(
      a1Element = a1Element,
      a2Element = a2Element,
      a3Element = a3Element,
      a4Element = element(value = value, label = label),
      delimiter = delimiter,
      separator = separator
    )
  }
}
