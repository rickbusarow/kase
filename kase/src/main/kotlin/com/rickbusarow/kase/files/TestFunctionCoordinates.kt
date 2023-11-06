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

package com.rickbusarow.kase.files

import com.rickbusarow.kase.stdlib.div
import dev.drewhamilton.poko.Poko
import org.jetbrains.annotations.VisibleForTesting
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.io.File
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method
import java.net.URI
import java.nio.file.Paths

/**
 * @property fileName ex: `com/example/foo/Outer.kt`
 * @property lineNumber ex: 1337
 * @property packageName ex: `com.example.foo`
 * @property declaringClass ex: `com.example.foo.Outer.Middle.Inner$$inlined$$execute$1`
 * @property declaringClassWithoutSynthetics ex: `com.example.foo.Outer.Middle.Inner`
 * @property declaringClassSimpleNames ex: `[Outer, Middle, Inner]`
 * @property callingFunctionSimpleName ex: `some function should return false`
 */
@Poko
public class TestFunctionCoordinates
@VisibleForTesting internal constructor(
  public val fileName: String,
  public val lineNumber: Int,
  public val packageName: String,
  public val declaringClass: Class<*>,
  public val declaringClassWithoutSynthetics: Class<*>,
  public val declaringClassSimpleNames: List<String>,
  public val callingFunctionSimpleName: String
) {

  @PublishedApi
  internal fun testUriOrNull(): URI? {

    infix operator fun String.div(other: String) = "$this${File.separatorChar}$other"

    val userDir = Paths.get("").toAbsolutePath().toFile()

    val packageDir = packageName.replace('.', File.separatorChar)

    val bestGuessSourceDirs = bestGuessSourceSetSimpleNames(userDir)
      .flatMapTo(mutableListOf()) { sourceSetName ->
        listOf(
          userDir / "src" / sourceSetName / "kotlin",
          userDir / "src" / sourceSetName / "java",
          userDir / "src" / sourceSetName
        )
      }

    val visited = mutableSetOf(userDir / "build")

    val sourceFile = setOf(
      *bestGuessSourceDirs.toTypedArray(),
      userDir / "src/test/kotlin",
      userDir / "src/test/java",
      userDir / "src/integrationTest/kotlin",
      userDir / "src/integrationTest/java",
      userDir / "src",
      userDir
    )
      .firstNotNullOfOrNull { base ->
        base.walkTopDown()
          .onEnter {
            when {
              !visited.add(it) -> false
              it.path.endsWith(packageDir) -> it.resolve(fileName).exists()
              else -> true
            }
          }
          .firstOrNull { it.isFile && it.path.endsWith("$packageDir/$fileName") }
      }
      ?: return null

    return URI.create("${sourceFile.toURI()}?line=$lineNumber")
  }

  private fun bestGuessSourceSetSimpleNames(userDir: File): Sequence<String> {

    val buildClassesKotlin = listOf("build", "classes", "kotlin")
    val classesDirSegmentCount = buildClassesKotlin.size + 1
    val classpath = System.getProperty("java.class.path") ?: return emptySequence()

    return classpath.splitToSequence(File.pathSeparator)
      .filter { !it.endsWith(".jar") }
      .mapNotNull {
        val split = it.split(File.separatorChar)

        fun subList() = split.subList(split.lastIndex - classesDirSegmentCount, split.lastIndex)

        when {
          split.size < classesDirSegmentCount -> null
          !it.startsWith(userDir.absolutePath) -> null
          subList() == buildClassesKotlin -> split.last()
          else -> null
        }
      }
  }

  public companion object {

    /** Finds the stack trace kaseParam corresponding to the invoking test function. */
    public fun get(): TestFunctionCoordinates = from(testStackTraceElement())

    /**
     * Finds the stack trace kaseParam corresponding to the invoking test
     * function. This should be called as close as possible to the test function.
     */
    internal fun testStackTraceElement(): StackTraceElement {
      val stackTrace = Thread.currentThread().stackTrace

      val testElement = stackTrace.firstNotNullOfOrNull { it.testStackTraceElementOrNull() }
      return testElement ?: error("No test StackTraceElement found.")
    }

    private fun from(stackTraceElement: StackTraceElement): TestFunctionCoordinates {
      val actualClass = Class.forName(stackTraceElement.className).removeSynthetics()

      return TestFunctionCoordinates(
        fileName = stackTraceElement.fileName as String,
        lineNumber = stackTraceElement.lineNumber,
        packageName = actualClass.`package`.name,
        declaringClass = Class.forName(stackTraceElement.className),
        declaringClassWithoutSynthetics = actualClass,
        declaringClassSimpleNames = actualClass.simpleNames(),
        callingFunctionSimpleName = stackTraceElement.methodName
      )
    }
  }
}

