package no.ntnu.remote.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import no.ntnu.remote.RemoteClientListener;

public class RemoteModel implements RemoteClientListener {
  private RemoteView view;
  private final BooleanProperty isOn = new SimpleBooleanProperty();
  private final IntegerProperty channelCount = new SimpleIntegerProperty();
  private final IntegerProperty currentChannel = new SimpleIntegerProperty();

    public RemoteModel(RemoteView view) {
        this.view = view;
    }

    @Override
    public void handleTvState(boolean isOn) {
        this.isOn.set(isOn);
    }

    @Override
    public void handleChannelCount(int channelCount) {
        this.channelCount.set(channelCount);
    }

    @Override
    public void handleCurrentChannel(int channel) {
        this.currentChannel.set(channel);
    }

    @Override
    public void handleErrorMessage(String message) {
        view.showError(message);
    }
}
