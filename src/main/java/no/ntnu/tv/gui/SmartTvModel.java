package no.ntnu.tv.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import no.ntnu.tv.SmartTv;
import no.ntnu.tv.SmartTvSubscriber;
import no.ntnu.tv.TvServer;

public class SmartTvModel implements SmartTvSubscriber {

  private final BooleanProperty isOn = new SimpleBooleanProperty(false);
  private final IntegerProperty channelCount = new SimpleIntegerProperty(0);
  private final IntegerProperty currentChannel = new SimpleIntegerProperty(0);
  private TvServer tvServer;
  private SmartTv smartTv;



  public SmartTvModel() {

  }

  public void newTvServer(String host, int port) throws RuntimeException {
    if (tvServer != null)
      tvServer.stopServer();
    tvServer = new TvServer(smartTv, port);
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
}
