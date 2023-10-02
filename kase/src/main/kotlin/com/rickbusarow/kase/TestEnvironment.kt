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
 */
public open class TestEnvironment<T : AnyKase>(
  public val kase: T,
  testFunctionName: TestFunctionName = TestFunctionName.get()
) : HasWorkingDir(
  createWorkingDir(
    testVariantNames = kase.displayNames(),
    testFunctionName = testFunctionName
  )
) {

  /** replace absolute paths with relative ones */
  public fun String.useRelativePaths(): String = useRelativePaths(workingDir)

  /**
   * Cleans the provided string in the context of the [TestEnvironment]'s working directory.
   *
   * @receiver The raw string that needs to be cleaned.
   * @return The cleaned string.
   */
  public fun String.cleanOutput(): String = cleanOutput(workingDir)
}

/** */
public data class DefaultTestEnvironmentParams(
  override val testVariant: TestVariant
) : HasTestVariant

/** */
public interface HasTestVariant {
  public val testVariant: TestVariant
}

/** Represents a specific instance of a test case invocation */
public interface TestVariant {

  /** The parameters for the test. */
  public val kase: AnyKase

  /**
   * The [TestFunctionName] from which the test is being run.
   * Defaults to the current stack frame if not provided.
   */
  public val testFunctionName: TestFunctionName
}
