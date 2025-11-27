package service;

import java.util.Optional;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

public class MovementServiceImp implements MovementService {

    @Override
    public void moveEntity(WorldMap worldMap, Coordinates from, Coordinates to) {
        Optional<Entity> entity = worldMap.get(from);
        if (entity.isPresent()) {
            worldMap.removeEntity(from);
        }
        worldMap.put(to, entity.orElse(null));
    }
}
