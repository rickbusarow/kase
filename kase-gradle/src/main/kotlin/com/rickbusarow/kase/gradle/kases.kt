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
import com.rickbusarow.kase.Kase3
import com.rickbusarow.kase.Kase4
import com.rickbusarow.kase.Kase5
import com.rickbusarow.kase.Kase6
import com.rickbusarow.kase.Kase7
import com.rickbusarow.kase.Kase8
import com.rickbusarow.kase.Kase9
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixElement
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
import com.rickbusarow.kase.kases

/**
 * Returns a [List] of [Kase1]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @return a [List] of [Kase1]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>
): List<Kase1<A1>> {
  return kases(
    args1 = get(a1Key)
  )
}

/**
 * Returns a [List] of [Kase2]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @return a [List] of [Kase2]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>
): List<Kase2<A1, A2>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key)
  )
}

/**
 * Returns a [List] of [Kase3]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @return a [List] of [Kase3]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>
): List<Kase3<A1, A2, A3>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key)
  )
}

/**
 * Returns a [List] of [Kase4]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @return a [List] of [Kase4]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>
): List<Kase4<A1, A2, A3, A4>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key)
  )
}

/**
 * Returns a [List] of [Kase5]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @return a [List] of [Kase5]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>
): List<Kase5<A1, A2, A3, A4, A5>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key)
  )
}

/**
 * Returns a [List] of [Kase6]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @return a [List] of [Kase6]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>
): List<Kase6<A1, A2, A3, A4, A5, A6>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key)
  )
}

/**
 * Returns a [List] of [Kase7]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @return a [List] of [Kase7]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>
): List<Kase7<A1, A2, A3, A4, A5, A6, A7>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key)
  )
}

/**
 * Returns a [List] of [Kase8]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @return a [List] of [Kase8]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>
): List<Kase8<A1, A2, A3, A4, A5, A6, A7, A8>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key)
  )
}

/**
 * Returns a [List] of [Kase9]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @return a [List] of [Kase9]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>
): List<Kase9<A1, A2, A3, A4, A5, A6, A7, A8, A9>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key)
  )
}

/**
 * Returns a [List] of [Kase10]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @return a [List] of [Kase10]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>
): List<Kase10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key)
  )
}

/**
 * Returns a [List] of [Kase11]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @return a [List] of [Kase11]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>
): List<Kase11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key)
  )
}

/**
 * Returns a [List] of [Kase12]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @return a [List] of [Kase12]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>
): List<Kase12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key)
  )
}

/**
 * Returns a [List] of [Kase13]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @return a [List] of [Kase13]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>
): List<Kase13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key)
  )
}

/**
 * Returns a [List] of [Kase14]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @return a [List] of [Kase14]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>
): List<Kase14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key)
  )
}

/**
 * Returns a [List] of [Kase15]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @return a [List] of [Kase15]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>,
  reified A15 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>,
  a15Key: VersionMatrixKey<A15>
): List<Kase15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key),
    args15 = get(a15Key)
  )
}

/**
 * Returns a [List] of [Kase16]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @return a [List] of [Kase16]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>,
  reified A15 : VersionMatrixElement<*>,
  reified A16 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>,
  a15Key: VersionMatrixKey<A15>,
  a16Key: VersionMatrixKey<A16>
): List<Kase16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key),
    args15 = get(a15Key),
    args16 = get(a16Key)
  )
}

/**
 * Returns a [List] of [Kase17]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @param a17Key the key for the 17th parameter.
 * @return a [List] of [Kase17]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>,
  reified A15 : VersionMatrixElement<*>,
  reified A16 : VersionMatrixElement<*>,
  reified A17 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>,
  a15Key: VersionMatrixKey<A15>,
  a16Key: VersionMatrixKey<A16>,
  a17Key: VersionMatrixKey<A17>
): List<Kase17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key),
    args15 = get(a15Key),
    args16 = get(a16Key),
    args17 = get(a17Key)
  )
}

/**
 * Returns a [List] of [Kase18]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @param a17Key the key for the 17th parameter.
 * @param a18Key the key for the 18th parameter.
 * @return a [List] of [Kase18]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>,
  reified A15 : VersionMatrixElement<*>,
  reified A16 : VersionMatrixElement<*>,
  reified A17 : VersionMatrixElement<*>,
  reified A18 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>,
  a15Key: VersionMatrixKey<A15>,
  a16Key: VersionMatrixKey<A16>,
  a17Key: VersionMatrixKey<A17>,
  a18Key: VersionMatrixKey<A18>
): List<Kase18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key),
    args15 = get(a15Key),
    args16 = get(a16Key),
    args17 = get(a17Key),
    args18 = get(a18Key)
  )
}

/**
 * Returns a [List] of [Kase19]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @param a17Key the key for the 17th parameter.
 * @param a18Key the key for the 18th parameter.
 * @param a19Key the key for the 19th parameter.
 * @return a [List] of [Kase19]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>,
  reified A15 : VersionMatrixElement<*>,
  reified A16 : VersionMatrixElement<*>,
  reified A17 : VersionMatrixElement<*>,
  reified A18 : VersionMatrixElement<*>,
  reified A19 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>,
  a15Key: VersionMatrixKey<A15>,
  a16Key: VersionMatrixKey<A16>,
  a17Key: VersionMatrixKey<A17>,
  a18Key: VersionMatrixKey<A18>,
  a19Key: VersionMatrixKey<A19>
): List<Kase19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key),
    args15 = get(a15Key),
    args16 = get(a16Key),
    args17 = get(a17Key),
    args18 = get(a18Key),
    args19 = get(a19Key)
  )
}

/**
 * Returns a [List] of [Kase20]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @param a17Key the key for the 17th parameter.
 * @param a18Key the key for the 18th parameter.
 * @param a19Key the key for the 19th parameter.
 * @param a20Key the key for the 20th parameter.
 * @return a [List] of [Kase20]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>,
  reified A15 : VersionMatrixElement<*>,
  reified A16 : VersionMatrixElement<*>,
  reified A17 : VersionMatrixElement<*>,
  reified A18 : VersionMatrixElement<*>,
  reified A19 : VersionMatrixElement<*>,
  reified A20 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>,
  a15Key: VersionMatrixKey<A15>,
  a16Key: VersionMatrixKey<A16>,
  a17Key: VersionMatrixKey<A17>,
  a18Key: VersionMatrixKey<A18>,
  a19Key: VersionMatrixKey<A19>,
  a20Key: VersionMatrixKey<A20>
): List<Kase20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key),
    args15 = get(a15Key),
    args16 = get(a16Key),
    args17 = get(a17Key),
    args18 = get(a18Key),
    args19 = get(a19Key),
    args20 = get(a20Key)
  )
}

/**
 * Returns a [List] of [Kase21]s from this [VersionMatrix] for the given keys.
 *
 * @param a1Key the key for the 1st parameter.
 * @param a2Key the key for the 2nd parameter.
 * @param a3Key the key for the 3rd parameter.
 * @param a4Key the key for the 4th parameter.
 * @param a5Key the key for the 5th parameter.
 * @param a6Key the key for the 6th parameter.
 * @param a7Key the key for the 7th parameter.
 * @param a8Key the key for the 8th parameter.
 * @param a9Key the key for the 9th parameter.
 * @param a10Key the key for the 10th parameter.
 * @param a11Key the key for the 11th parameter.
 * @param a12Key the key for the 12th parameter.
 * @param a13Key the key for the 13th parameter.
 * @param a14Key the key for the 14th parameter.
 * @param a15Key the key for the 15th parameter.
 * @param a16Key the key for the 16th parameter.
 * @param a17Key the key for the 17th parameter.
 * @param a18Key the key for the 18th parameter.
 * @param a19Key the key for the 19th parameter.
 * @param a20Key the key for the 20th parameter.
 * @param a21Key the key for the 21st parameter.
 * @return a [List] of [Kase21]s from this [VersionMatrix] for the given keys
 */
