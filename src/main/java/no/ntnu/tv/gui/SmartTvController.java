package no.ntnu.tv.gui;

/**
 * The SmartTvController is the controller component of the SmartTv MVC architecture.
 * It is responsible for taking user input and change the model or view.
 *
 * @author Anders Lund
 * @version 02.11.2023
 */
public class SmartTvController {
  final SmartTvModel model;

  /**
   * Construct a new SmartTVController with the provided SmartTvModel.
   *
   * @param model The SmartTvModel instance associated with the controller.
   */
  public SmartTvController(SmartTvModel model) {
    this.model = model;
  }

  /**
   * Restart the server
   */
  public void restartServer() {
    try {
      model.restartServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
