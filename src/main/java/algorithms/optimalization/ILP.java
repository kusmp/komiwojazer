package algorithms.optimalization;

import algorithms.initial_division.GreedyCycle;
import sample.City;
import sample.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ILP implements OptimalizationAlgorithm {


    private final Random random = new Random();
    private final LP localSearch = new LP();

    @Override
    public List<City> arrangePoints(List<City> citiesPath) {
        int noImprovalCount = 0;
        double bestPathDistance = EuclideanDistance.calcForPath(citiesPath);
        while (noImprovalCount < 10) {
            List<City> newCitiesPath = perturbation(citiesPath);
            newCitiesPath = localSearch.arrangePoints(newCitiesPath);
            double currentIterationDistance = EuclideanDistance.calcForPath(newCitiesPath);
            if (currentIterationDistance < bestPathDistance) {
                bestPathDistance = currentIterationDistance;
                citiesPath = newCitiesPath;
                noImprovalCount = 0;
            } else {
                noImprovalCount++;
            }
            System.out.println(noImprovalCount);
        }
        return citiesPath;
    }

    public List<City> perturbation(List<City> path) {
        Random r = new Random();
        int pathLength = path.size();
        List<City> newPath = new ArrayList<>(path);
        int random1 = r.nextInt(pathLength - 1);
        int random2 = r.nextInt(pathLength - random1 - 1) + random1 + 1;
        List<City> firstList = new ArrayList<>();
        firstList = path.subList(0, random1);
        List<City> secondList = new ArrayList<>();
        secondList = path.subList(random1, random2);
        List<City> thirdList = new ArrayList<>();
        thirdList = path.subList(random2, pathLength);
        GreedyCycle alg = new GreedyCycle(thirdList.get(0));
        List<City> tempThirdList = new ArrayList<>();
        for (int i = 0; i < thirdList.size(); i++) {
            tempThirdList.add(alg.nextPoint(thirdList, thirdList.get(i)));
        }
        thirdList = tempThirdList;
        newPath.addAll(firstList);
        newPath.addAll(secondList);
        newPath.addAll(thirdList);

        return newPath;

    }

//    private List<City> perturbate(List<City> citiesPath) {
//        List<City> newPath = new ArrayList<>(citiesPath);
//        for (int i = 0; i < 4; i++) {
//            int randomPoint = random.nextInt(citiesPath.size() - 1) + 1;
//            int nextRandomPoint = random.nextInt(citiesPath.size() - 1) + 1;
//            newPath = switchPoints(citiesPath, randomPoint, nextRandomPoint);
//        }
//        return newPath;
//    }
//
//    private List<City> switchPoints(List<City> citiesPath, int randomPoint, int nextRandomPoint) {
//        List<City> newCitiesPath = new ArrayList<>(citiesPath);
//        City tempCity = newCitiesPath.get(randomPoint);
//        newCitiesPath.set(randomPoint, newCitiesPath.get(nextRandomPoint));
//        newCitiesPath.set(nextRandomPoint, tempCity);
//        return newCitiesPath;
//    }



}