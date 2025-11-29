package map;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import entity.Entity;

public class WorldMap {

    private final int height;
    private final int width;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public WorldMap(int height, int column) {
        this.height = height;
        this.width = column;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void put(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public Optional<Entity> get(Coordinates coordinates) {
        return Optional.ofNullable(entities.get(coordinates));
    }

    public boolean isEmpty(Coordinates coordinates) {
        if (isOutOfBounds(coordinates)) {
            throw new IllegalArgumentException("Coordinates are out of map bounds");
        }
        return entities.get(coordinates) == null;
    }

    public boolean isOutOfBounds(Coordinates coordinates) {
        return (coordinates.row() < 0 || coordinates.row() >= height) || (coordinates.column() < 0 || coordinates.column() >= width);
    }

    public void removeEntity(Coordinates coordinates) {
        if (isEmpty(coordinates)) {
            throw new IllegalArgumentException("Entity not found for: " + coordinates);
        }
        entities.remove(coordinates);
    }

    public Set<Map.Entry<Coordinates, Entity>> getAllEntities() {
        return entities.entrySet();
    }

    public Optional<Coordinates> getCoordinates(Entity entity) {
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
            if (entry.getValue().equals(entity)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }
}
