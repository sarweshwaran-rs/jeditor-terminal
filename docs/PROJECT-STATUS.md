# JEditor

> A lightweight, cross-platform terminal text editor written entirely in Java ♨️

**Current Version:** `v0.5.0`

---
# Implemented Features

## Command Line Interface

Supported options:

* `-h`, `--help`
* `-v`, `--version`
* `-o`, `--open`

Implemented classes:

* CommandLineParser
* EditorOptions
* HelpPrinter
* Version

---
# Terminal Layer

Implemented using **JLine 4**.

### Features

* Raw mode
* Alternate screen buffer
* Cursor positioning
* Cursor visibility
* Screen clearing
* Window title updates
* Mouse protocol support
* Terminal resize handling

### Implemented Classes

* Terminal
* JLineTerminal

---
# Cross-Platform Input System

JEditor provides a unified input abstraction for keyboard and mouse events across ANSI terminals and Windows consoles.

### Keyboard Features

Supported keys:

* Character input
* Enter
* Tab
* Backspace
* Delete
* Escape
* Arrow keys
* Home
* End
* Page Up
* Page Down

Supported shortcuts:

* Ctrl+A
* Ctrl+C
* Ctrl+F
* Ctrl+G
* Ctrl+L
* Ctrl+N
* Ctrl+O
* Ctrl+Q
* Ctrl+S
* Ctrl+T
* Ctrl+V
* Ctrl+X
* Ctrl+Y
* Ctrl+Z

### Mouse Features

* Left click
* Right click
* Middle click
* Mouse movement
* Drag
* Double click
* Vertical scrolling
* Horizontal scrolling

### Implemented Classes

* InputReader
* InputReaderFactory
* InputEvent
* KeyboardInputEvent
* MouseInputEvent
* KeyboardParser
* MouseParser
* MouseController

---
# Document Model

### Features

* Multi-line editing
* Character insertion
* Character deletion
* Forward deletion
* Line insertion
* Line removal
* Line replacement
* Modified document tracking
* Word counting
* Character counting

### Implemented Classes

* Document

---
# Editor Core

### Editing

* Character insertion
* Multi-line insertion
* Text insertion
* Delete selection
* Multi-line selection deletion

### Navigation

* Left
* Right
* Up
* Down
* Home
* End
* Page Up
* Page Down

### Mouse Support

* Click to move cursor
* Drag selection
* Scroll navigation
* Menu interaction

### Implemented Classes

* Editor
* Cursor
* EditorLayout

---
# Search & Navigation

### Features

* Incremental search
* Exact match search
* Case-sensitive search
* Match navigation
* Match counter
* Go To Line
* Horizontal scrolling
* Vertical scrolling

### Implemented Classes

* SearchEngine
* SearchState
* ViewPort
* ViewportController

---
# Syntax Highlighting

Supported languages:

* Java
* Python
* C
* C++
* Markdown
* Plain Text

Features:

* Automatic language detection
* Regex-based tokenization
* Search highlighting

### Implemented Classes

* SyntaxHighlighter
* LanguageDefinition
* LanguageRegistry
* Token
* TokenType

---
# User Interface

### Features

* Menu bar
* Dialog system
* Status bar
* Message bar
* Line numbers
* Scrollbars
* Search highlighting

---
# Testing

The project currently contains **80+ automated JUnit tests** covering:

* Command-line parsing
* Document model
* Cursor
* Editor
* Keyboard parser
* Mouse parser
* Search
* Viewport
* Status bar

---
# Planned Features

## Phase – Configuration

* User configuration file
* Themes
* Editor preferences
* Startup options
* Key bindings
* Tab size
* Auto indentation

## Phase – Undo / Redo

* Multi-level undo
* Redo stack
* Grouped edits

## Phase – Advanced Editing

* Clipboard
* Copy
* Cut
* Paste
* Selection highlighting

## Phase – Language Features

* Auto indentation
* Smart indentation
* Bracket matching
* Comment toggling
* Auto pairs

## Phase – Command Palette

* Command launcher
* Quick actions
* Keyboard-driven workflow

## Phase – Multiple Buffers

* Multiple open files
* Buffer switching
* Dirty tracking

## Phase – Tree-sitter Integration

* Incremental parsing
* Syntax-aware highlighting
* Code folding
* Symbol navigation
* Diagnostics

## Phase – Editor Polish & Distribution

* Performance optimization
* Windows installer
* Linux packages
* macOS package
* Stable release
