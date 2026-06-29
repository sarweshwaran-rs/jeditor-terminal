package com.tecs.application.input;

import com.tecs.application.mouse.MouseEvent;

public record MouseInputEvent(MouseEvent event) implements InputEvent{

}
