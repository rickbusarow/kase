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

import com.rickbusarow.kase.gradle.generation.MavenRepositoryContentSpec
import com.rickbusarow.kase.gradle.generation.RepositoryContentSpec
import com.rickbusarow.kase.gradle.generation.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.generation.model.LambdaParameter

/**
 * The common configuration lambda in a repository declaration, like:
 *
 * ```
 * repositories {
 *   mavenLocal {
 *     // this lambda
 *   }
 * }
 */
public sealed class ArtifactRepositorySpec<T : RepositoryContentSpec<T>> :
  AbstractDslElementContainer<ArtifactRepositorySpec<T>>() {

  /** Adds a new `content { }` block to the repository configuration. */
  public abstract fun content(block: T.() -> Unit): ArtifactRepositorySpec<T>
}

/**
 * The maven-specific configuration lambda in a repository declaration, like:
 *
 * ```
 * repositories {
 *   mavenLocal {
 *     // this lambda
 *   }
 * }
 */
public class MavenArtifactRepositorySpec : ArtifactRepositorySpec<MavenRepositoryContentSpec>() {

  /** Adds a new `mavenContent { }` block to the repository configuration. */
  public fun mavenContent(
    block: MavenRepositoryContentSpec.() -> Unit
  ): ArtifactRepositorySpec<MavenRepositoryContentSpec> = functionCall(
    name = "mavenContent",
    labelSupport = NoLabels,
    LambdaParameter("block", block)
  )

  override fun content(
    block: MavenRepositoryContentSpec.() -> Unit
  ): ArtifactRepositorySpec<MavenRepositoryContentSpec> = functionCall(
    name = "content",
    labelSupport = NoLabels,
    LambdaParameter("block", block)
  )
}
