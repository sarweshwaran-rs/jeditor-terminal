# Editor & Navigation Tests

## Covered Components

* Editor
* Cursor
* ViewPort
* ViewportController
* StatusBar
* KeyboardParser
* MouseParser

---

## Editing Operations

* Character insertion
* Character deletion
* Forward deletion
* New line insertion
* Text insertion
* Multi-line text insertion
* Empty text insertion
* Single-line selection deletion
* Multi-line selection deletion
* Multi-line editing

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

## Viewport Operations

* Horizontal scrolling
* Vertical scrolling
* Row offset validation
* Column offset validation

---

## Status Bar

* File name display
* Modified indicator
* Narrow terminal handling

---

## Input Parsing

### Keyboard

* Control key parsing
* Character parsing
* Space parsing
* ANSI escape sequence parsing

### Mouse

* Left click
* Mouse release
* Scroll up
* Invalid sequence handling

---

## Complex Scenarios

* Merge lines on backspace
* Merge lines on delete
* Move between lines
* Insert in middle of line
* Delete in middle of line
* Repeated edits
* Multi-line text insertion
* Selection deletion across multiple lines

---

## Test Statistics

| Category                 | Count |
|--------------------------|------:|
| Editor Tests             | 30 |
| Cursor Tests             | 5 |
| ViewPort Tests           | 2 |
| ViewportController Tests | 2 |
| StatusBar Tests          | 3 |
| Keyboard/Mouse Parser Tests | 8 |
| Total                    | 50 |

---

## Current Coverage

Covered:

* Editing operations
* Cursor movement
* Navigation logic
* Text insertion
* Selection deletion
* Multi-line editing
* Viewport scrolling
* Status bar rendering
* Keyboard parsing
* Mouse parsing
* Edge-case handling
