package service;

import entity.Entity;
import map.Coordinates;
import map.WorldMap;

import java.util.*;

import static java.lang.Math.abs;

public class NavigationServiceImp implements NavigationService {

    @Override
    public List<Coordinates> findPath(WorldMap map, Coordinates start, Class<? extends Entity> targetType, int speed, int vision) {

        Optional<Coordinates> targetOpt = findTarget(map, start, vision, targetType);
        if (targetOpt.isEmpty()) {
            return Collections.emptyList();
        }
        Coordinates target = targetOpt.get();
        Queue<Node> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();

        queue.add(new Node(start, null));
        visited.add(start);

        List<Coordinates> moves = generateMoves(speed);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            Coordinates current = currentNode.coordinates();

            if (current.equals(target)) {
                return reconstructPath(currentNode);
            }

            for (Coordinates move : moves) {
                int newRow = current.row() + move.row();
                int newCol = current.column() + move.column();

                Coordinates neighbor = new Coordinates(newRow, newCol);

                if (map.isOutOfBounds(neighbor)) {
                    continue;
                }

                if (visited.contains(neighbor)) {
                    continue;
                }

                if (!canMoveTo(map, current, neighbor, target)) {
                    continue;
                }

                visited.add(neighbor);
                queue.add(new Node(neighbor, currentNode));
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

        List<Coordinates> moves = generateMoves(speed);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            int currentDistance = visited.get(current);

            for (Coordinates move : moves) {
                int stepDistance = abs(move.row()) + abs(move.column());

                if (currentDistance + stepDistance > speed) {
                    continue;
                }

                int newRow = current.row() + move.row();
                int newCol = current.column() + move.column();
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

    private Optional<Coordinates> findTarget(WorldMap map, Coordinates from, int range, Class<? extends Entity> targetType) {

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
                Optional<Entity> entity = map.get(candidate);
                if (entity.filter(targetType::isInstance).isPresent()) {
                    return Optional.of(candidate);
                }
            }
        }
        return Optional.empty();
    }

    private List<Coordinates> generateMoves(int maxDistance) {
        List<Coordinates> movesList = new ArrayList<>();
        for (int distance = 1; distance <= maxDistance; distance++) {
            movesList.add(new Coordinates(-distance, 0));  // вверх
            movesList.add(new Coordinates(distance, 0));   // вниз
            movesList.add(new Coordinates(0, -distance));  // влево
            movesList.add(new Coordinates(0, distance));   // вправо
        }
        return movesList;
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

    private List<Coordinates> reconstructPath(Node targetNode) {

        List<Coordinates> path = new ArrayList<>();

        for (Node node = targetNode; node != null; node = node.parent()) {
            path.add(node.coordinates());
        }
        Collections.reverse(path);
        path.removeFirst();
        return path;
    }
}

record Node(Coordinates coordinates, service.Node parent) {
}
