# Phase 0 — Foundation

## Status
IN PROGRESS

## Objectives
1. Establish repository structure.
2. Document architectural boundaries.
3. Define technology choices.
4. Define contracts and quality gates.
5. Keep Android, mathematical logic and AI providers decoupled.

## Technology decisions
Android: Kotlin, Jetpack Compose, MVVM, Clean Architecture, Room, WorkManager.
Backend: Node.js, TypeScript, REST, AI Gateway abstraction.
Storage: Room for structured local data; app-private files for board/photo assets; PostgreSQL planned server-side.

## Acceptance criteria
- [x] Repository identified; default branch is main.
- [x] Repository was empty before initialization.
- [x] Architecture documented.
- [x] Phase roadmap documented.
- [ ] Android Gradle project created.
- [ ] Backend TypeScript project created.
- [ ] CI baseline created.
- [ ] First compilation/build verified.

## Next phase
Phase 1: create the Android application shell and navigation without feature logic.
