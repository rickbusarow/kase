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

import com.rickbusarow.kase.stdlib.cleanOutput
import com.rickbusarow.kase.stdlib.useRelativePaths

/**
 * Represents a hermetic testing environment with an
 * associated working directory and certain assertions.
 *
 * @param testFunctionName The [TestFunctionName] from which the test is being run.
 * @param testVariantNames The variant names related to the test.
 */
open class TestEnvironment<T: TestEnvironmentParams>(
  testFunctionName: TestFunctionName = TestFunctionName.get(),
  testVariantNames: List<String>
) : HasWorkingDir(createWorkingDir(testFunctionName, testVariantNames)) {

  /**
   * replace absolute paths with relative ones
   */
  fun String.useRelativePaths(): String = useRelativePaths(workingDir)

  /**
   * Cleans the provided string in the context of the [TestEnvironment]'s working directory.
   *
   * @receiver The raw string that needs to be cleaned.
   * @return The cleaned string.
   */
  fun String.cleanOutput(): String = cleanOutput(workingDir)
}

/**
 * @property testFunctionName The [StackWalker.StackFrame] from which the test is being run.
 * @property testVariantNames The variant names related to the test.
 */
data class DefaultTestEnvironmentParams(
  override val testFunctionName: TestFunctionName,
  override val testVariantNames: List<String>
) : TestEnvironmentParams

/** */
interface TestEnvironmentParams : TestVariant

/** Represents a specific instance of a test case invocation */
interface TestVariant {
  /** The [TestFunctionName] from which the test is being run. Defaults to the current stack frame if not provided. */
  val testFunctionName: TestFunctionName

  /** The variant name(s) related to the test. The first name corresponds to a subdirectory under the directory derived from [testStackFrame]. Each additional name corresponds to a subdirectory inside its predecessor. */
  val testVariantNames: List<String>
}
