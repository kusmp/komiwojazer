package sample;

import java.util.List;

public class EuclideanDistance {
    private EuclideanDistance() {
    }

    public static double calc(City a, City b) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }

    public static double calcForPath(List<City> path) {
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += calc(path.get(i), path.get(i + 1));
        }
        distance += calc(path.get(path.size() - 1), path.get(0));
        return distance;
    }
}
