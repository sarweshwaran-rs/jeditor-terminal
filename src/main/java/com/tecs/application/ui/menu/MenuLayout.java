package com.tecs.application.ui.menu;

public final class MenuLayout {

    public int menuAt(MenuBar menuBar, int column) {
        int left = 1;

        for (int i = 0; i < menuBar.menuCount(); i++) {
            Menu menu = menuBar.menus().get(i);

            int width = menu.title().length() + 2;

            if (column >= left && column < left + width) {
                return i;
            }

            left += width + 1;
        }
        return -1;
    }

    public boolean isInsideDropdown(MenuBar menuBar, int row, int column) {
        if (!menuBar.isActive()) {
            return false;
        }
        MenuBounds bounds = dropdownBounds(menuBar);

        return row >= bounds.top()
                && row < bounds.top() + bounds.height()
                && column >= bounds.left()
                && column < bounds.left() + bounds.width();
    }

    public MenuBounds dropdownBounds(MenuBar menuBar) {
        Menu menu = menuBar.currentMenu();

        int left = 1;

        for (int i = 0; i< menuBar.selectedMenu(); i++) {
            left += menuBar.menus().get(i).title().length() + 3;
        }

        int width = calculateMenuWidth(menu);

        int top = 2;

        int height = menu.items().size() + 2;

        return new MenuBounds(left, top, width, height);
    }

    private int calculateMenuWidth(Menu menu) {
        int width = 0;

        for (MenuItem item : menu.items()) {
            String line = item.shortcut().isBlank() ? item.title()
                    : item.title() + " " + item.shortcut();

            width = Math.max(width, line.length());
        }

        return width + 4;
    }

    public int itemAt(MenuBar menuBar, int row) {
        if (!menuBar.isActive()) {
            return -1;
        }

        MenuBounds bounds = dropdownBounds(menuBar);

        int item = row - (bounds.top() + 1);

        if (item < 0) {
            return -1;
        }

        if (item >= menuBar.currentMenu().items().size()) {
            return -1;
        }

        return item;
    }
}
