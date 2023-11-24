package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.renderer.Renderer;
import game.odyssey.engine.utils.Coordinate;

import java.awt.*;

public class PlayerRenderModule extends RenderModule{
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        Coordinate center =
                new Coordinate(
                        (int) ((Renderer.TILE_PIXEL_WIDTH - Game.getGameInstance().getEngine().GAME_WIDTH) / 2.0),
                        (int) ((Renderer.TILE_PIXEL_HEIGHT - Game.getGameInstance().getEngine().GAME_HEIGHT) / 2.0)
                );

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
