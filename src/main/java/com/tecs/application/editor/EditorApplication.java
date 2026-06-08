package com.tecs.application.editor;

import com.tecs.application.cli.EditorOptions;
import com.tecs.application.render.ScreenRenderer;
import com.tecs.application.terminal.JLineTerminal;
import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyReader;
import com.tecs.application.terminal.KeyType;

public class EditorApplication {

    @SuppressWarnings("unused")
    private final EditorOptions options;
    
    public EditorApplication(EditorOptions options) {
        this.options = options;
    }

    public void run() {
        JLineTerminal terminal = new JLineTerminal();
        Editor editor = new Editor();
        ScreenRenderer render = new ScreenRenderer(terminal, editor);
        KeyReader keyReader = new KeyReader(terminal.getReader());

        try {
            terminal.enterRawMode();
            terminal.enterAlternateScreen();
            terminal.clearScreen();

            while(true) {
                render.refreshScreen();
                
                Key key = keyReader.readKey();

                if(key.type() == KeyType.CTRL_Q) {
                    break;
                }

                editor.processKey(key);
            }
        } finally {
            terminal.showCursor();
            terminal.exitAlternateScreen();
            terminal.exitRawMode();
            terminal.close();
        }
    }
}
