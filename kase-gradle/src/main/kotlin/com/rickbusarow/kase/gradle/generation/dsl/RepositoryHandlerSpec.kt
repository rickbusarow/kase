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
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.GroovyAndKotlinLabels
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.GroovyLabels
import com.rickbusarow.kase.gradle.generation.model.LambdaParameter
import com.rickbusarow.kase.gradle.generation.model.StringLiteral
import com.rickbusarow.kase.gradle.generation.model.ValueParameter

/** A `repositories { }` container */
public class RepositoryHandlerSpec : AbstractDslElementContainer<RepositoryHandlerSpec>() {

  /**
   * Adds a new repository to the repository handler.
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   */
  public fun maven(url: String): RepositoryHandlerSpec = functionCall(
    name = "maven",
    labelSupport = GroovyAndKotlinLabels,
    ValueParameter("url", url)
  )

  /**
   * Adds a new repository to the repository handler.
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   */
  public fun maven(
    url: String,
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "maven",
    labelSupport = GroovyAndKotlinLabels,
    ValueParameter("url", url),
    LambdaParameter(action)
  )

  /**
   * Adds a new repository to the repository handler.
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   */
  public fun maven(url: StringLiteral): RepositoryHandlerSpec = functionCall(
    name = "maven",
    labelSupport = GroovyAndKotlinLabels,
    ValueParameter("url", url)
  )

  /**
   * Adds a new repository to the repository handler.
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   */
  public fun maven(
    url: StringLiteral,
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "maven",
    labelSupport = GroovyAndKotlinLabels,
    ValueParameter("url", url),
    LambdaParameter(action)
  )

  /** Adds an invocation of `google()` to the repository handler */
  public fun google(): RepositoryHandlerSpec = functionCall(
    name = "google",
    labelSupport = GroovyLabels
  )

  /** Adds an invocation of `google()` to the repository handler */
  public fun google(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "google",
    labelSupport = GroovyLabels,
    LambdaParameter(action)
  )

  /** Adds an invocation of `gradlePluginPortal()` to the repository handler */
  public fun gradlePluginPortal(): RepositoryHandlerSpec = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GroovyLabels
  )

  /** Adds an invocation of `gradlePluginPortal()` to the repository handler */
  public fun gradlePluginPortal(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GroovyLabels,
    LambdaParameter(action)
  )

  /** Adds an invocation of `mavenCentral()` to the repository handler */
  public fun mavenCentral(): RepositoryHandlerSpec = functionCall(
    name = "mavenCentral",
    labelSupport = GroovyLabels
  )

  /** Adds an invocation of `mavenCentral()` to the repository handler */
  public fun mavenCentral(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "mavenCentral",
    labelSupport = GroovyLabels,
    LambdaParameter(action)
  )

  /** Adds an invocation of `mavenLocal()` to the repository handler */
  public fun mavenLocal(): RepositoryHandlerSpec = functionCall(
    name = "mavenLocal",
    labelSupport = GroovyLabels
  )

  /** Adds an invocation of `mavenLocal()` to the repository handler */
  public fun mavenLocal(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "mavenLocal",
    labelSupport = GroovyLabels,
    LambdaParameter(action)
  )
}
