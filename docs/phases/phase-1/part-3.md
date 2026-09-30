# Phase 1 — Part 3: Cross-Cutting Contracts and Multiplatform Architecture

## Status

Implementation complete at source level; CI/build verification is F1.P4.

## Multiplatform boundaries

Android is the primary client and produces APK/AAB.
Windows Desktop is the secondary client and produces EXE/MSI.

The shared KMP layer now contains platform-neutral contracts for:
- Math Engine.
- Board documents and ink strokes.
- stable IDs.
- sessions.
- storage.
- permissions.
- clipboard and sharing.
- connectivity.
- analytics.
- feature flags.
- clock/time.
- logging.
- platform capabilities.
- security policy.

Platform-specific implementations remain in androidMain/desktopMain or the platform entry-point modules.

## Shared Board model

The shared board model now includes BoardDocument, InkStroke, StrokePoint and InkTool. This makes the mathematical/interaction model portable before the actual rendering engine is implemented in Phase 2.

## Android isolation

Android pointer APIs are normalized at the Android UI boundary. Android-only CameraX, lifecycle, WorkManager and permission APIs must not enter commonMain.

## Desktop isolation

Desktop UI and packaging remain in desktopApp. Windows-specific packaging is configured through Compose Multiplatform native distributions.

## Build compatibility correction

Kotlin Multiplatform 2.4.20 is compatible with AGP through 9.3.1, so the project now pins AGP to 9.3.1 instead of 9.4.0. Gradle 9.6 remains within the supported KMP range. This correction is necessary for a valid multiplatform build.

## Acceptance

- [x] Android and Windows are first-class targets.
- [x] Shared KMP source sets exist.
- [x] Shared contracts do not depend on Android or desktop UI.
- [x] Board core model is shared.
- [x] Platform services are represented by ports.
- [x] Build plugin compatibility corrected for Kotlin 2.4.20.
- [ ] End-to-end Android build.
- [ ] End-to-end Windows package.
- [ ] Cross-platform CI verification.