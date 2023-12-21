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

package com.rickbusarow.kase.internal

import com.rickbusarow.kase.KaseElementList
import com.rickbusarow.kase.KaseMatrix
import com.rickbusarow.kase.KaseMatrix.KaseMatrixElement
import com.rickbusarow.kase.KaseMatrix.KaseMatrixKey

internal typealias AnyKey = KaseMatrixKey<KaseMatrixElement<*>>

internal class DefaultKaseMatrix(
  private val map: Map<AnyKey, KaseElementList<*, AnyKey>>
) : KaseMatrix {

  override val size: Int get() = map.size
  override fun keys(): Set<AnyKey> = map.keys

  override fun <E : KaseMatrixElement<*>, K : KaseMatrixKey<E>> getOrNull(
    key: K
  ): KaseElementList<E, K>? {
    @Suppress("UNCHECKED_CAST")
    return map[key] as? KaseElementList<E, K>
  }

  override fun <E : KaseMatrixElement<*>, K : KaseMatrixKey<E>> get(
    key: K
  ): KaseElementList<E, K> {
    return requireNotNull(getOrNull(key)) {
      "There is no entry in the matrix for the key $key"
    }
  }

  override fun <E : KaseMatrixElement<*>> plus(elements: Iterable<E>): KaseMatrix {

    if (!elements.iterator().hasNext()) return DefaultKaseMatrix(map.toMap())

    val newElementsMap = elements.groupBy { it.key }
      .mapValues { (key, list) ->

        val existing =
          getOrNull(key)

        if (existing != null) {
          existing + list
        } else {
          KaseElementList(list, list.first(), key)
        }
      }

    return DefaultKaseMatrix(map + newElementsMap)
  }

  override fun <E : KaseMatrixElement<*>> minus(key: KaseMatrixKey<E>): KaseMatrix {
    return DefaultKaseMatrix(map.minus(key))
  }

  override fun <E : KaseMatrixElement<*>> minus(elements: Iterable<E>): KaseMatrix {

    if (!elements.iterator().hasNext()) return DefaultKaseMatrix(map.toMap())

    val elementSet = elements.toSet()
    val newMap = map
      .mapValues { (_, list) -> list - elementSet }

    return DefaultKaseMatrix(newMap)
  }
}
