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

import com.rickbusarow.kase.gradle.generation.FunctionCall.LabelSupport.GROOVY
import com.rickbusarow.kase.gradle.generation.FunctionCall.LabelSupport.NONE

/** A `repositories { }` container */
public class RepositoryHandlerBuilder : AbstractDslElementContainer<RepositoryHandlerBuilder>() {

  /**
   * Adds a new repository to the repository handler.
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   */
  public fun maven(
    url: String,
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "maven",
    labelSupport = GROOVY,
    ValueParameter("url", url),
    LambdaParameter(label = "action", builder = action)
  )

  /**
   * Adds a new repository to the repository handler.
   *
   * @param url the url of the repository, such as `https://plugins.gradle.org/m2/`
   * @param action the action to perform on the repository
   */
  public fun maven(
    url: Quoted,
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "maven",
    labelSupport = GROOVY,
    ValueParameter("url", url),
    LambdaParameter(label = "action", builder = action)
  )

  /** Adds an invocation of `gradlePluginPortal()` to the repository handler */
  public fun gradlePluginPortal(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )

  /** Adds an invocation of `mavenCentral()` to the repository handler */
  public fun mavenCentral(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "mavenCentral",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )

  /** Adds an invocation of `mavenLocal()` to the repository handler */
  public fun mavenLocal(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "mavenLocal",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )

  /** Adds an invocation of `google()` to the repository handler */
  public fun google(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "google",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )
}

/**
 * The common configuration lambda in a repository declaration, like:
 *
 * ```
 * repositories {
 *   mavenLocal {
 *     // this lambda
 *   }
 * }
 */
public abstract class ArtifactRepositoryBuilder<T : RepositoryContentBuilder<T>> :
  AbstractDslElementContainer<ArtifactRepositoryBuilder<T>>() {

  /** Adds a new `content { }` block to the repository configuration. */
  public abstract fun content(block: T.() -> Unit): ArtifactRepositoryBuilder<T>
}

/**
 * The maven-specific configuration lambda in a repository declaration, like:
 *
 * ```
 * repositories {
 *   mavenLocal {
 *     // this lambda
 *   }
 * }
 */
public class MavenArtifactRepositoryBuilder : ArtifactRepositoryBuilder<MavenRepositoryContentBuilder>() {

  /** Adds a new `mavenContent { }` block to the repository configuration. */
  public fun mavenContent(
    block: MavenRepositoryContentBuilder.() -> Unit
  ): ArtifactRepositoryBuilder<MavenRepositoryContentBuilder> = functionCall(
    name = "mavenContent",
    labelSupport = GROOVY,
    LambdaParameter("block", block)
  )

  override fun content(
    block: MavenRepositoryContentBuilder.() -> Unit
  ): ArtifactRepositoryBuilder<MavenRepositoryContentBuilder> = functionCall(
    name = "content",
    labelSupport = GROOVY,
    LambdaParameter("block", block)
  )
}

/** The configuration options for a maven repository's content. */
public class MavenRepositoryContentBuilder : RepositoryContentBuilder<MavenRepositoryContentBuilder>() {

  /**
   * Adds the `releasesOnly()` invocation to a maven repository configuration.
   *
   * ```
   * repositories {
   *   mavenLocal {
   *     releasesOnly()
   *   }
   * }
   * ```
   */
  public fun releasesOnly(): MavenRepositoryContentBuilder = apply {
    addElement(FunctionCall(name = "releasesOnly", labelSupport = NONE))
  }

  /**
   * Adds the `snapshotsOnly()` invocation to a maven repository configuration.
   *
   * ```
   * repositories {
   *   mavenLocal {
   *     mavenContent {
   *       snapshotsOnly()
   *     }
   *   }
   * }
   * ```
   */
  public fun snapshotsOnly(): MavenRepositoryContentBuilder = apply {
    addElement(FunctionCall(name = "snapshotsOnly", labelSupport = NONE))
  }
}

