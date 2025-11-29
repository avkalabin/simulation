package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.Creature;
import entity.Entity;
import map.Coordinates;
import map.WorldMap;

public class TurnAction implements Action {

    @Override
    public void execute(WorldMap map) {
        List<Map.Entry<Coordinates, Creature>> creatures = new ArrayList<>();

        for (Map.Entry<Coordinates, Entity> entry : map.getAllEntities()) {
            Entity entity = entry.getValue();

            if (entity instanceof Creature creature) {
                Coordinates position = entry.getKey();
                creatures.add(Map.entry(position, creature));
            }
        }

        for (Map.Entry<Coordinates, Creature> entry : creatures) {
            Creature creature = entry.getValue();
                creature.makeMove(map);
        }
    }
}
