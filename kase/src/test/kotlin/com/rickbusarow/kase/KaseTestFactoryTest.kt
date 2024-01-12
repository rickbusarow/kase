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

import com.rickbusarow.kase.ParamTypes.A
import com.rickbusarow.kase.ParamTypes.B
import com.rickbusarow.kase.ParamTypes.C
import com.rickbusarow.kase.files.HasWorkingDir.Companion.baseWorkingDir
import com.rickbusarow.kase.files.HasWorkingDir.Companion.cleanStringForFileSystem
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.TestFactory
import java.io.File
import java.util.stream.Stream

fun <A1, A2, B1> Kase2<A1, A2>.plus(other: Kase1<B1>): Kase3<A1, A2, B1> = kase(a1, a2, other.a1)
fun <A1, A2, A3, B1> Kase3<A1, A2, A3>.plus(other: Kase1<B1>): Kase4<A1, A2, A3, B1> =
  kase(a1, a2, a3, other.a1)

class KaseTestFactoryTest : KaseTestFactory<TestEnvironment, Kase2<A, B>> {
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
            testLocation = testLocation
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
      .resolve(cleanStringForFileSystem("scoping nested asTests"))

    return cs.asContainers { k1 ->

      testFactory { k2 ->

        val path = functionDir
          .resolve(cleanStringForFileSystem(k1.displayName))
          .resolve(cleanStringForFileSystem(k2.displayName))

        workingDir.relativeTo(base) shouldBe path
      }
    }
  }
}

sealed class ParamTypes {
  abstract val name: String

  override fun toString() = name
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ParamTypes) return false

    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }

  class A(override val name: String) : ParamTypes()
  class B(override val name: String) : ParamTypes()
  class C(override val name: String) : ParamTypes()
  class D(override val name: String) : ParamTypes()
  class E(override val name: String) : ParamTypes()
  class F(override val name: String) : ParamTypes()
  class G(override val name: String) : ParamTypes()
  class H(override val name: String) : ParamTypes()
  class I(override val name: String) : ParamTypes()
  class J(override val name: String) : ParamTypes()
  class K(override val name: String) : ParamTypes()
  class L(override val name: String) : ParamTypes()
  class M(override val name: String) : ParamTypes()
  class N(override val name: String) : ParamTypes()
  class O(override val name: String) : ParamTypes()
  class P(override val name: String) : ParamTypes()
  class Q(override val name: String) : ParamTypes()
  class R(override val name: String) : ParamTypes()
  class S(override val name: String) : ParamTypes()
  class T(override val name: String) : ParamTypes()
  class U(override val name: String) : ParamTypes()
  class V(override val name: String) : ParamTypes()
  class W(override val name: String) : ParamTypes()
  class X(override val name: String) : ParamTypes()
  class Y(override val name: String) : ParamTypes()
  class Z(override val name: String) : ParamTypes()
}
