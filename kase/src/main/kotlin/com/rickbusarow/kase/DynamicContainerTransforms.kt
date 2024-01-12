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

import org.junit.jupiter.api.DynamicNode
import java.util.stream.Stream

/**
 * Defines overloads for the top-level [asContainers] functions. These overloading
 * functions add an [BUILDER] receiver to the `testAction` lambda for some nicer ergonomics.
 */
public interface DynamicContainerTransforms<BUILDER : TestNodeBuilder> {

  /**
   * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the containers are determined by the [displayName] function,
   * and the containers themselves are initialized by the [testAction] function.
   *
   * @param displayName a function to compute the name of each container.
   * @param testAction a function to initialize each container.
   * @receiver the [TestNodeBuilder] to which containers will be added.
   * @return the invoking [TestNodeBuilder], after adding the new containers.
   * @since 0.1.0
   */
  public fun <E> Iterable<E>.asContainers(
    displayName: (E) -> String = maybeDisplayName(),
    testAction: BUILDER.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode> = asSequence().asContainers(displayName, testAction)

  /**
   * Adds containers to the invoking [TestNodeBuilder] for each kaseParam of the
   * iterable. The names of the containers are determined by the [displayName] function,
   * and the containers themselves are initialized by the [childNodes] function.
   *
   * @param displayName a function to compute the name of each
   *   container. action a function to initialize each container.
   * @param childNodes a function to initialize each container.
   * @receiver the [TestNodeBuilder] to which containers will be added.
   * @return the invoking [TestNodeBuilder], after adding the new containers.
   * @since 0.1.0
   */
  public fun <E> Sequence<E>.asContainers(
    displayName: (E) -> String = maybeDisplayName(),
    childNodes: BUILDER.(E) -> Stream<out DynamicNode>
  ): Stream<out DynamicNode>
}
