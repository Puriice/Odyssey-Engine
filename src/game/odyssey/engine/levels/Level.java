package game.odyssey.engine.levels;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Resource;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public abstract class Level {
    private final ArrayList<Chunk> CHUNKS = new ArrayList<>();
    private Color background = Color.BLACK;
    private ImageIcon map;
    private Coordinate pivot = Coordinate.ZERO;
    public Level() {

    }

    protected void addChunk(Chunk chunk, int row, int column) {
        chunk.setPosition(new Coordinate(column, row).readOnly());
        chunk.build();
        this.CHUNKS.add(chunk);
    }

    public Chunk[] getChunks() {
        return this.CHUNKS.toArray(new Chunk[0]);
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public ImageIcon getMap() {
        try {
            String levelId = getId();
            return Resource.resolve("/" + Game.getGameInstance().GAME_ID + "/assets/levels/" + levelId + ".png");
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private String getId() {
        if (this.getClass().isAnnotationPresent(Id.class))
            return this.getClass().getAnnotation(Id.class).value();
        else return "";
    }

    protected void setMapPivotInPixel(Coordinate pivot) {
        this.pivot = pivot;
    }

    public Coordinate getPivot() {
        return this.pivot;
    }

    protected void navigate() {
        this.onLeave(Game.getGameInstance().getPlayer());
    }

    public abstract void onStart(Player player);

    public abstract void onUpdate(Player player, Coordinate playerPosition);

    public abstract void onLeave(Player player);
}
