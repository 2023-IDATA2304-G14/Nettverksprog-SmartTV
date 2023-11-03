package no.ntnu.tv.gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class of the SmartTvApp application.
 *
 * @author Anders Lund
 * @version 02.11.2023
 */
public class SmartTvApp extends Application {

    /**
     * Initialize and starts the SmartTvApp application.
     *
     * @param primaryStage The primary JavaFX stage for the application.
     * @throws Exception If there is an issue during application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        new SmartTvView(primaryStage);
    }

    /**
     * This method is called when the application is stopped.
     *
     * @throws Exception If there is an issue during application shutdown.
     */
    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    /**
     * The main method for launching the SmartTvApp application.
     *
     * @param args The command-lines arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
