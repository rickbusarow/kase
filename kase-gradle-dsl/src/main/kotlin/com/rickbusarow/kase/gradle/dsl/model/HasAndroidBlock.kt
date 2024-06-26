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

import com.rickbusarow.kase.gradle.dsl.AndroidComponentsExtensionSpec
import com.rickbusarow.kase.gradle.dsl.AndroidExtensionSpec
import com.rickbusarow.kase.gradle.dsl.BuildFileSpec

/**
 * Builds an `android { }` block in a build file.
 *
 * @since 0.1.0
 */
public interface HasAndroidBlock : DslElementContainer<BuildFileSpec> {

  /**
   * ```
   * // build.gradle
   * android {
   *   // ...
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun android(
    block: AndroidExtensionSpec.() -> Unit
  ): BuildFileSpec = functionCall(
    name = "android",
    LambdaParameter(builder = block)
  )
}

/**
 * Builds an `androidComponents { }` block in a build file.
 *
 * @since 0.1.0
 */
public interface HasAndroidComponentsBlock : DslElementContainer<BuildFileSpec> {

  /**
   * ```
   * // build.gradle
   * androidComponents {
   *   // ...
   * }
   * ```
   *
   * @since 0.1.0
   */
  public fun androidComponents(
    block: AndroidComponentsExtensionSpec.() -> Unit
  ): BuildFileSpec = functionCall(
    name = "androidComponents",
    LambdaParameter(builder = block)
  )
}
