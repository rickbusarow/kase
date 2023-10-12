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
import com.rickbusarow.kase.KaseParameterWithLabel.Companion.kaseParam
import com.rickbusarow.kase.internal.KaseInternal
import dev.drewhamilton.poko.Poko
import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

/** A strongly-typed version of [Kase] for 3 parameters. */
public interface Kase3<out A1, out A2, out A3> : Kase {

  /** The 1st parameter. */
  public val a1: A1
  /** The 1st parameter. */
  public val a1WithLabel: KaseParameterWithLabel<A1>
  /** The 2nd parameter. */
  public val a2: A2
  /** The 2nd parameter. */
  public val a2WithLabel: KaseParameterWithLabel<A2>
  /** The 3rd parameter. */
  public val a3: A3
  /** The 3rd parameter. */
  public val a3WithLabel: KaseParameterWithLabel<A3>

  public val labelDelimiter: String get() = KaseLabels.DELIMITER_DEFAULT

  public val displayNameSeparator: String get() = KaseLabels.SEPARATOR_DEFAULT

  override fun <A4> plus(label: String, value: A4): Kase4<A1, A2, A3, A4> {
    return DefaultKase4(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase3:a1] parameter.
 * @param a2 the [Kase3:a2] parameter.
 * @param a3 the [Kase3:a3] parameter.
 * @param labels the [KaseLabels] to use for this [Kase]
 * @param labelDelimiter the delimiter between the label and the value, like `": "` in `"label: value"`
 * @param displayNameSeparator the separator between each label/value pair, like `" | "` in `"label1: value1 | label2: value2"`
 */
public fun <A1, A2, A3> kase(
  a1: A1, a2: A2, a3: A3,
  labels: KaseLabels3 = KaseLabels3(),
  labelDelimiter: String = DELIMITER_DEFAULT,
  displayNameSeparator: String = SEPARATOR_DEFAULT
): Kase3<A1, A2, A3> {
  return DefaultKase3(
    a1WithLabel = kaseParam(value = a1, label = labels.a1Label),
    a2WithLabel = kaseParam(value = a2, label = labels.a2Label),
    a3WithLabel = kaseParam(value = a3, label = labels.a3Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
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
  return testFactory(kases = this@asTests, testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase3DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3> testFactory(
  vararg kases: K,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase3<A1, A2, A3> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase3DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3> testFactory(
  kases: Iterable<K>,
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

/**
 * A strongly-typed version of [KaseLabels] for 3 parameters.
 *
 * @property a1Label The label for the [Kase3.a1] parameter.
 * @property a2Label The label for the [Kase3.a2] parameter.
 * @property a3Label The label for the [Kase3.a3] parameter.
 * @property labelDelimiter The delimiter between the label and the value.  The default is `: `.
 * @property displayNameSeparator The separator between each label/value pair.  The default is ` | `.
 */
@Poko
public class KaseLabels3(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label)
  }
}

@Poko
internal class DefaultKase3<out A1, out A2, out A3>(
  override val a1WithLabel: KaseParameterWithLabel<A1>,
  override val a2WithLabel: KaseParameterWithLabel<A2>,
  override val a3WithLabel: KaseParameterWithLabel<A3>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase3<A1, A2, A3>, KaseInternal {
  override val a1: A1 get() = a1WithLabel.value
  override val a2: A2 get() = a2WithLabel.value
  override val a3: A3 get() = a3WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel)

  override fun <A4> plus(label: String, value: A4): DefaultKase4<A1, A2, A3, A4> {
    return DefaultKase4(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}
