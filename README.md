# MathBord AI

MathBord AI is an interactive mathematics learning application centered on an AI-powered mathematical whiteboard.

## Product targets

- Android — primary: APK/AAB.
- Windows Desktop — secondary: self-contained EXE/MSI.

## Core features

- AI Whiteboard
- Handwritten mathematics recognition
- "Where did I make a mistake?" step analysis
- Optional Socratic mode
- Photo to editable whiteboard
- Step-by-step solutions

## Architecture work

Phase 1 establishes the multiplatform application foundation. Shared KMP code holds platform-neutral domain and infrastructure contracts, while Android and Desktop retain platform-specific entry points and services.

See:
- docs/architecture.md
- docs/platforms.md
- docs/phases/phase-1/README.md