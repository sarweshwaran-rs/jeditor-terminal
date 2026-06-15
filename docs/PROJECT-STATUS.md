# JEditor

`
A terminal-based text editor written completely in Java ♨️
`
`
`0.1.0`
`
## Implemented Features

Command Line Interface

Supporte flags:
-   -h | --help
-   -v | --version
-   -o | --open

### Implemented Classes:

-   CommandLineParser
-   EditorOptions
-   HelpPrinter
-   Version

## Terminal Layer
Implemented using:
- JLine 4

### Features:

-   Raw mode
-   Alternate screen buffer
-   Screen clearning
-   Cursor positioning
-   Cursor Visibility control

#### Implemented Classes

-   Terminal (Interface)
-   JLineTerminal

#### Keyboard Input
Supported Keys:
1.  Character input
2.  Enter
3.  Backspace
4.  Delete
5.  Arrow Up
6.  Arrow Down
7.  Arrow Right
8.  Arrow Left
9.  Home
10. End
11. Page Up
12. Page Down

#### Implemente Classes
-   Key
-   KeyType (Enum)
-   KeyReader


`Document Model`
---

## Implemented Feautres

-   Insert Character
-   Delete Character
-   Insert Line
-   Remove Line
-   Multi-Line editing

### Implementing Classes:
-   Document

#### Cursor Navigation
### Implemented Features:

1. Move Left
2. Move Right
3. Move Up
4. Move Down
5. Home
6. End
7. Page Up
8. Page Down

#### Implemented Classes
-   Cursor
-   Editor

`Rendering Engine`
---

### Implemented Features:
-   Full-Screen redraw
-   Status bar
-   Message bar
-   Cursor positioning
-   Line clearing
-   Screen clearing

#### Implemented Classes:

-   ScreenRenderer

### Current Status
---

Phase 1: CLI
Phase 2: Document Model
Phase 3: Terminal Integration
Phase 4: Editor Core
Phase 5: Renderer
Phase 6: Keyboard and Navigation

### Planned Features

Phase 7: File Open/ Save
Phase 8: Status Message
Phase 9: Search
Phase 10: Syntax Highlighting
Phase 11: Configuration
Phase 12: Language Features
Phase 13: Undo/Redo
Phase 14: Packaging & Releases
