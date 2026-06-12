package com.tecs.application.ui.menu;

public record MenuItem(
    String title,
    String shortcut,
    MenuCommand command
) { }
