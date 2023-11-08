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
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.InfixSupport.GroovyInfix
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.GroovyLabels
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.generation.model.GradleProviderReference.GradlePropertyReference
import com.rickbusarow.kase.gradle.generation.model.HasRepositoriesBlock
import com.rickbusarow.kase.gradle.generation.model.LambdaParameter
import com.rickbusarow.kase.gradle.generation.model.gradlePropertyReference

/**
 * Builds the `dependencyResolutionManagement` block in
 * a `settings.gradle` or `settings.gradle.kts` file.
 */
public class DependencyResolutionManagementSpecSpec :
  AbstractDslElementContainer<DependencyResolutionManagementSpecSpec>(),
  HasRepositoriesBlock<DependencyResolutionManagementSpecSpec> {
  /** */
  public val defaultLibrariesExtensionName: GradlePropertyReference by gradlePropertyReference()

  /** */
  public val defaultProjectsExtensionName: GradlePropertyReference by gradlePropertyReference()

  /** */
  public val rulesMode: GradlePropertyReference by gradlePropertyReference()

  /** */
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
 */
public class ComponentMetadataContainer : AbstractDslElementContainer<ComponentMetadataContainer>()
