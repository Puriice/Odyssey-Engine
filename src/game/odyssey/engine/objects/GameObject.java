package game.odyssey.engine.objects;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.Id;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Resource;

import java.io.FileNotFoundException;

public class GameObject {
    private String id;
    private String entityId;
    private Coordinate position;

    public Resource getSprite() {
        try {
            return new Resource( Game.getGameInstance().GAME_ID + "/assets/objects/" + getId() + ".png");
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        if (id == null && this.getClass().isAnnotationPresent(Id.class)) {
            id = this.getClass().getAnnotation(Id.class).value();
        }

        return id;
    }

    public String getEntityId() {
        return entityId;
    }

    public GameObject setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public void onInteract() {}
}
