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

package com.rickbusarow.kase.gradle.generation.dsl

import com.rickbusarow.kase.asTests
import com.rickbusarow.kase.gradle.generation.ExpectedCodeGenerator
import com.rickbusarow.kase.gradle.generation.dslLanguages
import com.rickbusarow.kase.kases
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

class RepositoryContentDescriptorSpecTest {

  @TestFactory
  fun `custom mavenContent configuration`(): Stream<out DynamicNode> =
    kases(dslLanguages).asTests { language ->

      val builder = SettingsFileSpec {
        pluginManagement {
          repositories {
            mavenLocal {
              mavenContent {

                excludeGroupAndSubgroups(groupPrefix = "art")

                addBlankLine()
                excludeGroup(group = "a")
                excludeModule(group = "a", moduleName = "b")
                excludeVersion(group = "a", moduleName = "b", version = "c")

                addBlankLine()
                excludeGroupByRegex(groupRegex = "a\\.z\\..*")
                excludeModuleByRegex(groupRegex = "a\\.z\\..*", moduleNameRegex = "b.*")
                excludeVersionByRegex(
                  groupRegex = "a\\.z\\..*",
                  moduleNameRegex = "b.*",
                  versionRegex = "c\\.q\\.r"
                )

                addBlankLine()
                includeGroupAndSubgroups(groupPrefix = "art")

                addBlankLine()
                includeGroup(group = "a")
                includeModule(group = "a", moduleName = "b")
                includeVersion(group = "a", moduleName = "b", version = "c")

                addBlankLine()
                includeGroupByRegex(groupRegex = "a\\.z\\..*")
                includeModuleByRegex(groupRegex = "a\\.z\\..*", moduleNameRegex = "b.*")
                includeVersionByRegex(
                  groupRegex = "a\\.z\\..*",
                  moduleNameRegex = "b.*",
                  versionRegex = "c\\.q\\.r"
                )
              }
            }
          }
        }
      }

      val expectedGenerator = ExpectedCodeGenerator(
        language = language,
        kotlinLabels = false,
        kotlinInfix = false
      )

      val excludeGroupAndSubgroups = expectedGenerator.createFunction(
        functionName = "excludeGroupAndSubgroups",
        "groupPrefix" to "art"
      )

      val excludeGroup = expectedGenerator.createFunction(
        functionName = "excludeGroup",
        "group" to "a"
      )

      val excludeModule = expectedGenerator.createFunction(
        functionName = "excludeModule",
        "group" to "a",
        "moduleName" to "b"
      )

      val excludeVersion = expectedGenerator.createFunction(
        functionName = "excludeVersion",
        "group" to "a",
        "moduleName" to "b",
        "version" to "c"
      )

      val excludeGroupByRegex = expectedGenerator.createFunction(
        functionName = "excludeGroupByRegex",
        "groupRegex" to "a\\.z\\..*"
      )

      val excludeModuleByRegex = expectedGenerator.createFunction(
        functionName = "excludeModuleByRegex",
        "groupRegex" to "a\\.z\\..*",
        "moduleNameRegex" to "b.*"
      )

      val excludeVersionByRegex = expectedGenerator.createFunction(
        functionName = "excludeVersionByRegex",
        "groupRegex" to "a\\.z\\..*",
        "moduleNameRegex" to "b.*",
        "versionRegex" to "c\\.q\\.r"
      )

      val includeGroupAndSubgroups = expectedGenerator.createFunction(
        functionName = "includeGroupAndSubgroups",
        "groupPrefix" to "art"
      )

      val includeGroup = expectedGenerator.createFunction(
        functionName = "includeGroup",
        "group" to "a"
      )

      val includeModule = expectedGenerator.createFunction(
        functionName = "includeModule",
        "group" to "a",
        "moduleName" to "b"
      )

      val includeVersion = expectedGenerator.createFunction(
        functionName = "includeVersion",
        "group" to "a",
        "moduleName" to "b",
        "version" to "c"
      )

      val includeGroupByRegex = expectedGenerator.createFunction(
        functionName = "includeGroupByRegex",
        "groupRegex" to "a\\.z\\..*"
      )

      val includeModuleByRegex = expectedGenerator.createFunction(
        functionName = "includeModuleByRegex",
        "groupRegex" to "a\\.z\\..*",
        "moduleNameRegex" to "b.*"
      )

      val includeVersionByRegex = expectedGenerator.createFunction(
        functionName = "includeVersionByRegex",
        "groupRegex" to "a\\.z\\..*",
        "moduleNameRegex" to "b.*",
        "versionRegex" to "c\\.q\\.r"
      )

      val content = builder.write(language)

      content shouldBe """
        |pluginManagement {
        |  repositories {
        |    mavenLocal {
        |      mavenContent {
        |        $excludeGroupAndSubgroups
        |
        |        $excludeGroup
        |        $excludeModule
        |        $excludeVersion
        |
        |        $excludeGroupByRegex
        |        $excludeModuleByRegex
        |        $excludeVersionByRegex
        |
        |        $includeGroupAndSubgroups
        |
        |        $includeGroup
        |        $includeModule
        |        $includeVersion
        |
        |        $includeGroupByRegex
        |        $includeModuleByRegex
        |        $includeVersionByRegex
        |      }
        |    }
        |  }
        |}
      """.trimMargin()
    }
}
