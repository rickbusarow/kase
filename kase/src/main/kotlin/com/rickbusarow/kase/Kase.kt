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

package com.rickbusarow.kase

import com.rickbusarow.kase.KaseLabels.Companion.DELIMITER_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.POSTFIX_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.PREFIX_DEFAULT
import com.rickbusarow.kase.KaseLabels.Companion.SEPARATOR_DEFAULT
import dev.drewhamilton.poko.Poko

/** */
public interface Kase<T : KaseLabels> : HasDisplayName, HasDisplayNames {

  public fun <T> plus(label: String, value: T): AnyKase

  /** */
  public fun displayNames(delimiter: String = DELIMITER_DEFAULT): List<String>

  /** */
  public fun displayName(
    delimiter: String = DELIMITER_DEFAULT,
    separator: String = SEPARATOR_DEFAULT,
    prefix: String = PREFIX_DEFAULT,
    postfix: String = POSTFIX_DEFAULT
  ): String = displayNames(delimiter).joinToString(
    separator = separator,
    prefix = prefix,
    postfix = postfix
  )

  public companion object {
    public val EMPTY: Kase<KaseLabels> = object : Kase<KaseLabels> {

      override val displayName: String = ""
      override val displayNames: List<String> = emptyList()

      override fun displayNames(delimiter: String): List<String> = displayNames

      override fun <T> plus(label: String, value: T): Kase1<T> {
        return kase(a1 = value, labels = KaseLabels1(label))
      }
    }
  }
}

/** */
public interface KaseLabels {

  /**
   * between the label and the value.
   *
   * ex: the ':' in "label: value"
   */
  public val delimiter: String get() = DELIMITER_DEFAULT

  /**
   * between each label/value pair.
   *
   * ex: the " | " in "label1: value1 | label2: value2"
   */
  public val separator: String get() = SEPARATOR_DEFAULT

  /**
   * The labels in the order they should be displayed.
   *
   * ex: ["label1", "label2"]
   */
  public val orderedLabels: List<String>

  public companion object {
    public const val DELIMITER_DEFAULT: String = ": "
    public const val SEPARATOR_DEFAULT: String = " | "
    public const val PREFIX_DEFAULT: String = "["
    public const val POSTFIX_DEFAULT: String = "]"
  }
}

/** */
public interface KaseParameterWithLabel<out T> : HasLabel {
  /** */
  public val value: T

  public operator fun component1(): T = value
  public operator fun component2(): String = label

  public companion object {
    public fun <T> element(value: T, label: String): KaseParameterWithLabel<T> {
      return DefaultKaseParameterWithLabel(value, label)
    }
  }
}

/** Trait for a class which has a label. */
public interface HasLabel {
  /** */
  public val label: String
}

/** */
public interface HasDisplayName {
  /** */
  public val displayName: String
}

/** */
public interface HasDisplayNames {
  /** */
  public val displayNames: List<String>
}

/** */
@Poko
public class DefaultKaseParameterWithLabel<out T>(
  override val value: T,
  override val label: String
) : KaseParameterWithLabel<T>

public typealias AnyKase = Kase<*>

/**
 */
public interface KaseInternal<T : KaseLabels> : Kase<T> {

  /**
   * between the label and the value.
   *
   * ex: the ':' in "label: value"
   */
  public val delimiter: String get() = DELIMITER_DEFAULT

  /**
   * between each label/value pair.
   *
   * ex: the " | " in "label1: value1 | label2: value2"
   */
  public val separator: String get() = SEPARATOR_DEFAULT

  public val elements: List<KaseParameterWithLabel<Any?>>

  override val displayName: String
    get() = displayName(delimiter = delimiter, separator = separator)

  override val displayNames: List<String>
    get() = displayNames(delimiter = delimiter)

  override fun displayNames(delimiter: String): List<String> {
    return elements.map { (label, value) ->
      "$label${delimiter}$value"
    }
  }
}
