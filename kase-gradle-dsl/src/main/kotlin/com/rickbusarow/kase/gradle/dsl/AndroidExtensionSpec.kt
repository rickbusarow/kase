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
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.KotlinLabels
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.LambdaParameter
import com.rickbusarow.kase.gradle.dsl.model.RawLiteral
import com.rickbusarow.kase.gradle.dsl.model.RegularVariableReference
import com.rickbusarow.kase.gradle.dsl.model.StringLiteral
import com.rickbusarow.kase.gradle.dsl.model.ValueParameter
import com.rickbusarow.kase.gradle.dsl.model.mutableVariableReference

/**
 * ```
 * // build.gradle
 * android {
 *   compileOptions {
 *     // ...
 *   }
 * }
 * ```
 */
public open class AndroidCompileOptionsSpec : AbstractDslElementContainer<AndroidCompileOptionsSpec>() {

  /**
   * ```
   * sourceCompatibility = "1.8"
   * ```
   */
  public val sourceCompatibility: RegularVariableReference.MutableVariableReference<String>
    by mutableVariableReference<String> {
      ValueParameter(StringLiteral(it))
    }

  /**
   * ```
   * sourceCompatibility("1.8")
   * ```
   */
  public fun sourceCompatibility(sourceCompatibility: String): AndroidCompileOptionsSpec =
    functionCall(
      name = "sourceCompatibility",
      labelSupport = KotlinLabels,
      infixSupport = GroovyInfix,
      ValueParameter(
        label = "sourceCompatibility",
        valueElement = RawLiteral(sourceCompatibility)
      )
    )

  /**
   * ```
   * targetCompatibility = "1.8"
   * ```
   */
  public val targetCompatibility: RegularVariableReference.MutableVariableReference<String>
    by mutableVariableReference<String> {
      ValueParameter(StringLiteral(it))
    }

  /**
   * ```
   * targetCompatibility("1.8")
   * ```
   */
  public fun targetCompatibility(targetCompatibility: String): AndroidCompileOptionsSpec =
    functionCall(
      name = "targetCompatibility",
      labelSupport = KotlinLabels,
      infixSupport = GroovyInfix,
      ValueParameter(
        label = "targetCompatibility",
        valueElement = RawLiteral(targetCompatibility)
      )
    )

  /**
   * Java source file encoding.
   *
   * ```
   * encoding = "UTF-8"
   * ```
   */
  public val encoding: RegularVariableReference.MutableVariableReference<String>
    by mutableVariableReference<String> {
      ValueParameter(StringLiteral(it))
    }

  /** Whether core library desugaring is enabled. */
  public val isCoreLibraryDesugaringEnabled:
    RegularVariableReference.MutableVariableReference<Boolean>
    by mutableVariableReference<Boolean>()
}

/**
 * ```
 * // build.gradle
 * android {
 *   // ...
 * }
 * ```
 */
public open class AndroidExtensionSpec : AbstractDslElementContainer<AndroidExtensionSpec>() {

  /**
   * ```
   * // build.gradle
   * android {
   *   compileSdk = 33
   * }
   * ```
   */
  public val compileSdk: RegularVariableReference.MutableVariableReference<Int>
    by mutableVariableReference<Int> { int ->
      ValueParameter("$int")
    }

  /**
   * ```
   * // build.gradle
   * android {
   *   namespace = "com.acme.dynamite"
   * }
   * ```
   */
  public val namespace: RegularVariableReference.MutableVariableReference<String>
    by mutableVariableReference<String> {
      ValueParameter(StringLiteral(it))
    }

  /**
   * ```
   * // build.gradle
   * android {
   *   resourcePrefix = "acme"
   * }
   * ```
   */
  public val resourcePrefix: RegularVariableReference.MutableVariableReference<String>
    by mutableVariableReference<String> {
      ValueParameter(StringLiteral(it))
    }

  /**
   * ```
   * // build.gradle
   * android {
   *   compileOptions {
   *     // ...
   *   }
   * }
   * ```
   */
  public fun compileOptions(
    callback: AndroidCompileOptionsSpec.() -> Unit
  ): AndroidExtensionSpec = functionCall(
    name = "compileOptions",
    LambdaParameter(callback)
  )
}

/**
 * ```
 * // build.gradle
 * androidComponents {
 *   // ...
 * }
 * ```
 */
public open class AndroidComponentsExtensionSpec :
  AbstractDslElementContainer<AndroidComponentsExtensionSpec>() {

  /**
   * ```
   * // build.gradle
   * androidComponents {
   *   finalizeDsl {
   *     // ...
   *   }
   * }
   * ```
   */
  public fun finalizeDsl(
    callback: AndroidComponentsExtensionSpec.() -> Unit
  ): AndroidComponentsExtensionSpec = functionCall(
    name = "finalizeDsl",
    LambdaParameter(callback)
  )
}
