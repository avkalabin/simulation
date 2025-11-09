import entity.Grass;
import entity.Rock;
import entity.Tree;
import entity.creature.Herbivore;
import entity.creature.Predator;

public class Main {
    public static void main(String[] args) {

        Map map = new Map(10, 10);
        map.put(new Coordinates(0,0), new Predator());
        map.put(new Coordinates(1,1), new Herbivore());
        map.put(new Coordinates(2,2), new Tree());
        map.put(new Coordinates(3,3), new Grass());
        map.put(new Coordinates(4,4), new Rock());
        MapRenderer mapRenderer = new MapRenderer(map);
        mapRenderer.render();
    }
}
