package game.odyssey.engine.events.common;

import game.odyssey.engine.events.Event;

import java.awt.event.KeyEvent;

public class KeyTypeEvent extends BaseKeyEvent {
    public KeyTypeEvent(KeyEvent result) {
        super(result);
    }
}
