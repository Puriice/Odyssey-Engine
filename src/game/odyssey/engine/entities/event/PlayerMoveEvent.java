package game.odyssey.engine.entities.event;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.events.common.KeyPressEvent;
import game.odyssey.engine.input.Action;
import game.odyssey.engine.input.InputConfig;
import game.odyssey.engine.utils.Coordinate;

import java.awt.event.KeyEvent;

@SuppressWarnings("unused")
public class PlayerMoveEvent extends KeyPressEvent {
    // [up,right, down, left]
    public static final boolean[] isKeyPress = new boolean[4];
    public static char getKeyConfig(Action.Player action) {
        return InputConfig.player.getKey(new Action(action)).getKeyChar();
    }

    private static char[] getKeyChar() {
        char up = PlayerMoveEvent.getKeyConfig(Action.Player.MOVE_UP);
        char right = PlayerMoveEvent.getKeyConfig(Action.Player.MOVE_RIGHT);
        char down = PlayerMoveEvent.getKeyConfig(Action.Player.MOVE_DOWN);
        char left = PlayerMoveEvent.getKeyConfig(Action.Player.MOVE_LEFT);

        return new char[]{up, right, down, left};
    }
    public static void setDirection(KeyEvent keyEvent) {
        char[] keyChar = getKeyChar();
        char result = keyEvent.getKeyChar();

        for (int i = 0; i < keyChar.length; i++) {
            if (result == keyChar[i]) {
                isKeyPress[i] = true;
            }
        }
    }

    public static void removeDirection(KeyEvent keyEvent) {
        char[] keyChar = getKeyChar();
        char result = keyEvent.getKeyChar();

        for (int i = 0; i < keyChar.length; i++) {
            if (result == keyChar[i]) {
                isKeyPress[i] = false;
            }
        }
    }

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

    public int[] getDirection() {
//        System.out.println("isKeyPress = " + Arrays.toString(isKeyPress));
        int[] direction = new int[] {0, 0};

        if (isKeyPress[0]) {
            direction[1] += 1;
        }

        if (isKeyPress[1]) {
            direction[0] += 1;
        }

        if (isKeyPress[2]) {
            direction[1] -= 1;
        }

        if (isKeyPress[3]) {
            direction[0] -= 1;
        }

        return direction;
    }

//    public char getKeyConfig(Action.Player action) {
//        return InputConfig.player.getKey(new Action(action)).getKeyChar();
//    }
}
