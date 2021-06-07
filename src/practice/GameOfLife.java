package practice;

import javafx.application.Application;
import javafx.stage.Stage;
import practice.view.SceneController;

public class GameOfLife extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneController sceneController = new SceneController(primaryStage);
        sceneController.start();
    }

}
