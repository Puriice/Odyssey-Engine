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
    protected Direction[] facing = new Direction[]{ Direction.NORTH, Direction.NORTH };
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

        return sprite.getSprite().get(facing[0]);
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

    public void setFacing(Direction facing) {
        this.facing = new Direction[] { facing, facing };
    }
    public void setFacing(Direction facing1, Direction facing2) {
        this.facing = new Direction[] { facing1, facing2 };
    }

    protected void move(Coordinate position) {
        this.position.move(position);
    }

    protected void move(double dx, double dy) {
        this.position.move(dx, dy);
    }

    protected void moveBy(Coordinate position) {
        this.moveBy(position.getX(), position.getY());
    }

    protected void moveBy(double dx, double dy) {
        this.position.translate(dx, dy);

        if (dx > 0 && dy == 0) {
            setFacing(Direction.EAST);
        } else if (dx < 0 && dy == 0) {
            setFacing(Direction.WEST);
        } else if (dx == 0 && dy > 0) {
            setFacing(Direction.NORTH);
        } else if (dx == 0 && dy < 0) {
            setFacing(Direction.SOUTH);
        } else if (dx > 0 && dy > 0) {
            setFacing(Direction.NORTH, Direction.EAST);
        } else if (dx > 0 && dy < 0) {
            setFacing(Direction.SOUTH, Direction.EAST);
        } else if (dx < 0 && dy > 0) {
            setFacing(Direction.NORTH, Direction.WEST);
        } else if (dx < 0 && dy < 0) {
            setFacing(Direction.SOUTH, Direction.WEST);
        }
    }
    protected void moveUp() {
        setFacing(Direction.NORTH);
        this.position.translate(0,1);
    }

    protected void moveRight() {
        setFacing(Direction.EAST);
        this.position.translate(1, 0);
    }

    protected void moveDown() {
        setFacing(Direction.SOUTH);
        this.position.translate(0, -1);
    }

    protected void moveLeft() {
        setFacing(Direction.WEST);
        this.position.translate(-1, 0);
    }
}
