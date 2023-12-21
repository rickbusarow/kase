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

package com.rickbusarow.kase.internal

import com.rickbusarow.kase.KaseBag
import com.rickbusarow.kase.KaseMatrix.KaseMatrixElement
import com.rickbusarow.kase.KaseMatrix.KaseMatrixKey

internal class DefaultKaseBag(
  override val elements: List<KaseMatrixElement<*>>,
  override val displayName: String = elements.joinToString(separator = " | ") { it.asString() }
) : KaseBag {
  private val map by lazy(LazyThreadSafetyMode.NONE) {
    elements.associateBy { element -> element.key }
      .also {
        assert(it.size == elements.size) {
          "Duplicate keys found in KaseBag: ${elements.map { it.key }}"
        }
      }
  }

  override val keys: Set<KaseMatrixKey<KaseMatrixElement<*>>>
    get() = map.keys

  override fun <K : KaseMatrixKey<E>, E : KaseMatrixElement<*>> get(key: K): E {
    @Suppress("UNCHECKED_CAST")
    return requireNotNull(map[key]) { "There is no element for this key: $key" } as E
  }

  override fun <K : KaseMatrixKey<E>, E : KaseMatrixElement<*>> getOrNull(key: K): E? {
    val any = map[key] ?: return null
    @Suppress("UNCHECKED_CAST")
    return any as E
  }
}
