# Phase 1 — Part 1: Project Foundation & Application Shell

## 1. Purpose

Part 1 creates the first executable Android foundation of MathBord AI. It does not implement mathematics, handwriting recognition, the canvas engine or AI. Its purpose is to establish a stable application host so those systems can be added later without changing the application entry point or violating dependency boundaries.

## 2. Engineering objectives

1. Reproducible Gradle Android project.
2. Single application entry point.
3. Compose root owned by the application.
4. Centralized navigation.
5. Typed top-level routes.
6. Application dependency-composition boundary.
7. Common UI state, event, effect, result and error contracts.
8. Material 3 theme entry point.
9. Scalable package structure for future feature modules.
10. Repository and environment hygiene.
11. Baseline ready for automated tests and CI.

## 3. Exact implementation scope

### 3.1 Project and build system

Expected high-level structure:

~~~text
MathBord-AI-/
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
└── android/
    ├── .gitignore
    └── app/
        ├── build.gradle.kts
        ├── proguard-rules.pro
        └── src/
~~~

Rules:
- Kotlin DSL only.
- Dependency versions centralized in the Gradle version catalog.
- AndroidX only.
- Java/Kotlin toolchain pinned to Java 17.
- Debug and release build types defined from the start.
- Gradle wrapper committed to the repository before Part 1 is declared reproducible.
- No developer-machine paths, local SDK paths or credentials committed.

### 3.2 Current technical baseline

| Component | Version |
|---|---:|
| Android Gradle Plugin | 9.4.0 |
| Gradle | 9.6.0 |
| Kotlin | 2.4.20 |
| Java | 17 |
| Compile SDK | 37 |
| Target SDK | 37 |
| Minimum SDK | 26 |
| Compose BOM | 2026.09.00 |
| Activity Compose | 1.13.0 |
| Lifecycle | 2.11.0 |
| Navigation Compose | 2.10.2 |

Version changes must be made through the version catalog.

### 3.3 Application entry point

Required lifecycle:

~~~text
MathBordApplication
        │
        └── application-scoped dependencies

MainActivity
        │
        └── Compose content

MathBordTheme
        │
        └── MathBordApp
                │
                └── AppNavHost
~~~

MathBordApplication responsibilities:
- Own application-level dependency composition.
- Initialize only lightweight services.
- Avoid network, AI and heavy startup work.

MainActivity responsibilities:
- Host the Android lifecycle.
- Configure edge-to-edge.
- Install the Compose tree.
- Contain no business logic.

MathBordApp responsibilities:
- Act as the application-level Compose root.
- Apply the theme and compose navigation.
- Avoid direct knowledge of databases, HTTP clients or model providers.

### 3.4 Navigation shell

Initial routes:

~~~text
home
board
history
exercises
settings
~~~

Navigation rules:
- Navigation is centralized.
- Screens do not construct the global navigation graph.
- Route identifiers are not duplicated as arbitrary strings throughout feature code.
- Top-level navigation uses single-top behavior.
- Restoration flags are enabled where appropriate.
- The route model must be extensible to parameterized routes such as board/{sessionId}.
- Future deep links must be able to target the same route boundary.

### 3.5 UI contracts

Part 1 establishes the shared vocabulary:

~~~text
UiState
UiEvent
UiEffect
UiResult
AppError
~~~

Semantics:
- UiState: current renderable state.
- UiEvent: user intent or UI-originated action.
- UiEffect: one-time side effect such as navigation or a transient message.
- UiResult: success, loading or failure of an operation.
- AppError: stable application-level error category.

Part 1 does not yet require every screen to have a full ViewModel. Full UDF adoption is Part 2.

### 3.6 Error taxonomy

Initial categories:

~~~text
Network
Timeout
Validation
Storage
Recognition
Ai
Unknown
~~~

Future mapping examples:

~~~text
transport connectivity failure  -> Network
request timeout                 -> Timeout
invalid mathematical input      -> Validation
local persistence failure       -> Storage
handwriting/OCR failure         -> Recognition
AI gateway/provider failure     -> Ai
unexpected failure              -> Unknown
~~~

Raw exceptions may exist below the application boundary, but UI-facing code should converge on typed errors.

### 3.7 Dependency composition

AppContainer is the first composition boundary.

Allowed direction:

~~~text
UI
 ↓
application/domain contracts
 ↓
