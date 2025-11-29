import map.WorldMap;
import simulation.Simulation;

public class Main {

    public static void main(String[] args) {
        Simulation simulation = new Simulation(new WorldMap(10, 10));
        simulation.startSimulation();
    }
}
