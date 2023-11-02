package no.ntnu.tv;

import no.ntnu.message.Command;
import no.ntnu.message.Message;
import no.ntnu.message.MessageSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TvServer {
  public static final String DEFAULT_HOSTNAME = "localhost";
  public static final int DEFAULT_PORT = 12345;
  private final SmartTv smartTv;
  private boolean isServerRunning;
  private ServerSocket listeningSocket;
  private Socket clientSocket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private int port;
  private boolean useCustomPort;

  public TvServer(SmartTv smartTv) {
    this.smartTv = smartTv;
    startServer();
  }

  public TvServer(SmartTv smartTv, int port) {
    this.port = port;
    this.useCustomPort = true;
    this.smartTv = smartTv;
    startServer();
  }

  private void startServer() {
    listeningSocket = openListeningSocket();
    System.out.println("Server listening on port " + DEFAULT_PORT);
    if (listeningSocket != null) {
      isServerRunning = true;
      while (isServerRunning) {
        clientSocket = acceptNextClientConnection(listeningSocket);
        if (clientSocket != null) {
          System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
          handleClient(clientSocket);
        }
      }
    }
  }

  private void handleClient(Socket clientSocket) {
    Message response;
    do {
      Command clientCommand = readClientRequest();
      System.out.println("Received from client: " + clientCommand);
      response = clientCommand.execute(smartTv);
      if (response != null) {
        sendResponseToClient(response);
      }
    } while (response != null);
  }

  private void sendResponseToClient(Message response) {
    String serializedResponse = MessageSerializer.serialize(response);
    socketWriter.println(serializedResponse);
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

  private ServerSocket openListeningSocket() {
    ServerSocket listeningSocket = null;
    port = useCustomPort ? port : DEFAULT_PORT;
    try {
      listeningSocket = new ServerSocket(port);
    } catch (IOException e) {
      System.err.println("Could not open server socket: " + e.getMessage());
    }
    return listeningSocket;
  }

  public SmartTv getSmartTv() {
    return smartTv;
  }
}
