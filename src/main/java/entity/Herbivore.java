package entity;

import map.Coordinates;
import map.WorldMap;
import service.HerbivoreInteractionService;
import service.MovementService;
import service.NavigationService;

import java.util.List;
import java.util.Optional;

public class Herbivore extends Creature {

    private static final int SPEED = 2;
    private static final int HP = 10;
    private static final int DEFAULT_VISION_RANGE = 4;

    private final HerbivoreInteractionService herbivoreInteractionService;
    private final int visionRange;

    public Herbivore(MovementService movementService,
                     HerbivoreInteractionService herbivoreInteractionService,
                     NavigationService navigationService) {
        super(SPEED, HP, movementService, navigationService);
        this.visionRange = DEFAULT_VISION_RANGE;
        this.herbivoreInteractionService = herbivoreInteractionService;
    }

    @Override
    public void makeMove(WorldMap map) {
        Optional<Coordinates> currentPositionOpt = map.getCoordinates(this);
        if (currentPositionOpt.isEmpty()) {
            return;
        }
        Coordinates currentPosition = currentPositionOpt.get();
        Optional<Coordinates> target = navigationService.findTarget(map, currentPosition, visionRange, Grass.class);

        if (target.isPresent()) {
            List<Coordinates> path = navigationService.findPath(map, currentPosition, target.get(), getSpeed());
            if (!path.isEmpty()) {
                Coordinates nextStep = path.getFirst();
                if (nextStep.equals(target.get())) {
                    herbivoreInteractionService.eatGrass(map, currentPosition, nextStep);
                }
                movementService.moveEntity(map, currentPosition, nextStep);
                return;
            }
        }
        makeRandomMove(map, currentPosition);
    }
}
