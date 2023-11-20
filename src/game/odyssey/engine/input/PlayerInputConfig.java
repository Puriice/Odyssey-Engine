package game.odyssey.engine.input;

import javax.swing.*;

class PlayerInputConfig extends InputConfig{
    PlayerInputConfig() {
        this.setKey(new Action(Action.Player.MOVE_UP), KeyStroke.getKeyStroke('w'));
        this.setKey(new Action(Action.Player.MOVE_RIGHT), KeyStroke.getKeyStroke('d'));
        this.setKey(new Action(Action.Player.MOVE_DOWN), KeyStroke.getKeyStroke('s'));
        this.setKey(new Action(Action.Player.MOVE_LEFT), KeyStroke.getKeyStroke('a'));
        this.setKey(new Action(Action.Player.INTERACT), KeyStroke.getKeyStroke('f'));
        this.setKey(new Action(Action.Player.CANCEL), KeyStroke.getKeyStroke('x'));
    }
}
