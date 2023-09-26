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

import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.util.stream.Stream

fun <T> Iterable<T>.container(
  name: (T) -> String,
  action: (T) -> Iterable<DynamicNode>
): Stream<DynamicContainer> = map { t ->
  DynamicContainer.dynamicContainer(name(t), action(t))
}.stream()

fun test(name: String, action: () -> Unit): DynamicTest = DynamicTest.dynamicTest(name, action)

fun <T> Iterable<T>.test(name: (T) -> String, action: (T) -> Unit): List<DynamicTest> = map { t ->
  DynamicTest.dynamicTest(name(t)) { action(t) }
}

/**
 * Conditionally applies the provided transform function to the receiver
 * object if the predicate is true, then returns the result of that transform.
 * If the predicate is false, the receiver object itself is returned.
 *
 * @param predicate The predicate to determine whether to apply the transform function.
 * @param transform The transform function to apply to the receiver object.
 * @return The result of the transform function if the
 *   predicate is true, or the receiver object itself otherwise.
 */
inline fun <T> T.letIf(predicate: Boolean, transform: (T) -> T): T {
  return if (predicate) transform(this) else this
}
