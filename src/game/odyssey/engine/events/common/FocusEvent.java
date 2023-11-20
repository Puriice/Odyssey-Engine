package game.odyssey.engine.events.common;

import java.awt.event.MouseEvent;

public class FocusEvent extends BaseEvent<MouseEvent> {
    public FocusEvent(MouseEvent result) {
        super(result);
    }
}
