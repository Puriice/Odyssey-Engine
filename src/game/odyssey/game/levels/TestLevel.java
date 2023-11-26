package game.odyssey.game.levels;

import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Entry;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.levels.NoCollision;
import game.odyssey.engine.utils.Coordinate;


@NoCollision
@Entry
@Id("holder")
public class TestLevel extends Level {
    @Override
    public void onStart(Player player) {
        this.setMapPivotInPixel(new Coordinate(24, 3));

        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                this.addChunk("mc", i, j);
            }
        }

//        this.setSpawnPoint(16, 16);
    }

    @Override
    public void onHitChunkBorder(Player player, Coordinate playerPosition, Coordinate chunkPosition) {
//        System.out.println("chunkPosition = " + chunkPosition);
    }

    @Override
    public void onUpdate(Player player, Coordinate playerPosition) {

    }

    @Override
    public void onLeave(Player player) {

    }
}
