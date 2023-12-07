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

package com.rickbusarow.kase.generator

/**
 * Returns a view of the list with the first [n] elements dropped.
 *
 * @param n The number of elements to drop from the beginning of the list.
 * @return A list excluding the first [n] elements or an
 *   empty list if [n] is greater than the size of the list.
 */
fun <E> List<E>.dropView(n: Int): List<E> = subList(n.coerceAtLeast(0), size)
