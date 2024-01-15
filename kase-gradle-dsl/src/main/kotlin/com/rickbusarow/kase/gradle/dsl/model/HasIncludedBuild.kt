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

import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport
import com.rickbusarow.kase.gradle.dsl.IncludedBuildSpec

/**
 * Adds either of:
 *
 * ```
 * // settings.gradle(.kts)
 * includeBuild("../someProject")
 *
 * includeBuild("../someProject") {
 *   // ...
 * }
 * ```
 *
 * to a `settings.gradle` or `settings.gradle.kts` file.
 *
 * @since 0.1.0
 */
public interface HasIncludedBuild<SELF : HasIncludedBuild<SELF>> : DslElementContainer<SELF> {

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   includeBuild("someProject")
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun includeBuild(rootProject: String): SELF = functionCall(
    name = "includeBuild",
    labelSupport = LabelSupport.GroovyAndKotlinLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("rootProject", rootProject.asStringLiteral())
  )

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   includeBuild("someProject")
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun includeBuild(rootProject: DslElement): SELF = functionCall(
    name = "includeBuild",
    labelSupport = LabelSupport.GroovyAndKotlinLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("rootProject", rootProject)
  )

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   includeBuild("someProject") {
   *     // ...
   *   }
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun includeBuild(
    rootProject: String,
    block: IncludedBuildSpec.() -> Unit
  ): SELF = functionCall(
    name = "includeBuild",
    labelSupport = LabelSupport.GroovyAndKotlinLabels,
    infixSupport = InfixSupport.NoInfix,
    ValueParameter("rootProject", rootProject.asStringLiteral()),
    LambdaParameter("configuration", block)
  )

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   includeBuild("someProject") {
   *     // ...
   *   }
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun includeBuild(
    rootProject: DslElement,
    block: IncludedBuildSpec.() -> Unit
  ): SELF = functionCall(
    name = "includeBuild",
    labelSupport = LabelSupport.GroovyAndKotlinLabels,
    infixSupport = InfixSupport.NoInfix,
    ValueParameter("rootProject", rootProject),
    LambdaParameter("configuration", block)
  )
}

/**
 * Adds either of:
 *
 * ```
 * // settings.gradle(.kts)
 * include(":someProject")
 *
 * include(
 *   ":someProject",
 *   ":someOtherProject"
 * )
 * ```
 *
 * to a `settings.gradle` or `settings.gradle.kts` file.
 *
 * @since 0.1.0
 */
public interface HasIncludes<SELF : HasIncludedBuild<SELF>> : DslElementContainer<SELF> {

  /**
   * ```
   * // settings.gradle(.kts)
   * include(":someProject")
   *
   * include(
   *   ":someProject",
   *   ":someOtherProject"
   * )
   * ```
   * @param projectPaths the paths to include, such as `":someProject"` or `"../someProject"`
   * @param separator the separator between each path, such as `",\n"`
   * @since 0.1.0
   */
  public fun include(
    vararg projectPaths: String,
    separator: String = ",\n"
  ): SELF = functionCall(
    name = "include",
    labelSupport = LabelSupport.NoLabels,
    infixSupport = InfixSupport.NoInfix,
    *projectPaths.map { ValueParameter(it.asStringLiteral()) }.toTypedArray()
  )
}
