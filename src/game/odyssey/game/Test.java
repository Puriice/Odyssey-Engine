package game.odyssey.game;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Direction;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.events.Event;
import game.odyssey.engine.events.common.MouseClickEvent;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.registries.RegistryObject;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.game.chunks.GameChunk;
import game.odyssey.game.levels.TestLevel;
import game.odyssey.game.objects.Bedrock;

import javax.swing.*;
import java.awt.event.MouseEvent;

@Id(value = "test", name = "Tester Title")
public class Test {
    private static final Register<GameObject> objectRegister = Register.createRegister(Register.Type.OBJECT);
    private static final RegistryObject<GameObject> BEDROCK = objectRegister.enroll(Bedrock::new);
    private static final Register<Level> levelRegister = Register.createRegister(Register.Type.LEVEL);
    public static final RegistryObject<Level> TEST_LEVEL = levelRegister.enroll(TestLevel::new);
    private static final Register<Chunk> chunkRegister = Register.createRegister(Register.Type.CHUNK);
    private static final RegistryObject<Chunk> mc = chunkRegister.enroll(GameChunk::new);

    public Test() {
        Event.addListener(MouseClickEvent.class, this::mouseClickListener);
    }

    private void mouseClickListener(MouseClickEvent event) {
//        event.getResult().
        MouseEvent result = event.getResult();

        if (SwingUtilities.isRightMouseButton(result)) {
            Level level = Game.getGameInstance().getCurrentLevel();

            if (level == null) return;

            Chunk currentChunk = level.getCurrentChunk();

            Coordinate objPosition = getCoordinate();

            try {
                currentChunk.addObject("bedrock", objPosition);
            } catch (IllegalArgumentException ignored) {

            }
        }
    }

    private static Coordinate getCoordinate() {
        Player player = Game.getGameInstance().getPlayer();
        Direction[] facing = player.getFacing();
        Coordinate objPosition = new Coordinate(player.getPosition());


        if (facing[0] == Direction.NORTH) {
            objPosition.addY(1);
        } else if (facing[0] == Direction.EAST){
            objPosition.addX(1);
        } else if (facing[0] == Direction.SOUTH) {
            objPosition.addY(-1);
        } else if (facing[0] == Direction.WEST) {
            objPosition.addX(-1);
        }

        objPosition.move(objPosition.getX() % 16, (16 - objPosition.getY()) % 16);

        return objPosition;
    }
}