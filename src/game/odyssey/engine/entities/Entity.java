package game.odyssey.engine.entities;

import game.odyssey.engine.common.Id;
import game.odyssey.engine.utils.Coordinate;

import javax.swing.*;

@SuppressWarnings("unused")
public abstract class Entity {
    public static final int ENTITY_WIDTH = 48;
    public static final int ENTITY_HEIGHT = (int) (ENTITY_WIDTH * 1.5);
    public static final int MOVE_STATE_COUNT = 3;
    protected EntitySprite sprite;
    protected Coordinate position;
    protected Direction facing = Direction.NORTH;
    protected float hp = 100.0F;
    protected float atkDamage = 1.0F;
    protected float defensePoint = 0.0F;
    protected float speedMultiplier = 1.0F;
    protected int moveState = 0;
    public Entity() {
    }

    public Entity(float hp, float atkDamage, float speedMultiplier) {
        this.hp = hp;
        this.atkDamage = atkDamage;
        this.speedMultiplier = speedMultiplier;
    }

    public ImageIcon getSprite() {
        if (sprite == null) {
            sprite = new EntitySprite(this.getClass().getAnnotation(Id.class).value());
        }

        return sprite.getSprite().get(facing);
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getAtkDamage() {
        return atkDamage;
    }

    public void setAtkDamage(float atkDamage) {
        this.atkDamage = atkDamage;
    }

    public float getDefensePoint() {
        return defensePoint;
    }

    public void setDefensePoint(float defensePoint) {
        this.defensePoint = defensePoint;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public void nextMoveState() {
        if (moveState >= MOVE_STATE_COUNT) {
            moveState = 0;
        } else {
            moveState++;
        }
    }

    public int getMoveState() {
        return moveState;
    }

    public Coordinate getPosition() {
        return position;
    }
}
