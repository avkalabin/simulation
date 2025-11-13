package entity;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import map.Coordinates;
import map.WorldMap;
import service.InteractionService;
import service.MovementService;

public class Herbivore extends Creature {

    private static final int SPEED = 2;
    private static final int HP = 10;
    private static final int FOOD_RANGE = 1;

    private final MovementService movementService;
    private final InteractionService interactionService;
    private final static Random random = new Random();
    private final int foodRange;

    public Herbivore(MovementService movementService, InteractionService interactionService) {
        super(SPEED, HP);
        this.movementService = movementService;
        this.interactionService = interactionService;
        this.foodRange = FOOD_RANGE;
    }

    @Override
    public void makeMove(WorldMap map, Coordinates currentPosition) {

        Optional<Coordinates> grassPos = interactionService.findTarget(map, currentPosition, foodRange, Grass.class);

        if (grassPos.isPresent()) {
            Coordinates grass = grassPos.get();
            interactionService.eatGrass(map,currentPosition, grass);
            return;
        }

        List<Coordinates> availableMoves = movementService.getAvailableMoves(map, currentPosition, getSpeed());
        if (!availableMoves.isEmpty()) {
            int randomIndex = random.nextInt(availableMoves.size());
            Coordinates randomMove = availableMoves.get(randomIndex);
            movementService.moveEntity(map, currentPosition, randomMove);
        }
    }
}
