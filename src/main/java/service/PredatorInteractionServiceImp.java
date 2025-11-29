package service;

import entity.Entity;
import entity.Herbivore;
import entity.Predator;
import map.Coordinates;
import map.WorldMap;

import java.util.Optional;

public class PredatorInteractionServiceImp implements PredatorInteractionService {

    private final InteractionCallback callback;

    public PredatorInteractionServiceImp(InteractionCallback callback) {
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
}
