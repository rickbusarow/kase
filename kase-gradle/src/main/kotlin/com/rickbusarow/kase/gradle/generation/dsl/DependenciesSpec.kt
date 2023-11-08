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
import com.rickbusarow.kase.gradle.generation.model.DslElement
import com.rickbusarow.kase.gradle.generation.model.FunctionCall
import com.rickbusarow.kase.gradle.generation.model.LambdaParameter
import com.rickbusarow.kase.gradle.generation.model.ValueParameter

/** Builds the `dependencies { }` block */
public class DependenciesSpec : AbstractDslElementContainer<DependenciesSpec>() {

  public fun project(pathString: String): FunctionCall = FunctionCall(
    name = "project",
    labelSupport = FunctionCall.LabelSupport.GroovyAndKotlinLabels,
    infixSupport = FunctionCall.InfixSupport.NoInfix,
    ValueParameter("path", valueElement = stringLiteral(pathString))
  )

  public fun testFixtures(pathString: String): FunctionCall = FunctionCall(
    name = "testFixtures",
    labelSupport = FunctionCall.LabelSupport.GroovyAndKotlinLabels,
    infixSupport = FunctionCall.InfixSupport.NoInfix,
    ValueParameter("path", valueElement = stringLiteral(pathString))
  )

  /** `add("androidTestApi", "com.acme:hairbrush-api:1.0.0")` */
  public fun add(configuration: String, dependencyNotation: String): DependenciesSpec =
    functionCall(
      name = "add",
      labelSupport = FunctionCall.LabelSupport.GroovyAndKotlinLabels,
      infixSupport = FunctionCall.InfixSupport.GroovyInfix,
      ValueParameter(label = "configuration", valueString = configuration),
      ValueParameter(label = "dependencyNotation", valueString = dependencyNotation)
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
    labelSupport = FunctionCall.LabelSupport.KotlinLabels,
    infixSupport = FunctionCall.InfixSupport.GroovyInfix,
    ValueParameter(label = "configuration", valueString = configuration),
    ValueParameter(label = "dependencyNotation", valueString = dependencyNotation),
    LambdaParameter(label = "configureClosure", builder = configureClosure)
  )

  /** `androidTestApi("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestApi",
    dependencyNotation = dependencyNotation
  )

  /** `androidTestApi(libs.acme.hairbrush.api)` */
  public fun androidTestApi(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestApi",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * androidTestApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `androidTestCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestCompileOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "androidTestCompileOnly",
      dependencyNotation = dependencyNotation
    )

