package game.odyssey.engine.events.common;

import game.odyssey.engine.events.Event;

public class BaseEvent<T> extends Event {
    private final T result;

    public BaseEvent(T result) {
        this.result = result;
        this.setCancelable(false);
    }

    @SuppressWarnings("unused")
    public T getResult() {
        return result;
    }
}
