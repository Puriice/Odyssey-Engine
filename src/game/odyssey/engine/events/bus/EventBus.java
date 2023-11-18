package game.odyssey.engine.events.bus;

import game.odyssey.engine.events.Event;

import java.util.function.Consumer;

class EventBus implements IEventBus {

    @Override
    public void register(Class<?> eventClass) {

    }

    @Override
    public void addListener(Consumer<Event> consumer) {

    }
}
