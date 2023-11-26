package game.odyssey.game.chunks;

import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.utils.Coordinate;

@Id("mc")
public class GameChunk extends Chunk {
    @Override
    protected void build() {
        for (int i = 0; i < 15; i++) {
            addObject("bookshelf", new Coordinate(i,6));
        }
    }

    @Override
    protected void start(Player player, Coordinate playerCoordinate) {

    }

    @Override
    protected void destroy(Player player, Coordinate playerCoordinate) {

    }
}
