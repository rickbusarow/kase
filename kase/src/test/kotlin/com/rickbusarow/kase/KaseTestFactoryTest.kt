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

import com.rickbusarow.kase.files.HasWorkingDir.Companion.baseWorkingDir
import com.rickbusarow.kase.files.HasWorkingDir.Companion.cleanStringForFileSystem
import com.rickbusarow.kase.files.TestFunctionCoordinates
import com.rickbusarow.kase.utils.ParamTypes.A
import com.rickbusarow.kase.utils.ParamTypes.B
import com.rickbusarow.kase.utils.ParamTypes.C
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory
import java.io.File
import java.util.stream.Stream

fun <A1, A2, B1> Kase2<A1, A2>.plus(other: Kase1<B1>): Kase3<A1, A2, B1> = kase(a1, a2, other.a1)
fun <A1, A2, A3, B1> Kase3<A1, A2, A3>.plus(other: Kase1<B1>): Kase4<A1, A2, A3, B1> =
  kase(a1, a2, a3, other.a1)

internal class KaseTestFactoryTest : KaseTestFactory<TestEnvironment, Kase2<A, B>> {
  override val kases: List<Kase2<A, B>>
    get() = kases(
      listOf(A("a1"), A("a2")),
      listOf(B("b1"), B("b2"))
    )

  val cs = kases(listOf(C("c1"), C("c2")))

  @TestFactory
  fun `scoping for multiplied streams`(): Stream<out DynamicNode> {
    val base = baseWorkingDir()

    val functionDir = File("KaseTestFactoryTest")
      .resolve(cleanStringForFileSystem("scoping for multiplied streams"))

    return kases.asContainers { k1 ->

      cs.asTests(
        testEnvironmentFactory = { k2 ->
          TestEnvironment(
            k1.displayName,
            k2.displayName,
            testFunctionCoordinates = testFunctionCoordinates
          )
        }
      ) { k2 ->

        val path = functionDir
          .resolve(cleanStringForFileSystem(k1.displayName))
          .resolve(cleanStringForFileSystem(k2.displayName))

        workingDir.relativeTo(base) shouldBe path
      }
    }
  }

  @TestFactory
  fun `scoping nested asTests`(): Stream<out DynamicNode> {
    val base = baseWorkingDir()

    val functionDir = File("KaseTestFactoryTest")
      .resolve(cleanStringForFileSystem("scoping nested asTests"))

    return cs.asContainers { k1 ->

      kases.asTests { k2 ->

        val path = functionDir
          .resolve(cleanStringForFileSystem(k1.displayName))
          .resolve(cleanStringForFileSystem(k2.displayName))

        workingDir.relativeTo(base) shouldBe path
      }
    }
  }

  @TestFactory
  fun `scoping nested testFactory`(): Stream<out DynamicNode> {
    val base = baseWorkingDir()

    val functionDir = File("KaseTestFactoryTest")
      .resolve(cleanStringForFileSystem("scoping nested testFactory"))

    return cs.asContainers { k1 ->

      testFactory { k2 ->

        val path = functionDir
          .resolve(cleanStringForFileSystem(k1.displayName))
          .resolve(cleanStringForFileSystem(k2.displayName))

        workingDir.relativeTo(base) shouldBe path
      }
    }
  }

  @Nested
  inner class `testFactory with custom environment` : KaseTestFactory<CustomTestEnvironment, Kase2<A, B>> {
    override val kases: List<Kase2<A, B>>
      get() = kases(
        listOf(A("a1"), A("a2")),
        listOf(B("b1"), B("b2"))
      )

    val cs = kases(listOf(C("c1"), C("c2")))

    override fun newTestEnvironment(
      kase: Kase2<A, B>,
      parentNames: List<String>,
      testFunctionCoordinates: TestFunctionCoordinates
    ): CustomTestEnvironment = CustomTestEnvironment(
      params = kase,
      testParameterDisplayNames = parentNames + kase.displayName,
      testFunctionCoordinates = testFunctionCoordinates
    )

    @TestFactory
    fun `scoping nested asTests`(): Stream<out DynamicNode> {
      val base = baseWorkingDir()

      val functionDir = File("KaseTestFactoryTest")
        .resolve(cleanStringForFileSystem("testFactory with custom environment"))
        .resolve(cleanStringForFileSystem("scoping nested asTests"))

      return cs.asContainers { k1 ->

        kases.asTests { k2 ->

          val path = functionDir
            .resolve(cleanStringForFileSystem(k1.displayName))
            .resolve(cleanStringForFileSystem(k2.displayName))

          workingDir.relativeTo(base) shouldBe path
        }
      }
    }
  }

  class CustomTestEnvironment(
    val params: Kase,
    testParameterDisplayNames: List<String>,
    testFunctionCoordinates: TestFunctionCoordinates
  ) : DefaultTestEnvironment(testParameterDisplayNames, testFunctionCoordinates)
}
