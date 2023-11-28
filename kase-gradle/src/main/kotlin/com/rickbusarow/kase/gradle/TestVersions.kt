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
import com.rickbusarow.kase.gradle.DependencyVersion.Agp
import com.rickbusarow.kase.gradle.DependencyVersion.Gradle
import com.rickbusarow.kase.gradle.DependencyVersion.Kotlin
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
import com.rickbusarow.kase.kase
import dev.drewhamilton.poko.Poko
import org.gradle.util.GradleVersion

/** Interface for test versions. */
public interface TestVersions : Kase {

  /** The current Gradle version. */
  public val gradleVersion: String

  public companion object {

    /** An empty [TestVersions] object containing only the current Gradle TestKit Gradle version. */
    public val EMPTY: TestVersions = object :
      TestVersions,
      Kase by Kase.EMPTY {
      override val gradleVersion: String by lazy {
        GradleVersion.current().version
      }
    }
  }
}

/** Trait interface for [TestVersions] */
public interface HasTestVersions<T : TestVersions> {
  /** immutable */
  public val testVersions: T
}

/** Holds a [Gradle] version only */
@Poko
public class GradleTestVersions(
  override val a1: Gradle
) : TestVersions,
  Kase1<Gradle> by kase(a1) {

  /** not semver. ex: `8.0` or `8.1.1` */
  public override val gradleVersion: String get() = a1.value

  override fun toString(): String = displayName

  public companion object {
    /** */
    public fun from(versionMatrix: VersionMatrix): List<GradleTestVersions> {
      return versionMatrix.kases(Gradle).map { GradleTestVersions(it.a1) }
    }
  }
}

/** Holds [Gradle] and [Kotlin] versions */
@Poko
public class GradleKotlinTestVersions(
  override val a1: Gradle,
  override val a2: Kotlin
) : TestVersions,
  Kase2<Gradle, Kotlin> by kase(a1, a2) {

  /** not semver. ex: `8.0` or `8.1.1` */
  public override val gradleVersion: String get() = a1.value

  /** normal semver. ex `1.8.10` */
  public val kotlinVersion: String get() = a2.value

  override fun toString(): String = displayName

  public companion object {
    /** */
    public fun from(versionMatrix: VersionMatrix): List<GradleKotlinTestVersions> {

      return versionMatrix.kases(Gradle, Kotlin).map { GradleKotlinTestVersions(it.a1, it.a2) }
    }
  }
}

/** Holds [Gradle], [Kotlin], and [Agp] versions */
@Poko
public class GradleKotlinAgpTestVersions(
  override val a1: Gradle,
  override val a2: Kotlin,
  override val a3: Agp
) : TestVersions,
  Kase3<Gradle, Kotlin, Agp> by kase(a1, a2, a3) {

  /** not semver. ex: `8.0` or `8.1.1` */
  public override val gradleVersion: String get() = a1.value

  /** normal semver. ex `1.8.10` */
  public val kotlinVersion: String get() = a2.value

  /** normal semver. ex `8.1.1` */
  public val agpVersion: String get() = a3.value

  override fun toString(): String = displayName

  public companion object {
    /** */
    public fun from(versionMatrix: VersionMatrix): List<GradleKotlinAgpTestVersions> {

      return versionMatrix.kases(Gradle, Kotlin, Agp)
        .map { GradleKotlinAgpTestVersions(it.a1, it.a2, it.a3) }
    }

    private inline fun <reified T : DependencyVersion<*, *>> Map<VersionMatrixKey<*>, DependencyVersion<*, *>>.version(
      key: VersionMatrixKey<T>,
      default: () -> T
    ): T = get(key) as? T ?: default()
  }
}
