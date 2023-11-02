package no.ntnu.tv.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class SmartTvApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new SmartTvView(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
