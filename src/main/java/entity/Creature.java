package entity;

import map.Coordinates;
import map.Map;

public abstract class Creature extends Entity {
    protected int speed;
    protected int hp;

    public Creature(int speed, int hp) {
        this.speed = speed;
        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public abstract void makeMove(Map map, Coordinates currentPosition);
}
