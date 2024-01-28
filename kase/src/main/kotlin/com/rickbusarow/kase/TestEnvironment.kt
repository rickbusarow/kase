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
import com.rickbusarow.kase.files.TestLocation
import java.io.Closeable

/**
 * Represents a hermetic testing environment that may
 * be cleaned up automatically after test execution.
 *
 * @since 0.1.0
 */
public interface TestEnvironment : HasWorkingDir, Closeable {

  /**
   * Performs any necessary cleanup after the test has run.
   *
   * @since 0.1.0
   */
  @Deprecated(
    "renamed to `close` with the addition of the `Closable` implementation",
    ReplaceWith("close()")
  )
  public fun tearDown(): Unit = close()

  /** Performs any necessary cleanup after the test has run. */
  override fun close(): Unit = Unit

  public companion object {
    /**
     * Creates a new [TestEnvironment] instance.
     *
     * @param testParameterDisplayNames The display names of the test parameters, if any.
     * @param testLocation The [TestLocation] from which the test is being run.
     * @return A new [TestEnvironment] instance.
     * @see TestEnvironmentFactory
     * @see DefaultTestEnvironment
     * @since 0.1.0
     */
    public operator fun invoke(
      testParameterDisplayNames: List<String>,
      testLocation: TestLocation = TestLocation.get()
    ): TestEnvironment = DefaultTestEnvironment(
      names = testParameterDisplayNames,
      testLocation = testLocation
    )

    /**
     * Creates a new [TestEnvironment] instance.
     *
     * @param testParameterDisplayName The display name of the first test parameter
     * @param additionalNames optional additional names
     * @param testLocation The [TestLocation] from which the test is being run.
     * @return A new [TestEnvironment] instance.
     * @see TestEnvironmentFactory
     * @see DefaultTestEnvironment
     * @since 0.1.0
     */
    public operator fun invoke(
      testParameterDisplayName: String,
      vararg additionalNames: String,
      testLocation: TestLocation = TestLocation.get()
    ): TestEnvironment = DefaultTestEnvironment(
      names = listOf(testParameterDisplayName) + additionalNames,
      testLocation = testLocation
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
   * @param names The display names of the test parameters, if any.
   * @param testLocation The [TestLocation] from which the test is being run.
   * @since 0.1.0
   */
  public constructor(
    names: List<String>,
    testLocation: TestLocation = TestLocation.get()
  ) : this(
    HasWorkingDir(
      testVariantNames = names,
      testLocation = testLocation
    )
  )

  override fun toString(): String = hasWorkingDir.toString()

  /**
   * Creates a [DefaultTestEnvironment]
   *
   * @since 0.7.0
   */
  public class Factory : NoParamTestEnvironmentFactory<DefaultTestEnvironment> {

    /** @since 0.7.0 */
    override fun create(
      names: List<String>,
      location: TestLocation
    ): DefaultTestEnvironment = DefaultTestEnvironment(
      names = names,
      testLocation = location
    )
  }
}
