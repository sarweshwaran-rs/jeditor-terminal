package com.tecs.application.cli;

public class EditorOptions {
    private boolean showHelp;
    private boolean showVersion;
    private boolean lineNumbers = true;
    private String fileName;
    private boolean readOnly;
    private String theme = "default";

    public boolean isShowHelp() {
        return showHelp;
    }

    public void setShowHelp(boolean showHelp) {
        this.showHelp = showHelp;
    }

    public boolean isShowVersion() {
        return showVersion;
    }

    public void setShowVersion(boolean showVersion) {
        this.showVersion = showVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isLineNumbers() {
        return lineNumbers;
    }

    public void setLineNumbers(boolean lineNumbers) {
        this.lineNumbers = lineNumbers;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
