package map;

import entity.Entity;

import java.util.HashMap;
import java.util.List;

public class Map {
    private final int row;
    private final int column;
    private final HashMap<Coordinates, Entity> entities = new HashMap<>();

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Map(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void put(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public Entity get(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public boolean isEmpty(Coordinates coordinates) {
        return entities.get(coordinates) == null;
    }

    public boolean isOutOfBounds(Coordinates coordinates) {
        return (coordinates.row() < 0 || coordinates.row() >= row) || (coordinates.column() < 0 || coordinates.column() >= column);
    }

    public void removeEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    List<Coordinates> getAvailableMoves(){
        List<Coordinates> result = List.of();
        return result;

    }

}
