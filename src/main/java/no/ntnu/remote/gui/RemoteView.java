package no.ntnu.remote.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.Optional;

/**
 * The RemoteView represents the view component of the Remote MVC architecture
 * This class is responsible for showing the user the GUI, and let them execute
 * button presses.
 *
 * @author Lars Barstad & Daniel Neset
 * @version 02.11.2023
 */

public class RemoteView {
    private final RemoteController remoteController;
    private final RemoteModel remoteModel;

    /**
     * Constructor of the RemoteView. Initialize the scene and connect the view to the model
     * and controller.
     *
     * @param primaryStage The primary JavaFX stage where the RemoteView will be displayed.
     */
    public RemoteView(Stage primaryStage) {
        this.remoteModel = new RemoteModel();
        this.remoteController = new RemoteController(remoteModel, this);
        initialize(primaryStage);
    }

    /**
     *  Initialize the stage and displays the GUI components.
     *
     * @param primaryStage The primary JavaFX stage where the RemoteView will be displayed.
     */
    private void initialize(Stage primaryStage){
        primaryStage.setTitle("Remote Control");

        BorderPane root = new BorderPane();
        GridPane mainPanel = new GridPane();
        mainPanel.setPadding(new Insets(10, 10, 10, 10));
        mainPanel.setVgap(10);
        mainPanel.setHgap(10);

        Button powerOnButton = new Button("Turn On");
        Button powerOffButton = new Button("Turn Off");
        Button channelUpButton = new Button("Channel Up");
        Button channelDownButton = new Button("Channel Down");
        Button reconnectButton = new Button("Reconnect to server");

        mainPanel.add(powerOnButton, 0, 0);
        mainPanel.add(powerOffButton, 1, 0);
        mainPanel.add(channelUpButton, 0, 1);
        mainPanel.add(channelDownButton, 1, 1);
        mainPanel.add(reconnectButton, 0, 2);

        TextArea responseArea = new TextArea();
        responseArea.setEditable(false);

        VBox topPanel = new VBox();

        HBox hostBox = new HBox();
        Label hostLabel = new Label("Host: ");
        Label host = new Label();

        ChangeListener<String> hostListener = (observable, oldValue, newValue) -> {
            host.setText(newValue);
        };
        remoteModel.hostProperty().addListener(new WeakChangeListener<>(hostListener));
        host.setText(remoteModel.hostProperty().getValue());

        hostBox.getChildren().addAll(hostLabel, host);

        HBox portBox = new HBox();
        Label portLabel = new Label("Port: ");
        Label port = new Label();

        ChangeListener<Number> portListener = (observable, oldValue, newValue) -> {
            port.setText(newValue.toString());
        };
        remoteModel.portProperty().addListener(new WeakChangeListener<>(portListener));
        port.setText(remoteModel.portProperty().getValue().toString());

        portBox.getChildren().addAll(portLabel, port);

        HBox powerBox = new HBox();
        Label powerLabel = new Label("Power: ");
        Label power = new Label();

        ChangeListener<Boolean> powerListener = (observable, oldValue, newValue) -> {
            power.setText(Boolean.TRUE.equals(newValue) ? "On" : "Off");
        };
        remoteModel.isOnProperty().addListener(new WeakChangeListener<>(powerListener));
        power.setText(Boolean.TRUE.equals(remoteModel.isOnProperty().getValue()) ? "On" : "Off");

        powerBox.getChildren().addAll(powerLabel, power);

        HBox currentChannelBox = new HBox();
        Label currentChannelLabel = new Label("Current Channel: ");
        Label currentChannel = new Label();

        ChangeListener<Number> currentChannelListener = (observable, oldValue, newValue) -> {
            currentChannel.setText(newValue.toString());
        };
        remoteModel.currentChannelProperty().addListener(new WeakChangeListener<>(currentChannelListener));
        currentChannel.setText(remoteModel.currentChannelProperty().getValue().toString());

        currentChannelBox.getChildren().addAll(currentChannelLabel, currentChannel);

        HBox channelCountBox = new HBox();
        Label channelCountLabel = new Label("Channel Count: ");
        Label channelCount = new Label();

        ChangeListener<Number> channelCountListener = (observable, oldValue, newValue) -> {
            channelCount.setText(newValue.toString());
        };
        remoteModel.channelCountProperty().addListener(new WeakChangeListener<>(channelCountListener));
        channelCount.setText(remoteModel.channelCountProperty().getValue().toString());

        channelCountBox.getChildren().addAll(channelCountLabel, channelCount);

        topPanel.setSpacing(5);
        topPanel.getChildren().addAll(hostBox, portBox, powerBox, currentChannelBox, channelCountBox);

        root.setTop(topPanel);
        root.setCenter(mainPanel);
        root.setBottom(responseArea);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        dialog();

        powerOnButton.setOnAction(e -> remoteController.turnOnTV());
        powerOffButton.setOnAction(e -> remoteController.turnOffTV());
        channelUpButton.setOnAction(e -> remoteController.channelUp());
        channelDownButton.setOnAction(e -> remoteController.channelDown());
        reconnectButton.setOnAction(e -> remoteController.reconnect());
    }

    /**
     * Helper class that display a dialog where the user can input the ip and
     * the port to which tv one is going to connect to.
     */
    private void dialog(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Select TV");
        dialog.setHeaderText("Please enter the IP-address and the Port of the TV.");

        ButtonType selectButtonType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField ip = new TextField();
        ip.setPromptText("10.10.10.10");
        TextField port = new TextField();
        port.setPromptText("1238");



        grid.add(new Label("IP-address"), 0, 0);
        grid.add(ip, 1, 0);
        grid.add(new Label("Port number:"), 0, 1);
        grid.add(port, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(selectButtonType);
        loginButton.setDisable(true);

        ChangeListener<String> ipTextFieldListener = (observableValue, s, t1) -> {
            loginButton.setDisable(t1.trim().isEmpty() || port.getText().trim().isEmpty());
        };

        ChangeListener<String> portTextFieldListener = (observableValue2, s2, t2) -> {
            loginButton.setDisable(t2.trim().isEmpty() || ip.getText().trim().isEmpty());
        };

        ip.textProperty().addListener(new WeakChangeListener<>(ipTextFieldListener));

        port.textProperty().addListener(new WeakChangeListener<>(portTextFieldListener));


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(ip::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectButtonType) {
                return new Pair<>(ip.getText(), port.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(ipPort -> {
            remoteModel.newClient(ipPort.getKey().trim(), Integer.parseInt(ipPort.getValue().trim()));
            System.out.println("ip=" + ipPort.getKey().trim() + ", port=" + ipPort.getValue().trim());
        });
    }

}

