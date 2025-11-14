package entity;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import map.Coordinates;
import map.WorldMap;
import service.InteractionService;
import service.MovementService;
import service.NavigationService;

import static java.lang.Math.min;

public class Herbivore extends Creature {

    private static final int SPEED = 2;
    private static final int HP = 10;
    private static final int FOOD_RANGE = 1;
    private static final int DEFAULT_VISION_RANGE = 40;

    private final int foodRange;
    private final int visionRange;
    private final MovementService movementService;
    private final InteractionService interactionService;
    private final NavigationService navigationService;
    private final static Random random = new Random();


    public Herbivore(MovementService movementService, InteractionService interactionService, NavigationService navigationService) {
        super(SPEED, HP);
        this.movementService = movementService;
        this.interactionService = interactionService;
        this.navigationService = navigationService;
        this.foodRange = FOOD_RANGE;
        this.visionRange = DEFAULT_VISION_RANGE;
    }

    @Override
    public void makeMove(WorldMap map, Coordinates currentPosition) {

        Optional<Coordinates> grassPos = navigationService.findTarget(map, currentPosition, foodRange, Grass.class);

        if (grassPos.isPresent()) {
            Coordinates grass = grassPos.get();
            interactionService.eatGrass(map, currentPosition, grass);
            return;
        }

        Optional<Coordinates> distantTarget = navigationService.findTarget(map, currentPosition, visionRange, Grass.class);

        if (distantTarget.isPresent()) {
            List<Coordinates> path = navigationService.findPath(map, currentPosition, distantTarget.get(), getSpeed());
            if (!path.isEmpty()) {
                int steps = min(getSpeed(), path.size());
                Coordinates nextStep = path.get(steps - 1);
                movementService.moveEntity(map, currentPosition, nextStep);
                return;
            }
        }

        List<Coordinates> availableMoves = movementService.getAvailableMoves(map, currentPosition, getSpeed());
        if (!availableMoves.isEmpty()) {
            int randomIndex = random.nextInt(availableMoves.size());
            Coordinates randomMove = availableMoves.get(randomIndex);
            movementService.moveEntity(map, currentPosition, randomMove);
        }
    }
}
