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

import com.rickbusarow.kase.gradle.generation.model.DslLanguage

internal data class ExpectedCodeGenerator(
  val language: DslLanguage,
  val kotlinInfix: Boolean = language.useInfix,
  val kotlinLabels: Boolean = language.useLabels,
  val groovyInfix: Boolean = language.useInfix,
  val groovyLabels: Boolean = language.useLabels
) {

  internal fun create(
    functionName: String,
    labelName: String,
    valueString: String
  ): String = create(
    functionName = functionName,
    labelName to valueString
  )

  internal fun create(
    functionName: String,
    vararg labeledValues: Pair<String, String>
  ): String {

    val joinedArgs = labeledValues.joinToString(", ") { (label, value) ->
      language.run {

        val labels = useLabels && when (this) {
          is DslLanguage.KotlinDsl -> kotlinLabels
          is DslLanguage.GroovyDsl -> groovyLabels
        }

        when (this) {
          is DslLanguage.KotlinDsl -> when {
            labels -> "$label = \"$value\""
            else -> "\"$value\""
          }

          is DslLanguage.GroovyDsl -> when {
            useDoubleQuotes && labels -> """$label: "$value""""
            useDoubleQuotes && !labels -> """"$value""""
            labels -> "$label: '$value'"
            else -> "'$value'"
          }
        }
      }
    }

    return when {
      language.useInfix &&
        (language is DslLanguage.GroovyDsl && groovyInfix) -> "$functionName $joinedArgs"

      language.useInfix &&
        (language is DslLanguage.KotlinDsl && kotlinInfix) -> "$functionName $joinedArgs"

      else -> {
        "$functionName($joinedArgs)"
      }
    }
  }
}