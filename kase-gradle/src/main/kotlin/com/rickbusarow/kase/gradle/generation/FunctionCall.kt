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

import com.rickbusarow.kase.gradle.generation.LanguageSpecific.GroovyAndKotlinCompatible
import com.rickbusarow.kase.gradle.generation.LanguageSpecific.GroovyCompatible
import com.rickbusarow.kase.gradle.generation.LanguageSpecific.KotlinCompatible
import dev.drewhamilton.poko.Poko

@Poko
public class FunctionCall(
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
    labelSupport = LabelSupport.NONE,
    parameters = parameters.toList()
  )

  public constructor(
    name: String,
    vararg parameters: String
  ) : this(
    name = name,
    parameters = parameters.map { Parameter(it) },
    labelSupport = LabelSupport.NONE
  )

  override fun write(language: DslLanguage): String {
    return language.write { name.invoke(parameterList.write(language, labelSupport)) }
  }

  public sealed interface LabelSupport {
    public object NONE : LabelSupport
    public object KOTLIN : LabelSupport, KotlinCompatible
    public object GROOVY : LabelSupport, GroovyCompatible
    public object BOTH : LabelSupport, GroovyAndKotlinCompatible
  }
}
