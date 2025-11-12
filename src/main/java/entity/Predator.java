package entity;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import map.Coordinates;
import map.WorldMap;
import service.InteractionService;
import service.MovementService;

public class Predator extends Creature {

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HP = 15;
    private static final int DEFAULT_ATTACK_POWER = 5;
    private static final int DEFAULT_ATTACK_RANGE = 1;

    private final int attackPower;
    private final int attackRange;
    private final MovementService movementService;
    private final InteractionService interactionService;
    private final static Random random = new Random();

    public Predator(MovementService movementService, InteractionService interactionService) {
        super(DEFAULT_SPEED, DEFAULT_HP);
        this.movementService = movementService;
        this.interactionService = interactionService;
        this.attackPower = DEFAULT_ATTACK_POWER;
        this.attackRange = DEFAULT_ATTACK_RANGE;
    }

    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public void makeMove(WorldMap map, Coordinates currentPosition) {

        Optional<Coordinates> targetPos = interactionService.findTarget(map, currentPosition, attackRange, Herbivore.class);

        if (targetPos.isPresent()) {
            Coordinates target = targetPos.get();
            attack(target);
            System.out.println("Волк нашел цель!");
            return;
        }

        List<Coordinates> availableMoves = movementService.getAvailableMoves(map, currentPosition, getSpeed());
        if (!availableMoves.isEmpty()) {
            int randomIndex = random.nextInt(availableMoves.size());
            Coordinates randomMove = availableMoves.get(randomIndex);
            movementService.moveEntity(map, currentPosition, randomMove);
        }
    }

    private void attack(Coordinates target) {

    }
}
