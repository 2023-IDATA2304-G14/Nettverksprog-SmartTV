package no.ntnu.remote;

import no.ntnu.tv.SmartTvSubscriber;

/**
 * Remote client listener that handle error messages.
 *
 * @author Anders Lund
 * @version 02.11.2023
 */
public interface RemoteClientListener extends SmartTvSubscriber {
  /**
   * Handles an error message.
   * Gets called when an error occurs.
   *
   * @param message the error message
   */
  void handleErrorMessage(String message);
}
