package game.odyssey.engine.objects;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.Id;
import game.odyssey.engine.utils.Resource;

import java.io.FileNotFoundException;

public class GameObject {
    private String id;

    public Resource getResource() {
        try {
            return new Resource(Game.getGameInstance().GAME_ID + "/assets/objects/" + getId());
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

    public void onInteract() {};
}
