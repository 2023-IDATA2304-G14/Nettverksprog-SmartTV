package no.ntnu;

import javafx.application.Application;
import javafx.stage.Stage;
import no.ntnu.remote.gui.RemoteController;
import no.ntnu.remote.gui.RemoteModel;
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
