import entity.Entity;

public class MapRenderer {

    Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void render() {

        for (int row = 0; row < map.row; row++) {
            for (int column = 0; column < map.column; column++) {
                Coordinates coordinates = new Coordinates(row, column);
                Entity entity = map.get(coordinates);
                if (entity != null) {
                    System.out.print(selectUnicodeSpriteForEntity(entity));
                } else {
                    System.out.print("  ·  ");
                }
            }
            System.out.println("\n");
        }
    }


    private String selectUnicodeSpriteForEntity(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Predator" -> " \uD83D\uDC3A ";
            case "Herbivore" -> " \uD83D\uDC30 ";
            case "Grass" -> " \uD83C\uDF3F ";
            case "Rock" -> " ⛰ ";
            case "Tree" -> " \uD83C\uDF33 ";
            default -> "  ·  ";
        };
    }
}
