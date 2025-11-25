package entity;

import map.Coordinates;
import map.WorldMap;
import service.InteractionService;
import service.MovementService;
import service.NavigationService;

import java.util.List;
import java.util.Optional;

public class Herbivore extends Creature {

    private static final int SPEED = 2;
    private static final int HP = 10;
    private static final int DEFAULT_VISION_RANGE = 4;

    private final int visionRange;

    public Herbivore(MovementService movementService,
                     InteractionService interactionService,
                     NavigationService navigationService) {
        super(SPEED, HP, movementService, interactionService, navigationService);
        this.visionRange = DEFAULT_VISION_RANGE;
    }

    @Override
    public void makeMove(WorldMap map, Coordinates currentPosition) {

        Optional<Coordinates> target = navigationService.findTarget(map, currentPosition, visionRange, Grass.class);

        if (target.isPresent()) {
            List<Coordinates> path = navigationService.findPath(map, currentPosition, target.get(), getSpeed());
            if (!path.isEmpty()) {
                Coordinates nextStep = path.getFirst();
                if (nextStep.equals(target.get())) {
                    interactionService.eatGrass(map, currentPosition, nextStep);
                }
                movementService.moveEntity(map, currentPosition, nextStep);
                return;
            }
        }
        makeRandomMove(map, currentPosition);
    }
}
