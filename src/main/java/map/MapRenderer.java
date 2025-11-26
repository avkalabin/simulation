package map;

import java.util.Optional;

import entity.Entity;

public class MapRenderer {

    WorldMap map;

    public MapRenderer(WorldMap map) {
        this.map = map;
    }

    public void render() {

        for (int row = 0; row < map.getHeight(); row++) {
            for (int column = 0; column < map.getWidth(); column++) {
                Coordinates coordinates = new Coordinates(row, column);
                Optional<Entity> entity = map.get(coordinates);
                entity.ifPresentOrElse(
                    e -> System.out.print(getSymbolForEntity(e)),
                    () -> System.out.print("  ·  ")
                );

            }
            System.out.println("\n");
        }
        System.out.println();
    }

    private String getSymbolForEntity(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Predator" -> " 🐺 ";
            case "Herbivore" -> "🐰 ";
            case "Grass" -> " 🥕 ";
            case "Rock" -> " ⛰ ";
            case "Tree" -> " 🌳 ";
            default -> throw new IllegalArgumentException("Unknown entity: " + entity.getClass().getSimpleName());
        };
    }
}
