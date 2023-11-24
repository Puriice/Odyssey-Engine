package game.odyssey.engine.common;

@SuppressWarnings("unused")
public class TickUpdate implements Runnable
{
//    public static final int TICK_RATE = 20;
    private transient boolean isRunning = true;

    private final int TICK_RATE;
    private Runnable operation;

    public TickUpdate(int TICK_RATE) {
        this.TICK_RATE = TICK_RATE;
    }

    public void shutdown() {
        isRunning = false;
    }

    public void perform(Runnable operation) {
        this.operation = operation;
    }

    @Override
    public void run() {
        final int NANO = 1000000000;
//        long prevTime = System.nanoTime(), current, timeDelta;
//        while (isRunning) {
//            current = System.nanoTime();
//            timeDelta = current - prevTime;
//
//            // make game update every 1/TICK seconds
//            if (timeDelta < NANO / TICK_RATE) continue;
//            prevTime = current;
//
//            try {
//                if (operation != null) {
//                    operation.run();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
        double drawInterval = (double) NANO / TICK_RATE;

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(isRunning) {

            currentTime = System.nanoTime();

            delta +=(currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1 ) {
                delta--;
                drawCount++;
                try {
                    if (operation != null) {
                        operation.run();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(timer >= NANO) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
}
