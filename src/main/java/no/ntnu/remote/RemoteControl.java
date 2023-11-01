package no.ntnu.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static no.ntnu.tv.TvServer.PORT_NUMBER;

/**
 * Remote control for a TV - a TCP client.
 */
public class RemoteControl {

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

      System.out.println("Testing turning on the TV...");
      sendCommandToServer(TURN_ON_COMMAND);

      System.out.println("Testing getting channel count...");
      sendCommandToServer(CHANNEL_COUNT_COMMAND);

      System.out.println("Testing increasing the channel...");
      sendCommandToServer(CHANNEL_UP_COMMAND);

      System.out.println("Testing decreasing the channel...");
      sendCommandToServer(CHANNEL_DOWN_COMMAND);

      System.out.println("Testing setting a specific channel (channel 5)...");
      sendCommandToServer(SET_CHANNEL_COMMAND + "5");

      System.out.println("Testing getting TV status...");
      sendCommandToServer(GET_STATUS_COMMAND);

      System.out.println("Testing turning off the TV...");
      sendCommandToServer(TURN_OFF_COMMAND);

      System.out.println("Testing setting a specific channel (channel 13)...");
      sendCommandToServer("set 13");

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
