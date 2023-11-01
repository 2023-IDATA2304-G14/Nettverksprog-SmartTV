package no.ntnu.tv;

/**
 * Smart TV - TCP server.
 */
public class SmartTv {
  private boolean isTvOn;
  private final int numberOfChannels;
  private int currentChannel;
  private static final String ERR_MUST_BE_ON = "TV must be on";

  /**
   * Creates a new Smart TV with the given number of channels.
   * The TV is initially off and on channel 1.
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

  /**
   * Turns the TV on.
   */
  public void turnOn() {
    isTvOn = true;
  }

  /**
   * Turns the TV off.
   */
  public void turnOff() {
    isTvOn = false;
  }

  /**
   * Sets the channel to the given channel number.
   *
   * @param channel The channel number to set
   *                Must be between 1 and the number of channels
   * @throws IllegalArgumentException If the channel number is invalid
   * @throws IllegalStateException    If the TV is off
   */
  public void setChannel(int channel) throws IllegalArgumentException, IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException(ERR_MUST_BE_ON);
    }
    if (channel < 1 || channel > numberOfChannels) {
      throw new IllegalArgumentException("Channel must be between 1 and " + numberOfChannels);
    }
    currentChannel = channel;
  }

  /**
   * Returns the number of channels the TV has.
   *
   * @return The number of channels
   * @throws IllegalStateException If the TV is off
   */
  public int getChannelCount() {
    if (!isTvOn) {
      throw new IllegalStateException(ERR_MUST_BE_ON);
    }
    return numberOfChannels;
  }

  /**
   * Returns whether the TV is currently turned on.
   *
   * @return True if the TV is on, false otherwise
   */
  public boolean isTvOn() {
    return isTvOn;
  }
}
