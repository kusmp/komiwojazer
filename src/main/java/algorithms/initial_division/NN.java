package algorithms.initial_division;

import algorithms.initial_division.DivisionAlgorithm;
import sample.City;
import sample.EuclideanDistance;

import java.util.*;

public class NN implements DivisionAlgorithm {

    public NN() {}

    @Override
    public City nextPoint(List<City> points, City actualPoint) {
        Map<City, Double> distanceLengths = new HashMap<>();
        for (City point : points) {
            distanceLengths.put(point, EuclideanDistance.calc(actualPoint, point));
        }
        Map.Entry<City, Double> min = null;
        for (Map.Entry<City, Double> entry : distanceLengths.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        return min.getKey();
    }
}