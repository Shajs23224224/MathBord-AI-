# Phase 1 — Part 2: State, Navigation Resilience, Design System and Adaptive UI

## Objective

Turn the Part 1 shell into a resilient Compose application foundation with one consistent state architecture across all shell destinations.

Part 2 implements infrastructure, not mathematical feature logic.

## 1. State architecture

The shell follows:

~~~text
User action
    ↓
UiEvent
    ↓
ViewModel
    ↓
StateFlow
    ↓
Compose
    ↓
recomposition
~~~

One-time operations use:

~~~text
ViewModel
    ↓
UiEffect / SharedFlow
    ↓
LaunchedEffect
    ↓
transient UI effect
~~~

Implemented:

- ShellViewModel
- ShellUiState
- ShellEvent
- ShellEffect
- lifecycle-aware collectAsStateWithLifecycle()
- ViewModel-owned state mutation
- SavedStateHandle-backed small state

## 2. Process recreation and SavedStateHandle

The shell persists a small interaction counter through SavedStateHandle.

Policy:

- ViewModel owns screen state.
- SavedStateHandle stores small restorable values.
- Large board data must never be placed in SavedStateHandle.
- Durable mathematical/session data belongs to Room once Phase 3 begins.
- Navigation state remains owned by Navigation.
- UI must not depend on process lifetime.

Automated recreation verification remains a Part 4 gate.

## 3. Navigation resilience

Implemented:

- centralized graph;
- singleTop navigation;
- saveState/restoreState for top-level destinations;
- explicit system back behavior from non-home destinations;
- Escape and Alt+Left keyboard back behavior;
- responsive navigation surface;
- layout fills the available window.

Parameterized routes remain compatible with future forms such as board/{sessionId}.

## 4. Responsive/adaptive UI

Three width classes are established:

~~~text
Compact  <= 599 dp
Medium   600–839 dp
Expanded >= 840 dp
~~~

Behavior:

- Compact: bottom NavigationBar.
- Medium: NavigationRail without permanently visible labels.
- Expanded: NavigationRail with labels.
- Main content uses a shared maximum reading width.
- Navigation and content expand to the available window.

Width classification is separated into a pure function for future unit testing.

## 5. Design system foundation

Implemented:

- MathBordSpacing;
- MathBordDimensions;
- reusable content surface with max content width;
- reusable loading/empty/content/error presentation;
- centralized Material 3 theme;
- reusable shell placeholder structure.

## 6. Accessibility foundation

Implemented:

- semantic heading on screen titles;
- accessible labels through standard navigation items;
- icons are not redundantly announced when the parent navigation item has the label;
- text uses scalable Material typography rather than fixed-height containers;
- standard Material interactive controls are used.

The full automated accessibility audit remains part of Part 4.

## 7. Keyboard and back behavior

The shell supports:

- Android/system back;
- Escape;
- Alt+Left on compatible physical keyboards.

Board-specific shortcuts such as Ctrl+Z and Ctrl+Y remain reserved for the future Board feature.

## 8. Stylus/finger input boundary

A stable source vocabulary is available:

~~~text
TOUCH
STYLUS
MOUSE
OTHER
~~~

Pointer types are normalized at the UI boundary through PointerType.toInputSource().

This is not the drawing engine. The future Board module can classify input source without leaking raw pointer APIs into domain logic.

## 9. Loading / empty / content / error states

AsyncStateView establishes a reusable presentation boundary for:

- loading;
- empty;
- content;
- error with optional retry.

## 10. Acceptance criteria

- [x] All shell destinations use the same state/view-model pattern.
- [x] Lifecycle-aware StateFlow collection is used.
- [x] SavedStateHandle is used for small restorable shell state.
- [x] Navigation supports responsive top-level surfaces.
- [x] Back and keyboard shell navigation is centralized.
- [x] Adaptive compact/medium/expanded policy implemented.
- [x] Design spacing/dimensions tokens implemented.
- [x] Shared async state component implemented.
- [x] Basic accessibility semantics implemented.
- [x] Stylus/touch input boundary introduced.
- [ ] Automated rotation/process-death tests — Part 4.
- [ ] Full accessibility test audit — Part 4.
- [ ] Performance validation — Part 4.
- [ ] Cross-cutting service contracts — Part 3.

## 11. Explicit exclusions

Part 2 does not introduce concrete AI providers, network clients, Room databases, synchronization engines, authentication services, mathematical feature logic or canvas/stroke processing.
