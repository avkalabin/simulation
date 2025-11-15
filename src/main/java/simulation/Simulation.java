package simulation;

import java.util.ArrayList;
import java.util.List;

import action.IAction;
import action.InitIAction;
import action.TurnIAction;
import map.MapRenderer;
import map.WorldMap;
import service.*;

public class Simulation {

    private final WorldMap map;
    private final MapRenderer renderer;
    private final List<IAction> initIActions;
    private final List<IAction> turnIActions;

    private boolean isRunning;
    private int turnCounter;
    private boolean isInitialized;

    public Simulation(int mapHeight, int mapWidth) {
        this.map = new WorldMap(mapHeight, mapWidth);
        this.renderer = new MapRenderer(map);
        this.initIActions = new ArrayList<>();
        this.turnIActions = new ArrayList<>();
        this.isRunning = false;
        this.turnCounter = 0;
        this.isInitialized = false;

        setupActions();
    }

    public void nextTurn() {
        turnCounter++;
        System.out.println("ХОД №" + turnCounter);
        for (IAction IAction : turnIActions) {
            IAction.execute(map);
        }
        renderer.render();
    }

    public void startSimulation() {

        initialize();

        isRunning = true;

        while (isRunning) {
            nextTurn();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void pauseSimulation() {
        isRunning = false;
    }

    private void setupActions() {
        IMovementService movementService = new MovementService();
        IIteractionService interactionService = new InteractionService();
        INavigationService navigationService = new NavigationService();

        initIActions.add(new InitIAction(movementService, interactionService, navigationService));
        turnIActions.add(new TurnIAction());
    }

    private void initialize() {
        if (isInitialized) {
            return;
        }

        for (IAction IAction : initIActions) {
            IAction.execute(map);
        }
        System.out.println("Начальное состояние: ");
        renderer.render();

        isInitialized = true;
    }
}
