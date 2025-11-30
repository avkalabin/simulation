package entity;

import map.Coordinates;
import map.WorldMap;
import service.*;

import java.util.List;
import java.util.Optional;

public class Predator extends Creature {

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HP = 15;
    private static final int DEFAULT_ATTACK_POWER = 5;
    private static final int DEFAULT_VISION_RANGE = 4;

    private final PredatorInteractionService predatorInteractionService;
    private final int attackPower;
    private final int visionRange;

    public Predator(MovementService movementService,
                    PredatorInteractionService predatorInteractionService,
                    NavigationService navigationService) {
        super(DEFAULT_SPEED, DEFAULT_HP, movementService, navigationService);
        this.predatorInteractionService = predatorInteractionService;
        this.attackPower = DEFAULT_ATTACK_POWER;
        this.visionRange = DEFAULT_VISION_RANGE;
    }

    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public void makeMove(WorldMap map) {
        Optional<Coordinates> currentPositionOpt = map.getCoordinates(this);
        if (currentPositionOpt.isEmpty()) {
            return;
        }
        Coordinates currentPosition = currentPositionOpt.get();

        List<Coordinates> path = navigationService.findPath(map, currentPosition, Herbivore.class, getSpeed(), visionRange);
        if (!path.isEmpty()) {
            Coordinates nextStep = path.getFirst();
            if (path.size() == 1) {
                predatorInteractionService.attack(map, currentPosition, nextStep);
                return;
            }
            movementService.moveEntity(map, currentPosition, nextStep);
        } else {
            makeRandomMove(map, currentPosition);
        }
    }
}
