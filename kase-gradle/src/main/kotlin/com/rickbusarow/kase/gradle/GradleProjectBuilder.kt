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

import com.rickbusarow.kase.files.DirectoryBuilder
import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.directoryBuilder
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.internal.DefaultGradleRootProjectBuilder
import org.intellij.lang.annotations.Language
import java.io.File
import kotlin.reflect.KProperty

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
 *
 * @since 0.1.0
 */
public interface GradleProjectBuilder : DirectoryBuilder, HasDslLanguage {

  /**
   * The simple name of this project, corresponding to [File.getName] or Gradle's `project.name`.
   */
  public val name: String
    get() = path.nameWithoutExtension

  /** This project's relative path to the root project. */
  public val relativePath: File

  /**
   * This project's relative path to the root project, with segments separated by colons.
   *
   * Example: `:common:my-lib:api`
   */
  public val gradlePath: String

  override val parent: GradleProjectBuilder?

  /**
   * All direct child projects created by this builder.
   * This does not include the children of those projects.
   *
   * @since 0.1.0
   */
  public val subprojects: List<GradleProjectBuilder>

  /**
   * Returns a child project by its simple name.
   *
   * Example:
   * ```
   * val lib: GradleProjectBuilder = rootProject.subprojectByName("lib")
   * ```
   *
   * @throws IllegalArgumentException if no subproject has the given name
   */
  public fun subprojectByName(name: String): GradleProjectBuilder =
    subprojects.subprojectByName(name)

  /**
   * Returns a child project by its Gradle path. This includes nested subprojects.
   *
   * Example:
   * ```
   * val sub2 = rootProject.subprojectByGradlePath(":sub1:sub2")
   * ```
   *
   * @throws IllegalArgumentException if no subproject has the given Gradle Path
   */
  public fun subprojectByGradlePath(gradlePath: String): GradleProjectBuilder =
    subprojectsRecursive.subprojectByGradlePath(gradlePath)

  /**
   * Creates a `build.gradle(.kts)` file with the given [content].
   *
   * @since 0.1.0
   */
  public fun buildFile(content: String): File {
    return file(dslLanguage.buildFileName, content)
  }

  /**
   * Creates a `build.gradle(.kts)` file from the given [dslStringFactory].
   *
   * @since 0.1.0
   */
  public fun buildFile(dslStringFactory: DslStringFactory): File {
    return buildFile(dslStringFactory.write(dslLanguage))
  }

  /**
   * Creates a `gradle.properties` file with the given [content].
   *
   * @since 0.1.0
   */
  public fun gradlePropertiesFile(@Language("properties") content: String): File {
    return file("gradle.properties", content)
  }

  /**
   * Creates a child directory with the given [relativePath] and applies the [builder] to it.
   *
   * @since 0.1.0
   */
  public fun project(relativePath: String, builder: GradleProjectBuilder.() -> Unit)

  /**
   * Creates a child directory with the given relative
   * [pathSegments] and applies the [builder] to it.
   *
   * @since 0.1.0
   */
  public fun project(pathSegments: Iterable<String>, builder: GradleProjectBuilder.() -> Unit) {
    project(pathSegments.joinToPathString(), builder)
  }
}

/**
 * Delegate for retrieving a subproject by its simple name.
 *
 * ```
 * // These two lines are equivalent:
 * val lib: GradleProjectBuilder = rootProject.subprojectByName("lib")
 * val lib: GradleProjectBuilder by rootProject.subprojects
 *
 * // Names with dashes can be accessed using backticks:
 * val `my-project`: GradleProjectBuilder by rootProject.subprojects
 * ```
 */
public operator fun <E : GradleProjectBuilder> Collection<E>.getValue(
  thisRef: Any?,
  property: KProperty<*>
): E = subprojectByName(property.name)

/**
 * Shorthand for `singleOrNull { it.name == name }`
 *
 * @see GradleProjectBuilder.subprojectByName
 * @throws IllegalArgumentException if no subproject has the given name
 */
public fun <E : GradleProjectBuilder> Collection<E>.subprojectByName(name: String): E {
  return requireNotNull(singleOrNull { it.name == name }) {
    "Subproject $name not found in: ${map { it.name }.sorted()}"
  }
}

/**
 * Shorthand for `singleOrNull { it.gradlePath == gradlePath }`
 *
 * @see GradleProjectBuilder.subprojectByGradlePath
 * @throws IllegalArgumentException if no subproject has the given Gradle Path
 */
public fun <E : GradleProjectBuilder> Collection<E>.subprojectByGradlePath(gradlePath: String): E {
  return requireNotNull(singleOrNull { it.gradlePath == gradlePath }) {
    "Subproject $gradlePath not found in: ${map { it.gradlePath }.sorted()}"
  }
}

/**
 * Fetches a subproject by its Gradle path.
 *
 * Example:
 * ```
 * val sub2 = rootProject.subprojects[":sub1:sub2"]
 * ```
 *
 * @see GradleProjectBuilder.subprojectByGradlePath
 * @throws IllegalArgumentException if no subproject has the given Gradle Path
 */
public operator fun <E : GradleProjectBuilder> Collection<E>.get(gradlePath: String): E {
  require(gradlePath.startsWith(":")) { "gradle path must start with a colon" }
  return subprojectByGradlePath(gradlePath)
}

/**
 * All subprojects and their subprojects, recursively.
 *
 * @see GradleProjectBuilder.subprojects for a list of direct children.
 */
public val GradleProjectBuilder.subprojectsRecursive: List<GradleProjectBuilder>
  get() = subprojects + subprojects.flatMap { it.subprojectsRecursive }

/**
 * A special [GradleProjectBuilder] that models the creation of the root project.
 *
 * The entrypoint for this DSL is the [rootProject] function. Once
 * inside, additional projects are created using the [project] function.
 *
 * @since 0.1.0
 */
public interface GradleRootProjectBuilder : GradleProjectBuilder {

  /**
   * Creates a `settings.gradle(.kts)` file with the given [content].
   *
   * @since 0.1.0
   */
  public fun settingsFile(content: String): File {
    return file(dslLanguage.settingsFileName, content)
  }

  /**
   * Creates a `settings.gradle(.kts)` file from the given [dslStringFactory].
   *
   * @since 0.1.0
   */
  public fun settingsFile(dslStringFactory: DslStringFactory): File {
    return settingsFile(dslStringFactory.write(dslLanguage))
  }
}

/**
 * Returns a GradleProjectBuilder object for the root project.
 *
 * @param builder a lambda function that configures the GradleProjectBuilder object.
 * @receiver a type that extends HasWorkingDir and HasDslLanguage interfaces.
 * @return a GradleProjectBuilder object for the root project.
 * @since 0.1.0
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
 * @since 0.1.0
 */
public fun HasWorkingDir.rootProject(
  dslLanguage: DslLanguage = KotlinDsl(),
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
 * @since 0.1.0
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
 * @since 0.1.0
 */
public fun rootProject(
  path: File,
  dslLanguage: DslLanguage,
  builder: GradleRootProjectBuilder.() -> Unit
): GradleRootProjectBuilder = DefaultGradleRootProjectBuilder(
  path = path,
  dslLanguage = dslLanguage,
  directoryBuilder = path.directoryBuilder(),
  parent = null
)
  .apply(builder)
