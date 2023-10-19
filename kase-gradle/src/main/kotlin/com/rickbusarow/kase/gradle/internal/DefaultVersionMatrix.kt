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

@file:Suppress("MemberVisibilityCanBePrivate")

package com.rickbusarow.kase.gradle.internal

import com.rickbusarow.kase.gradle.VersionList
import com.rickbusarow.kase.gradle.VersionMatrix
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixElement
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey

internal typealias AnyKey = VersionMatrixKey<VersionMatrixElement>

internal class DefaultVersionMatrix(
  private val map: Map<AnyKey, VersionList<*, AnyKey>>
) : VersionMatrix {

  override val size: Int get() = map.size
  override fun keys(): Set<AnyKey> = map.keys

  override fun <E : VersionMatrixElement, K : VersionMatrixKey<E>> getOrNull(
    key: K
  ): VersionList<E, K>? {
    @Suppress("UNCHECKED_CAST")
    return map[key] as? VersionList<E, K>
  }

  override fun <E : VersionMatrixElement, K : VersionMatrixKey<E>> get(
    key: K
  ): VersionList<E, K> {
    return requireNotNull(getOrNull(key)) {
      "There is no entry in the matrix for the key $key"
    }
  }

  override fun <E : VersionMatrixElement> plus(elements: Iterable<E>): VersionMatrix {

    if (!elements.iterator().hasNext()) return DefaultVersionMatrix(map.toMap())

    val newElementsMap = elements.groupBy { it.key }
      .mapValues { (key, list) ->

        val existing =
          getOrNull(key)

        if (existing != null) {
          existing + list
        } else {
          VersionList(list, list.first(), key)
        }
      }

    return DefaultVersionMatrix(map + newElementsMap)
  }

  override fun <E : VersionMatrixElement> minus(key: VersionMatrixKey<E>): VersionMatrix {
    return DefaultVersionMatrix(map.minus(key))
  }

  override fun <E : VersionMatrixElement> minus(elements: Iterable<E>): VersionMatrix {

    if (!elements.iterator().hasNext()) return DefaultVersionMatrix(map.toMap())

    val elementSet = elements.toSet()
    val newMap = map
      .mapValues { (_, list) -> list - elementSet }

    return DefaultVersionMatrix(newMap)
  }
}
