package game.odyssey.engine.renderer;

import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.utils.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unused")
public class Context {
    public enum Common {
        LEVEL("LEVEL"),
        VISUAL("VISUAL"),
        ENTITY_OBJECT("ENTITY"),
        CENTER("CENTER");

        private final String key;
        Common(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
    public static final Context CONTEXT = new Context();
    private final HashMap<String, Object> data = new HashMap<>();

    public Context() {
        set(Common.VISUAL, new Coordinate());
//        set(Common.ENTITY_OBJECT, new HashMap<String, ArrayList<Coordinate>>());
        set(Common.ENTITY_OBJECT, new ArrayList<GameObject>());

    }

    public Object get(String id) {
        return data.get(id);
    }
    public Object get(Common id) { return data.get(id.getKey()); }

    public void set(String id, Object data) {
        this.data.put(id, data);
    }

    public void set(Common id, Object data) {
        this.data.put(id.getKey(), data);
    }
}
