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

package com.rickbusarow.kase

/**
 * Trait interface for a test class with default [kases]
 *
 * @since 0.1.0
 */
public interface HasKases<K : Kase> {
  /**
   * The default kases for tests in this class.
   *
   * @since 0.1.0
   */
  public val kases: List<K>
}

/**
 * Represents a case for testing. It contains a display
 * name and a list of parameters with their labels.
 *
 * @since 0.1.0
 */
public interface Kase : HasDisplayName {

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
public fun interface KaseDisplayNameFactory<K : Kase> {
  /** @since 0.1.0 */
  public fun K.createDisplayName(): String
}

/**
 * Trait for a class which has a label.
 *
 * @since 0.1.0
 */
public interface HasLabel {
  /** @since 0.1.0 */
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
public interface HasDisplayName {

  /**
   * The "name" of a [Kase] as it is used in a test function name. By default, this is a
   * pipe-separated list of the label/value pairs, such as: `[label1: value1 | label2: value2]`.
   *
   * @see HasDisplayNames.displayNames for a list of the label/value pairs.
   * @since 0.1.0
   */
  public val displayName: String
}

/**
 * Trait for a class which has a list of display names.
 *
 * @since 0.1.0
 */
public interface HasDisplayNames {

  /**
   * The "names" of individual [Kase] parameters as they are used in test function names. By
   * default, these are label/value pairs, such as: `["label1: value1", "label2: value2"]`.
   *
   * @see HasDisplayName.displayName for the full display name.
   * @since 0.1.0
   */
  public val displayNames: List<String>
}
