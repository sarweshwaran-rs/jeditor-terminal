# JEditor Roadmap
JEditor is a terminal-based text editor written entirely in Java.
This roadmap tracks the planned milestones and release targets.

## v0.1.0 - Core Editor Foundation

Status: Released

### Command Line Interface
* Help command (`-h`, `--help`)
* Version command (`-v`, `--version`)
* Open file argument (`-o`, `--open`)

### Terminal Integration

* JLine 4 integration
* Raw mode support
* Alternate screen buffer
* Cursor visibility control
* Terminal size detection

#### Document Model

* Multi-line document structure
* Line insertion
* Line deletion
* Line replacement

#### Editor Core

* Character Insertion
* Character Deletion
* Home / End Navigation
* Page Up / Page Down Navigation

#### Rendering Engine

* Full - Screen redraw
* Status bar
* Message bar
* Cursor positioning

#### Testing

* Unit test suite
* CLI tests
* Editor tests
* Cursor tests

---
## Version 0.2.0 - File Management

Status: Released

### Features

* Open existing files
* Save files
* Save As
* Line Number toggle with Gutter Space
* Modified document tracking
* File status messages
* Error handling for file operations
* About DIalog
* Save, Save As Dialog
* Modified Save

---
## Version 0.3.0 - Search & Navigation

### Features

* Horizontal scrolling
* Vertical Scrolling
* Character Count
* Word Count
* Incremental search
* Match type (Exact, partial match, Case Sensivitive)
* Go to line
* UI Management Menu Bar implementation

Status:
    Released
---
## Version 0.4.0 - Syntax Highlighting

Planned
### Languages 

* Search Highlight
* Scroll Bar (Horizontal and Vertical)
* Java
* Python
* C
* C++
* Plain Text

Status: Released

---
## Version 0.5.0 - Mouse Support

Planned

### Features

* Click to move cursor
* Scroll wheel support
* Menu interaction
* Dialog interaction

---

## Version 0.6.0 - Configuration

Planned

### Features

* Themes
* Read-only mode
* Config file support
* User prefrences

---
## Version 0.7.0 - Advanced Editing

Planned 

### Features

* New Data Structure for better Document
* Memory Store for Undo/ Redo
* Undo
* Redo
* Clipboard operations
* Selection support

---
## Version 0.8.0 - Language Features 

Features

* Auto indentation
* Bracket matching
* Comment toggling
* Smart editing
---

## Version 0.9.0 Multiple Buffers

### Features

* Tabs
* Buffer Switching
* Multiple open files

## Version 1.0.0 Stable Release

### Features

* File management
* Search
* Syntax Highlighting
* Mouse Support
* Undo/Redo
* Language Features
* Multiple buffers
