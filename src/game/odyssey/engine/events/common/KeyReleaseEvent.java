package game.odyssey.engine.events.common;

import java.awt.event.KeyEvent;

public class KeyReleaseEvent extends BaseEvent<KeyEvent> {
    public KeyReleaseEvent(KeyEvent result) {
        super(result);
    }
}
