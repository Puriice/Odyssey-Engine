package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.TickUpdate;
import game.odyssey.engine.entities.Entity;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.renderer.Renderer;
import game.odyssey.engine.utils.Coordinate;

import javax.swing.*;
import java.awt.*;

public class LevelRenderModule extends RenderModule {
    private static final int COUNT_OF_Xi = 60;
    private double dx = 0;
    private double dy = 0;
    private int x = 0, y = 0;
    private final TickUpdate animationTick = new TickUpdate(18);
    private boolean isSetup = false;


    private void setup() {
        if (isSetup) return;

        new Thread(animationTick).start();

        isSetup = true;
    }
    @Override
    public void update(Level level, Player player) {
        setup();
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

        position.setX(pivot.getX() + playerPosition.getX() * Renderer.TILE_PIXEL_WIDE);
        position.setY(pivot.getY() + playerPosition.getY() * Renderer.TILE_PIXEL_HEIGHT);

        if (visualPosition.equals(Coordinate.ZERO)) visualPosition.move(position);

        if (Double.compare(visualPosition.getX(), position.getX()) == 0) dx = 0;
        if (Double.compare(visualPosition.getY(), position.getY()) == 0) dy = 0;

        if (position.floorEqual(visualPosition)) {
            animationTick.perform(null);
            visualPosition.move(position);
            dx = dy = x = y = 0;

            player.resetMoveState();

            g2d.drawImage(
                    imageIcon.getImage(), -position.getIntX(), position.getIntY(), null
            );
        } else {
            animationTick.perform(player::nextMoveState);

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
            visualPosition.move(x, y);

            g2d.drawImage(
                    imageIcon.getImage(), -visualPosition.getIntX(), visualPosition.getIntY(), null
            );
        }

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