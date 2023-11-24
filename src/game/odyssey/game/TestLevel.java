package game.odyssey.game;

import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Entry;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.utils.Coordinate;

import java.util.function.Supplier;

@Entry
@Id("levelone")
public class TestLevel extends Level {
    @Override
    public void onStart(Player player) {
        this.setMapPivotInPixel(new Coordinate(24, 0));
        Supplier<Chunk> chunk = () -> new Chunk() {
            @Override
            protected void build() {
                System.out.println("Test");
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 8; j++) {
                        this.addObject("bookshelf", new Coordinate(i, 2*j + 1));
                    }
                }
            }

            @Override
            protected void start(Player player, Coordinate playerCoordinate) {

            }

            @Override
            protected void destroy(Player player, Coordinate playerCoordinate) {

            }
        };

        this.addChunk(chunk.get(), 0, 0);
        this.addChunk(chunk.get(), 0 ,1);
        this.addChunk(chunk.get(), 1 ,0);
        this.addChunk(chunk.get(), 1 ,1);

//        this.setBackground(Color.BLUE);
    }

    @Override
    public void onUpdate(Player player, Coordinate playerPosition) {

    }

    @Override
    public void onLeave(Player player) {

    }
}
