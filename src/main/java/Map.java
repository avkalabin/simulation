import entity.Entity;

import java.util.HashMap;

public class Map {
    private final HashMap<Coordinates, Entity> entities = new HashMap<>();
    public final int row;
    public final int column;

    public Map(int row, int column) {
        this.row = row;
        this.column = column;
    }

    void put(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    Entity get(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    boolean isEmpty(Coordinates coordinates) {
        return entities.get(coordinates) == null;
    }

    boolean isOutOfBounds(Coordinates coordinates) {
        return (coordinates.row() < 0 || coordinates.row() >= row) || (coordinates.column() < 0 || coordinates.column() >= column);
    }

    void removeEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

}
