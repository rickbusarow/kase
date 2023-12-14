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

import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.Kase3
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
    public val EMPTY: TestVersions = object : TestVersions by GradleTestVersions(
      GradleDependencyVersion(GradleVersion.current().version)
    ) {}
  }
}

/**
 * Trait interface for [TestVersions]
 *
 * @since 0.1.0
 */
public interface HasTestVersions<T : TestVersions> {
  /** @since 0.1.0 */
  public val testVersions: T
}

/**
 * Extracts a list of [TestVersions] from a [VersionMatrix]
 *
 * @since 0.1.0
 */
public fun interface TestVersionsFactory<T : TestVersions> {
  /**
   * Extracts a list of [TestVersions] from a [VersionMatrix]
   *
   * @since 0.1.0
   */
  public fun extract(matrix: VersionMatrix): List<T>
}

/**
 * Holds a [GradleDependencyVersion] version only
 *
 * @since 0.1.0
 */
@Poko
public class GradleTestVersions(
  override val a1: GradleDependencyVersion
) : TestVersions,
  HasGradleDependencyVersion by HasGradleDependencyVersion(a1),
  Kase1<GradleDependencyVersion> by kase(a1) {

  override val displayName: String
    get() = "gradle: $a1"

  override fun toString(): String = displayName

  public companion object : TestVersionsFactory<GradleTestVersions> {

    override fun extract(matrix: VersionMatrix): List<GradleTestVersions> {
      return matrix.kases(a1Key = GradleDependencyVersion)
        .map { GradleTestVersions(a1 = it.a1) }
    }
  }
}

/**
 * Holds [GradleDependencyVersion] and [KotlinDependencyVersion] versions
 *
 * @since 0.1.0
 */
@Poko
public class GradleKotlinTestVersions(
  override val a1: GradleDependencyVersion,
  override val a2: KotlinDependencyVersion
) : TestVersions,
  HasGradleDependencyVersion by HasGradleDependencyVersion(a1),
  HasKotlinDependencyVersion by HasKotlinDependencyVersion(a2),
  Kase2<GradleDependencyVersion, KotlinDependencyVersion> by kase(a1, a2) {

  override val displayName: String
    get() = "gradle: $a1 | kotlin: $a2"

  override fun toString(): String = displayName

  public companion object : TestVersionsFactory<GradleKotlinTestVersions> {

    override fun extract(matrix: VersionMatrix): List<GradleKotlinTestVersions> {
      return matrix.kases(a1Key = GradleDependencyVersion, a2Key = KotlinDependencyVersion)
        .map { GradleKotlinTestVersions(a1 = it.a1, a2 = it.a2) }
    }
  }
}

/**
 * Holds [GradleDependencyVersion], [KotlinDependencyVersion], and [AgpDependencyVersion] versions
 *
 * @since 0.1.0
 */
@Poko
public class GradleKotlinAgpTestVersions(
  override val a1: GradleDependencyVersion,
  override val a2: KotlinDependencyVersion,
  override val a3: AgpDependencyVersion
) : TestVersions,
  HasGradleDependencyVersion by HasGradleDependencyVersion(a1),
  HasKotlinDependencyVersion by HasKotlinDependencyVersion(a2),
  HasAgpDependencyVersion by HasAgpDependencyVersion(a3),
  Kase3<GradleDependencyVersion, KotlinDependencyVersion, AgpDependencyVersion>
  by kase(a1, a2, a3) {

  override val displayName: String
    get() = "gradle: $a1 | kotlin: $a2 | agp: $a3"

  override fun toString(): String = displayName

  public companion object : TestVersionsFactory<GradleKotlinAgpTestVersions> {
    override fun extract(matrix: VersionMatrix): List<GradleKotlinAgpTestVersions> {
      return matrix.kases(
        a1Key = GradleDependencyVersion,
        a2Key = KotlinDependencyVersion,
        a3Key = AgpDependencyVersion
      )
        .map { GradleKotlinAgpTestVersions(a1 = it.a1, a2 = it.a2, a3 = it.a3) }
    }
  }
}
