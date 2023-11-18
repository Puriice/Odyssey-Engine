package game.odyssey.engine.entities;

import game.odyssey.engine.Id;
import game.odyssey.engine.utils.Coordinate;

public abstract class Entity {
    protected EntitySprite sprite;
    protected Coordinate position;
    protected float hp = 100.0F;
    protected float atkDamage = 1.0F;
    protected float defensePoint = 0.0F;
    protected float speedMultiplier = 1.0F;
    public Entity() {
    }

    public Entity(float hp, float atkDamage, float speedMultiplier) {
        this.hp = hp;
        this.atkDamage = atkDamage;
        this.speedMultiplier = speedMultiplier;
    }

    public EntitySprite getSprite() {
        if (sprite == null) {
            sprite = new EntitySprite(this.getClass().getAnnotation(Id.class).value());
        }

        return sprite;
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


}
