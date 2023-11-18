package game.odyssey.engine.events.bus;

import game.odyssey.engine.events.Event;

import java.util.function.Consumer;

public interface IEventBus {
    void register(Class<?> eventClass);
    void addListener(Consumer<Event> consumer);
}
