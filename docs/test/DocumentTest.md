# Document Tests

## Covered Components

* Document

---

## Test Cases

| Test                             | Purpose                             |
| -------------------------------- | ----------------------------------- |
| shouldStartsWithSingleEmptyLine  | Verify default state                |
| shouldInsertLine                 | Verify insertion                    |
| shouldRemoveLine                 | Verify deletion                     |
| shouldReplaceLine                | Verify replacement                  |
| shouldCountCharacters            | Verify character counting           |
| shouldCountCharactersAcrossLines | Verify multiline character counting |
| shouldCountWords                 | Verify word counting                |
| shouldIgnoreMultipleSpaces       | Verify word parsing                 |
| shouldIgnoreEmptyLines           | Verify empty line handling          |
| shouldMarkModifiedAfterReplace   | Verify modified tracking            |
| shouldMarkModifiedAfterInsert    | Verify modified tracking            |
| shouldMarkModifiedAfterRemove    | Verify modified tracking            |

---

## Current Coverage

Covered:

* Document creation
* Line insertion
* Line deletion
* Line replacement
* Character counting
* Word counting
* Modified state tracking

---

## Total Document Tests

12
