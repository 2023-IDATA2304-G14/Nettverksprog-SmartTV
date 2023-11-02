package no.ntnu.tv;

import java.util.ArrayList;
import java.util.List;

/**
 * Smart TV - TCP server.
 */
public class SmartTv {
  private boolean isTvOn;
  private int channelCount;
  private int currentChannel;
  private static final String ERR_MUST_BE_ON = "TV must be on";
  private final List<SmartTvSubscriber> subscribers = new ArrayList<>();

  /**
   * Creates a new Smart TV with the given number of channels.
   * The TV is initially off and on channel 1.
   *
   * @param channelCount The total number of channels the TV has
   */
  public SmartTv(int channelCount) {
    isTvOn = false;
    setChannelCount(channelCount);
    currentChannel = 1;
  }

  public SmartTv(int channelCount, SmartTvSubscriber initialSubscriber) {
    this(channelCount);
    addSubscriber(initialSubscriber);
  }

  public void addSubscriber(SmartTvSubscriber subscriber) {
    subscribers.add(subscriber);
    subscriber.handleTvState(isTvOn);
    subscriber.handleChannelCount(channelCount);
    subscriber.handleCurrentChannel(currentChannel);
  }

  /**
   * Turns the TV on.
   */
  public void turnOn() {
    isTvOn = true;
    notifySubscribersOfTvState();
  }

  /**
   * Turns the TV off.
   */
  public void turnOff() {
    isTvOn = false;
    notifySubscribersOfTvState();
  }

  public void setChannelCount(int channelCount) throws IllegalArgumentException{
    if (channelCount < 1) {
      throw new IllegalArgumentException("Number of channels must be a positive number");
    }
    this.channelCount = channelCount;
    notifySubscribersOfChannelCount();
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
    if (channel < 1 || channel > channelCount) {
      throw new IllegalArgumentException("Channel must be between 1 and " + channelCount);
    }
    currentChannel = channel;
    notifySubscribersOfCurrentChannel();
  }

  /**
   * Returns the number of channels the TV has.
   *
   * @return The number of channels
   * @throws IllegalStateException If the TV is off
   */
  public int getChannelCount() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException(ERR_MUST_BE_ON);
    }
    return channelCount;
  }

  /**
   * Returns the current channel number.
   * The channel number is between 1 and the number of channels.
   *
   * @return The current channel number
   * @throws IllegalStateException If the TV is off
   */
  public int getCurrentChannel() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException(ERR_MUST_BE_ON);
    }
    return currentChannel;
  }

  /**
   * Returns whether the TV is currently turned on.
   *
   * @return True if the TV is on, false otherwise
   */
  public boolean isTvOn() {
    return isTvOn;
  }

  private void notifySubscribersOfTvState() {
    for (SmartTvSubscriber subscriber : subscribers) {
      subscriber.handleTvState(isTvOn);
    }
  }

  private void notifySubscribersOfChannelCount() {
    for (SmartTvSubscriber subscriber : subscribers) {
      subscriber.handleChannelCount(channelCount);
    }
  }

  private void notifySubscribersOfCurrentChannel() {
    for (SmartTvSubscriber subscriber : subscribers) {
      subscriber.handleCurrentChannel(currentChannel);
    }
  }
}
