# Phase 1 — Part 4: Testing, Static Analysis, Performance and CI

## Status

Verification stack implemented; CI execution is the active gate.

## Test layers

- Shared KMP: common invariants executed through the desktop JVM test target.
- Android: ViewModel/SavedState host tests.
- Android instrumented: Compose navigation on an emulator.
- Windows: JVM tests, Desktop Kotlin compilation and native EXE packaging.
- Android performance: separate Macrobenchmark module with cold-start measurement.

## Static analysis

- Android Lint.
- Kotlin compiler errors.
- commonMain platform-boundary scan.
- credential-pattern scan.

## Performance

AndroidX Macrobenchmark 1.5.0 is configured as a separate test module. The target app is profileable and contains Profile Installer 1.4.1. Cold startup is measured with StartupTimingMetric over five iterations. Benchmark CI is manual-dispatch initially, avoiding noisy performance gates on every commit.

## CI matrix

| Target | Runner | Verification | Artifact |
|---|---|---|---|
| Android | Ubuntu | Lint + unit + instrumented | Debug APK |
| Shared/JVM | Ubuntu/Windows | shared desktop tests | — |
| Windows | Windows | JVM compile + EXE package | Windows EXE |
| Android performance | Ubuntu | Macrobenchmark | benchmark output |

## Acceptance

- [x] Unit tests exist.
- [x] Instrumented test exists.
- [x] Windows build/package job exists.
- [x] Macrobenchmark exists.
- [x] Static architecture scan exists.
- [x] CI workflow is configured.
- [ ] Android CI run succeeds.
- [ ] Android instrumented CI succeeds.
- [ ] Windows EXE artifact verified.
- [ ] Macrobenchmark execution verified.
- [ ] Baseline recorded.