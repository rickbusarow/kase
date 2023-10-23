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

/** Models a `settings.gradle` or `settings.gradle.kts` file. */
public class SettingsFileBuilder : DslElementContainer() {

  public fun pluginManagement(
    block: PluginManagementSpecBuilder.() -> Unit
  ): SettingsFileBuilder = functionCall(
    name = "pluginManagement",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "block", builder = block)
  )
}

public class PluginManagementSpecBuilder : DslElementContainer() {

  public fun repositories(
    block: RepositoryHandlerBuilder.() -> Unit
  ): PluginManagementSpecBuilder = functionCall(
    name = "repositories",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "repositoriesAction", builder = block)
  )

  public fun resolutionStrategy(
    block: PluginResolutionStrategyBuilder.() -> Unit
  ): PluginManagementSpecBuilder = functionCall(
    name = "resolutionStrategy",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "action", builder = block)
  )

  public fun plugins(
    block: PluginDependenciesSpecBuilder.() -> Unit
  ): PluginManagementSpecBuilder = functionCall(
    name = "plugins",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "action", builder = block)
  )

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

public class PluginResolutionStrategyBuilder : DslElementContainer()

public class IncludedBuildSpecBuilder : DslElementContainer()

public class PluginDependenciesSpecBuilder : DslElementContainer() {

  public fun id(
    id: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.ID(identifier = id, version = version, apply = apply))
  }

  public fun alias(
    aliasName: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.Alias(aliasName = aliasName, version = version, apply = apply))
  }

  public fun precompiled(
    identifier: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.Precompiled(identifier = identifier, version = version, apply = apply))
  }

  public fun kotlinPlugin(
    identifier: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.KotlinPlugin(identifier = identifier, version = version, apply = apply))
  }
}
