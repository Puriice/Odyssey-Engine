package game.odyssey.engine.servers.modules;

import game.odyssey.engine.renderer.Context;

public abstract class CycleModule {
    Context getRenderContext() {
        return Context.CONTEXT;
    }

    public abstract void cycle();
}
