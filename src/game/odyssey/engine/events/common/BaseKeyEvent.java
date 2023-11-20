package game.odyssey.engine.events.common;

import game.odyssey.engine.events.Event;

import java.awt.event.KeyEvent;

public class BaseKeyEvent extends Event {
    private final KeyEvent result;

    public BaseKeyEvent(KeyEvent result) {
        this.result = result;
    }

    public KeyEvent getKeyEvent() {
        return result;
    }
}
