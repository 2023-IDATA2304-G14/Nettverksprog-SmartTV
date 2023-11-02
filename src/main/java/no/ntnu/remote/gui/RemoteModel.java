package no.ntnu.remote.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import no.ntnu.message.Command;
import no.ntnu.message.SetChannelCommand;
import no.ntnu.message.TurnOffCommand;
import no.ntnu.message.TurnOnCommand;
import no.ntnu.remote.RemoteClient;
import no.ntnu.remote.RemoteClientListener;

public class RemoteModel implements RemoteClientListener {
  private final RemoteView view;
  private final BooleanProperty isOn = new SimpleBooleanProperty();
  private final IntegerProperty channelCount = new SimpleIntegerProperty();
  private final IntegerProperty currentChannel = new SimpleIntegerProperty();
  private RemoteClient client;

    public RemoteModel(RemoteView view) {
        this.view = view;
    }

    public void removeClient() {
      if (client != null)
        client.stopClient();
    }

    public void newClient(String host, int port) throws RuntimeException{
      removeClient();
      client = new RemoteClient(host, port, this);
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
//      TODO: Implement this
//        view.showError(message);
    }

    public void turnOnTV() {
      Command turnOnCommand = new TurnOnCommand();
      client.sendCommand(turnOnCommand);
//      TODO: Add errorhandling
    }

    public void turnOffTV() {
      Command turnOffCommand = new TurnOffCommand();
      client.sendCommand(turnOffCommand);
//      TODO: Add errorhandling
    }

    public void channelUp() {
      int channel = currentChannel.get() + 1;
      if (channel > channelCount.get()) {
        channel = 1;
      }
      Command setChannelCommand = new SetChannelCommand(channel);
      client.sendCommand(setChannelCommand);
//      TODO: Add errorhandling
    }

    public void channelDown() {
      int channel = currentChannel.get() - 1;
      if (channel < 1) {
        channel = channelCount.get();
      }
      Command setChannelCommand = new SetChannelCommand(channel);
      client.sendCommand(setChannelCommand);
//      TODO: Add errorhandling
    }
}
