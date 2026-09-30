# Phase 1 — Part 3: Cross-Cutting Contracts and Multiplatform Architecture

## Multiplatform status

Android is primary (APK/AAB). Windows Desktop is secondary (EXE/MSI).

Shared KMP contracts now cover identifiers, environment, clock, lifecycle, connectivity, analytics, feature flags, logging, permissions, clipboard, sharing, capabilities, Board documents, Board serialization, Math Engine and session boundaries.

## Important build correction

Kotlin 2.4.20's documented KMP compatibility range allows AGP through 9.3.1, so the project uses AGP 9.3.1 rather than 9.4.0. Gradle 9.6 remains within the supported KMP/Gradle range. citeturn502508search4

## Platform separation

commonMain contains no Android, Swing/AWT or Windows UI imports. Android-only APIs stay in androidMain/androidApp; Desktop-only APIs stay in desktopMain/desktopApp.

## Board portability

BoardDocument, InkStroke, StrokePoint, InkTool and BoardSerializer are platform-neutral. The future Phase 2 rendering engine will translate platform pointer events into the shared input model.

## UI strategy

The current Android and Desktop shell entry points remain separate. Shared UI is not forced prematurely; shared domain/data contracts are established first so Android-specific UX can remain optimized for the primary platform.

## Acceptance

- [x] Android and Windows targets defined.
- [x] Shared KMP boundaries exist.
- [x] Shared lifecycle/storage/serialization/board contracts exist.
- [x] AGP/KMP compatibility corrected.
- [ ] End-to-end Android build verification.
- [ ] End-to-end Windows packaging verification.