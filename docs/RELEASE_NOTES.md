# JEditor v0.5.0 Release Notes

## Overview

JEditor  v0.5.0 introduces a complete cross-platform input system together with full mouse support, text selection, and major editor interaction improvements.
This release transforms JEditor from a keyborad-only editor into a modern terminal editor capable of respondig to both keyboard and mouse input on Linux/macOS (ANSI terminals) and Windows consoles.

The internal architecture has also been significantly refactored by introducing a unified input abstraction, platform-specific input adapters, editor layout managemnt, and selection infrastructure, laying the foundation for future clipboard operations and advanced editing features.

---

# New Features

## Cross-Platform Input System
Introduced a unified input architecture that delivers both keyboard and mouse events through a single interface.

New components include:

* InputReader,
* InputReaderFactory,
* InputEvent,
* KeyboardInputEvent,
* MouseInputEvent.

Supported platforms:

* ANSI terminals,
* Windows Console (Win32 FFM).

The editor now processes Keyboard and mouse input consistently across all supported platforms.

---
## Keyboard Input Improvements

The keyboard parser has been redesigned to support platform-independent key handling.

Supported Keys include:

* Character input,
* Escape,
* Enter,
* Tab,
* Backspace,
* Delete,
* Arrow Keys,
* Home,
* End,
* Page Up,
* Page Down.

Supported shortcut include:

* Ctrl + A,
* Ctrl + C,
* Ctrl + F,
* Ctrl + G,
* Ctrl + L,
* Ctrl + N,
* Ctrl + O,
* Ctrl + Q,
* Ctrl + S,
* Ctrl + T,
* Ctrl + V,
* Ctrl + X,
* Ctrl + Y,
* Ctrl + Z.

ANSI escape sequences and native Windows Keyboard events are translated into a common internal representation.

---
## Complete Mouse Support

JEditor now includes full cross-platform mouse support.

Supported actions:

* Left click,
* Right click,
* Middle click,
* Mouse release,
* Mouse movement,
* Drag,
* Double click,
* Vertical scrolling,
* Horizontal Scrolling.

Mouse support is available on both ANSI terminals and Windows consoles.

---
## Editor Layout System

Added a dedicated layout engine responsible for translating screen coordinate into document coordinates.

The layout model manages:
* Menu bar,
* Editor area,
* Gutter,
* Scrollbars,
* Search panel,
* Viewport mapping.

This removes coordinate calculations from editor logic and centralizes rendering geomertry.

---
## Mouse Cursor Navigation

The editor now supports mouse-driven cursor movement.

Feature include:

* Click to position cursor,
* Automatic cursor clamping,
* Viewpor visibility management,
* Cursor visibility management.

The editor immediately updates after every mouse interaction.

---
## Mouse-Based Text Selection

Introduced the first complete text selection system.

Features include:

* Click and drag selection,
* Selection tracking,
* Selection updates during dragging,
* Automatic selection clearing,
* Single-line selection,
* Multi-line selection.

The editing engine now supports deleting selected text across multiple lines.
This infrasructure prepares JEditor for future clipboard operations.

---
## Menu Bar Interaction

The menu system is now fully mouse-aware.

Supported operations:

* Open menus,
* Hover menus,
* Selet menu items,
* Execute menu commands,
* Click outside to dismiss menus.

Menus behave similarly to traditional desktop application.

---
## Search Panel Interaction

The search interface now supports mouse interaction.

Feature include:

* Clickable search controls,
* Search navigation,
* Match selection,
* Improved interaction workflow.

---
## Mouse Wheel Navigation

Viewport scrolling now supports mouse wheel input.

Features include:

* Vertical scrolling,
* Horizontal scrolling,
* Automatic viewport synchronization,
* Cursor visibility maintenance.

---
## Editing Improvements

The editor now includes several new editing capabilities.

New operations:

* Insert text,
* Insert multi-line text,
* Delete selected text,
* Delete multi-line selections.

The APIs simplify future implementation of copy, cut, and paste functionality.

---
# Windows Native Input Support

Implemented a native Windows input backend using the Java Foreign Function & Memory (FFM) API.

New components include:

* Win32Support,
* Win32InputReader,
* WindowsKeyAdapter,
* WindowsMouseAdapter,
* Win32Structures,
* Win32Layouts,
* Win32Constants

The Windows backed now supports native keyboard and mouse events without relying on ANSI sequences.

---
# ANSI Terminal Improvements

The ANSI backend has been redesigned.

Improvements include:

* Unified input reader,
* Dedicated Keyboard parser,
* Dedicated mouse parse,
* ANSI mouse protocol support,
* Improved editor event handling.

---
# Internal Improvements

Major architectual improvements include:

* Unified input abstraction,
* Event-driven input model,
* Shared keyboard representation,
* Shared mouse representations,
* Platform-specific adapters,
* Shared keyboard representation,
* Improved separation of concerns,
* Reduced platform-specific code duplication,
* Simplified editor event handling.

---
# Testing

The automated test suite has been significantly expanded.

Current coverage includes:

### Document Tests

* Document initialization,
* Line editing,
* Character counting,
* Word counting,
* Modified state tracking.

### Cursor Tets

* Cursor movements,
* Positon updates.

### Editor Tests

* Character editing,
* Multi-line editing,
* Text insertion,
* Multi-line insertion,
* Selection deletion,
* Multi-line Selection deletion,
* Navigation,
* File handling,
* Edge cases.

### Keyboard Parser Tests

* Character parsing,
* Control keys,
* ANSI escape sequences,
* Navigation Keys.

### Mouse Parser Tests

* Button press,
* Button Release,
* Scroll events,
* Invalid sequence handling.

### Search Tests

* Incremental search,
* Exact search,
* Case-Sensivitive search,
* Match navigation,
* Viewport sysnchronization.

Current automated coverage exceed **79 unit tests**.

---
# Performance Improvements

* Reduced input processing oerhead,
* Shared input parsing pipeline,
* Faster event dispatch,
* Improved platform abstraction,
* Cleaner rendering interaction.

---
# Breaking Internal Changes

The input subsystem has been completely redesigned.

Legacy components have been replaced by:

* InputReader,
* KeyboardParser,
* MouseParser,
* InputEvent hierarchy,
* Platform-specific input adapters.

This change simplifies future feature development while maintaining existing editor behavior.

---
# Known Limitations

The following features are planned for future releases:

* Clipboard integration,
* Undo / Redo,
* Multiple document buffers,
* Configurations system,
* Additional command-line options,
* Language aware editing,
* Tree-Sitter integration.
---
# Next Release

## Version 0.6.0
Planned features include:

### Configuration System

* User configuration file,
* Themes,
* Editor preferences
* Tab size,
* Auto indentation.

### New Command-Line Options

* Theme Selection,
* Read-only mode,
* Disable syntax highlighting,
* Tab size configuration,
* Configuration file override.

---

Thank you for using JEditor.
