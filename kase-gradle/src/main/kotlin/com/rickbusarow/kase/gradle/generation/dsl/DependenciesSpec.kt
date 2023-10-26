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
import com.rickbusarow.kase.gradle.generation.model.FunctionCall
import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.generation.model.LambdaParameter
import com.rickbusarow.kase.gradle.generation.model.ValueParameter

/** Builds the `dependencies { }` block */
public class DependenciesSpec : AbstractDslElementContainer<DependenciesSpec>() {

  /** `add("androidTestApi", "com.acme:hairbrush-api:1.0.0")` */
  public fun add(configuration: String, dependencyNotation: String): DependenciesSpec =
    functionCall(
      name = "add",
      labelSupport = FunctionCall.LabelSupport.GroovyAndKotlinLabels,
      ValueParameter("configuration", configuration),
      ValueParameter("dependencyNotation", dependencyNotation)
    )

  /**
   * ```
   * add("androidTestApi", "com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun add(
    configuration: String,
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = functionCall(
    name = "add",
    labelSupport = FunctionCall.LabelSupport.GroovyAndKotlinLabels,
    ValueParameter("configuration", configuration),
    ValueParameter("dependencyNotation", dependencyNotation),
    LambdaParameter("configureClosure", builder = configureClosure)
  )

  /** `androidTestApi("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "androidTestApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * androidTestApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "androidTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `androidTestCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "androidTestCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * androidTestCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "androidTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `androidTestImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestImplementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "androidTestImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * androidTestImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "androidTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `androidTestRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "androidTestRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * androidTestRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "androidTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonJvmApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonJvmCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmImplementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonJvmImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonJvmRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonJvmTestApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmTestApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonJvmTestCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmTestCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestImplementation(
    dependencyNotation: String
  ): DependenciesSpec = functionCall(
    name = "commonJvmTestImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmTestImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonJvmTestRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonJvmTestRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonJvmTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonMainApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonMainApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonMainApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonMainCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonMainCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonMainCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainImplementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonMainImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonMainImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonMainImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonMainRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonMainRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonMainRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonTestApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonTestApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonTestCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonTestCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestImplementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonTestImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonTestImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "commonTestRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * commonTestRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "commonTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugApi("com.acme:hairbrush-api:1.0.0")` */
  public fun debugApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "debugApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * debugApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "debugApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun debugCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "debugCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * debugCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "debugCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun debugImplementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "debugImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * debugImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "debugImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun debugRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "debugRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * debugRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "debugRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `api("com.acme:hairbrush-api:1.0.0")` */
  public fun api(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "api",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * api("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun api(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "api",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `compileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun compileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "compileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * compileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun compileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "compileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `implementation("com.acme:hairbrush-api:1.0.0")` */
  public fun implementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "implementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * implementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun implementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "implementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `runtimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun runtimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "runtimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * runtimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun runtimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "runtimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseApi("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "releaseApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * releaseApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "releaseApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "releaseCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * releaseCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "releaseCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseImplementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "releaseImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * releaseImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "releaseImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "releaseRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * releaseRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "releaseRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testApi("com.acme:hairbrush-api:1.0.0")` */
  public fun testApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "testApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "testCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun testImplementation(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "testImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "testRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesApi("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesApi(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "testFixturesApi",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testFixturesApi("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesApi(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testFixturesApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesCompileOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "testFixturesCompileOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testFixturesCompileOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesCompileOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testFixturesCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesImplementation(
    dependencyNotation: String
  ): DependenciesSpec = functionCall(
    name = "testFixturesImplementation",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testFixturesImplementation("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesImplementation(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testFixturesImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesRuntimeOnly(dependencyNotation: String): DependenciesSpec = functionCall(
    name = "testFixturesRuntimeOnly",
    labelSupport = NoLabels,
    ValueParameter(dependencyNotation.asStringLiteral())
  )

  /**
   * ```
   * testFixturesRuntimeOnly("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesRuntimeOnly(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = add(
    configuration = "testFixturesRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )
}
