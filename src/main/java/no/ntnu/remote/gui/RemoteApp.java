package no.ntnu.remote.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import no.ntnu.remote.gui.RemoteView;

public class RemoteApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new RemoteView(primaryStage);
    }
}
