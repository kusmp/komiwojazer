package sample;


import algorithms.initial_division.DivisionAlgorithm;
import algorithms.initial_division.DivisionAlgorithmType;
import algorithms.optimalization.ILP;
import algorithms.optimalization.LP;
import algorithms.optimalization.OptimalizationAlgorithm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import javax.xml.transform.sax.SAXSource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Controller {

    private static List<City> cities = new ArrayList<>();
    private static List<City> orderedCities = new ArrayList<>();
    private static List<City> orderedCities2 = new ArrayList<>();

    private Controller() {
    }

    private static List<Circle> addPoint(Color color) {
        cities = new ArrayList<>();
        List<Circle> circles = new ArrayList<>();
        Parser parser = new Parser();
        List<String> points = parser.readCitiesCoordList();
        String[] splitPoints;
        for (String element : points) {
            splitPoints = element.split(" ");
            cities.add(new City(splitPoints[0], splitPoints[1]));
        }
        for (City city : cities) {

            Circle circle = new Circle();
            circle.setCenterX(city.getX());
            circle.setCenterY(city.getY());
            circle.setRadius(12);
            circle.setFill(color);

            circles.add(circle);

        }
        return circles;
    }

    private static List<Line> joinPoints(List<City> orderedCities, Color color) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < orderedCities.size(); i++) {
            int next = i + 1;
            if (i == orderedCities.size() - 1) {
                next = 0;
            }
            Line line = new Line();
            Scale scale = new Scale();
            line.setStartX(orderedCities.get(i).getX());
            line.setStartY(orderedCities.get(i).getY());
            line.setEndX(orderedCities.get(next).getX());
            line.setEndY(orderedCities.get(next).getY());
            line.setStroke(color);
            line.setStrokeWidth(5);
            line.setVisible(true);
            scale.setX(0.3);
            scale.setY(0.3);
            scale.setPivotX(80);
            scale.setPivotY(40);
            line.getTransforms().addAll(scale);
            lines.add(line);
        }
        return lines;
    }

    private static void addCities(DivisionAlgorithmType algorithmType) {
        List<City> listofCities = new ArrayList<>(cities);
        List<List<City>> listOfPairs = new ArrayList<>();
        List<List<City>> listOfPairs2 = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        List<List<City>> lists = Parser.divideCitiesToEqualPair(listofCities, algorithmType);
        List<Double> pathLengths = new ArrayList<>();

        Map<Integer, List<List<Integer>>> leftMap = new HashMap<>();
        Map<Integer, List<List<Integer>>> rightMap = new HashMap<>();
        OptimalizationAlgorithm alg = new LP();


        //System.out.println(listOfRightIndex);
        List<Integer> leftPairs = new ArrayList<>();
        List<Integer> rightPairs = new ArrayList<>();
        List<List<Integer>> duplicatesCount = new ArrayList<>();
        List<List<Integer>> listOfLeftIndex1 = new ArrayList<>();
        List<List<Integer>> listOfRightIndex1 = new ArrayList<>();

        for (int m = 0; m < 1000; m++) {
            lists = Parser.divideCitiesToEqualPair(listofCities, algorithmType);
            orderedCities = alg.arrangePoints(lists.get(0));
            orderedCities2 = alg.arrangePoints(lists.get(1));

            List<Integer> listOfLeftIndex = new ArrayList<>();
            List<Integer> listOfRightIndex = new ArrayList<>();
            for (City orderedCity : orderedCities) {
                listOfLeftIndex.add(cities.indexOf(orderedCity));
            }
            for (City orderedCity : orderedCities2) {
                listOfRightIndex.add(cities.indexOf(orderedCity));
            }

            for (int i = 0; i < listOfLeftIndex.size(); i++) {
                int next = i + 1;
                if (next == listOfLeftIndex.size()) {
                    next = 0;
                }
                leftPairs.add(listOfLeftIndex.get(i));
                leftPairs.add(listOfLeftIndex.get(next));
                listOfLeftIndex1.add(leftPairs);
                leftPairs = new ArrayList<>();
            }
            duplicatesCount.add(leftPairs);

            for (int i = 0; i < listOfRightIndex.size(); i++) {
                int next = i + 1;
                if (next == listOfRightIndex.size()) {
                    next = 0;
                }
                rightPairs.add(listOfRightIndex.get(i));
                rightPairs.add(listOfRightIndex.get(next));
                listOfRightIndex1.add(rightPairs);
                rightPairs = new ArrayList<>();
            }
            System.out.println(m);
            rightMap.put(m, listOfRightIndex1);
        }

        Set<List<Integer>> uniqueLeft = new HashSet<>(listOfRightIndex1);
        for (List<Integer> key : uniqueLeft) {
            System.out.println(key + ": " + Collections.frequency(listOfRightIndex1, key));
        }

        Set<List<Integer>> uniqueRight = new HashSet<>(listOfRightIndex1);
        for (List<Integer> key : uniqueRight) {
            System.out.println(key + ": " + Collections.frequency(listOfRightIndex1, key));
        }




        long estimatedTime = System.currentTimeMillis() - startTime;

//        System.out.println("Max: " + Collections.max(pathLengths));
//        System.out.println("Min: " + Collections.min(pathLengths));
//        System.out.println("Average: " + pathLengths.stream().mapToDouble(a -> a).average());
//        System.out.println("Time: " + (double) estimatedTime / (double) 1000);


//
//        orderedCities = alg.arrangePoints(lists.get(0));
//        orderedCities2 = alg.arrangePoints(lists.get(1));
    }


    static void show(DivisionAlgorithmType algorithmType) {
        Stage primaryStage = new Stage();
        BorderPane mainRoot = new BorderPane();
        mainRoot.setPadding(new Insets(14));
        Pane root = new Pane();
        root.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        List<Circle> circles = addPoint(Color.BLACK);
        for (Circle circle : circles) {
            Scale scale = new Scale();
            Label label = new Label(Integer.toString(circles.indexOf(circle)));
            final double MAX_FONT_SIZE = 10.0; // define max font size you need
            label.setFont(new Font(MAX_FONT_SIZE));
            scale.setX(0.3);
            scale.setY(0.3);
            scale.setPivotX(80);
            scale.setPivotY(40);
            label.setLayoutX(circle.getCenterX() * 0.3 + 40);
            label.setLayoutY(circle.getCenterY() * 0.3 + 20);
            circle.getTransforms().addAll(scale);
            root.getChildren().add(circle);
            root.getChildren().add(label);
        }
        addCities(algorithmType);
        for (Line line1 : joinPoints(orderedCities, Color.BLUE)) {
            root.getChildren().add(line1);
        }
        for (Line line1 : joinPoints(orderedCities2, Color.RED)) {
            root.getChildren().add(line1);
        }
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0, 100, 15, 100));
        mainRoot.setCenter(root);
        mainRoot.setBottom(label);
        Scene scene = new Scene(mainRoot, 1980, 1080);
        primaryStage.setTitle("TSP Genetic");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}



