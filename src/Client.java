import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

  private Socket socket;
  private ObjectInputStream reader;
  private ObjectOutputStream writer;
  private String name;

  public Client(String name) {
    this.name = name;
    try {
      socket = new Socket();
      socket.connect(new InetSocketAddress("127.0.0.1", 8080));
      writer = new ObjectOutputStream(socket.getOutputStream());
      reader = new ObjectInputStream(socket.getInputStream());
      Thread t = new Thread(this::listen);
      t.start();
      System.out.println("[CLIENT " + name + "] connected");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void send(String s) {
    try {
      writer.writeObject(new Message("[" + name + "] " + s));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {
    try {
      System.out.println("[CLIENT " + name + "] disconnecting");
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void listen() {
    while (socket.isConnected()) {
      Message message = null;
      try {
        message = (Message) reader.readObject();
        System.out.println("[CLIENT " + name + "] received: " + message.content);
      } catch (IOException e) {
        System.out.println("[CLIENT " + name + "] connection lost");
        break; // stop listening
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
