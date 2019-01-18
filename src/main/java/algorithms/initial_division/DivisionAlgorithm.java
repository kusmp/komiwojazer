package algorithms.initial_division;

import sample.City;

import java.util.List;

public interface DivisionAlgorithm {
    City nextPoint(List<City> points, City actualPoint);
}