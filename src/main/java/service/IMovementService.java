package service;

import map.Coordinates;
import map.WorldMap;

public interface IMovementService {
    void moveEntity(WorldMap worldMap, Coordinates from, Coordinates to);
}
