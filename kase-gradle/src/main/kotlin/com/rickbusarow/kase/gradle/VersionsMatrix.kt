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
import kotlin.reflect.KClass

/** */
public interface VersionsMatrix {

  /** */
  public operator fun <E : Element> get(key: KClass<E>): List<E>

  /** */
  public fun <E : Element> getOrEmpty(key: KClass<E>): List<E>

  /** */
  public fun <E : Element> getOrNull(key: KClass<E>): List<E>?

  /** */
  public operator fun <E : Element> plus(elements: List<E>): VersionsMatrix

  /** */
  public operator fun <E : Element> minus(key: KClass<E>): VersionsMatrix

  /** */
  public operator fun <E : Element> minus(elements: List<E>): VersionsMatrix

  /** */
  public interface Element

  public companion object {
    /** */
    public operator fun invoke(elements: List<Element>): VersionsMatrix {
      return DefaultVersionMatrix(elements.groupBy { it::class })
    }

    /** */
    public operator fun invoke(elements: List<List<Element>>): VersionsMatrix {
      return invoke(elements.flatten())
    }

    /** */
    public inline fun <reified E : Element> VersionsMatrix.get(): List<E> = get(E::class)

    /** */
    public inline fun <reified E : Element> VersionsMatrix.getOrEmpty(): List<E> {
      return getOrEmpty(E::class)
    }

    /** */
    public inline fun <reified E : Element> VersionsMatrix.getOrNull(): List<E>? {
      return getOrNull(E::class)
    }
  }
}
