package entity;

import map.Coordinates;
import map.WorldMap;

public abstract class Creature extends Entity {

    private final int speed;
    private int hp;

    public Creature(int speed, int hp) {
        this.speed = speed;
        this.hp = hp;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public void heal(int hp) {
        this.hp += hp;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public abstract void makeMove(WorldMap worldMap, Coordinates currentPosition);
}
