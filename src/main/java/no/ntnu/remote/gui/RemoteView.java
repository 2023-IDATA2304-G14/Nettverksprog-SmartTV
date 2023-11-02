package no.ntnu.remote.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RemoteView {
    private Button powerOnButton;
    private Button powerOffButton;
    private Button channelUpButton;
    private Button channelDownButton;
    private Button setChannelButton;
    private TextField channelField;
    private TextArea responseArea;

    private RemoteController remoteController;
    private RemoteModel remoteModel;

    public RemoteView(Stage primaryStage) {
        this.remoteModel = new RemoteModel(this);
        this.remoteController = new RemoteController(remoteModel, this);
        initialize(primaryStage);
    }

    private void initialize(Stage primaryStage){
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

        powerOnButton.setOnAction(e -> remoteController.turnOnTV());
        powerOffButton.setOnAction(e -> remoteController.turnOffTV());
        channelUpButton.setOnAction(e -> remoteController.channelUp());
        channelDownButton.setOnAction(e -> remoteController.channelDown());
    }
}

