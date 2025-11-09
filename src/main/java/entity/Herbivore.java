package entity;

import map.Coordinates;
import map.Map;

public class Herbivore extends Creature {

    private static final int SPEED = 2;
    private static final int HP = 10;

    public Herbivore() {
        super(SPEED, HP);
    }

    @Override
    public String getSymbol() {
        return " \uD83D\uDC30 ";
    }

    @Override
    public void makeMove(Map map, Coordinates currentPosition) {

    }
}
