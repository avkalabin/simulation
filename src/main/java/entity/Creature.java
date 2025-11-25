package entity;

import map.Coordinates;
import map.WorldMap;
import service.*;

import java.util.List;
import java.util.Random;

public abstract class Creature extends Entity {

    private final int speed;
    private int hp;

    protected final MovementService movementService;
    protected final InteractionService interactionService;
    protected final NavigationService navigationService;
    protected static final Random random = new Random();

    public Creature(int speed,
                    int hp,
                    MovementService movementService,
                    InteractionService interactionService,
                    NavigationService navigationService) {
        this.speed = speed;
        this.hp = hp;
        this.movementService = movementService;
        this.interactionService = interactionService;
        this.navigationService = navigationService;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public void heal(int hp) {
        this.hp += hp;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public abstract void makeMove(WorldMap worldMap, Coordinates currentPosition);

    protected void makeRandomMove(WorldMap map, Coordinates currentPosition) {
        List<Coordinates> availableMoves = navigationService.getAvailableMoves(map, currentPosition, getSpeed());
        if (!availableMoves.isEmpty()) {
            int randomIndex = random.nextInt(availableMoves.size());
            Coordinates randomMove = availableMoves.get(randomIndex);
            movementService.moveEntity(map, currentPosition, randomMove);
        }
    }
}
