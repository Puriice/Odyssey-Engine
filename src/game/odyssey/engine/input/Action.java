package game.odyssey.engine.input;

public record Action(Object action) {
    public enum Player {
        MOVE_UP,
        MOVE_RIGHT,
        MOVE_DOWN,
        MOVE_LEFT,
        INTERACT,
        CANCEL
    }
}
