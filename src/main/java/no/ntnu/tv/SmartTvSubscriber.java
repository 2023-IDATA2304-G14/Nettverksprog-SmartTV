package no.ntnu.tv;

public interface SmartTvSubscriber {
  /**
   * Handles the change in TV state.
   * Gets called when the TV is turned on or off.
   *
   * @param isTvOn true if the TV is on, false otherwise
   */
  void handleTvState(boolean isTvOn);

  /**
   * Handles the change in channel count.
   * Gets called when the number of channels changes.
   *
   * @param channelCount the new number of channels
   */
  void handleChannelCount(int channelCount);

  /**
   * Handles the change in current channel.
   * Gets called when the current channel changes.
   *
   * @param channel the new current channel
   */
  void handleCurrentChannel(int channel);
}
