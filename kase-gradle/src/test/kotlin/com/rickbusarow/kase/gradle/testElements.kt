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
import com.rickbusarow.kase.gradle.TestVersion10.TestVersion10Key
import com.rickbusarow.kase.gradle.TestVersion11.TestVersion11Key
import com.rickbusarow.kase.gradle.TestVersion12.TestVersion12Key
import com.rickbusarow.kase.gradle.TestVersion13.TestVersion13Key
import com.rickbusarow.kase.gradle.TestVersion14.TestVersion14Key
import com.rickbusarow.kase.gradle.TestVersion15.TestVersion15Key
import com.rickbusarow.kase.gradle.TestVersion16.TestVersion16Key
import com.rickbusarow.kase.gradle.TestVersion17.TestVersion17Key
import com.rickbusarow.kase.gradle.TestVersion18.TestVersion18Key
import com.rickbusarow.kase.gradle.TestVersion19.TestVersion19Key
import com.rickbusarow.kase.gradle.TestVersion2.TestVersion2Key
import com.rickbusarow.kase.gradle.TestVersion20.TestVersion20Key
import com.rickbusarow.kase.gradle.TestVersion21.TestVersion21Key
import com.rickbusarow.kase.gradle.TestVersion3.TestVersion3Key
import com.rickbusarow.kase.gradle.TestVersion4.TestVersion4Key
import com.rickbusarow.kase.gradle.TestVersion5.TestVersion5Key
import com.rickbusarow.kase.gradle.TestVersion6.TestVersion6Key
import com.rickbusarow.kase.gradle.TestVersion7.TestVersion7Key
import com.rickbusarow.kase.gradle.TestVersion8.TestVersion8Key
import com.rickbusarow.kase.gradle.TestVersion9.TestVersion9Key
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

@Poko
class TestVersion4(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion4, TestVersion4Key>(TestVersion4Key) {

  companion object TestVersion4Key : VersionMatrixKey<TestVersion4>
}

@Poko
class TestVersion5(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion5, TestVersion5Key>(TestVersion5Key) {

  companion object TestVersion5Key : VersionMatrixKey<TestVersion5>
}

@Poko
class TestVersion6(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion6, TestVersion6Key>(TestVersion6Key) {

  companion object TestVersion6Key : VersionMatrixKey<TestVersion6>
}

@Poko
class TestVersion7(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion7, TestVersion7Key>(TestVersion7Key) {

  companion object TestVersion7Key : VersionMatrixKey<TestVersion7>
}

@Poko
class TestVersion8(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion8, TestVersion8Key>(TestVersion8Key) {

  companion object TestVersion8Key : VersionMatrixKey<TestVersion8>
}

@Poko
class TestVersion9(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion9, TestVersion9Key>(TestVersion9Key) {

  companion object TestVersion9Key : VersionMatrixKey<TestVersion9>
}

@Poko
class TestVersion10(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion10, TestVersion10Key>(TestVersion10Key) {

  companion object TestVersion10Key : VersionMatrixKey<TestVersion10>
}

@Poko
class TestVersion11(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion11, TestVersion11Key>(TestVersion11Key) {

  companion object TestVersion11Key : VersionMatrixKey<TestVersion11>
}

@Poko
class TestVersion12(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion12, TestVersion12Key>(TestVersion12Key) {

  companion object TestVersion12Key : VersionMatrixKey<TestVersion12>
}

@Poko
class TestVersion13(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion13, TestVersion13Key>(TestVersion13Key) {

  companion object TestVersion13Key : VersionMatrixKey<TestVersion13>
}

@Poko
class TestVersion14(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion14, TestVersion14Key>(TestVersion14Key) {

  companion object TestVersion14Key : VersionMatrixKey<TestVersion14>
}

@Poko
class TestVersion15(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion15, TestVersion15Key>(TestVersion15Key) {

  companion object TestVersion15Key : VersionMatrixKey<TestVersion15>
}

@Poko
class TestVersion16(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion16, TestVersion16Key>(TestVersion16Key) {

  companion object TestVersion16Key : VersionMatrixKey<TestVersion16>
}

@Poko
class TestVersion17(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion17, TestVersion17Key>(TestVersion17Key) {

  companion object TestVersion17Key : VersionMatrixKey<TestVersion17>
}

@Poko
class TestVersion18(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion18, TestVersion18Key>(TestVersion18Key) {

  companion object TestVersion18Key : VersionMatrixKey<TestVersion18>
}

@Poko
class TestVersion19(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion19, TestVersion19Key>(TestVersion19Key) {

  companion object TestVersion19Key : VersionMatrixKey<TestVersion19>
}

@Poko
class TestVersion20(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion20, TestVersion20Key>(TestVersion20Key) {

  companion object TestVersion20Key : VersionMatrixKey<TestVersion20>
}

@Poko
class TestVersion21(
  override val value: String
) : AbstractDependencyVersion<String, TestVersion21, TestVersion21Key>(TestVersion21Key) {

  companion object TestVersion21Key : VersionMatrixKey<TestVersion21>
}
