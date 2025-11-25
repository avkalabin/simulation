package service;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

import java.util.List;
import java.util.Optional;

public interface NavigationService {

    Optional<Coordinates> findTarget(WorldMap map, Coordinates from, int range, Class<? extends Entity> targetType);

    List<Coordinates> findPath(WorldMap map, Coordinates start, Coordinates target, int speed);

    List<Coordinates> getAvailableMoves(WorldMap map, Coordinates from, int speed);
}
