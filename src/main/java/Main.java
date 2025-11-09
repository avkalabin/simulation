import entity.Grass;
import entity.Rock;
import entity.Tree;
import entity.Herbivore;
import entity.Predator;
import map.Coordinates;
import map.Map;
import map.MapRenderer;

public class Main {
    public static void main(String[] args) {

        Map map = new Map(8, 20);
        map.put(new Coordinates(0,0), new Predator());
        map.put(new Coordinates(1,17), new Herbivore());
        map.put(new Coordinates(2,2), new Tree());
        map.put(new Coordinates(5,15), new Grass());
        map.put(new Coordinates(7,1), new Rock());
        MapRenderer mapRenderer = new MapRenderer(map);
        mapRenderer.render();
    }
}
