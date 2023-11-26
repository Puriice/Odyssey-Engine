package game.odyssey.engine.common;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class TickUpdate implements Runnable
{
//    public static final int TICK_RATE = 20;
    private transient boolean isRunning = true;
    private final int TICK_RATE;
    protected Runnable operation;
    private Consumer<Integer> listener;

    public TickUpdate(int TICK_RATE) {
        this.TICK_RATE = TICK_RATE;
    }

    public void shutdown() {
        isRunning = false;
    }

    public void perform(Runnable operation) {
        this.operation = operation;
    }

    public void performEverySecond(Consumer<Integer> listener) { this.listener = listener; }
    @Override
    public void run() {
        final int NANO = 1000000000;
        long prevTime = System.nanoTime(), current, timeDelta = 0L, sumTimeDelta = 0L;
        int drawCount = 0;

        while (isRunning) {
            current = System.nanoTime();
            timeDelta += current - prevTime;
            sumTimeDelta += current - prevTime;
            prevTime = current;

            if (sumTimeDelta > NANO) {
                if (listener != null) listener.accept(drawCount);

                sumTimeDelta = drawCount = 0;
            }

            // make update every 1/TICK seconds
            if (timeDelta < NANO / TICK_RATE) continue;

            timeDelta = 0;
            drawCount++;

            try {
                if (operation != null) {
                    operation.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
