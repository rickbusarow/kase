/*
 * Copyright (C) 2024 Rick Busarow
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

/**
 * Represents a hermetic testing environment that may
 * be cleaned up automatically after test execution.
 *
 * @since 0.1.0
 */
public interface TestEnvironment : HasWorkingDir {

  /**
   * Performs any necessary cleanup after the test has run.
   *
   * @since 0.1.0
   */
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
     * @since 0.1.0
     */
    public operator fun invoke(
      testParameterDisplayNames: List<String>,
      testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
    ): TestEnvironment = DefaultTestEnvironment(
      testParameterDisplayNames = testParameterDisplayNames,
      testFunctionCoordinates = testFunctionCoordinates
    )

    /**
     * Creates a new [TestEnvironment] instance.
     *
     * @param testParameterDisplayNames The display names of the test parameters, if any.
     * @param testFunctionCoordinates The [TestFunctionCoordinates]
     *   from which the test is being run.
     * @return A new [TestEnvironment] instance.
     * @see TestEnvironmentFactory
     * @see DefaultTestEnvironment
     * @since 0.1.0
     */
    public operator fun invoke(
      vararg testParameterDisplayNames: String,
      testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
    ): TestEnvironment = DefaultTestEnvironment(
      testParameterDisplayNames = testParameterDisplayNames.toList(),
      testFunctionCoordinates = testFunctionCoordinates
    )
  }
}

/**
 * Represents a hermetic testing environment with an
 * associated working directory and certain assertions.
 *
 * @param hasWorkingDir The associated working directory.
 * @since 0.1.0
 */
public open class DefaultTestEnvironment(
  private val hasWorkingDir: HasWorkingDir
) : TestEnvironment,
  HasWorkingDir by hasWorkingDir {

  /**
   * Represents a hermetic testing environment with an
   * associated working directory and certain assertions.
   *
   * @param testParameterDisplayNames The display names of the test parameters, if any.
   * @param testFunctionCoordinates The [TestFunctionCoordinates] from which the test is being run.
   * @since 0.1.0
   */
  public constructor(
    testParameterDisplayNames: List<String>,
    testFunctionCoordinates: TestFunctionCoordinates = TestFunctionCoordinates.get()
  ) : this(
    HasWorkingDir(
      testVariantNames = testParameterDisplayNames,
      testFunctionCoordinates = testFunctionCoordinates
    )
  )

  override fun toString(): String = hasWorkingDir.toString()
}
