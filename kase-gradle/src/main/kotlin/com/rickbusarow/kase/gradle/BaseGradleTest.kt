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

import com.rickbusarow.kase.AnyKase
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.TestEnvironmentFactory
import com.rickbusarow.kase.TestVariant
import com.rickbusarow.kase.files.TestFunctionCoordinates
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import java.io.File

/** A base class for Gradle plugin tests. */
@Execution(ExecutionMode.SAME_THREAD)
public interface BaseGradleTest<K> :
  GradleTestEnvironmentFactory<K>,
  HasVersionMatrix<K>,
  KaseTestFactory<GradleTestEnvironment<K>, K>
  where K : TestVersions,
        K : AnyKase

/** A factory for creating [GradleTestEnvironment]s. */
public interface GradleTestEnvironmentFactory<K> :
  TestEnvironmentFactory<GradleTestEnvironment<K>, K>
  where K : TestVersions,
        K : AnyKase {

  /** The [DslLanguage] to use for generating build and settings files. */
  public val dslLanguage: DslLanguage
    get() = DslLanguage.KotlinDsl(useInfix = true, useLabels = false)

  /**
   * A local Maven repository to use for resolving
   * dependencies, such as `<projectRoot>/build/m2` or `~/.m2`.
   */
  public val localM2Path: File? get() = null

  /** Creates a new [GradleTestEnvironment] for the given [testVariant] and [testVersions]. */
  public fun newTestEnvironment(
    testVariant: TestVariant<K>,
    testVersions: K
  ): GradleTestEnvironment<K> = newTestEnvironment(
    kase = testVersions,
    testFunctionCoordinates = testVariant.testFunctionCoordinates
  )

  override fun newTestEnvironment(
    kase: K,
    testFunctionCoordinates: TestFunctionCoordinates
  ): GradleTestEnvironment<K> = GradleTestEnvironment(
    testVersions = kase,
    testFunctionCoordinates = testFunctionCoordinates,
    kase = kase,

    dslLanguage = DslLanguage.GroovyDsl(useInfix = true, useLabels = true, useDoubleQuotes = false),
    localM2Path = localM2Path
  )
}
