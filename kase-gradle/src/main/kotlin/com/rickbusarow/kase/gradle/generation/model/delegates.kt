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

package com.rickbusarow.kase.gradle.generation.model

import com.rickbusarow.kase.gradle.generation.model.GradleProviderReference.GradlePropertyReference
import com.rickbusarow.kase.gradle.generation.model.GradleProviderReference.GradleReadOnlyProviderReference
import com.rickbusarow.kase.gradle.generation.model.RegularVariableReference.ImmutableVariableReference
import com.rickbusarow.kase.gradle.generation.model.RegularVariableReference.MutableVariableReference

public fun gradlePropertyReference(
  name: String? = null
): DslContainerProperty<GradlePropertyReference> {
  return DslContainerProperty { container, kProperty ->
    GradlePropertyReference(name ?: kProperty.name, container)
  }
}

public fun gradleProviderReference(
  name: String? = null
): DslContainerProperty<GradleReadOnlyProviderReference> {
  return DslContainerProperty { container, kProperty ->
    GradleReadOnlyProviderReference(name ?: kProperty.name, container)
  }
}

public inline fun <reified T> mutableVariableReference(
  name: String? = null,
  noinline tAsDslElement: (T) -> DslElement
): DslContainerProperty<MutableVariableReference<T>> {
  return DslContainerProperty { container, kProperty ->
    MutableVariableReference(name ?: kProperty.name, container, tAsDslElement)
  }
}

public fun immutableVariableReference(
  name: String? = null
): DslContainerProperty<ImmutableVariableReference> {
  return DslContainerProperty { container, kProperty ->
    ImmutableVariableReference(name ?: kProperty.name, container)
  }
}
