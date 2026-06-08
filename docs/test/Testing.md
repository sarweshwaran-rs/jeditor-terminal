# Testing Guide

## Overview

JEditor uses JUnit 5 for automated testing.

The test suite validates:

* CLI functionality
* Document operations
* Cursor movement
* Editor behavior

---

## Running Tests

Execute
```bash
mvn test
```

Expected output:

```text
BUILD SUCCESS
Tests run: 33
Failures: 0
Errors: 0
Skipped: 0
```

---

### Test Categories

### CLI Tests

Verifies:

* Help flat parsing
* Version flag parsing
* File argument parsing
* Invalid arguments

### Dcoument Tests

Verifies:

* Line insertion
* Line deletion
* Document initialization

### Editor Tests

Verifies:

* Character insetion
* Character deletion
* Multi-line editing
* Cursor navigation
* Page navigation

### Cursor Tests

Verifies:

* Row movement
* Column movement
* Boundary conditions

---

## Future Tests

Planned:

* File operations
* Search
* Syntax highlighting
* Configuation system
* Undo / Redo
