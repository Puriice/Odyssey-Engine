package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.registries.RegistryObject;
import game.odyssey.engine.renderer.Context;
import game.odyssey.engine.renderer.Renderer;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Resource;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjectRenderModule extends RenderModule {
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        Coordinate visualPosition = (Coordinate) getContext().get(Context.Common.VISUAL);
        Chunk[] chunks = ((Level) getContext().get(Context.Common.LEVEL)).getChunks();

        for (Chunk chunk: chunks) {
            Coordinate chunkPosition = chunk.getPosition();
            Coordinate positionOfChunkInPixel = new Coordinate();

            positionOfChunkInPixel.setX(-visualPosition.getX() + chunkPosition.getX()*Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_WIDTH);
            positionOfChunkInPixel.setY(visualPosition.getY() + chunkPosition.getY()*Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_HEIGHT);

            HashMap<String, ArrayList<Coordinate>> gameObjects = chunk.getObjects();
            Register<GameObject> objectRegister = Register.createRegister(Register.Type.OBJECT);

            for (Map.Entry<String, ArrayList<Coordinate>> entry: gameObjects.entrySet()) {
                RegistryObject<GameObject> registry = objectRegister.query(entry.getKey());

                GameObject object = registry.get();
                Resource sprite = object.getSprite();

                if (sprite == null) continue;

                for (Coordinate position: entry.getValue()) {
                    g2d.drawImage(
                            sprite.getImageIcon().getImage(),
                            positionOfChunkInPixel.getIntX() + position.getIntX() * Renderer.TILE_PIXEL_WIDTH,
                            positionOfChunkInPixel.getIntY() + position.getIntY() * Renderer.TILE_PIXEL_HEIGHT,
                            null
                    );
                }
            }
        }
    }
}
