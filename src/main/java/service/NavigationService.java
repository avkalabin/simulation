package service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

import static java.lang.Math.abs;

public class NavigationService {

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


    public List<Coordinates> findPath(WorldMap map, Coordinates start, Coordinates target, int maxDistance) {

        Queue<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

        queue.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            if (current.equals(target)) {
                return reconstructPath(cameFrom, start, target);
            }
            for (Coordinates neighbor : getNeighbors(map, current)) {
                if (!cameFrom.containsKey(neighbor)) {
                    queue.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private int getDistance(Coordinates from, Coordinates neighbor) {

        return abs(from.row() - neighbor.row()) + abs(from.column() - neighbor.column());
    }

    private List<Coordinates> getNeighbors(WorldMap map, Coordinates position) {
        List<Coordinates> neighbors = new ArrayList<>();

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            Coordinates neighbor = new Coordinates(
                position.row() + dir[0],
                position.column() + dir[1]
            );

            if (!map.isOutOfBounds(neighbor) && map.isEmpty(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private List<Coordinates> reconstructPath(Map<Coordinates, Coordinates> cameFrom, Coordinates start, Coordinates target) {

        List<Coordinates> path = new ArrayList<>();
        Coordinates current = target;
        while (current != null && !current.equals(start)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
