package no.ntnu.tv.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import no.ntnu.tv.SmartTv;
import no.ntnu.tv.TvServer;

public class SmartTvApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new SmartTvView(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
