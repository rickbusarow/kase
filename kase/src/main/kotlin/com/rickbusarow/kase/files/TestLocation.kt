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

package com.rickbusarow.kase.files

import com.rickbusarow.kase.stdlib.div
import com.rickbusarow.kase.stdlib.plus
import com.rickbusarow.kase.stdlib.takeLastView
import dev.drewhamilton.poko.Poko
import org.jetbrains.annotations.VisibleForTesting
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
 * @since 0.1.0
 */
@Poko
public class TestLocation
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
  internal val testUriOrNull: URI? by lazy { testUriOrNull() }

  private fun testUriOrNull(): URI? {

    infix operator fun String.div(other: String) = "$this${File.separatorChar}$other"

    val userDir = Paths.get("").toAbsolutePath().toFile()

    val packageDir = packageName.replace('.', File.separatorChar)

    val bestGuessSourceDirs = getBestGuessSourceDirs(userDir)

    val ignored = setOf(userDir / "build")
    val visited = mutableSetOf<File>()

    val sourceFile = bestGuessSourceDirs
      .plus(userDir / "src")
      .distinct()
      .firstNotNullOfOrNull { base ->
        base.walkTopDown()
          .onEnter {
            when {
              it in ignored -> false
              // skip anything ignored or already visited
              !visited.add(it) -> false
              it.path.endsWith(packageDir) -> it.resolve(fileName).exists()
              else -> true
            }
          }
          .firstOrNull { it.isFile && it.path.endsWith(packageDir / fileName) }
      }
      ?: return null

    return URI.create("${sourceFile.toURI()}?line=$lineNumber")
  }

  private fun getBestGuessSourceDirs(userDir: File) = bestGuessSourceSetSimpleNames(userDir)
    .flatMap { sourceSetName ->
      val sourceSetDir = userDir / "src" / sourceSetName
      listOf(
        sourceSetDir / "kotlin",
        sourceSetDir / "java",
        sourceSetDir
      )
    }

  private fun bestGuessSourceSetSimpleNames(userDir: File): Sequence<String> {

    val location = declaringClass.protectionDomain.codeSource.location.path
      .removeSuffix(File.separator)
      .substringAfterLast(File.separatorChar)

    val buildClassesKotlin = listOf("build", "classes", "kotlin")
    val classesDirSegmentCount = buildClassesKotlin.size + 1
    val classpath = System.getProperty("java.class.path") ?: return emptySequence()

    return sequenceOf(location) + classpath.splitToSequence(File.pathSeparator)
      .filter { !it.endsWith(".jar") }
      .filter { it.startsWith(userDir.absolutePath) }
      .mapNotNull {
        val split = it.removePrefix(File.separator).split(File.separatorChar)

        fun subList() = split.takeLastView(classesDirSegmentCount)

        when {
          split.size < classesDirSegmentCount -> null
          split.last() == location -> null
          subList() == buildClassesKotlin -> split.last()
          else -> null
        }
      }
  }

  public companion object {

    /**
     * Finds the stack trace kaseParam corresponding to the invoking test function.
     *
     * @since 0.1.0
     */
    public fun get(): TestLocation {
      val ele = testStackTraceElement()
      return from(ele)
    }

    /**
     * Finds the stack trace kaseParam corresponding to the invoking test
     * function. This should be called as close as possible to the test function.
     *
     * @since 0.1.0
     */
    internal fun testStackTraceElement(): StackTraceElement {
      val stackTrace = Thread.currentThread().stackTrace

      return stackTrace.firstNotNullOfOrNull { it.testStackTraceElementOrNull() }
        ?: error("No test StackTraceElement found.")
    }

    private fun from(stackTraceElement: StackTraceElement): TestLocation {
      val declaringClass = Class.forName(stackTraceElement.className)
      val actualClass = declaringClass.removeSynthetics()

      requireNotNull(actualClass) {
        "Could not find a non-synthetic class for ${stackTraceElement.className}"
      }

      return TestLocation(
        fileName = stackTraceElement.fileName as String,
        lineNumber = stackTraceElement.lineNumber,
        packageName = actualClass.`package`.name,
        declaringClass = declaringClass,
        declaringClassWithoutSynthetics = actualClass,
        declaringClassSimpleNames = actualClass.simpleNames(),
        callingFunctionSimpleName = stackTraceElement.methodName
      )
    }
  }
}

