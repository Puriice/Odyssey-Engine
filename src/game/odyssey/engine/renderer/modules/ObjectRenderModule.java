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

public class ObjectRenderModule extends RenderModule {
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        Coordinate visualPosition = (Coordinate) getContext().get(Context.Common.VISUAL);
        Chunk[] chunks = ((Level) getContext().get(Context.Common.LEVEL)).getChunks();

        Register<GameObject> register = Register.createRegister(Register.Type.OBJECT);
        ArrayList<GameObject> entityObjects = new ArrayList<>();
        ArrayList<GameObject> gameObjects;

        Coordinate chunkPosition, positionOfChunkInPixel, position;

        Resource sprite;
        int[] ChunkSize = new int[] { Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_WIDTH, Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_HEIGHT };

        for (Chunk chunk: chunks) {
            chunkPosition = chunk.getPosition();
            positionOfChunkInPixel = new Coordinate();

            positionOfChunkInPixel.setX(-visualPosition.getX() + chunkPosition.getX()*ChunkSize[0]);
            positionOfChunkInPixel.setY(visualPosition.getY() + chunkPosition.getY()*ChunkSize[1]);

            gameObjects = chunk.getObjects();

            for (GameObject object: gameObjects) {
                sprite = object.getSprite();

                if (sprite == null) continue;

                position = object.getPosition();

                g2d.drawImage(
                        sprite.getImageIcon().getImage(),
                        positionOfChunkInPixel.getIntX() + position.getIntX() * Renderer.TILE_PIXEL_WIDTH,
                        positionOfChunkInPixel.getIntY() + position.getIntY() * Renderer.TILE_PIXEL_HEIGHT,
                        null
                );

                if (object.getEntityId() != null) {
                    RegistryObject<GameObject> entityObj = register.query(object.getEntityId());

                    if (entityObj == null) continue;

                    GameObject entity = entityObj.get();

                    entity.setPosition(new Coordinate(
                            positionOfChunkInPixel.getIntX() + position.getIntX() * Renderer.TILE_PIXEL_WIDTH,
                            positionOfChunkInPixel.getIntY() + (position.getIntY() - 1) * Renderer.TILE_PIXEL_HEIGHT
                    ));

                    entityObjects.add(entity);
                }
            }
        }
        getContext().set(Context.Common.ENTITY_OBJECT, entityObjects);
    }
}
