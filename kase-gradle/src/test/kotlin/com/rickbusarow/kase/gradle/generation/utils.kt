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

package com.rickbusarow.kase.gradle.generation

import com.rickbusarow.kase.gradle.generation.dsl.MavenArtifactRepositorySpec
import com.rickbusarow.kase.gradle.generation.dsl.PluginManagementSpecSpec
import com.rickbusarow.kase.gradle.generation.dsl.RepositoryHandlerSpec
import com.rickbusarow.kase.gradle.generation.dsl.SettingsFileSpec
import com.rickbusarow.kase.gradle.generation.model.DslLanguage
import com.rickbusarow.kase.gradle.generation.model.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.generation.model.DslLanguage.KotlinDsl
import com.rickbusarow.kase.kases

typealias MavenArtifactRepositoryBuilderAction = MavenArtifactRepositorySpec.() -> Unit
typealias PluginManagementSpecBuilderAction = PluginManagementSpecSpec.() -> Unit
typealias RepositoryHandlerBuilderAction = RepositoryHandlerSpec.() -> Unit
typealias SettingsFileBuilderAction = SettingsFileSpec.() -> Unit

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
