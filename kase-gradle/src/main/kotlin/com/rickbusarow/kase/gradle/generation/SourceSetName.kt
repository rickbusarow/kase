package com.rickbusarow.kase.gradle.generation

import com.rickbusarow.kase.stdlib.capitalize

typealias SourceSets = MutableMap<SourceSetName, McSourceSet>

@JvmInline
value class SourceSetName(val value: String) {

  fun isTestFixtures(): Boolean = value.startsWith(TEST_FIXTURES.value, ignoreCase = true)

  override fun toString(): String = "(SourceSetName) `$value`"

  companion object {
    val ANDROID_TEST: SourceSetName = SourceSetName("androidTest")
    val ANVIL: SourceSetName = SourceSetName("anvil")
    val DEBUG: SourceSetName = SourceSetName("debug")
    val KAPT: SourceSetName = SourceSetName("kapt")
    val MAIN: SourceSetName = SourceSetName("main")
    val RELEASE: SourceSetName = SourceSetName("release")
    val TEST: SourceSetName = SourceSetName("test")
    val TEST_FIXTURES: SourceSetName = SourceSetName("testFixtures")
  }
}

/**
 * @return true if this source set is `androidTest` or `test`, or any
 *   other source set downstream of them, like `androidTestDebug`.
 */
fun SourceSetName.isTestingOnly(sourceSets: SourceSets): Boolean {

  return sourceSets.getOrElse(this) { sourceSets.getValue(SourceSetName.MAIN) }
    .withUpstream()
    .any { upstream ->
      upstream == SourceSetName.TEST || upstream == SourceSetName.ANDROID_TEST
    }
}

/**
 * @return the name of the non-test/published SourceSet associated with a given SourceSet
 *   name. For SourceSets which are published, this just returns the same name. For testing
 *   SourceSets, this returns the most-downstream source set which it's testing against.
 */
fun SourceSetName.nonTestSourceSetName(sourceSets: SourceSets): SourceSetName {

  return sourceSets.getOrElse(this) { sourceSets.getValue(SourceSetName.MAIN) }
    .withUpstream()
    .sortedByDescending { sourceSets.getValue(it).upstream.size }
    .firstOrNull { upstream -> !upstream.isTestingOnly(sourceSets) }
    .requireNotNull {
      val possible = sourceSets.getValue(this)
        .withUpstream()
        .sortedByDescending { sourceSets.getValue(it).upstream.size }

      "Could not find a non-test source set out of $possible"
    }
}

fun SourceSetName.javaConfigurationNames(): List<ConfigurationName> {

  return if (this == SourceSetName.MAIN) {
    ConfigurationName.main()
  } else {
    ConfigurationName.mainConfigurations
      .filterNot { it.asConfigurationName().isKapt() }
      .map { "${this.value}${it.capitalize()}".asConfigurationName() }
      .plus(kaptVariant())
  }
}

fun SourceSetName.apiConfig(): ConfigurationName {
  return if (this == SourceSetName.MAIN) {
    ConfigurationName.api
  } else {
    "${value}Api".asConfigurationName()
  }
}

fun SourceSetName.implementationConfig(): ConfigurationName {
  return if (this == SourceSetName.MAIN) {
    ConfigurationName.implementation
  } else {
    "${value}Implementation".asConfigurationName()
  }
}

/**
 * @return the 'kapt' name for this source set, such as `kapt`, `kaptTest`, or `kaptAndroidTest`
 * @since 0.12.0
 */
fun SourceSetName.kaptVariant(): ConfigurationName {
  return if (this == SourceSetName.MAIN) {
    ConfigurationName.kapt
  } else {
    "${ConfigurationName.kapt.value}${value.capitalize()}".asConfigurationName()
  }
}
