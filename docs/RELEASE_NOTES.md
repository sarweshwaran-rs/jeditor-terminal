# JEditor v0.4.0 Release Notes

## Overview

JEditor v0.4.0 introduces the first syntax highlighting system for the editor.

This release significantly improves code readability by adding language-aware highlighting, search result highlighting, automatic language detection, and visual scrollbars.

With support for multiple programming languages and Markdown documents, JEditor now provides a more productive editing experience while remaining lightweight and terminal-based.

---

## New Features

### Syntax Highlighting

Added a regex-based syntax highlighting engine.

Supported token types include:

* Keywords
* Types
* Strings
* Numbers
* Comments
* Preprocessor directives

Highlighting is performed in real time during rendering.

---

### Language Detection

JEditor now automatically detects the document language based on file extension.

Supported languages:

* Java
* Python
* C
* C++
* Markdown
* Plain Text

Examples:

```text
Main.java      -> Java
app.py         -> Python
program.c      -> C
vector.cpp     -> C++
README.md      -> Markdown
notes.txt      -> Plain Text
```

---

### Search Highlighting

Search results are now highlighted directly inside the editor.

Features include:

* Highlight all matches
* Highlight current match
* Real-time search updates
* Visual match navigation

Example:

```text
Search: editor [3/12]
```

The selected match is visually distinguished from other matches.

---

### Scroll Bar Support

Added visual scrollbars for large documents.

Supported scrollbars:

* Vertical scrollbar
* Horizontal scrollbar

Features include:

* Proportional thumb sizing
* Viewport position tracking
* Automatic updates while scrolling

Scrollbars provide visual feedback for document position and size.

---

### Markdown Highlighting

Added dedicated Markdown support.

Recognized elements:

* Headers
* Bold text
* Italic text
* Inline code

Example:

```markdown
# Header

**Bold**

*Italic*

`Code`
```

---

### Java Highlighting

Added Java syntax rules.

Recognized elements:

* Keywords
* Types
* Strings
* Numbers
* Comments

Example:

```java
public class Main {
    String name = "JEditor";
}
```

---

### Python Highlighting

Added Python syntax rules.

Recognized elements:

* Keywords
* Built-in types
* Strings
* Numbers
* Comments

Example:

```python
def greet():
    print("Hello")
```

---

### C Highlighting

Added C language syntax support.

Recognized elements:

* Keywords
* Primitive types
* Numbers
* Comments
* Preprocessor directives

Example:

```c
#include <stdio.h>

int main() {
    return 0;
}
```

---

### C++ Highlighting

Added C++ syntax support.

Recognized elements:

* Keywords
* STL types
* Numbers
* Comments
* Preprocessor directives

Example:

```cpp
class Test {
public:
    void run() {}
};
```

---

## User Interface Improvements

* Search result highlighting
* Current match highlighting
* Horizontal scrollbar rendering
* Vertical scrollbar rendering
* Language display in status bar
* Improved visual navigation
* Improved rendering pipeline

---

## Status Bar Improvements

The status bar now displays the active language.

Example:

```text
Ln 12, Col 8 | 450 words | 2800 characters                [Java] [Main.java]
```

Modified documents continue to display a dirty indicator.

Example:

```text
[Main.java *]
```

---

## Internal Improvements

* Added LanguageDefinition architecture
* Added SyntaxHighlighter system
* Added Token model
* Added TokenType classification
* Added LanguageRegistry
* Added SearchHighlighter
* Added ScrollBar rendering system
* Improved rendering pipeline
* Improved document language management

---

## Testing

Current test coverage includes:

### Syntax Highlighting Tests

* Java token recognition
* Python token recognition
* C token recognition
* C++ token recognition
* Markdown token recognition
* Plain text handling

### Search Highlight Tests

* Multiple match highlighting
* Current match highlighting
* Search navigation updates
* No-match scenarios

### Scrollbar Tests

* Vertical scrollbar rendering
* Horizontal scrollbar rendering
* Large document handling
* Viewport synchronization

### Language Detection Tests

* File extension detection
* Unknown file handling
* Plain text fallback

---

## Known Limitations

The following features are planned for future releases:

* Mouse support
* Undo / Redo
* Text selection
* Clipboard operations
* Multiple buffers
* Command palette
* Auto indentation
* Language-aware editing

---

## Next Release

### Version 0.5.0 - Mouse Support

Planned Features:

* Click to move cursor
* Scroll wheel navigation
* Menu bar interaction
* Dialog interaction
* Search panel interaction

Future Enhancements:

* Text selection
* Drag selection

---

Thank you for using JEditor.