concrete implementations
~~~

Forbidden pattern:

~~~text
Composable
 ├── creates database
 ├── creates HTTP client
 ├── creates AI client
 └── reads secrets
~~~

A DI framework can be introduced later without changing the public composition boundary.

### 3.8 Logging foundation

AppLogger provides debug, info, warning and error operations.

Rules:
- Debug and info logging disabled by default in release.
- No API keys, tokens, passwords or secrets in logs.
- Avoid logging complete student sessions or raw board contents unless explicitly justified.
- Logging must not become an application dependency on a single provider.

### 3.9 Theme foundation

Part 1 provides:
- Material 3.
- Light theme.
- Dark theme.
- System theme detection.
- Dynamic color where supported.
- Centralized typography entry point.

Part 2 will expand this into the full design system.

### 3.10 Shell screens

Initial placeholders:

~~~text
HomeScreen
BoardScreen
HistoryScreen
ExercisesScreen
SettingsScreen
~~~

These screens only validate application boot and navigation. They are not final UX.

## 4. Package conventions

~~~text
com.mathbord.ai
├── MainActivity
├── MathBordApplication
├── core
│   ├── di
│   ├── logging
│   └── state
├── navigation
└── ui
    ├── components
    ├── screens
    └── theme
~~~

Future dependency direction:

~~~text
ui → domain → data
~~~

UI must not directly depend on future provider SDKs, database implementations or AI providers.

## 5. Detailed execution sequence

### P1.1 — Reproducible repository
- Add Gradle wrapper.
- Confirm main branch is canonical.
- Add root and Android ignore rules.
- Confirm no local credentials or machine-specific paths.

### P1.2 — Gradle foundation
- Create root settings.
- Create root build configuration.
- Create the android app module.
- Create the version catalog.
- Pin Java and Kotlin toolchains.
- Define SDK levels and build types.

### P1.3 — Android identity
- Namespace.
- Application ID.
- Version code and version name.
- Manifest.
- Launcher activity.
- Application class.

### P1.4 — Compose bootstrap
- Activity Compose integration.
- Compose root.
- MathBord theme.
- Central typography and color entry points.

### P1.5 — Application composition
- Create MathBordApplication.
- Create AppContainer.
- Add minimal logger dependency.
- Keep startup deterministic and lightweight.

### P1.6 — Navigation shell
- Typed route definitions.
- Top-level destination metadata.
- AppNavHost.
- Five initial destinations.
- Single-top and restoration configuration.

### P1.7 — UI contracts
- Error categories.
- Generic UI result contract.
- Event/effect contracts.
- Consistent naming for future ViewModels.

### P1.8 — Shell placeholders
- Create five minimal screens.
- Validate they render independently.
- Keep business logic out of placeholders.

### P1.9 — Repository hygiene
- No println-based application logging.
- No secrets.
- No network access from UI.
- No direct storage creation in composables.
- No business logic in Activity.
- No arbitrary navigation strings outside navigation infrastructure.

### P1.10 — Documentation
- Update architecture documentation.
- Update phase roadmap.
- Record deviations from baseline.

## 6. Architectural invariants

### Invariant A — Single UI host
There is one application-level Activity responsible for the Compose host.

### Invariant B — Central navigation
Screens do not own the global navigation graph.

### Invariant C — Dependency direction
UI does not reach concrete AI, network or database implementations.

### Invariant D — No secrets in client source
The Android project contains no production API credentials.

### Invariant E — Lightweight startup
Application startup does not depend on network availability.

### Invariant F — Typed application errors
Feature-facing code should converge on typed application errors rather than leaking provider exceptions into the UI.

### Invariant G — Stable bootstrap
Adding a future feature does not require relocating or rewriting the application entry point.

## 7. Explicit non-goals

Do not implement in Part 1:
- mathematical parsing;
- Math Engine algorithms;
- canvas or stroke engine;
- handwriting recognition or OCR;
- camera integration;
- solver;
- error-analysis engine;
- AI provider calls;
- backend API;
- Room implementation;
- WorkManager jobs;
- synchronization;
- authentication;
- adaptive learning;
- production analytics;
- release signing.

Some of these systems may receive interfaces during Part 3.

## 8. Required repository deliverables

At minimum:

