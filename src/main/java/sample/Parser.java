package sample;

import algorithms.initial_division.DivisionAlgorithm;
import algorithms.initial_division.DivisionAlgorithmType;
import algorithms.initial_division.GreedyCycle;
import algorithms.initial_division.NN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parser {
    private static Random rand = new Random();
    private String pattern = "[0-9]{1,4}\\s[0-9]{1,4}\\s[0-9]{1,4}";
    private Pattern r = Pattern.compile(pattern);

    static List<List<City>> divideCitiesToEqualPair(List<City> cities, DivisionAlgorithmType algorithmType) {
        DivisionAlgorithm algorithm = new GreedyCycle();
        if (!algorithmType.equals(DivisionAlgorithmType.GREEDY_CYCLE)) {
            algorithm = new NN();
        }
        City leftFirstCity = cities.get(rand.nextInt(100));
        City rightFirstCity = cities.get(rand.nextInt(100));
        while (leftFirstCity.equals(rightFirstCity)) {
            rightFirstCity = cities.get(rand.nextInt(100));
        }

        List<City> leftList = new ArrayList<>();
        List<City> rightList = new ArrayList<>();
        leftList.add(leftFirstCity);
        rightList.add(rightFirstCity);
        cities.remove(leftFirstCity);
        cities.remove(rightFirstCity);


        City leftActualCity;
        City rightActualCity;
        City leftNextCity;
        City rightNextCity;

        int forSize = cities.size() / 2;
        for (int i = 0; i < forSize; i++) {
            leftActualCity = leftList.get(leftList.size() - 1);
            rightActualCity = rightList.get(rightList.size() - 1);

            leftNextCity = algorithm.nextPoint(cities, leftActualCity);
            leftList.add(leftNextCity);
            cities.remove(leftNextCity);
            rightNextCity = algorithm.nextPoint(cities, rightActualCity);
            rightList.add(rightNextCity);
            cities.remove(rightNextCity);

        }

        return Arrays.asList(leftList, rightList);
    }

    List<String> readCitiesCoordList() {
        List<String> points = new ArrayList<>();
        String[] splitLine;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass()
                .getResourceAsStream("/data/kroA100.tsp")))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher m = r.matcher(line);
                if (m.find()) {
                    splitLine = line.split(" ");
                    points.add(splitLine[1] + " " + splitLine[2]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }
}
