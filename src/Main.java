public class Main {

  public static void main(String[] args) throws InterruptedException {
    Server server = new Server(); // server startet
    Client client1 = new Client("c1"); // client verbindet sich zum server

    client1.send("Nachricht 1");
    Thread.sleep(1000);

    Client client2 = new Client("c2"); // c2 verbindet sich
    Thread.sleep(1000);
    client2.send("Das ist ein längerer Text :)");

    Thread.sleep(1000);
    client2.disconnect();

    Thread.sleep(1000);
    client1.send("Wo bist du c2?");

    Thread.sleep(1000);
    client1.disconnect();
    server.shutdown();
  }
}
