package no.ntnu.remote.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.w3c.dom.Text;

import java.io.InputStreamReader;
import java.util.Optional;

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

        alter();

        powerOnButton.setOnAction(e -> remoteController.turnOnTV());
        powerOffButton.setOnAction(e -> remoteController.turnOffTV());
        channelUpButton.setOnAction(e -> remoteController.channelUp());
        channelDownButton.setOnAction(e -> remoteController.channelDown());
    }

    private void alter(){
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

        ip.textProperty().addListener(((observableValue, s, t1) -> {
            if(!t1.trim().isEmpty()){
                port.textProperty().addListener(((observableValue2, s2, t2) -> {
                    loginButton.setDisable(t2.trim().isEmpty());
                }));
            }
        }));


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

    }

}