  /** `androidTestCompileOnly(libs.acme.hairbrush.api)` */
  public fun androidTestCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "androidTestCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * androidTestCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `androidTestImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "androidTestImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `androidTestImplementation(libs.acme.hairbrush.api)` */
  public fun androidTestImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "androidTestImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * androidTestImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `androidTestRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun androidTestRuntimeOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "androidTestRuntimeOnly",
      dependencyNotation = dependencyNotation
    )

  /** `androidTestRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun androidTestRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "androidTestRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * androidTestRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun androidTestRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "androidTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `anvil("com.acme:hairbrush-api:1.0.0")` */
  public fun anvil(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "anvil",
    dependencyNotation = dependencyNotation
  )

  /** `anvil(libs.acme.hairbrush.api)` */
  public fun anvil(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "anvil",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * anvil("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvil(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvil",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * anvil(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvil(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvil",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `anvilAndroidTest("com.acme:hairbrush-api:1.0.0")` */
  public fun anvilAndroidTest(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "anvilAndroidTest",
    dependencyNotation = dependencyNotation
  )

  /** `anvilAndroidTest(libs.acme.hairbrush.api)` */
  public fun anvilAndroidTest(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "anvilAndroidTest",
      dependencyNotation = dependencyNotation
    )

  /**
   * ```
   * anvilAndroidTest("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilAndroidTest(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilAndroidTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * anvilAndroidTest(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilAndroidTest(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilAndroidTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `anvilDebug("com.acme:hairbrush-api:1.0.0")` */
  public fun anvilDebug(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "anvilDebug",
    dependencyNotation = dependencyNotation
  )

  /** `anvilDebug(libs.acme.hairbrush.api)` */
  public fun anvilDebug(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "anvilDebug",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * anvilDebug("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilDebug(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilDebug",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * anvilDebug(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilDebug(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilDebug",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `anvilRelease("com.acme:hairbrush-api:1.0.0")` */
  public fun anvilRelease(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "anvilRelease",
    dependencyNotation = dependencyNotation
  )

  /** `anvilRelease(libs.acme.hairbrush.api)` */
  public fun anvilRelease(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "anvilRelease",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * anvilRelease("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilRelease(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilRelease",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * anvilRelease(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilRelease(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilRelease",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `anvilTest("com.acme:hairbrush-api:1.0.0")` */
  public fun anvilTest(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "anvilTest",
    dependencyNotation = dependencyNotation
  )

  /** `anvilTest(libs.acme.hairbrush.api)` */
  public fun anvilTest(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "anvilTest",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * anvilTest("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilTest(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * anvilTest(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun anvilTest(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "anvilTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `api("com.acme:hairbrush-api:1.0.0")` */
  public fun api(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "api",
    dependencyNotation = dependencyNotation
  )

  /** `api(libs.acme.hairbrush.api)` */
  public fun api(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "api",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "api",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * api(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun api(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "api",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmApi",
    dependencyNotation = dependencyNotation
  )

  /** `commonJvmApi(libs.acme.hairbrush.api)` */
  public fun commonJvmApi(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmApi",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmCompileOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmCompileOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonJvmCompileOnly(libs.acme.hairbrush.api)` */
  public fun commonJvmCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `commonJvmImplementation(libs.acme.hairbrush.api)` */
  public fun commonJvmImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmRuntimeOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmRuntimeOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonJvmRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun commonJvmRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestApi",
    dependencyNotation = dependencyNotation
  )

  /** `commonJvmTestApi(libs.acme.hairbrush.api)` */
  public fun commonJvmTestApi(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmTestApi",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmTestApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestCompileOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmTestCompileOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonJvmTestCompileOnly(libs.acme.hairbrush.api)` */
  public fun commonJvmTestCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmTestCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmTestCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmTestImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `commonJvmTestImplementation(libs.acme.hairbrush.api)` */
  public fun commonJvmTestImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmTestImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmTestImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonJvmTestRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonJvmTestRuntimeOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmTestRuntimeOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonJvmTestRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun commonJvmTestRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonJvmTestRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonJvmTestRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonJvmTestRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonJvmTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainApi",
    dependencyNotation = dependencyNotation
  )

  /** `commonMainApi(libs.acme.hairbrush.api)` */
  public fun commonMainApi(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainApi",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonMainApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainCompileOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonMainCompileOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonMainCompileOnly(libs.acme.hairbrush.api)` */
  public fun commonMainCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonMainCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonMainCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonMainImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `commonMainImplementation(libs.acme.hairbrush.api)` */
  public fun commonMainImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonMainImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonMainImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonMainRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonMainRuntimeOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonMainRuntimeOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonMainRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun commonMainRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonMainRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonMainRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonMainRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonMainRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestApi("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestApi",
    dependencyNotation = dependencyNotation
  )

  /** `commonTestApi(libs.acme.hairbrush.api)` */
  public fun commonTestApi(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestApi",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonTestApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestCompileOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonTestCompileOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonTestCompileOnly(libs.acme.hairbrush.api)` */
  public fun commonTestCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonTestCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonTestCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonTestImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `commonTestImplementation(libs.acme.hairbrush.api)` */
  public fun commonTestImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonTestImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonTestImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `commonTestRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun commonTestRuntimeOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonTestRuntimeOnly",
      dependencyNotation = dependencyNotation
    )

  /** `commonTestRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun commonTestRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "commonTestRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * commonTestRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun commonTestRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "commonTestRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `compileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun compileOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "compileOnly",
    dependencyNotation = dependencyNotation
  )

  /** `compileOnly(libs.acme.hairbrush.api)` */
  public fun compileOnly(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "compileOnly",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "compileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * compileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun compileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "compileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugApi("com.acme:hairbrush-api:1.0.0")` */
  public fun debugApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "debugApi",
    dependencyNotation = dependencyNotation
  )

  /** `debugApi(libs.acme.hairbrush.api)` */
  public fun debugApi(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "debugApi",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * debugApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun debugCompileOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "debugCompileOnly",
    dependencyNotation = dependencyNotation
  )

  /** `debugCompileOnly(libs.acme.hairbrush.api)` */
  public fun debugCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "debugCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * debugCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun debugImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "debugImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `debugImplementation(libs.acme.hairbrush.api)` */
  public fun debugImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "debugImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * debugImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `debugRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun debugRuntimeOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "debugRuntimeOnly",
    dependencyNotation = dependencyNotation
  )

  /** `debugRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun debugRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "debugRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * debugRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun debugRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "debugRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `implementation("com.acme:hairbrush-api:1.0.0")` */
  public fun implementation(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "implementation",
    dependencyNotation = dependencyNotation
  )

  /** `implementation(libs.acme.hairbrush.api)` */
  public fun implementation(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "implementation",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "implementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * implementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun implementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "implementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kapt("com.acme:hairbrush-api:1.0.0")` */
  public fun kapt(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kapt",
    dependencyNotation = dependencyNotation
  )

  /** `kapt(libs.acme.hairbrush.api)` */
  public fun kapt(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kapt",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kapt("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kapt(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kapt",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kapt(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kapt(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kapt",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kaptAndroidTest("com.acme:hairbrush-api:1.0.0")` */
  public fun kaptAndroidTest(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kaptAndroidTest",
    dependencyNotation = dependencyNotation
  )

  /** `kaptAndroidTest(libs.acme.hairbrush.api)` */
  public fun kaptAndroidTest(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "kaptAndroidTest",
      dependencyNotation = dependencyNotation
    )

  /**
   * ```
   * kaptAndroidTest("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptAndroidTest(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptAndroidTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kaptAndroidTest(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptAndroidTest(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptAndroidTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kaptDebug("com.acme:hairbrush-api:1.0.0")` */
  public fun kaptDebug(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kaptDebug",
    dependencyNotation = dependencyNotation
  )

  /** `kaptDebug(libs.acme.hairbrush.api)` */
  public fun kaptDebug(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kaptDebug",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kaptDebug("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptDebug(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptDebug",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kaptDebug(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptDebug(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptDebug",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kaptRelease("com.acme:hairbrush-api:1.0.0")` */
  public fun kaptRelease(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kaptRelease",
    dependencyNotation = dependencyNotation
  )

  /** `kaptRelease(libs.acme.hairbrush.api)` */
  public fun kaptRelease(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kaptRelease",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kaptRelease("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptRelease(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptRelease",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kaptRelease(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptRelease(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptRelease",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kaptTest("com.acme:hairbrush-api:1.0.0")` */
  public fun kaptTest(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kaptTest",
    dependencyNotation = dependencyNotation
  )

  /** `kaptTest(libs.acme.hairbrush.api)` */
  public fun kaptTest(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kaptTest",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kaptTest("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptTest(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kaptTest(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kaptTest(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kaptTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `ksp("com.acme:hairbrush-api:1.0.0")` */
  public fun ksp(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "ksp",
    dependencyNotation = dependencyNotation
  )

  /** `ksp(libs.acme.hairbrush.api)` */
  public fun ksp(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "ksp",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * ksp("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun ksp(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "ksp",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * ksp(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun ksp(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "ksp",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kspAndroidTest("com.acme:hairbrush-api:1.0.0")` */
  public fun kspAndroidTest(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kspAndroidTest",
    dependencyNotation = dependencyNotation
  )

  /** `kspAndroidTest(libs.acme.hairbrush.api)` */
  public fun kspAndroidTest(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kspAndroidTest",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kspAndroidTest("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspAndroidTest(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspAndroidTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kspAndroidTest(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspAndroidTest(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspAndroidTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kspDebug("com.acme:hairbrush-api:1.0.0")` */
  public fun kspDebug(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kspDebug",
    dependencyNotation = dependencyNotation
  )

  /** `kspDebug(libs.acme.hairbrush.api)` */
  public fun kspDebug(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kspDebug",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kspDebug("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspDebug(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspDebug",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kspDebug(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspDebug(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspDebug",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kspRelease("com.acme:hairbrush-api:1.0.0")` */
  public fun kspRelease(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kspRelease",
    dependencyNotation = dependencyNotation
  )

  /** `kspRelease(libs.acme.hairbrush.api)` */
  public fun kspRelease(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kspRelease",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kspRelease("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspRelease(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspRelease",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kspRelease(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspRelease(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspRelease",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `kspTest("com.acme:hairbrush-api:1.0.0")` */
  public fun kspTest(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "kspTest",
    dependencyNotation = dependencyNotation
  )

  /** `kspTest(libs.acme.hairbrush.api)` */
  public fun kspTest(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "kspTest",
    dependencyNotation = dependencyNotation
  )

  /**
   * ```
   * kspTest("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspTest(
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * kspTest(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun kspTest(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "kspTest",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseApi("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "releaseApi",
    dependencyNotation = dependencyNotation
  )

  /** `releaseApi(libs.acme.hairbrush.api)` */
  public fun releaseApi(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "releaseApi",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * releaseApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseCompileOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "releaseCompileOnly",
    dependencyNotation = dependencyNotation
  )

  /** `releaseCompileOnly(libs.acme.hairbrush.api)` */
  public fun releaseCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "releaseCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * releaseCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "releaseImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `releaseImplementation(libs.acme.hairbrush.api)` */
  public fun releaseImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "releaseImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * releaseImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `releaseRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun releaseRuntimeOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "releaseRuntimeOnly",
    dependencyNotation = dependencyNotation
  )

  /** `releaseRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun releaseRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "releaseRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * releaseRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun releaseRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "releaseRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `runtimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun runtimeOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "runtimeOnly",
    dependencyNotation = dependencyNotation
  )

  /** `runtimeOnly(libs.acme.hairbrush.api)` */
  public fun runtimeOnly(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "runtimeOnly",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "runtimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * runtimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun runtimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "runtimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testApi("com.acme:hairbrush-api:1.0.0")` */
  public fun testApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "testApi",
    dependencyNotation = dependencyNotation
  )

  /** `testApi(libs.acme.hairbrush.api)` */
  public fun testApi(dependencyNotation: DslElement): DependenciesSpec = invokeConfiguration(
    configuration = "testApi",
    dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testCompileOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "testCompileOnly",
    dependencyNotation = dependencyNotation
  )

  /** `testCompileOnly(libs.acme.hairbrush.api)` */
  public fun testCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "testCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesApi("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesApi(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesApi",
    dependencyNotation = dependencyNotation
  )

  /** `testFixturesApi(libs.acme.hairbrush.api)` */
  public fun testFixturesApi(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "testFixturesApi",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testFixturesApi(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesApi(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesApi",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesCompileOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesCompileOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "testFixturesCompileOnly",
      dependencyNotation = dependencyNotation
    )

  /** `testFixturesCompileOnly(libs.acme.hairbrush.api)` */
  public fun testFixturesCompileOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "testFixturesCompileOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testFixturesCompileOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesCompileOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesCompileOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesImplementation(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "testFixturesImplementation",
      dependencyNotation = dependencyNotation
    )

  /** `testFixturesImplementation(libs.acme.hairbrush.api)` */
  public fun testFixturesImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "testFixturesImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testFixturesImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testFixturesRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testFixturesRuntimeOnly(dependencyNotation: String): DependenciesSpec =
    invokeConfiguration(
      configuration = "testFixturesRuntimeOnly",
      dependencyNotation = dependencyNotation
    )

  /** `testFixturesRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun testFixturesRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "testFixturesRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testFixturesRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testFixturesRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testFixturesRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testImplementation("com.acme:hairbrush-api:1.0.0")` */
  public fun testImplementation(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "testImplementation",
    dependencyNotation = dependencyNotation
  )

  /** `testImplementation(libs.acme.hairbrush.api)` */
  public fun testImplementation(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "testImplementation",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testImplementation(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testImplementation(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testImplementation",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `testRuntimeOnly("com.acme:hairbrush-api:1.0.0")` */
  public fun testRuntimeOnly(dependencyNotation: String): DependenciesSpec = invokeConfiguration(
    configuration = "testRuntimeOnly",
    dependencyNotation = dependencyNotation
  )

  /** `testRuntimeOnly(libs.acme.hairbrush.api)` */
  public fun testRuntimeOnly(dependencyNotation: DslElement): DependenciesSpec =
    invokeConfiguration(
      configuration = "testRuntimeOnly",
      dependencyNotation = dependencyNotation
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
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /**
   * ```
   * testRuntimeOnly(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  public fun testRuntimeOnly(
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = "testRuntimeOnly",
    dependencyNotation = dependencyNotation,
    configureClosure = configureClosure
  )

  /** `someConfig("com.acme:hairbrush-api:1.0.0")` */
  private fun invokeConfiguration(
    configuration: String,
    dependencyNotation: String
  ): DependenciesSpec = invokeConfiguration(
    configuration = configuration,
    dependencyNotation = stringLiteral(dependencyNotation)
  )

  /** `someConfig(libs.acme.hairbrush.api)` */
  private fun invokeConfiguration(
    configuration: String,
    dependencyNotation: DslElement
  ): DependenciesSpec = functionCall(
    configuration,
    FunctionCall.LabelSupport.KotlinLabels,
    FunctionCall.InfixSupport.GroovyInfix,
    ValueParameter(label = "dependencyNotation", valueElement = dependencyNotation)
  )

  /**
   * ```
   * someConfig("com.acme:hairbrush-api:1.0.0") {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  private fun invokeConfiguration(
    configuration: String,
    dependencyNotation: String,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = invokeConfiguration(
    configuration = configuration,
    dependencyNotation = stringLiteral(dependencyNotation),
    configureClosure = configureClosure
  )

  /**
   * ```
   * someConfig(libs.acme.hairbrush.api) {
   *   exclude(group = "com.acme", module = "comb")
   * }
   * ```
   */
  private fun invokeConfiguration(
    configuration: String,
    dependencyNotation: DslElement,
    configureClosure: ModuleDependencySpec.() -> Unit
  ): DependenciesSpec = functionCall(
    configuration,
    FunctionCall.LabelSupport.KotlinLabels,
    FunctionCall.InfixSupport.NoInfix,
    ValueParameter(label = "dependencyNotation", valueElement = dependencyNotation),
    LambdaParameter(label = "configureClosure", builder = configureClosure)
  )
}
