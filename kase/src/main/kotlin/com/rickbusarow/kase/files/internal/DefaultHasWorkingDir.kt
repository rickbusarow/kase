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

package com.rickbusarow.kase.files.internal

import com.rickbusarow.kase.files.HasWorkingDir
import org.junit.jupiter.api.TestFactory
import java.io.File

/**
 * @param workingDir the directory containing all source and generated files to be used in this test
 */
public open class DefaultHasWorkingDir(workingDir: File) : HasWorkingDir {

  /** the directory containing all source and generated files to be used in this test */
  override val workingDir: File by lazy {
    checkInWorkingDir(workingDir)
    workingDir.deleteRecursively()
    workingDir
  }

  override fun toString(): String = "workingDir=$workingDir\n" + getSourceReport()

  public companion object {

    private val allWorkingDirs = mutableSetOf<File>()

    @PublishedApi
    internal fun checkInWorkingDir(workingDir: File) {
      synchronized(allWorkingDirs) {
        require(allWorkingDirs.add(workingDir)) {
          val annotation = "${TestFactory::class.simpleName}"
          """
          A working directory with this path has already been registered during this test run,
          meaning it would have multiple tests modifying it concurrently.

          This probably means you need to add test variant names to your TestEnvironment instance.

            @$annotation
            fun myTestFactory(
              @Language("proto")
              vararg content: String,
              /* ... /*
            ) = ...

          This is the working directory which would be duplicated: $workingDir
          """.trimIndent()
        }
      }
    }
  }
}

private fun HasWorkingDir.getSourceReport(): String {

  val grouped = workingDir
    .walkBottomUp()
    .filter { it.isFile }
    .groupBy { generated ->
      val pathSegments = generated.relativeTo(workingDir).path.split(File.separator)

      if (pathSegments.size > 1) {
        pathSegments.first()
      } else {
        "<root>"
      }
    }
    .toList()
    .sortedBy { it.first }

  return buildString {
    appendLine("====")
    grouped.forEach { (type, files) ->
      appendLine("## $type")
      files.forEach { path ->
        appendLine("file://$path")
      }
    }
    appendLine("----")
  }
}
