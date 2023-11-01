package no.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Smart TV - TCP server.
 */
public class SmartTv {
  public static final int PORT_NUMBER = 10025;
  boolean isTvOn;
  final int numberOfChannels;
  int currentChannel;
  boolean isTcpServerRunning;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

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

  public static void main(String[] args) {
    SmartTv tv = new SmartTv(13);
    tv.startServer();
  }


  /**
   * Start TCP server for this TV.
   */
  private void startServer() {
    ServerSocket listeningSocket = openListeningSocket();
    System.out.println("Server listening on port " + PORT_NUMBER);
    if (listeningSocket != null) {
      isTcpServerRunning = true;
      while (isTcpServerRunning) {
        Socket clientSocket = acceptNextClientConnection(listeningSocket);
        if (clientSocket != null) {
          System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
          handleClient(clientSocket);
        }
      }
    }
  }


  private ServerSocket openListeningSocket() {
    ServerSocket listeningSocket = null;
    try {
      listeningSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException e) {
      System.err.println("Could not open server socket: " + e.getMessage());
    }
    return listeningSocket;
  }

  private Socket acceptNextClientConnection(ServerSocket listeningSocket) {
    Socket clientSocket = null;
    try {
      clientSocket = listeningSocket.accept();
      socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);

    } catch (IOException e) {
      System.err.println("Could not accept client connection: " + e.getMessage());
    }
    return clientSocket;
  }


  private void handleClient(Socket clientSocket) {
    String response;
    do {
      String clientRequest = readClientRequest();
      System.out.println("Received from client: " + clientRequest);
      response = handleClientRequest(clientRequest);
      if (response != null) {
        sendResponseToClient(response);
      }
    } while (response != null);
  }

  /**
   * Read one message from the TCP socket - from the client.
   *
   * @return The received client message, or null on error
   */
  private String readClientRequest() {
    String clientRequest = null;
    try {
      clientRequest = socketReader.readLine();
    } catch (IOException e) {
      System.err.println("Could not receive client request: " + e.getMessage());
    }
    return clientRequest;
  }

  private String handleClientRequest(String clientRequest) {
    String response = null;

    //If TV is not ON and user does anything other then TURN ON, send msg.
    if (clientRequest != null) {
      if (!isTvOn && !clientRequest.equals(TURN_ON_COMMAND)) {
        return "TV is OFF. Please turn it ON first.";
      }

      switch (clientRequest) {
        case CHANNEL_COUNT_COMMAND:
          response = handleChannelCountCommand();
          break;
        case TURN_ON_COMMAND:
          response = handleTurnOnCommand();
          break;
        case TURN_OFF_COMMAND:
          response = handleTurnOffCommand();
          break;
        case CHANNEL_UP_COMMAND:
          response = handleChannelUpCommand();
          break;
        case CHANNEL_DOWN_COMMAND:
          response = handleChannelDownCommand();
          break;
        case GET_STATUS_COMMAND:
          response = handleGetStatusCommand();
          break;
        default:
          if (clientRequest.startsWith(SET_CHANNEL_PREFIX)) {
            response = handleSetChannelCommand(clientRequest);
          } else {
            response = "eUnknown command";
          }
          break;
      }
    }

    return response;
  }

  private String handleTurnOnCommand() {
    if (isTvOn) {
      return "The TV is already ON";
    }
    isTvOn = true;
    return OK_RESPONSE;
  }
  private String handleTurnOffCommand() {
    if (isTvOn) {
      return "The TV is already OFF";
    }
    isTvOn = false;
    return OK_RESPONSE;
  }

  private String handleChannelUpCommand() {
    if (isTvOn) {
      if (currentChannel < numberOfChannels) {
        currentChannel++;
      }
      return "Channel set to " + currentChannel;
    } else {
      return "You must turn on the TV first";
    }
  }

  private String handleChannelDownCommand() {
    if (isTvOn) {
      if (currentChannel > 1) {
        currentChannel--;
      }
      return "Channel set to " + currentChannel;
    } else {
      return "You must turn on the TV first";
    }
  }

  private String handleSetChannelCommand(String command) {
    if (isTvOn) {
      try {
        Pattern regexPattern = Pattern.compile(SET_CHANNEL_REGEX);
        Matcher regexMatcher = regexPattern.matcher(command);

        if (regexMatcher.find()) {
          int channel = Integer.parseInt(regexMatcher.group(1));
          if (channel >= 1 && channel <= numberOfChannels) {
            currentChannel = channel;
            return "Channel set to " + currentChannel;
          } else {
            return "Channel must be between 1 and " + numberOfChannels;
          }
        } else {
          return "Invalid channel number";
        }
      } catch (NumberFormatException e) {
        return "Invalid set command";
      }
    } else {
      return "You must turn on the TV first";
    }
  }
  private String handleChannelCountCommand() {
    String response;
    if (isTvOn) {
      response = "c" + numberOfChannels;
    } else {
      response = "eMust turn the TV on first";
    }
    return response;
  }

  public String handleGetStatusCommand() {
    return isTvOn ? "TV is ON" : "TV is OFF";
  }
  /**
   * Send a response from the server to the client, over the TCP socket.
   *
   * @param response The response to send to the client, NOT including the newline
   */
  private void sendResponseToClient(String response) {
    socketWriter.println(response);
  }
}
