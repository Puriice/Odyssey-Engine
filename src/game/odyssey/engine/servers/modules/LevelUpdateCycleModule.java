package game.odyssey.engine.servers.modules;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.renderer.Context;
import game.odyssey.engine.utils.Coordinate;

import java.util.concurrent.atomic.AtomicInteger;

public class LevelUpdateCycleModule extends CycleModule {
    @Override
    public void cycle() {
        Level level = (Level) getRenderContext().get(Context.Common.LEVEL);

        Chunk currentChunk = level.getCurrentChunk();
        Player player = Game.getGameInstance().getPlayer();

        Coordinate playerPosition = player.getPosition();

        if (isPlayerInChunk(playerPosition, currentChunk.getPosition())) return;

        AtomicInteger i = new AtomicInteger();

        level.getChunkGraph().bfs(currentChunk, c -> i.getAndIncrement() < 8,  (chunks) -> {
            for (Chunk chunk: chunks) {
                if (isPlayerInChunk(playerPosition, chunk.getPosition())) {
                    level.setCurrentChunk(chunk);
                }
            }
        });
    }

    private boolean isPlayerInChunk(Coordinate playerPosition, Coordinate chunkPosition) {
        double x = chunkPosition.getX();
        double y = -chunkPosition.getY();

        boolean isInX = (x * Chunk.CHUNK_TILE_WIDTH) <= playerPosition.getX() && playerPosition.getX() < (x + 1) * Chunk.CHUNK_TILE_WIDTH;
        boolean isInY = (y * Chunk.CHUNK_TILE_HEIGHT) >= playerPosition.getY() && playerPosition.getY() > (y - 1) * Chunk.CHUNK_TILE_HEIGHT;

        return isInX && isInY;
    }
}