public inline fun <
  reified A1 : VersionMatrixElement<*>,
  reified A2 : VersionMatrixElement<*>,
  reified A3 : VersionMatrixElement<*>,
  reified A4 : VersionMatrixElement<*>,
  reified A5 : VersionMatrixElement<*>,
  reified A6 : VersionMatrixElement<*>,
  reified A7 : VersionMatrixElement<*>,
  reified A8 : VersionMatrixElement<*>,
  reified A9 : VersionMatrixElement<*>,
  reified A10 : VersionMatrixElement<*>,
  reified A11 : VersionMatrixElement<*>,
  reified A12 : VersionMatrixElement<*>,
  reified A13 : VersionMatrixElement<*>,
  reified A14 : VersionMatrixElement<*>,
  reified A15 : VersionMatrixElement<*>,
  reified A16 : VersionMatrixElement<*>,
  reified A17 : VersionMatrixElement<*>,
  reified A18 : VersionMatrixElement<*>,
  reified A19 : VersionMatrixElement<*>,
  reified A20 : VersionMatrixElement<*>,
  reified A21 : VersionMatrixElement<*>
  > VersionMatrix.kases(
  a1Key: VersionMatrixKey<A1>,
  a2Key: VersionMatrixKey<A2>,
  a3Key: VersionMatrixKey<A3>,
  a4Key: VersionMatrixKey<A4>,
  a5Key: VersionMatrixKey<A5>,
  a6Key: VersionMatrixKey<A6>,
  a7Key: VersionMatrixKey<A7>,
  a8Key: VersionMatrixKey<A8>,
  a9Key: VersionMatrixKey<A9>,
  a10Key: VersionMatrixKey<A10>,
  a11Key: VersionMatrixKey<A11>,
  a12Key: VersionMatrixKey<A12>,
  a13Key: VersionMatrixKey<A13>,
  a14Key: VersionMatrixKey<A14>,
  a15Key: VersionMatrixKey<A15>,
  a16Key: VersionMatrixKey<A16>,
  a17Key: VersionMatrixKey<A17>,
  a18Key: VersionMatrixKey<A18>,
  a19Key: VersionMatrixKey<A19>,
  a20Key: VersionMatrixKey<A20>,
  a21Key: VersionMatrixKey<A21>
): List<Kase21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21>> {
  return kases(
    args1 = get(a1Key),
    args2 = get(a2Key),
    args3 = get(a3Key),
    args4 = get(a4Key),
    args5 = get(a5Key),
    args6 = get(a6Key),
    args7 = get(a7Key),
    args8 = get(a8Key),
    args9 = get(a9Key),
    args10 = get(a10Key),
    args11 = get(a11Key),
    args12 = get(a12Key),
    args13 = get(a13Key),
    args14 = get(a14Key),
    args15 = get(a15Key),
    args16 = get(a16Key),
    args17 = get(a17Key),
    args18 = get(a18Key),
    args19 = get(a19Key),
    args20 = get(a20Key),
    args21 = get(a21Key)
  )
}
