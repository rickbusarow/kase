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
import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.GradleProjectBuilder
import com.rickbusarow.kase.gradle.HasDslLanguage
import com.rickbusarow.kase.gradle.dsl.model.AbstractDslElementContainer
import com.rickbusarow.kase.gradle.dsl.model.DslFileBuilder
import com.rickbusarow.kase.gradle.dsl.model.HasAndroidBlock
import com.rickbusarow.kase.gradle.dsl.model.HasAndroidComponentsBlock
import com.rickbusarow.kase.gradle.dsl.model.HasDependenciesBlock
import com.rickbusarow.kase.gradle.dsl.model.HasPluginsBlock
import java.io.File

/**
 * Models a `settings.gradle` or `settings.gradle.kts` file.
 *
 * @since 0.1.0
 */
public class BuildFileSpec(
  build: BuildFileSpec.() -> Unit
) : AbstractDslElementContainer<BuildFileSpec>(),
  DslFileBuilder<BuildFileSpec>,
  HasDependenciesBlock<BuildFileSpec>,
  HasPluginsBlock<BuildFileSpec>,
  HasAndroidBlock,
  HasAndroidComponentsBlock,
  HasBuildscriptBlock {

  init {
    build()
  }
}

/**
 * Creates a `build.gradle[.kts]` file in the directory builder's current directory.
 *
 * @since 0.1.0
 */
public fun DirectoryBuilder.buildFile(
  dslLanguage: DslLanguage,
  builder: BuildFileSpec.() -> Unit
): File = file(
  relativePath = dslLanguage.buildFileName,
  content = BuildFileSpec(builder).write(dslLanguage)
)

/**
 * Creates a `build.gradle[.kts]` file in the directory builder's current directory.
 *
 * @since 0.1.0
 */
context(HasDslLanguage)
public fun DirectoryBuilder.buildFile(
  dslLanguage: DslLanguage = this@HasDslLanguage.dslLanguage,
  builder: BuildFileSpec.() -> Unit
): File = file(
  relativePath = dslLanguage.buildFileName,
  content = BuildFileSpec(builder).write(dslLanguage)
)

/**
 * Creates a `build.gradle[.kts]` file in the directory builder's current directory.
 *
 * @since 0.1.0
 */
public fun GradleProjectBuilder.buildFile(
  dslLanguage: DslLanguage = this@GradleProjectBuilder.dslLanguage,
  builder: BuildFileSpec.() -> Unit
): File = file(
  relativePath = dslLanguage.buildFileName,
  content = BuildFileSpec(builder).write(dslLanguage)
)
