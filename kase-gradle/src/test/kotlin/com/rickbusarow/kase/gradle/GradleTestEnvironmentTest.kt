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

package com.rickbusarow.kase.gradle

import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestFactory

class GradleTestEnvironmentTest : KaseGradleTest<TestVersions> {
  override val versionMatrix: VersionMatrix
    get() = VersionMatrix(GradleDependencyVersion("1.0.0"))
  override val kases: List<TestVersions>
    get() = versionMatrix.versions(GradleTestVersions)

  @TestFactory
  fun `invoking rootProject as a lambda writes the files afterwards`() = testFactory {

    rootProject {
      dir("nested") {
        file("file1.txt", "file1")
      }
      dir("nested") {
        file("file2.txt", "file2")
      }
    }

    workingDir.resolve("nested/file1.txt").shouldExist()
    workingDir.resolve("nested/file1.txt").readText() shouldBe "file1"
    workingDir.resolve("nested/file2.txt").shouldExist()
    workingDir.resolve("nested/file2.txt").readText() shouldBe "file2"
  }
}
