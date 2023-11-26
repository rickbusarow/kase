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

import com.rickbusarow.kase.files.DirectoryBuilder
import com.rickbusarow.kase.files.DirectoryBuilder.FileWithContent
import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import com.rickbusarow.kase.gradle.DslLanguageSettings.LabelSupport
import com.rickbusarow.kase.gradle.GradleProjectBuilder
import com.rickbusarow.kase.gradle.HasDslLanguage
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.DslFileBuilder
import com.rickbusarow.kase.gradle.dsl.model.HasIncludedBuild
import com.rickbusarow.kase.gradle.dsl.model.HasIncludes
import com.rickbusarow.kase.gradle.dsl.model.HasPluginsBlock
import com.rickbusarow.kase.gradle.dsl.model.LambdaParameter
import com.rickbusarow.kase.gradle.dsl.model.RegularVariableReference.MutableVariableReference
import com.rickbusarow.kase.gradle.dsl.model.mutableVariableReference

/** Models a `settings.gradle` or `settings.gradle.kts` file. */
public class SettingsFileSpec(
  build: SettingsFileSpec.() -> Unit
) : AbstractDslElementContainer<SettingsFileSpec>(),
  DslFileBuilder<SettingsFileSpec>,
  HasPluginsBlock<SettingsFileSpec>,
  HasIncludes<SettingsFileSpec>,
  HasIncludedBuild<SettingsFileSpec> {

  /**
   * ```
   * rootProject.name = 'my-project'
   * ```
   */
  public val rootProjectName: MutableVariableReference<String>
    by mutableVariableReference("rootProject.name") { stringLiteral(it) }

  init {
    build()
  }

  /**
   * ```
   * // settings.gradle
   * pluginManagement {
   *   // ...
   * }
   * ```
   */
  public fun pluginManagement(
    block: PluginManagementSpecSpec.() -> Unit
  ): SettingsFileSpec = functionCall(
    name = "pluginManagement",
    labelSupport = LabelSupport.NoLabels,
    infixSupport = InfixSupport.NoInfix,
    LambdaParameter(block)
  )

  /**
   * ```
   * // settings.gradle
   * dependencyResolutionManagement {
   *   // ...
   * }
   * ```
   */
  public fun dependencyResolutionManagement(
    block: DependencyResolutionManagementSpecSpec.() -> Unit
  ): SettingsFileSpec = functionCall(
    name = "dependencyResolutionManagement",
    labelSupport = LabelSupport.NoLabels,
    infixSupport = InfixSupport.NoInfix,
    LambdaParameter(block)
  )
}

/** Creates a `settings.gradle[.kts]` file in the directory builder's current directory. */
public fun DirectoryBuilder.settingsFile(
  dslLanguage: DslLanguage,
  builder: SettingsFileSpec.() -> Unit
): FileWithContent = file(
  relativePath = dslLanguage.settingsFileName,
  content = SettingsFileSpec(builder).write(dslLanguage)
)

/** Creates a `settings.gradle[.kts]` file in the directory builder's current directory. */
context(HasDslLanguage)
public fun DirectoryBuilder.settingsFile(
  dslLanguage: DslLanguage = this@HasDslLanguage.dslLanguage,
  builder: SettingsFileSpec.() -> Unit
): FileWithContent = file(
  relativePath = dslLanguage.settingsFileName,
  content = SettingsFileSpec(builder).write(dslLanguage)
)

/** Creates a `settings.gradle[.kts]` file in the directory builder's current directory. */
public fun GradleProjectBuilder.settingsFile(
  dslLanguage: DslLanguage = this@GradleProjectBuilder.dslLanguage,
  builder: SettingsFileSpec.() -> Unit
): FileWithContent = file(
  relativePath = dslLanguage.settingsFileName,
  content = SettingsFileSpec(builder).write(dslLanguage)
)
