# Changelog

## 0.5.0-SNAPSHOT (unreleased)

## [0.4.0] — 2023-12-14

### Fixed

- eagerly write files in `DirectoryBuilder` in https://github.com/rickbusarow/kase/pull/57

### 💥 Breaking Changes

- remove the `TestVersions` type and value from `GradleTestEnvironment` in https://github.com/rickbusarow/kase/pull/63
- make `HasKases<K: Kase>` out-variant in https://github.com/rickbusarow/kase/pull/64

### 🧰 Maintenance

- Update github/codeql-action action to v3 in https://github.com/rickbusarow/kase/pull/55
- Update dependencyAnalysis to v1.28.0 in https://github.com/rickbusarow/kase/pull/54
- Update actions/upload-artifact action to v4 in https://github.com/rickbusarow/kase/pull/62
- Update dependency com.google.devtools.ksp to v1.9.21-1.0.16 in https://github.com/rickbusarow/kase/pull/60
- Don't exclude GitHub bots from the generated changelog in https://github.com/rickbusarow/kase/pull/65

**Full Changelog**: https://github.com/rickbusarow/kase/compare/0.3.0...0.4.0

## [0.3.0] — 2023-12-13

### Added

- introduce trait interfaces for `DependencyVersion` impls in https://github.com/rickbusarow/kase/pull/56

### Fixed

- fix duplicate source files in published artifacts

### Maintenance

- fix weird `@SInCE` capitalization in the release script's commit messages (only visible in the GitHub UI)
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
