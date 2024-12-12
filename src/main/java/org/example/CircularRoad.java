package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.MotionDirection.*;

public class CircularRoad {
    private final int roadNumber;
    private final boolean isDeepestRoad;
    private final Set<Integer> roadInSectors;
    private CircularRoad rightRoad;
    private CircularRoad leftRoad;
    private final ConcurrentHashMap<Integer, Lock> sectors = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public CircularRoad(int roadNumber, boolean isDeepestRoad, int sectorCount, Set<Integer> roadInSectors) {
        this.roadNumber = roadNumber;
        this.isDeepestRoad = isDeepestRoad;
        this.roadInSectors = roadInSectors;
        for (int i = 0; i < sectorCount; i++) {
            sectors.put(i + 1, new ReentrantLock());
        }
    }

    public synchronized MotionDirection getNextAvailableAction(int currentSector) {
        int randomInt = random.nextInt(1, 4);
        MotionDirection nextAction = switch (randomInt) {
            case 1 -> LEFT;
            case 2 -> STRAIGHT;
            case 3 -> RIGHT;
            default -> null;
        };
        if (nextAction == LEFT && isDeepestRoad) {
            return STRAIGHT;
        }
        if (nextAction == RIGHT && roadNumber == 1 && roadInSectors.contains(currentSector)) {
            return GOOUT;
        }
        if (nextAction == RIGHT && roadNumber == 1) {
            return STRAIGHT;
        }
        return nextAction;
    }

    public CircularRoad getLeftRoad() {
        return leftRoad;
    }

    public CircularRoad getRightRoad() {
        return rightRoad;
    }

    public void setRightRoad(CircularRoad rightRoad) {
        this.rightRoad = rightRoad;
    }

    public void setLeftRoad(CircularRoad leftRoad) {
        this.leftRoad = leftRoad;
    }

    public int getRoadNumber() {
        return roadNumber;
    }

    public void initialJoin(Car car, int toSector) throws InterruptedException {
        sectors.get(toSector).lock();
        System.out.println("Car with id " + car.getCarId() + " start initial join on road " + roadNumber + " and sector " + toSector);
        Thread.sleep(100L);
    }

    public void lock(Car car, int whichLock, MotionDirection nextAvailableAction) throws InterruptedException {
        sectors.get(whichLock).lock();
        System.out.println(nextAvailableAction + " car with id " + car.getCarId() + " start join on road " + roadNumber + " and sector " + whichLock);
        Thread.sleep(100L);
    }

    public void release(Car car, int whichRelease) {
        sectors.get(whichRelease).unlock();
        System.out.println("Car with id " + car.getCarId() + " end join on road " + roadNumber + " and sector " + whichRelease);

    }
}
