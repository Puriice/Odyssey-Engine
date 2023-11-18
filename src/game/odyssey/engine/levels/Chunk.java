package game.odyssey.engine.levels;

import game.odyssey.engine.entities.Player;
import game.odyssey.engine.utils.Coordinate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Chunk {
    public enum State {
        ACTIVE,
        LAZY,
        BORDER,
        GHOST
    }

    public static final int CHUNK_TILE_WIDTH = 16;
    public static final int CHUNK_TILE_HEIGHT = 16;

    private State state = State.GHOST;
    private final HashMap<String, ArrayList<Coordinate>> OBJECTS = new HashMap<>();
    private ImageIcon map;

    public State getState() {
        return state;
    }

    void update(State state) {
        this.state = state;
    }

    public void addObject(String objectId, Coordinate... position) {

        if (!this.OBJECTS.containsKey(objectId)) {
            this.OBJECTS.put(objectId, new ArrayList<>(List.of(position)));
        } else {
            for (Coordinate pos: position) {
                if (isOverlap(pos)) throw new IllegalArgumentException("Object is overlap with another object");

                this.OBJECTS.get(objectId).add(pos);
            }
        }
    }

    private boolean isOverlap(Coordinate position) {
        for (ArrayList<Coordinate> v: this.OBJECTS.values()) {
            for (Coordinate pos: v) {
                if (pos.equals(position)) return true;
            }
        }

        return false;
    }

    public HashMap<String, ArrayList<Coordinate>> getObjects() {
        return OBJECTS;
    }

    public ImageIcon getMap() {
        return map;
    }

    public void setMap(ImageIcon map) {
        this.map = map;
    }

    protected void onStateChange() {};
    protected abstract void build();
    protected abstract void start(Player player, Coordinate playerCoordinate);
    protected abstract void destroy(Player player, Coordinate playerCoordinate);
}