internal fun StackTraceElement.clazz(): Class<*> = Class.forName(className)

/**
 * Returns a [StackTraceElement] if the receiver is a test function, otherwise `null`.
 *
 * @since 0.1.0
 */
internal fun StackTraceElement.testStackTraceElementOrNull(): StackTraceElement? {
  @Suppress("SwallowedException")
  val clazz = try {
    Class.forName(this.className)
  } catch (e: ClassNotFoundException) {
    return null
  }

  if (clazz.firstPackageSegment() in sdkPackagePrefixes) {
    return null
  }

  val actualClass = clazz.removeSynthetics() ?: return null

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

private val testAnnotations = setOf(
  "org.junit.jupiter.api.Test",
  "org.junit.jupiter.api.TestFactory",
  "org.junit.Test"
)

/**
 * Checks if an [AnnotatedElement] is annotated with
 * [@TestFactory][org.junit.jupiter.api.TestFactory] or [@Test][org.junit.jupiter.api.Test].
 *
 * @receiver [AnnotatedElement] The kaseParam to check.
 * @return `true` if the [AnnotatedElement] is annotated, `false` otherwise.
 * @since 0.1.0
 */
@PublishedApi
internal fun AnnotatedElement.hasTestAnnotation(): Boolean {
  return annotations.any { it.annotationClass.java.name in testAnnotations }
}

/**
 * Determines whether a method within the given class should be skipped.
 *
 * @param actualClass The class in which the method is declared.
 * @param actualMethodName The name of the method.
 * @return `true` if the method should be skipped, `false` otherwise.
 * @since 0.1.0
 */
internal fun hasTestAnnotation(actualClass: Class<*>, actualMethodName: String): Boolean {

  return actualClass
    .declaredMethods
    .filter { it.name == actualMethodName }
    .requireAllOrNoneAreAnnotated { it.hasTestAnnotation() }
}

/**
 * nested classes and functions have the java `$`
 * labelDelimiter ex: "com.example.MyTest$nested class$my test"
 *
 * @since 0.1.0
 */
internal fun Class<*>.segments(): List<String> = name.split(".", "$")
  .filter { it.isNotBlank() }

internal fun Class<*>.simpleBinaryName(): String {
  return segments().last { it.firstOrNull()?.isDigit() == false }
}

/**
 * Returns the name before the first period
 *
 * @since 0.1.0
 */
private fun Class<*>.firstPackageSegment(): String? = `package`?.name?.substringBefore('.')

/**
 * Validates that all methods in the list are either annotated with
 * [@TestFactory][org.junit.jupiter.api.TestFactory] or [@Test][org.junit.jupiter.api.Test].
 * If only some methods are annotated, an exception will be thrown.
 *
 * @receiver List of [Method] to check.
 * @return `true` if all methods are annotated as tests, `false` otherwise.
 * @since 0.1.0
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
 *
 * @since 0.1.0
 */
public tailrec fun Class<*>.removeSynthetics(): Class<*>? {
  val enclosing: Class<*>? = enclosingClass
  return when {
    isAnnotationPresent(JvmSynthetic::class.java) -> enclosing?.removeSynthetics()
    isSynthetic -> enclosing?.removeSynthetics()
    canonicalName != null -> this
    else -> enclosing?.removeSynthetics()
  }
}

/**
 * Returns the simple names of the receiver class and all of its enclosing classes.
 *
 * @since 0.1.0
 */
public fun Class<*>.simpleNames(): List<String> {
  return generateSequence(this) { it.enclosingClass }
    .mapNotNull { it.simpleName }
    .toList()
    .asReversed()
}

/**
 * Returns the simple names of the receiver class and all of
 * its enclosing classes, starting with the outermost class.
 *
 * @since 0.1.0
 */
internal fun Class<*>.enclosingClasses(): Sequence<Class<*>> {
  return generateSequence(this) { it.enclosingClass }
}

internal fun Sequence<Class<*>>.simpleNames() = map { it.simpleName }

internal fun Class<*>.enclosingClassesSimpleNames(): Sequence<String> {
  return enclosingClasses().map { it.simpleName }
}
