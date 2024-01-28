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
import java.io.Serializable

/**
 * Trait interface for providing a [TestEnvironmentFactory].
 *
 * @since 0.7.0
 */
public interface HasTestEnvironmentFactory<out FACT : TestEnvironmentFactory<*, *>> : KaseTests {

  /** @since 0.7.0 */
  public val testEnvironmentFactory: FACT
}

/**
 * Creates [TestEnvironment]s with a parameter.
 *
 * @since 0.9.0
 */
public fun interface ParamTestEnvironmentFactory<in PARAM, out ENV : TestEnvironment> :
  TestEnvironmentFactory<PARAM, ENV> {
  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [ENV].
   * @since 0.1.0
   */
  public fun create(params: PARAM, names: List<String>, location: TestLocation): ENV
}

/**
 * Transforms a [ParamTestEnvironmentFactory] into a [NoParamTestEnvironmentFactory],
 * in cases where the parameter is already provided. This is most useful when
 * the parameters for the environment are used to create dynamic containers.
 */
public fun <PARAM, ENV : TestEnvironment> ParamTestEnvironmentFactory<PARAM, ENV>.wrap(
  params: PARAM
): NoParamTestEnvironmentFactory<ENV> {
  return NoParamTestEnvironmentFactory { names, location ->
    this@wrap.create(params, names, location)
  }
}

/**
 * Creates [TestEnvironment]s with or without a parameter.
 *
 * @since 0.1.0
 */
public sealed interface TestEnvironmentFactory<in PARAM, out ENV : TestEnvironment> : Serializable

/**
 * Creates [TestEnvironment]s without a parameter.
 *
 * @since 0.9.0
 */
public fun interface NoParamTestEnvironmentFactory<out ENV : TestEnvironment> :
  TestEnvironmentFactory<Any, ENV> {
  /**
   * Creates a new [TestEnvironment].
   *
   * @return A new [TestEnvironment] of type [ENV].
   * @since 0.9.0
   */
  public fun create(names: List<String>, location: TestLocation): ENV
}
