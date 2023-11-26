package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.renderer.Context;
import game.odyssey.engine.utils.Coordinate;

import java.awt.*;

public class PlayerRenderModule extends RenderModule{
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        Coordinate center = (Coordinate) getContext().get(Context.Common.CENTER);

        Player player = Game.getGameInstance().getPlayer();

        try {
            g2d.drawImage(
                    player.getSprite().getImage(),
                    (int) -center.getX(),
                    (int) -center.getY() - 24,
                    (int) -center.getX() + Player.ENTITY_WIDTH,
                    (int) -center.getY() - 24 + Player.ENTITY_HEIGHT,
                    player.getMoveState() * Player.ENTITY_WIDTH,
                    0,
                    player.getMoveState() * Player.ENTITY_WIDTH + Player.ENTITY_WIDTH,
                    Player.ENTITY_HEIGHT,
                    null
            );
        } catch (NullPointerException ignored) {

        }
    }
}
