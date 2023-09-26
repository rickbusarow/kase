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

import org.gradle.api.Plugin
import org.gradle.api.Project

/** Applies conventions to any project which is not a Swift project. */
abstract class ModulePlugin : Plugin<Project> {
  override fun apply(target: Project) {

    target.plugins.apply(CheckPlugin::class.java)
    target.plugins.apply(CleanPlugin::class.java)
    target.plugins.apply(DependencyGuardConventionPlugin::class.java)
    target.plugins.apply(DetektConventionPlugin::class.java)
    target.plugins.apply(DokkaConventionPlugin::class.java)
    target.plugins.apply(KotlinJvmConventionPlugin::class.java)
    target.plugins.apply(KtLintConventionPlugin::class.java)
    target.plugins.apply(TestConventionPlugin::class.java)

    target.extensions.create("module", ModuleBuildExtension::class.java)
  }
}
