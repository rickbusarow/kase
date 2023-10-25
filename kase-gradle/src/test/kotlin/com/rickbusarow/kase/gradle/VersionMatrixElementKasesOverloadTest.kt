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

import com.rickbusarow.kase.TestEnvironment
import com.rickbusarow.kase.TestEnvironmentFactory
import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.gradle.VersionMatrix.Companion
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
import com.rickbusarow.kase.stdlib.createSafely
import com.rickbusarow.kase.stdlib.toStringPretty
import org.junit.jupiter.api.TestFactory

class VersionMatrixElementKasesOverloadTest : TestEnvironmentFactory<TestEnvironment> {

  @TestFactory
  fun `kases2 creates Kase2 kases`() =
    versionMatrix(numVersionTypes = 6, versionsPerType = 4)
      .kases(
        keyForTypeNumber(1),
        keyForTypeNumber(2),
        keyForTypeNumber(3),
        keyForTypeNumber(4),
        keyForTypeNumber(5),
        keyForTypeNumber(6)
      ).asTests { k ->
        workingDir.resolve(k.hashCode().toString()).createSafely(k.toStringPretty())
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
