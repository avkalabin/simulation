package service;

import map.Coordinates;
import map.WorldMap;

public interface InteractionService {
    void attack(WorldMap map, Coordinates attackerPosition, Coordinates targetPosition);

    void eatGrass(WorldMap map, Coordinates herbivorePosition, Coordinates grassPosition);
}
