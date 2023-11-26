package game.odyssey.engine.servers;

import game.odyssey.engine.common.TickUpdate;
import game.odyssey.engine.servers.modules.CycleModule;
import game.odyssey.engine.servers.modules.LevelUpdateCycleModule;
import game.odyssey.engine.servers.modules.PlayerPositionUpdateCycleModule;

import java.util.ArrayList;

public class ServerTick extends TickUpdate {
    private Runnable customOperation;
    private final ArrayList<CycleModule> cycleModules = new ArrayList<>();
    public ServerTick() {
        super(20);

        cycleModules.add(new LevelUpdateCycleModule());
        cycleModules.add(new PlayerPositionUpdateCycleModule());

        this.operation = this::cycle;
    }

    @Override
    public void perform(Runnable operation) {
        this.customOperation = operation;
    }

    private void cycle() {
        cycleModules.forEach(CycleModule::cycle);

        try {
            if (this.customOperation != null) {
                this.customOperation.run();
            }
        } catch (Exception e) {
            System.out.println("Exception is occur on Server Thread " + e.getMessage());
        }
    }
}
