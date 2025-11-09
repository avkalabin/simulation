package map;

import entity.Entity;

public class MapRenderer {

    Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void render() {

        for (int row = 0; row < map.getRow(); row++) {
            for (int column = 0; column < map.getColumn(); column++) {
                Coordinates coordinates = new Coordinates(row, column);
                Entity entity = map.get(coordinates);
                if (entity != null) {
                    System.out.print(entity.getSymbol());
                } else {
                    System.out.print("  ·  ");
                }
            }
            System.out.println("\n");
        }
    }

}
