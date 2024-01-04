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

import com.rickbusarow.kase.KaseMatrix.KaseMatrixElement
import com.rickbusarow.kase.KaseMatrix.KaseMatrixKey
import com.rickbusarow.kase.internal.DefaultKaseMatrix
import dev.drewhamilton.poko.Poko

/**
 * Trait interface for a [KaseMatrix].
 *
 * @since 0.1.0
 */
public interface HasKaseMatrix {
  /**
   * The [KaseMatrix] for this test class.
   *
   * @since 0.1.0
   */
  public val kaseMatrix: KaseMatrix

  /**
   * The [KaseMatrix] for this test class.
   *
   * @since 0.1.0
   */
  @Deprecated(
    "use kaseMatrix",
    ReplaceWith("kaseMatrix"),
    level = DeprecationLevel.ERROR
  )
  public val versionMatrix: KaseMatrix
    get() = kaseMatrix
}

/** @since 0.1.0 */
public interface KaseMatrix {

  /**
   * @return a set of all [KaseMatrixKey]s in this version matrix.
   * @since 0.1.0
   */
  public fun keys(): Set<KaseMatrixKey<KaseMatrixElement<*>>>

  /**
   * The number of entries in this kase matrix.
   *
   * @since 0.1.0
   */
  public val size: Int

  /**
   * @return a list of [KaseMatrixElement]s for the given [key].
   * @since 0.1.0
   */
  public operator fun <E : KaseMatrixElement<*>, K : KaseMatrixKey<E>> get(
    key: K
  ): KaseElementList<E, K>

  /**
   * @return a list of [KaseMatrixElement]s for the given [key], or
   *   `null` if no [KaseMatrixElement]s exist for the given [key].
   * @since 0.1.0
   */
  public fun <E : KaseMatrixElement<*>, K : KaseMatrixKey<E>> getOrNull(
    key: K
  ): KaseElementList<E, K>?

  /**
   * Returns a new [KaseMatrix] instance with the given [elements] added to the existing elements.
   *
   * @since 0.1.0
   */
  public fun <E : KaseMatrixElement<*>> plus(vararg elements: E): KaseMatrix {
    return plus(elements.toList())
  }

  /**
   * Returns a new [KaseMatrix] instance with the given [elements] added to the existing elements.
   *
   * @since 0.1.0
   */
  public operator fun <E : KaseMatrixElement<*>> plus(elements: Iterable<E>): KaseMatrix

  /**
   * Returns a new [KaseMatrix] instance with the given [key] removed from the existing elements.
   *
   * @since 0.1.0
   */
  public operator fun <E : KaseMatrixElement<*>> minus(
    key: KaseMatrixKey<E>
  ): KaseMatrix

  /**
   * Returns a new [KaseMatrix] instance with the given
   * [elements] removed from the existing elements.
   *
   * @since 0.1.0
   */
  public fun <E : KaseMatrixElement<*>> minus(vararg elements: E): KaseMatrix {
    return minus(elements.toList())
  }

  /**
   * Returns a new [KaseMatrix] instance with the given
   * [elements] removed from the existing elements.
   *
   * @since 0.1.0
   */
  public operator fun <E : KaseMatrixElement<*>> minus(elements: Iterable<E>): KaseMatrix

  /**
   * An element in a [KaseMatrix].
   *
   * @since 0.1.0
   */
  public interface KaseMatrixElement<out T> : HasLabel, Comparable<KaseMatrixElement<*>> {

    /**
     * The value of this element.
     *
     * @since 0.1.0
     */
    public val value: T

    override val label: String
      get() = this::class.java.simpleName

    /**
     * The [KaseMatrixKey] which can be used to retrieve this element from a [KaseMatrix].
     *
     * @since 0.1.0
     */
    public val key: KaseMatrixKey<KaseMatrixElement<T>>

    /**
     * Returns the element as a string in the format `$label: $value`.
     *
     * @since 0.5.0
     */
    public fun asString(): String = "$label: $value"

    override fun compareTo(other: KaseMatrixElement<*>): Int {
      return asString().compareTo(other.asString())
    }
  }

