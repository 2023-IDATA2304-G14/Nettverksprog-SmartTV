package no.ntnu.remote;

import no.ntnu.message.*;
import no.ntnu.tv.TvServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RemoteClient {

  private static final String host = "localhost";
  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  /**
   * Starts the client and connects to the server.
   * Prints an error message if the connection fails.
   *
   * @return true if the client successfully connected to the server, false otherwise.
   */
  public boolean startClient() {
    try {
      socket = new Socket(host, TvServer.PORT_NUMBER);
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      return true;
    } catch (Exception e) {
      System.out.println("Error connecting to server: " + e.getMessage());
    }
    return false;
  }

  /**
   * Starts a listening thread that listens for responses from the server.
   *
   * @param listener the listener that will be notified when a response is received.
   * #see RemoteClientListener
   */
    public void startListeningThread(RemoteClientListener listener) {
        new Thread(() -> {
          Message message = null;
            do {
                try {
                  if (socketReader != null && socketReader.ready()) {
                    String serializedMessage = socketReader.readLine();
                    message = MessageSerializer.deserialize(serializedMessage);
                    handleMessage(message, listener);
                  } else {
                    message = null;
                  }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                }
            } while (message != null);
        }).start();
    }

  /**
   * Handles a message received from the server.
   *
   * @param message the message received from the server.
   * @param listener the listener that will be notified when a response is received.
   */
    private void handleMessage(Message message, RemoteClientListener listener) {
        if (message instanceof ChannelCountMessage channelCountMessage) {
          listener.handleChannelCount(channelCountMessage.getChannelCount());
        } else if (message instanceof TvStateMessage tvStateMessage) {
          listener.handleTvState(tvStateMessage.isOn());
        } else if (message instanceof CurrentChannelMessage currentChannelMessage) {
          listener.handleCurrentChannel(currentChannelMessage.getChannel());
        } else if (message instanceof ErrorMessage errorMessage) {
          listener.handleErrorMessage(errorMessage.getMessage());
        }
    }

  /**
   * Sends a command to the server.
   *
   * @param command the command to send to the server.
   * @return true if the command was successfully sent, false otherwise.
   */
    public boolean sendCommand(Command command) {
        if (socketWriter != null && socketReader != null) {
          try {
            String serializedCommand = MessageSerializer.serialize(command);
            socketWriter.println(serializedCommand);
            return true;
          } catch (Exception e) {
            System.out.println("Error sending command to server: " + e.getMessage());
          }
        }
        return false;
    }

  /**
   * Stops the client by closing the socket and resetting the socket reader and writer.
   */
    public void stopClient() {
        try {
            socket.close();
            socket = null;
            socketReader = null;
            socketWriter = null;
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }
    }
}
