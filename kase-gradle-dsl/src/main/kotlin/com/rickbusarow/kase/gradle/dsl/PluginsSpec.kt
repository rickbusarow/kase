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

package com.rickbusarow.kase.gradle.dsl

import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.PluginApplication

/**
 * Builds a `plugins { }` block in a settings or build file.
 *
 * @since 0.1.0
 */
public class PluginsSpec : AbstractDslElementContainer<PluginsSpec>() {

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
   * ```
   *
   * @since 0.1.0
   */
  public fun id(
    id: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginsSpec = apply {
    addElement(PluginApplication.ID(identifier = id, version = version, apply = apply))
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
   * ```
   *
   * @since 0.1.0
   */
  public fun alias(
    aliasName: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginsSpec = apply {
    addElement(PluginApplication.Alias(aliasName = aliasName, version = version, apply = apply))
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
   * ```
   *
   * @since 0.1.0
   */
  public fun precompiled(
    identifier: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginsSpec = apply {
    addElement(
      PluginApplication.Precompiled(
        identifier = identifier,
        version = version,
        apply = apply
      )
    )
  }

  /**
   * Applies a Kotlin plugin via the `kotlin(...)` syntax if the [DslLanguage] is Kotlin,
   * or via the `id 'org.jetbrains.kotlin.___'` syntax if the [DslLanguage] is Groovy.
   *
   * @since 0.1.0
   */
  public fun kotlin(
    identifier: String,
    version: String? = null,
    apply: Boolean = true
  ): PluginsSpec = apply {
    addElement(
      PluginApplication.KotlinPlugin(
        identifier = identifier,
        version = version,
        apply = apply
      )
    )
  }
}
