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

public typealias AnyKase = Kase

/** */
public sealed interface Kase : HasDisplayName, HasDisplayNames {

  /** Creates a new [Kase] instance with the given label and value. */
  public fun <A> plus(label: String, value: A): AnyKase

  /** */
  public fun displayNames(delimiter: String = DELIMITER_DEFAULT): List<String>

  /** */
  public fun displayName(
    labelDelimiter: String = DELIMITER_DEFAULT,
    displayNameSeparator: String = SEPARATOR_DEFAULT,
    prefix: String = PREFIX_DEFAULT,
    postfix: String = POSTFIX_DEFAULT
  ): String = displayNames(labelDelimiter).joinToString(
    separator = displayNameSeparator,
    prefix = prefix,
    postfix = postfix
  )

  /** An ordered list of all parameter values and their labels for this kase */
  public val elements: List<KaseParameterWithLabel<Any?>>

  /** An empty [Kase] instance with no parameters. */
  public object EMPTY : KaseInternal {
    override val elements: List<KaseParameterWithLabel<Any?>>
      get() = emptyList()

    override val displayName: String = ""
    override val displayNames: List<String> = emptyList()

    override fun displayNames(delimiter: String): List<String> = displayNames

    override fun <T> plus(label: String, value: T): Kase1<T> {
      return kase(a1 = value, labels = KaseLabels1(label))
    }
  }
}

/** Trait for [labelDelimiter] and [displayNameSeparator] */
public interface HasLabelParameters {
  /**
   * between the label and the value.
   *
   * ex: the ':' in "label: value"
   */
  public val labelDelimiter: String

  /**
   * between each label/value pair.
   *
   * ex: the " | " in "label1: value1 | label2: value2"
   */
  public val displayNameSeparator: String
}

/** */
public interface KaseLabels : HasLabels, HasLabelParameters {

  public companion object {
    /** The default [labelDelimiter] and [displayNameSeparator]. */
    public const val DELIMITER_DEFAULT: String = ": "

    /**
     * The default separator between each label/value pair,
     * like `" | "` in `label1: value1 | label2: value2`
     */
    public const val SEPARATOR_DEFAULT: String = " | "

    /** The default prefix for the display name. */
    public const val PREFIX_DEFAULT: String = "["

    /** The default postfix for the display name. */
    public const val POSTFIX_DEFAULT: String = "]"
  }
}

/**
 * Holds an individual [Kase] parameter and its label.
 *
 * This type supports destructuring, where `component1()`
 * is the label and `component2()` is the value.
 *
 * ex:
 * ```
 * val (label, value) = kaseParam("label", "value")
 * ```
 */
public interface KaseParameterWithLabel<out T> : HasLabel {
  /** The value of the parameter. */
  public val value: T

  /** @return the [label] */
  public operator fun component1(): String = label

  /** @return the [value] */
  public operator fun component2(): T = value

  public companion object {
    /**
     * Creates a new [KaseParameterWithLabel] instance.
     *
     * @param label the label
     * @param value the value
     * @return a new [KaseParameterWithLabel] instance
     * @see KaseParameterWithLabel
     * @see DefaultKaseParameterWithLabel
     */
    public fun <T> kaseParam(label: String, value: T): KaseParameterWithLabel<T> {
      return DefaultKaseParameterWithLabel(value, label)
    }
  }
}

/** Trait for a class which has a label. */
public interface HasLabel {
  /** */
  public val label: String
}

/** Trait for a class which has labels for each parameter. */
public interface HasLabels {
  /**
   * The labels in the order they should be displayed.
   *
   * ex: ["label1", "label2"]
   */
  public val orderedLabels: List<String>
}

/** Trait for a class which has a display name. */
public interface HasDisplayName {

  /**
   * The "name" of a [Kase] as it is used in a test function name. By default, this is a
   * pipe-separated list of the label/value pairs, such as: `[label1: value1 | label2: value2]`.
   *
   * @see HasDisplayNames.displayNames for a list of the label/value pairs.
   */
  public val displayName: String
}

/** Trait for a class which has a list of display names. */
public interface HasDisplayNames {

  /**
   * The "names" of individual [Kase] parameters as they are used in test function names. By
   * default, these are label/value pairs, such as: `["label1: value1", "label2: value2"]`.
   *
   * @see HasDisplayName.displayName for the full display name.
   */
  public val displayNames: List<String>
}

/**
 * A [KaseParameterWithLabel] implementation.
 *
 * @property value the value
 * @property label the label
 */
@Poko
public class DefaultKaseParameterWithLabel<out T>(
  override val value: T,
  override val label: String
) : KaseParameterWithLabel<T>
