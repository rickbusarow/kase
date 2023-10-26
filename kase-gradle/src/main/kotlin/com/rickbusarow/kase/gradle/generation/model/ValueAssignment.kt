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

package com.rickbusarow.kase.gradle.generation.model

import dev.drewhamilton.poko.Poko

/** */
public sealed interface ValueAssignment : DslElement {

  /** the name of the variable or property being assigned, such as `version` or `group`. */
  public val name: String

  /**
   * Invoked during [DslLanguage.write] to create the value
   * being assigned, such as `"1.0.0"` or `"com.acme"`.
   */
  public val valueStringFactory: ValueStringFactory

  /**
   * A variable assignment, such as `version = "1.0.0"`
   *
   * @property name the name of the variable, such as `version`
   * @property valueStringFactory the value being assigned, such as `"1.0.0"`
   */
  @Poko
  public class SetterAssignment(
    override val name: String,
    override val valueStringFactory: ValueStringFactory
  ) : ValueAssignment {
    public constructor(name: String, value: String) : this(name, ValueStringFactory(value))
    public constructor(name: String, value: DslElement) : this(name, ValueStringFactory(value))

    override fun write(language: DslLanguage): String {
      return language.write { "$name = ${valueStringFactory.invoke(language)}" }
    }
  }

  /**
   * A gradle property assignment, such as `version.set("1.0.0")`
   *
   * @property name the name of the property, such as `version`
   * @property valueStringFactory the value being assigned, such as `"1.0.0"`
   */
  @Poko
  public class GradlePropertyAssignment(
    override val name: String,
    override val valueStringFactory: ValueStringFactory
  ) : ValueAssignment {
    public constructor(name: String, value: String) : this(name, ValueStringFactory(value))
    public constructor(name: String, value: DslElement) : this(name, ValueStringFactory(value))

    override fun write(language: DslLanguage): String {
      return language.write { "$name.set(${valueStringFactory.invoke(language)})" }
    }
  }
}

/** Invoked during [DslLanguage.write] to create a value string. */
public fun interface ValueStringFactory {
  /** @param language the [DslLanguage] being written */
  public operator fun invoke(language: DslLanguage): String

  public companion object {
    /** Creates a no-op [ValueStringFactory] which returns the given [value] string. */
    public operator fun invoke(value: String): ValueStringFactory {
      return ValueStringFactory { value }
    }

    /** Shortcut for `ValueStringFactory { value.write(language) }`. */
    public operator fun invoke(value: DslElement): ValueStringFactory {
      return ValueStringFactory(value::write)
    }
  }
}
