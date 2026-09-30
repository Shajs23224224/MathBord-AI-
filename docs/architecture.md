# MathBord AI — Phase 0 Architecture

## Goal
Establish a modular, testable foundation for an Android-first mathematics learning application.

## Android
- Kotlin
- Jetpack Compose
- MVVM + Clean Architecture
- Room for local persistence
- WorkManager for deferred/background work
- Feature modules for board, handwriting, photo, error analysis, solutions and Socratic mode

## Backend
- Node.js + TypeScript
- REST API
- AI Gateway/provider abstraction
- Authentication and rate limiting
- Structured AI responses validated before reaching the client

## Core principle
The AI explains, guides and interprets. Mathematical transformations should be represented and validated structurally by the Math Engine whenever possible.

## Data flow
Board strokes -> recognition -> mathematical expression -> Math Engine / AI -> structured result -> editable board.
Photo -> image preprocessing -> mathematical recognition -> structured objects -> editable board.
Student steps -> parsing -> mathematical validation -> first-error analysis -> explanation.
Socratic session -> problem + student state -> guided question -> response -> evaluation -> next question.

## Local-first
Whiteboard, session state and local history remain usable without network access. Network-dependent AI and advanced recognition are isolated behind repositories/gateways.

## Phases
0 Foundation; 1 App shell; 2 Whiteboard; 3 Persistence; 4 Sessions; 5 Math representation; 6 Handwriting; 7 Hybrid editor; 8 Solver; 9 Step UI; 10 Error analysis; 11 Backend; 12 AI tutor; 13 Socratic; 14 Photo-to-board; 15 Multimodal AI; 16 Pedagogical engine; 17 History/progress; 18 Adaptive exercises; 19 Offline optimization; 20 Synchronization; 21 Security; 22 UX; 23 Performance; 24 Testing; 25 CI/CD; 26 Beta; 27 Release 1.0.
