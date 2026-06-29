package com.tecs.application.mouse;

import com.tecs.application.editor.Editor;
import com.tecs.application.editor.layout.DocumentPosition;
import com.tecs.application.editor.layout.EditorLayout;
import com.tecs.application.editor.navigation.ViewPort;
import com.tecs.application.editor.navigation.ViewportController;
import com.tecs.application.selection.SelectionController;
import com.tecs.application.ui.menu.MenuBar;
import com.tecs.application.ui.menu.MenuCommand;
import com.tecs.application.ui.menu.MenuLayout;

public final class MouseController {
    private final MenuLayout menuLayout = new MenuLayout();
    private MenuCommand selectedCommand;
    private final SelectionController selectionController;

    public MouseController(SelectionController selectionController) {
        this.selectionController = selectionController;
    }

    public MenuCommand consumeSelectedCommand() {
        MenuCommand command = selectedCommand;
        selectedCommand = null ;
        return command;
    }

    public boolean handle(MouseEvent event, Editor editor, EditorLayout layout, ViewPort viewPort, ViewportController viewportController, MenuBar menuBar) {

        if (event == null) {
            return false;
        }

        return switch (event.type()) {
            case PRESS -> handlePress(event, editor, layout, viewPort, viewportController, menuBar);
            
            case SCROLL_UP -> {
                viewportController.scrollUp(viewPort);
                yield true;
            }
            
            case SCROLL_DOWN -> {
                viewportController.scrollDown(viewPort, editor, layout.textHeight());
                yield true;
            }

            case MOVE -> handleMove(event, layout, menuBar);

            case DRAG -> handleDrag(event, editor, layout, viewPort, viewportController);

            case RELEASE -> {
                selectionController.finish();
                yield true;
            }

            default -> false;
        };
    }

    private boolean handlePress(MouseEvent event, Editor editor, EditorLayout layout, ViewPort viewPort, ViewportController viewportController, MenuBar menuBar) {
        
        if(event.button() != MouseButton.LEFT) {
            return false;
        }

        if (handleMenuClick(event, layout, menuBar)) {
            return true;
        }

        if (handleDropdownClick(event, menuBar)) {
            return true;
        }

        if (handleOutsideClick(event, layout, menuBar)) {
            return true;
        }
        return moveCursor(event, editor, layout, viewPort, viewportController);
    }

    private boolean handleDropdownClick(MouseEvent event, MenuBar menuBar) {
        if (!menuBar.isActive()) {
            return false;
        }

        if (!menuLayout.isInsideDropdown(menuBar, event.row(), event.column())) {
            return false;
        }

        int item = menuLayout.itemAt(menuBar, event.row());

        if (item < 0) {
            return false;
        }

        menuBar.selectItem(item);
        selectedCommand = menuBar.currentItem().command();

        return true;
    }

    private boolean handleMenuClick(MouseEvent event, EditorLayout layout, MenuBar menuBar) {
        if (!layout.isInsideMenubar(event.row())) {
            return false;
        }

        int menu = menuLayout.menuAt(menuBar, event.column());

        if (menu < 0) {
            return false;
        }

        if (!menuBar.isActive()) {
            menuBar.activate();
        }
        menuBar.selectMenu(menu);

        return true;
    }

    private boolean moveCursor(MouseEvent event, Editor editor, EditorLayout layout, ViewPort viewPort, ViewportController viewportController) {

        if(!layout.isInsideEditor(event.row(), event.column())) {
            return false;
        }

        DocumentPosition position = layout.screenToDocument(event.row(), event.column(), viewPort);

        int row = clampRow(position.row(), editor);

        int column = clampColumn(row, position.column(), editor);

        editor.getCursor().setPosition(row, column);

        selectionController.begin(editor);
        viewportController.ensureCursorVisible(editor, layout.textWidth(), layout.textHeight());

        return true;
    }

    private int clampRow(int row, Editor editor) {
        if(editor.getLineCount() == 0) {
            return 0;
        }

        return Math.clamp(row, 0, editor.getLineCount() - 1);
    }

    private int clampColumn(int row, int column, Editor editor) {
        int maxColumn = editor.getLine(row).length();

        return Math.clamp(column, 0, maxColumn);
    }

    private boolean handleOutsideClick(MouseEvent event, EditorLayout layout, MenuBar menuBar) {
        if (!menuBar.isActive()) {
            return false;
        }
        if (layout.isInsideMenubar(event.row())) {
            return false;
        }

        if (menuLayout.isInsideDropdown(menuBar, event.row(), event.column())) {
            return false;
        }

        menuBar.deactivate();
        return true;
    }

    private boolean handleMove(MouseEvent event, EditorLayout layout, MenuBar menuBar) {
        if (handleMenuHover(event, layout, menuBar)) {
            return true;
        }
        return handleDropdownHover(event, menuBar);
    }

    private boolean handleMenuHover(MouseEvent event, EditorLayout layout, MenuBar menuBar) {
        if (!menuBar.isActive()) {
            return false;
        }

        if (!layout.isInsideMenubar(event.row())) {
            return false;
        }

        int menu = menuLayout.menuAt(menuBar, event.column());

        if (menu < 0) {
            return false;
        }

        if (menu == menuBar.selectedMenu()) {
            return false;
        }

        menuBar.selectMenu(menu);
        return true;
    }

    private boolean handleDropdownHover(MouseEvent event, MenuBar menuBar) {
        if (!menuBar.isActive()) {
            return false;
        }

        if (!menuLayout.isInsideDropdown(menuBar, event.row(), event.column())) {
            return false;
        }

        int item = menuLayout.itemAt(menuBar, event.row());

        if (item < 0) {
            return false;
        }

        if (item == menuBar.selectedItem()) {
            return false;
        }
        menuBar.selectItem(item);
        return true;
    }

    private boolean handleDrag(MouseEvent event, Editor editor, EditorLayout layout, ViewPort viewPort, ViewportController controller) {
        if (!layout.isInsideEditor(event.row(), event.column())) {
            return false;
        }

        DocumentPosition positon = layout.screenToDocument(event.row(), event.column(), viewPort);

        int row = clampRow(positon.row(), editor);
        int column = clampColumn(row, positon.column(), editor);
        editor.getCursor().setPosition(row, column);
        selectionController.update(editor);
        controller.ensureCursorVisible(editor, layout.textWidth(), layout.textHeight());

        return true;
    }
}
