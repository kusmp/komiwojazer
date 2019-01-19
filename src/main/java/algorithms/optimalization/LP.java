package algorithms.optimalization;
import sample.City;
import sample.EuclideanDistance;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LP implements OptimalizationAlgorithm {

    @Override
    public List<City> arrangePoints(List<City> path) {
        double initialDistance = EuclideanDistance.calcForPath(path);
        return arrangeNewPath(path, initialDistance);
    }

    private List<List<City>> twoOpt(List<City> citiesPath) {
        List<List<City>> paths = new ArrayList<>();
        int pathLength = citiesPath.size();
        for (int i = 0; i < pathLength - 1; i++) {

            for (int j = i + 1; j < pathLength - 1; j++) {

                List<City> firstList = new ArrayList<>(citiesPath.subList(0, i));
                List<City> secondList = new ArrayList<>(citiesPath.subList(i, j));
                Collections.reverse(secondList);
                List<City> thirdList = new ArrayList<>(citiesPath.subList(j, pathLength));
                List<City> newPath = new ArrayList<>();

                newPath.addAll(firstList);
                newPath.addAll(secondList);
                newPath.addAll(thirdList);

                paths.add(newPath);
            }
        }

        return paths;
    }

    public List<City> arrangeNewPath(List<City> currentPath, double currentDistance){
        int iterator = 0;
        while (iterator < 10) {
            List<List<City>> newPath = twoOpt(currentPath);
            for (List<City> path : newPath) {
                double distance = EuclideanDistance.calcForPath(path);
                if (distance < currentDistance) {
                    currentDistance = distance;
                    currentPath = path;
                    iterator = 0;
                } else {
                    iterator++;
                }
            }
        }
        return currentPath;
    }

}