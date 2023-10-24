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

import com.rickbusarow.kase.files.HasWorkingDir
import com.rickbusarow.kase.files.TestFunctionCoordinates
import dev.drewhamilton.poko.Poko

/**
 * Represents a hermetic testing environment that may
 * be cleaned up automatically after test execution.
 */
public interface TestEnvironment : HasWorkingDir {

  /** Performs any necessary cleanup after the test has run. */
  public fun tearDown() {}

  public companion object {
    /**
     * Creates a new [TestEnvironment] instance.
     *
     * @param testParameterDisplayNames The display names of the test parameters, if any.
     * @param testFunctionCoordinates The [TestFunctionCoordinates]
     *   from which the test is being run.
     * @return A new [TestEnvironment] instance.
     * @see TestEnvironmentFactory
     * @see DefaultTestEnvironment
     */
    public operator fun invoke(
      testParameterDisplayNames: List<String>,
      testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
    ): TestEnvironment {
      return DefaultTestEnvironment(
        testParameterDisplayNames = testParameterDisplayNames,
        testFunctionCoordinates = testFunctionCoordinates
      )
    }
  }
}

/**
 * Represents a hermetic testing environment with an
 * associated working directory and certain assertions.
 *
 * @param testParameterDisplayNames The display names of the test parameters, if any.
 * @param testFunctionCoordinates The [TestFunctionCoordinates] from which the test is being run.
 */
public open class DefaultTestEnvironment(
  testParameterDisplayNames: List<String>,
  testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
) : TestEnvironment,
  HasWorkingDir by HasWorkingDir(
    testVariantNames = testParameterDisplayNames,
    testFunctionCoordinates = testFunctionCoordinates
  )

/**
 * Represents a specific instance of a test case invocation with a specific set of parameters.
 *
 * @property kase The parameters for the test.
 * @property testFunctionCoordinates The [TestFunctionCoordinates] from
 *   which the test is being run. Defaults to the current stack frame.
 */
@Poko
public class TestVariant<K : Kase>(
  public val kase: K,
  public val testFunctionCoordinates: TestFunctionCoordinates
)
