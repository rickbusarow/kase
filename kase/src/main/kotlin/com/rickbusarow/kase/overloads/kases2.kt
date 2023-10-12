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
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParameterWithLabel
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase2:a1] parameter.
 * @param a2 the [Kase2:a2] parameter.
 * @param labels the [KaseLabels] to use for this [Kase]
 * @param labelDelimiter the delimiter between the label and the value, like `": "` in `"label: value"`
 * @param displayNameSeparator the separator between each label/value pair, like `" | "` in `"label1: value1 | label2: value2"`
 */
public fun <A1, A2> kase(
  a1: A1, a2: A2,
  labels: KaseLabels2 = KaseLabels2(),
  labelDelimiter: String = KaseLabels.DELIMITER_DEFAULT,
  displayNameSeparator: String = KaseLabels.SEPARATOR_DEFAULT
): Kase2<A1, A2> {
  return DefaultKase2(
    kaseParameterWithLabel(value = a1, label = labels.a1Label),
    kaseParameterWithLabel(value = a2, label = labels.a2Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
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

/** */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase2DestructuredTestEnvironment")
public inline fun <T, K, A1, A2> Iterable<K>.asTests(
  labels: KaseLabels2 = KaseLabels2(),
  crossinline testAction: T.(a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase2<A1, A2> {
  return testFactory(kases = this@asTests, testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase2DestructuredTestEnvironment")
public inline fun <T, K, A1, A2> testFactory(
  vararg kases: K,
  crossinline testAction: T.(a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase2<A1, A2> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase2DestructuredTestEnvironment")
public inline fun <T, K, A1, A2> testFactory(
  kases: Iterable<K>,
  crossinline testAction: T.(a1: A1, a2: A2) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase2<A1, A2> {

  return kases.asTests(
    testName = { kase -> kase.displayName() },
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
  /** The 1st parameter. */
  public val a1WithLabel: KaseParameterWithLabel<A1>
  /** The 2nd parameter. */
  public val a2: A2
  /** The 2nd parameter. */
  public val a2WithLabel: KaseParameterWithLabel<A2>

  public val labelDelimiter: String get() = KaseLabels.DELIMITER_DEFAULT

  public val displayNameSeparator: String get() = KaseLabels.SEPARATOR_DEFAULT

  override fun <T> plus(label: String, value: T): Kase3<A1, A2, T> {
    return DefaultKase3(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = kaseParameterWithLabel(value = value, label = label),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

/**
 * A strongly-typed version of [KaseLabels] for 2 parameters.
 *
 * @property a1Label The label for the [Kase2.a1] parameter.
 * @property a2Label The label for the [Kase2.a2] parameter.
 * @property labelDelimiter The delimiter between the label and the value.  The default is `: `.
 * @property displayNameSeparator The separator between each label/value pair.  The default is ` | `.
 */
@Poko
public class KaseLabels2(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label)
  }
}

@Poko
internal class DefaultKase2<out A1, out A2>(
  override val a1WithLabel: KaseParameterWithLabel<A1>,
  override val a2WithLabel: KaseParameterWithLabel<A2>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase2<A1, A2>, KaseInternal<KaseLabels2> {
  override val a1: A1 get() = a1WithLabel.value
  override val a2: A2 get() = a2WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel)

  override fun <T> plus(label: String, value: T): DefaultKase3<A1, A2, T> {
    return DefaultKase3(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = kaseParameterWithLabel(value = value, label = label),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}
