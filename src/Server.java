import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

  // über dieses serversocket werden die eingehenden verbindungen akzeptiert
  private ServerSocket serverSocket;

  // der einzige verbundene client kommuniziert über diesen socket mit diesem writer / reader mit
  // dem server -> für mehrere clients muss dies angepasst werden
  List<ServerConnection> connectedClients;

  public Server() {
    connectedClients = new ArrayList<>();
    try {
      serverSocket = new ServerSocket(8080);
      Thread t = new Thread(this::awaitConnection);
      t.start();
      System.out.println("[SERVER] waiting for Client");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void awaitConnection() {
    // hier wird im moment genau eine eingehende verbindung akzeptiert; ist der 1. Client verbunden,
    // dann wird dieser Thread beendet. für mehrere clients muss dies angepasst werden.
    while (true) {
      try {
        ServerConnection connection = new ServerConnection();
        connection.socket = serverSocket.accept();
        connection.writer = new ObjectOutputStream(connection.socket.getOutputStream());
        connection.reader = new ObjectInputStream(connection.socket.getInputStream());
        connectedClients.add(connection);
        System.out.println("[SERVER]: Client connected");
        Thread t = new Thread(() -> handleMessages(connection));
        t.start();
      } catch (IOException e) {
        System.out.println("Server no longer accepting clients");
        break;
      }
    }
  }

  public void shutdown(){
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private void handleMessages(ServerConnection connection) {
    // dieser thread wartet auf ankommende nachrichten des verbundenen clients. bei mehreren clients
    // gibt es für jeden client genau einen thread, der auf nachrichten wartet.
    while (connection.socket.isConnected()) {
      try {
        Message message = (Message) connection.reader.readObject();
        System.out.println("[SERVER] message received: " + message.content);
        sendToAllClients("Pong - " + message.content);
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("[SERVER] connection lost");
        break;
      }
    }
  }

  private void sendToAllClients(String s){
    for (ServerConnection c : connectedClients){
      try {
        c.writer.writeObject(new Message(s));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
