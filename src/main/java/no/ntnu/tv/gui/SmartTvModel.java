package no.ntnu.tv.gui;

import javafx.application.Platform;
import javafx.beans.property.*;
import no.ntnu.tv.SmartTv;
import no.ntnu.tv.SmartTvSubscriber;
import no.ntnu.tv.TvServer;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static javafx.application.Platform.runLater;

/**
 * The SmartTvModel class represents the Model component of the MCV
 * architecture. It is responsible for managing the data and logic
 * related to SmartTv.
 *
 * @author Anders Lund
 * @version 03.11.2023
 */
public class SmartTvModel implements SmartTvSubscriber {

  private final IntegerProperty port = new SimpleIntegerProperty(0);
  private final BooleanProperty isOn = new SimpleBooleanProperty(false);
  private final IntegerProperty channelCount = new SimpleIntegerProperty(0);
  private final IntegerProperty currentChannel = new SimpleIntegerProperty(0);
  private TvServer tvServer;
  private SmartTv smartTv;

  /**
   * Restart the server with new values.
   *
   * @param numberOfChannels The number of channels.
   * @param port The Port of the server
   * @throws IllegalArgumentException Throws an IllegalArgumentException if there is an error.
   * @throws IOException Throws an IOException if there is an error.
   */
  public void newConfig(int numberOfChannels, int port) throws IllegalArgumentException, IOException {
    removeServer();
    smartTv = new SmartTv(numberOfChannels, this);
    tvServer = new TvServer(smartTv, port);

    startServer();
  }

  /**
   * Restart the server and set default port.
   *
   * @param numberOfChannels The number of channels.
   * @throws IllegalArgumentException Throws an IllegalArgumentException if there is an error.
   * @throws IOException Throws an IOException if there is an error.
   */
  public void newConfig(int numberOfChannels) throws IllegalArgumentException, IOException {
    removeServer();
    smartTv = new SmartTv(numberOfChannels, this);
    tvServer = new TvServer(smartTv);

    startServer();
  }

  /**
   * Start the server
   */
  private void startServer() {
    CountDownLatch portAssigned = new CountDownLatch(1);
    ExecutorService serverExecutor = Executors.newSingleThreadExecutor();
    serverExecutor.execute(() -> {
      try {
        tvServer.startServer(portAssigned);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    try {
      portAssigned.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    runLater(() -> this.port.set(tvServer.getPort()));
  }

  /**
   * Restart the server
   *
   * @throws IOException Throws an IOException if there is an error.
   */
  public void restartServer() throws IOException {

    removeServer();
    newConfig(channelCount.get(), port.get());
  }

  /**
   * Remove the server
   */
  public void removeServer() {
    if (tvServer != null)
      tvServer.stopServer();
    tvServer = null;
  }

  /**
   * Handle the state of the tv.
   *
   * @param isOn true if the TV is on, false otherwise
   */
  @Override
  public void handleTvState(boolean isOn) {
    Platform.runLater(() -> this.isOn.set(isOn));
  }

  /**
   * Handle the channel count of the tv.
   *
   * @param channel the new number of channels
   */
  @Override
  public void handleChannelCount(int channel) {
    Platform.runLater(() -> this.channelCount.set(channel));
  }

  /**
   * Handle the current channel of the tv.
   *
   * @param channel the new current channel
   */
  @Override
  public void handleCurrentChannel(int channel) {
    Platform.runLater(() -> this.currentChannel.set(channel));
  }

  /**
   * Get the port property.
   *
   * @return Return the port.
   */
  public IntegerProperty getPortProperty() {
    return port;
  }

  /**
   * Get the tv state.
   *
   * @return Return a boolean about the state of the tv.
   */
  public BooleanProperty getIsOnProperty() {
    return isOn;
  }

  /**
   * Get the channel count.
   *
   * @return Return the channel count.
   */
  public IntegerProperty getChannelCountProperty() {
    return channelCount;
  }

  /**
   * Get the current channel.
   *
   * @return Return the current channel.
   */
  public IntegerProperty getCurrentChannelProperty() {
    return currentChannel;
  }
}
