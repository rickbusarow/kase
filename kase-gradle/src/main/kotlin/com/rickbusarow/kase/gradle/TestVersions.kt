/*
 * Copyright (C) 2024 Rick Busarow
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

import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.Kase3
import com.rickbusarow.kase.KaseMatrix
import com.rickbusarow.kase.get
import com.rickbusarow.kase.kase
import dev.drewhamilton.poko.Poko
import org.gradle.util.GradleVersion

/**
 * Interface for test versions.
 *
 * @since 0.1.0
 */
public interface TestVersions : Kase, HasGradleDependencyVersion {

  /**
   * The current Gradle version.
   *
   * @since 0.1.0
   */
  public override val gradleVersion: String

  public companion object {

    /**
     * An empty [TestVersions] object containing only the current Gradle TestKit Gradle version.
     *
     * @since 0.1.0
     */
    public val EMPTY: TestVersions =
      object : TestVersions by DefaultGradleTestVersions(
        GradleDependencyVersion(GradleVersion.current().version)
      ) {}
  }
}

/**
 * Trait interface for [TestVersions]
 *
 * @since 0.1.0
 */
public interface HasTestVersions<out T : TestVersions> {
  /** @since 0.1.0 */
  public val testVersions: T
}

/**
 * Extracts a list of [TestVersions] from a [KaseMatrix]
 *
 * @since 0.1.0
 */
public fun interface TestVersionsFactory<out T : TestVersions> {
  /**
   * Extracts a list of [TestVersions] from a [KaseMatrix]
   *
   * @since 0.1.0
   */
  public fun extract(matrix: KaseMatrix): List<T>
}

/**
 * Extracts a list of [TestVersions] from this matrix
 *
 * @since 0.1.0
 */
public fun <T : TestVersions> KaseMatrix.versions(factory: TestVersionsFactory<T>): List<T> {
  return factory.extract(this)
}

/**
 * Holds a [GradleDependencyVersion] version only
 *
 * @since 0.1.0
 */
public interface GradleTestVersions :
  TestVersions,
  HasGradleDependencyVersion,
  Kase1<GradleDependencyVersion> {
  public companion object : TestVersionsFactory<GradleTestVersions> {

    /**
     * Creates a new [DefaultGradleTestVersions] instance.
     *
     * @since 0.5.0
     */
    public operator fun invoke(gradleVersion: GradleDependencyVersion): GradleTestVersions {
      return DefaultGradleTestVersions(gradleVersion)
    }

    override fun extract(matrix: KaseMatrix): List<GradleTestVersions> {
      return matrix.get(a1Key = GradleDependencyVersion) { gradle ->
        DefaultGradleTestVersions(a1 = gradle)
      }
    }
  }
}

/**
 * Holds a [GradleDependencyVersion] version only
 *
 * @since 0.1.0
 */
@Poko
public class DefaultGradleTestVersions(
  override val a1: GradleDependencyVersion
) : GradleTestVersions,
  HasGradleDependencyVersion by HasGradleDependencyVersion(a1),
  Kase1<GradleDependencyVersion> by kase(a1) {

  override val displayName: String
    get() = "gradle: $a1"

  override fun toString(): String = displayName
}

/**
 * Holds [GradleDependencyVersion] and [KotlinDependencyVersion] versions
 *
 * @since 0.1.0
 */
public interface GradleKotlinTestVersions :
  TestVersions,
  HasGradleDependencyVersion,
  HasKotlinDependencyVersion,
  Kase2<GradleDependencyVersion, KotlinDependencyVersion>,
  GradleTestVersions {
  public companion object : TestVersionsFactory<GradleKotlinTestVersions> {

    /**
     * Creates a new [GradleKotlinTestVersions] instance.
     *
     * @since 0.5.0
     */
    public operator fun invoke(
      gradleVersion: GradleDependencyVersion,
      kotlinVersion: KotlinDependencyVersion
    ): GradleKotlinTestVersions {
      return DefaultGradleKotlinTestVersions(a1 = gradleVersion, a2 = kotlinVersion)
    }

    override fun extract(matrix: KaseMatrix): List<GradleKotlinTestVersions> = matrix.get(
      a1Key = GradleDependencyVersion,
      a2Key = KotlinDependencyVersion,
      instanceFactory = ::DefaultGradleKotlinTestVersions
    )
  }
}

