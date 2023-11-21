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
import com.rickbusarow.kase.gradle.DslLanguage.GroovyDsl
import com.rickbusarow.kase.gradle.DslLanguage.KotlinDsl
import com.rickbusarow.kase.gradle.DslLanguageSettings.InfixSupport
import dev.drewhamilton.poko.Poko

/** A single application of a plugin inside a `plugins { }` block. */
public sealed class PluginApplication : DslElement {
  /** The plugin identifier, such as `kotlin` or `com.android.application`. */
  public abstract val identifier: String

  /** The plugin version, such as `1.3.50`. */
  public abstract val version: String?

  /** If false, appends `apply false`. */
  public abstract val apply: Boolean

  final override fun write(language: DslLanguage): String {

    return buildString {

      append(simpleCase(language))

      val v = version

      if (language.useInfix) {
        if (v != null) {
          append(" version ${language.quote(v)}")
        }
        if (!apply) {
          append(" apply false")
        }
      } else {
        if (v != null) {
          append(".version(${language.quote(v)})")
        }
        if (!apply) {
          append(".apply(false)")
        }
      }
    }
  }

  internal abstract fun simpleCase(language: DslLanguage): String

  /** Applied via the typical `id("com.acme.anvil")` syntax. */
  @Poko
  public class ID(
    override val identifier: String,
    override val version: String? = null,
    override val apply: Boolean = true
  ) : PluginApplication() {

    override fun simpleCase(language: DslLanguage): String = buildString {
      append("id")
      append(language.parens(language.quote(identifier), infixSupport = InfixSupport.GroovyInfix))
    }
  }

  /**
   * Applied from a version catalog and using the `alias`
   * syntax, like `alias(libs.plugins.kotlin.jvm)`.
   *
   * @property aliasName the name of the alias, such as `libs.plugins.kotlin.jvm`
   * @property version the version of the plugin, such as `1.3.50`
   * @property apply whether to apply the plugin, or just add it to the classpath
   */
  @Poko
  public class Alias(
    public val aliasName: String,
    override val version: String? = null,
    override val apply: Boolean = true
  ) : PluginApplication() {

    public override val identifier: String = aliasName

    override fun simpleCase(language: DslLanguage): String = buildString {
      append("alias")
      append(language.parens(identifier, infixSupport = InfixSupport.GroovyInfix))
    }
  }

  /**
   * Applied from a precompiled script, like:
   *
   * ```
   * plugins {
   *   `kotlin-dsl`
   * }
   * ```
   */
  @Poko
  public class Precompiled(
    override val identifier: String,
    override val version: String? = null,
    override val apply: Boolean = true
  ) : PluginApplication() {

    override fun simpleCase(language: DslLanguage): String {

      return when (language) {
        is GroovyDsl -> buildString {
          append("id")
          append(language.parens(language.quote(identifier)))
        }

        is KotlinDsl -> if (identifier.contains('-')) {
          "`$identifier`"
        } else {
          identifier
        }
      }
    }
  }

  /**
   * Applied using the `kotlin()` syntax, like:
   *
   * ```
   * plugins {
   *   kotlin("jvm")
   * }
   * ```
   */
  @Poko
  public class KotlinPlugin(
    override val identifier: String,
    override val version: String? = null,
    override val apply: Boolean = true
  ) : PluginApplication() {

    init {
      require(!identifier.contains('.')) {
        "Kotlin plugin identifiers are simple names like `jvm` or `android`."
      }
    }

    override fun simpleCase(language: DslLanguage): String {

      return when (language) {
        is GroovyDsl -> buildString {
          append("id")
          append(language.parens(language.quote("org.jetbrains.kotlin.$identifier")))
        }

        is KotlinDsl -> "kotlin(\"$identifier\")"
      }
    }
  }
}
