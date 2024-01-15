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

package com.rickbusarow.kase.gradle.internal

import com.rickbusarow.kase.files.DirectoryBuilder
import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.GradleProjectBuilder
import com.rickbusarow.kase.gradle.GradleRootProjectBuilder
import com.rickbusarow.kase.stdlib.div
import java.io.File

internal interface GradleProjectBuilderInternal :
  GradleProjectBuilder,
  DirectoryBuilder {

  override val subprojects: MutableList<GradleProjectBuilderInternal>

  override fun project(relativePath: String, builder: GradleProjectBuilder.() -> Unit) {
    val parent = this@GradleProjectBuilderInternal

    dir(relativePath) db@{

      val db = this@db

      val child = DefaultGradleProjectBuilder(
        path = path / relativePath,
        dslLanguage = parent.dslLanguage,
        directoryBuilder = db,
        parent = parent
      )
      child.builder()
      parent.subprojects.add(child)
    }
  }
}

internal class DefaultGradleProjectBuilder(
  override val path: File,
  override val dslLanguage: DslLanguage,
  directoryBuilder: DirectoryBuilder,
  override val parent: GradleProjectBuilderInternal?
) : GradleProjectBuilderInternal,
  DirectoryBuilder by directoryBuilder {

  override val subprojects: MutableList<GradleProjectBuilderInternal> = mutableListOf()
}

internal class DefaultGradleRootProjectBuilder(
  override val path: File,
  override val dslLanguage: DslLanguage,
  directoryBuilder: DirectoryBuilder,
  override val parent: GradleProjectBuilderInternal?
) : GradleRootProjectBuilder,
  GradleProjectBuilderInternal by DefaultGradleProjectBuilder(
    path = path,
    dslLanguage = dslLanguage,
    directoryBuilder = directoryBuilder,
    parent = parent
  )
