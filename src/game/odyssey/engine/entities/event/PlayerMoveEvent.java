package game.odyssey.engine.entities.event;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.events.common.KeyPressEvent;
import game.odyssey.engine.input.Action;
import game.odyssey.engine.input.InputConfig;
import game.odyssey.engine.utils.Coordinate;

import java.awt.event.KeyEvent;

public class PlayerMoveEvent extends KeyPressEvent {

    public PlayerMoveEvent(KeyEvent result) {
        super(result);
        this.setCancelable(false);
    }

    public Player getPlayer() {
        return Game.getGameInstance().getPlayer();
    }

    public Coordinate getPlayerCoordinate() {
        return new Coordinate(getPlayer().getPosition()).readOnly();
    }

    public char getKeyConfig(Action.Player action) {
        return InputConfig.player.getKey(new Action(action)).getKeyChar();
    }
}
