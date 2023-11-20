package game.odyssey.engine.events;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public abstract class Event {
    private static final HashMap<Class<?>, ArrayList<Consumer<Event>>> LISTENER = new HashMap<>();
//    private static final Queue<Event> QUEUE = new LinkedList<>();
    public static <T extends Event> void createEvent(Class<T> event) {
        if (LISTENER.containsKey(event)) return;

        LISTENER.put(event, new ArrayList<>());
    }

    public static <T extends Event> ArrayList<Consumer<T>> getListener(Class<T> eventClass) {
        @SuppressWarnings("unchecked")
        ArrayList<Consumer<T>> toReturn =  (ArrayList<Consumer<T>>) (ArrayList<?>) LISTENER.get(eventClass);

        return toReturn;
    }

    public static <T extends Event> void addListener(Class<T> eventClass, Consumer<T> listener) {
        LISTENER.get(eventClass).add((Consumer<Event>) listener);
    }
    private boolean isCanceled = false;
    private boolean isCancelable = true;

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel() {
        if (!this.isCancelable) return;
        isCanceled = true;
    }

    protected void setCancelable(boolean b) {
        this.isCancelable = b;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean dispatch() {
        for (Consumer<Event> listener: LISTENER.get(this.getClass())) {
            listener.accept( this);
        }

        return this.isCanceled();
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean dispatch(Consumer<Boolean> afterListener) {
        boolean toReturn = this.dispatch();
        afterListener.accept(toReturn);

        return toReturn;
    }
}
