import map.WorldMap;
import simulation.Simulation;

public class MainWithPause {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(new WorldMap(10, 10));

        // Запуск симуляции
        Thread simulationThread = new Thread(simulation::startSimulation);
        simulationThread.start();

        // Работает 5 секунд
        sleep(5000);

        // Пауза
        simulation.pauseSimulation();
        joinThread(simulationThread);
        System.out.println("Пауза 3 секунды...");
        sleep(3000);

        // Возобновление
        Thread newThread = new Thread(simulation::startSimulation);
        newThread.start();
        sleep(5000);

        // Остановка
        simulation.pauseSimulation();
        joinThread(newThread);
        System.out.println("Симуляция завершена");

        // Ручной вызов хода
        System.out.println("Последний ход");
        simulation.nextTurn();
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Прервано во время ожидания");
        }
    }

    private static void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Прервано при ожидании потока");
        }
    }
}
