package algorithms.initial_division;

import sample.City;
import sample.EuclideanDistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreedyCycle implements DivisionAlgorithm {
    public GreedyCycle() {}

    @Override
    public City nextPoint(List<City> points, City actualPoint) {
        Map<City, Double> distanceLengths = new HashMap<>();
        for (City point: points) {
            double summaryDistance = EuclideanDistance.calc(actualPoint, point) + EuclideanDistance.calc(points.get(0), point);
            distanceLengths.put(point, summaryDistance);
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