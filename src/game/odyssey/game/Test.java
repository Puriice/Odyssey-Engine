package game.odyssey.game;

import game.odyssey.engine.common.Id;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.registries.RegistryObject;

@Id(value = "test", name = "Tester Title")
public class Test {
    private static final Register<GameObject> objectRegister = Register.createRegister(Register.Type.OBJECT);
    public static final RegistryObject<GameObject> BOOKSHELF = objectRegister.enroll("bookshelf", () -> new GameObject().setEntityId("top_bookshelf"));
//    public static final RegistryObject<GameObject> BOOKSHELF = objectRegister.enroll("bookshelf", GameObject::new);
    private static final RegistryObject<GameObject> TOP_BOOKSHELF = objectRegister.enroll("top_bookshelf", GameObject::new);
    private static final Register<Level> levelRegister = Register.createRegister(Register.Type.LEVEL);
    public static final RegistryObject<Level> TEST_LEVEL = levelRegister.enroll(TestLevel::new);

    public Test() {

    }
}
