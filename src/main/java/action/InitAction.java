package action;

import java.util.Random;
import java.util.function.Supplier;

import entity.*;
import map.Coordinates;
import map.WorldMap;
import service.*;

public class InitAction implements Action {
    MovementService movementService;
    InteractionService interactionService;
    NavigationService navigationService;

    public final Random random = new Random();

    public InitAction(MovementService movementService,
                       InteractionService interactionService,
                       NavigationService navigationService) {
        this.movementService = movementService;
        this.interactionService = interactionService;
        this.navigationService = navigationService;
    }

    @Override
    public void execute(WorldMap map) {
        spawnEntity(map, 10, Rock::new);
        spawnEntity(map, 8, Tree::new);
        spawnEntity(map, 15, Grass::new);
        spawnEntity(map, 5, () -> new Herbivore(movementService, interactionService, navigationService));
        spawnEntity(map, 3, () -> new Predator(movementService, interactionService, navigationService));
    }

    private void spawnEntity(WorldMap map, int count, Supplier<Entity> entitySupplier) {
        for (int i = 0; i < count; i++) {
            Coordinates position = getRandomEmptyCell(map);
            map.put(position, entitySupplier.get());
        }
    }

    private Coordinates getRandomEmptyCell(WorldMap map) {
        int row;
        int column;
        do {
            row = random.nextInt(map.getHeight());
            column = random.nextInt(map.getWidth());
        } while (!map.isEmpty(new Coordinates(row, column)));
        return new Coordinates(row, column);
    }
}
