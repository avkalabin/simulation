package service;

public interface InteractionCallback {

    void onAttack(int damage, int hpAfter, boolean isKilled);

    void onEat(int hpAfter);
}
