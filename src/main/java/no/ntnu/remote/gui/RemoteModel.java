package no.ntnu.remote.gui;

import no.ntnu.remote.RemoteClientListener;

public class RemoteModel implements RemoteClientListener {
  private RemoteView view;
  private Boolean isOn = false;
  private int channelCount = 0;
  private int currentChannel = 0;

    public RemoteModel(RemoteView view) {
        this.view = view;
    }

    @Override
    public void handleTvState(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void handleChannelCount(int channelCount) {
        this.channelCount = channelCount;
    }

    @Override
    public void handleCurrentChannel(int channel) {
        this.currentChannel = channel;
    }

    @Override
    public void handleErrorMessage(String message) {
        view.showError(message);
    }
}
