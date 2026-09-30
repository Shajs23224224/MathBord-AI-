# MathBord AI — Platform Strategy

MathBord AI has two first-class client targets:

1. Android — primary platform, distributed as APK/AAB.
2. Windows Desktop — secondary platform, distributed as a self-contained EXE/MSI package.

## Architecture

shared/commonMain contains platform-neutral contracts.
shared/androidMain contains Android-specific implementations.
shared/desktopMain contains JVM/Desktop-specific implementations.
android/app is the Android application entry point.
desktopApp is the Windows/Desktop application entry point.

commonMain must not import Android APIs, Swing/AWT, Windows APIs, or provider-specific SDKs.

## Android priority

Android receives first-class treatment for touch, stylus, low-memory operation, lifecycle/process death, offline behavior, camera, permissions and Play Store packaging.

## Desktop priority

Windows receives first-class treatment for keyboard, mouse, resizable windows, desktop file-system boundaries and EXE/MSI packaging.

## Shared-code policy

Share mathematical domain models, Math Engine contracts, stable IDs, error semantics, connectivity, analytics, feature flags, clock abstractions, security policy and repository contracts.

Do not force platform parity where operating-system behavior differs.

## Windows packaging

Compose Multiplatform native distribution tooling supports Windows EXE and MSI packages. These packages are built on the corresponding compatible operating system, so Windows packaging must run on a Windows build runner.

## Future platforms

Linux/macOS are possible later targets but are not current release requirements.
