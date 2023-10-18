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

@file:Suppress("PackageDirectoryMismatch", "DuplicatedCode", "MaxLineLength")
@file:JvmMultifileClass
@file:JvmName("KasesKt")

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.Kase10
import com.rickbusarow.kase.Kase11
import com.rickbusarow.kase.Kase12
import com.rickbusarow.kase.Kase13
import com.rickbusarow.kase.Kase14
import com.rickbusarow.kase.Kase15
import com.rickbusarow.kase.Kase16
import com.rickbusarow.kase.Kase17
import com.rickbusarow.kase.Kase18
import com.rickbusarow.kase.Kase19
import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.Kase20
import com.rickbusarow.kase.Kase21
import com.rickbusarow.kase.Kase22
import com.rickbusarow.kase.Kase3
import com.rickbusarow.kase.Kase4
import com.rickbusarow.kase.Kase5
import com.rickbusarow.kase.Kase6
import com.rickbusarow.kase.Kase7
import com.rickbusarow.kase.Kase8
import com.rickbusarow.kase.Kase9
import com.rickbusarow.kase.gradle.VersionsMatrix.Element
import com.rickbusarow.kase.kases

/** */
public inline fun <
  reified A1 : Element
  > VersionsMatrix.kases1(): List<Kase1<A1>> {
  return kases(
    args1 = get(A1::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element
  > VersionsMatrix.kases2(): List<Kase2<A1, A2>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element
  > VersionsMatrix.kases3(): List<Kase3<A1, A2, A3>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element
  > VersionsMatrix.kases4(): List<Kase4<A1, A2, A3, A4>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element
  > VersionsMatrix.kases5(): List<Kase5<A1, A2, A3, A4, A5>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element
  > VersionsMatrix.kases6(): List<Kase6<A1, A2, A3, A4, A5, A6>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element
  > VersionsMatrix.kases7(): List<Kase7<A1, A2, A3, A4, A5, A6, A7>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element
  > VersionsMatrix.kases8(): List<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element
  > VersionsMatrix.kases9(): List<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element
  > VersionsMatrix.kases10(): List<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element
  > VersionsMatrix.kases11(): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element
  > VersionsMatrix.kases12(): List<Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element
  > VersionsMatrix.kases13(): List<Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element
  > VersionsMatrix.kases14(): List<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element
  > VersionsMatrix.kases15(): List<Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element,
  reified A16 : Element
  > VersionsMatrix.kases16(): List<Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class),
    args16 = get(A16::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element,
  reified A16 : Element,
  reified A17 : Element
  > VersionsMatrix.kases17(): List<Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class),
    args16 = get(A16::class),
    args17 = get(A17::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element,
  reified A16 : Element,
  reified A17 : Element,
  reified A18 : Element
  > VersionsMatrix.kases18(): List<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class),
    args16 = get(A16::class),
    args17 = get(A17::class),
    args18 = get(A18::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element,
  reified A16 : Element,
  reified A17 : Element,
  reified A18 : Element,
  reified A19 : Element
  > VersionsMatrix.kases19(): List<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class),
    args16 = get(A16::class),
    args17 = get(A17::class),
    args18 = get(A18::class),
    args19 = get(A19::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element,
  reified A16 : Element,
  reified A17 : Element,
  reified A18 : Element,
  reified A19 : Element,
  reified A20 : Element
  > VersionsMatrix.kases20(): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class),
    args16 = get(A16::class),
    args17 = get(A17::class),
    args18 = get(A18::class),
    args19 = get(A19::class),
    args20 = get(A20::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element,
  reified A16 : Element,
  reified A17 : Element,
  reified A18 : Element,
  reified A19 : Element,
  reified A20 : Element,
  reified A21 : Element
  > VersionsMatrix.kases21(): List<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class),
    args16 = get(A16::class),
    args17 = get(A17::class),
    args18 = get(A18::class),
    args19 = get(A19::class),
    args20 = get(A20::class),
    args21 = get(A21::class)
  )
}

/** */
public inline fun <
  reified A1 : Element,
  reified A2 : Element,
  reified A3 : Element,
  reified A4 : Element,
  reified A5 : Element,
  reified A6 : Element,
  reified A7 : Element,
  reified A8 : Element,
  reified A9 : Element,
  reified A10 : Element,
  reified A11 : Element,
  reified A12 : Element,
  reified A13 : Element,
  reified A14 : Element,
  reified A15 : Element,
  reified A16 : Element,
  reified A17 : Element,
  reified A18 : Element,
  reified A19 : Element,
  reified A20 : Element,
  reified A21 : Element,
  reified A22 : Element
  > VersionsMatrix.kases22(): List<Kase22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22>> {
  return kases(
    args1 = get(A1::class),
    args2 = get(A2::class),
    args3 = get(A3::class),
    args4 = get(A4::class),
    args5 = get(A5::class),
    args6 = get(A6::class),
    args7 = get(A7::class),
    args8 = get(A8::class),
    args9 = get(A9::class),
    args10 = get(A10::class),
    args11 = get(A11::class),
    args12 = get(A12::class),
    args13 = get(A13::class),
    args14 = get(A14::class),
    args15 = get(A15::class),
    args16 = get(A16::class),
    args17 = get(A17::class),
    args18 = get(A18::class),
    args19 = get(A19::class),
    args20 = get(A20::class),
    args21 = get(A21::class),
    args22 = get(A22::class)
  )
}

