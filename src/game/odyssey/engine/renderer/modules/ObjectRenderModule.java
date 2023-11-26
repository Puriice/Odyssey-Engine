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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ObjectRenderModule extends RenderModule {
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        Coordinate visualPosition = (Coordinate) getContext().get(Context.Common.VISUAL);
        Coordinate center = (Coordinate) getContext().get(Context.Common.CENTER);
        Chunk[] chunks = ((Level) getContext().get(Context.Common.LEVEL)).getChunks();

        Register<GameObject> register = Register.createRegister(Register.Type.OBJECT);
        ArrayList<GameObject> entityObjects = new ArrayList<>();
        GameObject[] gameObjects;

        Coordinate chunkPosition, positionOfChunkInPixel, position;

        Resource sprite;
        int[] chunkSize = new int[] { Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_WIDTH, Chunk.CHUNK_TILE_HEIGHT*Renderer.TILE_PIXEL_HEIGHT };

        for (Chunk chunk: chunks) {
            chunkPosition = chunk.getPosition();
            positionOfChunkInPixel = new Coordinate();

            positionOfChunkInPixel.setX(-center.getX() + 24 -visualPosition.getX() + chunkPosition.getX()*chunkSize[0]);
            positionOfChunkInPixel.setY(-center.getY() + 6 + visualPosition.getY() + chunkPosition.getY()*chunkSize[1]);

            ImageIcon bg = chunk.getMap();

            if (bg != null) {
                g2d.drawImage(bg.getImage(), positionOfChunkInPixel.getIntX(), positionOfChunkInPixel.getIntY(), chunkSize[0], chunkSize[1], null);
            }

            gameObjects = chunk.getObjects();

            for (GameObject object: gameObjects) {
                sprite = object.getSprite();

                if (sprite == null) continue;

                position = object.getPosition();

                g2d.drawImage(
                        sprite.getImageIcon().getImage(),
                        (int) (positionOfChunkInPixel.getX() + position.getIntX() * Renderer.TILE_PIXEL_WIDTH),
                        (int) (positionOfChunkInPixel.getY() + position.getIntY() * Renderer.TILE_PIXEL_HEIGHT),
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
//        ArrayList<GameObject>
//        GameObject[] gameObjects = ((Level) getContext().get(Context.Common.LEVEL)).getGameObjects();
//        Resource sprite;
//        Coordinate visualPosition = (Coordinate) getContext().get(Context.Common.VISUAL);
//        Coordinate position;
//        for (GameObject object: gameObjects) {
//            sprite = object.getSprite();
//
//            if (sprite == null) continue;
//
//            position = object.getPosition();
//            System.out.println(position);
//
//            g2d.drawImage(
//                    sprite.getImageIcon().getImage(),
//                    (int) (-visualPosition.getX() + position.getX() * Renderer.TILE_PIXEL_WIDTH),
//                    (int) (visualPosition.getY() + position.getY() * Renderer.TILE_PIXEL_HEIGHT),
//                    null
//            );
//            g2d.drawImage(
//                    sprite.getImageIcon().getImage(),
//                    (int) (visualPosition.getX() + position.getX() * Renderer.TILE_PIXEL_WIDTH),
//                    (int) (visualPosition.getY() + position.getY() * Renderer.TILE_PIXEL_HEIGHT),
//                    null
//            );
//        }
    }
}
