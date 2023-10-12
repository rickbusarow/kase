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
import com.rickbusarow.kase.HasTestVariant
import com.rickbusarow.kase.Kase3
import com.rickbusarow.kase.TestVariant
import com.rickbusarow.kase.gradle.DependencyVersion.Agp
import com.rickbusarow.kase.gradle.DependencyVersion.Gradle
import com.rickbusarow.kase.gradle.DependencyVersion.Kotlin
import com.rickbusarow.kase.internal.KaseInternal
import com.rickbusarow.kase.kase
import kotlin.reflect.KClass

public interface TestVersionssss : Kase3<Gradle, Agp, Kotlin> {
  /** not semver. ex: `8.0` or `8.1.1` */
  public val gradle: String get() = a1.value

  /** normal semver. ex `8.1.1` */
  public val agp: String get() = a2.value

  /** normal semver. ex `1.8.10` */
  public val kotlin: String get() = a3.value
}

/** The versions of dependencies which are changed during parameterized tests. */
public data class TestVersions(
  override val a1: Gradle,
  override val a2: Agp,
  override val a3: Kotlin
) : TestVersionssss,
  Kase3<Gradle, Agp, Kotlin> by kase(a1, a2, a3) {

  override fun toString(): String = displayName

  public companion object {
    public fun from(kase: AnyKase, versionsMatrix: VersionsMatrix): TestVersions {

      kase as KaseInternal

      val versions = kase.elements
        .mapNotNull { it.value }
        .associateBy { it::class }

      return TestVersions(
        versions.version(Gradle::class) { versionsMatrix[Gradle].first() },
        versions.version(Agp::class) { versionsMatrix[Agp].first() },
        versions.version(Kotlin::class) { versionsMatrix[Kotlin].first() }
      )
    }

    private inline fun <reified T : DependencyVersion> Map<KClass<out Any>, Any>.version(
      clazz: KClass<T>,
      default: () -> T
    ): T = get(clazz) as? T ?: default()
  }
}

/** Trait interface for [TestVersions]*/
public interface HasTestVersions<T : TestVersionssss> {
  /** immutable */
  public val testVersions: T
}

public interface KaseGradleProject

public data class GradleTestEnvironmentParams(
  val projectCache: MutableMap<String, KaseGradleProject>,
  override val testVersions: TestVersions,
  override val testVariant: TestVariant
) : HasTestVariant, HasTestVersions<TestVersions>
