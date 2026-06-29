package com.tecs.application.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.tecs.application.input.keyboard.KeyboardParser;
import com.tecs.application.input.mouse.MouseParser;
import com.tecs.application.mouse.MouseButton;
import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.mouse.MouseEventType;
import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyType;

public class KeyboardParserTest {
    @Test
    void shouldParseCtrlA() {
        KeyboardParser parser = new KeyboardParser();

        Key key = parser.parseControl(1);

        assertEquals(KeyType.CTRL_A, key.type());
    }

    @Test
    void shouldParseCharacter() {
        KeyboardParser parser = new KeyboardParser();

        Key key = parser.parseCharacter('A');

        assertEquals(KeyType.CHARACTER, key.type());
        assertEquals('A', key.character());
    }

    @Test
    void shouldParseSpace() {
        KeyboardParser parser = new KeyboardParser();

        Key key = parser.parseCharacter(32);

        assertEquals(KeyType.SPACE, key.type());
    }

    @Test
    void shouldParseArrowEscape() {
        KeyboardParser parser = new KeyboardParser();

        Key key = parser.parseEscape('[', 'A', () -> -1);

        assertEquals(KeyType.ARROW_UP, key.type());
    }

    @Test
    void shouldParseLeftClick() {
        MouseParser parser = new MouseParser();

        MouseEvent event = parser.parse("\u001B[<0;10;20M");

        assertEquals(MouseButton.LEFT, event.button());
        assertEquals(MouseEventType.PRESS, event.type());
    }

    @Test
    void shouldParseRelease() {
        MouseParser parser = new MouseParser();

        MouseEvent event = parser.parse("\u001B[<0;10;20m");

        assertEquals(MouseEventType.RELEASE, event.type());
    }

    @Test
    void shouldParseScrollUp() {
        MouseParser parser = new MouseParser();

        MouseEvent event = parser.parse("\u001B[<64;10;20M");

        assertEquals(MouseEventType.SCROLL_UP, event.type());
    }

    @Test
    void shouldReturnNullForInvalidSequence() {
        MouseParser parser = new MouseParser();

        assertNull(parser.parse("ABC"));
    }
}
