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
import com.rickbusarow.kase.gradle.VersionsMatrix.Element.Key

public abstract class AbstractDependencyVersion<E, K>(
  override val value: String,
  override val key: K
) : DependencyVersion
  where E : DependencyVersion,
        K : Key<E> {
  override fun toString(): String = "$label: $value"
}

public inline fun <reified E, reified K> dependencyVersion(
  value: String,
  key: K
): DependencyVersion
  where E : DependencyVersion,
        K : Key<E> {
  return object : DependencyVersion, Comparable<E>, CharSequence by value {
    override val value: String = value
    override val key: K = key

    override fun compareTo(other: E): Int = value.compareTo(other.value)
  }
}

/** */
public interface DependencyVersion :
  HasLabel,
  VersionsMatrix.Element {
  /** */

  public val value: String

  /** */
  override val label: String
    get() = this::class.java.simpleName

  /** */
  public class Gradle(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Key) {
    public companion object Key : VersionsMatrix.Element.Key<Gradle>, HasLabel {
      override val label: String = "Gradle"
    }
  }

  /** */
  public class Agp(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Key) {
    public companion object Key : VersionsMatrix.Element.Key<Agp>
  }

  /** */
  public class Kotlin(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Key) {
    public companion object Key : VersionsMatrix.Element.Key<Kotlin>
  }

  /** */
  public class Ksp(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Key) {
    public companion object Key : VersionsMatrix.Element.Key<Ksp>
  }

  /** */
  public class Anvil(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Key) {
    public companion object Key : VersionsMatrix.Element.Key<Anvil>
  }

  /** */
  public class Keeper(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Anvil) {
    public companion object Key : VersionsMatrix.Element.Key<Keeper>
  }

  /** */
  public class Dagger(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Anvil) {
    public companion object Key : VersionsMatrix.Element.Key<Dagger>
  }

  /** */
  public class Detekt(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, Anvil) {
    public companion object Key : VersionsMatrix.Element.Key<Detekt>
  }
}
