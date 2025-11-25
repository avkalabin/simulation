package simulation;

import java.util.ArrayList;
import java.util.List;

import action.Action;
import action.InitAction;
import action.TurnAction;
import map.MapRenderer;
import map.WorldMap;
import service.*;

public class Simulation {

    private final WorldMap map;
    private final MapRenderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    private boolean isRunning;
    private int turnCounter;
    private boolean isInitialized;

    public Simulation(int mapHeight, int mapWidth) {
        this.map = new WorldMap(mapHeight, mapWidth);
        this.renderer = new MapRenderer(map);
        this.initActions = new ArrayList<>();
        this.turnActions = new ArrayList<>();
        this.isRunning = false;
        this.turnCounter = 0;
        this.isInitialized = false;

        setupActions();
    }

    public void nextTurn() {
        turnCounter++;
        System.out.println("ХОД №" + turnCounter);
        for (Action action : turnActions) {
            action.execute(map);
        }
        renderer.render();
    }

    public void startSimulation() {

        initialize();

        isRunning = true;

        while (isRunning) {
            nextTurn();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void pauseSimulation() {
        isRunning = false;
    }

    private void setupActions() {
        MovementService movementService = new MovementServiceImp();
        InteractionService interactionService = new InteractionServiceImp();
        NavigationService navigationService = new NavigationServiceImp();

        initActions.add(new InitAction(movementService, interactionService, navigationService));
        turnActions.add(new TurnAction());
    }

    private void initialize() {
        if (isInitialized) {
            return;
        }

        for (Action action : initActions) {
            action.execute(map);
        }
        System.out.println("Начальное состояние: ");
        renderer.render();

        isInitialized = true;
    }
}
