package game.odyssey.engine.registries;

import game.odyssey.engine.objects.GameObject;

import java.util.function.Supplier;

public class RegistryObject<T> {
    private final Supplier<T> object;
    private final String id;
    public RegistryObject(String id, Supplier<T> object) {
        this.id = id;
        this.object = object;
    }

    public T get() {
        T toReturn = object.get();

        if (toReturn instanceof GameObject obj && obj.getId() == null) obj.setId(id);

        return toReturn;
    }

    public String getId() {
        return id;
    }
}
