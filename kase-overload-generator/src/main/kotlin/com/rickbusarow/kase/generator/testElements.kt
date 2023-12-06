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

internal fun writeTestElements() {

  val classNames = List(MAX_PARAMS) { "TestVersion${it + 1}" }

  val content = buildString {

    appendLine(
      """
      |$LICENSE
      |
      |$FILE_ANNOTATIONS
      |
      |package com.rickbusarow.kase.gradle
      |
      |${
        classNames.sorted()
          .joinToString("\n") { "import com.rickbusarow.kase.gradle.$it.${it}Key" }
      }
      |import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
      |import dev.drewhamilton.poko.Poko
      |
      """.trimMargin()
    )

    val classes = classNames.map { tv ->
      """
      |@Poko
      |class $tv(
      |  override val value: String
      |) : AbstractDependencyVersion<String, $tv, ${tv}Key>(${tv}Key) {
      |
      |  companion object ${tv}Key : VersionMatrixKey<$tv>
      |}
      |
      """.trimMargin()
    }

    for (clazz in classes) {
      appendLine(clazz)
    }
  }
    .fixBlankLines()

  testElements.writeText(content)
}
