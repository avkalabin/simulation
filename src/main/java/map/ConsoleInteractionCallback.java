package map;

import service.InteractionCallback;

public class ConsoleInteractionCallback implements InteractionCallback {

    @Override
    public void onAttack(int damage, int hpAfter, boolean isKilled) {

        System.out.printf("Волк атакует! Урон: %d, HP жертвы: %d", damage, hpAfter);
        System.out.println();
        if (isKilled) {
            System.out.println("Зайца сьели!");
        }
    }

    @Override
    public void onEat(int hpAfter) {
        System.out.printf("Заяц съел морковку! HP зайца: %d", hpAfter);
        System.out.println();
    }
}
