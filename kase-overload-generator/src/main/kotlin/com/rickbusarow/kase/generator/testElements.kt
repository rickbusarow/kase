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

package com.rickbusarow.kase.generator

import com.rickbusarow.kase.generator.Files.testElements
import com.rickbusarow.kase.generator.Names.Fqn.Companion.asImports
import com.rickbusarow.kase.generator.Names.kaseMatrixKey
import com.rickbusarow.kase.generator.Names.poko

internal fun writeTestElements() {

  val gradlePackage = Names.basePackage.child("gradle")

  val classNames = List(MAX_PARAMS) {

    gradlePackage.child("TestElement${it + 1}").let { element ->

      // `$package.TestElement1` to `$package.TestElement1.TestElement1Key`
      element to element.child("${element.simple}Key")
    }
  }

  val content = buildString {

    val imports = buildList {
      add(kaseMatrixKey)
      addAll(classNames.map { it.second })
      add(poko)
    }
      .asImports()

    appendLine(
      """
      |$LICENSE
      |
      |package com.rickbusarow.kase.gradle
      |
      |$imports
      |
      """.trimMargin()
    )

    for ((element, key) in classNames) {
      appendLine(
        """
        |@Poko
        |class ${element.simple}(
        |  override val value: String
        |) : AbstractDependencyVersion<${element.simple}, ${key.simple}>(${key.simple}) {
        |
        |  companion object ${key.simple} : ${kaseMatrixKey.simple}<${element.simple}>
        |}
        |
        """.trimMargin()
      )
    }
  }
    .fixBlankLines()

  testElements.writeText(content)
}
