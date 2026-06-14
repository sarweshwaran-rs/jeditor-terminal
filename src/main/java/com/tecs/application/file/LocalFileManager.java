package com.tecs.application.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import com.tecs.application.document.Document;
import com.tecs.application.exceptions.FileOperationException;

public class LocalFileManager implements FileManager {

    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of(
            "txt",
            "java",
            "py",
            "c",
            "cpp",
            "h",
            "hpp",
            "cs",
            "md",
            "json",
            "xml",
            "yaml",
            "toml",
            "properties"
        );

    @Override
    public Document open(Path path) {
        if (!Files.exists(path)) {
            Document document = new Document();
            document.markSaved(path);
            return document;
        }

        String fileName = path.getFileName().toString();

        int dot = fileName.lastIndexOf('.');

        if (dot > 0) {
            String extension = fileName.substring(dot + 1).toLowerCase();

            if (!SUPPORTED_EXTENSIONS.contains(extension)) {
                throw new FileOperationException("Unsupported file type: ." + extension);
            }
        }

        try {
            List<String> lines = Files.readAllLines(path);
            Document document = new Document(lines);
            document.markSaved(path);
            return document;
        } catch (IOException ex) {
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

            if (parent != null) {
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
