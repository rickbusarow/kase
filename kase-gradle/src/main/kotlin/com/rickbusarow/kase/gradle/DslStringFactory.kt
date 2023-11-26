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

package com.rickbusarow.kase.gradle

/** Invoked during [DslLanguage.write] to create a value string. */
public interface DslStringFactory {
  /** @param language the [DslLanguage] being written */
  public fun write(language: DslLanguage): String

  public companion object {
    /** Creates a pass-through [DslStringFactory] which returns the given [value] string. */
    public operator fun invoke(value: String): DslStringFactory {
      return DslStringFactory { value }
    }

    /** Creates a [DslStringFactory] that just invokes [action]. */
    public inline operator fun invoke(
      crossinline action: (DslLanguage) -> String
    ): DslStringFactory = object : DslStringFactory {
      override fun write(language: DslLanguage): String = action(language)
    }
  }
}
