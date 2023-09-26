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

package com.rickbusarow.kase

import com.rickbusarow.kase.stdlib.cartesianProduct

class GradleVersion private constructor(val value: String) : Comparable<GradleVersion> {
  override fun compareTo(other: GradleVersion): Int = value.compareTo(other.value)

  companion object {
    fun current() = version("")
    fun version(str: String): GradleVersion = GradleVersion(str)
  }
}

class AgpVersion private constructor(val value: String) : Comparable<AgpVersion> {
  override fun compareTo(other: AgpVersion): Int = value.compareTo(other.value)

  companion object {
    fun version(value: String): AgpVersion = AgpVersion(value)
  }
}

interface VersionsMatrix {

  fun gradleVersions(): List<GradleVersion>
  fun agpVersions(): List<AgpVersion>
  fun gradlePlus(vararg others: Iterable<*>): List<List<*>>
  fun gradleAgpMatrix(gradleVersions: List<GradleVersion> = gradleVersions()): List<List<*>>
  fun gradleAgpMatrixPlus(vararg others: List<*>): List<List<*>>
}

internal sealed interface VersionType {
  val value: String

  @JvmInline
  value class Gradle(override val value: String) : VersionType

  @JvmInline
  value class Agp(override val value: String) : VersionType

  @JvmInline
  value class Kotlin(override val value: String) : VersionType

  @JvmInline
  value class Ksp(override val value: String) : VersionType

  @JvmInline
  value class Anvil(override val value: String) : VersionType

  @JvmInline
  value class Keeper(override val value: String) : VersionType

  @JvmInline
  value class Detekt(override val value: String) : VersionType
}

interface VersionsRow<T> {
  val gradle: T
  val agp: T
  val kotlin: T
  val ksp: T
  val anvil: T

  data class ExclusionRule(
    override val gradle: (String) -> Boolean? = { null },
    override val agp: (String) -> Boolean? = { null },
    override val kotlin: (String) -> Boolean? = { null },
    override val ksp: (String) -> Boolean? = { null },
    override val anvil: (String) -> Boolean? = { null }
  ) : VersionsRow<(String) -> Boolean?> {

    // fun excludes(versions: VersionSet): Boolean {
    // }
  }
}

data class VersionSet(
  override val gradle: String,
  override val agp: String,
  override val kotlin: String,
  override val ksp: String,
  override val anvil: String
) : VersionsRow<String>

object VersionsAndThings : VersionsMatrix {

  // Injected via plugins/build.gradle
  private val AGP_7_3 = System.getProperty("agp_7_3")
  private val AGP_7_4 = System.getProperty("agp_7_4")
  private val AGP_8_0 = System.getProperty("agp_8_0")
  private val AGP_8_1 = System.getProperty("agp_8_1")
  private val AGP_8_2 = System.getProperty("agp_8_2")
  private val QUICK_TEST = System.getProperty("quickTest").toBoolean()

  // The minimum supported Gradle version is whatever we're building the project with
  val MIN_GRADLE_VERSION = GradleVersion.current()
  val MAX_GRADLE_VERSION = GradleVersion.version("8.2.1")

  // Sometimes there is duplication here and that is ok and intentional
  val MIN_AGP_VERSION = AgpVersion.version(AGP_7_3)
  val NEXT_AGP_VERSION = AgpVersion.version(AGP_8_1)
  val MAX_AGP_VERSION = AgpVersion.version(AGP_8_2)

  // We test against the current Gradle version and the upcoming Gradle version
  private val TESTED_GRADLE_VERSIONS = listOf(
    MIN_GRADLE_VERSION,
    MAX_GRADLE_VERSION
  ).distinct()

  // We test against the current AGP version, and all versions upcoming (ideally just two)
  private val TESTED_AGP_VERSIONS = listOf(
    MIN_AGP_VERSION,
    NEXT_AGP_VERSION
    // Requires Build Tools 34.0.0 which is not available in kochiku
    // MAX_AGP_VERSION
  ).distinct()

  override fun gradleVersions() = TESTED_GRADLE_VERSIONS
  override fun agpVersions() = TESTED_AGP_VERSIONS

  override fun gradlePlus(vararg others: Iterable<*>): List<List<*>> {
    return listOf(gradleVersions(), *others).cartesianProduct()
  }

  override fun gradleAgpMatrix(gradleVersions: List<GradleVersion>): List<List<*>> {
    val matrix = gradleVersions.flatMap { gradle ->
      agpVersions()
        .filter { agp -> isCompatible(gradle, agp) }
        // Transform from AgpVersion to its string representation
        .map { listOf(gradle, it.value) }
    }

    return if (QUICK_TEST) {
      matrix.take(1)
    } else {
      matrix
    }
  }

  override fun gradleAgpMatrixPlus(vararg others: List<*>): List<List<*>> {

    val matrix = listOf(
      gradleVersions(),
      agpVersions(),
      *others
    )
      .cartesianProduct()
      .mapNotNull { combination ->
        val gradle = combination[0] as GradleVersion
        val agp = combination[1] as AgpVersion

        if (isCompatible(gradle, agp)) {
          listOf(gradle, agp.value) + combination.drop(2)
        } else {
          null
        }
      }

    return if (QUICK_TEST) {
      matrix.take(1)
    } else {
      matrix
    }
  }

  private fun isCompatible(gradleVersion: GradleVersion, agpVersion: AgpVersion): Boolean {
    return when {
      gradleVersion >= GradleVersion.version("8.2") &&
        agpVersion < AgpVersion.version("8.0.99999") -> {
        // Multiple configuration cache bugs with previous versions of AGP
        // https://issuetracker.google.com/issues/278767328
        // https://issuetracker.google.com/issues/263576736
        false
      }

      agpVersion >= AgpVersion.version("8.2") -> gradleVersion >= GradleVersion.version("8.1")
      agpVersion >= AgpVersion.version("8.0") -> gradleVersion >= GradleVersion.version("8.0")
      agpVersion >= AgpVersion.version("7.4") -> gradleVersion >= GradleVersion.version("7.5")
      agpVersion >= AgpVersion.version("7.3") -> gradleVersion >= GradleVersion.version("7.4")
      else -> throw IllegalArgumentException(
        "AGP $agpVersion is incompatible with Gradle $gradleVersion"
      )
    }
  }
}
