package service;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

import static java.lang.Math.abs;

public class MovementService {

    public List<Coordinates> getAvailableMoves(WorldMap map, Coordinates from, int speed) {
        List<Coordinates> result = new ArrayList<>();

        for (int deltaRow = -speed; deltaRow <= speed; deltaRow++) {
            for (int deltaCol = -speed; deltaCol <= speed; deltaCol++) {
                if (deltaRow == 0 && deltaCol == 0) {
                    continue;
                }

                int distance = abs(deltaRow) + abs(deltaCol);
                if (distance > speed) {
                    continue;
                }
                Coordinates candidate = new Coordinates(
                    from.row() + deltaRow,
                    from.column() + deltaCol
                );

                if (map.isOutOfBounds(candidate)) {
                    continue;
                }
                if (map.isEmpty(candidate)) {
                    result.add(candidate);
                }
            }
        }
        return result;
    }

    public void moveEntity(WorldMap worldMap, Coordinates from, Coordinates to) {
        Entity entity = worldMap.get(from);
        worldMap.removeEntity(from);
        worldMap.put(to, entity);
    }
}
