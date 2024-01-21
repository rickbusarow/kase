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

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.DefaultTestEnvironment
import com.rickbusarow.kase.HasTestEnvironmentFactory
import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.Kase3
import com.rickbusarow.kase.Kase6
import com.rickbusarow.kase.KaseMatrix
import com.rickbusarow.kase.KaseMatrix.KaseMatrixKey
import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.kases
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.TestFactory

class KaseMatrixElementKasesOverloadTest : HasTestEnvironmentFactory<DefaultTestEnvironment.Factory> {

  override val testEnvironmentFactory = DefaultTestEnvironment.Factory()

  @TestFactory
  fun `kases creates Kase2 kases`() =
    KaseMatrix(numVersionTypes = 6, versionsPerType = 2).kases(
      keyForTypeNumber(1),
      keyForTypeNumber(2)
    ).asTests { k ->

      k.shouldBeInstanceOf<Kase2<*, *>>()
    }

  @TestFactory
  fun `kases creates Kase3 kases`() =
    KaseMatrix(numVersionTypes = 6, versionsPerType = 2).kases(
      keyForTypeNumber(1),
      keyForTypeNumber(2),
      keyForTypeNumber(3)
    ).asTests { k ->
      k.shouldBeInstanceOf<Kase3<*, *, *>>()
    }

  @TestFactory
  fun `kases creates Kase6 kases`() =
    KaseMatrix(numVersionTypes = 6, versionsPerType = 2).kases(
      keyForTypeNumber(1),
      keyForTypeNumber(2),
      keyForTypeNumber(3),
      keyForTypeNumber(4),
      keyForTypeNumber(5),
      keyForTypeNumber(6)
    ).asTests { k ->
      k.shouldBeInstanceOf<Kase6<*, *, *, *, *, *>>()
    }

  fun keyForTypeNumber(typeNumber: Int): KaseMatrixKey<*> {

    return Class.forName("com.rickbusarow.kase.gradle.TestElement$typeNumber")
      .getField("TestElement${typeNumber}Key")
      .get(null) as KaseMatrixKey<*>
  }

  fun KaseMatrix(
    numVersionTypes: Int,
    versionsPerType: Int = 3,
    valueFactory: (Int, Char) -> String = { type, versionChar -> "$type.$versionChar" }
  ): KaseMatrix {

    val elements =
      (1..numVersionTypes).flatMap { typeNumber ->
        (1..versionsPerType).map { versionNumber ->

          val versionChar = 'a' + versionNumber - 1

          val value = valueFactory(typeNumber, versionChar)

          Class.forName("com.rickbusarow.kase.gradle.TestElement$typeNumber")
            .declaredConstructors
            .single()
            .newInstance(value) as AbstractDependencyVersion<*, *>
        }
      }
    return KaseMatrix.invoke(elements)
  }
}
