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

package com.rickbusarow.kase.utils

import com.rickbusarow.kase.stdlib.indent
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import kotlin.streams.asSequence

fun DynamicNode.printTree(): String = buildString {
  val node = this@printTree

  appendLine(node)
  if (node is DynamicContainer) {
    indent {
      for (child in node.children) {
        appendLine(child.printTree())
      }
    }
  }
}

fun DynamicNode.childrenDepthFirstWithSelf() = generateSequence(sequenceOf(this)) { nodes ->
  nodes.flatMap { node ->
    (node as? DynamicContainer)?.children?.asSequence().orEmpty()
  }
    .takeIf { it.iterator().hasNext() }
}
  .flatten()
