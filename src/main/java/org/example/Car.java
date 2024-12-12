package org.example;

public class Car extends Thread {
    private final int countOfSectors;
    private int currentSector;
    private CircularRoad circularRoad;
    private final int whichSectorToJoin;
    private final int carId;

    public Car(int countOfSectors, CircularRoad circularRoad, int whichSectorToJoinInitially, int carId) {
        this.countOfSectors = countOfSectors;
        this.circularRoad = circularRoad;
        this.whichSectorToJoin = whichSectorToJoinInitially;
        this.carId = carId;
    }

    @Override
    public void run() {
        try {
            circularRoad.initialJoin(this, whichSectorToJoin);
            currentSector = whichSectorToJoin;
            while (true) {
                MotionDirection nextAvailableAction = circularRoad.getNextAvailableAction(currentSector);
                switch (nextAvailableAction) {
                    case LEFT: {
                        circularRoad.getLeftRoad().lock(this, getCurrentSector(), nextAvailableAction);
                        circularRoad.release(this, getCurrentSector());
                        circularRoad = circularRoad.getLeftRoad();
                        break;
                    }
                    case RIGHT: {
                        circularRoad.getRightRoad().lock(this, getCurrentSector(), nextAvailableAction);
                        circularRoad.release(this, getCurrentSector());
                        circularRoad = circularRoad.getRightRoad();
                        break;
                    }
                    case STRAIGHT: {
                        circularRoad.lock(this, getNextSector(), nextAvailableAction);
                        circularRoad.release(this, getCurrentSector());
                        decrementSector();
                        break;
                    }
                    case GOOUT: {
                        circularRoad.release(this, getCurrentSector());
                        break;
                    }
                }
                if (nextAvailableAction == MotionDirection.GOOUT) {
                    System.out.println("car id " + getCarId() + " deleted");
                    break;
                } else {
                    System.out.println("For car id " + getCarId() + " new sector is " + getCurrentSector()
                            + " and new road is " + circularRoad.getRoadNumber());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getNextSector() {
        if (currentSector == 1) return countOfSectors;
        return currentSector - 1;
    }

    public int getCurrentSector() {
        return currentSector;
    }



    public void decrementSector() {
        if (currentSector == 1) currentSector = countOfSectors;
        else currentSector -= 1;
    }

    public int getCarId() {
        return carId;
    }
}
