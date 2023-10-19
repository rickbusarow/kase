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

@file:Suppress("MemberVisibilityCanBePrivate")

package com.rickbusarow.kase.gradle.generation

import com.rickbusarow.kase.gradle.generation.Lambda.ConfigurationLambda
import dev.drewhamilton.poko.Poko

/**
 * A dependency declaration like `implementation("com.acme:dynamite:1.0.0")`
 * or `implementation project(':example')`
 */
public sealed interface DependencyDeclaration : DslBuilderComponent {
  /** The configuration, e.g. `implementation` */
  public val configuration: String

  /** A list of [DependencyExclusion]s, e.g. `exclude group: 'com.acme', module: 'rocket'` */
  public val exclusions: List<DependencyExclusion>

  /**
   * A dependency on a project in the same build, e.g. `implementation project(':example')`
   *
   * @property configuration the configuration, e.g. `implementation`
   * @property projectPath the path to the project, e.g. `:dynamite`
   * @property exclusions a list of [DependencyExclusion]s,
   *   e.g. `exclude group: 'com.acme', module: 'rocket'`
   */
  @Poko
  public class ProjectDependency(
    public override val configuration: String,
    public val projectPath: String,
    public override val exclusions: List<DependencyExclusion> = emptyList()
  ) : DependencyDeclaration {

    override fun write(language: DslLanguage): String {
      val lambda = ConfigurationLambda(exclusions, writeIfEmpty = false)

      val pathArg = "(${language.quote(projectPath)})"
      val projectArg = language.parens(pathArg)

      return "$configuration$projectArg${lambda.write(language)}"
    }
  }

  /**
   * A dependency on an external library, e.g. `implementation("com.acme:example:1.0.0")`
   *
   * @property configuration the configuration, e.g. `implementation`
   * @property group the group, e.g. `com.acme`
   * @property module the module, e.g. `dynamite`
   * @property version the version, e.g. `1.0.0`
   * @property exclusions a list of [DependencyExclusion]s,
   *   e.g. `exclude group: 'com.acme', module: 'rocket'`
   */
  @Poko
  public class ExternalDependency(
    public override val configuration: String,
    public val group: String,
    public val module: String,
    public val version: String,
    public override val exclusions: List<DependencyExclusion> = emptyList()
  ) : DependencyDeclaration {

    override fun write(language: DslLanguage): String {
      val lambda = ConfigurationLambda(exclusions, writeIfEmpty = false)

      val coords = language.quote("$group:$module:$version")
      val args = language.parens(coords)

      return "$configuration$args${lambda.write(language)}"
    }
  }
}
