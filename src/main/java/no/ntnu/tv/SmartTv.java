package no.ntnu.tv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Smart TV - TCP server.
 */
public class SmartTv {
  public static final int PORT_NUMBER = 10025;
  private boolean isTvOn;
  private final int numberOfChannels;
  private int currentChannel;
  private static final String ERR_MUST_BE_ON = "TV must be on";

  /**
   * Create a new Smart TV.
   *
   * @param numberOfChannels The total number of channels the TV has
   */
  public SmartTv(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("Number of channels must be a positive number");
    }

    this.numberOfChannels = numberOfChannels;
    isTvOn = false;
    currentChannel = 1;
  }

  public void turnOn() {
    isTvOn = true;
  }
  public void turnOff() {
    isTvOn = false;
  }

  public void setChannel(int channel) throws IllegalArgumentException, IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException(ERR_MUST_BE_ON);
    }
    if (channel < 1 || channel > numberOfChannels) {
      throw new IllegalArgumentException("Channel must be between 1 and " + numberOfChannels);
    }
    currentChannel = channel;
  }
  public int getChannelCount() {
    if (!isTvOn) {
      throw new IllegalStateException(ERR_MUST_BE_ON);
    }
    return numberOfChannels;
  }

  public boolean isTvOn() {
    return isTvOn;
  }
}
