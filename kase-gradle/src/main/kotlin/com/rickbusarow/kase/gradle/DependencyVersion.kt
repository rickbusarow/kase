/*
 * Copyright (C) 2025 Rick Busarow
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
import com.rickbusarow.kase.KaseMatrix
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
import kotlin.LazyThreadSafetyMode.NONE

/**
 * A type-safe wrapper for a dependency version string, such as "1.0.0" or "1.0.0-alpha01".
 *
 * @since 0.1.0
 */
@Suppress("UnnecessaryAbstractClass")
public abstract class AbstractDependencyVersion<SELF, out KEY>(
  override val key: KEY
) : DependencyVersion<KEY>
  where KEY : KaseMatrix.KaseMatrixKey<SELF>,
        SELF : DependencyVersion<KEY> {

  private val split: List<String> by lazy(NONE) { value.split("[.-]".toRegex(), limit = 4) }
  override val major: Int by lazy(NONE) { split[0].toInt() }
  override val minor: Int by lazy(NONE) { split.getOrNull(1)?.toInt() ?: 0 }
  override val patch: Int by lazy(NONE) { split.getOrNull(2)?.toInt() ?: 0 }
  override val preRelease: String? by lazy(NONE) {
    @Suppress("MagicNumber")
    split.getOrNull(3)
  }

  override fun toString(): String = value
  override fun equals(other: Any?): Boolean {
    return when {
      this === other -> true
      other !is AbstractDependencyVersion<*, *> -> false
      key != other.key -> false
      value != other.value -> false
      else -> true
    }
  }

  override fun hashCode(): Int {
    return key.hashCode()
  }
}

/** @since 0.1.0 */
public interface DependencyVersion<out K : KaseMatrix.KaseMatrixKey<DependencyVersion<K>>> :
  KaseMatrix.KaseMatrixElement<String>,
  HasLabel {

  /** ex: `1` in `1.2.3-beta04` */
  public val major: Int

  /** ex: `2` in `1.2.3-beta04` */
  public val minor: Int

  /** ex: `3` in `1.2.3-beta04` */
  public val patch: Int

  /** ex: `beta04` in `1.2.3-beta04` */
  public val preRelease: String?

  override fun compareTo(other: KaseMatrix.KaseMatrixElement<*>): Int {
    return when (other) {
      is DependencyVersion<*> -> {
        compareValuesBy(
          this,
          other,
          { it.major },
          { it.minor },
          { it.patch },
          { it.preRelease == null },
          { it.preRelease }
        )
      }

      else -> super.compareTo(other)
    }
  }
}

/** @since 0.1.0 */
public class GradleDependencyVersion(
  override val value: String
) : AbstractDependencyVersion<GradleDependencyVersion, GradleKey>(GradleKey) {

  /**
   * Key for [GradleDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object GradleKey : KaseMatrix.KaseMatrixKey<GradleDependencyVersion> {
    /**
     * pulls the value from `GradleVersion.current().version`
     *
     * @since 0.4.0
     */
    public fun current(): GradleDependencyVersion = GradleDependencyVersion(
      value = org.gradle.util.GradleVersion.current().version
    )
  }
}

/**
 * Trait interface for [GradleDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasGradleDependencyVersion {
  /** @since 0.3.0 */
  public val gradle: GradleDependencyVersion

  /** @since 0.3.0 */
  public val gradleVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<AgpDependencyVersion, AgpKey>(AgpKey) {

  /**
   * Key for [AgpDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object AgpKey : KaseMatrix.KaseMatrixKey<AgpDependencyVersion>
}

/**
 * Trait interface for [AgpDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasAgpDependencyVersion {
  /** @since 0.3.0 */
  public val agp: AgpDependencyVersion

  /** @since 0.3.0 */
  public val agpVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<KotlinDependencyVersion, KotlinKey>(KotlinKey) {
  /**
   * Key for [KotlinDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KotlinKey : KaseMatrix.KaseMatrixKey<KotlinDependencyVersion> {
    /**
     * pulls the value from `KotlinVersion.CURRENT.toString()`
     *
     * @since 0.4.0
     */
    public fun current(): KotlinDependencyVersion = KotlinDependencyVersion(
      value = KotlinVersion.CURRENT.toString()
    )
  }
}

