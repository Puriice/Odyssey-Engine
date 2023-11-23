package game.odyssey.engine.utils;

@SuppressWarnings("unused")
public class Timer {
    private static final long NANO_TO_MILLI = 1000000;
    private int duration;
    private long prevTime;
    private boolean isStarted = false;

    public Timer(int ms) {
        this.duration = ms;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int ms) {
        this.duration = ms;
    }

    public void start() {
        if (isStarted) return;

        prevTime = System.nanoTime();

        isStarted = true;
    }

    public void reset() {
        this.prevTime = System.nanoTime();
    }

    public void turnOff() {
        isStarted = false;
    }

    public boolean isTime() {
        if ((System.nanoTime() - this.prevTime) / NANO_TO_MILLI >= this.duration) {
            this.reset();
            return true;
        }
        return false;
    }
}