/** Adds the common configurations to a repository's content, like: */
public sealed class RepositoryContentBuilder<SELF : RepositoryContentBuilder<SELF>> :
  AbstractDslElementContainer<SELF>() {

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       includeGroup("com.acme")
   *     }
   *   }
   * }
   * ```
   */
  public fun includeGroup(group: String): SELF = functionCall(
    name = "includeGroup",
    labelSupport = GROOVY,
    ValueParameter("group", group)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       includeGroupByRegex("com.acme.*")
   *     }
   *   }
   * }
   * ```
   */
  public fun includeGroupByRegex(groupRegex: String): SELF = functionCall(
    name = "includeGroupByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       includeGroupAndSubgroups("com.acme")
   *     }
   *   }
   * }
   * ```
   */
  public fun includeGroupAndSubgroups(groupPrefix: String): SELF = functionCall(
    name = "includeGroupAndSubgroups",
    labelSupport = GROOVY,
    ValueParameter("groupPrefix", groupPrefix)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       includeModule("com.acme", "my-module")
   *     }
   *   }
   * }
   * ```
   */
  public fun includeModule(group: String, moduleName: String): SELF = functionCall(
    name = "includeModule",
    labelSupport = GROOVY,
    ValueParameter("group", group),
    ValueParameter("moduleName", moduleName)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       includeModuleByRegex("com.acme.*", "my-module.*")
   *     }
   *   }
   * }
   * ```
   */
  public fun includeModuleByRegex(groupRegex: String, moduleNameRegex: String): SELF = functionCall(
    name = "includeModuleByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       includeVersion("com.acme", "my-module", "1.0.0")
   *     }
   *   }
   * }
   * ```
   */
  public fun includeVersion(
    group: String,
    moduleName: String,
    version: String
  ): SELF =
    functionCall(
      name = "includeVersion",
      labelSupport = GROOVY,
      ValueParameter("group", group),
      ValueParameter("moduleName", moduleName),
      ValueParameter("version", version)
    )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       includeVersionByRegex("com.acme.*", "my-module.*", "\d.0.0")
   *     }
   *   }
   * }
   * ```
   */
  public fun includeVersionByRegex(
    groupRegex: String,
    moduleNameRegex: String,
    versionRegex: String
  ): SELF = functionCall(
    name = "includeVersionByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex),
    ValueParameter("versionRegex", versionRegex)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       excludeGroup("com.acme")
   *     }
   *   }
   * }
   * ```
   */
  public fun excludeGroup(group: String): SELF = functionCall(
    name = "excludeGroup",
    labelSupport = GROOVY,
    ValueParameter("group", group)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       excludeGroupByRegex("com.acme.*")
   *     }
   *   }
   * }
   * ```
   */
  public fun excludeGroupByRegex(groupRegex: String): SELF = functionCall(
    name = "excludeGroupByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       excludeGroupAndSubgroups("com.acme")
   *     }
   *   }
   * }
   * ```
   */
  public fun excludeGroupAndSubgroups(groupPrefix: String): SELF = functionCall(
    name = "excludeGroupAndSubgroups",
    labelSupport = GROOVY,
    ValueParameter("groupPrefix", groupPrefix)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       excludeModule("com.acme", "my-module")
   *     }
   *   }
   * }
   * ```
   */
  public fun excludeModule(group: String, moduleName: String): SELF = functionCall(
    name = "excludeModule",
    labelSupport = GROOVY,
    ValueParameter("group", group),
    ValueParameter("moduleName", moduleName)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       excludeModuleByRegex("com.acme.*", "my-module.*")
   *     }
   *   }
   * }
   * ```
   */
  public fun excludeModuleByRegex(groupRegex: String, moduleNameRegex: String): SELF = functionCall(
    name = "excludeModuleByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       excludeVersion("com.acme", "my-module", "1.0.0")
   *     }
   *   }
   * }
   * ```
   */
  public fun excludeVersion(
    group: String,
    moduleName: String,
    version: String
  ): SELF = functionCall(
    name = "excludeVersion",
    labelSupport = GROOVY,
    ValueParameter("group", group),
    ValueParameter("moduleName", moduleName),
    ValueParameter("version", version)
  )

  /**
   * ```
   * repositories {
   *   mavenLocal {
   *     content {
   *       excludeVersionByRegex("com.acme.*", "my-module.*", "\d.0.0")
   *     }
   *   }
   * }
   * ```
   */
  public fun excludeVersionByRegex(
    groupRegex: String,
    moduleNameRegex: String,
    versionRegex: String
  ): SELF = functionCall(
    name = "excludeVersionByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex),
    ValueParameter("versionRegex", versionRegex)
  )
}
