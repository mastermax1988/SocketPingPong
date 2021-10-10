import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private ServerSocket serverSocket;
  private Socket socket;
  private ObjectOutputStream writer;
  private ObjectInputStream reader;

  public Server() {
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
    try {
      socket = serverSocket.accept();
      writer = new ObjectOutputStream(socket.getOutputStream());
      reader = new ObjectInputStream(socket.getInputStream());
      System.out.println("[SERVER]: Client connected");
      Thread t = new Thread(this::handleMessages);
      t.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleMessages() {
    while (socket.isConnected()) {
      try {
        Message message = (Message) reader.readObject();
        System.out.println("[SERVER] message received: " + message.content);
        writer.writeObject(new Message("Pong - " + message.content));
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("[SERVER] connection lost");
        break;
      }
    }
  }
}
