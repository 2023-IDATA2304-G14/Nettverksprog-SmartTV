package no.ntnu.message;

public class MessageSerializer {

  public static final String CHANNEL_COUNT_COMMAND = "count";
  public static final String CURRENT_CHANNEL_COMMAND = "current";
  public static final String TURN_ON_COMMAND = "on";
  public static final String TURN_OFF_COMMAND = "off";
  public static final String SET_CHANNEL_COMMAND_PREFIX = "set ";
  private static final String SET_CHANNEL_COMMAND_REGEX = "set\\s+(\\d+)";

  public static final String CHANNEL_COUNT_RESPONSE_PREFIX = "Count ";
  public static final String CHANNEL_COUNT_RESPONSE_REGEX = "Count\\s+(\\d+)";
  public static final String CURRENT_CHANNEL_RESPONSE_PREFIX = "Channel ";
  public static final String CURRENT_CHANNEL_RESPONSE_REGEX = "Channel\\s+(\\d+)";
  public static final String ERROR_MESSAGE_PREFIX = "Error: ";
  public static final String ERROR_MESSAGE_REGEX = "Error:\\s+(.+)";
  public static final String TV_STATE_ON = "TVOn";
  public static final String TV_STATE_OFF = "TVOff";
  private static final String SINGLE_PARAMETER_COMMAND_REGEX = "^[a-zA-Z0-9]+$";
  public static String serialize(Message message) {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    if (message instanceof ChannelCountCommand) {
      return CHANNEL_COUNT_COMMAND;
    } else if (message instanceof TurnOnCommand) {
      return TURN_ON_COMMAND;
    } else if (message instanceof TurnOffCommand) {
      return TURN_OFF_COMMAND;
    } else if (message instanceof CurrentChannelCommand) {
      return CURRENT_CHANNEL_COMMAND;
    } else if (message instanceof SetChannelCommand setChannelCommand) {
      return SET_CHANNEL_COMMAND_PREFIX + setChannelCommand.getChannel();
    } else if (message instanceof ChannelCountMessage channelCountMessage) {
      return CHANNEL_COUNT_RESPONSE_PREFIX + channelCountMessage.getChannelCount();
    } else if (message instanceof CurrentChannelMessage currentChannelMessage) {
      return CURRENT_CHANNEL_RESPONSE_PREFIX + currentChannelMessage.getChannel();
    } else if (message instanceof ErrorMessage errorMessage) {
      return ERROR_MESSAGE_PREFIX + errorMessage.getMessage();
    } else if (message instanceof TvStateMessage tvStateMessage) {
      return tvStateMessage.isOn() ? TV_STATE_ON : TV_STATE_OFF;
    } else {
      throw new IllegalArgumentException("Unknown command: " + message);
    }
  }

  public static Message deserialize(String message) {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    if (message.matches(SINGLE_PARAMETER_COMMAND_REGEX)) {
      switch (message) {
        case CHANNEL_COUNT_COMMAND:
          return new ChannelCountCommand();
        case TURN_ON_COMMAND:
          return new TurnOnCommand();
        case TURN_OFF_COMMAND:
          return new TurnOffCommand();
        case CURRENT_CHANNEL_COMMAND:
          return new CurrentChannelCommand();
        case TV_STATE_ON:
          return new TvStateMessage(true);
        case TV_STATE_OFF:
          return new TvStateMessage(false);
        default:
          throw new IllegalArgumentException("Unknown command: " + message);
      }
    } else {
      return deserializeParameterizedCommand(message);
    }
  }

  private static Message deserializeParameterizedCommand(String message) {
    if (message.matches(SET_CHANNEL_COMMAND_REGEX)) {
      return new SetChannelCommand(Integer.parseInt(message.substring(4)));
    } else if (message.matches(CURRENT_CHANNEL_RESPONSE_REGEX)) {
      return new CurrentChannelMessage(Integer.parseInt(message.substring(8)));
    } else if (message.matches(CHANNEL_COUNT_RESPONSE_REGEX)) {
      return new ChannelCountMessage(Integer.parseInt(message.substring(6)));
    } else if (message.matches(ERROR_MESSAGE_REGEX)) {
      return new ErrorMessage(message.substring(7));
    } else {
      throw new IllegalArgumentException("Unknown command: " + message);
    }
  }
}