  /**
   * A key which can be used to retrieve a list of [KaseMatrixElement]s from a [KaseMatrix].
   *
   * @see KaseMatrix
   * @see KaseMatrixElement
   * @since 0.1.0
   */
  public interface KaseMatrixKey<out E : KaseMatrixElement<*>> : Comparable<KaseMatrixKey<*>> {

    override fun compareTo(other: KaseMatrixKey<*>): Int {
      return toString().compareTo(other.toString())
    }
  }

  public companion object {

    /**
     * Creates a new [KaseMatrix] instance from the given [elements]. The [elements]
     * can be retrieved from the matrix by their [KaseMatrixElement.key]s.
     *
     * @param elements the [KaseMatrixElement]s to group into a [KaseMatrix]
     * @return a new [KaseMatrix] instance
     * @since 0.1.0
     */
    public operator fun invoke(vararg elements: KaseMatrixElement<*>): KaseMatrix {
      return DefaultKaseMatrix(
        elements.groupBy { it.key }
          .mapValues { (key, list) ->
            KaseElementList(list, list.first(), key)
          }
      )
    }

    /**
     * Creates a new [KaseMatrix] instance from the given [elements]. The [elements]
     * can be retrieved from the matrix by their [KaseMatrixElement.key]s.
     *
     * @param elements the [KaseMatrixElement]s to group into a [KaseMatrix]
     * @return a new [KaseMatrix] instance
     * @since 0.1.0
     */
    public operator fun invoke(elements: List<KaseMatrixElement<*>>): KaseMatrix {
      return DefaultKaseMatrix(
        elements.groupBy { it.key }
          .mapValues { (key, list) ->
            KaseElementList(list, list.first(), key)
          }
      )
    }

    /**
     * Creates a new [KaseMatrix] instance from the given [elements]. The [elements]
     * can be retrieved from the matrix by their [KaseMatrixElement.key]s.
     *
     * @param elements the [KaseMatrixElement]s to group into a [KaseMatrix]
     * @return a new [KaseMatrix] instance
     * @since 0.1.0
     */
    @JvmName("invokeNested")
    public operator fun invoke(elements: List<List<KaseMatrixElement<*>>>): KaseMatrix {
      return invoke(elements.flatten())
    }
  }
}

/**
 * An implementation of [KaseMatrixKey] with a nicer `toString()`.
 *
 * @since 0.5.0
 */
@Suppress("UnnecessaryAbstractClass")
public abstract class AbstractKaseMatrixKey<E : KaseMatrixElement<*>> : KaseMatrixKey<E> {
  override fun toString(): String = this::class.java.name
}

/**
 * A container for a list of [KaseMatrixElement]s which share a common [key].
 *
 * @property elements the list of [KaseMatrixElement]s which share a common [key].
 * @property default the default [KaseMatrixElement] to use when no other element is available.
 * @property key the [KaseMatrixKey] which can be used to retrieve this list from a [KaseMatrix].
 * @see KaseMatrix
 * @since 0.1.0
 */
@Poko
public class KaseElementList<E : KaseMatrixElement<*>, out K : KaseMatrixKey<E>>(
  public val elements: List<E>,
  public val default: E,
  public val key: K
) : List<E> by elements {
  init {
    require(elements.isNotEmpty()) { "The elements list for key '$key' must not be empty." }
  }

  /**
   * Returns a new [KaseElementList] instance with the
   * given [elements] added to the existing elements.
   *
   * @since 0.1.0
   */
  public operator fun plus(others: Iterable<E>): KaseElementList<E, K> {
    return KaseElementList(elements + others, default, key)
  }

  /**
   * Returns a new [KaseElementList] instance with the
   * given [elements] removed from the existing elements.
   *
   * @since 0.1.0
   */
  public operator fun minus(others: Iterable<KaseMatrixElement<*>>): KaseElementList<E, K> {
    val otherSet = others.toSet()
    return KaseElementList(elements.filter { it !in otherSet }, default, key)
  }
}
