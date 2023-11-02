package no.ntnu.tv;

import no.ntnu.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TvServer {
  public static final String DEFAULT_HOSTNAME = "localhost";
  public static final int DEFAULT_PORT = 12345;
  private final SmartTv smartTv;
  private int port;
  boolean isServerRunning;
  private final List<ClientHandler> connectedClients = new ArrayList<>();

  public TvServer(SmartTv smartTv) {
    this(smartTv, DEFAULT_PORT);
  }

  public TvServer(SmartTv smartTv, int port) {
    if (smartTv == null) {
      throw new IllegalArgumentException("SmartTv cannot be null");
    }
    if (port < 0 || port > 65535) {
      throw new IllegalArgumentException("Invalid port number: " + port);
    }

    this.smartTv = smartTv;
    this.port = port;
    startServer();
  }

  private void startServer() {
    ServerSocket listeningSocket = openListeningSocket();
    System.out.println("Server listening on port " + DEFAULT_PORT);
    if (listeningSocket != null) {
      isServerRunning = true;
      while (isServerRunning) {
        ClientHandler clientHandler = acceptNextClientConnection(listeningSocket);
        if (clientHandler != null) {
          connectedClients.add(clientHandler);
          clientHandler.start();
        }
      }
    }
  }

  public void broadcastMessage(Message response) {
    for (ClientHandler client : connectedClients) {
      client.sendResponseToClient(response);
    }
  }

  private ClientHandler acceptNextClientConnection(ServerSocket listeningSocket) {
    ClientHandler clientHandler = null;
    try {
      Socket clientSocket = listeningSocket.accept();
      System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
      clientHandler = new ClientHandler(clientSocket, this);
    } catch (IOException e) {
      System.err.println("Could not accept client connection: " + e.getMessage());
    }
    return clientHandler;
  }

  private ServerSocket openListeningSocket() {
    ServerSocket listeningSocket = null;
    try {
      listeningSocket = new ServerSocket(port);
    } catch (IOException e) {
      System.err.println("Could not open server socket: " + e.getMessage());
    }
    return listeningSocket;
  }
  public void stopServer() {
    isServerRunning = false;
    for (ClientHandler client : connectedClients) {
      client.close();
    }
  }

  public void removeClient(ClientHandler client) {
    connectedClients.remove(client);
  }

  public SmartTv getSmartTv() {
    return smartTv;
  }
}