~~~text
settings.gradle.kts
build.gradle.kts
gradle.properties
gradle/libs.versions.toml
gradle/wrapper/*
android/app/build.gradle.kts
android/app/src/main/AndroidManifest.xml
android/app/src/main/java/com/mathbord/ai/MainActivity.kt
android/app/src/main/java/com/mathbord/ai/MathBordApplication.kt
android/app/src/main/java/com/mathbord/ai/navigation/*
android/app/src/main/java/com/mathbord/ai/core/*
android/app/src/main/java/com/mathbord/ai/ui/*
~~~

## 9. Verification matrix

| Area | Requirement | Status |
|---|---|---|
| Repository | Android tree exists | Implemented |
| Gradle | Module and catalog exist | Implemented |
| Wrapper | Scripts, JAR and pinned distribution/checksum | Implemented |
| Manifest | Launcher Activity exists | Implemented |
| Compose | Application root exists | Implemented |
| Navigation | Five shell destinations exist | Implemented |
| Theme | Light/dark path exists | Implemented |
| Logging | Release logging policy | Implemented |
| Secrets | No credentials committed | Required audit |
| UI contracts | State/event/effect/result/error boundaries exist | Implemented |
| Fresh checkout | Build from committed wrapper | Pending |
| CI compilation | Automated validation | Part 4 |
| Instrumented navigation | Automated validation | Part 4 |
| Adaptive UI | Responsive layout audit | Part 2 |
| Accessibility | Semantics and scaling audit | Part 2 |
| Future contracts | Math/Board/connectivity boundaries | Part 3 |

## 10. Definition of Done

Part 1 is complete only when the following chain exists:

~~~text
Repository
  ↓
reproducible Gradle project
  ↓
Android application
  ↓
single Activity
  ↓
Compose root
  ↓
theme
  ↓
central navigation
  ↓
five shell destinations
  ↓
typed UI/application contracts
  ↓
dependency composition boundary
  ↓
clean repository
~~~

Acceptance checklist:
- [x] Gradle project structure exists.
- [x] Android app module exists at android/app.
- [x] Version catalog exists.
- [x] Java 17 toolchain is configured.
- [x] Application identity and manifest exist.
- [x] Single Activity exists.
- [x] Compose root exists.
- [x] Theme exists.
- [x] Typed navigation exists.
- [x] Five shell destinations exist.
- [x] Application dependency boundary exists.
- [x] Typed UI/error/result contracts exist.
- [x] Logging abstraction exists.
- [x] Repository hygiene exists.
- [x] Gradle wrapper committed.
- [ ] Fresh-checkout build verified.
- [ ] CI build verified.
- [ ] Instrumented navigation test verified.

## 11. Risks controlled

Part 1 is specifically designed to reduce:
- navigation sprawl;
- UI-to-database coupling;
- UI-to-AI-provider coupling;
- secret leakage;
- inconsistent error semantics;
- monolithic Activity growth;
- scattered dependency versions;
- non-reproducible builds;
- premature feature logic inside the shell;
- expensive startup behavior.

## 12. Handoff to Part 2

Part 2 receives stable boundaries for:

~~~text
MainActivity
MathBordApplication
MathBordApp
AppNavHost
AppRoute
AppError
UiEvent
UiEffect
UiResult
AppContainer
AppLogger
MathBordTheme
~~~

Part 2 then implements the real UDF/ViewModel architecture, lifecycle restoration, adaptive UI, accessibility, reusable design-system components, keyboard/back behavior and the stylus/finger input boundary.

## 13. Architectural review gate

Before moving to Part 2, the following must be answered with evidence:
1. Can a fresh checkout identify the exact Gradle entry point?
2. Can the application start without Internet?
3. Can every shell destination be reached through the centralized graph?
4. Can UI code be written without creating concrete infrastructure clients?
5. Can a future Board session receive a stable session identifier without redesigning navigation?
6. Can release builds suppress debug logging?
7. Are there zero production secrets in the Android source tree?
8. Is the application entry point free of domain logic?

## P1.1 completion record

- Gradle Wrapper scripts committed: `gradlew` and `gradlew.bat`.
- Wrapper JAR committed at `gradle/wrapper/gradle-wrapper.jar`.
- Wrapper distribution pinned to `gradle-9.6-bin.zip`.
- Gradle distribution SHA-256 pinned in `gradle-wrapper.properties`.
- Fresh-checkout execution remains environment-dependent until Part 4 CI executes the first automated build.
