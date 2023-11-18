package game.odyssey.engine.events;

public class Event {
    private boolean isCanceled = false;

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel(boolean canceled) {
        isCanceled = canceled;
    }
}
