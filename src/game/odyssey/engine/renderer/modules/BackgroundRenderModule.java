package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.common.Engine;
import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;

import java.awt.*;

public class BackgroundRenderModule extends RenderModule{
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setPaint(((Level) getContext().get("LEVEL")).getBackground());

        Engine engine = Game.getGameInstance().getEngine();

        g2d.fillRect(0,
                0,
                (int) engine.GAME_WIDTH,
                (int) engine.GAME_HEIGHT
        );
    }
}
