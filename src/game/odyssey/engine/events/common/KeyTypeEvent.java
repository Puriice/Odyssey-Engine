package game.odyssey.engine.events.common;

import java.awt.event.KeyEvent;

public class KeyTypeEvent extends BaseEvent<KeyEvent> {
    public KeyTypeEvent(KeyEvent result) {
        super(result);
    }
}
