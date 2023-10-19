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

/** A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01". */
@Suppress("UnnecessaryAbstractClass")
public abstract class AbstractDependencyVersion(
  override val value: String
) : DependencyVersion {
  override fun toString(): String = "$label: $value"
}

/** A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01". */
public inline fun <reified E : DependencyVersion> dependencyVersion(
  value: String
): DependencyVersion {
  return object :
    DependencyVersion,
    Comparable<E>,
    CharSequence by value {
    override val value: String = value

    override fun compareTo(other: E): Int = value.compareTo(other.value)
  }
}

/** */
public interface DependencyVersion : HasLabel, VersionsMatrix.Element {

  /** */
  public val value: String

  /** */
  override val label: String
    get() = this::class.java.simpleName

  /** */
  public class Gradle(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Gradle>(value)

  /** */
  public class Agp(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Agp>(value)

  /** */
  public class Kotlin(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Kotlin>(value)

  /** */
  public class Ksp(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Ksp>(value)

  /** */
  public class Anvil(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Anvil>(value)

  /** */
  public class Keeper(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Keeper>(value)

  /** */
  public class Dagger(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Dagger>(value)

  /** */
  public class Detekt(
    override val value: String
  ) : DependencyVersion by dependencyVersion<Detekt>(value)
}
