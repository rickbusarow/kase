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

package com.rickbusarow.kase.gradle.dsl.model

import com.rickbusarow.kase.gradle.dsl.model.GradleProviderReference.GradlePropertyReference
import com.rickbusarow.kase.gradle.dsl.model.GradleProviderReference.GradleReadOnlyProviderReference
import com.rickbusarow.kase.gradle.dsl.model.RegularVariableReference.ImmutableVariableReference
import com.rickbusarow.kase.gradle.dsl.model.RegularVariableReference.MutableVariableReference

/**
 * Creates a reference to a mutable Gradle property (like `Property<String>`). If a [name]
 * argument is not provided, the name of the property will be inferred by the delegate.
 *
 * ```
 * val myProperty by gradlePropertyReference()
 * myProperty.set("foo")
 * ```
 *
 * @since 0.1.0
 */
public fun gradlePropertyReference(
  name: String? = null
): DslContainerProperty<GradlePropertyReference> {
  return DslContainerProperty { container, kProperty ->
    GradlePropertyReference(name ?: kProperty.name, container)
  }
}

/**
 * Creates a reference to an immutable Gradle provider (like `Provider<String>`). If
 * a [name] argument is not provided, the name of the delegated property will be used.
 *
 * ```
 * val myProvider by gradleProviderReference()
 * val value = myProvider.get()
 * ```
 *
 * @since 0.1.0
 */
public fun gradleProviderReference(
  name: String? = null
): DslContainerProperty<GradleReadOnlyProviderReference> {
  return DslContainerProperty { container, kProperty ->
    GradleReadOnlyProviderReference(name ?: kProperty.name, container)
  }
}

/**
 * Creates a reference to a mutable variable (like `var foo: String`). If a [name]
 * argument is not provided, the name of the delegated property will be used.
 *
 * ```
 * val myVariable by mutableVariableReference()
 * myVariable.value = "foo"
 * ```
 *
 * @since 0.1.0
 */
public inline fun <reified T> mutableVariableReference(
  name: String? = null,
  noinline tAsDslElement: (T) -> DslElement = {
    if (T::class == String::class) StringLiteral(it.toString()) else RawLiteral(it.toString())
  }
): DslContainerProperty<MutableVariableReference<T>> {
  return DslContainerProperty { container, kProperty ->
    MutableVariableReference(name ?: kProperty.name, container, tAsDslElement)
  }
}

/**
 * Creates a reference to an immutable variable (like `val foo: String`). If a
 * [name] argument is not provided, the name of the delegated property will be used.
 *
 * ```
 * val myVariable by immutableVariableReference()
 * val value = myVariable.value
 * ```
 *
 * @since 0.1.0
 */
public fun immutableVariableReference(
  name: String? = null
): DslContainerProperty<ImmutableVariableReference> {
  return DslContainerProperty { container, kProperty ->
    ImmutableVariableReference(name ?: kProperty.name, container)
  }
}
