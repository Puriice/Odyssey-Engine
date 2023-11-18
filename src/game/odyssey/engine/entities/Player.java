package game.odyssey.engine.entities;

public class Player extends Entity {

    @Override
    public EntitySprite getSprite() {
        if (sprite == null) {
            sprite = new EntitySprite("player");
        }
        return sprite;
    }


}
