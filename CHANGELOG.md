# Changelog

## 0.4.0-SNAPSHOT (unreleased)

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
