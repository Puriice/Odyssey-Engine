package game.odyssey.engine.entities;

import game.odyssey.engine.entities.event.PlayerMoveEvent;
import game.odyssey.engine.events.Event;
import game.odyssey.engine.input.Action;
import game.odyssey.engine.input.InputConfig;

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

    private void interact() {

    }

    private void cancel() {

    }
    private void listener(PlayerMoveEvent event) {
        char up = event.getKeyConfig(Action.Player.MOVE_UP);
        char right = event.getKeyConfig(Action.Player.MOVE_RIGHT);
        char down = event.getKeyConfig(Action.Player.MOVE_DOWN);
        char left = event.getKeyConfig(Action.Player.MOVE_LEFT);
        char interaction = event.getKeyConfig(Action.Player.INTERACT);
        char cancel = event.getKeyConfig(Action.Player.CANCEL);

        char result = event.getResult().getKeyChar();

        if (result == up) moveUp();
        else if (result == right) moveRight();
        else if (result == down) moveDown();
        else if (result == left) moveLeft();
        else if (result == interaction) interact();
        else if (result == cancel) cancel();
    }
}
