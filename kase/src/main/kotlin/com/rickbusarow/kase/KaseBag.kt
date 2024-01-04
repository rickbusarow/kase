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
import com.rickbusarow.kase.internal.DefaultKaseBag

/**
 * A polymorphic [Kase] which contains a list of [KaseMatrixElement][KaseMatrix.KaseMatrixElement]s.
 *
 * @since 0.5.0
 */
public interface KaseBag : Kase {
  /**
   * All elements (values) in this bag. Implementations should
   * preserve the same order in which the elements were added.
   *
   * @since 0.5.0
   */
  public val elements: List<KaseMatrixElement<*>>

  /** @since 0.5.0 */
  public val keys: Set<KaseMatrixKey<KaseMatrixElement<*>>>

  /**
   * Returns the element corresponding to this key, or throws if that key is not present.
   *
   * @since 0.5.0
   * @throws IllegalArgumentException if the key is not present in this bag.
   */
  public fun <K : KaseMatrixKey<E>, E : KaseMatrixElement<*>> get(key: K): E

  /**
   * Returns the element corresponding to this key, or null if that key is not present.
   *
   * @since 0.5.0
   */
  public fun <K : KaseMatrixKey<E>, E : KaseMatrixElement<*>> getOrNull(key: K): E?

  public companion object {
    /**
     * Creates a new [KaseBag] from the given [elements] and [displayName].
     *
     * @since 0.5.0
     */
    public operator fun invoke(
      elements: List<KaseMatrixElement<*>>,
      displayName: String = elements.joinToString(separator = " | ") { it.asString() }
    ): KaseBag = DefaultKaseBag(elements = elements, displayName = displayName)
  }
}
