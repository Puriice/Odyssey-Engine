package game.odyssey.engine.entities;

import javax.swing.*;

public class Player extends Entity {

    @Override
    public ImageIcon getSprite() {
        if (sprite == null) {
            sprite = new EntitySprite("player");
        }
        return sprite.getSprite().get(facing);
    }


}
