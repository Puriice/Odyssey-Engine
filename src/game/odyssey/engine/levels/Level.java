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

@SuppressWarnings("unused")
public abstract class Level {
    private final ArrayList<Chunk> CHUNKS = new ArrayList<>();
    private Color background = Color.BLACK;
    private ImageIcon map;
    private Coordinate pivot = Coordinate.ZERO;
    private Coordinate visualPosition = Coordinate.ZERO;
//    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
//    private final ArrayList<GameObject> entityObjects = new ArrayList<>();
    public Level() {

    }

    protected void addChunk(Chunk chunk, int row, int column) {
        chunk.build();
        chunk.setPosition(new Coordinate(column, row).readOnly());

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
            if (map == null) {
                String levelId = getId();
                map = Resource.resolve(Game.getGameInstance().GAME_ID + "/assets/levels/" + levelId + ".png");
            }
            return map;
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

    protected void setMapPivotInPixel(double x, double y) {
        this.pivot = new Coordinate(x, y);
    }

    public Coordinate getPivot() {
        return this.pivot;
    }

    public Coordinate getPosition() {
        return visualPosition;
    }

    public void setPosition(Coordinate visualPosition) {
        this.visualPosition = visualPosition;
    }

    protected void navigate() {
        this.onLeave(Game.getGameInstance().getPlayer());
    }

//    public void buildObject() {
//        this.gameObjects.clear();
//        this.entityObjects.clear();
//
////        int[] ChunkSize = new int[] { Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_WIDTH, Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_HEIGHT };
//
//        Register<GameObject> register = Register.createRegister(Register.Type.OBJECT);
//        ArrayList<GameObject> gameObjects;
//        GameObject gameObject, entityObject;
//
//        Coordinate chunkPosition, positionOfChunk, position;
//        RegistryObject<GameObject> objectRegistry, entityRegistry;
//
//        for (Chunk chunk: CHUNKS) {
//            chunkPosition = chunk.getPosition();
//
//            positionOfChunk = new Coordinate();
//
//            positionOfChunk.setX(chunkPosition.getX()*Chunk.CHUNK_TILE_WIDTH);
//            positionOfChunk.setY(chunkPosition.getY()*Chunk.CHUNK_TILE_HEIGHT);
//
//            gameObjects = chunk.getObjects();
//
//            for (GameObject obj: gameObjects) {
//                objectRegistry = register.query(obj.getId());
//
//                position = obj.getPosition();
//
//                gameObject = objectRegistry.get();
//                gameObject.setPosition(new Coordinate(
//                        positionOfChunk.getX() + position.getX(),
//                        positionOfChunk.getY() + position.getY()
//                ));
//
//                this.gameObjects.add(gameObject);
//
//                if (obj.getEntityId() != null) {
//                    entityRegistry = register.query(obj.getEntityId());
//
//                    if (entityRegistry == null) continue;
//
//                    entityObject = entityRegistry.get();
//
//                    entityObject.setPosition(new Coordinate(
//                        positionOfChunk.getX() + position.getX(),
//                        positionOfChunk.getY() + position.getY() + 1
//                    ));
//
//                    entityObjects.add(entityObject);
//                }
//            }
//        }
//    }
//    public GameObject[] getGameObjects() {
//        return gameObjects.toArray(new GameObject[0]);
//    }
//
//    public GameObject[] getEntityObjects() {
//        return entityObjects.toArray(new GameObject[0]);
//    }

    // on Register
    public abstract void onStart(Player player);

    // everytime appear on screen
    public abstract void onUpdate(Player player, Coordinate playerPosition);

    // everytime disappear on screen
    public abstract void onLeave(Player player);
}
