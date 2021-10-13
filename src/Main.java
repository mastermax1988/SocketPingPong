public class Main {

  public static void main(String[] args) {
    new Server(); //server startet
    Client client1 = new Client(); //client verbindet sich zum server
    Client client2 = new Client();

    client1.send("c1 Nachricht 1");
    client2.send("c2 asdf");
    client1.send("c1 Das ist ein längerer Text :)");


    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    client1.disconnect();
  }
}
