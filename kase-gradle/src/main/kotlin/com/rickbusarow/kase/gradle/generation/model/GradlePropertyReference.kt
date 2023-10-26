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

package com.rickbusarow.kase.gradle.generation.model

import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.generation.model.ValueAssignment.SetterAssignment
import dev.drewhamilton.poko.Poko

/**
 * Represents accessing a property or variable, and optionally performing some operation on it.
 *
 * @see GradlePropertyReference for variables like `val acmeVersion: Property<String>`
 * @see RegularVariableReference for variables like `val acmeVersion: String`
 */
public sealed interface DslValueReference : DslElement {
  /** The name of the property or variable, such as `acmeVersion` in: `val acmeVersion: String` */
  public val name: String

  /**
   * The parent container of this property or variable, such as `buildscript` in:
   * ```
   * buildscript {
   *   val acmeVersion: String = "1.0.0"
   *   // ...
   * }
   * ```
   */
  public val parentContainer: DslElementContainer<*>
}

/**
 * Represents a Gradle property reference, like 'acmeVersion' in:
 *
 * ```
 * val acmeVersion: Property<String> = ...
 *
 * val coords = acmeVersion.map { "com.acme:rocket:$it" }
 * ```
 */
@Poko
public class GradlePropertyReference(
  override val name: String,
  override val parentContainer: DslElementContainer<*>
) : DslValueReference {

  private var withReference: Any? = null

  /**
   * Adds a `.map { }` operator to this property reference, like '.map { "com.acme:rocket:$it" }'
   */
  public fun map(transform: DslElementContainer<*>.() -> Unit): GradlePropertyReference = apply {

    val container = SimpleDslElementContainer()

    withReference = FunctionCall(
      name = "$name.map",
      labelSupport = NoLabels,
      LambdaParameter(
        label = null,
        receiver = container,
        builder = transform
      )
    )
  }

  /** Gets the value for this mutable variable, like '.get()' in: `acmeVersion.get()` */
  public fun get(): GradlePropertyReference = apply {
    withReference = "$name.get()"
  }

  /**
   * Sets the value for this mutable variable, like '.set("1.0.0")' in: `acmeVersion.set("1.0.0")`
   */
  public fun set(value: String): GradlePropertyReference = apply {
    withReference =
      ValueAssignment.GradlePropertyAssignment(name, value).also(parentContainer::addElement)
  }

  /**
   * Sets the value for this mutable variable, like '.set("1.0.0")' in: `acmeVersion.set("1.0.0")`
   */
  public fun set(value: DslElement): GradlePropertyReference = apply {
    withReference =
      ValueAssignment.GradlePropertyAssignment(name, value).also(parentContainer::addElement)
  }

  override fun write(language: DslLanguage): String {
    return when (val ref = withReference) {
      null -> name
      is DslElement -> ref.write(language)
      is String -> ref
      else -> error("Unknown reference type: $withReference")
    }
  }
}

/**
 * Represents a Gradle property reference, like 'acmeVersion' in:
 *
 * ```
 * val acmeVersion: Property<String> = ...
 *
 * val coords = acmeVersion.map { "com.acme:rocket:$it" }
 * ```
 */
public sealed class RegularVariableReference : DslValueReference {

  protected var withReference: Any? = null

  /**
   * Represents an immutable variable reference, like 'acmeVersion' in: `val acmeVersion = "1.0.0"`
   */
  @Poko
  public class ImmutableVariableReference(
    override val name: String,
    override val parentContainer: DslElementContainer<*>
  ) : RegularVariableReference()

  /** Represents a mutable variable reference, like 'acmeVersion' in: `var acmeVersion = "1.0.0"` */
  @Poko
  public class MutableVariableReference(
    override val name: String,
    override val parentContainer: DslElementContainer<*>
  ) : RegularVariableReference() {

    /** Sets the value for this mutable variable, like ' = "1.0.0"' in: `acmeVersion = "1.0.0"` */
    public fun set(value: String): RegularVariableReference = apply {
      withReference = SetterAssignment(name, value).also(parentContainer::addElement)
    }

    /** Sets the value for this mutable variable, like ' = "1.0.0"' in: `acmeVersion = "1.0.0"` */
    public fun set(value: DslElement): RegularVariableReference = apply {
      withReference = SetterAssignment(name, value).also(parentContainer::addElement)
    }
  }

  override fun write(language: DslLanguage): String {
    return when (val ref = withReference) {
      null -> name
      is DslElement -> ref.write(language)
      is String -> ref
      else -> error("Unknown reference type: $withReference")
    }
  }
}
