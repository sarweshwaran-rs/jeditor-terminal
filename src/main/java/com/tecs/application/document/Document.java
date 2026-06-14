package com.tecs.application.document;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Document {

    private final List<String> lines;
    private Path filePath;
    private boolean modified;

    public Document() {
        this.lines = new ArrayList<>();
        this.lines.add("");
    }

    public Document(List<String> lines) {
        this.lines = new ArrayList<>(lines);

        if (this.lines.isEmpty()) {
            this.lines.add("");
        }
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public List<String> snapshot() {
        return List.copyOf(lines);
    }

    public int lineCount() {
        return lines.size();
    }

    public String getLine(int row) {
        return lines.get(row);
    }

    public void replaceLine(int row, String value) {
        lines.set(row, value);
        modified = true;
    }

    public void insertLine(int row, String value) {
        lines.add(row, value);
        modified = true;
    }

    public void removeLine(int row) {
        lines.remove(row);
        modified = true;
    }

    public Path getFilePath() {
        return filePath;
    }

    public boolean isModified() {
        return modified;
    }

    public void markSaved(Path path) {
        this.filePath = path;
        this.modified = false;
    }

    public int characterCount() {
        int count = 0;

        for (int i = 0; i < lineCount(); i++) {
            count += lines.get(i).length();

            if (i < lineCount()) {
                count += 1;
            }
        }
        return count;
    }

    public int wordCount() {
        int count = 0;

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.isEmpty()) {
                continue;
            }

            count += trimmed.split("\\s+").length;
        }
        return count;
    }
}
