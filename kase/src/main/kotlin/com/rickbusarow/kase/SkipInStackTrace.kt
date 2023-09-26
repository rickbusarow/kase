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

package com.rickbusarow.kase

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.lang.StackWalker.StackFrame
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method

private val sdkPackagePrefixes = setOf("java", "jdk", "kotlin")

/**
 * Retrieves the class from a [StackFrame].
 *
 * @receiver [StackFrame] The frame to inspect.
 * @return The class object for the stack frame.
 */
@PublishedApi
internal fun StackFrame.declaringClass(): Class<*> = declaringClass

/**
 * Checks if an [AnnotatedElement] is annotated with
 * [@TestFactory][org.junit.jupiter.api.TestFactory] or [@Test][org.junit.jupiter.api.Test].
 *
 * @receiver [AnnotatedElement] The element to check.
 * @return `true` if the [AnnotatedElement] is annotated, `false` otherwise.
 */
@PublishedApi
internal fun AnnotatedElement.hasTestAnnotation(): Boolean {
  return isAnnotationPresent(TestFactory::class.java) ||
    isAnnotationPresent(Test::class.java)
}

/**
 * Checks if the [StackFrame] is for a test function annotated with
 * [@TestFactory][org.junit.jupiter.api.TestFactory] or [@Test][org.junit.jupiter.api.Test].
 *
 * @receiver [StackFrame] The frame to check.
 * @return `true` if the [StackFrame] is for a test function.
 */
@PublishedApi
internal fun StackFrame.isTestFunction(): Boolean {
  val clazz = declaringClass ?: return false

  if (clazz.firstPackageSegment() in sdkPackagePrefixes) {
    return false
  }

  return hasTestAnnotation(
    actualClass = clazz.removeSynthetics(),
    actualMethodName = callingFunctionName()
  )
}

/**
 * Finds the stack trace element corresponding to the invoking test function.
 *
 * @return The StackFrame corresponding to the test function.
 */
fun StackFrame.callingFunctionName(
  declaringClass: Class<*> = declaringClass(),
  actualClass: Class<*> = declaringClass.removeSynthetics()
): String {
  return declaringClass.name.removePrefix(actualClass.name)
    .segments()
    .firstOrNull()
    ?: methodName
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
 * delimiter ex: "com.example.MyTest$nested class$my test"
 */
internal fun String.segments(): List<String> = split(".", "$")
  .filter { it.isNotBlank() }

/** Returns the name before the first period */
private fun Class<*>.firstPackageSegment(): String? = canonicalName?.substringBefore('.')

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
tailrec fun Class<*>.removeSynthetics(): Class<*> {
  return when {
    isAnnotationPresent(JvmSynthetic::class.java) -> enclosingClass.removeSynthetics()
    canonicalName != null -> this
    else -> enclosingClass.removeSynthetics()
  }
}

fun Class<*>.simpleNames(): List<String> {
  return generateSequence(this) { it.enclosingClass }
    .mapNotNull { it.simpleName }
    .toList()
    .asReversed()
}
