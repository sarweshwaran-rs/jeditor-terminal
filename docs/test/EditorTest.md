# Editor & Navigation Tests

## Covered Components

* Editor
* Cursor
* Document
* Keyboard Parser
* Mouse Parser
* Search Engine
* Search State
* ViewPort
* ViewportController
* StatusBar

---

## Editing Operations

* Character insertion
* Character deletion
* Forward deletion
* Text insertion
* Multi-line text insertion
* Empty text insertion
* New-line insertion
* Multi-line editing
* Selection deletion
* Multi-line selection deletion

---

## Navigation Operations

* Left
* Right
* Up
* Down
* Home
* End
* Page Up
* Page Down
* Cursor positioning

---

## Keyboard Parsing

* Control keys
* Characters
* Space
* ANSI escape sequences
* Arrow keys

---

## Mouse Parsing

* Button press
* Button release
* Scroll events
* Invalid sequences

---

## Viewport Operations

* Horizontal scrolling
* Vertical scrolling
* Row offset validation
* Column offset validation

---

## Status Bar

* File name display
* Modified indicator
* Narrow terminal rendering

---

## Complex Scenarios

* Merge lines using Backspace
* Merge lines using Delete
* Cursor movement across lines
* Insert in middle of line
* Delete in middle of line
* Multi-line insertion
* Multi-line deletion
* Repeated editing

---

## Test Statistics

| Category                            |   Count |
| ----------------------------------- | ------: |
| Editor Tests                        |      30 |
| Cursor Tests                        |       5 |
| Document Tests                      |      12 |
| Keyboard & Mouse Parser Tests       |       8 |
| Search / ViewPort / StatusBar Tests |      19 |
| **Total**                           | **79+** |

---

## Current Coverage

Covered:

* Editing operations
* Cursor movement
* Navigation logic
* Selection editing
* Text insertion
* Keyboard parsing
* Mouse parsing
* Search functionality
* Viewport scrolling
* Status bar rendering
* Edge-case handling
