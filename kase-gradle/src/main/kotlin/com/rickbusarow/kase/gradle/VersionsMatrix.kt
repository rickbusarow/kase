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

import com.rickbusarow.kase.gradle.VersionsMatrix.Element

/** */
public interface VersionsMatrix {

  /** */
  public operator fun <E : Element> get(key: Element.Key<E>): List<E>

  /** */
  public fun <E : Element> getOrEmpty(key: Element.Key<E>): List<E>

  /** */
  public fun <E : Element> getOrNull(key: Element.Key<E>): List<E>?

  /** */
  public operator fun <E : Element> plus(elements: List<E>): VersionsMatrix

  /** */
  public operator fun <E : Element> minus(key: Element.Key<E>): VersionsMatrix

  /** */
  public operator fun <E : Element> minus(elements: List<E>): VersionsMatrix

  /** */
  public interface Element {

    /** */
    public val key: Key<*>

    /** */
    public interface Key<E : Element>

    public companion object {
      public operator fun <E : Element, K : Key<E>> invoke(key: K): Element {
        return object : Element {
          override val key: Key<*> = key
        }
      }
    }
  }

  public companion object {
    /** */
    public operator fun invoke(elements: List<Element>): VersionsMatrix {
      return DefaultVersionMatrix(elements.groupBy { it.key })
    }
  }
}

internal class DefaultVersionMatrix(
  private val map: Map<Element.Key<*>, List<Element>>
) : VersionsMatrix {

  override fun <E : Element> getOrNull(key: Element.Key<E>): List<E>? {
    @Suppress("UNCHECKED_CAST")
    return map[key] as? List<E>
  }

  override fun <E : Element> getOrEmpty(
    key: Element.Key<E>
  ): List<E> {
    return getOrNull(key) ?: emptyList()
  }

  override fun <E : Element> get(key: Element.Key<E>): List<E> {
    return requireNotNull(getOrNull(key)) {
      "There is no entry in the matrix for the key ${key::class.qualifiedName}"
    }
  }

  override fun <E : Element> plus(elements: List<E>): VersionsMatrix {

    if (elements.isEmpty()) return DefaultVersionMatrix(map.toMap())

    val newElementsMap = elements.groupBy { it.key }
      .mapValues { (key, list) -> list + getOrEmpty(key) }

    return DefaultVersionMatrix(map.plus(newElementsMap))
  }

  override fun <E : Element> minus(key: Element.Key<E>): VersionsMatrix {
    return DefaultVersionMatrix(map.minus(key))
  }

  override fun <E : Element> minus(elements: List<E>): VersionsMatrix {

    if (elements.isEmpty()) return DefaultVersionMatrix(map.toMap())

    val elementSet = elements.toSet()
    val newMap = map.mapValues { (_, list) -> list - elementSet }

    return DefaultVersionMatrix(newMap)
  }
}
