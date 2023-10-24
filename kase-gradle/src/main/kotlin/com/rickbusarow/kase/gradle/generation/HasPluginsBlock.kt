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

/** Builds a `plugins` block in a `build.gradle` or `build.gradle.kts` file. */
public interface HasPluginsBlock<SELF : HasPluginsBlock<SELF>> : DslElementContainer<SELF> {

  /**
   * ```
   * // build.gradle
   * plugins {
   *   // ...
   * }
   */
  public fun plugins(
    block: PluginDependenciesSpecBuilder.() -> Unit
  ): SELF = functionCall(
    name = "plugins",
    labelSupport = FunctionCall.LabelSupport.NONE,
    LambdaParameter(builder = block)
  )
}

/** Builds a `repositories` block in a `build.gradle` or `build.gradle.kts` file. */
public interface HasRepositoriesBlock<SELF : HasRepositoriesBlock<SELF>> :
  DslElementContainer<SELF> {

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
  ): SELF = functionCall(
    name = "repositories",
    labelSupport = FunctionCall.LabelSupport.NONE,
    LambdaParameter(builder = block)
  )
}
