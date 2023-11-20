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
        char up = InputConfig.player.getKey(new Action(Action.Player.MOVE_UP)).getKeyChar();
        char right = InputConfig.player.getKey(new Action(Action.Player.MOVE_RIGHT)).getKeyChar();
        char down = InputConfig.player.getKey(new Action(Action.Player.MOVE_DOWN)).getKeyChar();
        char left = InputConfig.player.getKey(new Action(Action.Player.MOVE_LEFT)).getKeyChar();
        char interaction = InputConfig.player.getKey(new Action(Action.Player.INTERACT)).getKeyChar();
        char cancel = InputConfig.player.getKey(new Action(Action.Player.CANCEL)).getKeyChar();

        char result = event.getResult().getKeyChar();

        if (result == up) moveUp();
        else if (result == right) moveRight();
        else if (result == down) moveDown();
        else if (result == left) moveLeft();
        else if (result == interaction) interact();
        else if (result == cancel) cancel();
    }
}