internal fun StackTraceElement.clazz(): Class<*> = Class.forName(className)

/** Returns a [StackTraceElement] if the receiver is a test function, otherwise `null`. */
@PublishedApi
internal fun StackTraceElement.testStackTraceElementOrNull(): StackTraceElement? {
  val clazz = Class.forName(this.className) ?: return null

  if (clazz.firstPackageSegment() in sdkPackagePrefixes) {
    return null
  }

  val actualClass = clazz.removeSynthetics()

  val actualMethodName = if (actualClass == clazz) {
    this.methodName
  } else {
    val actualClassSegments = actualClass.segments()
    clazz.segments()[actualClassSegments.size]
  }

  return if (hasTestAnnotation(actualClass, actualMethodName)) {
    StackTraceElement(
      actualClass.name,
      actualMethodName,
      fileName,
      lineNumber
    )
  } else {
    null
  }
}

private val sdkPackagePrefixes = setOf("java", "jdk", "kotlin")

/**
 * Checks if an [AnnotatedElement] is annotated with
 * [@TestFactory][org.junit.jupiter.api.TestFactory] or [@Test][org.junit.jupiter.api.Test].
 *
 * @receiver [AnnotatedElement] The kaseParam to check.
 * @return `true` if the [AnnotatedElement] is annotated, `false` otherwise.
 */
@PublishedApi
internal fun AnnotatedElement.hasTestAnnotation(): Boolean {
  return isAnnotationPresent(TestFactory::class.java) ||
    isAnnotationPresent(Test::class.java)
}

/**
 * Determines whether a method within the given class should be skipped.
 *
 * @param actualClass The class in which the method is declared.
 * @param actualMethodName The name of the method.
 * @return `true` if the method should be skipped, `false` otherwise.
 */
internal fun hasTestAnnotation(actualClass: Class<*>, actualMethodName: String): Boolean {

  return actualClass
    .methods
    .filter { it.name == actualMethodName }
    .requireAllOrNoneAreAnnotated { it.hasTestAnnotation() }
}

/**
 * nested classes and functions have the java `$`
 * labelDelimiter ex: "com.example.MyTest$nested class$my test"
 */
internal fun Class<*>.segments(): List<String> = name.split(".", "$")
  .filter { it.isNotBlank() }

internal fun Class<*>.simpleBinaryName(): String {
  return segments().last { it.firstOrNull()?.isDigit() == false }
}

/** Returns the name before the first period */
private fun Class<*>.firstPackageSegment(): String? = `package`?.name?.substringBefore('.')

/**
 * Validates that all methods in the list are either annotated with
 * [@TestFactory][org.junit.jupiter.api.TestFactory] or [@Test][org.junit.jupiter.api.Test].
 * If only some methods are annotated, an exception will be thrown.
 *
 * @receiver List of [Method] to check.
 * @return `true` if all methods are annotated as tests, `false` otherwise.
 */
@PublishedApi
internal fun List<Method>.requireAllOrNoneAreAnnotated(
  hasAnnotation: (Method) -> Boolean
): Boolean {

  if (isEmpty()) return false

  val (annotated, notAnnotated) = partition(hasAnnotation)

  require(annotated.size == size || notAnnotated.size == size) {
    "The function named '${first().name}' is overloaded, " +
      "and only some those overloads are annotated with `@TestFactory` or `@Test`.  " +
      "Either all must be annotated or none of them."
  }

  return annotated.size == size
}

/**
 * Returns the current class if it's a real class, otherwise walks up
 * the hierarchy of enclosing/nesting classes until it finds a real one.
 *
 * In practical terms, this strips away Kotlin's anonymous lambda
 * "classes" and other compatibility shims, returning the real class.
 */
public tailrec fun Class<*>.removeSynthetics(): Class<*> {
  return when {
    isAnnotationPresent(JvmSynthetic::class.java) -> enclosingClass.removeSynthetics()
    isSynthetic -> enclosingClass.removeSynthetics()
    canonicalName != null -> this
    else -> enclosingClass.removeSynthetics()
  }
}

/** Returns the simple names of the receiver class and all of its enclosing classes. */
public fun Class<*>.simpleNames(): List<String> {
  return generateSequence(this) { it.enclosingClass }
    .mapNotNull { it.simpleName }
    .toList()
    .asReversed()
}

/**
 * Returns the simple names of the receiver class and all of
 * its enclosing classes, starting with the outermost class.
 */
internal fun Class<*>.enclosingClasses(): Sequence<Class<*>> {
  return generateSequence(this) { it.enclosingClass }
}

internal fun Sequence<Class<*>>.simpleNames() = map { it.simpleName }

internal fun Class<*>.enclosingClassesSimpleNames(): Sequence<String> {
  return enclosingClasses().map { it.simpleName }
}
