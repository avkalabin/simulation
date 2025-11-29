package action;

import java.util.Random;
import java.util.function.Supplier;

import entity.*;
import map.Coordinates;
import map.WorldMap;
import service.*;

public class InitAction implements Action {

    private static final int ROCK_SPAWN_COUNT = 10;
    private static final int TREE_SPAWN_COUNT = 8;
    private static final int GRASS_SPAWN_COUNT = 15;
    private static final int HERBIVORE_SPAWN_COUNT = 5;
    private static final int PREDATOR_SPAWN_COUNT = 3;

    MovementService movementService;
    HerbivoreInteractionService herbivoreInteractionService;
    PredatorInteractionService predatorInteractionService;
    NavigationService navigationService;

    public final Random random = new Random();

    public InitAction(MovementService movementService,
                      HerbivoreInteractionService herbivoreInteractionService,
                      PredatorInteractionService predatorInteractionService,
                      NavigationService navigationService) {
        this.movementService = movementService;
        this.herbivoreInteractionService = herbivoreInteractionService;
        this.predatorInteractionService = predatorInteractionService;
        this.navigationService = navigationService;
    }

    @Override
    public void execute(WorldMap map) {
        spawnEntity(map, ROCK_SPAWN_COUNT, Rock::new);
        spawnEntity(map, TREE_SPAWN_COUNT, Tree::new);
        spawnEntity(map, GRASS_SPAWN_COUNT, Grass::new);
        spawnEntity(map, HERBIVORE_SPAWN_COUNT, () -> new Herbivore(movementService, herbivoreInteractionService, navigationService));
        spawnEntity(map, PREDATOR_SPAWN_COUNT, () -> new Predator(movementService, predatorInteractionService, navigationService));
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
