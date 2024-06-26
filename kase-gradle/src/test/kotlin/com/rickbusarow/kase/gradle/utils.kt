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

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.kases

inline val dslLanguages: List<DslLanguage>
  get() = kases(
    listOf(true, false),
    listOf(true, false)
  ).flatMap { (useInfix, useLabels) ->
    listOf(
      KotlinDsl(useInfix, useLabels),
      GroovyDsl(useInfix = useInfix, useLabels = useLabels, useDoubleQuotes = true),
      GroovyDsl(useInfix = useInfix, useLabels = useLabels, useDoubleQuotes = false)
    )
  }
