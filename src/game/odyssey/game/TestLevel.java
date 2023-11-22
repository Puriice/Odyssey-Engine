package game.odyssey.game;

import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Entry;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.utils.Coordinate;

@Entry
@Id("levelone")
public class TestLevel extends Level {
    @Override
    public void onStart(Player player) {
        this.setMapPivotInPixel(new Coordinate(24, 0));
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

//        this.setBackground(Color.BLUE);
    }

    @Override
    public void onUpdate(Player player, Coordinate playerPosition) {

    }

    @Override
    public void onLeave(Player player) {

    }
}
