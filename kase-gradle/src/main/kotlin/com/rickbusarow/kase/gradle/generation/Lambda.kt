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

import com.rickbusarow.kase.stdlib.indent
import dev.drewhamilton.poko.Poko

/** Models any lambda in a Gradle DSL */
public interface Lambda : DslElement {

  /** The contents of the lambda, such as dependency declarations or property assignments */
  public val contents: Collection<DslElement>

  /**
   * A lambda with no name, such as this configuration block:
   *
   * ```
   * tasks.named("someTask") {
   *   dependsOn("someOtherTask")
   * }
   * ```
   *
   * @property contents statements inside the lambda, such
   *   as `exclude group: "com.acme", module: "rocket"`
   * @property writeIfEmpty if true, the lambda will be written as `{ }` if it has no contents
   */
  @Poko
  public class ConfigurationLambda(
    public override val contents: Collection<DslElement>,
    public val writeIfEmpty: Boolean = false
  ) : Lambda {
    override fun write(language: DslLanguage): String {
      return when {
        contents.isEmpty() && writeIfEmpty -> " { }"
        contents.isEmpty() && !writeIfEmpty -> ""
        else -> buildString {
          appendLine(" {")
          indent {
            for (component in contents) {
              appendLine(component.write(language))
            }
          }
          appendLine("}")
        }
      }
    }
  }
}
