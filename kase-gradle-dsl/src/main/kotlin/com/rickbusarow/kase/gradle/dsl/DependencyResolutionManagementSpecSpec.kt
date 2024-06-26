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

import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.GroovyInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.GroovyLabels
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.GradleProviderReference.GradlePropertyReference
import com.rickbusarow.kase.gradle.dsl.model.HasRepositoriesBlock
import com.rickbusarow.kase.gradle.dsl.model.LambdaParameter
import com.rickbusarow.kase.gradle.dsl.model.gradlePropertyReference

/**
 * Builds the `dependencyResolutionManagement` block in
 * a `settings.gradle` or `settings.gradle.kts` file.
 *
 * @since 0.1.0
 */
public class DependencyResolutionManagementSpecSpec :
  AbstractDslElementContainer<DependencyResolutionManagementSpecSpec>(),
  HasRepositoriesBlock<DependencyResolutionManagementSpecSpec> {
  /** @since 0.1.0 */
  public val defaultLibrariesExtensionName: GradlePropertyReference by gradlePropertyReference()

  /** @since 0.1.0 */
  public val defaultProjectsExtensionName: GradlePropertyReference by gradlePropertyReference()

  /** @since 0.1.0 */
  public val rulesMode: GradlePropertyReference by gradlePropertyReference()

  /** @since 0.1.0 */
  public val repositoriesMode: GradlePropertyReference by gradlePropertyReference()

  /**
   * ```
   * // settings.gradle
   * dependencyResolutionManagement {
   *   resolutionStrategy {
   *     // ...
   *   }
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun versionCatalogs(
    block: MutableVersionCatalogContainer.() -> Unit
  ): DependencyResolutionManagementSpecSpec = functionCall(
    name = "versionCatalogs",
    labelSupport = GroovyLabels,
    infixSupport = GroovyInfix,
    LambdaParameter(label = "spec", builder = block)
  )

  /**
   * ```
   * // settings.gradle
   * dependencyResolutionManagement {
   *   components {
   *     // ...
   *   }
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun components(
    block: ComponentMetadataContainer.() -> Unit
  ): DependencyResolutionManagementSpecSpec = functionCall(
    name = "components",
    labelSupport = NoLabels,
    infixSupport = GroovyInfix,
    LambdaParameter(builder = block)
  )
}

/**
 * ```
 * // settings.gradle
 * dependencyResolutionManagement {
 *   resolutionStrategy {
 *     // ...
 *   }
 * }
 * ```
 *
 * @since 0.1.0
 */
public class MutableVersionCatalogContainer : AbstractDslElementContainer<MutableVersionCatalogContainer>()

/**
 * ```
 * // settings.gradle
 * dependencyResolutionManagement {
 *   components {
 *     // ...
 *   }
 * }
 * ```
 *
 * @since 0.1.0
 */
public class ComponentMetadataContainer : AbstractDslElementContainer<ComponentMetadataContainer>()
