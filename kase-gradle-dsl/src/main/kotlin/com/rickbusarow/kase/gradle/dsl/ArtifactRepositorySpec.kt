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

import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.GroovyInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.NoInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.GroovyLabels
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.DslLanguageSettings.PropertyAccessSupport.GroovyPropertyAccess
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.DslElement
import com.rickbusarow.kase.gradle.dsl.model.DslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.FunctionCall
import com.rickbusarow.kase.gradle.dsl.model.GradleBuilderDsl
import com.rickbusarow.kase.gradle.dsl.model.LambdaParameter
import com.rickbusarow.kase.gradle.dsl.model.RegularVariableReference.MutableVariableReference
import com.rickbusarow.kase.gradle.dsl.model.ValueParameter
import com.rickbusarow.kase.gradle.dsl.model.mutableVariableReference
import java.net.URI

/** Adds the `url` property access to repository configurations */
public interface UrlArtifactRepositorySpec<SELF : DslElementContainer<SELF>> :
  DslElementContainer<SELF> {

  /**
   * The `url` property in a repository configuration. This
   * is overloaded with different `setUrl(...)` functions.
   *
   * ```
   * repositories { // RepositoryHandlerSpec
   *   maven { // MavenArtifactRepositorySpec
   *     url = "https://maven.pkg.jetbrains.space/public/p/compose/dev"
   *   }
   * }
   * ```
   * @see setUrl
   */
  public val url: MutableVariableReference<URI>

  /**
   * The overloaded setter for the `url` property in a repository configuration.
   *
   * ```groovy
   * repositories { // RepositoryHandlerSpec
   *   maven { // MavenArtifactRepositorySpec
   *     url 'https://maven.pkg.jetbrains.space/public/p/compose/dev'
   *   }
   * }
   * ```
   *
   * ```kotlin
   * repositories { // RepositoryHandlerSpec
   *   maven { // MavenArtifactRepositorySpec
   *     setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev")
   *   }
   * }
   * ```
   *
   * @see setUrl
   */
  public fun setUrl(url: String): SELF = setterFunctionCall(
    propertyName = "url",
    parameter = ValueParameter(url),
    propertyAccessSupport = GroovyPropertyAccess,
    labelSupport = GroovyLabels,
    infixSupport = GroovyInfix
  )

  /**
   * The overloaded setter for the `url` property in a repository configuration.
   *
   * ```groovy
   * repositories { // RepositoryHandlerSpec
   *   maven { // MavenArtifactRepositorySpec
   *     url 'https://maven.pkg.jetbrains.space/public/p/compose/dev'
   *   }
   * }
   * ```
   *
   * ```kotlin
   * repositories { // RepositoryHandlerSpec
   *   maven { // MavenArtifactRepositorySpec
   *     setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev")
   *   }
   * }
   * ```
   *
   * @see setUrl
   */
  public fun setUrl(url: DslElement): SELF = setterFunctionCall(
    propertyName = "url",
    parameter = ValueParameter(url),
    propertyAccessSupport = GroovyPropertyAccess,
    labelSupport = GroovyLabels,
    infixSupport = GroovyInfix
  )
}

/**
 * The common configuration lambda in a repository declaration, like:
 *
 * ```
 * repositories { // RepositoryHandlerSpec
 *   mavenLocal { // MavenArtifactRepositorySpec
 *     // this lambda
 *   }
 * }
 * ```
 */
public sealed class ArtifactRepositorySpec<SELF : ArtifactRepositorySpec<SELF>> :
  AbstractDslElementContainer<SELF>() {

  /** Adds a new `content { }` block to the repository configuration. */
  public abstract fun content(
    block: RepositoryContentDescriptorSpec<*>.() -> Unit
  ): SELF
}

/**
 * The maven-specific configuration lambda in a repository declaration, like:
 *
 * ```
 * repositories { // RepositoryHandlerSpec
 *   mavenLocal { // MavenArtifactRepositorySpec
 *     // this lambda
 *   }
 * }
 * ```
 */
@GradleBuilderDsl
public class MavenArtifactRepositorySpec :
  ArtifactRepositorySpec<MavenArtifactRepositorySpec>(),
  UrlArtifactRepositorySpec<MavenArtifactRepositorySpec> {

  override val url: MutableVariableReference<URI> by mutableVariableReference("url") { uri ->
    FunctionCall("path", NoLabels, NoInfix, ValueParameter(uri.toString()))
  }

  /** Adds a new `content { }` block to the repository configuration. */
  public override fun content(
    block: RepositoryContentDescriptorSpec<*>.() -> Unit
  ): MavenArtifactRepositorySpec = functionCall(
    name = "content",
    labelSupport = NoLabels,
    infixSupport = NoInfix,
    LambdaParameter("block", MavenRepositoryContentDescriptorSpec(), block)
  )

  /** Adds a new `mavenContent { }` block to the repository configuration. */
  public fun mavenContent(
    block: MavenRepositoryContentDescriptorSpec.() -> Unit
  ): MavenArtifactRepositorySpec = functionCall(
    name = "mavenContent",
    labelSupport = NoLabels,
    infixSupport = NoInfix,
    LambdaParameter("block", MavenRepositoryContentDescriptorSpec(), block)
  )
}
