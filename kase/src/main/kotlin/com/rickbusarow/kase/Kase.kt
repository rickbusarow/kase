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

import dev.drewhamilton.poko.Poko

/** */
public interface Kase<T : KaseLabels> {

  public fun <T> plus(label: String, value: T): AnyKase

  /** */
  public fun displayNames(labels: T): List<String>

  /** */
  public fun displayNames(delimiter: String = ": "): List<String>

  /** */
  public fun displayName(labels: T): String = displayNames(labels).joinToString(
    separator = labels.separator,
    prefix = labels.prefix,
    postfix = labels.postfix
  )
}

/** */
public interface KaseLabels {

  /**
   * between the label and the value.
   *
   * ex: the ':' in "label: value"
   */
  public val delimiter: String get() = ": "

  /** */
  public val separator: String get() = " | "

  /** */
  public val prefix: String get() = "["

  /** */
  public val postfix: String get() = "]"

  /** */
  public val orderedLabels: List<String>
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

/** */
public interface HasLabel {
  /** */
  public val label: String
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

  public val elements: List<KaseParameterWithLabel<Any?>>

  /** */
  override fun displayNames(labels: T): List<String> {
    return labels.orderedLabels.zip(elements)
      .map { (label, element) ->
        "$label${labels.delimiter}${element.value}"
      }
  }

  /** */
  override fun displayNames(delimiter: String): List<String> {
    return elements.map { (label, value) ->
      "$label${delimiter}$value"
    }
  }
}
