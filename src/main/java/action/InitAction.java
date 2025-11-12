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

public class InitAction implements Action {

    MovementService movementService;
    InteractionService interactionService;
    public final Random random = new Random();

    public InitAction(MovementService movementService, InteractionService interactionService) {
        this.movementService = movementService;
        this.interactionService = interactionService;
    }

    @Override
    public void execute(WorldMap map) {
        spawnRocks(map, 10);
        spawnTrees(map, 8);
        spawnGrass(map, 15);
        spawnHerbivores(map, 5);
        spawnPredators(map, 3);
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
            map.put(position, new Herbivore(movementService));
        }
    }

    private void spawnPredators(WorldMap map, int count) {
        for (int i = 0; i < count; i++) {
            Coordinates position = getRandomEmptyCell(map);
            map.put(position, new Predator(movementService, interactionService));
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
