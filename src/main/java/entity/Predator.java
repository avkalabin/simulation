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

public class Predator extends Creature {

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HP = 15;
    private static final int DEFAULT_ATTACK_POWER = 5;
    private static final int DEFAULT_ATTACK_RANGE = 1;
    private static final int DEFAULT_VISION_RANGE = 6;

    private final int attackPower;
    private final int attackRange;
    private final int visionRange;
    private final MovementService movementService;
    private final InteractionService interactionService;
    private final NavigationService navigationService;
    private final static Random random = new Random();

    public Predator(MovementService movementService, InteractionService interactionService, NavigationService navigationService) {
        super(DEFAULT_SPEED, DEFAULT_HP);
        this.movementService = movementService;
        this.interactionService = interactionService;
        this.navigationService = navigationService;
        this.attackPower = DEFAULT_ATTACK_POWER;
        this.attackRange = DEFAULT_ATTACK_RANGE;
        this.visionRange = DEFAULT_VISION_RANGE;
    }

    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public void makeMove(WorldMap map, Coordinates currentPosition) {

        Optional<Coordinates> targetPos = navigationService.findTarget(map, currentPosition, attackRange, Herbivore.class);

        if (targetPos.isPresent()) {
            Coordinates target = targetPos.get();
            interactionService.attack(map, currentPosition, target);
            return;
        }

        Optional<Coordinates> distantTarget = navigationService.findTarget(map, currentPosition, visionRange, Herbivore.class);

        if (distantTarget.isPresent()) {
            List<Coordinates> path = navigationService.findPath(map, currentPosition, distantTarget.get(), getSpeed());
            if (!path.isEmpty()) {
                int steps = min(getSpeed(), path.size());
                Coordinates nextStep = path.get(steps - 1);
                movementService.moveEntity(map, currentPosition, nextStep);
                return;
            }
        }

        List<Coordinates> availableMoves = navigationService.getAvailableMoves(map, currentPosition, getSpeed());
        if (!availableMoves.isEmpty()) {
            int randomIndex = random.nextInt(availableMoves.size());
            Coordinates randomMove = availableMoves.get(randomIndex);
            movementService.moveEntity(map, currentPosition, randomMove);
        }
    }
}