/**
 * Trait interface for [KotlinDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasKotlinDependencyVersion {
  /** @since 0.3.0 */
  public val kotlin: KotlinDependencyVersion

  /** @since 0.3.0 */
  public val kotlinVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<KspDependencyVersion, KspKey>(KspKey) {
  /**
   * Key for [KspDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KspKey : KaseMatrix.KaseMatrixKey<KspDependencyVersion>
}

/**
 * Trait interface for [KspDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasKspDependencyVersion {
  /** @since 0.3.0 */
  public val ksp: KspDependencyVersion

  /** @since 0.3.0 */
  public val kspVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<AnvilDependencyVersion, AnvilKey>(AnvilKey) {
  /**
   * Key for [AnvilDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object AnvilKey : KaseMatrix.KaseMatrixKey<AnvilDependencyVersion>
}

/**
 * Trait interface for [AnvilDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasAnvilDependencyVersion {
  /** @since 0.3.0 */
  public val anvil: AnvilDependencyVersion

  /** @since 0.3.0 */
  public val anvilVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<ComposeCompilerDependencyVersion, ComposeCompilerKey>(
  ComposeCompilerKey
) {
  /**
   * Key for [ComposeCompilerDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object ComposeCompilerKey :
    KaseMatrix.KaseMatrixKey<ComposeCompilerDependencyVersion>
}

/**
 * Trait interface for [ComposeCompilerDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasComposeCompilerDependencyVersion {
  /** @since 0.3.0 */
  public val composeCompiler: ComposeCompilerDependencyVersion

  /** @since 0.3.0 */
  public val composeCompilerVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<KotlinxSerializationDependencyVersion, KotlinxSerializationKey>(
  KotlinxSerializationKey
) {
  /**
   * Key for [KotlinxSerializationDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KotlinxSerializationKey :
    KaseMatrix.KaseMatrixKey<KotlinxSerializationDependencyVersion>
}

/**
 * Trait interface for [KotlinxSerializationDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasKotlinxSerializationDependencyVersion {
  /** @since 0.3.0 */
  public val kotlinxSerialization: KotlinxSerializationDependencyVersion

  /** @since 0.3.0 */
  public val kotlinxSerializationVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<DaggerDependencyVersion, DaggerKey>(DaggerKey) {
  /**
   * Key for [DaggerDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object DaggerKey : KaseMatrix.KaseMatrixKey<DaggerDependencyVersion>
}

/**
 * Trait interface for [DaggerDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasDaggerDependencyVersion {
  /** @since 0.3.0 */
  public val dagger: DaggerDependencyVersion

  /** @since 0.3.0 */
  public val daggerVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<KtLintDependencyVersion, KtLintKey>(KtLintKey) {
  /**
   * Key for [KtLintDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object KtLintKey : KaseMatrix.KaseMatrixKey<KtLintDependencyVersion>
}

/**
 * Trait interface for [KtLintDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasKtLintDependencyVersion {
  /** @since 0.3.0 */
  public val ktlint: KtLintDependencyVersion

  /** @since 0.3.0 */
  public val ktlintVersion: String

  public companion object {
    /** @since 0.3.0 */
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
) : AbstractDependencyVersion<DetektDependencyVersion, DetektKey>(DetektKey) {
  /**
   * Key for [DetektDependencyVersion] dependency versions.
   *
   * @since 0.1.0
   */
  public companion object DetektKey : KaseMatrix.KaseMatrixKey<DetektDependencyVersion>
}

/**
 * Trait interface for [DetektDependencyVersion]
 *
 * @since 0.3.0
 */
public interface HasDetektDependencyVersion {
  /** @since 0.3.0 */
  public val detekt: DetektDependencyVersion

  /** @since 0.3.0 */
  public val detektVersion: String

  public companion object {
    /** @since 0.3.0 */
    public operator fun invoke(
      version: DetektDependencyVersion
    ): HasDetektDependencyVersion = object : HasDetektDependencyVersion {
      override val detekt: DetektDependencyVersion = version
      override val detektVersion: String = version.value
    }
  }
}
