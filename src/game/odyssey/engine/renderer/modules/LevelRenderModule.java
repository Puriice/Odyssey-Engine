package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.TickUpdate;
import game.odyssey.engine.entities.Entity;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.renderer.Context;
import game.odyssey.engine.renderer.Renderer;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Rectangle;

import javax.swing.*;
import java.awt.*;

import static game.odyssey.engine.entities.Entity.ENTITY_WIDTH;

public class LevelRenderModule extends RenderModule {
    private static final int COUNT_OF_Xi = 60;
    private double dx = 0;
    private double dy = 0;
    private int x = 0, y = 0;
    private final TickUpdate animationTick = new TickUpdate(30);

    public LevelRenderModule() {
        new Thread(animationTick).start();
    }

    @Override
    public void update(Level level, Player player) {
    }
    @Override
    public void render(Graphics2D g2d) {
        Level level = ((Level) getContext().get(Context.Common.LEVEL));

        ImageIcon imageIcon = level.getMap();

        if (imageIcon == null) return;

        Coordinate pivot = level.getPivot();
        Player player = Game.getGameInstance().getPlayer();

        Coordinate playerPosition = player.getPosition();

        Coordinate position  = new Coordinate();
        Coordinate visualPosition = (Coordinate) getContext().get(Context.Common.VISUAL);

        position.setX(pivot.getX() + playerPosition.getX() * Renderer.TILE_PIXEL_WIDTH);
        position.setY(-pivot.getY() + playerPosition.getY() * Renderer.TILE_PIXEL_HEIGHT);

        if (visualPosition.equals(Coordinate.ZERO)) {
            visualPosition.move(position);
        }


        if (Double.compare(visualPosition.getX(), position.getX()) == 0) dx = 0;
        if (Double.compare(visualPosition.getY(), position.getY()) == 0) dy = 0;


        if (position.equals(visualPosition)) {
            animationTick.perform(null);
            visualPosition.move(position);
            dx = dy = x = y = 0;

            player.resetMoveState();
        } else {
            boolean[] possibleMove = getPossibleTileMoving(visualPosition, position, level);

            if (!possibleMove[0]) {
                playerPosition.translate(0, -1);
            }
            if (!possibleMove[1]) {
                playerPosition.translate(-1, 0);
            }
            if (!possibleMove[2]) {
                playerPosition.translate(0, 1);
            }
            if (!possibleMove[3]) {
                playerPosition.translate(1, 0);
            }
//            System.out.println(Arrays.toString());
            position.setX(pivot.getX() + playerPosition.getX() * Renderer.TILE_PIXEL_WIDTH);
            position.setY(-pivot.getY() + playerPosition.getY() * Renderer.TILE_PIXEL_HEIGHT);

            double y2 = position.getY();
            double y1 = visualPosition.getY();
            double x2 = position.getX();
            double x1 = visualPosition.getX();

            if (dx == 0) dx = x2 - x1;
            if (dy == 0) dy = y2 - y1;

            double x, y, slope;

            if (dx != 0) {
                slope = calculateSlope(x2, x1, COUNT_OF_Xi, 0);

                x = calculateLinearInterpolation(slope, 0, x1, this.x);

                addX(Entity.MOVE_STATE_COUNT);
            } else {
                x = x1;
            }


            if (dy != 0) {
                slope = calculateSlope(y2, y1, COUNT_OF_Xi, 0);

                y = calculateLinearInterpolation(slope, 0, y1, this.y);

                addY(Entity.MOVE_STATE_COUNT);
            } else {
                y = y1;
            }

//            player.nextMoveState();
            animationTick.perform(player::nextMoveState);
            visualPosition.move(x, y);
        }


        g2d.drawImage(
                imageIcon.getImage(), -visualPosition.getIntX(), visualPosition.getIntY(), null
        );
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private void checkIfItOutSideMap(Coordinate target, Level level, boolean[] toReturn) {

        ImageIcon map = level.getMap();

        Coordinate center = ((Coordinate) getContext().get(Context.Common.CENTER));
        Coordinate pivot = ((Level) getContext().get(Context.Common.LEVEL)).getPivot();

        target.translate(-center.getX(), center.getY() - Renderer.TILE_PIXEL_HEIGHT / 2);

        boolean isOutsideTopOfTheMap = -target.getY() < 0;
        boolean isOutsideRightOfTheMap = target.getX() > map.getIconWidth() - pivot.getX();
        boolean isOutsideBottomOfTheMap = -target.getY() > map.getIconHeight() - pivot.getY();
        boolean isOutsideLeftOfTheMap = target.getX() < 0;

        if (isOutsideTopOfTheMap) toReturn[0] = false;
        if (isOutsideRightOfTheMap) toReturn[1] = false;
        if (isOutsideBottomOfTheMap) toReturn[2] = false;
        if (isOutsideLeftOfTheMap) toReturn[3] = false;

    }

    private boolean checkIfItOutsideChunk(Coordinate pivot, Coordinate target, Rectangle chunkFrame, boolean[] toReturn) {
        int[] ChunkSize = new int[] { Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_WIDTH, Chunk.CHUNK_TILE_HEIGHT*Renderer.TILE_PIXEL_HEIGHT };
        target.translate(-pivot.getX(), pivot.getY());
//        System.out.println("Target = " + target.getY());
//        System.out.println("Y top left = " + ((-(chunkFrame.getTopLeft().getY() + 1) * ChunkSize[1]) + ENTITY_WIDTH));
//        System.out.println("Y bottom left = " + chunkFrame.getBottomLeft().getY());

        boolean isOutsideTopOfTheChunk = target.getY() > -chunkFrame.getBottomLeft().getY() * ChunkSize[1];
        boolean isOutsideRightOfTheChunk = target.getX() > (chunkFrame.getTopRight().getX() + 1) * ChunkSize[0] - ENTITY_WIDTH;
        boolean isOutsideBottomOfTheChunk = target.getY() < (-chunkFrame.getTopLeft().getY() - 1) *ChunkSize[1] + ENTITY_WIDTH;
        boolean isOutsideLeftOfTheChunk = target.getX() < chunkFrame.getTopLeft().getX();

        if (isOutsideTopOfTheChunk) toReturn[0] = false;
        if (isOutsideRightOfTheChunk) toReturn[1] = false;
        if (isOutsideBottomOfTheChunk) toReturn[2] = false;
        if (isOutsideLeftOfTheChunk) toReturn[3] = false;

        return isOutsideTopOfTheChunk || isOutsideRightOfTheChunk || isOutsideBottomOfTheChunk || isOutsideLeftOfTheChunk;
//        return false;
    }
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private boolean[] getPossibleTileMoving(Coordinate visual, Coordinate target, Level level) {
        boolean[] toReturn = new boolean[] { true, true, true, true };

        if (visual.equals(target)) return toReturn;

        Coordinate pivot = level.getPivot();

        Coordinate newTarget = new Coordinate(target);

        checkIfItOutSideMap(newTarget, level, toReturn);

        newTarget = new Coordinate(target);

        boolean isHitBorder = checkIfItOutsideChunk(pivot, newTarget, level.getChunkFrame(), toReturn);


        Player player = Game.getGameInstance().getPlayer();

        if (isHitBorder) {
            level.onHitChunkBorder(
                    player,
                    new Coordinate(player.getPosition()).readOnly(),
                    new Coordinate(
                            player.getPosition().getIntX() / Chunk.CHUNK_TILE_WIDTH,
                            -player.getPosition().getIntY() * Renderer.TILE_PIXEL_HEIGHT / Chunk.CHUNK_TILE_HEIGHT
                    )
            );
        }

//        System.out.println(Arrays.toString(toReturn));
//        toReturn[0] =false;

        return toReturn;
    }

    private double calculateLinearInterpolation(double m, double x1, double y1, double x) {
        try {
            return y1 + m*(x - x1);
        } catch (ArithmeticException e) {
            return 0;
        }
    }
    private double calculateSlope(double y2, double y1, double x2, double x1) {
        return (y2 - y1) / (x2 - x1);
    }

    private void addX(int x) {
        this.x += x;

        if (this.x > COUNT_OF_Xi) {
            this.x = 0;
        }
    }

    private void addY(int y) {
        this.y += y;

        if (this.y > COUNT_OF_Xi) {
            this.y = 0;
        }
    }
}
