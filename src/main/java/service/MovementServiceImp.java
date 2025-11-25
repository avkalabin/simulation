package service;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

public class MovementServiceImp implements MovementService {

    @Override
    public void moveEntity(WorldMap worldMap, Coordinates from, Coordinates to) {
        Entity entity = worldMap.get(from);
        worldMap.removeEntity(from);
        worldMap.put(to, entity);
    }
}
