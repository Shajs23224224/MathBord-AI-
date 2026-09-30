# Phase 1 — Part 3: Cross-Cutting Contracts and Multiplatform Architecture

## Objective

Establish the platform-neutral contracts that allow MathBord AI to evolve as an Android-first application while also producing a Windows desktop executable.

## 1. Platform model

Android is the primary target and produces APK/AAB.
Windows Desktop is the secondary target and produces self-contained EXE/MSI packages.

Kotlin Multiplatform is introduced at the shared boundary. Compose Multiplatform is introduced for the desktop application and future shared UI work.

## 2. Shared module

A new shared KMP module contains platform-neutral contracts in commonMain and platform implementations in androidMain and desktopMain.

This follows the Kotlin Multiplatform source-set model: common code is shared and platform-specific code belongs in platform source sets. This model is explicitly documented by Kotlin. 

## 3. Stable identity

EntityId provides a platform-neutral stable identifier. newEntityId is declared in common code and implemented separately on Android and desktop with UUID generation.

Stable IDs are required for future Room records, board entities, session records, synchronization and conflict handling.

## 4. Environment

AppEnvironment distinguishes DEVELOPMENT, STAGING and PRODUCTION.

EnvironmentConfig permits platform applications to choose an endpoint and logging policy without embedding secrets in shared source.

## 5. Clock

AppClock isolates wall-clock access so tests can use deterministic fake clocks.

## 6. Connectivity

ConnectivityObserver provides Connected, Disconnected and Unknown. The shared contract does not assume Android ConnectivityManager or a desktop-specific implementation.

## 7. Analytics

AnalyticsTracker and AnalyticsEvent define a provider-neutral boundary. NoOpAnalyticsTracker is the default.

## 8. Feature flags

FeatureFlagProvider defines runtime capability checks. EmptyFeatureFlagProvider is the safe disabled-by-default implementation.

## 9. Logging

AppLogSink and LogEvent define provider-neutral logging. Shared code does not call Android Log, SLF4J or another concrete logger.

## 10. Math Engine

MathEngine is a domain contract only. It defines parse and validate operations and typed results. No solver algorithm is included yet.

The future solver must not make UI code depend directly on an AI provider.

## 11. Board boundary

BoardPort identifies board documents using EntityId. The drawing engine remains Phase 2.

## 12. Platform-neutral input

InputSource defines TOUCH, STYLUS, MOUSE and OTHER. Raw pointer APIs must be normalized before crossing into shared/domain code.

## 13. Security policy

SecurityPolicy records mandatory invariants: no secrets in logs, no production credentials in client source, and no raw provider errors in UI.

This is a policy boundary, not completion of the later security phase.

## 14. Desktop executable foundation

desktopApp is a JVM Compose Multiplatform application with Windows EXE and MSI targets, package name MathBordAI and version 0.1.0.

Compose Multiplatform's native distribution tooling uses jpackage and supports EXE/MSI on Windows. Windows installers are produced on a compatible Windows host. 

## 15. Dependency direction

shared/commonMain
    |
    +-- androidMain --> android/app --> Android APK/AAB
    |
    +-- desktopMain --> desktopApp --> Windows EXE/MSI

commonMain must remain free of Android and desktop UI implementations.

## 16. Deliberate trade-off

The existing Android shell is not fully migrated into commonMain in this part.

Android remains the primary product, and platform-specific features such as CameraX, permissions and WorkManager naturally belong on Android.

The shared domain boundary is introduced now to prevent a later Windows implementation from requiring a rewrite of mathematical/business contracts.

Compose Multiplatform is stable for Android, iOS and desktop according to JetBrains' current support matrix.

## 17. Acceptance criteria

- [x] Android APK/AAB and Windows EXE/MSI explicitly defined as first-class targets.
- [x] KMP shared module created.
- [x] commonMain/androidMain/desktopMain created.
- [x] Stable EntityId boundary created.
- [x] Environment contract created.
- [x] Clock abstraction created.
- [x] Connectivity abstraction created.
- [x] Analytics abstraction created.
- [x] Feature flag abstraction created.
- [x] Shared logging abstraction created.
- [x] MathEngine contract created.
- [x] BoardPort contract created.
- [x] Platform-neutral input model created.
- [x] desktopApp created.
- [x] Windows EXE/MSI packaging configured.
- [ ] Desktop package execution verified.
- [ ] Android/desktop integration tests.
- [ ] Final architecture audit in Part 5.
