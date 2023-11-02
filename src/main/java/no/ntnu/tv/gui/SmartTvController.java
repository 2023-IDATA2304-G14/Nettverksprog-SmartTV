package no.ntnu.tv.gui;

public class SmartTvController {
  final SmartTvModel model;
  public SmartTvController(SmartTvModel model) {
    this.model = model;
  }

  public void restartServer() {
    try {
      model.restartServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
