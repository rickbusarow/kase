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
import com.rickbusarow.kase.gradle.DependencyVersion.Agp.AgpKey
import com.rickbusarow.kase.gradle.DependencyVersion.Anvil.AnvilKey
import com.rickbusarow.kase.gradle.DependencyVersion.ComposeCompiler.ComposeCompilerKey
import com.rickbusarow.kase.gradle.DependencyVersion.Dagger.DaggerKey
import com.rickbusarow.kase.gradle.DependencyVersion.Detekt.DetektKey
import com.rickbusarow.kase.gradle.DependencyVersion.Gradle.GradleKey
import com.rickbusarow.kase.gradle.DependencyVersion.Kotlin.KotlinKey
import com.rickbusarow.kase.gradle.DependencyVersion.KotlinxSerialization.KotlinxSerializationKey
import com.rickbusarow.kase.gradle.DependencyVersion.Ksp.KspKey
import com.rickbusarow.kase.gradle.DependencyVersion.KtLint.KtLintKey
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey

/** A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01". */
@Suppress("UnnecessaryAbstractClass")
public abstract class AbstractDependencyVersion<out T, V, out K>(
  override val key: K
) : DependencyVersion<T, K>,
  Comparable<V>
  where K : VersionMatrixKey<V>,
        V : DependencyVersion<T, K> {

  override fun toString(): String = value.toString()
  override fun compareTo(other: V): Int = value.toString().compareTo(other.value.toString())
}

// /** A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01". */
// public inline fun <reified E, reified K> dependencyVersion(
//   value: String,
//   key: K
// ): DependencyVersion
//   where E : DependencyVersion,
//         K : VersionMatrixKey<E> {
//   return object :
//     DependencyVersion,
//     Comparable<E>,
//     CharSequence by value {
//
//     override val value: String = value
//     override val key: K = key
//
//     override fun compareTo(other: E): Int = value.compareTo(other.value)
//   }
// }

/** */
public interface DependencyVersion<out T, out K : VersionMatrixKey<DependencyVersion<T, K>>> :
  VersionMatrix.VersionMatrixElement<T>,
  HasLabel {

  /** */
  public class Gradle(
    override val value: String
  ) : AbstractDependencyVersion<String, Gradle, GradleKey>(GradleKey) {

    /** Key for [Gradle] dependency versions. */
    public companion object GradleKey : VersionMatrixKey<Gradle>
  }

  /** */
  public class Agp(
    override val value: String
  ) : AbstractDependencyVersion<String, Agp, AgpKey>(AgpKey) {

    /** Key for [Agp] dependency versions. */
    public companion object AgpKey : VersionMatrixKey<Agp>
  }

  /** */
  public class Kotlin(
    override val value: String
  ) : AbstractDependencyVersion<String, Kotlin, KotlinKey>(KotlinKey) {
    /** Key for [Kotlin] dependency versions. */
    public companion object KotlinKey : VersionMatrixKey<Kotlin>
  }

  /** */
  public class Ksp(
    override val value: String
  ) : AbstractDependencyVersion<String, Ksp, KspKey>(KspKey) {
    /** Key for [Ksp] dependency versions. */
    public companion object KspKey : VersionMatrixKey<Ksp>
  }

  /** */
  public class Anvil(
    override val value: String
  ) : AbstractDependencyVersion<String, Anvil, AnvilKey>(AnvilKey) {
    /** Key for [Anvil] dependency versions. */
    public companion object AnvilKey : VersionMatrixKey<Anvil>
  }

  /** */
  public class ComposeCompiler(
    override val value: String
  ) : AbstractDependencyVersion<String, ComposeCompiler, ComposeCompilerKey>(ComposeCompilerKey) {
    /** Key for [ComposeCompiler] dependency versions. */
    public companion object ComposeCompilerKey :
      VersionMatrixKey<ComposeCompiler>
  }

  /** */
  public class KotlinxSerialization(
    override val value: String
  ) : AbstractDependencyVersion<String, KotlinxSerialization, KotlinxSerializationKey>(
    KotlinxSerializationKey
  ) {
    /** Key for [KotlinxSerialization] dependency versions. */
    public companion object KotlinxSerializationKey :
      VersionMatrixKey<KotlinxSerialization>
  }

  /** */
  public class Dagger(
    override val value: String
  ) : AbstractDependencyVersion<String, Dagger, DaggerKey>(DaggerKey) {
    /** Key for [Dagger] dependency versions. */
    public companion object DaggerKey : VersionMatrixKey<Dagger>
  }

  /** */
  public class KtLint(
    override val value: String
  ) : AbstractDependencyVersion<String, KtLint, KtLintKey>(KtLintKey) {
    /** Key for [KtLint] dependency versions. */
    public companion object KtLintKey : VersionMatrixKey<KtLint>
  }

  /** */
  public class Detekt(
    override val value: String
  ) : AbstractDependencyVersion<String, Detekt, DetektKey>(DetektKey) {
    /** Key for [Detekt] dependency versions. */
    public companion object DetektKey : VersionMatrixKey<Detekt>
  }
}
