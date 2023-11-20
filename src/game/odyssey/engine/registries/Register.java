package game.odyssey.engine.registries;

import game.odyssey.engine.common.Id;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.objects.GameObject;

import java.util.HashMap;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class Register<T> {
    public enum Type {
        OBJECT,
        LEVEL,
        CHUNK
    }
    private static final HashMap<Type, Register<?>> register = new HashMap<>();
    @SuppressWarnings("unchecked")
    public static <T> Register<T> createRegister(Type type) {
        if (!register.containsKey(type)) {
            switch (type) {
                case OBJECT -> register.put(type, new Register<GameObject>());
                case LEVEL -> register.put(type, new Register<Level>());
                case CHUNK -> register.put(type, new Register<Chunk>());
            }
        }

        return (Register<T>) register.get(type);
    }

    private final HashMap<String, RegistryObject<T>> REGISTRIES = new HashMap<>();

    public RegistryObject<T> enroll(Supplier<T> supplier) {
        String id = supplier.get().getClass().getAnnotation(Id.class).value();

        return this.enroll(id, supplier);
    }


    public RegistryObject<T> enroll(String id, Supplier<T> supplier) {
        RegistryObject<T> toReturn = new RegistryObject<>(id, supplier);

//        Object obj = supplier.get();
//        if (obj instanceof Level lvl) lvl.onStart(Game.getGameInstance().getPlayer());
        REGISTRIES.put(id, toReturn);

        return toReturn;
    }

    public HashMap<String, RegistryObject<T>> query() {
        return REGISTRIES;
    }
}
