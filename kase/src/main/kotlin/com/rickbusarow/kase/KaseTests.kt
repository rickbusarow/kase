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

import com.rickbusarow.kase.files.TestLocation
import kotlinx.coroutines.runBlocking

/**
 * Holds some single-shot `test { }` functions, with no-param versions for
 * no-param factories. Add this as a super-interface to any test class.
 */
public interface KaseTests {

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param names The values of variables related to the test.
   * @param testLocation The class, test function name, and source code location for this test.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun <ENV : TestEnvironment> HasTestEnvironmentFactory<NoParamTestEnvironmentFactory<ENV>>.test(
    names: List<String> = emptyList(),
    testLocation: TestLocation = TestLocation.get(),
    testAction: suspend ENV.() -> Unit
  ) {
    test(
      testEnvironmentFactory = testEnvironmentFactory,
      names = names,
      testLocation = testLocation,
      testAction = testAction
    )
  }

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param param Additional variables for this test case
   * @param names The values of variables related to the test.
   * @param testLocation The class, test function name, and source code location for this test.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun <PARAM, ENV : TestEnvironment> HasTestEnvironmentFactory<ParamTestEnvironmentFactory<PARAM, ENV>>.test(
    param: PARAM,
    names: List<String> = emptyList(),
    testLocation: TestLocation = TestLocation.get(),
    testAction: suspend ENV.() -> Unit
  ) {
    test(
      param = param,
      testEnvironmentFactory = testEnvironmentFactory,
      names = names,
      testLocation = testLocation,
      testAction = testAction
    )
  }

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param testEnvironmentFactory A factory which creates a new [TestEnvironment] for this test.
   * @param names The values of variables related to the test.
   * @param testLocation The class, test function name, and source code location for this test.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun <ENV : TestEnvironment> test(
    testEnvironmentFactory: NoParamTestEnvironmentFactory<ENV>,
    names: List<String> = emptyList(),
    testLocation: TestLocation = TestLocation.get(),
    testAction: suspend ENV.() -> Unit
  ) {
    test(
      param = Unit,
      testEnvironmentFactory = testEnvironmentFactory,
      names = names,
      testLocation = testLocation,
      testAction = testAction
    )
  }

  /**
   * Runs the provided test [testAction] in the context of a new [TestEnvironment].
   *
   * @param param Additional variables for this test case
   * @param testEnvironmentFactory A factory which creates a new [TestEnvironment] for this test.
   * @param names The values of variables related to the test.
   * @param testLocation The class, test function name, and source code location for this test.
   * @param testAction The test action to run within the [TestEnvironment].
   */
  public fun <PARAM, ENV : TestEnvironment> test(
    param: PARAM,
    testEnvironmentFactory: TestEnvironmentFactory<PARAM, ENV>,
    names: List<String> = emptyList(),
    testLocation: TestLocation = TestLocation.get(),
    testAction: suspend ENV.() -> Unit
  ) {
    val testEnvironment = when (testEnvironmentFactory) {
      is NoParamTestEnvironmentFactory -> testEnvironmentFactory.create(
        names = names,
        location = testLocation
      )

      is ParamTestEnvironmentFactory -> testEnvironmentFactory.create(
        params = param,
        names = names,
        location = testLocation
      )
    }
    runBlocking {
      testEnvironment.asClueCatching {
        testEnvironment.testAction()
        println(testEnvironment)
      }
    }
  }
}
