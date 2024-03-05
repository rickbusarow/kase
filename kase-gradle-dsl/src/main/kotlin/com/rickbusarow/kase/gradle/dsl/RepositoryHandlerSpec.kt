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

package com.rickbusarow.kase.gradle.dsl

import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.GroovyInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.NoInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.GroovyAndKotlinLabels
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.GroovyLabels
import com.rickbusarow.kase.gradle.DslLanguageSettings.PropertyAccessSupport.GroovyPropertyAccess
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.DslElement
import com.rickbusarow.kase.gradle.dsl.model.FunctionCall
import com.rickbusarow.kase.gradle.dsl.model.LambdaParameter
import com.rickbusarow.kase.gradle.dsl.model.ValueParameter
import java.io.File
import java.net.URI

/**
 * A `repositories { }` container
 *
 * @since 0.1.0
 */
public class RepositoryHandlerSpec : AbstractDslElementContainer<RepositoryHandlerSpec>() {

  /**
   * Adds an invocation of `maven { ... }` to the repository handler
   *
   * @since 0.1.0
   */
  public fun maven(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "maven",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix,
    LambdaParameter(action)
  )

  /**
   * Adds a new `maven` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   maven("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   maven {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.1.0
   */
  public fun maven(
    url: String,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("maven", url, action)

  /**
   * Adds a new `maven` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   maven("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   maven {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.1.0
   */
  public fun maven(
    url: DslElement,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("maven", url, action)

  /**
   * Adds a new `maven` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   maven("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   maven {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.1.0
   */
  public fun maven(
    url: File,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("maven", url, action)

  /**
   * Adds a new `maven` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   maven("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   maven {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.1.0
   */
  public fun maven(
    url: URI,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("maven", url, action)

  private fun mavenInternal(
    functionName: String,
    urlParam: Any,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec {

    val valueParameter = when (urlParam) {
      is String -> ValueParameter("url", urlParam)
      is DslElement -> ValueParameter("url", urlParam)
      is File -> ValueParameter(label = "url", stringLiteral(urlParam.path))
      is URI -> ValueParameter(
        label = "url",
        valueElement = FunctionCall(
          name = "uri",
          ValueParameter("path", stringLiteral(urlParam.path))
        )
      )

      else -> throw IllegalArgumentException("Unsupported type: ${urlParam::class}")
    }

    return deciding(
      kotlinElement = {

        FunctionCall(
          name = functionName,
          labelSupport = GroovyAndKotlinLabels,
          infixSupport = NoInfix,
          valueParameter,
          action?.let { LambdaParameter(it) }
        )
      },
      groovyElement = {
        FunctionCall(
          name = functionName,
          labelSupport = GroovyAndKotlinLabels,
          infixSupport = NoInfix,
          LambdaParameter.invoke<MavenArtifactRepositorySpec> {

            setterFunctionCall(
              propertyName = "url",
              parameter = valueParameter,
              propertyAccessSupport = GroovyPropertyAccess,
              labelSupport = GroovyLabels,
              infixSupport = GroovyInfix
            )
            action?.invoke(this@invoke)
          }
        )
      }
    )
  }

  /**
   * Adds an invocation of `google()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun google(): RepositoryHandlerSpec = functionCall(
    name = "google",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix
  )

  /**
   * Adds an invocation of `google()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun google(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "google",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix,
    LambdaParameter(action)
  )

  /**
   * Adds an invocation of `gradlePluginPortal()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun gradlePluginPortal(): RepositoryHandlerSpec = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix
  )

  /**
   * Adds an invocation of `gradlePluginPortal()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun gradlePluginPortal(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix,
    LambdaParameter(action)
  )

  /**
   * Adds an invocation of `mavenCentral()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun mavenCentral(): RepositoryHandlerSpec = functionCall(
    name = "mavenCentral",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix
  )

  /**
   * Adds an invocation of `mavenCentral()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun mavenCentral(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "mavenCentral",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix,
    LambdaParameter(action)
  )

  /**
   * Adds an invocation of `mavenLocal()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun mavenLocal(): RepositoryHandlerSpec = functionCall(
    name = "mavenLocal",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix
  )

  /**
   * Adds an invocation of `mavenLocal()` to the repository handler
   *
   * @since 0.1.0
   */
  public fun mavenLocal(
    action: MavenArtifactRepositorySpec.() -> Unit
  ): RepositoryHandlerSpec = functionCall(
    name = "mavenLocal",
    labelSupport = GroovyLabels,
    infixSupport = NoInfix,
    LambdaParameter(action)
  )

  /**
   * Adds a new `mavenLocal` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   mavenLocal("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   mavenLocal {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.11.0
   */
  public fun mavenLocal(
    url: String,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("mavenLocal", url, action)

  /**
   * Adds a new `mavenLocal` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   mavenLocal("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   mavenLocal {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.11.0
   */
  public fun mavenLocal(
    url: DslElement,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("mavenLocal", url, action)

  /**
   * Adds a new `mavenLocal` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   mavenLocal("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   mavenLocal {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.11.0
   */
  public fun mavenLocal(
    url: File,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("mavenLocal", url, action)

  /**
   * Adds a new `mavenLocal` repository to the repository handler.
   *
   * The `url` parameter has different behavior defined in the Gradle Kotlin DSL.
   *
   * ### Kotlin behavior
   *
   * ```
   * repositories {
   *   mavenLocal("my-url")
   * }
   * ```
   *
   * ### Groovy behavior
   *
   * ```
   * repositories {
   *   mavenLocal {
   *     url 'my-url'
   *   }
   * }
   * ```
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   * @since 0.11.0
   */
  public fun mavenLocal(
    url: URI,
    action: (MavenArtifactRepositorySpec.() -> Unit)? = null
  ): RepositoryHandlerSpec = mavenInternal("mavenLocal", url, action)
}