/**
 * Holds [GradleDependencyVersion] and [KotlinDependencyVersion] versions
 *
 * @since 0.1.0
 */
@Poko
public class DefaultGradleKotlinTestVersions(
  override val a1: GradleDependencyVersion,
  override val a2: KotlinDependencyVersion
) : GradleKotlinTestVersions,
  HasGradleDependencyVersion by HasGradleDependencyVersion(a1),
  HasKotlinDependencyVersion by HasKotlinDependencyVersion(a2),
  Kase2<GradleDependencyVersion, KotlinDependencyVersion> by kase(a1, a2) {

  override val displayName: String
    get() = "gradle: $a1 | kotlin: $a2"

  override fun toString(): String = displayName
}

/**
 * Holds [GradleDependencyVersion] and [AgpDependencyVersion] versions
 *
 * @since 0.1.0
 */
public interface GradleAgpTestVersions :
  TestVersions,
  HasGradleDependencyVersion,
  HasAgpDependencyVersion,
  Kase2<GradleDependencyVersion, AgpDependencyVersion>,
  GradleTestVersions {
  public companion object : TestVersionsFactory<GradleAgpTestVersions> {

    /**
     * Creates a new [DefaultGradleAgpTestVersions] instance.
     *
     * @since 0.5.0
     */
    public operator fun invoke(
      gradleVersion: GradleDependencyVersion,
      agpVersion: AgpDependencyVersion
    ): GradleAgpTestVersions = DefaultGradleAgpTestVersions(a1 = gradleVersion, a2 = agpVersion)

    override fun extract(matrix: KaseMatrix): List<GradleAgpTestVersions> = matrix.get(
      a1Key = GradleDependencyVersion,
      a2Key = AgpDependencyVersion,
      instanceFactory = ::DefaultGradleAgpTestVersions
    )
  }
}

/**
 * Holds [GradleDependencyVersion] and [AgpDependencyVersion] versions
 *
 * @since 0.1.0
 */
@Poko
public class DefaultGradleAgpTestVersions(
  override val a1: GradleDependencyVersion,
  override val a2: AgpDependencyVersion
) : GradleAgpTestVersions,
  HasGradleDependencyVersion by HasGradleDependencyVersion(a1),
  HasAgpDependencyVersion by HasAgpDependencyVersion(a2),
  Kase2<GradleDependencyVersion, AgpDependencyVersion> by kase(a1, a2) {

  override val displayName: String
    get() = "gradle: $a1 | agp: $a2"

  override fun toString(): String = displayName
}

/**
 * Holds [GradleDependencyVersion], [KotlinDependencyVersion], and [AgpDependencyVersion] versions
 *
 * @since 0.1.0
 */
public interface GradleKotlinAgpTestVersions :
  TestVersions,
  HasGradleDependencyVersion,
  HasKotlinDependencyVersion,
  HasAgpDependencyVersion,
  Kase3<GradleDependencyVersion, KotlinDependencyVersion, AgpDependencyVersion>,
  GradleTestVersions,
  GradleKotlinTestVersions {

  public companion object : TestVersionsFactory<GradleKotlinAgpTestVersions> {
    override fun extract(matrix: KaseMatrix): List<GradleKotlinAgpTestVersions> = matrix.get(
      a1Key = GradleDependencyVersion,
      a2Key = KotlinDependencyVersion,
      a3Key = AgpDependencyVersion,
      instanceFactory = ::DefaultGradleKotlinAgpTestVersions
    )
  }
}

/**
 * Holds [GradleDependencyVersion], [KotlinDependencyVersion], and [AgpDependencyVersion] versions
 *
 * @since 0.1.0
 */
@Poko
public class DefaultGradleKotlinAgpTestVersions(
  override val a1: GradleDependencyVersion,
  override val a2: KotlinDependencyVersion,
  override val a3: AgpDependencyVersion
) : GradleKotlinAgpTestVersions,
  HasGradleDependencyVersion by HasGradleDependencyVersion(a1),
  HasKotlinDependencyVersion by HasKotlinDependencyVersion(a2),
  HasAgpDependencyVersion by HasAgpDependencyVersion(a3),
  Kase3<GradleDependencyVersion, KotlinDependencyVersion, AgpDependencyVersion>
  by kase(a1, a2, a3) {

  override val displayName: String
    get() = "gradle: $a1 | kotlin: $a2 | agp: $a3"

  override fun toString(): String = displayName
}
