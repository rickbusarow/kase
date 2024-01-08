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

import builds.GROUP
import com.rickbusarow.doks.DoksTask
import com.rickbusarow.kgx.mustRunAfter
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

  dependencies {
    classpath(libs.kotlin.gradle.plugin)
  }
}

plugins {
  id("root")
  alias(libs.plugins.doks)
}

doks {
  dokSet {
    docs("README.md", "CHANGELOG.md")

    sampleCodeSource("kase/src/test/kotlin/com/rickbusarow/kase/samples") {
      include("**/*.kt")
    }

    rule("maven-with-version") {
      regex = maven(GROUP)
      replacement = "$1:$2:${libs.versions.rickBusarow.kase.get().escapeReplacement()}"
    }

    rule("kase-type-safe-tuple-pair") {

      replacement = sourceCode(
        fqName = "com.rickbusarow.kase.samples.KaseTupleSample.kaseTypeSafeTuplePair",
        bodyOnly = true,
        codeBlockLanguage = "kotlin"
      )
    }
    rule("kase-type-safe-tuple-big") {

      replacement = sourceCode(
        fqName = "com.rickbusarow.kase.samples.KaseTupleSample.kaseTypeSafeTupleBig",
        bodyOnly = true,
        codeBlockLanguage = "kotlin"
      )
    }
    rule("asTests-simple") {

      replacement = sourceCode(
        fqName = "com.rickbusarow.kase.samples.PersonTests.`person plurals`",
        bodyOnly = false,
        codeBlockLanguage = "kotlin"
      )
    }
    rule("list-multiplication-simple") {

      replacement = sourceCode(
        fqName = "com.rickbusarow.kase.samples.TransformSamples.`list multiplication simple`",
        bodyOnly = true,
        codeBlockLanguage = "kotlin"
      )
    }
    rule("list-multiplication-custom-type") {

      replacement = sourceCode(
        fqName = "com.rickbusarow.kase.samples.TransformSamples.`list multiplication custom type`",
        bodyOnly = true,
        codeBlockLanguage = "kotlin"
      )
    }
  }
}

subprojects.map {
  it.tasks.withType(KotlinCompile::class.java)
    .mustRunAfter(tasks.withType(DoksTask::class.java))
}
