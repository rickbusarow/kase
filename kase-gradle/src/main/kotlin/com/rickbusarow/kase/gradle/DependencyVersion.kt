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

/** */
public interface DependencyVersion :
  Comparable<CharSequence>,
  CharSequence,
  HasLabel,
  VersionsMatrix.Element {
  /** */

  public val value: String

  /** */
  override val label: String
    get() = this::class.java.simpleName

  override fun compareTo(other: CharSequence): Int = value.compareTo(other.toString())

  /** */
  @JvmInline
  public value class Gradle(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Gradle>
  }

  /** */
  @JvmInline
  public value class Agp(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Agp>
  }

  /** */
  @JvmInline
  public value class Kotlin(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Kotlin>
  }

  /** */
  @JvmInline
  public value class Ksp(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Ksp>
  }

  /** */
  @JvmInline
  public value class Anvil(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Anvil>
  }

  /** */
  @JvmInline
  public value class Keeper(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Keeper>
  }

  /** */
  @JvmInline
  public value class Dagger(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Dagger>
  }

  /** */
  @JvmInline
  public value class Detekt(override val value: String) :
    DependencyVersion,
    CharSequence by value,
    VersionsMatrix.Element {

    /** */
    override val key: Key get() = Key

    override fun toString(): String = "$label: $value"

    /** */
    public companion object Key : VersionsMatrix.Element.Key<Detekt>
  }
}
