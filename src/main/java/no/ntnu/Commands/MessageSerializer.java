package no.ntnu.Commands;

public class MessageSerializer {

  public static final String CHANNEL_COUNT_COMMAND = "count";
  public static final String TURN_ON_COMMAND = "1";
  public static final String TURN_OFF_COMMAND = "0";
  public static final String CHANNEL_UP_COMMAND = "up";
  public static final String CHANNEL_DOWN_COMMAND = "down";
  public static final String SET_CHANNEL_COMMAND_PREFIX = "set";
  public static final String SET_CHANNEL_COMMAND_REGEX = "set\\s+(\\d+)";
  public static final String GET_STATUS_COMMAND = "get";
  public static final String CHANNEL_COUNT_RESPONSE = "Count";
  public static final String CHANNEL_SET_RESPONSE = "Channel";
  public static final String OK_RESPONSE = "Ok";
  public static final String TV_STATE_ON = "TVOn";
  public static final String TV_STATE_OFF = "TVOff";
  public static String serialize(Message message) {
    return null;
  }

  public static Message deserialize(String message) {
    return null;
  }
}
