# JEditor v0.3.0 Release Notes

## Overview

JEditor v0.3.0 introduces search, navigation, scrolling, and document statistics.

This release significantly improves usability when working with larger files by adding incremental search, match navigation, viewport-based scrolling, and quick navigation tools.

---

## New Features

### Search

Added a dedicated search system with:

* Incremental search
* Exact match search
* Case-sensitive search
* Match counter
* Match navigation

Search updates in real time as text is entered.

Example:

```text
Search: editor [3/12]
```

Supported shortcuts:

```text
Ctrl + F     Open Search
↑            Previous Match
↓            Next Match
Enter        Select Match
Esc          Close Search
```

---

### Go To Line

Quickly jump to any position within a document.

Supported formats:

```text
15
25:10
100:1
```

Examples:

```text
15      -> Line 15
25:10   -> Line 25, Column 10
```

Shortcut:

```text
Ctrl + G
```

---

### Viewport Scrolling

Added viewport-based rendering for large files.

Support includes:

* Vertical scrolling
* Horizontal scrolling
* Automatic cursor tracking
* Margin-based viewport movement

Large files can now be edited without rendering the entire document on screen.

---

### Document Statistics

Added real-time document statistics.

Status bar now displays:

* Current line
* Current column
* Word count
* Character count

Example:

```text
Ln 12, Col 8 | 450 words | 2800 characters
```

---

### Edit Menu

Added a dedicated Edit menu.

Commands:

* Find
* Go To Line

Keyboard shortcuts are displayed directly inside the menu.

---

## User Interface Improvements

* Search panel
* Match counter display
* Improved status bar information
* Improved viewport behavior
* Improved navigation workflow
* Cleaner gutter rendering
* Better cursor tracking

---

## Search Features

Supported search modes:

### Partial Match

Matches occurrences inside words.

Example:

```text
editor
```

Matches:

```text
editor
texteditor
editorConfig
```

### Exact Match

Matches complete words only.

Example:

```text
editor
```

Matches:

```text
editor
```

Does not match:

```text
texteditor
editorConfig
```

### Case Sensitive

Respects letter casing.

Example:

```text
Editor
```

Matches:

```text
Editor
```

Does not match:

```text
editor
EDITOR
```

---

## Internal Improvements

* Added SearchEngine architecture
* Added SearchState management
* Added viewport controller system
* Improved rendering pipeline
* Improved cursor synchronization
* Improved editor navigation structure

---

## Testing

Current test coverage includes:

### Search Tests

* Incremental search
* Exact match search
* Case-sensitive search
* Match navigation
* Multiple match handling
* No-match scenarios

### Navigation Tests

* Go To Line
* Go To Position
* Horizontal scrolling
* Vertical scrolling
* Viewport updates

### Statistics Tests

* Word count
* Character count
* Status bar rendering

---

## Known Limitations

The following features are planned for future releases:

* Syntax highlighting
* Search result highlighting
* Scroll bars
* Mouse support
* Undo / Redo
* Multiple buffers
* Command palette
* Language-aware editing

---

## Next Release

Version 0.4.0 will focus on:

* Syntax Highlighting
* Search Highlighting
* Scroll Bar Support
* Java Syntax Rules
* Python Syntax Rules
* C/C++ Syntax Rules

---

Thank you for using JEditor.
