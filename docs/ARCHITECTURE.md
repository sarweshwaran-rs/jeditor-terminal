# JEditor Architecture

## Overview

JEditor is a terminal-based text editor implemented in Java.
The application follows a layered architecture to maintain separation of concerns and Simplify future enhancements.

---
## High-Level Architecture
```text
    Application Layer
            |
    CLI Layer
            |
    Editor Core 
            |
    Document Model
            |
    Rendering Layer
            |
    Terminal Layer
```
Each layer has a single responsibility and communicates only with adjacent layers.
---
## Package Structure
```text
    com.tecs.application
    |
    |-- cli
    |
    |-- document
    |
    |-- editor
    |
    |-- exceptions
    |
    |-- render
    |
    |-- terminal
```
---
## Application Layer
Responsible for application startup and shutdown.

### Classes

* App
* EditorLauncher
* Version
* ExitCode

### Responsibilities

* Application entry point
* Error handling
* Exit code management
* Startup coordination
---

## CLI Layer

Responsible for processing command-line arguments

### Classes

* CommandLineParser
* EditorOptions
* HelpPrinter

### Responsibilities

* Parse arguments
* Validate arguments
* Display help
* Display version information
---

## Terminal Layer

Provides terminal abstraction.

### Classes

* Terminal
* JLineTerminal
* TerminalSize

### Responsibilities

* Raw mode management
* Alternate screen handling
* Cursor Movement
* Terminal size detection
* Output rendering
---

## Keyboard Input Layer
Handles Key event processing.

### Classes

* KeyReader
* Key
* KeyType

### Responsibilities

* Read terminal input
* Decode escape sequences
* Convert terminal input into editor actions

---
## Document Layer

Stores editor contents.

### Classes

* Document

### Responsibilities

* Manage document lines
* Insert content
* Delete content
* Maintain document structure
---

## Editor Core

Contains editor behavior and editing logic.

### Classes

* Editor
* Cursor

### Responsibilities

* Cursor movement
* Text insertion
* Text deletion
* Keyboard action dispatching
* Navigation logic
---

## Rendering Layer

Responsible for visual output

### Classes

* ScreenRenderer

### Responsibilities

* Screen redraw
* Status bar rendering
* Message bar rendering
* Cursor positioning

---

## Curent Design Goals

* Modular architecture
* Cross-Platform terminal support
* Easy testing
* Future syntax highlighting support
* Future plugin support
* Future language integration
---
