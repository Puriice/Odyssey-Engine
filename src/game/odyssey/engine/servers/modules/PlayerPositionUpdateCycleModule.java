package game.odyssey.engine.servers.modules;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.renderer.Renderer;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Rectangle;

import javax.swing.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static game.odyssey.engine.entities.Entity.ENTITY_WIDTH;
import static game.odyssey.engine.levels.Chunk.CHUNK_TILE_HEIGHT;
import static game.odyssey.engine.levels.Chunk.CHUNK_TILE_WIDTH;
import static game.odyssey.engine.renderer.Context.Common.*;
import static game.odyssey.engine.renderer.Renderer.TILE_PIXEL_HEIGHT;
import static game.odyssey.engine.renderer.Renderer.TILE_PIXEL_WIDTH;

public class PlayerPositionUpdateCycleModule extends CycleModule{
    @Override
    public void cycle() {
        Player player = Game.getGameInstance().getPlayer();
        Coordinate targetPosition = player.getTargetPosition();

        if (targetPosition.equals(Coordinate.ZERO)) return;


        Coordinate visualPosition = (Coordinate) getRenderContext().get(VISUAL);
        Level level = (Level) getRenderContext().get(LEVEL);


        Coordinate playerPosition = player.getPosition();

        Coordinate targetPlayerPosition = new Coordinate(playerPosition);
        Coordinate pivot = level.getPivot();

        targetPlayerPosition.translate(targetPosition);

        Coordinate position = new Coordinate();

        position.setX(pivot.getX() + targetPlayerPosition.getX() * TILE_PIXEL_WIDTH);
        position.setY(-pivot.getY() + targetPlayerPosition.getY() * TILE_PIXEL_HEIGHT);

        boolean possibleMove = getPossibleTileMoving(visualPosition, position, level, targetPlayerPosition);


        if (possibleMove) {
            player.move(new Coordinate(targetPlayerPosition));
        }

        player.resetTargetPosition();
    }


    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private boolean checkIfItOutSideMap(Coordinate target, Level level) {
        ImageIcon map = level.getMap();

        Coordinate center = ((Coordinate) getRenderContext().get(CENTER));
        Coordinate pivot = ((Level) getRenderContext().get(LEVEL)).getPivot();

        target.translate(-center.getX(), center.getY() - TILE_PIXEL_HEIGHT / 2);

        boolean isOutsideTopOfTheMap = -target.getY() < 0;
        boolean isOutsideRightOfTheMap = target.getX() > map.getIconWidth() - pivot.getX();
        boolean isOutsideBottomOfTheMap = -target.getY() > map.getIconHeight() - pivot.getY();
        boolean isOutsideLeftOfTheMap = target.getX() < 0;

        return !(isOutsideTopOfTheMap || isOutsideRightOfTheMap || isOutsideBottomOfTheMap || isOutsideLeftOfTheMap);

//        return true;
    }

    private boolean checkIfItOutsideChunk(Coordinate pivot, Coordinate target, Rectangle chunkFrame) {
        int[] chunkSize = new int[] { CHUNK_TILE_WIDTH* TILE_PIXEL_WIDTH, Chunk.CHUNK_TILE_HEIGHT* TILE_PIXEL_HEIGHT };
        target.translate(-pivot.getX(), pivot.getY());

        boolean isOutsideTopOfTheChunk = target.getY() > -chunkFrame.getBottomLeft().getY() * chunkSize[1];
        boolean isOutsideRightOfTheChunk = target.getX() > (chunkFrame.getTopRight().getX() + 1) * chunkSize[0] - ENTITY_WIDTH;
        boolean isOutsideBottomOfTheChunk = target.getY() < (-chunkFrame.getTopLeft().getY() - 1) *chunkSize[1] + ENTITY_WIDTH;
        boolean isOutsideLeftOfTheChunk = target.getX() < chunkFrame.getTopLeft().getX();

        return !(isOutsideTopOfTheChunk || isOutsideRightOfTheChunk || isOutsideBottomOfTheChunk || isOutsideLeftOfTheChunk);
    }

    private Coordinate getObjectPosition(Coordinate object, Coordinate chunk) {
        Coordinate position = new Coordinate();

        position.setX(object.getX() + (chunk.getX() * CHUNK_TILE_WIDTH));
        position.setY(-object.getY() + (chunk.getY() * -CHUNK_TILE_HEIGHT));

        return position;
    }

    private boolean checkIfHitGameObject(Coordinate targetPlayerPosition, Level level) {
        Chunk currentChunk = level.getCurrentChunk();

        AtomicInteger i = new AtomicInteger(0);

        List<Chunk> surroundedChunk = level.getChunkGraph().bfs(currentChunk, c -> i.getAndIncrement() < 2);


        for (Chunk c: surroundedChunk) {
            GameObject[] gameObjects= c.getObjects();
            for (GameObject object: gameObjects) {
                Coordinate objectPosition = getObjectPosition(object.getPosition(), c.getPosition());
                if (targetPlayerPosition.equals(objectPosition)) return false;
            }
        }


        return true;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private boolean getPossibleTileMoving(Coordinate visual, Coordinate target, Level level, Coordinate playerTarget) {
        boolean toReturn = true;

        if (visual.equals(target)) return toReturn;

        Coordinate pivot = level.getPivot();

        Coordinate newTarget = new Coordinate(target);

        if (level.willDoBorderCollision()) {
            toReturn = checkIfItOutSideMap(newTarget, level);

            if (!toReturn) return false;

        }

        newTarget = new Coordinate(target);

        toReturn = checkIfItOutsideChunk(pivot, newTarget, level.getChunkFrame());


        Player player = Game.getGameInstance().getPlayer();

        if (!toReturn) {
            level.onHitChunkBorder(
                    player,
                    new Coordinate(player.getPosition()).readOnly(),
                    new Coordinate(
                            player.getPosition().getIntX() / Chunk.CHUNK_TILE_WIDTH,
                            -player.getPosition().getIntY() * Renderer.TILE_PIXEL_HEIGHT / Chunk.CHUNK_TILE_HEIGHT
                    )
            );

            return false;
        }


        toReturn = checkIfHitGameObject(new Coordinate(playerTarget).readOnly(), level);

        return toReturn;
    }

}
