package no.ntnu.tv.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import no.ntnu.tv.TvServer;

import java.util.Optional;

public class SmartTvView {
  private final SmartTvModel model;
  private final SmartTvController controller;
  private ChangeListener<Number> portListener;
  private ChangeListener<Number> currentChannelListener;
  private ChangeListener<Number> channelCountListener;
  private ChangeListener<Boolean> powerListener;


  public SmartTvView(Stage stage) {
    model = new SmartTvModel();
    controller = new SmartTvController(model);
    initialize(stage);
  }

  private void initialize(Stage stage) {
    stage.setTitle("Smart TV");

    BorderPane root = new BorderPane();
    VBox vBox = new VBox();
    vBox.setSpacing(10);
    root.setCenter(vBox);

    HBox portBox = new HBox();
    portBox.setSpacing(5);
    Label portLabel = new Label("Port: ");
    Label port = new Label();
    portBox.getChildren().addAll(portLabel, port);

    portListener = (observable, oldValue, newValue) -> {
      port.setText(newValue.toString());
    };
    model.getPortProperty().addListener(new WeakChangeListener<>(portListener));
    port.setText(model.getPortProperty().getValue().toString());

    HBox currentChannelBox = new HBox();
    currentChannelBox.setSpacing(5);
    Label currentChannelLabel = new Label("Current channel: ");
    Label currentChannel = new Label();
    currentChannelBox.getChildren().addAll(currentChannelLabel, currentChannel);

    currentChannelListener = (observable, oldValue, newValue) -> {
      currentChannel.setText(newValue.toString());
    };
    model.getCurrentChannelProperty().addListener(new WeakChangeListener<>(currentChannelListener));
    currentChannel.setText(model.getCurrentChannelProperty().getValue().toString());

    HBox channelCountBox = new HBox();
    channelCountBox.setSpacing(5);
    Label channelCountLabel = new Label("Channel count: ");
    Label channelCount = new Label();
    channelCountBox.getChildren().addAll(channelCountLabel, channelCount);

    channelCountListener = (observable, oldValue, newValue) -> {
      channelCount.setText(newValue.toString());
    };
    model.getChannelCountProperty().addListener(new WeakChangeListener<>(channelCountListener));
    channelCount.setText(model.getChannelCountProperty().getValue().toString());

    HBox powerBox = new HBox();
    powerBox.setSpacing(5);
    Label powerLabel = new Label("Power: ");
    Label power = new Label();
    powerBox.getChildren().addAll(powerLabel, power);

    powerListener = (observable, oldValue, newValue) -> {
      power.setText(Boolean.TRUE.equals(newValue) ? "On" : "Off");
    };
    model.getIsOnProperty().addListener(new WeakChangeListener<>(powerListener));
    power.setText(Boolean.TRUE.equals(model.getIsOnProperty().getValue()) ? "On" : "Off");

    Button restartServerButton = new Button("Restart server");
    restartServerButton.setOnAction(event -> {
      controller.restartServer();
    });

    Button configureButton = new Button("Configure");
    configureButton.setOnAction(event -> {
      showConfigDialog();
    });

    vBox.getChildren().addAll(portBox, currentChannelBox, channelCountBox, powerBox, restartServerButton, configureButton);

    Scene scene = new Scene(root, 400, 300);
    stage.setScene(scene);
    stage.show();

    showConfigDialog();
  }

  public void showConfigDialog() {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Configure TV");
    dialog.setHeaderText("Please enter the Port of the TV and wanted channelCount");

    ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20,150,10,10));

    TextField portField = new TextField();
    portField.setPromptText("1-65535");
    TextField channelCountField = new TextField();
    channelCountField.setPromptText("1-" + Integer.MAX_VALUE);



    grid.add(new Label("Port number (\"0\" for auto, \"\" for default):"), 0, 0);
    grid.add(portField, 1, 0);
    grid.add(new Label("Channel count:"), 0, 1);
    grid.add(channelCountField, 1, 1);

    Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
    saveButton.setDisable(true);

    ChangeListener<String> channelCountTextFieldListener = (observableValue, s, t1) -> {
      saveButton.setDisable(t1.trim().isEmpty());
    };

//    portField.textProperty().addListener(new WeakChangeListener<>(portTextFieldListener));

    channelCountField.textProperty().addListener(new WeakChangeListener<>(channelCountTextFieldListener));

    dialog.getDialogPane().setContent(grid);

    Platform.runLater(portField::requestFocus);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == saveButtonType) {
        return new Pair<>(portField.getText(), channelCountField.getText());
      }
      return null;
    });

    Optional<Pair<String, String>> result = dialog.showAndWait();

    result.ifPresent(resultPair -> {
      try {
        boolean isDefaultPort = resultPair.getKey().trim().isEmpty();
        int channelCount = Integer.parseInt(resultPair.getValue().trim());

        System.out.println("Channel count: " + channelCount);

        if (isDefaultPort) {
          model.newConfig(channelCount);
        } else {
          int port = Integer.parseInt(resultPair.getKey().trim());
          System.out.println("Port: " + port);
          model.newConfig(channelCount, port);
        }
      } catch (Exception e) {
//        TODO: Show error dialog
        e.printStackTrace();
      }
    });
  }

}
