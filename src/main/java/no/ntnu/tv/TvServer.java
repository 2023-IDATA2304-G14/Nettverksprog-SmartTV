package no.ntnu.tv;

import no.ntnu.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TvServer {
  public static final String DEFAULT_HOSTNAME = "localhost";
  public static final int DEFAULT_PORT = 12345;
  private final SmartTv smartTv;
  private int port;
  boolean isServerRunning;
  private final List<ClientHandler> connectedClients = new ArrayList<>();
  private ServerSocket serverSocket;

  public TvServer(SmartTv smartTv) throws IllegalArgumentException, IOException {
    this(smartTv, DEFAULT_PORT);
  }

  public TvServer(SmartTv smartTv, int port) throws IllegalArgumentException, IOException {
    if (smartTv == null) {
      throw new IllegalArgumentException("SmartTv cannot be null");
    }
    if (port < 0 || port > 65535) {
      throw new IllegalArgumentException("Invalid port number: " + port);
    }

    this.smartTv = smartTv;
    this.port = port;
  }

  public void startServer() throws IOException {
    serverSocket = openListeningSocket();
    System.out.println("Server listening on port " + port);
    isServerRunning = true;
    while (isServerRunning) {
      ClientHandler clientHandler = acceptNextClientConnection(serverSocket);
      if (clientHandler != null) {
        connectedClients.add(clientHandler);
        clientHandler.start();
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

  private ServerSocket openListeningSocket() throws IOException {
    ServerSocket listeningSocket = null;
    listeningSocket = new ServerSocket(port);
    port = listeningSocket.getLocalPort();
    return listeningSocket;
  }
  public void stopServer() {
    isServerRunning = false;
    Iterator<ClientHandler> iterator = connectedClients.iterator();
    while (iterator.hasNext()) {
      ClientHandler clientHandler = iterator.next();
      clientHandler.close();
      iterator.remove();
    }
    try {
      serverSocket.close();
    } catch (IOException e) {
      System.err.println("Could not close server socket: " + e.getMessage());
    }
  }

  public void removeClient(ClientHandler client) {
    connectedClients.remove(client);
  }

  public SmartTv getSmartTv() {
    return smartTv;
  }

  public int getPort() {
    return port;
  }
}
