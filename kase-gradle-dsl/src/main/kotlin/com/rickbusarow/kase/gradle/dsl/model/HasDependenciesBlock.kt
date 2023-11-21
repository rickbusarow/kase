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

package com.rickbusarow.kase.gradle.dsl.model

import com.rickbusarow.kase.gradle.dsl.DependenciesSpec

/** Builds a `dependencies { }` block in a build or settings Gradle file. */
public interface HasDependenciesBlock<SELF : HasDependenciesBlock<SELF>> : DslElementContainer<SELF> {

  /**
   * ```
   * // build.gradle
   * dependencies {
   *   // ...
   * }
   * ```
   */
  public fun dependencies(
    block: DependenciesSpec.() -> Unit
  ): SELF = functionCall(
    name = "dependencies",
    LambdaParameter(builder = block)
  )
}
