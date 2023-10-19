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
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey

/** A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01". */
@Suppress("UnnecessaryAbstractClass")
public abstract class AbstractDependencyVersion(
  override val value: String
) : DependencyVersion {
  override fun toString(): String = "$label: $value"
}

/** A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01". */
public inline fun <reified E, reified K> dependencyVersion(
  value: String,
  key: K
): DependencyVersion
  where E : DependencyVersion,
        K : VersionMatrix.VersionMatrixKey<E> {
  return object :
    DependencyVersion,
    Comparable<E>,
    CharSequence by value {
    override val value: String = value
    override val key: K = key

    override fun compareTo(other: E): Int = value.compareTo(other.value)
  }
}

/** */
public interface DependencyVersion :
  HasLabel,
  VersionMatrix.VersionMatrixElement {

  /** */
  public val value: String

  /** */
  override val label: String
    get() = this::class.java.simpleName

  /** */
  public class Gradle(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, GradleKey) {
    /** Key for [Gradle] dependency versions. */
    public companion object GradleKey : VersionMatrixKey<Gradle>
  }

  /** */
  public class Agp(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, AgpKey) {
    /** Key for [Agp] dependency versions. */
    public companion object AgpKey : VersionMatrixKey<Agp>
  }

  /** */
  public class Kotlin(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, KotlinKey) {
    /** Key for [Kotlin] dependency versions. */
    public companion object KotlinKey : VersionMatrixKey<Kotlin>
  }

  /** */
  public class Ksp(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, KspKey) {
    /** Key for [Ksp] dependency versions. */
    public companion object KspKey : VersionMatrixKey<Ksp>
  }

  /** */
  public class Anvil(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, AnvilKey) {
    /** Key for [Anvil] dependency versions. */
    public companion object AnvilKey : VersionMatrixKey<Anvil>
  }

  /** */
  public class ComposeCompiler(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, ComposeCompilerKey) {
    /** Key for [ComposeCompiler] dependency versions. */
    public companion object ComposeCompilerKey :
      VersionMatrixKey<ComposeCompiler>
  }

  /** */
  public class KotlinxSerialization(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, KotlinxSerializationKey) {
    /** Key for [KotlinxSerialization] dependency versions. */
    public companion object KotlinxSerializationKey :
      VersionMatrixKey<KotlinxSerialization>
  }

  /** */
  public class Dagger(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, DaggerKey) {
    /** Key for [Dagger] dependency versions. */
    public companion object DaggerKey : VersionMatrixKey<Dagger>
  }

  /** */
  public class KtLint(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, KtLintKey) {
    /** Key for [KtLint] dependency versions. */
    public companion object KtLintKey : VersionMatrixKey<KtLint>
  }

  /** */
  public class Detekt(
    override val value: String
  ) : DependencyVersion by dependencyVersion(value, DetektKey) {
    /** Key for [Detekt] dependency versions. */
    public companion object DetektKey : VersionMatrixKey<Detekt>
  }
}
