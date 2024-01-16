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

pluginManagement {
  val allowMavenLocal = providers
    .gradleProperty("kase.allow-maven-local")
    .orNull.toBoolean()

  repositories {
    if (allowMavenLocal) {
      logger.lifecycle("${rootProject.name} -- allowing mavenLocal for plugins")
      mavenLocal()
    }
    gradlePluginPortal()
    mavenCentral()
    google()
  }
  includeBuild("build-logic")
}

plugins {
  id("com.gradle.enterprise") version "3.16.1"
}

gradleEnterprise {
  buildScan {

    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()

    // https://docs.github.com/en/actions/learn-github-actions/variables#default-environment-variables

    tag(if (System.getenv("CI").isNullOrBlank()) "Local" else "CI")

    val gitHubActions = System.getenv("GITHUB_ACTIONS")?.toBoolean() ?: false

    if (gitHubActions) {
      // ex: `octocat/Hello-World` as in github.com/octocat/Hello-World
      val repository = System.getenv("GITHUB_REPOSITORY")!!
      val runId = System.getenv("GITHUB_RUN_ID")!!

      link(
        "GitHub Action Run",
        "https://github.com/$repository/actions/runs/$runId"
      )
    }
  }
}

val allowMavenLocal = providers
  .gradleProperty("kase.allow-maven-local")
  .orNull.toBoolean()

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositories {
    if (allowMavenLocal) {
      logger.lifecycle("${rootProject.name} -- allowing mavenLocal for dependencies")
      mavenLocal()
    }
    mavenCentral()
  }
}

rootProject.name = "kase"

include(
  ":kase",
  ":kase-gradle",
  ":kase-gradle-dsl",
  ":kase-overload-generator"
)
