# MathBord AI — Architecture

## Android
- Kotlin
- Jetpack Compose
- MVVM + Clean Architecture
- Room for local persistence
- WorkManager for deferred/background work
- Feature modules for board, handwriting, photo, error analysis, solutions and Socratic mode
- Single-Activity application shell
- Unidirectional data flow
- Centralized typed navigation
- Cross-cutting services behind interfaces

## Backend
- Node.js + TypeScript
- REST API
- AI Gateway/provider abstraction
- Authentication and rate limiting
- Structured AI responses validated before the client

## Core principle
The AI explains, guides and interprets. Mathematical transformations should be represented and validated structurally by the Math Engine whenever possible.

## Phase 1 guardrails
The application entry point owns dependency composition. UI does not create network, database or AI clients directly. Navigation is centralized. UI state follows state/event/effect conventions. Cross-cutting concerns use abstractions. Future Math Engine and Board systems have explicit boundaries.

## Platform architecture

MathBord AI is Android-first and explicitly multiplatform:

- Android application: APK/AAB.
- Windows desktop application: EXE/MSI.
- Shared KMP contracts: commonMain.
- Android implementations: androidMain.
- Desktop/JVM implementations: desktopMain.

The shared layer contains platform-neutral domain and infrastructure contracts. Android-only APIs and desktop-only APIs remain outside commonMain.

## F1 integration contract

Phase 1 CI validates the Android debug/release build path, shared tests, Android instrumented shell navigation, Windows JVM/packaging path and static platform-boundary checks. Release APK/AAB are unsigned foundation artifacts; Windows packaging runs on a Windows runner.

## Phases
0 Foundation; 1 Multiplatform application foundation; 2 Whiteboard engine; 3 Local persistence; 4 Math sessions; 5 Mathematical representation; 6 Handwriting; 7 Hybrid editor; 8 Solver; 9 Step UI; 10 Error analysis; 11 Backend; 12 AI tutor; 13 Socratic; 14 Photo-to-board; 15 Multimodal AI; 16 Pedagogical engine; 17 History/progress; 18 Adaptive exercises; 19 Offline optimization; 20 Synchronization; 21 Security; 22 UX; 23 Performance; 24 Testing; 25 CI/CD; 26 Beta; 27 Release 1.0.