package com.tecs.application.file;

import java.nio.file.Path;

import com.tecs.application.document.Document;

public interface FileManager {
    Document open(Path path);
    void save(Document document);
    void saveAs(Document document, Path path);
    Document createNew();
}
