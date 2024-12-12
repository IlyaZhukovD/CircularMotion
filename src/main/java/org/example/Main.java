package org.example;

import java.util.List;

public class Main {
    //circularRoadCount must be 2 or more
    //движение против часовой
    private static final int circularRoadCount = 3;
    private static final int sectorCount = 8;
//    private static final List<Integer> roadInSectorNumbers = List.of(2, 5, 8);
//    private static final List<Double> roadInProbabilityOfSpawn = List.of(0.2, 0.5, 0.8);
    private static final List<Integer> roadInSectorNumbers = List.of(2, 5);
    private static final List<Double> roadInProbabilityOfSpawn = List.of(0.9, 0.9);
    private static final int roadInCount = roadInSectorNumbers.size();

    public static void main(String[] args) {
        CircularRoadManager circularRoadManager = new CircularRoadManager(
                circularRoadCount, sectorCount, roadInSectorNumbers, roadInProbabilityOfSpawn);
        circularRoadManager.launchRoadInTraffic(roadInCount);
    }
}