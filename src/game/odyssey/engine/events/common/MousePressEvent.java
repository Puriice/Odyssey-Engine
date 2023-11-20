package game.odyssey.engine.events.common;

import java.awt.event.MouseEvent;

public class MousePressEvent extends BaseEvent<MouseEvent> {
    public MousePressEvent(MouseEvent result) {
        super(result);
    }
}
