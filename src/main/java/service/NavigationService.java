package service;

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

public class NavigationService implements INavigationService {

    @Override
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

    @Override
    public List<Coordinates> findPath(WorldMap map, Coordinates start, Coordinates target, int speed) {

        Queue<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Coordinates> parentMap = new HashMap<>();

        queue.add(start);
        parentMap.put(start, null);

        int[][] moves = generateMoves(speed);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (current.equals(target)) {
                return reconstructPath(parentMap, start, target);
            }

            for (int[] move : moves) {
                int newRow = current.row() + move[0];
                int newCol = current.column() + move[1];

                Coordinates neighbor = new Coordinates(newRow, newCol);

                if (map.isOutOfBounds(neighbor)) {
                    continue;
                }

                if (parentMap.containsKey(neighbor)) {
                    continue;
                }

                if (!canMoveTo(map, current, neighbor, target)) {
                    continue;
                }

                parentMap.put(neighbor, current);
                queue.add(neighbor);
            }
        }

        return Collections.emptyList();
    }

    @Override
    public List<Coordinates> getAvailableMoves(WorldMap map, Coordinates from, int speed) {
        List<Coordinates> result = new ArrayList<>();

        Queue<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Integer> visited = new HashMap<>();

        queue.add(from);
        visited.put(from, 0);

        int[][] moves = generateMoves(speed);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            int currentDistance = visited.get(current);

            for (int[] move : moves) {
                int stepDistance = abs(move[0]) + abs(move[1]);

                if (currentDistance + stepDistance > speed) {
                    continue;
                }

                int newRow = current.row() + move[0];
                int newCol = current.column() + move[1];
                Coordinates neighbor = new Coordinates(newRow, newCol);

                if (map.isOutOfBounds(neighbor)) {
                    continue;
                }

                if (visited.containsKey(neighbor)) {
                    continue;
                }

                if (!canMoveTo(map, current, neighbor, null)) {
                    continue;
                }

                visited.put(neighbor, currentDistance + stepDistance);
                queue.add(neighbor);

                if (!neighbor.equals(from)) {
                    result.add(neighbor);
                }
            }
        }

        return result;
    }

    private int[][] generateMoves(int maxDistance) {
        List<int[]> movesList = new ArrayList<>();

        for (int distance = 1; distance <= maxDistance; distance++) {
            movesList.add(new int[]{-distance, 0});  // вверх
            movesList.add(new int[]{distance, 0});   // вниз
            movesList.add(new int[]{0, -distance});  // влево
            movesList.add(new int[]{0, distance});   // вправо
        }

        return movesList.toArray(new int[0][]);
    }

    private boolean canMoveTo(WorldMap map, Coordinates from, Coordinates to, Coordinates target) {
        boolean isTarget = to.equals(target);

        // Конечная клетка должна быть пустой (кроме случая, когда это цель)
        if (!isTarget && !map.isEmpty(to)) {
            return false;
        }

        // Проверяем все промежуточные клетки на пути
        int rowDiff = to.row() - from.row();
        int colDiff = to.column() - from.column();
        int steps = abs(rowDiff) + abs(colDiff);

        // Для прыжков больше 1 клетки проверяем промежуточные
        if (steps > 1) {
            int rowStep = rowDiff != 0 ? rowDiff / abs(rowDiff) : 0;
            int colStep = colDiff != 0 ? colDiff / abs(colDiff) : 0;

            int currentRow = from.row();
            int currentCol = from.column();

            for (int i = 1; i < steps; i++) {
                currentRow += rowStep;
                currentCol += colStep;

                Coordinates intermediate = new Coordinates(currentRow, currentCol);

                if (map.isOutOfBounds(intermediate) || !map.isEmpty(intermediate)) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<Coordinates> reconstructPath(Map<Coordinates, Coordinates> parentMap,
                                              Coordinates start,
                                              Coordinates target) {

        List<Coordinates> path = new ArrayList<>();
        Coordinates current = target;

        while (current != null && !current.equals(start)) {
            path.add(current);
            current = parentMap.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}
