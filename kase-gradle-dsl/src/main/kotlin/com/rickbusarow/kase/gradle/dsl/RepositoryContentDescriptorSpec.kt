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

package com.rickbusarow.kase.gradle.dsl

import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport.NoInfix
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.FunctionCall
import com.rickbusarow.kase.gradle.dsl.model.ValueParameter

/**
 * Adds the common configurations to a repository's content, like:
 *
 * @since 0.1.0
 */
public sealed class RepositoryContentDescriptorSpec<SELF : RepositoryContentDescriptorSpec<SELF>> :
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
   *
   * @since 0.1.0
   */
  public fun includeGroup(group: String): SELF = functionCall(
    name = "includeGroup",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("group", group.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun includeGroupByRegex(groupRegex: String): SELF = functionCall(
    name = "includeGroupByRegex",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupRegex", groupRegex.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun includeGroupAndSubgroups(groupPrefix: String): SELF = functionCall(
    name = "includeGroupAndSubgroups",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupPrefix", groupPrefix.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun includeModule(group: String, moduleName: String): SELF = functionCall(
    name = "includeModule",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("group", group.asStringLiteral()),
    ValueParameter("moduleName", moduleName.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun includeModuleByRegex(groupRegex: String, moduleNameRegex: String): SELF = functionCall(
    name = "includeModuleByRegex",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupRegex", groupRegex.asStringLiteral()),
    ValueParameter("moduleNameRegex", moduleNameRegex.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun includeVersion(
    group: String,
    moduleName: String,
    version: String
  ): SELF =
    functionCall(
      name = "includeVersion",
      labelSupport = LabelSupport.GroovyLabels,
      infixSupport = InfixSupport.GroovyInfix,
      ValueParameter("group", group.asStringLiteral()),
      ValueParameter("moduleName", moduleName.asStringLiteral()),
      ValueParameter("version", version.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun includeVersionByRegex(
    groupRegex: String,
    moduleNameRegex: String,
    versionRegex: String
  ): SELF = functionCall(
    name = "includeVersionByRegex",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupRegex", groupRegex.asStringLiteral()),
    ValueParameter("moduleNameRegex", moduleNameRegex.asStringLiteral()),
    ValueParameter("versionRegex", versionRegex.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun excludeGroup(group: String): SELF = functionCall(
    name = "excludeGroup",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("group", group.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun excludeGroupByRegex(groupRegex: String): SELF = functionCall(
    name = "excludeGroupByRegex",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupRegex", groupRegex.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun excludeGroupAndSubgroups(groupPrefix: String): SELF = functionCall(
    name = "excludeGroupAndSubgroups",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupPrefix", groupPrefix.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun excludeModule(group: String, moduleName: String): SELF = functionCall(
    name = "excludeModule",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("group", group.asStringLiteral()),
    ValueParameter("moduleName", moduleName.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun excludeModuleByRegex(groupRegex: String, moduleNameRegex: String): SELF = functionCall(
    name = "excludeModuleByRegex",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupRegex", groupRegex.asStringLiteral()),
    ValueParameter("moduleNameRegex", moduleNameRegex.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun excludeVersion(
    group: String,
    moduleName: String,
    version: String
  ): SELF = functionCall(
    name = "excludeVersion",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("group", group.asStringLiteral()),
    ValueParameter("moduleName", moduleName.asStringLiteral()),
    ValueParameter("version", version.asStringLiteral())
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
   *
   * @since 0.1.0
   */
  public fun excludeVersionByRegex(
    groupRegex: String,
    moduleNameRegex: String,
    versionRegex: String
  ): SELF = functionCall(
    name = "excludeVersionByRegex",
    labelSupport = LabelSupport.GroovyLabels,
    infixSupport = InfixSupport.GroovyInfix,
    ValueParameter("groupRegex", groupRegex.asStringLiteral()),
    ValueParameter("moduleNameRegex", moduleNameRegex.asStringLiteral()),
    ValueParameter("versionRegex", versionRegex.asStringLiteral())
  )
}

/**
 * The configuration options for a maven repository's content.
 *
 * @since 0.1.0
 */
public class MavenRepositoryContentDescriptorSpec :
  RepositoryContentDescriptorSpec<MavenRepositoryContentDescriptorSpec>() {

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
   *
   * @since 0.1.0
   */
  public fun releasesOnly(): MavenRepositoryContentDescriptorSpec = apply {
    addElement(FunctionCall(name = "releasesOnly", labelSupport = NoLabels, infixSupport = NoInfix))
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
   *
   * @since 0.1.0
   */
  public fun snapshotsOnly(): MavenRepositoryContentDescriptorSpec = apply {
    addElement(
      FunctionCall(name = "snapshotsOnly", labelSupport = NoLabels, infixSupport = NoInfix)
    )
  }
}
