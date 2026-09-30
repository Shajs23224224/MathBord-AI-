# Phase 1 — Part 4: Testing, Static Analysis, Performance and CI

## Status

Implementation in progress. The repository now contains the complete initial verification stack; successful execution is the remaining gate.

## 1. Test layers

Shared commonTest validates platform-neutral invariants.
Android host tests validate ViewModel/SavedState behavior.
Android instrumented tests validate shell navigation.
Desktop JVM tests validate the desktop platform boundary.
Macrobenchmark validates Android cold startup.

## 2. Static analysis

Android Lint is a required build gate.
A custom architecture script rejects platform-specific imports in commonMain and scans for common credential patterns.
Kotlin compiler failures are treated as hard CI failures.

## 3. Performance

AndroidX Macrobenchmark 1.5.0 is configured in a separate benchmark module.
The target Android app is profileable and includes Profile Installer 1.4.1.
StartupTimingMetric measures cold start over five iterations.
The benchmark job is manually triggerable initially because emulator performance is variable.

## 4. Android CI

Normal CI executes architecture checks, shared tests, Android Lint, Android unit tests and Debug APK assembly.
An emulator job executes Compose navigation instrumentation.

## 5. Windows CI

Windows CI executes shared tests, Desktop JVM tests, Desktop Kotlin compilation and Windows EXE packaging.
The EXE is uploaded as a workflow artifact.
MSI packaging remains configured and will be published after successful EXE validation.

## 6. Quality policy

No arbitrary performance threshold is imposed before a controlled baseline exists.
No production credentials are available to CI.
No AI/network functionality is required for this phase.

## 7. Acceptance criteria

- [x] Shared unit-test baseline exists.
- [x] Android ViewModel unit test exists.
- [x] Android Compose navigation test exists.
- [x] Android Lint is configured as a CI gate.
- [x] Architecture/security pattern checks exist.
- [x] Windows JVM test/compile job exists.
- [x] Windows EXE packaging job exists.
- [x] Macrobenchmark module exists.
- [x] Cold-start benchmark exists.
- [ ] CI Android build passes.
- [ ] CI Android instrumentation passes.
- [ ] CI Windows build/package passes.
- [ ] Macrobenchmark execution passes.
- [ ] Performance baseline recorded.
- [ ] Phase 1 final audit in Part 5.