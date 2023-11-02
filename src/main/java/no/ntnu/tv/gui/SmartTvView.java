package no.ntnu.tv.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.tv.TvServer;

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

    vBox.getChildren().addAll(portBox, currentChannelBox, channelCountBox, powerBox);

    Scene scene = new Scene(root, 400, 300);
    stage.setScene(scene);
    stage.show();
  }

  public void showConfig() {

  }

  public void showTv() {

  }
}
