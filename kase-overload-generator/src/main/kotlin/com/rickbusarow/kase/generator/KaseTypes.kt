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

package com.rickbusarow.kase.generator

import com.rickbusarow.kase.generator.KaseArg.Companion.valueTypesString

internal data class KaseTypes(val number: Int, val args: List<KaseArg>) {

  private val argsValueTypesString: String by lazy { args.valueTypesString }

  /** `Kase3` */
  val kaseInterfaceNoTypes = "Kase$number"

  /** `Kase3<A1, A2, A3>` */
  val kaseInterface: String = "$kaseInterfaceNoTypes<$argsValueTypesString>"

  /** `Kase2<A1, A2>` in `Kase3<A1, A2, A3> : Kase2<A1, A2>` */
  val kaseSuperInterface: String = if (number == 1) {
    "Kase"
  } else {
    KaseTypes(number - 1, args.dropLast(1)).kaseInterface
  }

  /** `defaultKase2DisplayNameFactory` */
  val defaultDisplayNameFactory: String = "default${kaseInterfaceNoTypes}DisplayNameFactory"

  /** `KaseDisplayNameFactory<Kase2<A1, A2>>` */
  val displayNameFactory: String = "KaseDisplayNameFactory<$kaseInterface>"

  /** `DefaultKase3` */
  val defaultKaseNoTypes = "DefaultKase$number"

  /** `DefaultKase3<A1, A2, A3>` */
  val defaultKase = "DefaultKase$number<$argsValueTypesString>"

  /** `KaseLabels3` */
  val kaseLabels = "KaseLabels$number"

  /** `TestEnvironment` */
  val testEnvironment = "TestEnvironment"

  /** `TestEnvironmentFactory<TestEnvironment, Kase3<A1, A2, A3>>` */
  fun testEnvironmentFactory(environmentType: String) = "TestEnvironmentFactory<$environmentType>"

  /** `KaseParameterWithLabel<A1, A2, A3>` */
  fun kaseParameterWithLabel(argValueType: String) = "KaseParameterWithLabel<$argValueType>"
}
