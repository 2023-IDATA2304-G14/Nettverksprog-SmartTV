package no.ntnu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RemoteView extends Application {
    Button powerOnButton;
    Button powerOffButton;
    Button channelUpButton;
    Button channelDownButton;
    Button setChannelButton;
    TextField channelField;
    TextArea responseArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Remote Control");

        BorderPane root = new BorderPane();
        GridPane mainPanel = new GridPane();
        mainPanel.setPadding(new Insets(10, 10, 10, 10));
        mainPanel.setVgap(10);
        mainPanel.setHgap(10);

        powerOnButton = new Button("Turn On");
        powerOffButton = new Button("Turn Off");
        channelUpButton = new Button("Channel Up");
        channelDownButton = new Button("Channel Down");
        setChannelButton = new Button("Set Channel");
        channelField = new TextField();

        mainPanel.add(powerOnButton, 0, 0);
        mainPanel.add(powerOffButton, 1, 0);
        mainPanel.add(channelUpButton, 0, 1);
        mainPanel.add(channelDownButton, 1, 1);
        mainPanel.add(setChannelButton, 0, 2);
        mainPanel.add(channelField, 1, 2);

        responseArea = new TextArea();
        responseArea.setEditable(false);

        root.setCenter(mainPanel);
        root.setBottom(responseArea);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

