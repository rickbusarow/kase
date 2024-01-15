/*
 * Copyright (C) 2024 Rick Busarow
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

package com.rickbusarow.kase.gradle.dsl.model

import com.rickbusarow.kase.gradle.DslLanguage

/**
 * Allows for a different [DslElement] to be used depending on the [DslLanguage].
 *
 * @since 0.1.0
 */
public class DecidingDslElement<T : DslElement>(
  private val kotlinElement: () -> T,
  private val groovyElement: () -> T
) : DslElement {
  override fun write(language: DslLanguage): String {
    return when (language) {
      is DslLanguage.GroovyDsl -> groovyElement().write(language)
      is DslLanguage.KotlinDsl -> kotlinElement().write(language)
    }
  }
}
