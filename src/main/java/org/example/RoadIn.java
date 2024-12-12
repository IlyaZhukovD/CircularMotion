package org.example;

import java.util.Random;

import static org.example.CircularRoadManager.carIndexes;

public class RoadIn extends Thread {
    private final int connectedToSector;
    private final double probabilityOfSpawn;
    private final CircularRoad outerRoad;
    private Random random = new Random();
    private final Integer countOfSectors;

    public RoadIn(Integer connectedToSector, double probabilityOfSpawn, CircularRoad outerRoad, Integer countOfSectors) {
        this.connectedToSector = connectedToSector;
        this.probabilityOfSpawn = probabilityOfSpawn;
        this.outerRoad = outerRoad;
        this.countOfSectors = countOfSectors;
    }

    @Override
    public void run() {
        try {
            while (true) {
                double random0and1 = random.nextDouble();
                if (random0and1 < probabilityOfSpawn) {
                    spawnCar();
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void spawnCar() {
        int index = carIndexes.getAndIncrement();
        Car car = new Car(countOfSectors, outerRoad, connectedToSector, index);
        car.start();
        car.setName(String.valueOf(index));
        System.out.println("Start car id " + index);
    }
}
