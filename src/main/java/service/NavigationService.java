package service;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

import java.util.List;

public interface NavigationService {

    List<Coordinates> findPath(WorldMap map, Coordinates start, Class<? extends Entity> target, int speed, int vision);

    List<Coordinates> getAvailableMoves(WorldMap map, Coordinates from, int speed);
}
