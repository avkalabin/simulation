package service;

import map.Coordinates;
import map.WorldMap;

public interface MovementService {
    void moveEntity(WorldMap worldMap, Coordinates from, Coordinates to);
}
