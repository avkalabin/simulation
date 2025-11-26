package service;

import java.util.Optional;

import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import entity.Predator;
import map.Coordinates;
import map.WorldMap;

public class InteractionServiceImp implements InteractionService {

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

        System.out.printf("Волк атакует! Урон: %d, HP жертвы: %d", damage, herbivore.getHp());
        System.out.println();

        if (herbivore.getHp() <= 0) {
            map.removeEntity(targetPosition);
            System.out.println("Зайца сьели!");
        }
    }

    @Override
    public void eatGrass(WorldMap map, Coordinates herbivorePosition, Coordinates grassPosition) {

        Optional<Entity> herbivoreEntity = map.get(herbivorePosition);
        Optional<Entity>  grassEntity = map.get(grassPosition);

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

        System.out.printf("Заяц съел морковку! HP зайца: %d", herbivore.getHp());
        System.out.println();
    }
}
