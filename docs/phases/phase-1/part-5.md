# Phase 1 — Part 5: Integration Hardening and Closure

## Status

Implementation complete. F1 closure is gated by CI evidence on the current main commit; no acceptance box is marked passed solely from source inspection.

## Objective

Convert the foundation into one verifiable integration contract for the two supported distributions:

- Android primary: APK/AAB.
- Windows secondary: EXE/MSI.
- Shared KMP contracts remain the boundary between platform implementations.
- CI validates source boundaries, tests and distributable artifacts.

## Integration hardening delivered

### Android

CI now executes architecture/security checks, shared desktop tests, Android lint, Android host unit tests, Debug APK assembly, Release APK assembly and Release AAB bundle generation.

The Android release outputs are then checked for existence, non-zero size and ZIP integrity before upload.

Phase 1 does not introduce a production signing key. The generated release APK/AAB are unsigned foundation artifacts; store signing and publishing are later release work.

### Windows

CI now executes shared desktop tests, Desktop JVM tests, Desktop Kotlin compilation, Windows EXE packaging and Windows MSI packaging.

Both native distribution outputs are verified for existence and non-zero size before upload.

### Architecture boundary

commonMain remains platform-neutral. Android-specific lifecycle, CameraX, WorkManager, permissions and UI APIs stay outside commonMain. Desktop/JVM packaging and window behavior stay in desktopApp/desktopMain.

## Artifact contract

| Target | Expected artifact | Verification |
|---|---|---|
| Android | app-release-unsigned.apk | Exists + ZIP integrity |
| Android | app-release.aab | Exists + ZIP integrity |
| Android | Debug APK | Exists + uploaded |
| Windows | EXE | Exists + non-empty |
| Windows | MSI | Exists + non-empty |

## Acceptance

- [x] Dual-target integration matrix defined.
- [x] Android release APK/AAB commands are CI gates.
- [x] Windows EXE/MSI commands are CI gates.
- [x] Android artifact verification script exists.
- [x] Windows artifact verification script exists.
- [x] CI uploads distributable artifacts.
- [x] Performance benchmark remains an explicit manual workflow.
- [x] No production credential is required for F1.
- [ ] Current main CI Android job passes.
- [ ] Current main CI Android instrumented job passes.
- [ ] Current main CI Windows packaging job passes.
- [ ] Macrobenchmark execution recorded.
- [ ] Phase 1 closed after CI evidence is confirmed.

## Release identity

Current foundation version is 0.1.0 on both Android and Windows packaging.

## Explicit non-goals

F1 does not implement the real whiteboard renderer, handwriting recognition, structured equation parsing, Math Engine transformation validation, AI provider calls, Room persistence, background synchronization or store publishing/signing.