package game.odyssey.game;

import game.odyssey.engine.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Entry;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.utils.Coordinate;

@Id("levelone")
@Entry
public class TestLevel extends Level {
    public Level setup() {
        this.addChunk(new Chunk() {
            @Override
            protected void build() {
                this.addObject("bookshelf", new Coordinate(0,0));
            }

            @Override
            protected void start(Player player, Coordinate playerCoordinate) {

            }

            @Override
            protected void destroy(Player player, Coordinate playerCoordinate) {

            }
        }, 0, 0);

        return this;
    }

    @Override
    public void onStart(Player player) {

    }

    @Override
    public void onUpdate(Player player, Coordinate playerPosition) {

    }

    @Override
    public void onLeave(Player player) {

    }
}
