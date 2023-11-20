package game.odyssey.engine.entities;

import game.odyssey.engine.entities.event.PlayerMoveEvent;
import game.odyssey.engine.events.Event;

import javax.swing.*;

public class Player extends Entity {

    public Player() {
        Event.addListener(PlayerMoveEvent.class, this::listener);
    }

    @Override
    public ImageIcon getSprite() {
        if (sprite == null) {
            sprite = new EntitySprite("player");
        }
        return sprite.getSprite().get(facing);
    }

    private void listener(PlayerMoveEvent event) {
        System.out.println("Event trigger");
    }
}
