package entity;

import map.Coordinates;
import map.Map;

public class Predator extends Creature {

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HP = 15;
    private static final int DEFAULT_ATTACK_POWER = 5;

    private final int attackPower;

    public Predator() {
        super(DEFAULT_SPEED, DEFAULT_HP);
        this.attackPower = DEFAULT_ATTACK_POWER;
    }

    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public String getSymbol() {
        return " \uD83D\uDC3A  ";
    }

    @Override
    public void makeMove(Map map, Coordinates currentPosition) {

    }
}
