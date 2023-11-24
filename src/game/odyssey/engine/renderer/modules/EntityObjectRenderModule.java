package game.odyssey.engine.renderer.modules;

import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.objects.GameObject;
import game.odyssey.engine.renderer.Context;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Resource;

import java.awt.*;
import java.util.ArrayList;

public class EntityObjectRenderModule extends RenderModule {
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        @SuppressWarnings("unchecked")
        ArrayList<GameObject> entityObjects = (ArrayList<GameObject>) getContext().get(Context.Common.ENTITY_OBJECT);

        Coordinate position = new Coordinate();

        for (GameObject object: entityObjects) {
            Resource sprite = object.getSprite();

            if (sprite == null) continue;

            position.move(object.getPosition());

            g2d.drawImage(
                    sprite.getImageIcon().getImage(),
                    position.getIntX(),
                    position.getIntY(),
                    null
            );

        }
    }
}
