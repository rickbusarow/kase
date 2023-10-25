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
import com.rickbusarow.kase.gradle.generation.model.DependencyDeclaration

/** Builds the `dependencies { }` block */
public class DependenciesSpec : AbstractDslElementContainer<DependenciesSpec>() {

  /** Adds a dependency declaration to the container. */
  public fun add(configuration: String, group: String, module: String, version: String) {
    addElement(
      DependencyDeclaration.ExternalDependency(
        configuration = configuration,
        group = group,
        module = module,
        version = version
      )
    )
  }

  public fun api(group: String, module: String, version: String) {
    add(configuration = "api", group = group, module = module, version = version)
  }

  public fun implementation(group: String, module: String, version: String) {
    add(configuration = "implementation", group = group, module = module, version = version)
  }

  public fun compileOnly(group: String, module: String, version: String) {
    add(configuration = "compileOnly", group = group, module = module, version = version)
  }

  public fun runtimeOnly(group: String, module: String, version: String) {
    add(configuration = "runtimeOnly", group = group, module = module, version = version)
  }

  public fun testImplementation(group: String, module: String, version: String) {
    add(configuration = "testImplementation", group = group, module = module, version = version)
  }

  public fun debugImplementation(group: String, module: String, version: String) {
    add(configuration = "debugImplementation", group = group, module = module, version = version)
  }

  public fun debugApi(group: String, module: String, version: String) {
    add(configuration = "debugApi", group = group, module = module, version = version)
  }
}
