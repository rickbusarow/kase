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

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.HasLabel
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixElement
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
import com.rickbusarow.kase.gradle.internal.DefaultVersionMatrix
import dev.drewhamilton.poko.Poko

/** */
public interface HasVersionMatrix {
  public val versionMatrix: VersionMatrix
}

/** */
public interface VersionMatrix {

  /** @return a set of all [VersionMatrixKey]s in this version matrix. */
  public fun keys(): Set<VersionMatrixKey<VersionMatrixElement<*>>>

  /** The number of entries in this version matrix. */
  public val size: Int

  /** @return a list of [VersionMatrixElement]s for the given [key]. */
  public operator fun <E : VersionMatrixElement<*>, K : VersionMatrixKey<E>> get(
    key: K
  ): VersionList<E, K>

  /**
   * @return a list of [VersionMatrixElement]s for the given [key], or
   *   `null` if no [VersionMatrixElement]s exist for the given [key].
   */
  public fun <E : VersionMatrixElement<*>, K : VersionMatrixKey<E>> getOrNull(
    key: K
  ): VersionList<E, K>?

  /**
   * Returns a new [VersionMatrix] instance with the
   * given [elements] added to the existing elements.
   */
  public fun <E : VersionMatrixElement<*>> plus(vararg elements: E): VersionMatrix {
    return plus(elements.toList())
  }

  /**
   * Returns a new [VersionMatrix] instance with the
   * given [elements] added to the existing elements.
   */
  public operator fun <E : VersionMatrixElement<*>> plus(elements: Iterable<E>): VersionMatrix

  /**
   * Returns a new [VersionMatrix] instance with the given [key] removed from the existing elements.
   */
  public operator fun <E : VersionMatrixElement<*>> minus(
    key: VersionMatrixKey<E>
  ): VersionMatrix

  /**
   * Returns a new [VersionMatrix] instance with the
   * given [elements] removed from the existing elements.
   */
  public fun <E : VersionMatrixElement<*>> minus(vararg elements: E): VersionMatrix {
    return minus(elements.toList())
  }

  /**
   * Returns a new [VersionMatrix] instance with the
   * given [elements] removed from the existing elements.
   */
  public operator fun <E : VersionMatrixElement<*>> minus(elements: Iterable<E>): VersionMatrix

  /** An element in a [VersionMatrix]. */
  public sealed interface VersionMatrixElement<out T> : HasLabel {

    /** The value of this element. */
    public val value: T

    override val label: String
      get() = this::class.java.simpleName

    /** The [VersionMatrixKey] which can be used to retrieve this element from a [VersionMatrix]. */
    public val key: VersionMatrixKey<VersionMatrixElement<T>>
  }

  /**
   * A key which can be used to retrieve a list of [VersionMatrixElement]s from a [VersionMatrix].
   *
   * @see VersionMatrix
   * @see VersionMatrixElement
   */
  public interface VersionMatrixKey<out E : VersionMatrixElement<*>>

  public companion object {

    /**
     * Creates a new [VersionMatrix] instance from the given [elements]. The [elements]
     * can be retrieved from the matrix by their [VersionMatrixElement.key]s.
     *
     * @param elements the [VersionMatrixElement]s to group into a [VersionMatrix]
     * @return a new [VersionMatrix] instance
     */
    public operator fun invoke(vararg elements: VersionMatrixElement<*>): VersionMatrix {
      return DefaultVersionMatrix(
        elements.groupBy { it.key }
          .mapValues { (key, list) ->
            VersionList(list, list.first(), key)
          }
      )
    }

    /**
     * Creates a new [VersionMatrix] instance from the given [elements]. The [elements]
     * can be retrieved from the matrix by their [VersionMatrixElement.key]s.
     *
     * @param elements the [VersionMatrixElement]s to group into a [VersionMatrix]
     * @return a new [VersionMatrix] instance
     */
    public operator fun invoke(elements: List<VersionMatrixElement<*>>): VersionMatrix {
      return DefaultVersionMatrix(
        elements.groupBy { it.key }
          .mapValues { (key, list) ->
            VersionList(list, list.first(), key)
          }
      )
    }

    /**
     * Creates a new [VersionMatrix] instance from the given [elements]. The [elements]
     * can be retrieved from the matrix by their [VersionMatrixElement.key]s.
     *
     * @param elements the [VersionMatrixElement]s to group into a [VersionMatrix]
     * @return a new [VersionMatrix] instance
     */
    @JvmName("invokeNested")
    public operator fun invoke(elements: List<List<VersionMatrixElement<*>>>): VersionMatrix {
      return invoke(elements.flatten())
    }
  }
}

/**
 * A container for a list of [VersionMatrixElement]s which share a common [key].
 *
 * @property elements the list of [VersionMatrixElement]s which share a common [key].
 * @property default the default [VersionMatrixElement] to use when no other element is available.
 * @property key the [VersionMatrixKey] which can be
 *   used to retrieve this list from a [VersionMatrix].
 * @see VersionMatrix
 */
@Poko
public class VersionList<E : VersionMatrixElement<*>, out K : VersionMatrixKey<E>>(
  public val elements: List<E>,
  public val default: E,
  public val key: K
) : Iterable<E> by elements {
  init {
    require(elements.isNotEmpty()) { "The elements list for key '$key' must not be empty." }
  }

  /**
   * Returns a new [VersionList] instance with the given [elements] added to the existing elements.
   */
  public operator fun plus(others: Iterable<E>): VersionList<E, K> {
    return VersionList(elements + others, default, key)
  }

  /**
   * Returns a new [VersionList] instance with the given
   * [elements] removed from the existing elements.
   */
  public operator fun minus(others: Iterable<VersionMatrixElement<*>>): VersionList<E, K> {
    val otherSet = others.toSet()
    return VersionList(elements.filter { it !in otherSet }, default, key)
  }
}
