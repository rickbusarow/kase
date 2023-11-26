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

import com.rickbusarow.kase.Kase
import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.Kase3
import com.rickbusarow.kase.Kase6
import com.rickbusarow.kase.KaseTestFactory
import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.gradle.VersionMatrix.Companion
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.TestFactory

class VersionMatrixElementKasesOverloadTest : KaseTestFactory<TestEnvironment, Kase> {

  override val kases: List<Kase>
    get() = error("not used")

  @TestFactory
  fun `kases creates Kase2 kases`() =
    versionMatrix(numVersionTypes = 6, versionsPerType = 2).kases(
      keyForTypeNumber(1),
      keyForTypeNumber(2)
    ).asTests { k ->

      k.shouldBeInstanceOf<Kase2<*, *>>()
    }

  @TestFactory
  fun `kases creates Kase3 kases`() =
    versionMatrix(numVersionTypes = 6, versionsPerType = 2).kases(
      keyForTypeNumber(1),
      keyForTypeNumber(2),
      keyForTypeNumber(3)
    ).asTests { k ->
      k.shouldBeInstanceOf<Kase3<*, *, *>>()
    }

  @TestFactory
  fun `kases creates Kase6 kases`() =
    versionMatrix(numVersionTypes = 6, versionsPerType = 2).kases(
      keyForTypeNumber(1),
      keyForTypeNumber(2),
      keyForTypeNumber(3),
      keyForTypeNumber(4),
      keyForTypeNumber(5),
      keyForTypeNumber(6)
    ).asTests { k ->
      k.shouldBeInstanceOf<Kase6<*, *, *, *, *, *>>()
    }

  fun keyForTypeNumber(typeNumber: Int): VersionMatrixKey<*> {

    return Class.forName("com.rickbusarow.kase.gradle.TestVersion$typeNumber")
      .getField("TestVersion${typeNumber}Key")
      .get(null) as VersionMatrixKey<*>
  }

  fun versionMatrix(
    numVersionTypes: Int,
    versionsPerType: Int = 3,
    valueFactory: (Int, Char) -> String = { type, versionChar -> "$type.$versionChar" }
  ): VersionMatrix {

    val elements =
      (1..numVersionTypes).flatMap { typeNumber ->
        (1..versionsPerType).map { versionNumber ->

          val versionChar = 'a' + versionNumber - 1

          val value = valueFactory(typeNumber, versionChar)

          Class.forName("com.rickbusarow.kase.gradle.TestVersion$typeNumber")
            .declaredConstructors
            .single()
            .newInstance(value) as AbstractDependencyVersion<*, *, *>
        }
      }
    return Companion(elements)
  }
}
