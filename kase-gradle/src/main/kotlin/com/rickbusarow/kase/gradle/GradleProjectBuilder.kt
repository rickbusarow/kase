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

import com.rickbusarow.kase.files.DirectoryBuilder
import com.rickbusarow.kase.files.DirectoryBuilder.FileWithContent
import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.directoryBuilder
import com.rickbusarow.kase.files.internal.DirectoryBuilderInternal
import com.rickbusarow.kase.gradle.internal.DefaultGradleRootProjectBuilder
import org.intellij.lang.annotations.Language
import java.io.File

/**
 * A special [DirectoryBuilder] that models the creation of Gradle projects.
 *
 * The entrypoint for this DSL is the [rootProject] function. Once
 * inside, additional projects are created using the [project] function.
 *
 * ```
 * rootProject {
 *   project("lib") {
 *     buildFile {
 *       """
 *       plugins {
 *         id("com.acme.anvil")
 *       }
 *       """.trimIndent()
 *     }
 *   }
 * }
 * ```
 */
public interface GradleProjectBuilder :
  DirectoryBuilder,
  HasDslLanguage {

  /**
   * All child projects created by this builder. This
   * does not include the children of those projects.
   */
  public val subprojects: List<GradleProjectBuilder>

  /** Creates a `build.gradle(.kts)` file with the given [content]. */
  public fun buildFile(content: String): FileWithContent {
    return file(dslLanguage.buildFileName, content)
  }

  /** Creates a `build.gradle(.kts)` file from the given [dslStringFactory]. */
  public fun buildFile(dslStringFactory: DslStringFactory): FileWithContent {
    return buildFile(dslStringFactory.write(dslLanguage))
  }

  /** Creates a `gradle.properties` file with the given [content]. */
  public fun gradlePropertiesFile(@Language("properties") content: String): FileWithContent {
    return file("gradle.properties", content)
  }

  /** Creates a child directory with the given [relativePath] and applies the [builder] to it. */
  public fun project(relativePath: String, builder: GradleProjectBuilder.() -> Unit)

  /**
   * Creates a child directory with the given relative
   * [pathSegments] and applies the [builder] to it.
   */
  public fun project(pathSegments: Iterable<String>, builder: GradleProjectBuilder.() -> Unit) {
    project(pathSegments.joinToPathString(), builder)
  }
}

/**
 * A special [GradleProjectBuilder] that models the creation of the root project.
 *
 * The entrypoint for this DSL is the [rootProject] function. Once
 * inside, additional projects are created using the [project] function.
 */
public interface GradleRootProjectBuilder : GradleProjectBuilder {

  /** Creates a `settings.gradle(.kts)` file with the given [content]. */
  public fun settingsFile(content: String): FileWithContent {
    return file(dslLanguage.settingsFileName, content)
  }

  /** Creates a `settings.gradle(.kts)` file from the given [dslStringFactory]. */
  public fun settingsFile(dslStringFactory: DslStringFactory): FileWithContent {
    return settingsFile(dslStringFactory.write(dslLanguage))
  }
}

/**
 * Returns a GradleProjectBuilder object for the root project.
 *
 * @param builder a lambda function that configures the GradleProjectBuilder object.
 * @receiver a type that extends HasWorkingDir and HasDslLanguage interfaces.
 * @return a GradleProjectBuilder object for the root project.
 */
public fun <T> T.rootProject(
  builder: GradleRootProjectBuilder.() -> Unit
): GradleRootProjectBuilder
  where T : HasWorkingDir,
        T : HasDslLanguage = rootProject(
  path = workingDir,
  dslLanguage = dslLanguage,
  builder = builder
)

/**
 * Returns a GradleProjectBuilder object for the root project.
 *
 * @param dslLanguage the DSL language to use for the root project.
 * @param builder a lambda function that configures the GradleProjectBuilder object.
 * @return a GradleProjectBuilder object for the root project.
 */
public fun HasWorkingDir.rootProject(
  dslLanguage: DslLanguage,
  builder: GradleRootProjectBuilder.() -> Unit
): GradleRootProjectBuilder = rootProject(
  path = workingDir,
  dslLanguage = dslLanguage,
  builder = builder
)

/**
 * Returns a GradleProjectBuilder object for the root project.
 *
 * @param path the path to the root project.
 * @param builder a lambda function that configures the GradleProjectBuilder object.
 * @return a GradleProjectBuilder object for the root project.
 */
public fun HasDslLanguage.rootProject(
  path: File,
  builder: GradleRootProjectBuilder.() -> Unit
): GradleRootProjectBuilder = rootProject(
  path = path,
  dslLanguage = dslLanguage,
  builder = builder
)

/**
 * Returns a GradleProjectBuilder object for the root project.
 *
 * @param path the path to the root project.
 * @param dslLanguage the DSL language to use for the root project.
 * @param builder a lambda function that configures the GradleProjectBuilder object.
 * @return a GradleProjectBuilder object for the root project.
 */
public fun rootProject(
  path: File,
  dslLanguage: DslLanguage,
  builder: GradleRootProjectBuilder.() -> Unit
): GradleRootProjectBuilder = DefaultGradleRootProjectBuilder(
  path = path,
  dslLanguage = dslLanguage,
  directoryBuilder = path.directoryBuilder() as DirectoryBuilderInternal,
  parent = null
)
  .apply(builder)