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

package com.rickbusarow.kase.gradle.generation

import com.rickbusarow.kase.gradle.TestVersions
import com.rickbusarow.kase.gradle.generation.DependencyDeclaration.ExternalDependency
import com.rickbusarow.kase.gradle.generation.internal.DslLanguage

/** Models the contents of a Gradle build file. */
public interface BuildFileComponents {

  /**
   * Everything inside the `dependencies { }` block. This would
   * typically be the 'classpath' dependency declarations.
   *
   * ```
   * buildscript {
   *   dependencies {
   *     classpath("com.android.tools.build:gradle:$agpVersion")
   *     classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
   *   }
   * }
   * ```
   */
  public fun <T : TestVersions> buildscriptDependencies(testVersions: T): List<ExternalDependency> =
    listOf(
      ExternalDependency(
        configuration = "classpath",
        group = "com.android.tools.build",
        module = "gradle",
        version = testVersions.gradleVersion
      )
    )

  /**
   * Everything inside the `dependencies { }` block. This would
   * typically be the 'classpath' dependency declarations.
   *
   * ```
   * buildscript {
   *   dependencies {
   *     classpath("com.android.tools.build:gradle:$agpVersion")
   *     classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
   *   }
   * }
   * ```
   */
  public fun <T : TestVersions> buildscriptDependenciesBlockElements(
    dslLanguage: DslLanguage,
    testVersions: T
  ): List<String> = buildscriptDependencies(testVersions).map { it.write(dslLanguage) }

  /**
   * Typically the 'classpath' dependency declarations
   * ```
   * buildscript {
   *   dependencies {
   *     classpath("com.android.tools.build:gradle:$agpVersion")
   *     classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
   *   }
   * }
   * ```
   */
  public fun <T : TestVersions> buildscriptDependenciesBlockContent(
    dslLanguage: DslLanguage,
    testVersions: T
  ): String = buildscriptDependenciesBlockElements(dslLanguage, testVersions)
    .joinToString(separator = "\n")
}
