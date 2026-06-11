# JEditor v0.2.0 Release Notes

## Overview

JEditor v0.2.0 introduces file management, save workflows, document statement tracking, dialogs, and improved editor usability.

This release transforms JEditor from a basic editing prototype into a practical
terminal text editor capable of editing and managing real files.
---

## New Features

### File Management

* Open existing files
* Save files
* Save As support
* Automatic creation of new documents for non-existing file paths.

Examples:

```bash
jeditor notes.txt
jeditor Main.java
jeditor --open README.md
```
---

### Document State Tracking

* Modified document tracking
* Unsaved change detection
* Visual modified indicator in the status bar.

Example:
```text
[notes.txt *]
```

The `*` indicates unsaved changes.
---

### Dialog System

Added modal dialogs for common editor operations:

* Open File Dialog
* Save As Dialog
* Confirmation Dialog
* About Dialog

---
### Unsaved Change Protection

JEditor now prevents accidental data loss. When attemting to:

* Quit the editor
* Open another file

while changes are unsaved, a confirmation dialog is displayed.

Option:

* Save
* Don't Save
* Cancel

---

### User Interface Improvements

* Toggle line numbers
* Gutter support
* Improved status bar spacing
* File name display in status bar
* Editor status messages

---

### Error Handling

Added dedicated exception handling for:

* Invalid command line arguments
* File operation failures
* Internal application errors

Exit Codes:

| Code  | Meaning                |
|-------|------------------------|
|0      | Success                |
|1      | Invalid Arguments      |
|2      | File Error             |
|3      | Internal Error         |
---

### Testing
Current test coverage includes:

### Editor Tests

* Character inseration
* Character deletion
* Forward deletion
* Line merge operations
* Home / End navigation
* Page Up / Page Down navigation
* Cursor movement
* Multi-line editing
* Modification tracking

#### Document Tests

* Initial document state
* Line inseration
* Line replacement
* Line removal

#### Command Line Tests

* Help flag parsing
* Version flag parsing
* File argument parsing
* Invalid flag handling
* Multiple file rejection
---

## Internal Improvements

* Refactored file loading logic
* Improved save workflow
* Improved dialog flow management
* Cleaner editor state transitions
* Improved exception handling
---

## Known Limitations

The following features are planned for future releases:

* Vertical scrolling
* Horizontal scrolling
* Incremental search
* Syntax highlighting
* Undo / Redo
* Clipboard support
* Configuration files
---

## Next Release

Version 0.3.0 will focus on:

* Incremental Search
* Find Next
* Find Previous
* Go To Line
* Character Count
* Word Count
---

Thank you for using JEditor
