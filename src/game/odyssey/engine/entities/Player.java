package game.odyssey.engine.entities;

import game.odyssey.engine.entities.event.PlayerMoveEvent;
import game.odyssey.engine.events.Event;
import game.odyssey.engine.input.Action;

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
        return sprite.getSprite().get(facing[0]);
    }

    private void interact() {

    }

    private void cancel() {

    }
    private void listener(PlayerMoveEvent event) {
        int[] direction = event.getDirection();
        char interaction = PlayerMoveEvent.getKeyConfig(Action.Player.INTERACT);
        char cancel = PlayerMoveEvent.getKeyConfig(Action.Player.CANCEL);

        char result = event.getResult().getKeyChar();

        moveBy(direction[0] * this.speedMultiplier, direction[1] * this.speedMultiplier);

        if (result == interaction) interact();
        else if (result == cancel) cancel();
    }

}
