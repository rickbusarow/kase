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
 * Common interface for creating dynamic tests with predefined
 * [kases][HasParams.kases] and a unique [TestEnvironment]
 *
 * @since 0.1.0
 */
public interface KaseTestFactory<PARAM, ENV, FACT> :
  HasTestEnvironmentFactory<FACT>,
  EnvironmentTests<PARAM, ENV, FACT>,
  DynamicContainerTransforms<EnvironmentTestNodeBuilder<PARAM, ENV, FACT>>,
  DynamicTestTransforms<PARAM, ENV>,
  HasParams<PARAM>
  where ENV : TestEnvironment,
        FACT : TestEnvironmentFactory<PARAM, ENV>
