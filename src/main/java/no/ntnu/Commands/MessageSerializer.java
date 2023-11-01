package no.ntnu.Commands;

public class MessageSerializer {

  public static final String CHANNEL_COUNT_COMMAND = "count";
  public static final String TURN_ON_COMMAND = "1";
  public static final String TURN_OFF_COMMAND = "0";
  public static final String CHANNEL_UP_COMMAND = "up";
  public static final String CHANNEL_DOWN_COMMAND = "down";
  public static final String SET_CHANNEL_COMMAND_PREFIX = "set";
  private static final String SET_CHANNEL_COMMAND_REGEX = "set\\s+(\\d+)";
  public static final String GET_STATUS_COMMAND = "get";
  public static final String CHANNEL_COUNT_RESPONSE = "Count";
  public static final String CHANNEL_SET_RESPONSE = "Channel";
  public static final String OK_RESPONSE = "Ok";
  public static final String TV_STATE_ON = "TVOn";
  public static final String TV_STATE_OFF = "TVOff";
  private static final String SINGLE_PARAMETER_COMMAND_REGEX = "^[a-zA-Z]+$";
  public static String serialize(Message message) {
    if (message instanceof ChannelCountCommand) {
      return CHANNEL_COUNT_COMMAND;
    } else if (message instanceof TurnOnCommand) {
      return TURN_ON_COMMAND;
    } else if (message instanceof TurnOffCommand) {
      return TURN_OFF_COMMAND;
    } else if (message instanceof ChannelUpCommand) {
      return CHANNEL_UP_COMMAND;
    } else if (message instanceof ChannelDownCommand) {
      return CHANNEL_DOWN_COMMAND;
    } else if (message instanceof GetStatusCommand) {
      return GET_STATUS_COMMAND;
    } else if (message instanceof SetChannelCommand) {
      return SET_CHANNEL_COMMAND_PREFIX + " " + ((SetChannelCommand) message).getChannel();
    } else {
      throw new IllegalArgumentException("Unknown command");
    }
  }

  public static Message deserialize(String message) {
    if (message.matches(SINGLE_PARAMETER_COMMAND_REGEX)) {
      switch (message) {
        case CHANNEL_COUNT_COMMAND:
          return new ChannelCountCommand();
        case TURN_ON_COMMAND:
          return new TurnOnCommand();
        case TURN_OFF_COMMAND:
          return new TurnOffCommand();
        case CHANNEL_UP_COMMAND:
          return new ChannelUpCommand();
        case CHANNEL_DOWN_COMMAND:
          return new ChannelDownCommand();
        case GET_STATUS_COMMAND:
          return new GetStatusCommand();
        default:
          throw new IllegalArgumentException("Unknown command");
      }
    } else {
      return deserializeParameterizedCommand(message);
    }
  }

  private static Message deserializeParameterizedCommand(String message) {
    if (message.matches(SET_CHANNEL_COMMAND_REGEX)) {
      return new SetChannelCommand(Integer.parseInt(message.substring(4)));
    } else {
      throw new IllegalArgumentException("Unknown command");
    }
  }
}
