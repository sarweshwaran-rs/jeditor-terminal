package com.tecs.application.ui.menu;

import java.util.List;

public final class EditMenu implements Menu {

    private final List<MenuItem> items = List.of(
            new MenuItem("Find", "Ctrl+F", MenuCommand.FIND),
            new MenuItem("Go To Line", "Ctrl+G", MenuCommand.GO_TO_LINE)
        );

    @Override
    public String title() {
        return "Edit";
    }

    @Override
    public List<MenuItem> items() {
        return items;
    }
}
