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

package com.rickbusarow.kase.gradle.generation.dsl

import com.rickbusarow.kase.gradle.generation.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.generation.model.DslFileBuilder
import com.rickbusarow.kase.gradle.generation.model.FunctionCall
import com.rickbusarow.kase.gradle.generation.model.HasIncludedBuild
import com.rickbusarow.kase.gradle.generation.model.HasIncludes
import com.rickbusarow.kase.gradle.generation.model.HasPluginsBlock
import com.rickbusarow.kase.gradle.generation.model.LambdaParameter
import com.rickbusarow.kase.gradle.generation.model.RegularVariableReference.MutableVariableReference
import com.rickbusarow.kase.gradle.generation.model.mutableVariableReference

/** Models a `settings.gradle` or `settings.gradle.kts` file. */
public class SettingsFileSpec(
  build: SettingsFileSpec.() -> Unit
) : AbstractDslElementContainer<SettingsFileSpec>(),
  DslFileBuilder<SettingsFileSpec>,
  HasPluginsBlock<SettingsFileSpec>,
  HasIncludes<SettingsFileSpec>,
  HasIncludedBuild<SettingsFileSpec> {

  /**
   * ```
   * rootProject.name = 'my-project'
   * ```
   */
  public val rootProjectName: MutableVariableReference<String>
    by mutableVariableReference("rootProject.name") { stringLiteral(it) }

  init {
    build()
  }

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   // ...
   * }
   * ```
   */
  public fun pluginManagement(
    block: PluginManagementSpecSpec.() -> Unit
  ): SettingsFileSpec = functionCall(
    name = "pluginManagement",
    labelSupport = FunctionCall.LabelSupport.NoLabels,
    infixSupport = FunctionCall.InfixSupport.NoInfix,
    LambdaParameter(block)
  )

  /**
   * ```
   * // settings.gradle
   * dependencyResolutionManagement {
   *   // ...
   * }
   * ```
   */
  public fun dependencyResolutionManagement(
    block: DependencyResolutionManagementSpecSpec.() -> Unit
  ): SettingsFileSpec = functionCall(
    name = "dependencyResolutionManagement",
    labelSupport = FunctionCall.LabelSupport.NoLabels,
    infixSupport = FunctionCall.InfixSupport.NoInfix,
    LambdaParameter(block)
  )
}
