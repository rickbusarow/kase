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

package com.rickbusarow.kase

import com.rickbusarow.kase.stdlib.div
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.file.shouldNotExist
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

class HasWorkingDirTest {

  @Test
  fun `workingDir should delete recursively and return the directory`() {
    val tempDir = createTempDir()
    val file = File(tempDir, "testFile").apply { createNewFile() }

    val hasWorkingDir = object : HasWorkingDir(tempDir) {}

    hasWorkingDir.workingDir shouldBe tempDir
    file.shouldNotExist()
  }

  @Test
  fun `relativePath should return relative path to workingDir`() {
    val tempDir = createTempDir()

    val file = tempDir / "subDir/testFile"

    val hasWorkingDir = object : HasWorkingDir(tempDir) {}

    with(hasWorkingDir) {
      file.relativePath() shouldBe "subDir/testFile"
    }
  }

  @Test
  fun `checkInWorkingDir should throw exception if workingDir is already registered`() {
    val tempDir = createTempDir()

    HasWorkingDir.checkInWorkingDir(tempDir)

    val exception = shouldThrow<IllegalArgumentException> {
      HasWorkingDir.checkInWorkingDir(tempDir)
    }

    exception.message.shouldBeInstanceOf<String>()
  }

  @Test
  fun `createWorkingDir should create appropriate working directory`() {
    val testVariantNames = listOf("variant1", "variant2")
    val testFunctionCoordinates = TestFunctionCoordinates.get()

    val workingDir = HasWorkingDir.createWorkingDir(testVariantNames, testFunctionCoordinates)

    val thisFun = "HasWorkingDirTest/createWorkingDir_should_create_appropriate_working_directory"
    val expectedPath = "build/kase/$thisFun/variant1/variant2"

    val projectRoot = File("").absoluteFile

    workingDir.toRelativeString(projectRoot) shouldBe expectedPath
  }
}

internal fun createTempDir() = Files.createTempDirectory("temp").toFile()
