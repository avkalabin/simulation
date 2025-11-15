package entity;

import map.Coordinates;
import map.WorldMap;
import service.InteractionService;
import service.MovementService;
import service.NavigationService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Herbivore extends Creature {

    private static final int SPEED = 2;
    private static final int HP = 10;
    private static final int FOOD_RANGE = 1;
    private static final int DEFAULT_VISION_RANGE = 16;

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
                // Берем ПЕРВЫЙ шаг из пути (он всегда в пределах досягаемости BFS)
                Coordinates nextStep = path.getFirst();
                movementService.moveEntity(map, currentPosition, nextStep);
                return;
            }
        }

        // Случайное движение - используем NavigationService вместо MovementService
        List<Coordinates> availableMoves = navigationService.getAvailableMoves(map, currentPosition, getSpeed());
        if (!availableMoves.isEmpty()) {
            int randomIndex = random.nextInt(availableMoves.size());
            Coordinates randomMove = availableMoves.get(randomIndex);
            movementService.moveEntity(map, currentPosition, randomMove);
        }
    }
}
