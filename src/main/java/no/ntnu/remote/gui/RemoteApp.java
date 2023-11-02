package no.ntnu.remote.gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class of the RemoteApp application.
 *
 * @author Anders Lund
 * @version 02.11.2023
 */
public class RemoteApp extends Application {

    /**
     * The main method for launching the RemoteApp application.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and starts the RemoteApp application.
     *
     * @param primaryStage The primary JavaFX stage for the application.
     * @throws Exception If there is an issue during the application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        new RemoteView(primaryStage);
    }
}
