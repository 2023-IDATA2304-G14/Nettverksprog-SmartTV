package no.ntnu.tv;

import no.ntnu.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TvServer {
  public static final String DEFAULT_HOSTNAME = "localhost";
  public static final int DEFAULT_PORT = 1238;
  private final SmartTv smartTv;
  private int port;
  boolean isServerRunning;
  private final List<ClientHandler> connectedClients = new ArrayList<>();
  private ServerSocket serverSocket;
  private CountDownLatch portAssigned;

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

  public void startServer(CountDownLatch portAssigned) throws IOException {
    this.portAssigned = portAssigned;
    startServer();
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
    System.out.println("Broadcasting message to " + connectedClients.size() + " clients");
    for (ClientHandler client : connectedClients) {
      client.sendResponseToClient(response);
    }
  }

  private ClientHandler acceptNextClientConnection(ServerSocket listeningSocket) {
    ClientHandler clientHandler = null;
    if (listeningSocket == null || listeningSocket.isClosed()) {
      return null;
    }
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
    portAssigned.countDown();
    return listeningSocket;
  }
  public void stopServer() {
    isServerRunning = false;

    if (!connectedClients.isEmpty()) {
      List<ClientHandler> clientsCopy = new ArrayList<>(connectedClients);

      ExecutorService executor = Executors.newFixedThreadPool(clientsCopy.size());

      for (ClientHandler clientHandler : clientsCopy) {
        executor.execute(() -> {
          clientHandler.close();
          connectedClients.remove(clientHandler);
        });
      }

      executor.shutdown();
    }

    try {
      serverSocket.close();
      serverSocket = null;
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
