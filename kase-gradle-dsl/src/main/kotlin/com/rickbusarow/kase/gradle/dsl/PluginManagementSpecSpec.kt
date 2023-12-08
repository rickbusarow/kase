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

package com.rickbusarow.kase.gradle.dsl

import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.HasIncludedBuild
import com.rickbusarow.kase.gradle.dsl.model.HasPluginsBlock
import com.rickbusarow.kase.gradle.dsl.model.HasRepositoriesBlock
import com.rickbusarow.kase.gradle.dsl.model.LambdaParameter

/**
 * Builds the `pluginManagement` block in a `settings.gradle` or `settings.gradle.kts` file.
 *
 * @since 0.1.0
 */
public class PluginManagementSpecSpec :
  AbstractDslElementContainer<PluginManagementSpecSpec>(),
  HasRepositoriesBlock<PluginManagementSpecSpec>,
  HasPluginsBlock<PluginManagementSpecSpec>,
  HasIncludedBuild<PluginManagementSpecSpec> {

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   resolutionStrategy {
   *     // ...
   *   }
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun resolutionStrategy(
    block: PluginResolutionStrategySpec.() -> Unit = {}
  ): PluginManagementSpecSpec = functionCall(
    name = "resolutionStrategy",
    labelSupport = LabelSupport.NoLabels,
    infixSupport = InfixSupport.NoInfix,
    LambdaParameter(label = "action", builder = block)
  )
}
