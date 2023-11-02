package no.ntnu.tv.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import no.ntnu.tv.SmartTvSubscriber;

public class SmartTvModel implements SmartTvSubscriber {

  private final BooleanProperty isOn = new SimpleBooleanProperty(false);
  private final IntegerProperty channelCount = new SimpleIntegerProperty(0);
  private final IntegerProperty currentChannel = new SimpleIntegerProperty(0);


  public SmartTvModel() {
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
