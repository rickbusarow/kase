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

/** Interface for any DSL builder components. */
public interface DslFileBuilder : DslElement {
  /** Wraps a String literal in language-specific quotes */
  public fun quoted(stringValue: String): Quoted = Quoted(stringValue)
}

/** Models a `settings.gradle` or `settings.gradle.kts` file. */
public class SettingsFileBuilder(build: SettingsFileBuilder.() -> Unit) :
  DslElementContainer(),
  DslFileBuilder {

  init {
    build()
  }

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   // ...
   * }
   */
  public fun pluginManagement(
    block: PluginManagementSpecBuilder.() -> Unit
  ): SettingsFileBuilder = functionCall(
    name = "pluginManagement",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "block", builder = block)
  )
}

/**
 * Builds the `pluginManagement.resolutionStrategy { }` block
 * in a `settings.gradle` or `settings.gradle.kts` file.
 */
public class PluginResolutionStrategyBuilder : DslElementContainer()

/**
 * Builds an `includeBuild(...) { }` configuration block
 * in a `settings.gradle` or `settings.gradle.kts` file.
 */
public class IncludedBuildSpecBuilder : DslElementContainer()

/**
 * Builds a `pluginManagement.plugins { }` block in
 * a `settings.gradle` or `settings.gradle.kts` file.
 */
public class PluginDependenciesSpecBuilder : DslElementContainer() {

  /**
   * Applies a plugin via the typical `id(...)` syntax.
   *
   * ```
   * // settings.gradle
   * pluginManagement {
   *   plugins {
   *     id("somePlugin") version "1.0.0"
   *   }
   * }
   */
  public fun id(
    id: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.ID(identifier = id, version = version, apply = apply))
  }

  /**
   * Applies a plugin via the `alias(...)` syntax for a version catalog `plugins` entry.
   *
   * ```
   * // settings.gradle
   * pluginManagement {
   *   plugins {
   *     alias(libs.plugins.kotlin.jvm)
   *   }
   * }
   */
  public fun alias(
    aliasName: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.Alias(aliasName = aliasName, version = version, apply = apply))
  }

  /**
   * Applies a precompiled plugin like `base` or `java-platform`. If the [DslLanguage] is Kotlin
   * and the plugin name has a hyphen, the invocation will be automatically wrapped in backticks.
   *
   * ```
   * // settings.gradle
   * pluginManagement {
   *   plugins {
   *     `java-platform`
   *   }
   * }
   */
  public fun precompiled(
    identifier: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.Precompiled(identifier = identifier, version = version, apply = apply))
  }

  /**
   * Applies a Kotlin plugin via the `kotlin(...)` syntax if the [DslLanguage] is Kotlin,
   * or via the `id 'org.jetbrains.kotlin.___'` syntax if the [DslLanguage] is Groovy.
   */
  public fun kotlinPlugin(
    identifier: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginDependenciesSpecBuilder = apply {
    add(PluginApplication.KotlinPlugin(identifier = identifier, version = version, apply = apply))
  }
}
