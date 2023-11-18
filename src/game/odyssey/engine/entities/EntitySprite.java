package game.odyssey.engine.entities;

import game.odyssey.engine.Game;
import game.odyssey.engine.utils.Resource;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.HashMap;

@SuppressWarnings("unused")
public class EntitySprite {
    public static final Direction NORTH = Direction.NORTH;
    public static final Direction EAST = Direction.EAST;
    public static final Direction SOUTH = Direction.SOUTH;
    public static final Direction WEST = Direction.WEST;
    private final HashMap<Direction, ImageIcon> SPRITE = new HashMap<>();
    public EntitySprite(String entityId) {
        String gameId = Game.getGameInstance().GAME_ID;
        String template = gameId + "/assets/entities/" + entityId;

        try {
            SPRITE.put(NORTH, Resource.resolve(template + "/north.png"));
            SPRITE.put(EAST, Resource.resolve(template + "/east.png"));
            SPRITE.put(SOUTH, Resource.resolve(template + "/south.png"));
            SPRITE.put(WEST, Resource.resolve(template + "/west.png"));
        } catch (FileNotFoundException ignore) {
            System.out.println("Can not found sprite of " + entityId);
        }
    }

    public HashMap<Direction, ImageIcon> getSprite() {
        return SPRITE;
    }
}
