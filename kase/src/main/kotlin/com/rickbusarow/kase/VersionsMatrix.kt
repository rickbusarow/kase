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

import com.rickbusarow.kase.VersionSet.VersionSetLabels
import com.rickbusarow.kase.VersionType.Agp
import com.rickbusarow.kase.VersionType.Gradle
import com.rickbusarow.kase.VersionType.Kotlin

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

data class VersionSet(
  val gradle: String,
  val agp: String,
  val kotlin: String,
  val ksp: String,
  val anvil: String
) : Kase<VersionSetLabels> {
  override val args: List<String>
    get() = listOf(gradle, agp, kotlin, ksp, anvil)

  override fun names(labels: VersionSetLabels): List<String> {
    return listOf(
      "gradle: $gradle",
      "agp: $agp",
      "kotlin: $kotlin",
      "ksp: $ksp",
      "anvil: $anvil"
    )
  }

  object VersionSetLabels : KaseLabels {
    override val delimiter: String = ": "
    override val separator: String = " | "
  }
}

interface VersionType : Comparable<CharSequence>, CharSequence {
  val value: String
  val label: String
    get() = this::class.java.simpleName

  override fun compareTo(other: CharSequence): Int = value.compareTo(other.toString())

  @JvmInline
  value class Gradle(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }

  @JvmInline
  value class Agp(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }

  @JvmInline
  value class Kotlin(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }

  @JvmInline
  value class Ksp(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }

  @JvmInline
  value class Anvil(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }

  @JvmInline
  value class Keeper(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }

  @JvmInline
  value class Dagger(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }

  @JvmInline
  value class Detekt(override val value: String) : VersionType, CharSequence by value {
    override fun toString(): String = "$label: $value"
  }
}

interface VersionsMatrix {

  fun gradleVersions(): List<Gradle>
  fun agpVersions(): List<Agp>
  fun kotlinVersions(): List<Kotlin>

  fun <T : VersionType> gradlePlus(others: Iterable<T>): List<Kase2<Gradle, T>>
  fun gradleAgpMatrix(gradleVersions: Iterable<Gradle> = gradleVersions()): List<Kase2<Gradle, Agp>>
  fun <T : VersionType> gradleAgpMatrixPlus(others: Iterable<T>): List<Kase3<Gradle, Agp, T>>
}

object VersionMatrixImpl : VersionsMatrix {

  private val GRADLE_VERSIONS = listOf(
    "8.2.1",
    "8.3",
    "8.4-rc-2"
  ).map(VersionType::Gradle)

  private val AGP_VERSIONS = listOf(
    "7.3.1",
    "7.4.1",
    "8.0.2",
    "8.1.1"
  ).map(VersionType::Agp)
  private val KOTLIN_VERSIONS = listOf(
    "1.8.10",
    "1.8.22",
    "1.9.0",
    "1.9.10"
  ).map(VersionType::Kotlin)

  override fun gradleVersions() = GRADLE_VERSIONS
  override fun agpVersions() = AGP_VERSIONS
  override fun kotlinVersions() = KOTLIN_VERSIONS

  override fun <T : VersionType> gradlePlus(others: Iterable<T>): List<Kase2<Gradle, T>> {
    return kases(gradleVersions(), others)
  }

  override fun gradleAgpMatrix(gradleVersions: Iterable<Gradle>): List<Kase2<Gradle, Agp>> {

    return kases(gradleVersions, agpVersions())
      .filter { (gradle, agp) -> isCompatible(gradle, agp) }
  }

  override fun <T : VersionType> gradleAgpMatrixPlus(
    others: Iterable<T>
  ): List<Kase3<Gradle, Agp, T>> {

    val matrix = kases(
      gradleVersions(),
      agpVersions(),
      others
    )
      .filter { (gradle, agp, _) ->
        isCompatible(gradleVersion = gradle, agpVersion = agp)
      }
    return matrix
  }

  fun versions() = kases(
    GRADLE_VERSIONS,
    AGP_VERSIONS,
    KOTLIN_VERSIONS
  ).asTests { }

  private fun isCompatible(gradleVersion: Gradle, agpVersion: Agp): Boolean {
    return when {
      gradleVersion >= "8.2" &&
        agpVersion < "8.0.99999" -> {
        // Multiple configuration cache bugs with previous versions of AGP
        // https://issuetracker.google.com/issues/278767328
        // https://issuetracker.google.com/issues/263576736
        false
      }

      agpVersion >= "8.2" -> gradleVersion >= "8.1"
      agpVersion >= "8.0" -> gradleVersion >= "8.0"
      agpVersion >= "7.4" -> gradleVersion >= "7.5"
      agpVersion >= "7.3" -> gradleVersion >= "7.4"
      else -> throw IllegalArgumentException(
        "AGP $agpVersion is incompatible with Gradle $gradleVersion"
      )
    }
  }
}
