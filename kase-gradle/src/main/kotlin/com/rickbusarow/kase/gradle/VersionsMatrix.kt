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

import com.rickbusarow.kase.gradle.internal.DefaultVersionMatrix

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
