package practice.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import practice.logiclayer.CellController;
import practice.logiclayer.CellGrid;

public class SceneController {

    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 300;

    private int height = 100;
    private int width = 100;
    private int cellSize = 3;
    private int cellQuantity = 0;
    private int gameSpeed = 500;

    private Stage primaryStage;
    private Scene gameplayScene;

    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createMenuScene();
    }

    public void start() {
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
    }

    private void createMenuScene() {
        Button startButton = new Button("Запуск");
        startButton.setOnAction((ActionEvent event) -> {
            createGameplayScene(width, height, cellQuantity);
            primaryStage.setScene(this.gameplayScene);
        });
        Button optionsButton = new Button("Настройки");
        optionsButton.setOnAction((ActionEvent event) -> createSettingsScene());
        Button exitButton = new Button("Выход");
        exitButton.setOnAction((ActionEvent event) -> primaryStage.close());
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(startButton, optionsButton, exitButton);
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(hBox, WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    private void createSettingsScene() {
        Label widthLabel = new Label("Ширина");
        Slider widthSlider = new Slider(10, 600, width);
        Label heightLabel = new Label("Высота");
        Slider heightSlider = new Slider(10, 600, height);
        Label cellSizeLabel = new Label("Размер клетки");
        Slider cellSizeSlider = new Slider(1, 5, cellSize);
        Label cellQuantityLabel = new Label("Количество клеток");
        Slider cellQuantitySlider = new Slider(0, 5, 5);
        Label gameSpeedLabel = new Label("Скорость игры");
        Slider gameSpeedSlider = new Slider(100, 1000, gameSpeed);
        Button cancelButton = new Button("Назад");
        cancelButton.setOnAction((event -> createMenuScene()));
        Button acceptButton = new Button("Принять");
        acceptButton.setOnAction(event -> {
            width = widthSlider.valueProperty().intValue();
            height = heightSlider.valueProperty().intValue();
            cellSize = cellSizeSlider.valueProperty().intValue();
            cellQuantity = Math.abs(cellQuantitySlider.valueProperty().intValue() - 5);
            gameSpeed = Math.abs(gameSpeedSlider.valueProperty().intValue() - 1100);
            createMenuScene();
        });
        HBox bottomBox = new HBox(cancelButton, acceptButton);
        VBox vBox = new VBox(widthLabel, widthSlider, heightLabel,
                heightSlider, cellSizeLabel, cellSizeSlider,
                cellQuantityLabel, cellQuantitySlider, gameSpeedLabel,
                gameSpeedSlider, bottomBox);
        vBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);
        Scene optionsScene = new Scene(hBox, WINDOW_WIDTH, WINDOW_HEIGHT);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        primaryStage.setScene(optionsScene);
    }

    private void createGameplayScene(int width, int height, int cellQuantity) {
        Canvas canvas = new Canvas((width * cellSize), (height * cellSize));
        CellGrid cellGrid = new CellGrid(width, height, cellQuantity);
        CellController cellController = new CellController(cellGrid);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(canvas);
        this.gameplayScene = new Scene(stackPane);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                (ActionEvent event) -> {
                    cellController.updateCellStates();
                    updateGrid(graphicsContext, cellController.getCellGrid().getCellStates());
                }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void updateGrid(GraphicsContext graphicsContext, boolean[][] states) {
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[i].length; j++) {
                if (states[i][j]) {
                    graphicsContext.setFill(Color.WHITE);
                } else {
                    graphicsContext.setFill(Color.BLACK);
                }
                graphicsContext.fillRect((cellSize * i), (cellSize * j), cellSize, cellSize);
            }
        }
    }

}
