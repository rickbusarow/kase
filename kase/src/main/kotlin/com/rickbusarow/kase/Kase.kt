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

package com.rickbusarow.kase

import java.io.Serializable

@Deprecated(
  "renamed to HasParams",
  ReplaceWith("HasParams", "com.rickbusarow.kase.HasParams")
)
public typealias HasKases<K> = HasParams<K>

/**
 * Should prevent name shadowing issues when referencing a `params` property in dynamic tests.
 *
 * @see DslMarker
 * @since 0.7.0
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class ParamsScope

/**
 * Trait interface for a test class with default [params]
 *
 * @since 0.1.0
 */
@ParamsScope
public interface HasParams<out PARAM> {
  /**
   * The default kases for tests in this class.
   *
   * @since 0.1.0
   */
  @Deprecated("renamed to params", ReplaceWith("params"))
  public val kases: List<PARAM> get() = params

  /**
   * The default parameters for tests in this class.
   *
   * @since 0.7.0
   */
  public val params: List<PARAM>
}

/**
 * Represents a case for testing. It contains a display
 * name and a list of parameters with their labels.
 *
 * @since 0.1.0
 */
public interface Kase : HasDisplayName, Serializable {

  public companion object {
    /**
     * An empty [Kase] instance with no parameters.
     *
     * @since 0.1.0
     */
    public inline val EMPTY: Kase get() = EmptyCase()
  }
}

/**
 * An empty [Kase] instance with no parameters.
 *
 * @since 0.1.0
 */
public class EmptyCase : Kase {

  override val displayName: String = ""
}

/**
 * Generates a [displayName][HasDisplayName.displayName] from a [Kase] instance. The display
 * name is used when creating a [TestEnvironment]'s working directory and in dynamic tests.
 *
 * @since 0.1.0
 */
public fun interface KaseDisplayNameFactory<K : Kase> : Serializable {
  /** @since 0.1.0 */
  public fun K.createDisplayName(): String
}

/**
 * Trait for a class which has a label.
 *
 * @since 0.1.0
 */
public interface HasLabel : Serializable {

  /**
   * Identifies some parameter, such as `version` in `version: 1.0.0`.
   *
   * @since 0.1.0
   */
  public val label: String
}

/**
 * Trait for a class which has labels for each parameter.
 *
 * @since 0.1.0
 */
public interface HasLabels {
  /**
   * The labels in the order they should be displayed.
   *
   * ex: ["label1", "label2"]
   *
   * @since 0.1.0
   */
  public val orderedLabels: List<String>
}

/**
 * Trait for a class which has a display name.
 *
 * @since 0.1.0
 */
public interface HasDisplayName : Serializable {

  /**
   * The name of a test or test container, as it is displayed in the IDE and in reports.
   * @since 0.1.0
   */
  public val displayName: String
}

/**
 * Trait for a class which has a list of display names.
 *
 * @since 0.1.0
 */
public interface HasDisplayNames : Serializable {

  /**
   * A "fully qualified" name for a test or test container, as it is
   * displayed in the IDE and in reports. The first name in the list
   * is the outermost container, and the last name is the test itself.
   *
   * @since 0.1.0
   */
  public val displayNames: List<String>
}
