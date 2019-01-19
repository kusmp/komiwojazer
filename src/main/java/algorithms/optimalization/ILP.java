package algorithms.optimalization;
import sample.City;
import sample.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ILP implements OptimalizationAlgorithm {

    @Override
    public List<City> arrangePoints(List<City> path) {
        LP lp = new LP();
        int iterator = 0;
        double newPathDistance = EuclideanDistance.calcForPath(path);
        do  {
            List<City> newPath = perturbation(path);
            newPath = lp.arrangePoints(newPath);
            double newDistance = EuclideanDistance.calcForPath(newPath);
            if (newDistance < newPathDistance) {
                newPathDistance = newDistance;
                path = newPath;
                iterator = 0;
            } else {
                iterator++;
            }
        } while (iterator<500);
        return path;
    }


    private List<City> perturbation(List<City> citiesPath) {
        Random random = new Random();
        List<City> newPath = new ArrayList<>(citiesPath);
        for (int i = 0; i < 10; i++) {

            int r1 = random.nextInt(citiesPath.size() - 1) + 1;
            int r2 = random.nextInt(citiesPath.size() - 1) + 1;

            List<City> newCitiesPath = new ArrayList<>(citiesPath);
            City tempCity = newCitiesPath.get(r1);
            City tempCity2 = newCitiesPath.get(r2);

            newCitiesPath.set(r1, tempCity2);
            newCitiesPath.set(r2, tempCity);

            newPath = newCitiesPath;
        }
        return newPath;
    }

}