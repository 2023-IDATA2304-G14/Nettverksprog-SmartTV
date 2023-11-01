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
  boolean isTvOn;
  final int numberOfChannels;
  int currentChannel;
  boolean isTcpServerRunning;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
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
  /**
   * Send a response from the server to the client, over the TCP socket.
   *
   * @param response The response to send to the client, NOT including the newline
   */
  private void sendResponseToClient(String response) {
    socketWriter.println(response);
  }
}
