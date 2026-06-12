package com.tecs.application.ui.menu;

import java.util.List;

public final class FileMenu implements Menu {

    private final List<MenuItem> items = List.of(
            new MenuItem("New", "Ctrl+N", MenuCommand.NEW),
            new MenuItem("Open", "Ctrl+O", MenuCommand.OPEN),
            new MenuItem("Save", "Ctrl+S", MenuCommand.SAVE),
            new MenuItem("Save As", "", MenuCommand.SAVE_AS),
            new MenuItem("Quit", "Ctrl+Q", MenuCommand.QUIT)
        );

    @Override
    public String title() {
        return "File";
    }

    @Override
    public List<MenuItem> items() {
        return items;
    }
}
