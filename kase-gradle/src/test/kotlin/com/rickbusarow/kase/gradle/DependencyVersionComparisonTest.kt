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

import com.rickbusarow.kase.kase
import com.rickbusarow.kase.testFactory
import io.kotest.matchers.comparables.shouldBeLessThan
import org.junit.jupiter.api.TestFactory

class DependencyVersionComparisonTest {

  @TestFactory
  fun `dependency version sorting`() = testFactory(
    // basic semver increments
    row("1.2.3", "2.2.3"),
    row("1.2.3", "1.3.0"),
    row("1.2.3", "1.2.4"),
    row("1.2.9", "1.2.10"),
    row("1.9.0", "1.10.0"),

    // prerelease hierarchy and ordering
    row("1.0.0-alpha01", "1.0.0-beta01"),
    row("1.0.0-beta01", "1.0.0-rc01"),
    row("1.0.0-rc01", "1.0.0"),
    row("2.0.0-alpha02", "2.0.0-alpha10"),

    // snapshot vs stable
    row("3.1.0-SNAPSHOT", "3.1.0"),

    // timestamp / calendar versions
    row("20240101", "20250101"),

    // decimal Gradle versions
    row("8.9", "8.10"),

    // KSP hybrid scheme
    row("1.0.15-1.0.14", "1.0.16-1.0.14")
  ) { (old, new) ->

    val older = GradleDependencyVersion(old)
    val newer = GradleDependencyVersion(new)

    older shouldBeLessThan newer
  }

  fun row(older: String, newer: String) = kase(
    displayName = "$older < $newer",
    a1 = older,
    a2 = newer
  )
}
