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
import com.rickbusarow.kase.gradle.GradleProjectBuilder
import com.rickbusarow.kase.gradle.HasDslLanguage
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.BuildscriptSpec
import com.rickbusarow.kase.gradle.dsl.model.DslFileBuilder
import com.rickbusarow.kase.gradle.dsl.model.HasDependenciesBlock
import com.rickbusarow.kase.gradle.dsl.model.HasPluginsBlock
import com.rickbusarow.kase.gradle.dsl.model.LambdaParameter

/** Models a `settings.gradle` or `settings.gradle.kts` file. */
public class BuildFileSpec(
  build: BuildFileSpec.() -> Unit
) : AbstractDslElementContainer<BuildFileSpec>(),
  DslFileBuilder<BuildFileSpec>,
  HasPluginsBlock<BuildFileSpec>,
  HasDependenciesBlock<BuildFileSpec> {

  init {
    build()
  }

  /**
   * ```
   * // build.gradle
   * buildscript {
   *   // ...
   * }
   * ```
   */
  public fun buildscript(
    block: BuildscriptSpec.() -> Unit
  ): BuildFileSpec = functionCall(
    name = "buildscript",
    LambdaParameter(builder = block)
  )
}

/** Creates a `build.gradle[.kts]` file in the directory builder's current directory. */
public fun DirectoryBuilder.buildFile(
  dslLanguage: DslLanguage,
  builder: BuildFileSpec.() -> Unit
): FileWithContent = file(
  relativePath = dslLanguage.buildFileName,
  content = BuildFileSpec(builder).write(dslLanguage)
)

/** Creates a `build.gradle[.kts]` file in the directory builder's current directory. */
context(HasDslLanguage)
public fun DirectoryBuilder.buildFile(
  dslLanguage: DslLanguage = this@HasDslLanguage.dslLanguage,
  builder: BuildFileSpec.() -> Unit
): FileWithContent = file(
  relativePath = dslLanguage.buildFileName,
  content = BuildFileSpec(builder).write(dslLanguage)
)

/** Creates a `build.gradle[.kts]` file in the directory builder's current directory. */
public fun GradleProjectBuilder.buildFile(
  dslLanguage: DslLanguage = this@GradleProjectBuilder.dslLanguage,
  builder: BuildFileSpec.() -> Unit
): FileWithContent = file(
  relativePath = dslLanguage.buildFileName,
  content = BuildFileSpec(builder).write(dslLanguage)
)