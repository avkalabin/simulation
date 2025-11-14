package action;

import java.util.Random;

import entity.Grass;
import entity.Herbivore;
import entity.Predator;
import entity.Rock;
import entity.Tree;
import map.Coordinates;
import map.WorldMap;
import service.InteractionService;
import service.MovementService;
import service.NavigationService;

public class InitAction implements Action {

    MovementService movementService;
    InteractionService interactionService;
    NavigationService navigationService;
    public final Random random = new Random();

    public InitAction(MovementService movementService, InteractionService interactionService, NavigationService navigationService) {
        this.movementService = movementService;
        this.interactionService = interactionService;
        this.navigationService = navigationService;
    }

    @Override
    public void execute(WorldMap map) {
        //        spawnRocks(map, 10);
        //        spawnTrees(map, 8);
        //        spawnGrass(map, 15);
        //        spawnHerbivores(map, 5);
        //        spawnPredators(map, 3);

        map.put(new Coordinates(0, 3), new Rock());
        map.put(new Coordinates(1, 3), new Rock());
        map.put(new Coordinates(2, 3), new Rock());
        map.put(new Coordinates(3, 3), new Rock());
        map.put(new Coordinates(4, 3), new Rock());
        map.put(new Coordinates(5, 3), new Rock());
        map.put(new Coordinates(6, 3), new Rock());
        map.put(new Coordinates(7, 3), new Rock());
        map.put(new Coordinates(8, 3), new Rock());
        map.put(new Coordinates(0, 0), new Herbivore(movementService, interactionService, navigationService));
        map.put(new Coordinates(0, 9), new Grass());
    }

    private void spawnRocks(WorldMap map, int count) {
        for (int i = 0; i < count; i++) {
            Coordinates position = getRandomEmptyCell(map);
            map.put(position, new Rock());
        }
    }

    private void spawnTrees(WorldMap map, int count) {
        for (int i = 0; i < count; i++) {
            Coordinates position = getRandomEmptyCell(map);
            map.put(position, new Tree());
        }
    }

    private void spawnGrass(WorldMap map, int count) {
        for (int i = 0; i < count; i++) {
            Coordinates position = getRandomEmptyCell(map);
            map.put(position, new Grass());
        }
    }

    private void spawnHerbivores(WorldMap map, int count) {
        for (int i = 0; i < count; i++) {
            Coordinates position = getRandomEmptyCell(map);
            map.put(position, new Herbivore(movementService, interactionService, navigationService));
        }
    }

    private void spawnPredators(WorldMap map, int count) {
        for (int i = 0; i < count; i++) {
            Coordinates position = getRandomEmptyCell(map);
            map.put(position, new Predator(movementService, interactionService, navigationService));
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
