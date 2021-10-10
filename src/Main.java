public class Main {

  public static void main(String[] args) {
    Server server = new Server();
    Client client = new Client();

    client.send("Nachricht 1");
    client.send("Das ist ein längerer Text :)");

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    client.disconnect();
  }
}
