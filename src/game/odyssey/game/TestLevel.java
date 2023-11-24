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
//                this.addObject("bookshelf", new Coordinate(0,1));
//                this.addObject("bookshelf", new Coordinate(1,1));
//                this.addObject("bookshelf", new Coordinate(2,1));
//                this.addObject("bookshelf", new Coordinate(3,1));
//                this.addObject("bookshelf", new Coordinate(4,1));
//                this.addObject("bookshelf", new Coordinate(5,1));
//                this.addObject("bookshelf", new Coordinate(6,1));
//                this.addObject("bookshelf", new Coordinate(7,1));
//                this.addObject("bookshelf", new Coordinate(8,1));
//                this.addObject("bookshelf", new Coordinate(9,1));
//                this.addObject("bookshelf", new Coordinate(10,1));
//                this.addObject("bookshelf", new Coordinate(11,1));
//                this.addObject("bookshelf", new Coordinate(12,1));
//                this.addObject("bookshelf", new Coordinate(13,1));
//                this.addObject("bookshelf", new Coordinate(14,1));
//                this.addObject("bookshelf", new Coordinate(15,1));
//                this.addObject("bookshelf", new Coordinate(0,3));
//                this.addObject("bookshelf", new Coordinate(1,3));
//                this.addObject("bookshelf", new Coordinate(2,3));
//                this.addObject("bookshelf", new Coordinate(3,3));
//                this.addObject("bookshelf", new Coordinate(4,3));
//                this.addObject("bookshelf", new Coordinate(5,3));
//                this.addObject("bookshelf", new Coordinate(6,3));
//                this.addObject("bookshelf", new Coordinate(7,3));
//                this.addObject("bookshelf", new Coordinate(8,3));
//                this.addObject("bookshelf", new Coordinate(9,3));
//                this.addObject("bookshelf", new Coordinate(10,3));
//                this.addObject("bookshelf", new Coordinate(11,3));
//                this.addObject("bookshelf", new Coordinate(12,3));
//                this.addObject("bookshelf", new Coordinate(13,3));
//                this.addObject("bookshelf", new Coordinate(14,3));
//                this.addObject("bookshelf", new Coordinate(15,3));
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
        }, 1, 1);

//        this.setBackground(Color.BLUE);
    }

    @Override
    public void onUpdate(Player player, Coordinate playerPosition) {

    }

    @Override
    public void onLeave(Player player) {

    }
}
