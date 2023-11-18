package game.odyssey.game;

import game.odyssey.engine.Id;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.registries.RegistryObject;

@Id(value = "test", name = "Tester")
public class Test {
    private static final Register<GameObject> objectRegister = Register.createRegister(Register.Type.OBJECT);
//    public static final RegistryObject<GameObject> STONE = objectRegister.enroll(TestObj::new);
//    public static final RegistryObject<GameObject> DIRT  = objectRegister.enroll("dirt", GameObject::new);
    public static final RegistryObject<GameObject> BOOKSHELF = objectRegister.enroll("bookshelf", GameObject::new);

    private static final Register<Level> levelRegister = Register.createRegister(Register.Type.LEVEL);

    public static final RegistryObject<Level> TEST_LEVEL = levelRegister.enroll(() -> new TestLevel().setup());

    public Test() {

    }
}
