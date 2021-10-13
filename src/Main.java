public class Main {

  public static void main(String[] args) {
    new Server(); //server startet
    Client client = new Client(); //client verbindet sich zum server

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
