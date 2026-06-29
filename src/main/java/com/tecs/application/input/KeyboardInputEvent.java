package com.tecs.application.input;

import com.tecs.application.terminal.Key;

public record KeyboardInputEvent(Key key) implements InputEvent {
}
