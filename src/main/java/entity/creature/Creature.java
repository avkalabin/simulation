package entity.creature;

import entity.Entity;

public abstract class Creature extends Entity {
    public int speed;
    public int hp;


    public abstract void makeMove();
}
