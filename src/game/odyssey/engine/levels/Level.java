package game.odyssey.engine.levels;

import game.odyssey.engine.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.utils.Coordinate;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Level {
    private final ArrayList<Chunk> CHUNKS = new ArrayList<>();
    private ImageIcon background;
    private ImageIcon map;
    private Coordinate pivot = Coordinate.ZERO;
    public Level() {

    }

    protected void addChunk(Chunk chunk) {
        chunk.build();
        this.CHUNKS.add(chunk);
    }

    public Chunk[] getChunks() {
        return this.CHUNKS.toArray(new Chunk[0]);
    }

    public ImageIcon getBackground() {
        return background;
    }

    public void setBackground(ImageIcon background) {
        this.background = background;
    }

    public ImageIcon getMap() {
        return map;
    }

    public void setMap(ImageIcon map) {
        this.map = map;
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
