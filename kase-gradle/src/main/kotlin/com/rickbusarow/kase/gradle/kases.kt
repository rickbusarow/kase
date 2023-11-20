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

@file:Suppress(
  "DestructuringDeclarationWithTooManyEntries",
  "DuplicatedCode",
  "MaxLineLength",
  "PackageDirectoryMismatch"
)
@file:JvmMultifileClass
@file:JvmName("KasesKt")

package com.rickbusarow.kase.gradle

import com.rickbusarow.kase.Kase1
import com.rickbusarow.kase.Kase2
import com.rickbusarow.kase.Kase3
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
