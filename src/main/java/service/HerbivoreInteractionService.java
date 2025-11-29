package service;

import map.Coordinates;
import map.WorldMap;

public interface HerbivoreInteractionService {

    void eatGrass(WorldMap map, Coordinates herbivorePosition, Coordinates grassPosition);
}
