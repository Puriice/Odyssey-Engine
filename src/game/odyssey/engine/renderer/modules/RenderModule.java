package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;

import java.awt.*;

public abstract class RenderModule {
    Context getContext() {
        return Context.CONTEXT;
    }

    public abstract void update(Level level, Player player);

    public abstract void render(Graphics2D g2d);
}
