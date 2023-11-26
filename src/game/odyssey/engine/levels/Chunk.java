package game.odyssey.engine.levels;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.registries.RegistryObject;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Resource;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

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
//    private final HashMap<String, ArrayList<Coordinate>> OBJECTS = new HashMap<>();
    private final ArrayList<GameObject> OBJECTS = new ArrayList<>();
    private ImageIcon map;
    private Coordinate position = Coordinate.ZERO;

    public State getState() {
        return state;
    }

    void update(State state) {
        this.state = state;
        onStateChange();
    }

//    public void addObject(String objectId, Coordinate... position) {
//
//        if (!this.OBJECTS.containsKey(objectId)) {
//            this.OBJECTS.put(objectId, new ArrayList<>(List.of(position)));
//        } else {
//            for (Coordinate pos: position) {
//                if (pos.getX() > Chunk.CHUNK_TILE_WIDTH || pos.getX() < 0|| pos.getY() > Chunk.CHUNK_TILE_HEIGHT || pos.getY() < 0) {
//                    System.out.println("Object is outside of Chunk");
//                }
//                if (isOverlap(pos)) throw new IllegalArgumentException("Object is overlap with another object");
//
//                this.OBJECTS.get(objectId).add(pos);
//            }
//        }
//    }

    public void addObject(String objectId, Coordinate... position) {
        Register<GameObject> objectRegister = Register.createRegister(Register.Type.OBJECT);

        for (Coordinate pos: position) {
            if (pos.getX() > Chunk.CHUNK_TILE_WIDTH || pos.getX() < 0|| pos.getY() > Chunk.CHUNK_TILE_HEIGHT || pos.getY() < 0) {
                System.out.println("Object is outside of Chunk");
                return;
            }

            if (isOverlap(pos)) throw new IllegalArgumentException("Object is overlap with another object");

            RegistryObject<GameObject> registry = objectRegister.query(objectId);

            if (registry == null) return;

            GameObject gameObject = registry.get();

            gameObject.setPosition(pos);

            this.OBJECTS.add(gameObject);
        }
    }

//    private boolean isOverlap(Coordinate position) {
//        for (ArrayList<Coordinate> v: this.OBJECTS.values()) {
//            for (Coordinate pos: v) {
//                if (pos.equals(position)) return true;
//            }
//        }
//
//        return false;
//    }

    private boolean isOverlap(Coordinate position) {
        for (GameObject object: this.OBJECTS) {
            if (object.getPosition().equals(position)) return true;
        }

        return false;
    }
//    public HashMap<String, ArrayList<Coordinate>> getObjects() {
//        return OBJECTS;
//    }

    public ArrayList<GameObject> getObjects() {
        return OBJECTS;
    }

    public ImageIcon getMap() {
        if (map != null) return map;
        try {
            String chunkId = getId();
            return Resource.resolve("/" + Game.getGameInstance().GAME_ID + "/assets/chunks/" + chunkId + ".png");
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private String getId() {
        if (this.getClass().isAnnotationPresent(Id.class))
            return this.getClass().getAnnotation(Id.class).value();
        else return "";
    }
    public void setMap(ImageIcon map) {
        this.map = map;
    }

    public Coordinate getPosition() {
        return position;
    }

    void setPosition(Coordinate position) {
        this.position = position;
    }

    protected void onStateChange() {}
    protected abstract void build();
    protected abstract void start(Player player, Coordinate playerCoordinate);
    protected abstract void destroy(Player player, Coordinate playerCoordinate);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chunk chunk = (Chunk) o;
        return Objects.equals(position, chunk.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
