package map;

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
                Entity entity = map.get(coordinates);
                if (entity != null) {
                    System.out.print(getSymbolForEntity(entity));
                } else {
                    System.out.print("  ·  ");
                }
            }
            System.out.println("\n");
        }
        System.out.println();
    }

    private String getSymbolForEntity(Entity entity) {
        if (entity == null) {
            return "  ·  ";
        }

        return switch (entity.getClass().getSimpleName()) {
            case "Predator" -> " 🐺 ";
            case "Herbivore" -> "🐰 ";
            case "Grass" -> " 🥕 ";
            case "Rock" -> " ⛰ ";
            case "Tree" -> " 🌳 ";
            default -> throw new IllegalArgumentException("Неизвестная сущность: " + entity.getClass().getSimpleName());
        };
    }
}
