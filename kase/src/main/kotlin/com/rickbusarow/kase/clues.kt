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

package com.rickbusarow.kase

/**
 * Catch anything and rethrow, but prepend the [clue]'s toString(). This
 * is basically the same as just using Kotest's `clue.asClue { ... }`,
 * except that `asClue` only catches assertion errors from inside Kotest.
 *
 * @since 0.1.0
 */
public inline fun <R> withClueCatching(clue: () -> Any?, thunk: () -> R): R {
  try {
    return thunk()
  } catch (@Suppress("TooGenericExceptionCaught") throwable: Throwable) {
    System.err.println(clue.invoke())
    throw throwable
  }
}

/**
 * Catch anything and rethrow, but prepend the receiver's toString(). This
 * toString is evaluated lazily, meaning that it should reflect its state at the
 * time of the exception (possibly excluding weirdness caused by concurrency).
 *
 * This is basically the same as just using Kotest's `__.asClue { ... }`,
 * except that `asClue` only catches assertion errors from inside Kotest.
 *
 * @since 0.1.0
 */
public inline fun <T : Any?, R> T.asClueCatching(block: T.() -> R): R =
  withClueCatching({ this.toString() }) { block() }
