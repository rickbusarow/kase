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

import com.rickbusarow.kase.gradle.generation.model.FunctionCall.LabelSupport.NoLabels
import com.rickbusarow.kase.gradle.generation.model.LanguageSpecific.GroovyAndKotlinCompatible
import com.rickbusarow.kase.gradle.generation.model.LanguageSpecific.GroovyCompatible
import com.rickbusarow.kase.gradle.generation.model.LanguageSpecific.KotlinCompatible
import dev.drewhamilton.poko.Poko

/**
 * A function call
 *
 * @property name the name of the function, such as `exclude`
 * @property parameterList the list of parameters to pass to the function
 * @property labelSupport whether to use labels in the function call, such as `group = "com.acme"`
 */
@Poko
public class FunctionCall public constructor(
  public val name: String,
  public val parameterList: ParameterList,
  public val labelSupport: LabelSupport
) : DslElement {

  public constructor(
    name: String,
    labelSupport: LabelSupport,
    vararg parameters: Parameter
  ) : this(
    name = name,
    parameterList = ParameterList(parameters.toList()),
    labelSupport = labelSupport
  )

  public constructor(
    name: String,
    parameters: List<Parameter>
  ) : this(
    name = name,
    parameterList = ParameterList(parameters),
    labelSupport = NoLabels
  )

  public constructor(
    name: String,
    labelSupport: LabelSupport,
    parameters: List<Parameter>
  ) : this(
    name = name,
    parameterList = ParameterList(parameters),
    labelSupport = labelSupport
  )

  public constructor(
    name: String,
    vararg parameters: Parameter
  ) : this(
    name = name,
    labelSupport = NoLabels,
    parameters = parameters.toList()
  )

  public constructor(
    name: String,
    vararg parameters: String
  ) : this(
    name = name,
    parameters = parameters.map { Parameter(it) },
    labelSupport = NoLabels
  )

  override fun write(language: DslLanguage): String {
    return language.write { "$name${parameterList.write(language, labelSupport)}" }
  }

  /**
   * Whether this function call supports labels for its parameters.
   *
   * Labels will be supported in Kotlin if the function was re-written in the Kotlin DSL.
   */
  public sealed class LabelSupport {

    override fun toString(): String = javaClass.simpleName

    /** No labels are supported. */
    public object NoLabels : LabelSupport()

    /** Labels are supported in the Groovy DSL. */
    public object GroovyLabels : LabelSupport(), GroovyCompatible

    /** Labels are supported in the Kotlin DSL. */
    public object KotlinLabels : LabelSupport(), KotlinCompatible

    /** Labels are supported in both the Groovy and Kotlin DSLs. */
    public object GroovyAndKotlinLabels : LabelSupport(), GroovyAndKotlinCompatible
  }
}
