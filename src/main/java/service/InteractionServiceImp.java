package service;

import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import entity.Predator;
import map.Coordinates;
import map.WorldMap;

import static entity.Grass.HP_VALUE;

public class InteractionServiceImp implements InteractionService {

    @Override
    public void attack(WorldMap map, Coordinates attackerPosition, Coordinates targetPosition) {

        Entity attackerEntity = map.get(attackerPosition);
        Entity targetEntity = map.get(targetPosition);

        if (!(attackerEntity instanceof Predator predator)) {
            return;
        }

        if (!(targetEntity instanceof Herbivore herbivore)) {
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

        Entity herbivoreEntity = map.get(herbivorePosition);
        Entity grassEntity = map.get(grassPosition);

        if (!(herbivoreEntity instanceof Herbivore herbivore)) {
            return;
        }

        if (!(grassEntity instanceof Grass)) {
            return;
        }

        map.removeEntity(grassPosition);
        herbivore.heal(HP_VALUE);

        System.out.printf("Заяц съел морковку! HP зайца: %d", herbivore.getHp());
        System.out.println();
    }
}
