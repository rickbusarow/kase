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

import kotlin.experimental.ExperimentalTypeInference

/**
 * Returns a list containing all combinations of elements
 * from the original collection and the other collection.
 */
@OptIn(ExperimentalTypeInference::class)
public inline fun <A, B, C> Iterable<A>.times(
  others: Iterable<B>,
  @BuilderInference
  instanceFactory: (A, B) -> C
): List<C> = flatMap { a -> others.map { b -> instanceFactory(a, b) } }

/**
 * Returns a sequence containing all combinations of elements
 * from the original collection and the other collection.
 */
@OptIn(ExperimentalTypeInference::class)
public inline fun <A, B, C> Sequence<A>.times(
  others: Sequence<B>,
  @BuilderInference
  crossinline instanceFactory: (A, B) -> C
): Sequence<C> = flatMap { a -> others.map { b -> instanceFactory(a, b) } }
