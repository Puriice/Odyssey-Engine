package game.odyssey.engine.events.common;

import java.awt.event.MouseEvent;

public class BlurEvent extends BaseEvent<MouseEvent> {
    public BlurEvent(MouseEvent result) {
        super(result);
    }
}
