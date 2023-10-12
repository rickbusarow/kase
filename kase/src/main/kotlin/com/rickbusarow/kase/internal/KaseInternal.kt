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

package com.rickbusarow.kase.internal

import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.KaseLabels
import com.rickbusarow.kase.KaseParameterWithLabel

/** Common implementation details for a [Kase]. */
public interface KaseInternal : Kase {

  /**
   * between the label and the value.
   *
   * ex: the ':' in "label: value"
   */
  public val labelDelimiter: String get() = KaseLabels.DELIMITER_DEFAULT

  /**
   * between each label/value pair.
   *
   * ex: the " | " in "label1: value1 | label2: value2"
   */
  public val displayNameSeparator: String get() = KaseLabels.SEPARATOR_DEFAULT

  public val elements: List<KaseParameterWithLabel<Any?>>

  override val displayName: String
    get() = displayName(
      labelDelimiter = labelDelimiter,
      displayNameSeparator = displayNameSeparator
    )

  override val displayNames: List<String>
    get() = displayNames(delimiter = labelDelimiter)

  override fun displayNames(delimiter: String): List<String> {
    return elements.map { (label, value) ->
      "$label${delimiter}$value"
    }
  }
}
