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

import com.rickbusarow.kase.gradle.TestVersion1.TestVersion1Key
import com.rickbusarow.kase.gradle.TestVersion2.TestVersion2Key
import com.rickbusarow.kase.gradle.TestVersion3.TestVersion3Key
import com.rickbusarow.kase.gradle.VersionMatrix.VersionMatrixKey
import dev.drewhamilton.poko.Poko

@Poko
class TestVersion1(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion1, TestVersion1Key>(TestVersion1Key) {

  companion object TestVersion1Key : VersionMatrixKey<TestVersion1>
}

@Poko
class TestVersion2(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion2, TestVersion2Key>(TestVersion2Key) {

  companion object TestVersion2Key : VersionMatrixKey<TestVersion2>
}

@Poko
class TestVersion3(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion3, TestVersion3Key>(TestVersion3Key) {

  companion object TestVersion3Key : VersionMatrixKey<TestVersion3>
}
