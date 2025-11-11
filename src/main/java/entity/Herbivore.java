package entity;

import java.util.List;
import java.util.Random;

import map.Coordinates;
import map.WorldMap;
import service.MovementService;

public class Herbivore extends Creature {

    private static final int SPEED = 2;
    private static final int HP = 10;

    private final MovementService movementService;
    private final static Random random = new Random();

    public Herbivore(MovementService movementService) {
        super(SPEED, HP);
        this.movementService = movementService;
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
