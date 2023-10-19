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

package com.rickbusarow.kase.gradle.internal

import com.rickbusarow.kase.gradle.VersionsMatrix
import com.rickbusarow.kase.gradle.VersionsMatrix.Element
import kotlin.reflect.KClass

internal class DefaultVersionMatrix(
  private val map: Map<KClass<*>, List<Element>>
) : VersionsMatrix {

  override fun <E : Element> getOrNull(key: KClass<E>): List<E>? {
    @Suppress("UNCHECKED_CAST")
    return map[key] as? List<E>
  }

  override fun <E : Element> getOrEmpty(key: KClass<E>): List<E> = getOrNull(key).orEmpty()

  override fun <E : Element> get(key: KClass<E>): List<E> {
    return requireNotNull(getOrNull(key)) {
      "There is no entry in the matrix for the key ${key.qualifiedName}"
    }
  }

  override fun <E : Element> plus(elements: List<E>): VersionsMatrix {

    if (elements.isEmpty()) return DefaultVersionMatrix(map.toMap())

    val newElementsMap = elements.groupBy { it::class }
      .mapValues { (key, list) -> list + getOrEmpty(key) }

    return DefaultVersionMatrix(map.plus(newElementsMap))
  }

  override fun <E : Element> minus(key: KClass<E>): VersionsMatrix {
    return DefaultVersionMatrix(map.minus(key))
  }

  override fun <E : Element> minus(elements: List<E>): VersionsMatrix {

    if (elements.isEmpty()) return DefaultVersionMatrix(map.toMap())

    val elementSet = elements.toSet()
    val newMap = map.mapValues { (_, list) -> list - elementSet }

    return DefaultVersionMatrix(newMap)
  }
}
