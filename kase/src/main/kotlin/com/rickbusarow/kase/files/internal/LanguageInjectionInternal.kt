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

package com.rickbusarow.kase.files.internal

import com.rickbusarow.kase.files.LanguageInjection

internal interface FileInjection<T> {
  fun createInstance(name: String, content: String): T
  fun update(t: T, content: String): T
}

internal interface LanguageInjectionInternal<T> : LanguageInjection<T>, FileInjection<T>

internal class DefaultLanguageInjection<T>(
  private val fileInjection: FileInjection<T>
) : LanguageInjection<T>, LanguageInjectionInternal<T> {
  override fun javaFile(path: String, content: String): T = createInstance(path, content)
  override fun T.java(content: String): T = update(this, content)
  override fun groovyFile(path: String, content: String): T = createInstance(path, content)
  override fun T.groovy(content: String): T = update(this, content)
  override fun kotlinFile(path: String, content: String): T = createInstance(path, content)
  override fun T.kotlin(content: String): T = update(this, content)

  override fun T.invoke(contentBuilder: () -> String): T = update(this, contentBuilder())

  override fun createInstance(name: String, content: String): T {
    return fileInjection.createInstance(name, content)
  }

  override fun update(t: T, content: String): T = fileInjection.update(t, content)
}
