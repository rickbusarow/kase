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

public class RepositoryHandlerBuilder : DslElementContainer() {

  public fun maven(
    url: String,
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "maven",
    labelSupport = GROOVY,
    ValueParameter("url", url),
    LambdaParameter(label = "action", builder = action)
  )

  public fun gradlePluginPortal(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "gradlePluginPortal",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )

  public fun mavenCentral(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "mavenCentral",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )

  public fun mavenLocal(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "mavenLocal",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )

  public fun google(
    action: MavenArtifactRepositoryBuilder.() -> Unit = {}
  ): RepositoryHandlerBuilder = functionCall(
    name = "google",
    labelSupport = GROOVY,
    LambdaParameter("action", action)
  )
}

public abstract class ArtifactRepositoryBuilder<T : RepositoryContentBuilder> : DslElementContainer() {

  public abstract fun content(block: T.() -> Unit): ArtifactRepositoryBuilder<T>
}

public class MavenArtifactRepositoryBuilder : ArtifactRepositoryBuilder<MavenRepositoryContentBuilder>() {

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

public class MavenRepositoryContentBuilder : RepositoryContentBuilder() {

  public fun releasesOnly(): MavenRepositoryContentBuilder = apply {
    add(FunctionCall(name = "releasesOnly", labelSupport = NONE))
  }

  public fun snapshotsOnly(): MavenRepositoryContentBuilder = apply {
    add(FunctionCall(name = "snapshotsOnly", labelSupport = NONE))
  }
}

public abstract class RepositoryContentBuilder : DslElementContainer() {

  public fun includeGroup(group: String): RepositoryContentBuilder = functionCall(
    name = "includeGroup",
    labelSupport = GROOVY,
    ValueParameter("group", group)
  )

  public fun includeGroupByRegex(groupRegex: String): RepositoryContentBuilder = functionCall(
    name = "includeGroupByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex)
  )

  public fun includeGroupAndSubgroups(groupPrefix: String): RepositoryContentBuilder = functionCall(
    name = "includeGroupAndSubgroups",
    labelSupport = GROOVY,
    ValueParameter("groupPrefix", groupPrefix)
  )

  public fun includeModule(
    group: String,
    moduleName: String
  ): RepositoryContentBuilder = functionCall(
    name = "includeModule",
    labelSupport = GROOVY,
    ValueParameter("group", group),
    ValueParameter("moduleName", moduleName)
  )

  public fun includeModuleByRegex(
    groupRegex: String,
    moduleNameRegex: String
  ): RepositoryContentBuilder = functionCall(
    name = "includeModuleByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex)
  )

  public fun includeVersion(
    group: String,
    moduleName: String,
    version: String
  ): RepositoryContentBuilder = functionCall(
    name = "includeVersion",
    labelSupport = GROOVY,
    ValueParameter("group", group),
    ValueParameter("moduleName", moduleName),
    ValueParameter("version", version)
  )

  public fun includeVersionByRegex(
    groupRegex: String,
    moduleNameRegex: String,
    versionRegex: String
  ): RepositoryContentBuilder = functionCall(
    name = "includeVersionByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex),
    ValueParameter("versionRegex", versionRegex)
  )

  public fun excludeGroup(group: String): RepositoryContentBuilder = functionCall(
    name = "excludeGroup",
    labelSupport = GROOVY,
    ValueParameter("group", group)
  )

  public fun excludeGroupByRegex(groupRegex: String): RepositoryContentBuilder = functionCall(
    name = "excludeGroupByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex)
  )

  public fun excludeGroupAndSubgroups(groupPrefix: String): RepositoryContentBuilder = functionCall(
    name = "excludeGroupAndSubgroups",
    labelSupport = GROOVY,
    ValueParameter("groupPrefix", groupPrefix)
  )

  public fun excludeModule(
    group: String,
    moduleName: String
  ): RepositoryContentBuilder = functionCall(
    name = "excludeModule",
    labelSupport = GROOVY,
    ValueParameter("group", group),
    ValueParameter("moduleName", moduleName)
  )

  public fun excludeModuleByRegex(
    groupRegex: String,
    moduleNameRegex: String
  ): RepositoryContentBuilder = functionCall(
    name = "excludeModuleByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex)
  )

  public fun excludeVersion(
    group: String,
    moduleName: String,
    version: String
  ): RepositoryContentBuilder = functionCall(
    name = "excludeVersion",
    labelSupport = GROOVY,
    ValueParameter("group", group),
    ValueParameter("moduleName", moduleName),
    ValueParameter("version", version)
  )

  public fun excludeVersionByRegex(
    groupRegex: String,
    moduleNameRegex: String,
    versionRegex: String
  ): RepositoryContentBuilder = functionCall(
    name = "excludeVersionByRegex",
    labelSupport = GROOVY,
    ValueParameter("groupRegex", groupRegex),
    ValueParameter("moduleNameRegex", moduleNameRegex),
    ValueParameter("versionRegex", versionRegex)
  )
}
