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
import com.rickbusarow.kase.gradle.AgpDependencyVersion.AgpKey
import com.rickbusarow.kase.gradle.AnvilDependencyVersion.AnvilKey
import com.rickbusarow.kase.gradle.ComposeCompilerDependencyVersion.ComposeCompilerKey
import com.rickbusarow.kase.gradle.DaggerDependencyVersion.DaggerKey
import com.rickbusarow.kase.gradle.DetektDependencyVersion.DetektKey
import com.rickbusarow.kase.gradle.GradleDependencyVersion.GradleKey
import com.rickbusarow.kase.gradle.KotlinDependencyVersion.KotlinKey
import com.rickbusarow.kase.gradle.KotlinxSerializationDependencyVersion.KotlinxSerializationKey
import com.rickbusarow.kase.gradle.KspDependencyVersion.KspKey
import com.rickbusarow.kase.gradle.KtLintDependencyVersion.KtLintKey
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
  HasLabel

/** @since 0.1.0 */
public class GradleDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, GradleDependencyVersion, GradleKey>(GradleKey) {

  /**
   * Key for [GradleDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object GradleKey : VersionMatrixKey<GradleDependencyVersion>
}

/** Trait interface for [GradleDependencyVersion] */
public interface HasGradleDependencyVersion {
  /** */
  public val gradle: GradleDependencyVersion

  /** */
  public val gradleVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: GradleDependencyVersion
    ): HasGradleDependencyVersion = object : HasGradleDependencyVersion {
      override val gradle: GradleDependencyVersion = version
      override val gradleVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class AgpDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, AgpDependencyVersion, AgpKey>(AgpKey) {

  /**
   * Key for [AgpDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object AgpKey : VersionMatrixKey<AgpDependencyVersion>
}

/** Trait interface for [AgpDependencyVersion] */
public interface HasAgpDependencyVersion {
  /** */
  public val agp: AgpDependencyVersion

  /** */
  public val agpVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: AgpDependencyVersion
    ): HasAgpDependencyVersion = object : HasAgpDependencyVersion {
      override val agp: AgpDependencyVersion = version
      override val agpVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class KotlinDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, KotlinDependencyVersion, KotlinKey>(KotlinKey) {
  /**
   * Key for [KotlinDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KotlinKey : VersionMatrixKey<KotlinDependencyVersion>
}

/** Trait interface for [KotlinDependencyVersion] */
public interface HasKotlinDependencyVersion {
  /** */
  public val kotlin: KotlinDependencyVersion

  /** */
  public val kotlinVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: KotlinDependencyVersion
    ): HasKotlinDependencyVersion = object : HasKotlinDependencyVersion {
      override val kotlin: KotlinDependencyVersion = version
      override val kotlinVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class KspDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, KspDependencyVersion, KspKey>(KspKey) {
  /**
   * Key for [KspDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KspKey : VersionMatrixKey<KspDependencyVersion>
}

/** Trait interface for [KspDependencyVersion] */
public interface HasKspDependencyVersion {
  /** */
  public val ksp: KspDependencyVersion

  /** */
  public val kspVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: KspDependencyVersion
    ): HasKspDependencyVersion = object : HasKspDependencyVersion {
      override val ksp: KspDependencyVersion = version
      override val kspVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class AnvilDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, AnvilDependencyVersion, AnvilKey>(AnvilKey) {
  /**
   * Key for [AnvilDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object AnvilKey : VersionMatrixKey<AnvilDependencyVersion>
}

/** Trait interface for [AnvilDependencyVersion] */
public interface HasAnvilDependencyVersion {
  /** */
  public val anvil: AnvilDependencyVersion

  /** */
  public val anvilVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: AnvilDependencyVersion
    ): HasAnvilDependencyVersion = object : HasAnvilDependencyVersion {
      override val anvil: AnvilDependencyVersion = version
      override val anvilVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class ComposeCompilerDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, ComposeCompilerDependencyVersion, ComposeCompilerKey>(
  ComposeCompilerKey
) {
  /**
   * Key for [ComposeCompilerDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object ComposeCompilerKey :
    VersionMatrixKey<ComposeCompilerDependencyVersion>
}

/** Trait interface for [ComposeCompilerDependencyVersion] */
public interface HasComposeCompilerDependencyVersion {
  /** */
  public val composeCompiler: ComposeCompilerDependencyVersion

  /** */
  public val composeCompilerVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: ComposeCompilerDependencyVersion
    ): HasComposeCompilerDependencyVersion = object : HasComposeCompilerDependencyVersion {
      override val composeCompiler: ComposeCompilerDependencyVersion = version
      override val composeCompilerVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class KotlinxSerializationDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, KotlinxSerializationDependencyVersion, KotlinxSerializationKey>(
  KotlinxSerializationKey
) {
  /**
   * Key for [KotlinxSerializationDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KotlinxSerializationKey :
    VersionMatrixKey<KotlinxSerializationDependencyVersion>
}

/** Trait interface for [KotlinxSerializationDependencyVersion] */
public interface HasKotlinxSerializationDependencyVersion {
  /** */
  public val kotlinxSerialization: KotlinxSerializationDependencyVersion

  /** */
  public val kotlinxSerializationVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: KotlinxSerializationDependencyVersion
    ): HasKotlinxSerializationDependencyVersion =
      object : HasKotlinxSerializationDependencyVersion {
        override val kotlinxSerialization: KotlinxSerializationDependencyVersion = version
        override val kotlinxSerializationVersion: String = version.value
      }
  }
}

/** @since 0.1.0 */
public class DaggerDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, DaggerDependencyVersion, DaggerKey>(DaggerKey) {
  /**
   * Key for [DaggerDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object DaggerKey : VersionMatrixKey<DaggerDependencyVersion>
}

/** Trait interface for [DaggerDependencyVersion] */
public interface HasDaggerDependencyVersion {
  /** */
  public val dagger: DaggerDependencyVersion

  /** */
  public val daggerVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: DaggerDependencyVersion
    ): HasDaggerDependencyVersion = object : HasDaggerDependencyVersion {
      override val dagger: DaggerDependencyVersion = version
      override val daggerVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class KtLintDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, KtLintDependencyVersion, KtLintKey>(KtLintKey) {
  /**
   * Key for [KtLintDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KtLintKey : VersionMatrixKey<KtLintDependencyVersion>
}

/** Trait interface for [KtLintDependencyVersion] */
public interface HasKtLintDependencyVersion {
  /** */
  public val ktlint: KtLintDependencyVersion

  /** */
  public val ktlintVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: KtLintDependencyVersion
    ): HasKtLintDependencyVersion = object : HasKtLintDependencyVersion {
      override val ktlint: KtLintDependencyVersion = version
      override val ktlintVersion: String = version.value
    }
  }
}

/** @since 0.1.0 */
public class DetektDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<String, DetektDependencyVersion, DetektKey>(DetektKey) {
  /**
   * Key for [DetektDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object DetektKey : VersionMatrixKey<DetektDependencyVersion>
}

/** Trait interface for [DetektDependencyVersion] */
public interface HasDetektDependencyVersion {
  /** */
  public val detekt: DetektDependencyVersion

  /** */
  public val detektVersion: String

  public companion object {
    /** */
    public operator fun invoke(
      version: DetektDependencyVersion
    ): HasDetektDependencyVersion = object : HasDetektDependencyVersion {
      override val detekt: DetektDependencyVersion = version
      override val detektVersion: String = version.value
    }
  }
}
