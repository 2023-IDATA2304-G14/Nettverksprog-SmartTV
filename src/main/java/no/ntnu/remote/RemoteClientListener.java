package no.ntnu.remote;

import no.ntnu.tv.SmartTvSubscriber;

public interface RemoteClientListener extends SmartTvSubscriber {
  /**
   * Handles an error message.
   * Gets called when an error occurs.
   *
   * @param message the error message
   */
  void handleErrorMessage(String message);
}
