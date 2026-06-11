package com.tecs.application.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.tecs.application.document.Document;
import com.tecs.application.exceptions.FileOperationException;


public class LocalFileManager implements FileManager {

    @Override
    public Document open(Path path) {
        if(!Files.exists(path)) {
            Document document = new Document();
            document.markSaved(path);
            return document;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            Document document = new Document(lines);
            document.markSaved(path);
            return document;
        } catch(IOException ex) {
            throw new FileOperationException("Failed to open file: " + path, ex);
        }
    }

    @Override
    public void save(Document document) {
        Path path = document.getFilePath();

        saveAs(document, path);
    }

    @Override
    public void saveAs(Document document, Path path) {
        try {
            Path parent = path.getParent();
            
            if(parent != null) {
                Files.createDirectories(parent);
            }
            
            Files.write(path, document.snapshot());
            document.markSaved(path);
        } catch (IOException e) {
            throw new FileOperationException("Failed to save file: " + path, e);
        }
    }

    @Override
    public Document createNew() {
        return new Document();
    }
}
