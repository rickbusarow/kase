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

/** A strongly-typed version of [Kase] for 5 parameters. */
public interface Kase5<out A1, out A2, out A3, out A4, out A5> : Kase {

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
  /** The 4th parameter. */
  public val a4: A4
  /** The 4th parameter. */
  public val a4WithLabel: KaseParameterWithLabel<A4>
  /** The 5th parameter. */
  public val a5: A5
  /** The 5th parameter. */
  public val a5WithLabel: KaseParameterWithLabel<A5>

  public val labelDelimiter: String get() = KaseLabels.DELIMITER_DEFAULT

  public val displayNameSeparator: String get() = KaseLabels.SEPARATOR_DEFAULT

  override fun <A6> plus(label: String, value: A6): Kase6<A1, A2, A3, A4, A5, A6> {
    return DefaultKase6(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = a5WithLabel,
      a6WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}

/**
 * Creates a new [Kase] with the given parameters.
 *
 * @param a1 the [Kase5:a1] parameter.
 * @param a2 the [Kase5:a2] parameter.
 * @param a3 the [Kase5:a3] parameter.
 * @param a4 the [Kase5:a4] parameter.
 * @param a5 the [Kase5:a5] parameter.
 * @param labels the [KaseLabels] to use for this [Kase]
 * @param labelDelimiter the delimiter between the label and the value, like `": "` in `"label: value"`
 * @param displayNameSeparator the separator between each label/value pair, like `" | "` in `"label1: value1 | label2: value2"`
 */
public fun <A1, A2, A3, A4, A5> kase(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5,
  labels: KaseLabels5 = KaseLabels5(),
  labelDelimiter: String = DELIMITER_DEFAULT,
  displayNameSeparator: String = SEPARATOR_DEFAULT
): Kase5<A1, A2, A3, A4, A5> {
  return DefaultKase5(
    a1WithLabel = kaseParam(value = a1, label = labels.a1Label),
    a2WithLabel = kaseParam(value = a2, label = labels.a2Label),
    a3WithLabel = kaseParam(value = a3, label = labels.a3Label),
    a4WithLabel = kaseParam(value = a4, label = labels.a4Label),
    a5WithLabel = kaseParam(value = a5, label = labels.a5Label),
    labelDelimiter = labelDelimiter,
    displayNameSeparator = displayNameSeparator
  )
}

/** */
context(TestEnvironmentFactory<T>)
public fun <T, K, A1, A2, A3, A4, A5> test(
  a1: A1, a2: A2, a3: A3, a4: A4, a5: A5,
  labels: KaseLabels5 = KaseLabels5(),
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testAction: suspend T.() -> Unit
) where T : TestEnvironment<K>,
        K : Kase5<A1, A2, A3, A4, A5> {
  test(
    kase = kase(a1, a2, a3, a4, a5, labels),
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
  a5Label: String = "a5"
): KaseLabels5 {
  return KaseLabels5(a1Label = a1Label, a2Label = a2Label, a3Label = a3Label, a4Label = a4Label, a5Label = a5Label)
}

/** */
public fun <A1, A2, A3, A4, A5> kases(
  args1: Iterable<A1>,
  args2: Iterable<A2>,
  args3: Iterable<A3>,
  args4: Iterable<A4>,
  args5: Iterable<A5>,
  labels: KaseLabels5 = KaseLabels5()
): List<Kase5<A1, A2, A3, A4, A5>> {
  return buildList {
    for (a1 in args1) {
      for (a2 in args2) {
        for (a3 in args3) {
          for (a4 in args4) {
            for (a5 in args5) {
              add(kase(a1 = a1, a2 = a2, a3 = a3, a4 = a4, a5 = a5, labels = labels))
            }
          }
        }
      }
    }
  }
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("asTestsKase5DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5> Iterable<K>.asTests(
  labels: KaseLabels5 = KaseLabels5(),
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase5<A1, A2, A3, A4, A5> {
  return testFactory(kases = this@asTests, testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase5DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5> testFactory(
  vararg kases: K,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase5<A1, A2, A3, A4, A5> {
  return testFactory(kases = kases.toList(), testAction = testAction)
}

/** */
context(TestEnvironmentFactory<T>)
@JvmName("testFactoryKase5DestructuredTestEnvironment")
public inline fun <T, K, A1, A2, A3, A4, A5> testFactory(
  kases: Iterable<K>,
  crossinline testAction: T.(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode>
  where T : TestEnvironment<K>,
        K : Kase5<A1, A2, A3, A4, A5> {

  return kases.asTests(
    testName = { kase -> kase.displayName() },
    testAction = { kase -> testAction(kase.a1, kase.a2, kase.a3, kase.a4, kase.a5) }
  )
}

/** */
@JvmName("testFactoryKase5")
public inline fun <A1, A2, A3, A4, A5> testFactory(
  vararg kases: Kase5<A1, A2, A3, A4, A5>,
  crossinline kaseName: (Kase5<A1, A2, A3, A4, A5>) -> String = { it.toString() },
  crossinline testAction: (a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) -> Unit
): Stream<out DynamicNode> {
  return kases.asSequence().asTests(kaseName) { testAction(it.a1, it.a2, it.a3, it.a4, it.a5) }
}

/**
 * A strongly-typed version of [KaseLabels] for 5 parameters.
 *
 * @property a1Label The label for the [Kase5.a1] parameter.
 * @property a2Label The label for the [Kase5.a2] parameter.
 * @property a3Label The label for the [Kase5.a3] parameter.
 * @property a4Label The label for the [Kase5.a4] parameter.
 * @property a5Label The label for the [Kase5.a5] parameter.
 * @property labelDelimiter The delimiter between the label and the value.  The default is `: `.
 * @property displayNameSeparator The separator between each label/value pair.  The default is ` | `.
 */
@Poko
public class KaseLabels5(
  public val a1Label: String = "a1",
  public val a2Label: String = "a2",
  public val a3Label: String = "a3",
  public val a4Label: String = "a4",
  public val a5Label: String = "a5",
  override val labelDelimiter: String = DELIMITER_DEFAULT,
  override val displayNameSeparator: String = SEPARATOR_DEFAULT
) : KaseLabels {

  override val orderedLabels: List<String> by lazy {
    listOf(a1Label, a2Label, a3Label, a4Label, a5Label)
  }
}

@Poko
internal class DefaultKase5<out A1, out A2, out A3, out A4, out A5>(
  override val a1WithLabel: KaseParameterWithLabel<A1>,
  override val a2WithLabel: KaseParameterWithLabel<A2>,
  override val a3WithLabel: KaseParameterWithLabel<A3>,
  override val a4WithLabel: KaseParameterWithLabel<A4>,
  override val a5WithLabel: KaseParameterWithLabel<A5>,
  override val labelDelimiter: String,
  override val displayNameSeparator: String,
) : Kase5<A1, A2, A3, A4, A5>, KaseInternal {
  override val a1: A1 get() = a1WithLabel.value
  override val a2: A2 get() = a2WithLabel.value
  override val a3: A3 get() = a3WithLabel.value
  override val a4: A4 get() = a4WithLabel.value
  override val a5: A5 get() = a5WithLabel.value

  override val elements: List<KaseParameterWithLabel<Any?>>
    get() = listOf(a1WithLabel, a2WithLabel, a3WithLabel, a4WithLabel, a5WithLabel)

  override fun <A6> plus(label: String, value: A6): DefaultKase6<A1, A2, A3, A4, A5, A6> {
    return DefaultKase6(
      a1WithLabel = a1WithLabel,
      a2WithLabel = a2WithLabel,
      a3WithLabel = a3WithLabel,
      a4WithLabel = a4WithLabel,
      a5WithLabel = a5WithLabel,
      a6WithLabel = kaseParam(label = label, value = value),
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )
  }
}
