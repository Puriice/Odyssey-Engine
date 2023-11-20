package game.odyssey.engine.events.common;

import java.awt.event.MouseEvent;

public class MouseClickEvent extends BaseEvent<MouseEvent> {
    public MouseClickEvent(MouseEvent result) {
        super(result);
    }
}
