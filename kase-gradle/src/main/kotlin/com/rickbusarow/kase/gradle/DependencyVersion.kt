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

/**
 * A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01".
 *
 * @since 0.1.0
 */
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

/** @since 0.1.0 */
public interface DependencyVersion<out T, out K : VersionMatrixKey<DependencyVersion<T, K>>> :
  VersionMatrix.VersionMatrixElement<T>,
  HasLabel {

  /** @since 0.1.0 */
  public class Gradle(
    override val value: String
  ) : AbstractDependencyVersion<String, Gradle, GradleKey>(GradleKey) {

    /**
     * Key for [Gradle] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object GradleKey : VersionMatrixKey<Gradle>
  }

  /** @since 0.1.0 */
  public class Agp(
    override val value: String
  ) : AbstractDependencyVersion<String, Agp, AgpKey>(AgpKey) {

    /**
     * Key for [Agp] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object AgpKey : VersionMatrixKey<Agp>
  }

  /** @since 0.1.0 */
  public class Kotlin(
    override val value: String
  ) : AbstractDependencyVersion<String, Kotlin, KotlinKey>(KotlinKey) {
    /**
     * Key for [Kotlin] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object KotlinKey : VersionMatrixKey<Kotlin>
  }

  /** @since 0.1.0 */
  public class Ksp(
    override val value: String
  ) : AbstractDependencyVersion<String, Ksp, KspKey>(KspKey) {
    /**
     * Key for [Ksp] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object KspKey : VersionMatrixKey<Ksp>
  }

  /** @since 0.1.0 */
  public class Anvil(
    override val value: String
  ) : AbstractDependencyVersion<String, Anvil, AnvilKey>(AnvilKey) {
    /**
     * Key for [Anvil] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object AnvilKey : VersionMatrixKey<Anvil>
  }

  /** @since 0.1.0 */
  public class ComposeCompiler(
    override val value: String
  ) : AbstractDependencyVersion<String, ComposeCompiler, ComposeCompilerKey>(ComposeCompilerKey) {
    /**
     * Key for [ComposeCompiler] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object ComposeCompilerKey :
      VersionMatrixKey<ComposeCompiler>
  }

  /** @since 0.1.0 */
  public class KotlinxSerialization(
    override val value: String
  ) : AbstractDependencyVersion<String, KotlinxSerialization, KotlinxSerializationKey>(
    KotlinxSerializationKey
  ) {
    /**
     * Key for [KotlinxSerialization] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object KotlinxSerializationKey :
      VersionMatrixKey<KotlinxSerialization>
  }

  /** @since 0.1.0 */
  public class Dagger(
    override val value: String
  ) : AbstractDependencyVersion<String, Dagger, DaggerKey>(DaggerKey) {
    /**
     * Key for [Dagger] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object DaggerKey : VersionMatrixKey<Dagger>
  }

  /** @since 0.1.0 */
  public class KtLint(
    override val value: String
  ) : AbstractDependencyVersion<String, KtLint, KtLintKey>(KtLintKey) {
    /**
     * Key for [KtLint] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object KtLintKey : VersionMatrixKey<KtLint>
  }

  /** @since 0.1.0 */
  public class Detekt(
    override val value: String
  ) : AbstractDependencyVersion<String, Detekt, DetektKey>(DetektKey) {
    /**
     * Key for [Detekt] dependency versions.
     *
     * @since 0.1.0
     */
    public companion object DetektKey : VersionMatrixKey<Detekt>
  }
}
