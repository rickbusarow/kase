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

import com.rickbusarow.kase.AnyKase
import com.rickbusarow.kase.Kase3
import com.rickbusarow.kase.gradle.DependencyVersion.Agp
import com.rickbusarow.kase.gradle.DependencyVersion.Gradle
import com.rickbusarow.kase.gradle.DependencyVersion.Kotlin
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
import com.rickbusarow.kase.kase
import dev.drewhamilton.poko.Poko

/** The versions of dependencies which are changed during parameterized tests. */
@Poko
public class MyTestVersions(
  override val a1: Gradle,
  override val a2: Agp,
  override val a3: Kotlin
) : TestVersions,
  Kase3<Gradle, Agp, Kotlin> by kase(a1, a2, a3) {

  /** not semver. ex: `8.0` or `8.1.1` */
  public override val gradleVersion: String get() = a1.value

  /** normal semver. ex `8.1.1` */
  public val agpVersion: String get() = a2.value

  /** normal semver. ex `1.8.10` */
  public val kotlinVersion: String get() = a3.value

  override fun toString(): String = displayName

  public companion object {
    /** */
    public fun from(kase: AnyKase, versionMatrix: VersionMatrix): MyTestVersions {

      val versions = kase.elements
        .mapNotNull { it.value as? DependencyVersion }
        .associateBy { it.key }

      return MyTestVersions(
        versions.version(Gradle) { versionMatrix[Gradle].first() },
        versions.version(Agp) { versionMatrix[Agp].first() },
        versions.version(Kotlin) { versionMatrix[Kotlin].first() }
      )
    }

    private inline fun <reified T : DependencyVersion> Map<VersionMatrixKey<*>, DependencyVersion>.version(
      key: VersionMatrixKey<T>,
      default: () -> T
    ): T = get(key) as? T ?: default()
  }
}
