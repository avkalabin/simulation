package service;

import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import map.Coordinates;
import map.WorldMap;

import java.util.Optional;

public class HerbivoreInteractionServiceImp implements HerbivoreInteractionService {

    private final InteractionCallback callback;

    public HerbivoreInteractionServiceImp(InteractionCallback callback) {
        this.callback = callback;
    }


    @Override
    public void eatGrass(WorldMap map, Coordinates herbivorePosition, Coordinates grassPosition) {

        Optional<Entity> herbivoreEntity = map.get(herbivorePosition);
        Optional<Entity> grassEntity = map.get(grassPosition);

        if (herbivoreEntity.isEmpty() || grassEntity.isEmpty()) {
            return;
        }

        if (!(herbivoreEntity.get() instanceof Herbivore herbivore)) {
            return;
        }

        if (!(grassEntity.get() instanceof Grass)) {
            return;
        }

        map.removeEntity(grassPosition);
        herbivore.heal(Grass.HP_VALUE);

        callback.onEat(herbivore.getHp());
    }
}
