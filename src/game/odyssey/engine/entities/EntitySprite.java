package game.odyssey.engine.entities;

import game.odyssey.engine.common.Game;
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

        int w = Entity.ENTITY_WIDTH * Entity.MOVE_STATE_COUNT, h = Entity.ENTITY_HEIGHT;
        try {
            SPRITE.put(NORTH, new Resource(template + "/north.png").scale(w, h).getImageIcon());
            SPRITE.put(EAST, new Resource(template + "/east.png").scale(w, h).getImageIcon());
            SPRITE.put(SOUTH, new Resource(template + "/south.png").scale(w, h).getImageIcon());
            SPRITE.put(WEST, new Resource(template + "/west.png").scale(w, h).getImageIcon());
        } catch (FileNotFoundException ignore) {
            System.out.println("Can not found sprite of " + entityId);
        }
    }

    public HashMap<Direction, ImageIcon> getSprite() {
        return SPRITE;
    }
}
