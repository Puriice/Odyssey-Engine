package game.odyssey.engine.renderer.modules;

import java.util.HashMap;

public class Context {
    public static final Context CONTEXT = new Context();
    private final HashMap<String, Object> data = new HashMap<>();

    public Object get(String id) {
        return data.get(id);
    }

    public void set(String id, Object data) {
        this.data.put(id, data);
    }
}
