package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.AnyKase
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.TestEnvironmentFactory
import com.rickbusarow.kase.TestVariant
import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.gradle.generation.BuildFileComponents
import com.rickbusarow.kase.gradle.generation.model.DslLanguage
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import java.io.File
import java.util.stream.Stream

/** A base class for Gradle plugin tests. */
@Execution(ExecutionMode.SAME_THREAD)
public interface BaseGradleTest<V> :
  GradleTestEnvironmentFactory<V>,
  HasVersionMatrix<V>,
  KaseTestFactory<GradleTestEnvironment<V>, V>
  where V : TestVersions,
        V : AnyKase

/** Trait interface for a test class with default [kases] */
public interface HasKases<K : AnyKase> {
  /** The default kases for tests in this class. */
  public val kases: List<K>
}

/**
 * Common interface for creating dynamic tests with predefined
 * [kases][HasKases.kases] and a unique [TestEnvironment]
 */
public interface KaseTestFactory<T : TestEnvironment, K : AnyKase> :
  HasVersionMatrix<K>,
  HasKases<K>,
  TestEnvironmentFactory<T, K> {

  /** Creates a stream of tests from [kases] */
  public fun testFactory(
    testAction: T.(K) -> Unit
  ): Stream<out DynamicNode> = com.rickbusarow.kase.testFactory {
    kases.asTests {
      newTestEnvironment(it, testFunctionCoordinates).testAction(it)
    }
  }
}

/** A factory for creating [GradleTestEnvironment]s. */
public interface GradleTestEnvironmentFactory<TV> :
  TestEnvironmentFactory<GradleTestEnvironment<TV>, TV>
  where TV : TestVersions,
        TV : AnyKase {

  public val localM2Path: File

  /** Creates a new [GradleTestEnvironment] for the given [testVariant] and [testVersions]. */
  public fun newTestEnvironment(
    testVariant: TestVariant<TV>,
    testVersions: TV
  ): GradleTestEnvironment<TV> = newTestEnvironment(
    kase = testVersions,
    testFunctionCoordinates = testVariant.testFunctionCoordinates
  )

  override fun newTestEnvironment(
    kase: TV,
    testFunctionCoordinates: TestFunctionCoordinates
  ): GradleTestEnvironment<TV> = GradleTestEnvironment(
    testVersions = kase,
    testFunctionCoordinates = testFunctionCoordinates,
    kase = kase,
    buildFileComponents = object : BuildFileComponents {},
    dslLanguage = DslLanguage.GroovyDsl(useInfix = true, useLabels = true, useDoubleQuotes = false),
    localM2Path = localM2Path
  )
}
