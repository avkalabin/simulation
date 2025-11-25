package map;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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

    public Entity get(Coordinates coordinates) {
        Entity entity = entities.get(coordinates);
        if (entity == null) {
            throw new NoSuchElementException("Сущность не найдена для координат: " + coordinates);
        }
        return entity;
    }

    public boolean isEmpty(Coordinates coordinates) {
        if (isOutOfBounds(coordinates)) {
            throw new IllegalArgumentException("Координаты находятся за пределами карты");
        }
        return entities.get(coordinates) == null;
    }

    public boolean isOutOfBounds(Coordinates coordinates) {
        return (coordinates.row() < 0 || coordinates.row() >= height) || (coordinates.column() < 0 || coordinates.column() >= width);
    }

    public void removeEntity(Coordinates coordinates) {
        if (isEmpty(coordinates)){
            throw new NoSuchElementException("Нет сущности на указанных координатах");
        }
        entities.remove(coordinates);
    }

    public Set<Map.Entry<Coordinates, Entity>> getAllEntities() {
        return entities.entrySet();
    }

}
