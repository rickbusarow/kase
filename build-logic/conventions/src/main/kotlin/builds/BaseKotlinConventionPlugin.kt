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

package builds

import com.rickbusarow.kgx.applyOnce
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.BaseKotlinCompile
import java.io.Serializable
import kotlin.jvm.java
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile as KotlinCompileDsl

interface KotlinJvmExtension : KotlinExtension
interface KotlinMultiplatformExtension : KotlinExtension
interface KotlinExtension : Serializable {

  val allWarningsAsErrors: Property<Boolean>
  val explicitApi: Property<Boolean>
}

abstract class BaseKotlinConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    target.plugins.applyOnce("org.jetbrains.kotlin.jvm")

    val extension = target.extensions.getByType(KotlinExtension::class.java)

    target.kotlinExtension.jvmToolchain(target.JDK_INT)

    target.tasks.withType(KotlinCompileDsl::class.java).configureEach { task ->
      task.kotlinOptions {

        options.allWarningsAsErrors.set(extension.allWarningsAsErrors)

        val kotlinMajor = target.KOTLIN_API
        languageVersion = kotlinMajor
        apiVersion = kotlinMajor

        @Suppress("SpellCheckingInspection")
        freeCompilerArgs += buildList {
          add("-Xinline-classes")
          add("-Xcontext-receivers")

          val sourceSetName = (task as? BaseKotlinCompile)?.sourceSetName?.orNull

          val shouldBeStrict = when {
            extension.explicitApi.orNull == false -> false
            sourceSetName == "test" -> false
            sourceSetName == null -> false
            else -> true
          }
          if (shouldBeStrict) {
            add("-Xexplicit-api=strict")
          }
        }
      }
    }
  }
}
