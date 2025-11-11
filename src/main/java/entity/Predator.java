package entity;

import java.util.List;
import java.util.Random;

import map.Coordinates;
import map.WorldMap;
import service.MovementService;

public class Predator extends Creature {

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HP = 15;
    private static final int DEFAULT_ATTACK_POWER = 5;

    private final int attackPower;
    private final MovementService movementService;
    private final static Random random = new Random();

    public Predator(MovementService movementService) {
        super(DEFAULT_SPEED, DEFAULT_HP);
        this.movementService = movementService;
        this.attackPower = DEFAULT_ATTACK_POWER;
    }

    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public void makeMove(WorldMap worldMap, Coordinates currentPosition) {
        List<Coordinates> availableMoves = movementService.getAvailableMoves(worldMap, currentPosition, getSpeed());
        if (!availableMoves.isEmpty()) {
            int randomIndex = random.nextInt(availableMoves.size());
            Coordinates randomMove = availableMoves.get(randomIndex);
            movementService.moveEntity(worldMap, currentPosition, randomMove);
        }
    }
}
