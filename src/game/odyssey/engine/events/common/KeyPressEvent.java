package game.odyssey.engine.events.common;

import java.awt.event.KeyEvent;

public class KeyPressEvent extends BaseEvent<KeyEvent> {
    public KeyPressEvent(KeyEvent result) {
        super(result);
    }
}
