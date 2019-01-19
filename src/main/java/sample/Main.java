package sample;

import algorithms.initial_division.DivisionAlgorithmType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Controller.show(DivisionAlgorithmType.GREEDY_CYCLE);
        Controller.show(DivisionAlgorithmType.NN);
    }
}
