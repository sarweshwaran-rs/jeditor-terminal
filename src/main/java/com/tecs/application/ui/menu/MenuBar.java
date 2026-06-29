package com.tecs.application.ui.menu;

import java.util.List;

public class MenuBar {
    private final List<Menu> menus;

    private int selectedMenu;
    private int selectedItem;

    private boolean active;

    public MenuBar(List<Menu> menus) {
        this.menus = menus;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
        selectedMenu = 0;
        selectedItem = 0;
    }

    public void toggle() {
        if(active) {
            deactivate();
        } else {
            activate();
        }
    }
    
    public boolean isActive() {
        return active;
    }

    public void nextMenu() {
        selectedMenu = (selectedMenu + 1) % menus.size();
        selectedItem = 0;
    }

    public void previousMenu() {
        selectedMenu = (selectedMenu - 1 + menus.size()) % menus.size();
        selectedItem = 0;
    }

    public Menu currentMenu() {
        return menus.get(selectedMenu);
    }

    public void nextItem() {
        selectedItem = (selectedItem + 1) % currentMenu().items().size();
    }

    public void previousItem() {
        selectedItem = (selectedItem - 1 + currentMenu().items().size()) % currentMenu().items().size();
    }

    public MenuItem currentItem() {
        return currentMenu().items().get(selectedItem);
    }

    public int menuCount() {
        return menus.size();
    }

    public List<Menu> menus() {
        return menus;
    }

    public int selectedMenu() {
        return selectedMenu;
    }

    public int selectedItem() {
        return selectedItem;
    }

    public void selectMenu(int index) {
        selectedMenu = index;
        selectedItem = 0;
    }

    public void selectItem(int index) {
        selectedItem = index;
    }
}
