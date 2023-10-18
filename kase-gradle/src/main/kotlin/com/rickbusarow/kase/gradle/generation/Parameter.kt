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

@file:Suppress("MemberVisibilityCanBePrivate")

package com.rickbusarow.kase.gradle.generation

import dev.drewhamilton.poko.Poko

/**
 * A parameter with an optional label, such as `group: "com.acme"` or `"com.acme"`.
 *
 * @property label the label, such as `group` in `group: "com.acme"`
 * @property valueString the value, such as `"com.acme"`
 */
@Poko
public class Parameter(
  public val label: String?,
  public val valueString: String
) : DslBuilderComponent {
  public constructor(value: String) : this(label = null, valueString = value)

  override fun write(language: DslLanguage): String {
    return if (label != null) {
      "$label${language.labelDelimiter} $valueString"
    } else {
      valueString
    }
  }

  public companion object {
    /** Joins a list of [Parameter]s into a [ParameterList]. */
    public fun Iterable<Parameter>.join(separator: String = ", "): ParameterList {
      return ParameterList(parameters = toList(), separator = separator)
    }
  }
}
