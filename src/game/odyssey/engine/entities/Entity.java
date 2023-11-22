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
    protected Coordinate position = new Coordinate();
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
        if (moveState >= MOVE_STATE_COUNT - 1) {
            moveState = 0;
        } else {
            moveState++;
        }
    }

    public int getMoveState() {
        return moveState;
    }

    public void resetMoveState() {
        moveState = 0;
    }

    public Coordinate getPosition() {
        return position;
    }

    protected void move(Coordinate position) {
        this.position.move(position);
    }

    protected void move(double dx, double dy) {
        this.position.move(dx, dy);
    }
    protected void moveUp() {
        facing = Direction.NORTH;
        this.position.translate(0,1);
    }

    protected void moveRight() {
        facing = Direction.EAST;
        this.position.translate(1, 0);
    }

    protected void moveDown() {
        facing = Direction.SOUTH;
        this.position.translate(0, -1);
    }

    protected void moveLeft() {
        facing = Direction.WEST;
        this.position.translate(-1, 0);
    }
}
