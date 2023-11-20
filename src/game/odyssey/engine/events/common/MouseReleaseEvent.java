package game.odyssey.engine.events.common;

import java.awt.event.MouseEvent;

public class MouseReleaseEvent extends BaseEvent<MouseEvent> {
    public MouseReleaseEvent(MouseEvent result) {
        super(result);
    }
}
