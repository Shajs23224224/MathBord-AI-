# Phase 2 — Whiteboard Engine

## Objective

Replace the Phase 1 placeholder board with a real, structured whiteboard foundation that separates:

- platform-neutral document editing and history;
- Android pointer input and rendering;
- future recognition, Math Engine and persistence layers.

## Scope

### Shared engine

- `BoardEngine` owns mutations of `BoardDocument`.
- Pencil and highlighter create immutable `InkStroke` objects.
- Eraser removes strokes intersecting an erase radius.
- Undo/redo operates on document snapshots.
- Resize changes the logical board dimensions without destroying strokes.
- Hit testing uses point-to-segment distance, with no Android/UI dependencies.

### Android renderer and input

- `BoardScreen` is now an actual interactive canvas.
- Touch, stylus, mouse and other pointer types map to the shared `InputSource`.
- Pencil and highlighter are rendered as rounded paths.
- Eraser is gesture-based and removes intersecting strokes.
- Toolbar exposes pencil, highlighter, eraser, undo, redo and clear.
- Empty-board state remains accessible and explicit.

### Validation

Common tests cover:

- stroke commit;
- undo/redo;
- erasing by geometry;
- resize invariants.

## Data contract

The whiteboard continues to store structured strokes rather than flattened pixels:

`BoardDocument -> InkStroke -> StrokePoint`

This keeps later handwriting recognition able to consume original stroke trajectories and pressure data.

## Non-goals

Phase 2 does not implement:

- handwriting recognition;
- equation parsing;
- mathematical object recognition;
- photo-to-board;
- Room persistence;
- cloud synchronization;
- AI calls;
- solver/error analysis.

Those remain later phases.

## Acceptance

- [x] Platform-neutral board mutation engine.
- [x] Stroke creation.
- [x] Stylus/touch/mouse source classification at Android input boundary.
- [x] Highlighter tool.
- [x] Eraser with geometric hit testing.
- [x] Undo/redo.
- [x] Clear.
- [x] Android interactive canvas.
- [x] Shared engine unit tests.
- [ ] Android CI build passes on the Phase 2 commit.
- [ ] Android instrumented tests pass on the Phase 2 commit.
- [ ] Windows shared/desktop verification remains green after Phase 2 changes.

## F2 deliverable

A working interactive whiteboard foundation on Android, with structured stroke data preserved at the shared domain boundary for subsequent handwriting and mathematical recognition work.
