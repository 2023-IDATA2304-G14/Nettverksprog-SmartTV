package no.ntnu.tv.gui;

import javafx.stage.Stage;
import no.ntnu.tv.TvServer;

public class SmartTvView {
  final SmartTvModel model;
  final SmartTvController controller;
  public SmartTvView(Stage stage) {
    model = new SmartTvModel();
    controller = new SmartTvController(model);
    initialize(stage);
  }

  private void initialize(Stage stage) {

  }

  public void showConfig() {

  }

  public void showTv() {

  }
}
