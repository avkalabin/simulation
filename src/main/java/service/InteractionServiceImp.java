package service;

import java.util.Optional;

import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import entity.Predator;
import map.Coordinates;
import map.WorldMap;

public class InteractionServiceImp implements InteractionService {

    private final InteractionCallback callback;

    public InteractionServiceImp(InteractionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void attack(WorldMap map, Coordinates attackerPosition, Coordinates targetPosition) {

        Optional<Entity> attackerEntity = map.get(attackerPosition);
        Optional<Entity> targetEntity = map.get(targetPosition);

        if (attackerEntity.isEmpty() || targetEntity.isEmpty()) {
            return;
        }

        if (!(attackerEntity.get() instanceof Predator predator)) {
            return;
        }

        if (!(targetEntity.get() instanceof Herbivore herbivore)) {
            return;
        }

        int damage = predator.getAttackPower();
        herbivore.takeDamage(damage);

        boolean isKilled = herbivore.getHp() <= 0;

        if (isKilled) {
            map.removeEntity(targetPosition);
        }

        callback.onAttack(damage, herbivore.getHp(), isKilled);
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
