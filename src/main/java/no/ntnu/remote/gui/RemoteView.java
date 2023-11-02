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
    private Button powerOnButton;
    private Button powerOffButton;
    private Button channelUpButton;
    private Button channelDownButton;
    private Button setChannelButton;
    private TextField channelField;
    private TextArea responseArea;
    private RemoteController remoteController;
    private RemoteModel remoteModel;
    private ChangeListener<String> ipTextFieldListener;
    private ChangeListener<String> portTextFieldListener;


    /**
     * Constructor of the RemoteView. Initialize the scene and connect the view to the model
     * and controller.
     *
     * @param primaryStage The primary JavaFX stage where the RemoteView will be displayed.
     */
    public RemoteView(Stage primaryStage) {
        this.remoteModel = new RemoteModel(this);
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

        dialog();

        powerOnButton.setOnAction(e -> remoteController.turnOnTV());
        powerOffButton.setOnAction(e -> remoteController.turnOffTV());
        channelUpButton.setOnAction(e -> remoteController.channelUp());
        channelDownButton.setOnAction(e -> remoteController.channelDown());
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

        ipTextFieldListener = (observableValue, s, t1) -> {
            loginButton.setDisable(t1.trim().isEmpty() || port.getText().trim().isEmpty());
        };

        portTextFieldListener = (observableValue2, s2, t2) -> {
            loginButton.setDisable(t2.trim().isEmpty() || ip.getText().trim().isEmpty());
        };

        ip.textProperty().addListener(new WeakChangeListener<>(ipTextFieldListener));

        port.textProperty().addListener(new WeakChangeListener<>(portTextFieldListener));


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> ip.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectButtonType) {
                return new Pair<>(ip.getText(), port.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(ipPort -> {
            remoteModel.newClient(ipPort.getKey(), Integer.parseInt(ipPort.getValue()));
            System.out.println("ip=" + ipPort.getKey() + ", port=" + ipPort.getValue());
        });

//        Clears the listeners to avoid memory leaks
        ipTextFieldListener = null;
        portTextFieldListener = null;
    }

}

