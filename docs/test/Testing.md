# Testing Guide

## Overview

JEditor uses **JUnit 5** for automated testing.

The test suite validates:

* Command-line parsing
* Document operations
* Cursor movement
* Editor behavior
* Text insertion and deletion
* Selection editing
* Keyboard parsing
* Mouse parsing
* Search functionality
* Viewport navigation
* Status bar rendering

---

# Running Tests

Execute:

```bash
mvn clean test
```

Expected output:

```text
BUILD SUCCESS
Tests run: 80+
Failures: 0
Errors: 0
Skipped: 0
```

---

# Test Categories

## Command Line Tests

Verifies:

* Help flag parsing
* Version flag parsing
* File argument parsing
* Invalid argument handling
* Multiple file protection

---

## Document Tests

Verifies:

* Document initialization
* Line insertion
* Line deletion
* Line replacement
* Character counting
* Multi-line character counting
* Word counting
* Empty line handling
* Multiple-space handling
* Modified document tracking

---

## Cursor Tests

Verifies:

* Move left
* Move right
* Move up
* Move down
* Set cursor position

---

## Editor Tests

Verifies:

### Editing

* Character insertion
* Character deletion
* Forward deletion
* New-line insertion
* Multi-line editing
* Insert text
* Insert multi-line text
* Ignore empty text insertion
* Delete selected text
* Delete multi-line selection
* Merge lines using Backspace
* Merge lines using Delete

### Navigation

* Move left
* Move right
* Move up
* Move down
* Home
* End
* Page Up
* Page Down

### Document State

* Modified document tracking
* Save state handling
* Opening non-existing files

### Complex Editing

* Insert characters in middle of line
* Delete characters in middle of line
* Cursor movement across lines
* Repeated editing scenarios

---

## Keyboard Parser Tests

Verifies:

* Control-key parsing
* Character parsing
* Space parsing
* ANSI escape sequence parsing
* Arrow key parsing

---

## Mouse Parser Tests

Verifies:

* Left button press
* Button release
* Scroll up
* Invalid escape sequence handling

---

## Search Engine Tests

Verifies:

* Partial matches
* Exact matches
* Case-sensitive searching
* Empty query handling
* Multiple matches
* Search activation
* Search deactivation
* Query insertion
* Query deletion
* Match navigation
* Match wrapping
* Match index safety

---

## ViewPort Tests

Verifies:

* Row offset validation
* Column offset validation

---

## Viewport Controller Tests

Verifies:

* Horizontal scrolling
* Vertical scrolling
* Cursor visibility management

---

## Status Bar Tests

Verifies:

* File name rendering
* Modified indicator
* Narrow terminal rendering

---

# Current Test Statistics

| Component                     |   Tests |
| ----------------------------- | ------: |
| CommandLineParser             |       5 |
| Document                      |      12 |
| Cursor                        |       5 |
| Editor                        |      30 |
| Keyboard & Mouse Parser       |       8 |
| Search / ViewPort / StatusBar |      19 |
| **Total**                     | **79+** |

> **Note:** The total test count may increase as new features are added.

---

# Coverage Areas

Covered:

* Command-line processing
* Document manipulation
* Cursor movement
* Text editing operations
* Multi-line editing
* Selection deletion
* Text insertion
* Keyboard parsing
* Mouse parsing
* Search algorithms
* Search state management
* Viewport scrolling
* Status bar rendering
* Navigation edge cases

---

# Future Test Coverage

Planned:

* Win32 input integration
* ANSI input reader
* Mouse controller
* Rendering engine
* Menu system
* Clipboard support
* Undo / Redo
* Multiple document buffers
* Syntax highlighting
* Language-specific parsing

---

# Continuous Verification

Before every release:

```bash
mvn clean test
```

Requirements:

* All tests must pass
* No skipped tests
* No compiler errors
* Release builds must be generated only from a passing test suite

---

# Version Coverage

## v0.1.0

Covered:

* CLI
* Document model
* Cursor
* Basic editor operations

---

## v0.2.0

Covered:

* File management
* Save workflow
* Modified document tracking
* Additional editor edge cases

---

## v0.3.0

Covered:

* Incremental search
* Exact search
* Case-sensitive search
* Match navigation
* Viewport scrolling
* Document statistics
* Status bar enhancements

---

## v0.4.0

Covered:

* Syntax highlighting
* Language framework
* Tokenization
* Rendering improvements

---

## v0.5.0

Covered:

* Cross-platform input abstraction
* ANSI keyboard parsing
* ANSI mouse parsing
* Win32 keyboard support
* Mouse event parsing
* Text insertion API
* Multi-line text insertion
* Selection deletion
* Expanded editor editing scenarios
* Additional cursor functionality
* Comprehensive parser unit tests
