package algorithms.optimalization;


import sample.City;
import sample.EuclideanDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LP implements OptimalizationAlgorithm {

    @Override
    public List<City> arrangePoints(List<City> citiesPath) {
        long startTime = System.currentTimeMillis();
        double currentTotalDistance = EuclideanDistance.calcForPath(citiesPath);
        boolean next;
        do {
            next = false;
            List<List<City>> newPaths = twoOpt(citiesPath);
            for (List<City> path : newPaths) {
                double newDistance = EuclideanDistance.calcForPath(path);
                if (newDistance < currentTotalDistance) {
                    System.out.println("Dist. delta: " + (newDistance - currentTotalDistance) + " -- Total curr. dist: " + newDistance);
                    currentTotalDistance = newDistance;
                    citiesPath = path;
                    next = true;
                }
            }
        } while (next);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Local Search time elapsed: " + elapsedTime + " ms");
        return citiesPath;
    }

    private List<List<City>> twoOpt(List<City> citiesPath){
            List<List<City>> availablePaths = new ArrayList<>();
            int pathLength = citiesPath.size();
            for (int i = 0; i < pathLength - 1; i++) {
                for (int j = i + 1; j < pathLength - 1; j++) {
                    List<City> beginningSublist = new ArrayList<>(citiesPath.subList(0, i));
                    List<City> middleSublist = new ArrayList<>(citiesPath.subList(i, j));
                    Collections.reverse(middleSublist);
                    List<City> endSublist = new ArrayList<>(citiesPath.subList(j, pathLength));
                    List<City> newPath = new ArrayList<>();
                    newPath.addAll(beginningSublist);
                    newPath.addAll(middleSublist);
                    newPath.addAll(endSublist);
                    availablePaths.add(newPath);
                }
            }
            return availablePaths;
        }
    }
