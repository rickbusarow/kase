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

/** Builds the `pluginManagement` block in a `settings.gradle` or `settings.gradle.kts` file. */
public class PluginManagementSpecBuilder : DslElementContainer() {

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   repositories {
   *     // ...
   *   }
   * }
   */
  public fun repositories(
    block: RepositoryHandlerBuilder.() -> Unit
  ): PluginManagementSpecBuilder = functionCall(
    name = "repositories",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "repositoriesAction", builder = block)
  )

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   resolutionStrategy {
   *     // ...
   *   }
   * }
   */
  public fun resolutionStrategy(
    block: PluginResolutionStrategyBuilder.() -> Unit
  ): PluginManagementSpecBuilder = functionCall(
    name = "resolutionStrategy",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "action", builder = block)
  )

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   plugins {
   *     // ...
   *   }
   * }
   */
  public fun plugins(
    block: PluginDependenciesSpecBuilder.() -> Unit
  ): PluginManagementSpecBuilder = functionCall(
    name = "plugins",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "action", builder = block)
  )

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   includeBuild("someProject") {
   *     // ...
   *   }
   * }
   */
  public fun includeBuild(
    rootProject: String,
    block: IncludedBuildSpecBuilder.() -> Unit
  ): PluginManagementSpecBuilder = functionCall(
    name = "includeBuild",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    ValueParameter("rootProject", rootProject),
    LambdaParameter(label = "configuration", builder = block)
  )
}