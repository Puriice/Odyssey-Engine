package game.odyssey.engine.events.common;

import game.odyssey.engine.events.Event;

import java.awt.event.KeyEvent;

public class BaseEvent<T> extends Event {
    private final T result;

    public BaseEvent(T result) {
        this.result = result;
        this.setCancelable(false);
    }

    public T getKeyEvent() {
        return result;
    }
}
