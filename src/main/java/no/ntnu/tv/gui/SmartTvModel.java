package no.ntnu.tv.gui;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import no.ntnu.tv.SmartTv;
import no.ntnu.tv.SmartTvSubscriber;
import no.ntnu.tv.TvServer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.application.Platform.runLater;

public class SmartTvModel implements SmartTvSubscriber {

  private final IntegerProperty port = new SimpleIntegerProperty(0);
  private final BooleanProperty isOn = new SimpleBooleanProperty(false);
  private final IntegerProperty channelCount = new SimpleIntegerProperty(0);
  private final IntegerProperty currentChannel = new SimpleIntegerProperty(0);
  private TvServer tvServer;
  private SmartTv smartTv;
  private ExecutorService serverExecutor;

  public void newConfig(int numberOfChannels, int port) throws IllegalArgumentException, IOException {
    removeServer();
    smartTv = new SmartTv(numberOfChannels, this);
    tvServer = new TvServer(smartTv, port);

    startServer();
  }

  public void newConfig(int numberOfChannels) throws IllegalArgumentException, IOException {
    removeServer();
    smartTv = new SmartTv(numberOfChannels, this);
    tvServer = new TvServer(smartTv);

    startServer();
  }

  private void startServer() {
    CountDownLatch portAssigned = new CountDownLatch(1);
    serverExecutor = Executors.newSingleThreadExecutor();
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

  public void restartServer() throws IOException {

    removeServer();
    newConfig(channelCount.get(), port.get());
  }

  public void removeServer() {
    if (tvServer != null)
      tvServer.stopServer();
    tvServer = null;
  }

  @Override
  public void handleTvState(boolean isOn) {
    Platform.runLater(() -> this.isOn.set(isOn));
  }

  @Override
  public void handleChannelCount(int channel) {
    Platform.runLater(() -> this.channelCount.set(channel));
  }

  @Override
  public void handleCurrentChannel(int channel) {
    Platform.runLater(() -> this.currentChannel.set(channel));
  }

  public IntegerProperty getPortProperty() {
    return port;
  }

  public BooleanProperty getIsOnProperty() {
    return isOn;
  }

  public IntegerProperty getChannelCountProperty() {
    return channelCount;
  }

  public IntegerProperty getCurrentChannelProperty() {
    return currentChannel;
  }
}
