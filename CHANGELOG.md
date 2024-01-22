# Changelog

## 0.10.0-SNAPSHOT (unreleased)

## [0.9.1] — 2024-01-22

### added

- introduce `KaseTests` and move all top-level single-shot `test { }` functions there
  in https://github.com/rickbusarow/kase/pull/114

### 🧰 Maintenance

- Update rickBusarow.kase to v0.9.0 by @renovate in https://github.com/rickbusarow/kase/pull/111
- autoApprove and autoMerge Renovate's updates to the published version of Kase
  in https://github.com/rickbusarow/kase/pull/112
- Update Kase published version to v0.9.0 by @renovate
  in https://github.com/rickbusarow/kase/pull/113
- **Full Changelog**: https://github.com/rickbusarow/kase/compare/0.9.0...0.9.1

## [0.9.0] - 2024-01-20

### 💥 Breaking Changes

- split `TestEnvironmentFactory` up into `NoParamTestEnvironmentFactory`
  and `ParamTestEnvironmentFactory` in https://github.com/rickbusarow/kase/pull/110
- move the times extensions so that they're in a file matching the receiver type
  in https://github.com/rickbusarow/kase/pull/109
- more cleanup of unused dependencies in https://github.com/rickbusarow/kase/pull/108

### 🧰 Maintenance

- Update rickBusarow.kase to v0.8.0 by @renovate in https://github.com/rickbusarow/kase/pull/106

### Other Changes

- make all Kase types implement `java.io.Serializable`
  in https://github.com/rickbusarow/kase/pull/107

**Full Changelog**: https://github.com/rickbusarow/kase/compare/0.8.0...0.9.0

## [0.8.0] — 2024-01-19

### Added

- introduce overloaded `AbstractKaseN` types in https://github.com/rickbusarow/kase/pull/100
- support JUnit4 `@Test` annotations in https://github.com/rickbusarow/kase/pull/105

### 🧰 Maintenance

- Update dependency com.squareup:kotlinpoet to v1.16.0 by @renovate
  in https://github.com/rickbusarow/kase/pull/102
- Update dependencyAnalysis to v1.29.0 by @renovate in https://github.com/rickbusarow/kase/pull/101
- Update dependency com.google.devtools.ksp:symbol-processing-gradle-plugin to v1.9.22-1.0.17 by
  @renovate in https://github.com/rickbusarow/kase/pull/104

**Full Changelog**: https://github.com/rickbusarow/kase/compare/0.7.0...0.8.0

## [0.7.0] — 2024-01-12

### 💥 Breaking Changes

- fixing nested scoping for custom test environments in https://github.com/rickbusarow/kase/pull/95

**Full Changelog**: https://github.com/rickbusarow/kase/compare/0.6.0...0.7.0

## [0.6.0] — 2024-01-05

### Added

- adding `KaseTestFactoryNodeBuilder` in https://github.com/rickbusarow/kase/pull/87

**Full Changelog**: https://github.com/rickbusarow/kase/compare/0.5.0...0.6.0

## [0.5.0] — 2024-01-04

### Added

- `KaseMatrix` replaces the `VersionMatrix` type in https://github.com/rickbusarow/kase/pull/83
- A new `KaseBag` type is a type-safe, indexed set of kase elements
  in https://github.com/rickbusarow/kase/pull/83
- Add `projectDir` and `debug` parameters to gradle runner functions

## [0.4.0] — 2023-12-14

### Fixed

- eagerly write files in `DirectoryBuilder` in https://github.com/rickbusarow/kase/pull/57

### 💥 Breaking Changes

- remove the `TestVersions` type and value from `GradleTestEnvironment`
  in https://github.com/rickbusarow/kase/pull/63
- make `HasKases<K: Kase>` out-variant in https://github.com/rickbusarow/kase/pull/64

### 🧰 Maintenance

- Update github/codeql-action action to v3 in https://github.com/rickbusarow/kase/pull/55
- Update dependencyAnalysis to v1.28.0 in https://github.com/rickbusarow/kase/pull/54
- Update actions/upload-artifact action to v4 in https://github.com/rickbusarow/kase/pull/62
- Update dependency com.google.devtools.ksp to v1.9.21-1.0.16
  in https://github.com/rickbusarow/kase/pull/60
- Don't exclude GitHub bots from the generated changelog
  in https://github.com/rickbusarow/kase/pull/65

**Full Changelog**: https://github.com/rickbusarow/kase/compare/0.3.0...0.4.0

## [0.3.0] — 2023-12-13

### Added

- introduce trait interfaces for `DependencyVersion` impls
  in https://github.com/rickbusarow/kase/pull/56

### Fixed

- fix duplicate source files in published artifacts

### Maintenance

- fix weird `@SInCE` capitalization in the release script's commit messages (only visible in the
  GitHub UI)
- fix `gradle.properties` `VERSION_NAME` parsing in the release script
- use the stable Kase version in the Doks config

**Full Changelog**: https://github.com/rickbusarow/kase/compare/0.2.0...0.3.0

## [0.2.0] - 2023-12-08

### Fixed

- fix writing multiple files within the same `dir { }` block
- create proper display names for TestVersions implementations

### Maintenance

- hard-code `@since 0.1.0` tags into generated overload kdoc

## [0.1.0] - 2023-12-08

Hello World

[0.0.1]

[0.1.0]: https://github.com/rickbusarow/kase/releases/tag/0.1.0
[0.2.0]: https://github.com/rickbusarow/kase/releases/tag/0.2.0
[0.3.0]: https://github.com/rickbusarow/kase/releases/tag/0.3.0
[0.4.0]: https://github.com/rickbusarow/kase/releases/tag/0.4.0
[0.5.0]: https://github.com/rickbusarow/kase/releases/tag/0.5.0
[0.6.0]: https://github.com/rickbusarow/kase/releases/tag/0.6.0
[0.7.0]: https://github.com/rickbusarow/kase/releases/tag/0.7.0
[0.8.0]: https://github.com/rickbusarow/kase/releases/tag/0.8.0
[0.9.0]: https://github.com/rickbusarow/kase/releases/tag/0.9.0
[0.9.1]: https://github.com/rickbusarow/kase/releases/tag/0.9.1
