package no.ntnu.tv.gui;

import javafx.beans.Observable;
import javafx.beans.property.*;
import no.ntnu.tv.SmartTv;
import no.ntnu.tv.SmartTvSubscriber;
import no.ntnu.tv.TvServer;

import java.io.IOException;

public class SmartTvModel implements SmartTvSubscriber {

  private final IntegerProperty port = new SimpleIntegerProperty(0);
  private final BooleanProperty isOn = new SimpleBooleanProperty(false);
  private final IntegerProperty channelCount = new SimpleIntegerProperty(0);
  private final IntegerProperty currentChannel = new SimpleIntegerProperty(0);
  private TvServer tvServer;
  private SmartTv smartTv;

  public void newConfig(int numberOfChannels, int port) throws IllegalArgumentException, IOException {
    removeServer();
    smartTv = new SmartTv(numberOfChannels, this);
    tvServer = new TvServer(smartTv, port);
  }

  public void newConfig(int numberOfChannels) throws IllegalArgumentException, IOException {
    removeServer();
    smartTv = new SmartTv(numberOfChannels, this);
    tvServer = new TvServer(smartTv);
  }

  public void removeServer() {
    if (tvServer != null)
      tvServer.stopServer();
    tvServer = null;
  }

  @Override
  public void handleTvState(boolean isOn) {
    this.isOn.set(isOn);
  }

  @Override
  public void handleChannelCount(int channel) {
    this.channelCount.set(channel);
  }

  @Override
  public void handleCurrentChannel(int channel) {
    this.currentChannel.set(channel);
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
