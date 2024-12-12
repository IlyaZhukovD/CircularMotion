package org.example;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CircularRoadManager {
    private final List<CircularRoad> circularRoadList = new ArrayList<>();
    private final int countOfSector;
    private final List<Integer> roadInSectorNumbers;
    private final List<Double> roadInProbabilityOfSpawn;
    public static AtomicInteger carIndexes = new AtomicInteger();

    public CircularRoadManager(int circularRoadCount, int countOfSector,
                               List<Integer> roadInSectorNumbers, List<Double> roadInProbabilityOfSpawn) {
        this.countOfSector = countOfSector;
        this.roadInSectorNumbers = roadInSectorNumbers;
        this.roadInProbabilityOfSpawn = roadInProbabilityOfSpawn;
        createCircularRoads(circularRoadCount, countOfSector);

    }

    private void createCircularRoads(int circularRoadCount, int sectorCount) {
        boolean isDeepestRoad = false;
        Set<Integer> roadInSectors = new HashSet<>(roadInSectorNumbers);
        for (int i = 1; i <= circularRoadCount; i++) {
            if (i == circularRoadCount) isDeepestRoad = true;
            circularRoadList.add(new CircularRoad(i, isDeepestRoad, sectorCount, roadInSectors));
        }
        setRightAndLeftRoads();
    }

    private void setRightAndLeftRoads() {
        for (int i = 0; i < circularRoadList.size(); i++) {
            CircularRoad circularRoad = circularRoadList.get(i);
            CircularRoad rightRoad = null;
            CircularRoad leftRoad = null;
            if (i == 0) {
                leftRoad = circularRoadList.get(i + 1);
            } else if (i == circularRoadList.size() - 1) {
                rightRoad = circularRoadList.get(i - 1);
            } else {
                rightRoad = circularRoadList.get(i - 1);
                leftRoad = circularRoadList.get(i + 1);
            }
            circularRoad.setLeftRoad(leftRoad);
            circularRoad.setRightRoad(rightRoad);
        }
    }

    public void launchRoadInTraffic(int roadInCount) {
        CircularRoad outerRoad = circularRoadList.get(0);
        for (int i = 0; i < roadInCount; i++) {
            RoadIn road = new RoadIn(roadInSectorNumbers.get(i), roadInProbabilityOfSpawn.get(i), outerRoad, countOfSector);
            road.setName("road id = " + roadInSectorNumbers.get(i));
            road.start();
        }
    }
}
