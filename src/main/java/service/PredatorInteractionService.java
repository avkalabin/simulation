package service;

import map.Coordinates;
import map.WorldMap;

public interface PredatorInteractionService {

    void attack(WorldMap map, Coordinates attackerPosition, Coordinates targetPosition);
}
