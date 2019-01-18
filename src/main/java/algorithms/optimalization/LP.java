package algorithms.optimalization;

import sample.City;
import sample.EuclideanDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LP implements OptimalizationAlgorithm {
    private List<City> currentPath;
    private double currentTotalDistance;

    public LP(List<City> path) {
        this.currentPath = path;
        this.currentTotalDistance = EuclideanDistance.calcForPath(currentPath);
        System.out.println(this.currentTotalDistance);
    }

    @Override
    public List<City> arrangePoints() {
        boolean next;
        do {
            next = false;
            List<List<City>> newPaths = this.twoOpt();
            for (List<City> path : newPaths) {
                double newDistance = EuclideanDistance.calcForPath(path);
                if (newDistance < this.currentTotalDistance) {
                    this.currentTotalDistance = newDistance;
                    System.out.println(this.currentTotalDistance);
                    this.currentPath = path;
                    next = true;
                }
            }
        } while (next);

        return this.currentPath;
    }

    private List<List<City>> twoOpt() {
        List<List<City>> N = new ArrayList<>();
        int pathLength = currentPath.size();
        for (int i = 0; i < pathLength - 1; i++) {
            for (int j = i + 1; j < pathLength - 1; j++) {
                List<City> l1 = new ArrayList<>(currentPath.subList(0, i));
                List<City> l2 = new ArrayList<>(currentPath.subList(i, j));
                Collections.reverse(l2);
                List<City> l3 = new ArrayList<>(currentPath.subList(j, pathLength));
                List<City> newPath = new ArrayList<>();
                newPath.addAll(l1);
                newPath.addAll(l2);
                newPath.addAll(l3);
                N.add(newPath);
            }
        }
        return N;
    }
}
