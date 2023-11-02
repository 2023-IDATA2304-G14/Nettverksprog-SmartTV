package no.ntnu.tv;

import no.ntnu.message.BroadcastMessage;
import no.ntnu.message.Command;
import no.ntnu.message.Message;
import no.ntnu.message.MessageSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
  private final Socket clientSocket;
  private final TvServer tvServer;
  private final BufferedReader socketReader;
  private final PrintWriter socketWriter;

  public ClientHandler(Socket clientSocket, TvServer tvServer) throws IOException {
    this.clientSocket = clientSocket;
    this.tvServer = tvServer;
    this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
  }

  @Override
  public void run() {
    Message response;
    do {
      Command clientCommand = readClientRequest();
      if (clientCommand != null) {
        System.out.println("Received from client: " + clientCommand);
        response = clientCommand.execute(tvServer.getSmartTv());
        if (response != null) {
          if (response instanceof BroadcastMessage) {
            tvServer.broadcastMessage(response);
          } else {
            sendResponseToClient(response);
          }
          System.out.println("Sent to client: " + response);
        }
      } else {
        response = null;
      }
    } while (response != null);
    System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
    tvServer.removeClient(this);
  }

  private Command readClientRequest() {
    Message clientCommand = null;
    try {
      String rawClientRequest = socketReader.readLine();
      clientCommand = MessageSerializer.deserialize(rawClientRequest);
      if (!(clientCommand instanceof Command)) {
        System.err.println("Received invalid request from client: " + clientCommand);
        clientCommand = null;
      }
    } catch (IOException e) {
      System.err.println("Could not read from client socket: " + e.getMessage());
    }
    return (Command) clientCommand;
  }

  public void sendResponseToClient(Message response) {
    String serializedResponse = MessageSerializer.serialize(response);
    socketWriter.println(serializedResponse);
  }

  public void close() {
    try {
      socketReader.close();
      socketWriter.close();
      clientSocket.close();
    } catch (IOException e) {
      System.err.println("Could not close client socket: " + e.getMessage());
    }
  }
}
