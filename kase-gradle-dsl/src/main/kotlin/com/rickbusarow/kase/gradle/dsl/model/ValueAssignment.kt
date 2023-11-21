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

package com.rickbusarow.kase.gradle.dsl.model

import com.rickbusarow.kase.gradle.DslLanguage
import com.rickbusarow.kase.gradle.internal.DslStringFactory
import dev.drewhamilton.poko.Poko

/** */
public sealed interface ValueAssignment : DslElement {

  /** the name of the variable or property being assigned, such as `version` or `group`. */
  public val name: String

  /**
   * Invoked during [DslLanguage.write] to create the value
   * being assigned, such as `"1.0.0"` or `"com.acme"`.
   */
  public val dslStringFactory: DslStringFactory

  /**
   * A variable assignment, such as `version = "1.0.0"`
   *
   * @property name the name of the variable, such as `version`
   * @property dslStringFactory the value being assigned, such as `"1.0.0"`
   */
  @Poko
  public class SetterAssignment(
    override val name: String,
    override val dslStringFactory: DslStringFactory
  ) : ValueAssignment {
    public constructor(name: String, value: String) : this(name, DslStringFactory(value))

    override fun write(language: DslLanguage): String {
      return language.write { "$name = ${dslStringFactory.write(language)}" }
    }
  }

  /**
   * A gradle property assignment, such as `version.set("1.0.0")`
   *
   * @property name the name of the property, such as `version`
   * @property dslStringFactory the value being assigned, such as `"1.0.0"`
   */
  @Poko
  public class GradlePropertyAssignment(
    override val name: String,
    override val dslStringFactory: DslStringFactory
  ) : ValueAssignment {
    public constructor(name: String, value: String) : this(name, DslStringFactory(value))

    override fun write(language: DslLanguage): String {
      return language.write { "$name.set(${dslStringFactory.write(language)})" }
    }
  }
}
