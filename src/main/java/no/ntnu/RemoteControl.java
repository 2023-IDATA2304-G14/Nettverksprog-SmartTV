package no.ntnu;

import static no.ntnu.SmartTv.PORT_NUMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Remote control for a TV - a TCP client.
 */
public class RemoteControl {
  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  public static final String CHANNEL_COUNT_COMMAND = "count";
  public static final String TURN_ON_COMMAND = "1";
  public static final String OK_RESPONSE = "ok";
  public static final String TURN_OFF_COMMAND = "2";
  public static final String CHANNEL_UP_COMMAND = "up";
  public static final String CHANNEL_DOWN_COMMAND = "down";
  public static final String SET_CHANNEL_COMAND = "set";
  public static final String GET_STATUS_COMMAND = "get";

  public static void main(String[] args) {
    RemoteControl remoteControl = new RemoteControl();
    remoteControl.run();
  }

  private void run() {
    try {
      socket = new Socket("localhost", PORT_NUMBER);
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
      socketReader = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));
      // Test turning on the TV
      sendCommandToServer(TURN_ON_COMMAND);

      // Test getting channel count
      sendCommandToServer(CHANNEL_COUNT_COMMAND);

      // Test increasing the channel
      sendCommandToServer(CHANNEL_UP_COMMAND);

      // Test decreasing the channel
      sendCommandToServer(CHANNEL_DOWN_COMMAND);

      // Test setting a specific channel (e.g., channel 5)
      sendCommandToServer("s5");

      // Test getting TV status
      sendCommandToServer(GET_STATUS_COMMAND);

      // Test turning off the TV
      sendCommandToServer(TURN_OFF_COMMAND);
      sendCommandToServer("c");
      sendCommandToServer("1");
      sendCommandToServer("c");
      sendCommandToServer("s13");

    } catch (IOException e) {
      System.err.println("Could not establish connection to the server: " + e.getMessage());
    }
  }

  private void sendCommandToServer(String command) throws IOException {
    socketWriter.println(command);
    String serverResponse = socketReader.readLine();
    System.out.println("Server's response: " + serverResponse);
  }
}
