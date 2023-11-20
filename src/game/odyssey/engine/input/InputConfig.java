package game.odyssey.engine.input;

import javax.swing.*;
import java.util.HashMap;

@SuppressWarnings("unused")
public abstract class InputConfig {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final InputConfig player = new PlayerInputConfig();

    public static final InputConfig custom = new InputConfig() {
    };

    private final HashMap<Action, KeyStroke> ACTION_HASHMAP = new HashMap<>();

    public void setKey(Action action, KeyStroke input) {
        ACTION_HASHMAP.put(action, input);
    }

    public KeyStroke getKey(Action action) {
        return ACTION_HASHMAP.get(action);
    }
}
