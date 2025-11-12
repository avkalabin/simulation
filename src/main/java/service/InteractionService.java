package service;

import java.util.Optional;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

import static java.lang.Math.abs;

public class InteractionService {

    public Optional<Coordinates> findTarget(WorldMap map, Coordinates from, int range, Class<? extends Entity> targetType) {

        for (int deltaRow = -range; deltaRow <= range; deltaRow++) {
            for (int deltaCol = -range; deltaCol <= range; deltaCol++) {
                if (deltaRow == 0 && deltaCol == 0) {
                    continue;
                }
                int distance = abs(deltaRow) + abs(deltaCol);
                if (distance > range) {
                    continue;
                }

                Coordinates candidate = new Coordinates(
                    from.row() + deltaRow,
                    from.column() + deltaCol
                );
                if (map.isOutOfBounds(candidate)) {
                    continue;
                }
                Entity entity = map.get(candidate);
                if (targetType.isInstance(entity)) {
                    return Optional.of(candidate);
                }
            }
        }
        return Optional.empty();
    }
}
