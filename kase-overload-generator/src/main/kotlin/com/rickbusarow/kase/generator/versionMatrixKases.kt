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

internal fun writeGradleFile() {
  val gradle = buildString {

    appendLine(
      """
      |$LICENSE
      |
      |$FILE_ANNOTATIONS
      |
      |package com.rickbusarow.kase.gradle
      |
      """.trimMargin()
    )

    List(MAX_PARAMS) { it + 1 }
      .map { "import com.rickbusarow.kase.Kase$it" }
      .sorted()
      .forEach(::appendLine)

    appendLine("import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixElement")
    appendLine("import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey")
    appendLine("import com.rickbusarow.kase.kases")
    appendLine()

    for (ct in (1..MAX_PARAMS)) {

      val args = (1..ct).map { KaseArg(it) }
      val types = KaseTypes(ct, args)

      val kdoc = buildList {
        add(
          "Returns a list of [${types.kaseInterfaceNoTypes}]s from this [VersionMatrix] for the given keys."
        )
        add("")
        for (arg in args) {
          add("@param ${arg.valueName}Key the key for the ${arg.ordinalSuffix} parameter.")
        }
        add(
          "@return a list of [${types.kaseInterfaceNoTypes}]s from this [VersionMatrix] for the given keys."
        )
      }
        .makeKdoc()

      val typeParams = args.joinToString(
        separator = ",\n",
        prefix = "\n",
        postfix = "\n"
      ) { "  reified ${it.valueTypeName} : VersionMatrixElement<*>" }

      val params = args.joinToString(
        separator = ",\n",
        prefix = "\n",
        postfix = "\n"
      ) { "  ${it.valueName}Key: VersionMatrixKey<${it.valueTypeName}>" }

      appendLine(
        """
        |$kdoc
        |public inline fun <$typeParams> VersionMatrix.kases($params): List<${types.kaseInterface}> {
        |  return kases(${
          (1..ct).joinToString(separator = ",\n", prefix = "\n", postfix = "\n  ") {
            "    args$it = get(a${it}Key)"
          }
        })
        |}
        |
        """.trimMargin()
      )
    }
  }
    .fixBlankLines()

  kasesKt.writeText(gradle)
}
