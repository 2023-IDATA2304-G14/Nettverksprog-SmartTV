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

/**
 * The RemoteModel class represents the Model component of the MCV
 * architecture. It is responsible for managing the data and logic
 * related to sending commands.
 *
 * @author Anders Lund
 * @version 02.11.2023
 */
public class RemoteModel implements RemoteClientListener {
  private final RemoteView view;
  private final BooleanProperty isOn = new SimpleBooleanProperty();
  private final IntegerProperty channelCount = new SimpleIntegerProperty();
  private final IntegerProperty currentChannel = new SimpleIntegerProperty();
  private RemoteClient client;

  /**
   * The constructor which connect the model to the view.
   *
   * @param view The RemoteView of the MVC architecture.
   */
    public RemoteModel(RemoteView view) {
        this.view = view;
    }

  /**
   * Disconnect the client.
   */
  public void removeClient() {
      if (client != null)
        client.stopClient();
    }

  /**
   * Connect a new client.
   *
   * @param host The Ip of the host
   * @param port The port of the host
   * @throws RuntimeException Throws a RuntimeException if they are an exception
   */
    public void newClient(String host, int port) throws RuntimeException{
      removeClient();
      client = new RemoteClient(host, port, this);
    }

  /**
   * Handle the tv state.
   *
   * @param isOn true if the TV is on, false otherwise
   */
  @Override
    public void handleTvState(boolean isOn) {
        this.isOn.set(isOn);
    }

  /**
   * Handle the channel count.
   *
   * @param channelCount the new number of channels
   */
  @Override
    public void handleChannelCount(int channelCount) {
        this.channelCount.set(channelCount);
    }


  /**
   * Handle the current channel.
   *
   * @param channel the new current channel
   */
  @Override
    public void handleCurrentChannel(int channel) {
        this.currentChannel.set(channel);
    }

  /**
   * Handle error messages.
   *
   * @param message the error message
   */
  @Override
    public void handleErrorMessage(String message) {
//      TODO: Implement this
//        view.showError(message);
    }

  /**
   * Turn the tv on.
   */
  public void turnOnTV() {
      Command turnOnCommand = new TurnOnCommand();
      client.sendCommand(turnOnCommand);
//      TODO: Add errorhandling
    }

  /**
   * Turn the TV off.
   */
  public void turnOffTV() {
      Command turnOffCommand = new TurnOffCommand();
      client.sendCommand(turnOffCommand);
//      TODO: Add errorhandling
    }

  /**
   * Change the channel to one up.
   */
  public void channelUp() {
      int channel = currentChannel.get() + 1;
      if (channel > channelCount.get()) {
        channel = 1;
      }
      Command setChannelCommand = new SetChannelCommand(channel);
      client.sendCommand(setChannelCommand);
//      TODO: Add errorhandling
    }

  /**
   * Change the channel to one down.
   */
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
