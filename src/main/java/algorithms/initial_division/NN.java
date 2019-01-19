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
        points.forEach(point -> distanceLengths.put(point, EuclideanDistance.calc(actualPoint, point)));
        Map.Entry<City, Double> min = Collections.min(distanceLengths.entrySet(), Comparator.comparing(Map.Entry::getValue));
        return min.getKey();
    }
}