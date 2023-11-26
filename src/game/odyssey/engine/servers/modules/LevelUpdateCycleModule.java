package game.odyssey.engine.servers.modules;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.renderer.Context;
import game.odyssey.engine.utils.Coordinate;

public class LevelUpdateCycleModule extends CycleModule {
    @Override
    public void cycle() {
        Level level = (Level) getRenderContext().get(Context.Common.LEVEL);

        if (level == null) return;

        Player player = Game.getGameInstance().getPlayer();

        level.onUpdate(player, new Coordinate(player.getPosition()).readOnly());
    }
}
