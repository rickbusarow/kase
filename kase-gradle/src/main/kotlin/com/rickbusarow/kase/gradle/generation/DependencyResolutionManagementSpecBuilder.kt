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

import com.rickbusarow.kase.gradle.generation.RegularVariableReference.ImmutableVariableReference
import com.rickbusarow.kase.gradle.generation.RegularVariableReference.MutableVariableReference
import kotlin.properties.ReadOnlyProperty

internal fun gradlePropertyReference(): ReadOnlyProperty<AbstractDslElementContainer<*>, GradlePropertyReference> {
  return ReadOnlyProperty { container, kProperty ->
    GradlePropertyReference(kProperty.name, container)
  }
}

internal fun regularVariableReference(
  mutable: Boolean
): ReadOnlyProperty<AbstractDslElementContainer<*>, RegularVariableReference> {
  return ReadOnlyProperty { container, kProperty ->
    if (mutable) {
      MutableVariableReference(kProperty.name, container)
    } else {
      ImmutableVariableReference(kProperty.name, container)
    }
  }
}

/**
 * Builds the `dependencyResolutionManagement` block in
 * a `settings.gradle` or `settings.gradle.kts` file.
 */
public class DependencyResolutionManagementSpecBuilder :
  AbstractDslElementContainer<DependencyResolutionManagementSpecBuilder>(),
  HasRepositoriesBlock<DependencyResolutionManagementSpecBuilder> {
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
   */
  public fun versionCatalogs(
    block: MutableVersionCatalogContainer.() -> Unit
  ): DependencyResolutionManagementSpecBuilder = functionCall(
    name = "versionCatalogs",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
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
   */
  public fun components(
    block: ComponentMetadataContainer.() -> Unit
  ): DependencyResolutionManagementSpecBuilder = functionCall(
    name = "components",
    labelSupport = FunctionCall.LabelSupport.GROOVY,
    LambdaParameter(label = "registration", builder = block)
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
 */
public class ComponentMetadataContainer : AbstractDslElementContainer<ComponentMetadataContainer>()
