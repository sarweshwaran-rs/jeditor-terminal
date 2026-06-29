# Testing Guide

## Overview

JEditor uses JUnit 5 for automated testing.

The test suite validates:

* Command line parsing
* Document operations
* Cursor movement
* Editor behavior
* Search functionality
* Keyboard parsing
* Mouse parsing
* Viewport navigation
* Status bar rendering

---

## Running Tests

Execute:

```bash
mvn clean test
```

Expected output:

```text
BUILD SUCCESS
Tests run: 80
Failures: 0
Errors: 0
Skipped: 0
```

---

## Test Categories

### Command Line Tests

Verifies:

* Help flag parsing
* Version flag parsing
* Open file argument parsing
* Invalid argument handling
* Multiple file protection

---

### Document Tests

Verifies:

* Document initialization
* Line insertion
* Line deletion
* Line replacement
* Character counting
* Word counting
* Empty line handling
* Modified document tracking

---

### Cursor Tests

Verifies:

* Move left
* Move right
* Move up
* Move down
* Cursor positioning
* Cursor boundary behavior

---

### Editor Tests

Verifies:

* Character insertion
* Character deletion
* Forward deletion
* New line insertion
* Text insertion
* Multi-line text insertion
* Empty text insertion
* Single-line editing
* Multi-line editing
* Selection deletion
* Line merging
* Cursor navigation
* Home navigation
* End navigation
* Page Up
* Page Down
* Document modification tracking
* File opening behavior

---

### Keyboard Parser Tests

Verifies:

* Control key parsing
* Character parsing
* Space parsing
* ANSI escape sequence parsing

---

### Mouse Parser Tests

Verifies:

* Left click parsing
* Mouse release parsing
* Mouse wheel parsing
* Invalid sequence handling

---

### Search Engine Tests

Verifies:

* Partial match search
* Exact match search
* Case-sensitive search
* Empty query handling
* Multiple matches on a single line
* Match navigation
* Match wrapping

---

### Search State Tests

Verifies:

* Search activation
* Search deactivation
* Query insertion
* Query deletion
* Match navigation
* Match wrapping
* Selected match tracking
* Match index safety

---

### ViewPort Tests

Verifies:

* Row offset validation
* Column offset validation
* Negative offset protection

---

### Viewport Controller Tests

Verifies:

* Horizontal scrolling
* Vertical scrolling
* Cursor tracking
* Viewport adjustment

---

### Status Bar Tests

Verifies:

* File name rendering
* Modified indicator rendering
* Narrow terminal handling
* Status bar formatting

---

## Current Test Statistics

| Component | Tests |
|-----------|------:|
| CommandLineParser | 5 |
| Document | 12 |
| Cursor | 5 |
| Editor | 30 |
| Keyboard/Mouse Parser | 8 |
| SearchEngine | 16 |
| StatusBar | 3 |
| **Total** | **80** |

---

## Coverage Areas

Covered:

* Command line processing
* Document manipulation
* Cursor movement
* Text editing operations
* Text insertion
* Selection deletion
* Multi-line editing
* Search algorithms
* Search state management
* Keyboard parsing
* Mouse parsing
* Viewport scrolling
* Status bar rendering
* Edge-case navigation

---

## Future Test Coverage

Planned:

* Native Win32 input integration tests
* ANSI input reader tests
* Rendering engine tests
* Menu system tests
* Mouse controller tests
* Configuration system tests
* Syntax highlighting tests
* Undo / Redo tests
* Clipboard integration tests
* Multiple buffer tests
* Language feature tests

---

## Continuous Verification

Before every release:

```bash
mvn clean test
```

Requirements:

* All tests must pass
* No skipped tests
* No compiler warnings
* Release builds must be generated only from a passing test suite

---

## Version Coverage

### v0.1.0

Covered:

* CLI
* Document model
* Cursor
* Core editor operations

### v0.2.0

Covered:

* File management workflows
* Modified document tracking
* Save behavior
* Additional editor edge cases

### v0.3.0

Covered:

* Incremental search
* Search state management
* Exact match search
* Case-sensitive search
* Match navigation
* Horizontal scrolling
* Vertical scrolling
* Word count
* Character count
* Status bar improvements

### v0.4.0

Covered:

* Text insertion
* Selection deletion
* Keyboard parser
* Mouse parser
* Improved cursor operations
* Extended editor editing scenarios
