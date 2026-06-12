package com.tecs.application.ui.menu;

import java.util.List;

public class HelpMenu implements Menu {

    private final List<MenuItem> items = List.of(
        new MenuItem("About", "", MenuCommand.ABOUT)
    );

    @Override
    public String title() {
        return "Help";
    }

    @Override
    public List<MenuItem> items() {
        return items;
    }
}
