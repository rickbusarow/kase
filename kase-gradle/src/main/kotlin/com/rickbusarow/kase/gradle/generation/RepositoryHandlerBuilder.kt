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

import com.rickbusarow.kase.gradle.generation.internal.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.generation.internal.FunctionCall.LabelSupport.GROOVY
import com.rickbusarow.kase.gradle.generation.internal.Quoted

/** A `repositories { }` container */
public class RepositoryHandlerBuilder : AbstractDslElementContainer<RepositoryHandlerBuilder>() {

  /**
   * Adds a new repository to the repository handler.
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   */
  public fun maven(url: String): RepositoryHandlerBuilder = functionCall(
    name = "maven",
    labelSupport = GROOVY,
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
    action: MavenArtifactRepositoryBuilder.() -> Unit
  ): RepositoryHandlerBuilder = functionCall(
    name = "maven",
    labelSupport = GROOVY,
    ValueParameter("url", url),
    LambdaParameter(action)
  )

  /**
   * Adds a new repository to the repository handler.
   *  @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   */
  public fun maven(url: Quoted): RepositoryHandlerBuilder = functionCall(
    name = "maven",
    labelSupport = GROOVY,
    ValueParameter("url", url)
  )

  /**
   * Adds a new repository to the repository handler.
   *  @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   *  @param action the action to perform on the repository
   */
  public fun maven(
    url: Quoted,
    action: MavenArtifactRepositoryBuilder.() -> Unit
  ): RepositoryHandlerBuilder = functionCall(
    name = "maven",
    labelSupport = GROOVY,
    ValueParameter("url", url),
    LambdaParameter(action)
  )

  /** Adds an invocation of `google()` to the repository handler */
  public fun google(): RepositoryHandlerBuilder = functionCall(
    name = "google",
    labelSupport = GROOVY
  )

  /** Adds an invocation of `google()` to the repository handler */
  public fun google(
    action: MavenArtifactRepositoryBuilder.() -> Unit
  ): RepositoryHandlerBuilder = functionCall(
    name = "google",
    labelSupport = GROOVY,
    LambdaParameter(action)
  )

  /** Adds an invocation of `gradlePluginPortal()` to the repository handler */
  public fun gradlePluginPortal(): RepositoryHandlerBuilder = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GROOVY
  )

  /** Adds an invocation of `gradlePluginPortal()` to the repository handler */
  public fun gradlePluginPortal(
    action: MavenArtifactRepositoryBuilder.() -> Unit
  ): RepositoryHandlerBuilder = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GROOVY,
    LambdaParameter(action)
  )

  /** Adds an invocation of `mavenCentral()` to the repository handler */
  public fun mavenCentral(): RepositoryHandlerBuilder = functionCall(
    name = "mavenCentral",
    labelSupport = GROOVY
  )

  /** Adds an invocation of `mavenCentral()` to the repository handler */
  public fun mavenCentral(
    action: MavenArtifactRepositoryBuilder.() -> Unit
  ): RepositoryHandlerBuilder = functionCall(
    name = "mavenCentral",
    labelSupport = GROOVY,
    LambdaParameter(action)
  )

  /** Adds an invocation of `mavenLocal()` to the repository handler */
  public fun mavenLocal(): RepositoryHandlerBuilder = functionCall(
    name = "mavenLocal",
    labelSupport = GROOVY
  )

  /** Adds an invocation of `mavenLocal()` to the repository handler */
  public fun mavenLocal(
    action: MavenArtifactRepositoryBuilder.() -> Unit
  ): RepositoryHandlerBuilder = functionCall(
    name = "mavenLocal",
    labelSupport = GROOVY,
    LambdaParameter(action)
  )
}
